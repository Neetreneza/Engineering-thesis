package com.example.logowaniep1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button logowanie,wydarzenia,profil,obiekty,profilW, rejestracja, dodajObiekt;
    boolean zalogowany=false;
    TextView tresc;

    String[] dane;
    String emailBaza, hasloBaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      logowanie = (Button) findViewById(R.id.przejdzDoLogwaniaB);
      wydarzenia = (Button) findViewById(R.id.przejdzDoWydarzenB);
      obiekty = (Button) findViewById(R.id.przejdzDoObiektow);
      profil = (Button) findViewById(R.id.przejdzDoProfiluB);
      profilW = (Button) findViewById(R.id.przejdzDoProfiluWlascB);
      rejestracja = (Button) findViewById(R.id.przejdzDoRejestracjiB);
      tresc = (TextView) findViewById(R.id.trescZalogwanych);
      dodajObiekt = (Button) findViewById(R.id.przejdzDoDodawanieObiektu);

      dodajObiekt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              otworzDodawanieObiektu();
          }
      });


      profilW.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              otworzProfilWlasciciela();
          }
      });

      logowanie.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              otworzLogowanie();
          }
      });

     rejestracja.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             otworzRejestracje();
         }
     });

      wydarzenia.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              otworzWydarzenia();
          }
      });

      profil.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              otworzProfil();
          }
      });

      obiekty.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              otworzObiekty();
          }
      });
    }

    private void otworzDodawanieObiektu()
    {
        Intent intent = new Intent(this, DodajObiekt.class);
        startActivity(intent);
    }

    private void otworzProfilWlasciciela() {
        Intent intent = new Intent(this,ProfilWlasciciel.class);
        startActivity(intent);
    }

    public void otworzObiekty()
    {
        Intent intent = new Intent(this,Obiekty.class);
        startActivity(intent);
    }


    public void otworzProfil() {
        if(!zalogowany) otworzLogowanie();
        else
        {
            Intent intent = new Intent(this,Profil.class);
            startActivity(intent);
        }
    }

    public  void otworzWydarzenia() {
        //if(!zalogowany) otworzLogowanie();
        //else
        {
            Intent intent = new Intent(this,Wydarzenia.class);
            startActivity(intent);
        }

    }

    public void otworzLogowanie()
    {
        String czyZalogowany; // po tym stringu sprawdzamy czy zalogowany;
        czyZalogowany = logowanie.getText().toString();

        if(czyZalogowany.equals("Logowanie")) {

            Intent intent = new Intent(this, Logowanie.class);
            //intent.setAction("Logowanie");
            startActivityForResult(intent, 1);
        }
        else
        {
            tresc.setVisibility(View.INVISIBLE);
            logowanie.setText("Logowanie");
        }
    }

    public void otworzRejestracje()
    {
        String czyZalogowany; // po tym stringu sprawdzamy czy zalogowany;
        czyZalogowany = logowanie.getText().toString();

        if(czyZalogowany.equals("Logowanie")) {

            Intent intent = new Intent(this, Rejestracja.class);
            //intent.setAction("Logowanie");
            startActivityForResult(intent, 2);
        }
        else
        {
            tresc.setVisibility(View.INVISIBLE);
            logowanie.setText("Logowanie");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                boolean result = data.getBooleanExtra("result",false);
                Log.i("Baza","Wynik to "+result);

                zalogowany = result;

                if(zalogowany==true)
                {
                    tresc.setVisibility(View.VISIBLE);
                    logowanie.setText("Wyloguj");
                }
                else
                {
                    tresc.setVisibility(View.INVISIBLE);
                }
            }
        }
        if(requestCode == 2)
        {

        }
    }
}
