package com.example.logowaniep1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Button logowanie,wydarzenia,profil,obiekty,profilW, rejestracja, dodajObiekt, zarzadzanie, spisUzytkownikow;
    boolean zalogowany=false;
    TextView tresc,test;

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
      test = (TextView) findViewById(R.id.textView);
      zarzadzanie = (Button) findViewById(R.id.buttonAdmin);
      spisUzytkownikow = (Button) findViewById(R.id.spisUzytkownikowMainB);
      BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewMain);
      bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
      Menu menu = bottomNavigationView.getMenu();
      MenuItem menuItem = menu.getItem(2);
      menuItem.setChecked(true);


      spisUzytkownikow.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              otworzListeUzytkownikow();
          }
      });

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
        zarzadzanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otworzPanelAdmina();
            }
        });
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = null;
            switch (item.getItemId())
            {
                case R.id.nav_glowna:
                    //intent = new Intent(this, MainActivity.class);
                    break;
                case R.id.nav_obiekty:
                    intent = new Intent(getBaseContext(), Obiekty.class);
                    break;
                case R.id.nav_wydarzenia:
                    intent = new Intent(getBaseContext(), Wydarzenia.class);
                    break;
                case R.id.nav_profil:
                    intent = new Intent(getBaseContext(), Profil.class);
                    break;
                case R.id.nav_uzytkownicy:
                    intent = new Intent(getBaseContext(), SpisUzytkownikow.class);
                    break;
            }
            if(intent != null) {
                startActivity(intent);
                return true;
            }
            else
                return false;
        }
    };

    public void otworzListeUzytkownikow() {
        Intent intent = new Intent(this, SpisUzytkownikow.class);
        startActivity(intent);
    }


    public void otworzDodawanieObiektu()
    {
        Intent intent = new Intent(this, DodajObiekt.class);
        startActivity(intent);
    }

    public void otworzProfilWlasciciela() {
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
            Singleton.getInstance().setUzytkownikID(0);
            Singleton.getInstance().setUzytkownikEmail(null);
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
    public void otworzPanelAdmina(){
        Intent intent = new Intent(this, ZarzadzanieAdmin.class);
        startActivity(intent);
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
                    zarzadzanie.setVisibility(View.VISIBLE);
                    test.setText(Singleton.getInstance().getuzytkownikEmail());
                }
                else
                {
                    tresc.setVisibility(View.INVISIBLE);
                    zarzadzanie.setVisibility(View.INVISIBLE);
                }
            }
        }
        if(requestCode == 2)
        {

        }
    }
}


