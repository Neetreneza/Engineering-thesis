package com.example.logowaniep1;

//TODO OnPluskClick zwraca wartość id obiektu, ktory mozna dodac do formularza dodawania wydarzenia

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Obiekty extends AppCompatActivity {

    ArrayList<ObiektyItem> obiektyList;
    private RecyclerView recyclerView2;
    private ObiektAdapter adapter2;
    private RecyclerView.LayoutManager layoutManager2;
    ProgressDialog progressDialog;
    Button filtrowanie, plus;
    LinearLayout filtrowanieLayout; // szczegolyObiektu;
    Intent intent;
    int p=0;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obiekty);

        this.setTitle("Lista obiektów sportowych");

        progressDialog = new ProgressDialog(this);
        obiektyList =new ArrayList<>();

        filtrowanie = (Button) findViewById(R.id.filtrowanieObiektyB);
        filtrowanieLayout = (LinearLayout) findViewById(R.id.filtrowanieLayout);
        //szczegolyObiektu = (LinearLayout) findViewById(R.id.szczegolyObiektuLayout);

        filtrowanieLayout.setVisibility(View.GONE);


        if(getIntent()!=null)
        intent = getIntent();






        createObiektList cOL = new createObiektList();
        cOL.execute();
        //buildRecyclerView2();
    }

    public class  createObiektList extends AsyncTask<String,String,String> {



        int obiekt_id, kryty, szatnia, oplata, aktywny, cena;
        Float cena1;
        String nazwa, miejscowosc, ulica, numerLokalu, wojewodztwo, kodPocztowy, email, telefon, opis, numerRachunku, dyscyplina, KrytoscSzatnosc="", wlasciciel_kontakt;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();


            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {



            try {

//        obiektyList.add(newObiektyItem(R.drawable.gora, "70","10-19","Pn-Sb"));
//        obiektyList.add(new ObiektyItem(R.drawable.gora, "39", "9-20", "Pn-Pt"));
//        obiektyList.add(new ObiektyItem(R.drawable.gora, "129", "12-18", "Pn-Nd"));

                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select obiekt.*, obiekt_cennik.*, dyscyplina.*, uzytkownik.email AS kontakt_do_wlasciciela, wojewodztwo.nazwa from obiekt,obiekt_cennik, dyscyplina, uzytkownik, wojewodztwo where obiekt.aktywny = 1 and obiekt.obiekt_id = obiekt_cennik.obiekt_id and obiekt_cennik.dyscyplina_id = dyscyplina.dyscyplina_id and obiekt.wlasciciel_id=uzytkownik.uzytkownik_id AND obiekt.wojewodztwo_id=wojewodztwo.wojewodztwo_id;");
                while (resultSet.next()) {
// DODAC FILTROWANIE WYNIKOW
                    Log.i("while","Czytam z wynikow");
                    obiekt_id = resultSet.getInt(1);
                    nazwa = resultSet.getString(2);
                    miejscowosc = resultSet.getString(3);
                    ulica = resultSet.getString(4);
                    numerLokalu = resultSet.getString(5);
                    wojewodztwo = resultSet.getString(25);
                    kodPocztowy = resultSet.getString(7);
                    email = resultSet.getString(8);
                    telefon = resultSet.getString(9);
                    kryty = resultSet.getInt(10);
                    szatnia = resultSet.getInt(11);
                    oplata = resultSet.getInt(12);
                    numerRachunku = resultSet.getString(13);
                    opis = resultSet.getString(14);
                    aktywny = resultSet.getInt(15);
                    wlasciciel_kontakt = resultSet.getString(24);
                    java.sql.Timestamp dbSqlTimestamp = resultSet.getTimestamp(17);
                    cena1 = resultSet.getFloat(21);
                    dyscyplina = resultSet.getString(23);
                    if(kryty == 1)
                        KrytoscSzatnosc = "Kryty";
                    else
                        KrytoscSzatnosc = "Niekryty";
                    if(szatnia == 1)
                        KrytoscSzatnosc += ", posiada szatnie";
                    else
                        KrytoscSzatnosc += ", bez szatni";

                    obiektyList.add(new ObiektyItem(R.drawable.gora, obiekt_id, nazwa, miejscowosc, ulica, numerLokalu, wojewodztwo, kodPocztowy, email, telefon, kryty, szatnia, oplata, numerRachunku, opis, aktywny, dbSqlTimestamp, Float.toString(cena1) + "zł/h", dyscyplina, KrytoscSzatnosc, wlasciciel_kontakt));

                    Log.i("while","dodaje obiekt: " + dyscyplina + " " + dyscyplina + " " + miejscowosc);

                }
                connection.close();
            } catch (Exception e) {
                System.err.println("Błąd: ");
                System.err.println(e.getMessage());
            }


            return "1";
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.hide();
            buildRecyclerView2();





            filtrowanie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(p==0) {
                        filtrowanieLayout.setVisibility(View.VISIBLE);
                        Log.i("while","Robie VISIBLE");
                        p=1;
                    }

                    else
                    {
                        filtrowanieLayout.setVisibility(View.GONE);
                        Log.i("while","Robie GONE");
                        p=0;
                    }

                }
            });
        }
    }

    public void buildRecyclerView2()
    {
        Log.i("while","1");
        recyclerView2 = findViewById(R.id.recyclerViewObiekty);
        recyclerView2.setHasFixedSize(true);
        Log.i("while","2");
        layoutManager2 = new LinearLayoutManager(this);
        adapter2 = new ObiektAdapter(obiektyList);
        Log.i("while","3");

        recyclerView2.setLayoutManager(layoutManager2);
        Log.i("while","4");
        recyclerView2.setAdapter(adapter2);
        Log.i("while","5");

        adapter2.setOnItemClickListener(new ObiektAdapter.onItemClickListener() {
            @Override
            public void onPlusClick(int position) {
                int id = obiektyList.get(position).getObiekt_id();
                //int idInt = Integer.parseInt(id);
                //Log.i("Klik",id);
                if(intent!=null)
                {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result", id);
                    Log.i("Klik", String.valueOf(id));
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }

            @Override
            public void onItemClick(int position) {
                Log.i("Klik","KLIKKKK");
                obiektyList.get(position);
                int id = obiektyList.get(position).getObiekt_id();
                Intent intent = new Intent(Obiekty.this, SzczegolyObiekt.class);
                intent.putExtra("obiekt",obiektyList.get(position));
                startActivity(intent);
            }

        });

    }

    public void zmienSzczegoly(int position)
    {

    }

    public ArrayList<ObiektyItem> getArrayList()
    {
        return obiektyList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.obiekt_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter2.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}
