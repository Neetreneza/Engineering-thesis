package com.example.logowaniep1;

public class SpisUzytkownikowItem {
    private int zdjecieUzytkownika;
    private String nazwaUzytkownika;
    private String emailUzytkownika;
    private int id;


    public SpisUzytkownikowItem(int zdjecieWe, String nazwaWe, String emailWe, int idWe){
        zdjecieUzytkownika = zdjecieWe;
        nazwaUzytkownika = nazwaWe;
        emailUzytkownika = emailWe;
        id = idWe;
    }

    public int getZdjecieUzytkownika()
    {
        return  zdjecieUzytkownika;
    }

    public String getNazwaUzytkownika(){
        return nazwaUzytkownika;
    }

    public String getEmailUzytkownika(){
        return  emailUzytkownika;
    }

    public int getId(){
        return  id;
    }

}
