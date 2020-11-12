package com.example.logowaniep1;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

public class ObiektDoWeryfikacji implements Parcelable {

    private int imageResource, obiekt_id, kryty, szatnia, oplata, aktywny;
    private String nazwa, miejscowosc, ulica, numer_lokalu, wojewodztwo, kod_pocztowy, email,  telefon, numer_rachunku, opis;
    private Timestamp data;

    protected ObiektDoWeryfikacji(Parcel in) {
        imageResource = in.readInt();
        obiekt_id = in.readInt();
        kryty = in.readInt();
        szatnia = in.readInt();
        oplata = in.readInt();
        aktywny = in.readInt();
        nazwa = in.readString();
        miejscowosc = in.readString();
        ulica = in.readString();
        numer_lokalu = in.readString();
        wojewodztwo = in.readString();
        kod_pocztowy = in.readString();
        email = in.readString();
        telefon = in.readString();
        numer_rachunku = in.readString();
        opis = in.readString();
    }

    public static final Creator<ObiektDoWeryfikacji> CREATOR = new Creator<ObiektDoWeryfikacji>() {
        @Override
        public ObiektDoWeryfikacji createFromParcel(Parcel in) {
            return new ObiektDoWeryfikacji(in);
        }

        @Override
        public ObiektDoWeryfikacji[] newArray(int size) {
            return new ObiektDoWeryfikacji[size];
        }
    };

    public int getImageResource() {
        return imageResource;
    }

    public int getObiekt_id() {
        return obiekt_id;
    }

    public int getKryty() {
        return kryty;
    }

    public int getSzatnia() {
        return szatnia;
    }

    public int getOplata() {
        return oplata;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public String getUlica() {
        return ulica;
    }

    public String getNumer_lokalu() {
        return numer_lokalu;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public String getKod_pocztowy() {
        return kod_pocztowy;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getNumer_rachunku() {
        return numer_rachunku;
    }

    public String getOpis() {
        return opis;
    }

    public int getAktywny() {
        return aktywny;
    }

    public Timestamp getData() {
        return data;
    }

    //konstruktor
    public ObiektDoWeryfikacji(int image, int id, String txt1, String txt2, String txt3, String txt4, String txt5, String txt6, String txt7, String txt8, int txt9, int txt10, int txt11, String txt12, String txt13, int txt14, Timestamp data1) {
        imageResource = image;
        obiekt_id = id;
        nazwa = txt1;
        miejscowosc = txt2;
        ulica = txt3;
        numer_lokalu = txt4;
        wojewodztwo = txt5;
        kod_pocztowy = txt6;
        email = txt7;
        telefon = txt8;
        kryty = txt9;
        szatnia = txt10;
        oplata = txt11;
        numer_rachunku = txt12;
        opis = txt13;
        aktywny = txt14;
        data = data1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
        dest.writeInt(obiekt_id);
        dest.writeInt(kryty);
        dest.writeInt(szatnia);
        dest.writeInt(oplata);
        dest.writeInt(aktywny);
        dest.writeString(nazwa);
        dest.writeString(miejscowosc);
        dest.writeString(ulica);
        dest.writeString(numer_lokalu);
        dest.writeString(wojewodztwo);
        dest.writeString(kod_pocztowy);
        dest.writeString(email);
        dest.writeString(telefon);
        dest.writeString(numer_rachunku);
        dest.writeString(opis);
    }
}
