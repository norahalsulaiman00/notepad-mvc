package controller;
import model.*;
import view.NotepadView;
import javax.swing.*;
import java.io.*;
import java.util.List;

/**
 * NotepadController - handles user actions.
 */
public class NotepadController {
    
    private Document document; // model
    private EditHistory history;
    private NotepadView view;
    
    public NotepadController(Document document, NotepadView view) {
        this.document = document;
        this.view = view;
        this.history = new EditHistory();
        history.save("");
    }
    
    //  Create new document
    public void createNew() {
        if (document.isModified() && !askToClose()) {
            return;
        }
        document.setContent("");
        document.setMetadata(new DocumentMetadata(null, false));
        history = new EditHistory();
        history.save("");
    }
    
    // Save
    public void saveDocument() {
        if (document.getMetadata().getFilePath() == null) {
            saveAsDocument();
        } else {
            writeToFile(document.getMetadata().getFilePath());
        }
    }
    
    // Save As
    public void saveAsDocument() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            writeToFile(filePath);
        }
    }
    
    private void writeToFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(document.getContent());
            writer.close();
            document.setMetadata(new DocumentMetadata(filePath, false));
        } catch (Exception error) {
            JOptionPane.showMessageDialog(view, "Error saving file");
        }
    }
    
    // Text modification
    public void handleTextChange(String newText) {
        if (!newText.equals(document.getContent())) {
            history.save(document.getContent());
            document.setContent(newText);
        }
    }
    
    //  Undo
    public void undoEdit() {
        if (history.canUndo()) {
            String previousText = history.undo();
            document.setContent(previousText);
            view.updateText(previousText);
        }
    }
    
    //  Redo
    public void redoEdit() {
        if (history.canRedo()) {
            String nextText = history.redo();
            document.setContent(nextText);
            view.updateText(nextText);
        }
    }
    
    // Find
    public void findText(String searchText) {
        List<Integer> positions = document.find(searchText);
        if (positions.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Text not found");
        } else {
            view.highlightText(positions.get(0), searchText.length());
        }
    }
    
    //  Change font
    public void changeFont(String fontName, int fontSize) {
        ApplicationSettings settings = ApplicationSettings.getInstance();
        settings.setFontName(fontName);
        settings.setFontSize(fontSize);
        view.applyFont();
    }
    
    //  Toggle theme
    public void toggleTheme() {
        ApplicationSettings settings = ApplicationSettings.getInstance();
        String currentTheme = settings.getTheme();
        String newTheme = currentTheme.equals("Light") ? "Dark" : "Light";
        settings.setTheme(newTheme);
        view.applyTheme();
    }
    
    // Exit with confirmation
    public void exitApplication() {
        if (document.isModified() && !askToClose()) {
            return;
        }
        System.exit(0);
    }
    
    private boolean askToClose() {
        int choice = JOptionPane.showConfirmDialog(view, "You have unsaved changes. Close anyway?","Unsaved Changes",JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }
}
