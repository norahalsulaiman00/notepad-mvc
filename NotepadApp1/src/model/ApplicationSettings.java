package model;

/**
 * ApplicationSettings - Singleton for global settings (Requirement 12).
 * This class stores the global application settings such as theme and font.
 * It uses the Singleton pattern because the application should have only one
 * settings object shared across all components.
 */
public class ApplicationSettings {
    
    private static ApplicationSettings instance; 
    private String theme;
    private String fontName;
    private int fontSize;
    /**
     * Private constructor.
     * @effects initializes defaults
     */
    private ApplicationSettings() {  //Constractor
        theme = "Light";
        fontName = "Monospaced";
        fontSize = 14;
    }
    
    /**
     * Returns the single instance of ApplicationSettings.
     *
     * @requires none
     * @return the unique global ApplicationSettings instance
     * @effects If the instance does not exist, this method creates it.
     */
    public static ApplicationSettings getInstance() {   // accosser method 
        if (instance == null) {
            instance = new ApplicationSettings();
        }
        return instance;
    }
    
    public String getTheme() { 
        return theme; 
    }
    
    public void setTheme(String newTheme) { 
        theme = newTheme; 
    }
    
    public String getFontName() { 
        return fontName; 
    }
    
    public void setFontName(String newFontName) { 
        fontName = newFontName; 
    }
    
    public int getFontSize() { 
        return fontSize; 
    }
    
    public void setFontSize(int newFontSize) { 
        fontSize = newFontSize; 
    }
}