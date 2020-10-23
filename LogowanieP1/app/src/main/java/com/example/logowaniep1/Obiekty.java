package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Obiekty extends AppCompatActivity {

    ArrayList<ObiektyItem> obiektyList;
    private RecyclerView recyclerView2;
    private ObiektAdapter adapter2;
    private RecyclerView.LayoutManager layoutManager2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obiekty);



        createObiektList();
        buildRecyclerView2();
    }


    public void  createObiektList() {
        obiektyList = new ArrayList<>();
        obiektyList.add(new ObiektyItem(R.drawable.gora, "70", "10-19", "Pn-Sb"));
        obiektyList.add(new ObiektyItem(R.drawable.gora, "39", "9-20", "Pn-Pt"));
        obiektyList.add(new ObiektyItem(R.drawable.gora, "129", "12-18", "Pn-Nd"));



    }

    public void buildRecyclerView2()
    {
        recyclerView2 = findViewById(R.id.recyclerViewObiekty);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        adapter2 = new ObiektAdapter(obiektyList);

        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(adapter2);

        adapter2.setOnItemClickListener(new ObiektAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
              //donotjing
                Log.i("Klik","KLIKKKK");
            }

        });
    }

    public ArrayList<ObiektyItem> getArrayList()
    {
        return obiektyList;
    }
}
