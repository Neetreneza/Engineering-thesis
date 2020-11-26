package com.example.logowaniep1;

public class ObiektyItem {
    private int imageResource;
    private String oText1,oText2,oText3,oText4,oText5,oText6,oText0;

    public ObiektyItem(int image , String txt1, String txt2, String txt3, String txt4, String txt5, String txt6, String txt7)
    {
        imageResource = image;
        oText1 = txt1;
        oText2 = txt2;
        oText3 = txt3;
        oText4 = txt4;
        oText5 = txt5;
        oText6 = txt6;
        oText0 = txt7;

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

    public String getOText4()
    {
        return oText4;
    }

    public String getOText5()
    {
        return oText5;
    }

    public String getOText6()
    {
        return oText6;
    }

    public String getOText0()
    {
        return oText0;
    }

    public void changeSzczegolowosc()
    {

    }




}
