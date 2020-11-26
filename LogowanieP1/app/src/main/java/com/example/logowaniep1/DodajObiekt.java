package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

interface OnWojewodztwaListener {
    void onWojewodztwaCompleted(ArrayList<String> wojewodztwa);
    void onWojewodztwaError(String error);
}

interface OnDyscyplinyListener {
    void onDyscyplinyCompleted(ArrayList<String> dyscypliny);
    void onDyscyplinyError(String error);
}

public class DodajObiekt extends AppCompatActivity implements OnWojewodztwaListener, OnDyscyplinyListener{

    private TextInputLayout nazwa, miejscowosc, ulica, numerLokalu, telefon, email , wojewodztwo, kodPocztowy, numerRachunku, opis;
    private AutoCompleteTextView listaWojewodztw, listaDyscyplin;
    int kryty, oplata, szatnia;
    Button krytyT, krytyN, oplataT, oplataN, szatniaT, szatniaN, dodajObiekt;
    ProgressDialog progressDialog;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dodaj_obiekt);

        this.setTitle("Formularz dodania obiektu");

        progressDialog = new ProgressDialog(this);

        nazwa = (TextInputLayout) findViewById(R.id.nazwaDO);
        miejscowosc = (TextInputLayout) findViewById(R.id.miejscowoscDO);
        ulica = (TextInputLayout) findViewById(R.id.ulicaDO);
        numerLokalu = (TextInputLayout) findViewById(R.id.numerLokaluDO);
        telefon = (TextInputLayout) findViewById(R.id.telefonDO);
        email = (TextInputLayout) findViewById(R.id.emailDO);
        wojewodztwo = (TextInputLayout) findViewById(R.id.wojewodztwoDO);
        kodPocztowy = (TextInputLayout) findViewById(R.id.kodPocztowyDO);
        numerRachunku = (TextInputLayout) findViewById(R.id.numerRachunkuDO);
        opis = (TextInputLayout) findViewById(R.id.opisDO);
        listaWojewodztw = (AutoCompleteTextView) findViewById(R.id.listaWojewodztw);
        listaDyscyplin = (AutoCompleteTextView) findViewById(R.id.listaWDyscyplin);

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

        new pobranieDanychDoWojewodztw(this).execute();
        new pobranieDanychDoDyscyplin(this).execute();

        //listaWojewodztw.setAdapter(wojewodztwaAdapter);
        //listaDyscyplin.setAdapter(dyscyplinyAdapter);

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
                numerRachunku.setEnabled(false);
            }
        });

        oplataT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oplata = 1;
                oplataN.setBackgroundColor(Color.GRAY);
                oplataT.setBackgroundColor(Color.GREEN);
                numerRachunku.setEnabled(true);
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

    public void onDyscyplinyCompleted(ArrayList<String> dyscypliny) {
        ArrayAdapter<String> dyscyplinyAdapter = new ArrayAdapter<>(
                DodajObiekt.this,
                R.layout.dropdown_item,
                dyscypliny
        );
        listaDyscyplin.setAdapter(dyscyplinyAdapter);
        Log.i("Rozmiar: ", "" + dyscypliny.size());
    }

    @Override
    public void onDyscyplinyError(String error) {

    }

    public void onWojewodztwaCompleted(ArrayList<String> wojewodztwa) {
        ArrayAdapter<String> wojewodztwaAdapter = new ArrayAdapter<>(
                DodajObiekt.this,
                R.layout.dropdown_item,
                wojewodztwa
        );
        listaWojewodztw.setAdapter(wojewodztwaAdapter);
    }

    public void onWojewodztwaError(String error) {
        Log.e("Blad: ", error);
    }

    private class pobranieDanychDoWojewodztw extends AsyncTask<String,String,ArrayList<String>>
    {
        private final OnWojewodztwaListener mListener;
        ArrayList<String> wojewodztwaAL = new ArrayList<String>();

        public pobranieDanychDoWojewodztw(OnWojewodztwaListener listener) {
            mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try
            {
                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from wojewodztwo ORDER BY wojewodztwo_id asc;");
                while(resultSet.next()){
                    wojewodztwaAL.add(resultSet.getString(2));
                }
                connection.close();
                Log.i("why","znaleziono wojewodztw: "+wojewodztwaAL.size());
            }
            catch (SQLException e){
                System.err.println("Błąd SQL: " + e.getMessage());
            }

            return wojewodztwaAL;
        }

        protected void onPostExecute(ArrayList<String> wojewodztwaAL) {
            if (mListener != null) {
                mListener.onWojewodztwaCompleted(wojewodztwaAL);
            }
        }
    }

   private class pobranieDanychDoDyscyplin extends AsyncTask<String,String,ArrayList<String>>
    {
        private final OnDyscyplinyListener mListener;
        ArrayList<String> dyscyplinyAL = new ArrayList<String>();

        public pobranieDanychDoDyscyplin(OnDyscyplinyListener listener) {
            mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try
            {
                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from dyscyplina ORDER BY dyscyplina_id asc;");
                while(resultSet.next()){
                    dyscyplinyAL.add(resultSet.getString(2));
                }
                connection.close();
                Log.i("why","znaleziono dyscyplin: "+dyscyplinyAL.size());
            }
            catch (SQLException e){
                System.err.println("Błąd SQL: " + e.getMessage());
            }

            return dyscyplinyAL;
        }

        protected void onPostExecute(ArrayList<String> dyscyplinyAL) {
            if (mListener != null) {
                mListener.onDyscyplinyCompleted(dyscyplinyAL);
            }
        }
    }

    private class Dodawanie extends AsyncTask<String,String,String>
    {
        String nazwaS = nazwa.getEditText().getText().toString();
        String miejscowoscS = miejscowosc.getEditText().getText().toString();
        String ulicaS = ulica.getEditText().getText().toString();
        String numerLokaluS = numerLokalu.getEditText().getText().toString();
        String wojewodztwoS = wojewodztwo.getEditText().getText().toString();
        String kodPocztowyS = kodPocztowy.getEditText().getText().toString();
        String emailS = email.getEditText().getText().toString();
        String telefonS = telefon.getEditText().getText().toString();
        String opisS = opis.getEditText().getText().toString();
        String numerRachunkuS = numerRachunku.getEditText().getText().toString();

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

                if((!nazwaS.trim().equals("") && nazwaS.matches(("[a-zA-Z]+\\.?"))) || ((!miejscowoscS.trim().equals("")) && miejscowoscS.matches(("[a-zA-Z]+\\.?"))) || (!ulicaS.equals("")) || (!wojewodztwoS.equals("")));
                else
                {
                    if(nazwaS.trim().equals("")){
                        nazwa.setHelperText("Nazwa nie może być pusta!");
                        nazwa.setHelperTextEnabled(true);
                    }
                    if(miejscowoscS.trim().equals("")){
                        miejscowosc.setHelperText("Miejscowość nie może być pusta!");
                        miejscowosc.setHelperTextEnabled(true);
                    }
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

                    connection = ConnectionManager.getConnection();
                    statement = connection.createStatement();

                String query = "insert into obiekt (nazwa,miejscowosc,ulica,numer_lokalu,wojewodztwo_id,kod_pocztowy,email,telefon,kryty,szatnia,oplata,numer_rachunku,opis,aktywny,wlasciciel_id) values (?,?,?,?,5,?,?,?,?,?,?,?,?,0,1)";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, nazwaS);
                preparedStmt.setString(2, miejscowoscS);
                preparedStmt.setString(3, ulicaS);
                preparedStmt.setString(4, numerLokaluS);
                //preparedStmt.setString(5, wojewodztwoS);
                preparedStmt.setString(5, kodPocztowyS);
                preparedStmt.setString(6, emailS);
                preparedStmt.setString(7, telefonS);
                preparedStmt.setInt(8, kryty);
                preparedStmt.setInt(9, szatnia);
                preparedStmt.setInt(10, oplata);
                preparedStmt.setString(11, numerRachunkuS);
                preparedStmt.setString(12, opisS);




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
            if (!blad.trim().equals(""))
                Toast.makeText(getBaseContext(), blad, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getBaseContext(), "Zgłoszenie dodania obiektu wysłane!", Toast.LENGTH_LONG).show();

            }
        }
    }

