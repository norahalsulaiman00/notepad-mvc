package model;

/**
 * DocumentListener - Observer pattern interface.
 */
public interface DocumentListener { 
    /**
     * Called on document change.
     * @effects notifies of change
     */
    void onChange();
}