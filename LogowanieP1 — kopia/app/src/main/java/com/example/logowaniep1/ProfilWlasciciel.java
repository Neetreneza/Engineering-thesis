package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ProfilWlasciciel extends AppCompatActivity {

    Button wyswietlObiektyWlasciciela;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_wlasciciel);

        wyswietlObiektyWlasciciela = (Button) findViewById(R.id.obiektyWlascicielaProfilW);


        wyswietlObiektyWlasciciela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wyswietlObiektyWlascicielaF();
            }
        });



    }

    private void wyswietlObiektyWlascicielaF() {
        Intent intent = new Intent(this,Obiekty.class);
        startActivity(intent);
    }

}
