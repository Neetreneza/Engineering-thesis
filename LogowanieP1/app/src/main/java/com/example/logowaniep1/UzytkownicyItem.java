package com.example.logowaniep1;

public class UzytkownicyItem {
    private int imageResource;
    private String nazwa;

    public UzytkownicyItem(int image, String nazwaWe){
        imageResource = image;
        nazwa = nazwaWe;
    }

    public int getImageResourceUzytkownika(){
        return imageResource;
    }

    public String getNazwaUzytkownika(){
        return nazwa;
    }
}
