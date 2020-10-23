package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DodajWydarzenie extends AppCompatActivity {

    EditText nazwa,cena,start,koniec;
    String headUser;
    Button dodajWydarzenie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wydarzenie);

        nazwa = (EditText) findViewById(R.id.nazwaET);
        cena = (EditText) findViewById(R.id.cenaET);
        start = (EditText) findViewById(R.id.startET);
        koniec = (EditText) findViewById(R.id.koniecET);
        dodajWydarzenie = (Button) findViewById(R.id.dodajWydarzenie);
        headUser = ustalHeadUser();

        dodajWydarzenie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajWydarzenie();
            }
        });


    }

    private void dodajWydarzenie() {
        Intent resultIntent = new Intent();

        resultIntent.putExtra("nazwa",nazwa.getText().toString());
        resultIntent.putExtra("cena",cena.getText().toString());
        resultIntent.putExtra("start",start.getText().toString());
        resultIntent.putExtra("koniec",koniec.getText().toString());
        resultIntent.putExtra("headUser",headUser);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public String ustalHeadUser()
    {
        //funkcja ustalania nazwy uzytkownika ktory zaklada
        return "PawelG98";
    }




}
