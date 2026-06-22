package view;
import controller.NotepadController;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * NotepadView - UI (Observer implementation).
 */
public class NotepadView extends JFrame implements DocumentListener {
    
    private JTextArea textArea;
    private JLabel statusBar;
    private NotepadController controller;
    private Document document;
    
    public NotepadView(Document document) {
        this.document = document;
        document.addListener(this); //عشان تسجل نفسها ك احد المتابعين 
        
        setTitle("Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        // Requirement 11: Word/char count
        statusBar = new JLabel("Words: 0 | Chars: 0");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusBar, BorderLayout.SOUTH);
        
        createMenus();
        
        addWindowListener(new WindowAdapter() {
        	
            public void windowClosing(WindowEvent event) {
                controller.exitApplication();
            }
        });
        
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent event) { 
                textChanged(); 
            }
            public void removeUpdate(javax.swing.event.DocumentEvent event) { 
                textChanged(); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent event) {}
            
            private void textChanged() {
                if (controller != null) {
                    controller.handleTextChange(textArea.getText());
                }
            }
        });
    }
    
    public void setController(NotepadController controller) {
        this.controller = controller;
    }
    
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("New", e -> controller.createNew()));
        fileMenu.add(createMenuItem("Save", e -> controller.saveDocument()));
        fileMenu.add(createMenuItem("Save As", e -> controller.saveAsDocument()));
        fileMenu.add(createMenuItem("Exit", e -> controller.exitApplication()));
        
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(createMenuItem("Undo", e -> controller.undoEdit()));
        editMenu.add(createMenuItem("Redo", e -> controller.redoEdit()));
        editMenu.add(createMenuItem("Find", e -> showFindDialog()));
        
        JMenu formatMenu = new JMenu("Format");
        formatMenu.add(createMenuItem("Font", e -> showFontDialog()));
        
        JMenu viewMenu = new JMenu("View");
        viewMenu.add(createMenuItem("Toggle Theme", e -> controller.toggleTheme()));
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        setJMenuBar(menuBar);
    }
    
    private JMenuItem createMenuItem(String text, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(action);
        return menuItem;
    }
    
    private void showFindDialog() {
        JTextField searchField = new JTextField(20);
        Object[] message = {"Find:", searchField};
        
        int choice = JOptionPane.showConfirmDialog(this, message, "Find", 
            JOptionPane.OK_CANCEL_OPTION);
        
        if (choice == JOptionPane.OK_OPTION) {
            String searchText = searchField.getText();
            if (!searchText.isEmpty()) {
                controller.findText(searchText);
            }
        }
    }
    
    private void showFontDialog() {
        String[] fonts = {"Monospaced", "SansSerif", "Serif"};
        Integer[] sizes = {10, 12, 14, 16, 18, 20};
        
        JComboBox<String> fontBox = new JComboBox<>(fonts);
        JComboBox<Integer> sizeBox = new JComboBox<>(sizes);
        
        Object[] message = {"Font:", fontBox, "Size:", sizeBox};
        
        int choice = JOptionPane.showConfirmDialog(this, message, "Font",
            JOptionPane.OK_CANCEL_OPTION);
        
        if (choice == JOptionPane.OK_OPTION) {
            String selectedFont = (String) fontBox.getSelectedItem();
            Integer selectedSize = (Integer) sizeBox.getSelectedItem();
            controller.changeFont(selectedFont, selectedSize);
        }
    }
    
    public void updateText(String text) {
        textArea.setText(text);
    }
    
    public void highlightText(int start, int length) {
        textArea.setSelectionStart(start);
        textArea.setSelectionEnd(start + length);
        textArea.requestFocus();
    }
    
    public void applyFont() {
        ApplicationSettings settings = ApplicationSettings.getInstance(); // حق السنقلتون
        textArea.setFont(new Font(settings.getFontName(), Font.PLAIN, 
            settings.getFontSize()));
    }
    
    public void applyTheme() {
        ApplicationSettings settings = ApplicationSettings.getInstance(); // حق السنقلتون
        if (settings.getTheme().equals("Dark")) {
            textArea.setBackground(Color.DARK_GRAY);
            textArea.setForeground(Color.WHITE);
        } else {
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
        }
    }
    
    // Observer pattern
    @Override
    public void onChange() {   //observers
        String title = document.getMetadata().getFilePath() != null ? 
            document.getMetadata().getFilePath() : "Untitled";
        
        if (document.isModified()) {
            title += " *";
        }
        
        setTitle(title);
        statusBar.setText("Words: " + document.getWordCount() + 
            " | Chars: " + document.getCharCount());
    }
}