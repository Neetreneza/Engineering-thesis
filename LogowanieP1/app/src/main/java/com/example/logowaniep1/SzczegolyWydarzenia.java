package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SzczegolyWydarzenia extends AppCompatActivity {
    EditText headUser,cena,data,godzina,miejsce,nazwa,obiekt,opis;
    TextView oplacony;
    ImageView image;
    Button dolacz, zaplac;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    int identyfikatorWydarzenia, status;
    int headUserInt;
    ArrayList<UzytkownicyItem> uzytkownicyLista;
    Boolean czyJestem = false;
    String headUserS;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_wydarzenia);
        uzytkownicyLista = new ArrayList<>();


        nazwa = (EditText) findViewById(R.id.nazwaWydSzczeET);
        headUser = (EditText) findViewById(R.id.headUserWydSzczeET);
        cena = (EditText) findViewById(R.id.cenaWydSzczeET);
        data = (EditText) findViewById(R.id.dataWydSzczeET);
        opis = (EditText) findViewById(R.id.opisWydSzczeET);
        godzina = (EditText) findViewById(R.id.godzinaWydSzczeET);
        miejsce = (EditText) findViewById(R.id.miejsceWydSzczeET);
        obiekt = (EditText) findViewById(R.id.obiektWydSzczeET);
        image = (ImageView) findViewById(R.id.imageView3);
        dolacz = (Button) findViewById(R.id.dolaczSzczeWydB);
        zaplac = (Button) findViewById(R.id.zaplacSzczeWydB);
        oplacony = (TextView) findViewById(R.id.oplacony);
        //zaplac.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        nazwa.setText(intent.getStringExtra("nazwa"));
        headUser.setText(intent.getStringExtra("imie")+" "+intent.getStringExtra("nazwisko"));
        //headUserInt = Integer.parseInt(headUser.getText().toString());
        cena.setText(intent.getStringExtra("cena"));
        data.setText(intent.getStringExtra("start"));
        godzina.setText(intent.getStringExtra("koniec"));
        image.setImageResource(intent.getIntExtra("img",0));
        miejsce.setText(intent.getStringExtra("miejsce"));
        obiekt.setText(intent.getStringExtra("obiektId"));
        identyfikatorWydarzenia = intent.getIntExtra("wydarzenieId",0);
        opis.setText(intent.getStringExtra("opis"));
        headUserS = intent.getStringExtra("headUser");
        status = intent.getIntExtra("status",0);

        DrawableCompat.setTint(image.getDrawable(), Color.BLACK);

        dolacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dolaczanie();
            }
        });

        zaplac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SzczegolyWydarzenia.this, Zaplac.class);
                startActivity(intent);
            }
        });

        if(Integer.parseInt(headUserS) == Singleton.getInstance().getUzytkownikID()) {
            if (status == 1) {
                oplacony.setText("Wydarzenie opłacone");
                zaplac.setEnabled(false);
            } else {
                oplacony.setText("Wydarzenie nie jest opłacone");
                oplacony.setTextColor(Color.RED);
                zaplac.setEnabled(true);
            }
        }
        else
        {
            if (status == 1) {
                oplacony.setText("Wydarzenie opłacone");
                zaplac.setVisibility(View.GONE);
            } else {
                oplacony.setText("Wydarzenie nie jest opłacone");
                zaplac.setVisibility(View.GONE);
            }
        }

        ListaUzytkownikowBaza listaUzytkownikowBaza = new ListaUzytkownikowBaza();
        listaUzytkownikowBaza.execute();




    }

    private void dolaczanie() {
        Dolaczanie dolaczanie = new Dolaczanie();
        dolaczanie.execute();

    }

    private class ListaUzytkownikowBaza extends AsyncTask<String,String,String>
    {
        String nazwa,p;
        int organizatorId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();
            uzytkownicyLista = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                czyJestem = false;
                //uzytkownicyLista.clear();
                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();
                Log.i("Bla bla","ble ble");
                resultSet = statement.executeQuery("select wydarzenie_uczestnicy.uzytkownik_id, uzytkownik.uzytkownik_id, uzytkownik.imie, uzytkownik.nazwisko from wydarzenie_uczestnicy, uzytkownik where wydarzenie_id='"+identyfikatorWydarzenia+"' and wydarzenie_uczestnicy.uzytkownik_id = uzytkownik.uzytkownik_id");

                while(resultSet.next())
                {
                    Log.i("Bla bla","DODAJE UZYTKOWNIKA");
                    nazwa = resultSet.getString(3);
                    //nazwa += resultSet.getString(4);
                    uzytkownicyLista.add(new UzytkownicyItem(R.drawable.domyslne, nazwa));
                    if(resultSet.getInt(1) == (Singleton.getInstance().getUzytkownikID())){
                        czyJestem = true;
                    }
                }
//                Log.i("Bla bla","bla bla");
//                resultSet = statement.executeQuery("select wydarzenie.organizator_id, uzytkownik.imie from wydarzenie, uzytkownik where  wydarzenie.organizator_id = uzytkownik.uzytkownik_id and wydarzenie_id ='"+ identyfikatorWydarzenia+"'");
//                while(resultSet.next()) {
//                    uzytkownicyLista.add(new UzytkownicyItem(R.drawable.gora, resultSet.getString(2)));
//                    if(resultSet.getInt(1) == (Singleton.getInstance().getUzytkownikID())){
//                        czyJestem = true;
//                    }
//                }
                connection.close();
                Log.i("Jestem", "OBECNY");

            }
            catch (Exception e){
                System.err.println("Błąd1: ");
                System.err.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            buildRecyclerView();
            progressDialog.hide();
            Log.i("Warunku",czyJestem +" ||| "+headUserInt+ "  |||  "+ Singleton.getInstance().getUzytkownikID());
            if(headUserInt == (Singleton.getInstance().getUzytkownikID()))

                dolacz.setText("Usuń wydarzenie");
            else
            {
                if(czyJestem)
            dolacz.setText("Opuść");
            else
                dolacz.setText("Dołącz");
            }

        }
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewUzytkownicy);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new UzytkownicyAdapter(uzytkownicyLista);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    private class Dolaczanie extends AsyncTask<String,String,String>
    {

        String nazwaDD;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                PreparedStatement preparedStmt;
                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();
                if(dolacz.getText().equals("Dołącz")) {
                    //resultSet = stmt.executeQuery("insert into wydarzenie_uczestnicy values (?,?)'"+identyfikatorWydarzenia+"'");
                    String query = "insert into wydarzenie_uczestnicy values (?,?)";
                    preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, identyfikatorWydarzenia);
                    preparedStmt.setInt(2, Singleton.getInstance().getUzytkownikID());
                    preparedStmt.execute();
                }
                else
                {
                    if(dolacz.getText().equals("Usuń wydarzenie"))
                    {
                        String query = "delete from wydarzenie where wydarzenie_id ='"+identyfikatorWydarzenia+"'";
                        preparedStmt = connection.prepareStatement(query);
                    }
                    else {
                        String query = "delete from wydarzenie_uczestnicy where uzytkownik_id ='" + Singleton.getInstance().getUzytkownikID() + "'";
                        preparedStmt = connection.prepareStatement(query);
                    }
                }
                preparedStmt.execute();
                connection.close();
            }
            catch (Exception e){
                System.err.println("Błąd2: ");
                System.err.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
            ListaUzytkownikowBaza listaUzytkownikowBaza = new ListaUzytkownikowBaza();
            listaUzytkownikowBaza.execute();
            if(dolacz.getText()=="Usuń wydarzenie") {
                Toast.makeText(getApplicationContext(),"Usunięto wydarzenie!",Toast.LENGTH_LONG).show();
                finish();
            }
            if(!czyJestem)
            Toast.makeText(getApplicationContext(),"Udało się zapisać na listę!",Toast.LENGTH_LONG).show();
            else
            {
                Toast.makeText(getApplicationContext(),"Udało się opuścić wydarzenie!",Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }




}
