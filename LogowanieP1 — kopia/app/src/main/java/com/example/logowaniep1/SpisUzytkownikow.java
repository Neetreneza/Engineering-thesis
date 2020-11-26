
package com.example.logowaniep1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;

public class SpisUzytkownikow extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SpisUzytkownikowAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    ProgressDialog progressDialog;
    int year;



    ArrayList<SpisUzytkownikowItem> spisUzytkownikow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spis_uzytkownikow);

        progressDialog = new ProgressDialog(this);

        spisUzytkownikow = new ArrayList<>();

        recyclerView = findViewById(R.id.SpisUzytkownikowRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new SpisUzytkownikowAdapter(spisUzytkownikow);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewUzytkownicy);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);


        adapter.setOnItemClickListener(new SpisUzytkownikowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(SpisUzytkownikow.this, Profil.class);
                intent.putExtra("id", spisUzytkownikow.get(position).getId());
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SpisUzytkownikowC spisUzyktownikowC = new SpisUzytkownikowC();
        spisUzyktownikowC.execute();

        year = Calendar.getInstance().get(Calendar.YEAR);

    }

    private class  SpisUzytkownikowC extends AsyncTask<String,String,String> {
        String imieU, nazwiskoU, dataUrodzeniaU, emailU;
        int idU;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {


            try {

                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select uzytkownik_id, imie, nazwisko, data_urodzenia, email from uzytkownik");

                while (resultSet.next()) {
                    idU = resultSet.getInt(1);
                    imieU = resultSet.getString(2);
                    nazwiskoU = resultSet.getString(3);
                    dataUrodzeniaU = resultSet.getString(4);
                    emailU = resultSet.getString(5);
                    spisUzytkownikow.add(new SpisUzytkownikowItem(R.drawable.domyslne, imieU + " " + nazwiskoU + ", "+(year - Integer.parseInt(dataUrodzeniaU.substring(0,4))), emailU, idU));
                }


            } catch (Exception e) {
                System.err.println("Błąd: ");
                System.err.println(e.getMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.hide();
            buildAdapter();
        }


    }

    private void buildAdapter() {
        recyclerView = findViewById(R.id.SpisUzytkownikowRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new SpisUzytkownikowAdapter(spisUzytkownikow);

        adapter.setOnItemClickListener(new SpisUzytkownikowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(SpisUzytkownikow.this, Profil.class);
                intent.putExtra("id", spisUzytkownikow.get(position).getId());
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.uzytkownicy_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_searchUzytkownicy);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = null;
            switch (item.getItemId())
            {
                case R.id.nav_glowna:
                    intent = new Intent(getBaseContext(), MainActivity.class);
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
                    //intent = new Intent(getBaseContext(), SpisUzytkownikow.class);
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
}