package model;

import java.util.Objects;

/**
 * DocumentMetadata - Immutable ADT for document properties.
 */
public final class DocumentMetadata { //ADT
    
    private final String filePath;
    private final boolean modified;
    
    //Representation Invariant (RI):
    //  true (no constraints)
    // Abstraction Function (AF):
    //  AF(filePath, modified) = metadata object representing:
    // filePath: the file location (null if not saved yet)
    // modified: true if the document has unsaved changes
    
    /**
     * Creates metadata.
     * @param path file path (null for new docs)
     * @param isModified modification status
     */
    public DocumentMetadata(String path, boolean isModified) { //Creators
        this.filePath = path;
        this.modified = isModified;
    }
    
    /**
     * Gets file path.
     *
     * @requires nothing
     * @return file path as a String (may be null)
     * @effects none
     */
    public String getFilePath() { //observers (ADT)
        return filePath;
    }
    
    /**
     * Checks if modified.
     * @return true if modified
     */
    public boolean isModified() { //observers (ADT)
        return modified;
    }
    
    /**
     * Creates new metadata with different path.
     * @param newPath new file path
     * @return new DocumentMetadata
     * @effects returns new instance
     */
    public DocumentMetadata withFilePath(String newPath) { //producers
        return new DocumentMetadata(newPath, modified);
    }
    
    /**
     * Creates new metadata with different modified status.
     * @param isModified new status
     * @return new DocumentMetadata
     * @effects returns new instance
     */
    public DocumentMetadata withModified(boolean isModified) {  //producers
        return new DocumentMetadata(filePath, isModified);
    }
    
    /**
     * Checks if this metadata object is equal to another one.
     * Two metadata objects are considered equal when:
     * they have the same filePath (both null or equal strings)
     * and they have the same modified status.
     *
     * @param obj the object to compare
     * @requires nothing
     * @return true if both filePath and modified match, false otherwise
     * @effects none
     */
    @Override
    public boolean equals(Object obj) { //observers (ADT)
        if (this == obj)
        	return true;
        if (!(obj instanceof DocumentMetadata)) 
        	return false;
        DocumentMetadata other = (DocumentMetadata) obj;
        
        return Objects.equals(filePath, other.filePath) && modified == other.modified;
    }

    /**
     * Computes a hash code for this metadata.
     * The hash code is consistent with equals(), and is based on:
     *  filePath
     *  modified
     *
     * @requires nothing
     * @return integer hash code
     * @effects none
     */
    @Override
    public int hashCode() { //observers (ADT)
        return Objects.hash(filePath , modified);
    }
}
