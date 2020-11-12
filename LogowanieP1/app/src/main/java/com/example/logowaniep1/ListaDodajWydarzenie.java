package com.example.logowaniep1;

import androidx.annotation.NonNull;

public class ListaDodajWydarzenie
{
    String nazwa;
    int id;
    public  ListaDodajWydarzenie(String nazwaWe, int idWe)
    {
        nazwa = nazwaWe;
        id = idWe;
    }

    @NonNull
    @Override
    public String toString() {
        return nazwa;
    }
}
