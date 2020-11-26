package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SzczegolyObiekt extends AppCompatActivity implements OnMapReadyCallback{
    @BindView(R.id.zdjecie)
    ImageView zdjecie;
    @BindView(R.id.nazwaTextView2)
    TextView nazwaTV;
    @BindView(R.id.dyscyplinaTextView2)
    TextView dyscyplinaTV;
    @BindView(R.id.adresTextView2)
    TextView adresTV;
    @BindView(R.id.emailTextView2)
    TextView emailTV;
    @BindView(R.id.telefonTextView2)
    TextView telefonTV;
    @BindView(R.id.opisTextView2)
    TextView opisTV;
    @BindView(R.id.krytyTextView2)
    TextView krytyTV;
    @BindView(R.id.szatniaTextView2)
    TextView szatniaTV;
    @BindView(R.id.cenaTextView2)
    TextView cenaTV;
    @BindView(R.id.kierownikTextView2)
    TextView kierownikTV;

    private int imageResource, obiekt_id, kryty, szatnia;
    private String nazwa, miejscowosc, ulica, numer_lokalu, email,  telefon, opis, dyscyplina, cena, kierownik_kontakt;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_obiekt);

        Intent intent = getIntent();
        ObiektyItem obiekt = intent.getParcelableExtra("obiekt");

        ButterKnife.bind(this);

        imageResource = obiekt.getImageResource();
        obiekt_id = obiekt.getObiekt_id();
        nazwa = obiekt.getNazwa();
        dyscyplina = obiekt.getDyscyplina();
        miejscowosc = obiekt.getMiejscowosc();
        ulica = obiekt.getUlica();
        numer_lokalu = obiekt.getNumer_lokalu();
        email = obiekt.getEmail();
        telefon = obiekt.getTelefon();
        opis = obiekt.getOpis();
        kryty = obiekt.getKryty();
        szatnia = obiekt.getSzatnia();
        cena = obiekt.getCena();
        kierownik_kontakt = obiekt.getWlasciciel_kontakt();

        this.setTitle("Szczegóły obiektu: "+nazwa);

        zdjecie.setImageResource(imageResource);
        nazwaTV.setText(nazwa);
        dyscyplinaTV.setText(dyscyplina);
        adresTV.setText(miejscowosc+", "+ulica+" "+numer_lokalu);
        emailTV.setText(email);
        telefonTV.setText(telefon);
        opisTV.setText(opis);
        if(kryty==1){
            krytyTV.setTextColor(Color.GREEN);
            krytyTV.setText("Tak");
        }
        else{
            krytyTV.setTextColor(Color.RED);
            krytyTV.setText("Nie");
        }
        if(szatnia==1){
            szatniaTV.setTextColor(Color.GREEN);
            szatniaTV.setText("Tak");
        }
        else{
            szatniaTV.setTextColor(Color.RED);
            szatniaTV.setText("Nie");
        }
        if(cena.equals("0.0zł/h")){
            cenaTV.setText("bezpłatny");
        }
        else{
            cenaTV.setText(cena);
        }

        //obsługa mapy
        MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        kierownikTV.setText(kierownik_kontakt);
    }

    @OnClick(R.id.stworzWydarzenie)
    public void onStworzWydarzenieButtonClicked(){
        Toast.makeText(this, "Tworzenie wydarzenia na tym obiekcie", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("mapa","Mapa jest gotowa");
        LatLng wspolrzedne = new LatLng(51.235114, 22.548770);
        mMap.addMarker(new MarkerOptions().position(wspolrzedne).title("Politechnika Lubelska"));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(wspolrzedne));
    }
}