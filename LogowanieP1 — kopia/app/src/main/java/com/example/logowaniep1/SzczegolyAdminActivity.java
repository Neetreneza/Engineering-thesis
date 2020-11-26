package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SzczegolyAdminActivity extends AppCompatActivity {

    @BindView(R.id.identyfikatorTextView2)
    TextView identyfikatorTV2;
    @BindView(R.id.zdjecie)
    ImageView zdjecie;
    @BindView(R.id.nazwaEditText)
    EditText nazwaET;
    @BindView(R.id.miejscowoscEditText)
    EditText miejscowoscET;
    @BindView(R.id.ulicaEditText)
    EditText ulicaET;
    @BindView(R.id.lokalEditText)
    EditText lokalET;
    @BindView(R.id.wojewodztwoEditText)
    EditText wojewodztwoET;
    @BindView(R.id.kodPocztowyEditText)
    EditText pocztaET;
    @BindView(R.id.emailTextView2)
    TextView emailTV;
    @BindView(R.id.telefonTextView2)
    TextView telefonTV;
    @BindView(R.id.rachunekTextView2)
    TextView rachunekTV;
    @BindView(R.id.opisEditText)
    TextView opisET;

    private int imageResource, obiekt_id;
    private String nazwa, miejscowosc, ulica, numer_lokalu, wojewodztwo, kod_pocztowy, email,  telefon, numer_rachunku, opis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_admin);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        ObiektDoWeryfikacji obiektDoWeryfikacji = intent.getParcelableExtra("obiekt");

        imageResource = obiektDoWeryfikacji.getImageResource();
        obiekt_id = obiektDoWeryfikacji.getObiekt_id();
        nazwa = obiektDoWeryfikacji.getNazwa();
        miejscowosc = obiektDoWeryfikacji.getMiejscowosc();
        ulica = obiektDoWeryfikacji.getUlica();
        numer_lokalu = obiektDoWeryfikacji.getNumer_lokalu();
        wojewodztwo = obiektDoWeryfikacji.getWojewodztwo();
        kod_pocztowy = obiektDoWeryfikacji.getKod_pocztowy();
        email = obiektDoWeryfikacji.getEmail();
        telefon = obiektDoWeryfikacji.getTelefon();
        numer_rachunku = obiektDoWeryfikacji.getNumer_rachunku();
        opis = obiektDoWeryfikacji.getOpis();

        zdjecie.setImageResource(imageResource);
        identyfikatorTV2.setText(String.valueOf(obiekt_id));
        nazwaET.setText(nazwa);
        miejscowoscET.setText(miejscowosc);
        ulicaET.setText(ulica);
        lokalET.setText(numer_lokalu);
        wojewodztwoET.setText(wojewodztwo);
        pocztaET.setText(kod_pocztowy);
        emailTV.setText(email);
        telefonTV.setText(telefon);
        rachunekTV.setText(numer_rachunku);
        opisET.setText(opis);
    }
}