package main;
import model.Document;
import view.NotepadView;
import controller.NotepadController;

/**
 * This class starts the Notepad program.
 * It creates the Model, View, and Controller,
 * connects them together, and opens the main window.
 * All the application setup happens here.
 */
public class NotepadApp {
    public static void main(String[] args) {

        // Run the GUI on the Swing thread
        javax.swing.SwingUtilities.invokeLater(() -> {

            // Create Model (document data)
            Document document = new Document();

            // Create View (the GUI)
            NotepadView view = new NotepadView(document);

            // Create Controller (handles user actions)
            NotepadController controller = new NotepadController(document, view);

            // Connect View with Controller
            view.setController(controller);

            // Show the program window
            view.setVisible(true);
        });
    }
}