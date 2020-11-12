package com.example.logowaniep1;
// Dodawanie pol - jesli trzeba w obiekt_wydarzenie.xml dodac elemtn TextView, iamge itd
// wpisac do Array list (tutaj) wartosci elemtnow
//w example adapter pododawac elementy do adaptera
//Start = data, koniec = godziny , nie chce mi sie poprawiac :)

//to do
//1 usunac wydarzenie dataAktualna > dataWydarzenia
//2 dodac status wydarzenia
//Liste uzytkownikow - dynamiczne
// Komentarze ... oh jesus


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Wydarzenia extends AppCompatActivity {

    Button dodajWydarzenie,TEST;
    ArrayList<ExampleItem> exampleList;
    private RecyclerView recyclerView;
    private ExampleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydarzenia);

        dodajWydarzenie = (Button) findViewById(R.id.dodajWydB);
        TEST = (Button) findViewById(R.id.TestButton1);

        progressDialog = new ProgressDialog(this);
        exampleList = new ArrayList<>();

        TEST.setText("Prosze dzialac");

        createExampleList CEL = new createExampleList();
        CEL.execute();

        dodajWydarzenie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajWydarzenie();
            }
        });

    }

    public class createExampleList  extends AsyncTask<String,String,String>
    {
//        exampleList =  new ArrayList<>();
//        exampleList.add(new ExampleItem(R.drawable.running,"Bieganie 10km","Darmowe","12.11.2020","12:00 - 13:00",R.drawable.delete,"Andrzej"));
//        exampleList.add(new ExampleItem(R.drawable.bike,"Rower 18,5km","30zł","15.11.2020","8:00 - 9:45",R.drawable.delete,"Duda"));
//        exampleList.add(new ExampleItem(R.drawable.bed,"Drzemka","Darmowe","18.07.2021","10:00 - 10:30",R.drawable.delete,"PawelG"));
//
//        exampleList.add(new ExampleItem(R.drawable.running,"Bieganie 10km","Darmowe","12.11.2020","12:00 - 13:00",R.drawable.delete,"Andrzej"));
//        exampleList.add(new ExampleItem(R.drawable.bike,"Rower 18,5km","30zł","15.11.2020","8:00 - 9:45",R.drawable.delete,"Duda"));
//        exampleList.add(new ExampleItem(R.drawable.bed,"Drzemka","Darmowe","18.07.2021","10:00 - 10:30",R.drawable.delete,"PawelG"));
//
//        exampleList.add(new ExampleItem(R.drawable.running,"Bieganie 10km","Darmowe","12.11.2020","12:00 - 13:00",R.drawable.delete,"Andrzej"));
//        exampleList.add(new ExampleItem(R.drawable.bike,"Rower 18,5km","30zł","15.11.2020","8:00 - 9:45",R.drawable.delete,"Duda"));
//       exampleList.add(new ExampleItem(R.drawable.bed,"Drzemka","Darmowe","18.07.2021","10:00 - 10:30",R.drawable.delete,"PawelG"));

        String data, czasRozpoczecia, czasZakonczenia, dyscyplinaNazwa, cena, zakladajacy, miasto, ulica, nrLokalu, miejsce, obiektId,opis;
        int wydarzenieId;


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

            ResultSet resultSet = statement.executeQuery("select wydarzenie.data, wydarzenie.organizator_id, wydarzenie.czas_rozpoczecia, wydarzenie.czas_zakonczenia, wydarzenie.dyscyplina_id, wydarzenie.obiekt_id, obiekt_cennik.cena, dyscyplina.nazwa, obiekt.nazwa, obiekt.obiekt_id, obiekt.miejscowosc, obiekt.ulica, obiekt.numer_lokalu, wydarzenie.wydarzenie_id, wydarzenie.opis from wydarzenie, obiekt_cennik, dyscyplina, obiekt where obiekt.obiekt_id = wydarzenie.obiekt_id and obiekt_cennik.obiekt_id = obiekt.obiekt_id and wydarzenie.obiekt_id = obiekt_cennik.obiekt_id and wydarzenie.dyscyplina_id = obiekt_cennik.dyscyplina_id and dyscyplina.dyscyplina_id = wydarzenie.dyscyplina_id");

            while (resultSet.next()) {
// DODAC FILTROWANIE WYNIKOW
                Log.i("while","Czytam z wynikow");
                data = resultSet.getString(1);
                czasRozpoczecia = resultSet.getString(3);
                czasZakonczenia = resultSet.getString(4);
                dyscyplinaNazwa = resultSet.getString(8);
                cena = resultSet.getString(7);
                zakladajacy = resultSet.getString(2);
                miasto = resultSet.getString(11);
                ulica = resultSet.getString(12);
                nrLokalu = resultSet.getString(13);
                obiektId = resultSet.getString(6);
                wydarzenieId = resultSet.getInt(14);
                opis = resultSet.getString(15);
                miejsce = miasto+", "+ulica+" "+nrLokalu;
//                if(miejsce.length()>20)
//                miejsce = miasto;

                exampleList.add(new ExampleItem(R.drawable.bike, dyscyplinaNazwa,  cena+"zł", data, czasRozpoczecia.substring(0, czasRozpoczecia.length() - 3)+" - "+czasZakonczenia.substring(0, czasRozpoczecia.length() - 3), R.drawable.delete, zakladajacy,miejsce,obiektId,wydarzenieId,opis));
            }

            connection.close();
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
            buildRecyclerView();
        }
    }

    public void buildRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

       adapter.setOnItemClickListener(new ExampleAdapter.onItemClickListener() {
           @Override
           public void onItemClick(int position) {
               wybierz(position);
           }

           @Override
           public void onDeleteClick(int position) {
               usunWydarzenie(position);
           }
       });
    }


    public void dodajWydarzenie() {
        Intent intent = new Intent(this,DodajWydarzenie.class);
        //intent.setAction("Logowanie");
        startActivityForResult(intent, 1);
    }

    public void wybierz(int position)
    {
        //exampleList.get(position).changeText1(text);
        //adapter.notifyItemChanged(position);

        //DOPOPRAWY PRZESYLAC LISTE CALOSCIOWA, mala efektynowsc??

        Intent intentSzczegoly = new Intent(this,SzczegolyWydarzenia.class);
        ExampleItem doPodania = exampleList.get(position);
        String t1 = doPodania.getText1();
        String t2 = doPodania.getText2();
        String t3 = doPodania.getHeadUser();
        String t4 = doPodania.getKoniec();
        String t5 = doPodania.getStart();
        String t6 = doPodania.getMiejsceE();
        String t7 = doPodania.getObiektId();
        String t9 = doPodania.getOpis();
        int t8 = doPodania.getWydarzenieId();
        int i1 = doPodania.getImageResource();

        intentSzczegoly.putExtra("nazwa",t1);
        intentSzczegoly.putExtra("cena",t2);
        intentSzczegoly.putExtra("headUser",t3);
        intentSzczegoly.putExtra("koniec",t4);
        intentSzczegoly.putExtra("start",t5);
        intentSzczegoly.putExtra("img",i1);
        intentSzczegoly.putExtra("miejsce",t6);
        intentSzczegoly.putExtra("obiektId", t7);
        intentSzczegoly.putExtra("wydarzenieId", t8);
        intentSzczegoly.putExtra("opis",t9);

        startActivity(intentSzczegoly);



    }



    public void usunWydarzenie(int position)
    {
        exampleList.remove(position);
        adapter.notifyItemRemoved(position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//
//        //powrot danych z akwtywnosci DodajWydarzenie
//        if(requestCode == 1)
//        {
//            if(resultCode == RESULT_OK)
//            {
//              String nazwaNowe = data.getStringExtra("nazwa");
//              String cenaNowe = data.getStringExtra("cena");
//              String startNowe = data.getStringExtra("start");
//              String koniecNowe = data.getStringExtra("koniec");
//              String headUser = data.getStringExtra("headUser");
//              String miejsce = data.getStringExtra("miejsce");
//              String obiektId = data.getStringExtra("obiektId");
//              int zdj=0;
//
//              if(nazwaNowe.contains("Bieganie")) zdj = R.drawable.running; //totalnie glupie, ale narazie przejdzie
//              else if(nazwaNowe.contains("Rower")) zdj = R.drawable.bike; // zasada dzialania znana
//              else zdj = R.drawable.domyslne;
//
//              exampleList.add(new ExampleItem(zdj,nazwaNowe,cenaNowe,startNowe,koniecNowe,R.drawable.delete,headUser, miejsce, obiektId,wydarzenieId));
//              adapter.notifyItemInserted(exampleList.size());// w argumencie podajemy pozycje dodanego elementu
//
//            }
//        }
    }
}