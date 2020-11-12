package com.example.logowaniep1;

public class ExampleItem {
    private int imageResource,imageResource2;
    private String text1,text2,start,koniec,headUser,miejsceE, obiektId, opis;
    int wydarzenieId;

    public ExampleItem(int image , String txt1, String txt2, String startWe, String koniecWe, int image2, String headUserWe, String miejsce, String obiektIdWe, int wydarzenieIdWe, String opisWe)
    {
        imageResource = image;
        text1 = txt1;
        text2 = txt2;
        start = startWe;
        koniec = koniecWe;
        imageResource2 = image2;
        headUser = headUserWe;
        miejsceE = miejsce;
        obiektId = obiektIdWe;
        wydarzenieId = wydarzenieIdWe;
        opis = opisWe;
    }

    public int getImageResource()
    {
        return imageResource;

    }

    public void changeText1(String t)
    {
        text1 = t;
    }
    public String getMiejsceE()
    {
        return miejsceE;
    }

    public String getText1()
    {
        return text1;
    }

    public String getText2()
    {
        return text2;
    }

    public String getStart()
    {
        return start;
    }

    public String getKoniec()
    {
        return  koniec;
    }

    public int getImageResource2()
    {
        return imageResource2;
    }

    public String getObiektId()
    {
        return  obiektId;
    }

    public int getWydarzenieId(){
        return  wydarzenieId;
    }

    public String getOpis()
    {
        return opis;
    }

    String getHeadUser()//kiedys moze nawet jakies argumenty
    {
        //funkcja kiedys bedzie napisana
        return headUser;
    }


}
