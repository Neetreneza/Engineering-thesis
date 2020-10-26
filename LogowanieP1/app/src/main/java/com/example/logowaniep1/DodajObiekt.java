package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DodajObiekt extends AppCompatActivity {

    EditText nazwa, miejscowosc, ulica, numerLokalu, telefon, email , wojewodztwo, kodPocztowy, numerRachunku, opis;
    int kryty, oplata, szatnia;
    Button krytyT, krytyN, oplataT, oplataN, szatniaT, szatniaN, dodajObiekt;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dodaj_obiekt);



        progressDialog = new ProgressDialog(this);

        nazwa = (EditText) findViewById(R.id.nazwaDO);
        miejscowosc = (EditText) findViewById(R.id.miejscowoscDO);
        ulica = (EditText) findViewById(R.id.ulicaDO);
        numerLokalu = (EditText) findViewById(R.id.numerLokaluDO);
        telefon = (EditText) findViewById(R.id.telefonDO);
        email = (EditText) findViewById(R.id.emailDO);
        wojewodztwo = (EditText) findViewById(R.id.wojewodztwoDO);
        kodPocztowy = (EditText) findViewById(R.id.kodPocztowyDO);
        numerRachunku = (EditText) findViewById(R.id.numerRachunkuDO);
        opis = (EditText) findViewById(R.id.opisDO);

        krytyT = (Button) findViewById(R.id.krytyTDO);
        krytyN = (Button) findViewById(R.id.krytyNDO);
        szatniaN = (Button) findViewById(R.id.szatnieNDO);
        szatniaT = (Button) findViewById(R.id.szatnieTDO);
        oplataN = (Button) findViewById(R.id.platnyNDO);
        oplataT = (Button) findViewById(R.id.platnyTDO);
        dodajObiekt = (Button) findViewById(R.id.dodajObiektDO);

        kryty = 0;
        krytyN.setBackgroundColor(Color.RED);
        krytyT.setBackgroundColor(Color.GRAY);

        oplata = 0;
        oplataN.setBackgroundColor(Color.RED);
        oplataT.setBackgroundColor(Color.GRAY);

        szatnia = 0;
        szatniaN.setBackgroundColor(Color.RED);
        szatniaT.setBackgroundColor(Color.GRAY);

        dodajObiekt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dodawanie dodawanie = new Dodawanie();
                dodawanie.execute();
            }
        });

        krytyN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kryty = 0;
                krytyN.setBackgroundColor(Color.RED);
                krytyT.setBackgroundColor(Color.GRAY);
            }
        });

        krytyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kryty = 1;
                krytyN.setBackgroundColor(Color.GRAY);
                krytyT.setBackgroundColor(Color.GREEN);

            }
        });
        oplataN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oplata = 0;
                oplataN.setBackgroundColor(Color.RED);
                oplataT.setBackgroundColor(Color.GRAY);
            }
        });

        oplataT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oplata = 1;
                oplataN.setBackgroundColor(Color.GRAY);
                oplataT.setBackgroundColor(Color.GREEN);
            }
        });
        szatniaN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                szatnia = 0;
                szatniaN.setBackgroundColor(Color.RED);
                szatniaT.setBackgroundColor(Color.GRAY);
            }
        });

        szatniaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                szatnia = 1;
                szatniaN.setBackgroundColor(Color.GRAY);
                szatniaT.setBackgroundColor(Color.GREEN);
            }
        });


    }

    private class Dodawanie extends AsyncTask<String,String,String>
    {
        String nazwaS = nazwa.getText().toString();
        String miejscowoscS = miejscowosc.getText().toString();
        String ulicaS = ulica.getText().toString();
        String numerLokaluS = numerLokalu.getText().toString();
        String wojewodztwoS = wojewodztwo.getText().toString();
        String kodPocztowyS = kodPocztowy.getText().toString();
        String emailS = email.getText().toString();
        String telefonS = telefon.getText().toString();
        String opisS = opis.getText().toString();
        String numerRachunkuS = numerRachunku.getText().toString();

        String blad = "";

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                // WALIDACJA DANYCH

                if((!nazwaS.trim().equals("") && nazwaS.matches(("[a-zA-Z]+\\.?"))) || ((!miejscowoscS.trim().equals("")) && miejscowoscS.matches(("[a-zA-Z]+\\.?"))) || (!ulicaS.equals("")) || (!wojewodztwoS.equals("") && wojewodztwoS.matches("[a-zA-Z]+\\.?")));
                else
                {
                    blad += "Proszę uzupełnić wszystkie pola\n";
                }

                String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                if(emailS.matches(emailPattern));
                else
                    blad+="Proszę podać poprawny e-mail\n";

                String numerTelefonuPattern =  "^\\+(?:[0-9] ?){6,14}[0-9]$";;
                if(telefonS.matches(numerTelefonuPattern));
                else
                    blad += "Numer telefonu należy podać w formacie +CC CCCCCCCCC\n";

                String numerLokaluPattern = "\\d+";
                if(!numerLokaluS.trim().equals(""))
                {
                    if (numerLokaluS.matches(numerLokaluPattern)) ;
                    else {
                        blad += "Numer lokalu musi składać się z samych cyfr\n";
                    }
                }

                String kodPocztowyPattern =  "[0-9]{2}\\-[0-9]{3}";
                if(kodPocztowyS.matches(kodPocztowyPattern));
                else
                {
                    blad += "Kod pocztowy należy podać w formacie CC-CCC\n";
                }


                    if (numerRachunkuS.matches(numerLokaluPattern)) ;
                    else {
                        blad += "Numer rachunku musi składać się z samych cyfr\n";
                    }

                    if(opisS.trim().equals(""))
                        opisS = nazwaS;



                // LACZENIE Z BAZA

                if(blad.equals(""))
                {

                Class.forName("com.mysql.jdbc.Driver");

                Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.102:3306/aplikacja", "andro", "andro");

                Statement statement = connection.createStatement();

                String query = "insert into obiekt (nazwa,miejscowosc,ulica,numer_lokalu,wojewodztwo,kod_pocztowy,email,telefon,kryty,szatnia,oplata,numer_rachunku,opis,aktywny) values (?,?,?,?,?,?,?,?,?,?,?,?,?,0)";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, nazwaS);
                preparedStmt.setString(2, miejscowoscS);
                preparedStmt.setString(3, ulicaS);
                preparedStmt.setString(4, numerLokaluS);
                preparedStmt.setString(5, wojewodztwoS);
                preparedStmt.setString(6, kodPocztowyS);
                preparedStmt.setString(7, emailS);
                preparedStmt.setString(8, telefonS);
                preparedStmt.setInt(9, kryty);
                preparedStmt.setInt(10, szatnia);
                preparedStmt.setInt(11, oplata);
                preparedStmt.setString(12, numerRachunkuS);
                preparedStmt.setString(13, opisS);




                preparedStmt.execute();

                connection.close();

                }

            }
            catch (Exception e){
                e.printStackTrace();
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            progressDialog.hide();
            if(!blad.trim().equals(""))
            Toast.makeText(getBaseContext(),blad, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getBaseContext(),"Zgłoszenie dodania obiektu wysłane!", Toast.LENGTH_LONG).show();



            }
        }
    }

