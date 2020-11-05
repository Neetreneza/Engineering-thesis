package com.example.logowaniep1;

public class Singleton {
    private static final Singleton INSTANCE = new Singleton();
    private String uzytkownikID; // email tu siedzi

    private Singleton() {
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }

    public String getUzytkownikID() {
        return uzytkownikID;
    }

    public void setUzytkownikID(String uzytkownikID) {
        this.uzytkownikID = uzytkownikID;
    }
}