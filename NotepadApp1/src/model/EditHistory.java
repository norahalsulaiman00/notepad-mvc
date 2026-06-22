package model;
import java.util.Stack;

/**
 * EditHistory ADT - manages undo/redo
 * Mutable ADT with two stacks.
 */
public class EditHistory {
    
    private Stack<String> undoStack;
    private Stack<String> redoStack;
    
      // Representation Invariant (RI):
      //  undoStack is never null
      //  redoStack is never null 
     //   Abstraction Function (AF):
     //   AF(undoStack, redoStack) = edit timeline where:
     //	 undoStack = past states
     //	 redoStack = future states
    
    /**
     * Creates new history.
      * @effects initializes empty undo and redo stacks
     */
    public EditHistory() { //creators
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        checkRep();
    }
    
    /**
     * Saves state to history.
     * @param state content to save, must be non-null
     * @requires state != null
      * @effects adds state to the undo stack and clears the redo stack
     */
    public void save(String state) {
        undoStack.push(state);
        redoStack.clear();
        checkRep();
    }
    
    /**
     * Checks if undo available.
     * @return true if can undo
     */
    public boolean canUndo() {
        return undoStack.size() > 1;
    }
    
    /**
     * Checks if redo available.
     * @return true if can redo
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    /**
     * Performs undo.
     * @return previous state
     * @requires canUndo()
     * @effects moves the current state to the redo stack and returns the previous state
     */
    public String undo() {
        redoStack.push(undoStack.pop());
        return undoStack.peek();
    }
    
    /**
     * Performs redo.
     * @return next state
     * @requires canRedo()
     * @effects moves the next state to the undo stack and returns it
     */
    public String redo() {
        String nextState = redoStack.pop();
        undoStack.push(nextState);
        return nextState;
    }
    
    /**
     * Checks representation invariant.
     */
    private void checkRep() {
        assert undoStack != null;
        assert redoStack != null;
    }
}