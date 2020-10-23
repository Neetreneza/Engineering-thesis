package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class SzczegolyWydarzenia extends AppCompatActivity {
    EditText headUser,cena,data,godzina,miejsce,nazwa;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_wydarzenia);

        nazwa = (EditText) findViewById(R.id.nazwaWydSzczeET);
        headUser = (EditText) findViewById(R.id.headUserWydSzczeET);
        cena = (EditText) findViewById(R.id.cenaWydSzczeET);
        data = (EditText) findViewById(R.id.dataWydSzczeET);
        godzina = (EditText) findViewById(R.id.godzinaWydSzczeET);
        miejsce = (EditText) findViewById(R.id.miejsceWydSzczeET);
        image = (ImageView) findViewById(R.id.imageView3);

        Intent intent = getIntent();
        nazwa.setText(intent.getStringExtra("nazwa"));
        headUser.setText(intent.getStringExtra("headUser"));
        cena.setText(intent.getStringExtra("cena"));
        data.setText(intent.getStringExtra("start"));
        godzina.setText(intent.getStringExtra("koniec"));
        image.setImageResource(intent.getIntExtra("img",0));
        //miejsce.setText(intent.getStringExtra("miejsce"));




    }
}
