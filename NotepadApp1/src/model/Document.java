package model;
import java.util.*;

/**
 * Document ADT - represents a text document.
 * Mutable ADT that manages document content and notifies observers when changes happen.
 */


public class Document {     //Subject المودل
    
    private String content;
    private DocumentMetadata metadata;
    private List<DocumentListener> listeners;

    
    // Representation Invariant (RI):
    // content != null, metadata != null, listeners != null
    // Abstraction Function (AF):
    // Represents a text document where:
    // content = actual text of the document
    // metadata = file information and modified status
    // listeners = list of observers waiting for updates

    /**
     * Creates new empty document.
     * @requires nothing
     * @effects creates document with empty content
     */
    public Document() { //Constractor
        this.content = "";
        this.metadata = new DocumentMetadata(null, false);
        this.listeners = new ArrayList<>();
        checkRep();
    }
    
    /**
     * Gets document content.
     * @return current text content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Updates the document content.
     *
     * @param newContent the new text (must not be null)
     * @requires newContent != null
     * @effects updates the content, marks the document as modified, and notifies all listeners
     */  
    
    public void setContent(String newContent) {
        this.content = newContent;
        this.metadata = metadata.withModified(true);
        notifyListeners();
        checkRep();
    }
    
    /**
     * Gets the document metadata.
     *
     * @requires nothing
     * @return the metadata object
     * @effects none
     */
    public DocumentMetadata getMetadata() {
        return metadata;
    }
    
    /**
     * Sets document metadata.
     * @param newMetadata new metadata; must not be null
     * @requires newMetadata != null
     * @effects updates metadata
     */
    public void setMetadata(DocumentMetadata newMetadata) {
        this.metadata = newMetadata;
        checkRep();
    }
    
    /**
     * Checks if document is modified.
     * @return true if there are unsaved changes
     */
    public boolean isModified() {
        return metadata.isModified();
    }
    
    /**
     * Marks document as saved.
     * @effects sets modified to false and notifies the listeners
     */
    public void markSaved() {
        this.metadata = metadata.withModified(false);
        notifyListeners();
        checkRep();
    }
    
    /**
     * Adds listener for document changes.
     * @param listener listener to add, must be non-null
     * @requires listener != null
     * @effects adds listener to list
     */
    public void addListener(DocumentListener listener) {
        listeners.add(listener);
        checkRep();
    }
    
    /**
     * Notifies all listeners of changes.
     * @effects calls onChange on all listeners
     */
    private void notifyListeners() {
        for (DocumentListener listener : listeners) {
            listener.onChange();
        }
    }
    
    //R-11
    /**
     * Counts words using regex
     * @return number of words
     * @effects splits by whitespace using \\s+ regex
     */
    public int getWordCount() {
        if (content.trim().isEmpty()) {
            return 0;
        }
        return content.trim().split("\\s+").length;
    }
  
    /**
     * Counts characters
     * @return number of characters
     */
    public int getCharCount() {
        return content.length();
    }
    
    //R-8
    /**
     * Finds text in document (Requirement 8).
     * @param searchText text to find, must be non-null
     * @return list of positions where the text is found
     * @requires searchText != null && searchText.length() > 0
     * @effects returns all match positions
     */
    public List<Integer> find(String searchText) {
        List<Integer> positions = new ArrayList<>();
        String lowercaseContent = content.toLowerCase();
        String lowercaseSearch = searchText.toLowerCase();
        int index = 0;
        while ((index = lowercaseContent.indexOf(lowercaseSearch, index)) != -1) {
            positions.add(index);
            index++;
        }
        return positions;
    }
    
    /**
     * Checks representation invariant.
     */
    private void checkRep() {
        assert content != null;
        assert metadata != null;
        assert listeners != null;
    }
}
