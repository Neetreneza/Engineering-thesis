package com.example.logowaniep1;

public class Singleton {
    private static final Singleton INSTANCE = new Singleton();
    private String uzytkownikEmail; // email tu siedzi
    private int uzytkownikID;

    private Singleton() {
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }

    public int getUzytkownikID() {
        return uzytkownikID;
    }

    public void setUzytkownikID(int uzytkownikID) {
        this.uzytkownikID = uzytkownikID;
    }
    public String getuzytkownikEmail()
    {
        return uzytkownikEmail;
    }
    public void setUzytkownikEmail(String em)
    {
        this.uzytkownikEmail = em;
    }

}