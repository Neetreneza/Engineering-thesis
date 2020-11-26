package com.example.logowaniep1;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class ObiektyItem implements Parcelable {
    private int imageResource, obiekt_id, kryty, szatnia, oplata, aktywny;
    private String nazwa, miejscowosc, ulica, numer_lokalu, wojewodztwo, kod_pocztowy, email,  telefon, numer_rachunku, opis, cena, dyscyplina, krytoscSzatnosc, wlasciciel_kontakt;
    private Timestamp data;

    public ObiektyItem(int image, int id, String txt1, String txt2, String txt3, String txt4, String txt5, String txt6, String txt7, String txt8, int txt9, int txt10, int txt11, String txt12, String txt13, int txt14, Timestamp data1, String txt15, String txt16, String txt17, String txt18)
    {
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
        cena = txt15;
        dyscyplina = txt16;
        krytoscSzatnosc = txt17;
        wlasciciel_kontakt = txt18;
    }

    protected ObiektyItem(Parcel in) {
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
        cena = in.readString();
        dyscyplina = in.readString();
        krytoscSzatnosc = in.readString();
        wlasciciel_kontakt = in.readString();
    }

    public static final Creator<ObiektyItem> CREATOR = new Creator<ObiektyItem>() {
        @Override
        public ObiektyItem createFromParcel(Parcel in) {
            return new ObiektyItem(in);
        }

        @Override
        public ObiektyItem[] newArray(int size) {
            return new ObiektyItem[size];
        }
    };

    public int getImageResource()
    {
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

    public int getAktywny() {
        return aktywny;
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

    public String getCena() {
        return cena;
    }

    public String getDyscyplina() {
        return dyscyplina;
    }

    public Timestamp getData() {
        return data;
    }

    public String getKrytoscSzatnosc() {
        return krytoscSzatnosc;
    }

    public String getWlasciciel_kontakt() {
        return wlasciciel_kontakt;
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
        dest.writeString(cena);
        dest.writeString(dyscyplina);
        dest.writeString(krytoscSzatnosc);
        dest.writeString(wlasciciel_kontakt);
    }
}
