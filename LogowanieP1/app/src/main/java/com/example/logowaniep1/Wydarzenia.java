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

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Wydarzenia extends AppCompatActivity {

    Button dodaj;
    ArrayList<ExampleItem> exampleList;
    private RecyclerView recyclerView;
    private ExampleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydarzenia);

        createExampleList();
        buildRecyclerView();

        dodaj = (Button) findViewById(R.id.dodaj);

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajWydarzenie();
            }
        });

    }

    public void createExampleList()
    {
        exampleList =  new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.running,"Bieganie 10km","Darmowe","12.11.2020","12:00 - 13:00",R.drawable.delete,"Andrzej"));
        exampleList.add(new ExampleItem(R.drawable.bike,"Rower 18,5km","30zł","15.11.2020","8:00 - 9:45",R.drawable.delete,"Duda"));
        exampleList.add(new ExampleItem(R.drawable.bed,"Drzemka","Darmowe","18.07.2021","10:00 - 10:30",R.drawable.delete,"PawelG"));

        exampleList.add(new ExampleItem(R.drawable.running,"Bieganie 10km","Darmowe","12.11.2020","12:00 - 13:00",R.drawable.delete,"Andrzej"));
        exampleList.add(new ExampleItem(R.drawable.bike,"Rower 18,5km","30zł","15.11.2020","8:00 - 9:45",R.drawable.delete,"Duda"));
        exampleList.add(new ExampleItem(R.drawable.bed,"Drzemka","Darmowe","18.07.2021","10:00 - 10:30",R.drawable.delete,"PawelG"));

        exampleList.add(new ExampleItem(R.drawable.running,"Bieganie 10km","Darmowe","12.11.2020","12:00 - 13:00",R.drawable.delete,"Andrzej"));
        exampleList.add(new ExampleItem(R.drawable.bike,"Rower 18,5km","30zł","15.11.2020","8:00 - 9:45",R.drawable.delete,"Duda"));
        exampleList.add(new ExampleItem(R.drawable.bed,"Drzemka","Darmowe","18.07.2021","10:00 - 10:30",R.drawable.delete,"PawelG"));

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
        int i1 = doPodania.getImageResource();

        intentSzczegoly.putExtra("nazwa",t1);
        intentSzczegoly.putExtra("cena",t2);
        intentSzczegoly.putExtra("headUser",t3);
        intentSzczegoly.putExtra("koniec",t4);
        intentSzczegoly.putExtra("start",t5);
        intentSzczegoly.putExtra("img",i1);

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


        //powrot danych z akwtywnosci DodajWydarzenie
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
              String nazwaNowe = data.getStringExtra("nazwa");
              String cenaNowe = data.getStringExtra("cena");
              String startNowe = data.getStringExtra("start");
              String koniecNowe = data.getStringExtra("koniec");
              String headUser = data.getStringExtra("headUser");
              int zdj=0;

              if(nazwaNowe.contains("Bieganie")) zdj = R.drawable.running; //totalnie glupie, ale narazie przejdzie
              else if(nazwaNowe.contains("Rower")) zdj = R.drawable.bike; // zasada dzialania znana
              else zdj = R.drawable.domyslne;

              exampleList.add(new ExampleItem(zdj,nazwaNowe,cenaNowe,startNowe,koniecNowe,R.drawable.delete,headUser));
              adapter.notifyItemInserted(exampleList.size());// w argumencie podajemy pozycje dodanego elementu

            }
        }
    }
}