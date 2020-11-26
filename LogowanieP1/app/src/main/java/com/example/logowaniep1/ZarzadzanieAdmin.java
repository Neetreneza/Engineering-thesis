package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ZarzadzanieAdmin extends AppCompatActivity {

    private ArrayList<ObiektDoWeryfikacji> obiektyList;
    private RecyclerView mRecyclerView;
    private ZarzadzanieAdminAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zarzadzanie_admin);

        obiektyList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        createObiektList cOL = new createObiektList();
        cOL.execute();
    }

    public class createObiektList extends AsyncTask<String,String,String>{

        int obiekt_id, kryty, szatnia, oplata, aktywny;
        String miejscowosc, nazwa, ulica, numer_lokalu, wojewodztwo, kod_pocztowy, email,  telefon, numer_rachunku, opis;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from obiekt where obiekt.aktywny=0 order by obiekt.obiekt_id asc;");

                while(resultSet.next()){
                    obiekt_id = resultSet.getInt(1);
                    nazwa = resultSet.getString(2);
                    miejscowosc = resultSet.getString(3);
                    ulica = resultSet.getString(4);
                    numer_lokalu = resultSet.getString(5);
                    wojewodztwo = resultSet.getString(6);
                    kod_pocztowy = resultSet.getString(7);
                    email = resultSet.getString(8);
                    telefon = resultSet.getString(9);
                    kryty = resultSet.getInt(10);
                    szatnia = resultSet.getInt(11);
                    oplata = resultSet.getInt(12);
                    numer_rachunku = resultSet.getString(13);
                    opis = resultSet.getString(14);
                    aktywny = resultSet.getInt(15);
                    java.sql.Timestamp dbSqlTimestamp = resultSet.getTimestamp(16);

                    obiektyList.add(new ObiektDoWeryfikacji(R.drawable.gora, obiekt_id, nazwa, miejscowosc, ulica, numer_lokalu, wojewodztwo, kod_pocztowy, email, telefon, kryty, szatnia, oplata, numer_rachunku, opis, aktywny, dbSqlTimestamp));

                    Log.i("while","dodaje obiekt: " + nazwa + " " + ulica + " " + miejscowosc);
                }
                connection.close();
            }
            catch (SQLException e){
                System.err.println("Błąd SQL: " + e.getMessage());
            }
//            catch (ClassNotFoundException e) {
//                System.err.println("Nie wczytano sterownika JDBC");
//            }
            return "1";
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.hide();
            buildRecyclerView();
        }
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ZarzadzanieAdminAdapter(obiektyList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //obsługa wciśnięć
        mAdapter.setOnItemClickListener(new ZarzadzanieAdminAdapter.onItemClickListener(){
            //wciśnięcie przycisku Szczegóły
            @Override
            public void onSzczegolyClick(int position) {
                int id = obiektyList.get(position).getObiekt_id();
                Intent intent = new Intent(ZarzadzanieAdmin.this, SzczegolyAdminActivity.class);
                //intent.putExtra("obiekt",obiektyList.get(position).getObiekt_id());
                intent.putExtra("obiekt",obiektyList.get(position));
                startActivity(intent);
                Log.i("buttonSzczegoly","Otworzono szczegóły obiektu " + position + " z listy o identyfikatorze: " + id);
            }
            //wciśnięcie przycisku Akceptuj
            @Override
            public void onAkceptujClick(int position) {
                int x = obiektyList.get(position).getObiekt_id();
                try {
                    akceptujObiekt aO = new akceptujObiekt(x);
                    aO.execute();
                }
                catch (Exception e){
                    System.out.println("ERROR WTF: " + e.getMessage());
                }
            }
        });
    }

    public class akceptujObiekt extends AsyncTask<String,String,String> {
        int a;
        public akceptujObiekt(int a) {
            a=a;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            int id = obiektyList.get(a).getObiekt_id();
            try{
                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();
                statement.executeUpdate("UPDATE obiekt SET aktywny=1 WHERE obiekt_id=" + id + ";");
                connection.close();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
            catch (SQLException e){
                System.out.println("Wyjątek SQL: " + e);
            }
            Log.i("button","Zaakceptowano obiekt " + a + " z listy o identyfikatorze: " + id);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            progressDialog.hide();
        }
    }
}