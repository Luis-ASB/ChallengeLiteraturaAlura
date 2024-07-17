package com.literatura.challenger_literatura.model;

public enum Idioma {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    ITALIAN("it"),
    PORTUGUESE("pt"),
    GERMAN("de"),
    RUSSIAN("ru");

    
    private final String language;
    
    Idioma(String language) {
        this.language = language;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public static Idioma fromString(String text) {
        for (Idioma language : Idioma.values()) {
            if (language.language.equalsIgnoreCase(text)) {
                return language;
            }
        }
        return null;
    }
}
