package com.example.logowaniep1;

public class ObiektyItem {
    private int imageResource;
    private String oText1,oText2,oText3;

    public ObiektyItem(int image , String txt1, String txt2, String txt3)
    {
        imageResource = image;
        oText1 = txt1;
        oText2 = txt2;
        oText3 = txt3;

    }

    public int getImageResource()
    {
        return imageResource;

    }

    public void changeText1(String t)
    {
        oText1 = t;
    }

    public String getOText1()
    {
        return oText1;
    }

    public String getOText2()
    {
        return oText2;
    }

    public String getOText3()
    {
        return oText3;
    }




}
