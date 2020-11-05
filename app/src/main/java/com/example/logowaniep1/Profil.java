package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.View.GONE;

public class Profil extends AppCompatActivity {

    TextView znajomiTV, umiejetnosciTV;
    TextView znajomiET, umiejetnosciET;
    LinearLayout umiejetnosciLayout, znajomiLayout;
    ImageView schowajZnajomych, schowajUmiejetnosci;
    Button pilkaL, pilkaM, pilkaH,  koszL, koszM, koszH;

    int z=0,u=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        pilkaL = (Button) findViewById(R.id.nogaNiski);
        pilkaM = (Button) findViewById(R.id.nogaSredni);
        pilkaH = (Button) findViewById(R.id.nogaWysoki);

        koszL = (Button) findViewById(R.id.koszNiski);
        koszM = (Button) findViewById(R.id.koszSredni);
        koszH = (Button) findViewById(R.id.koszWysoki);


        znajomiET = (TextView) findViewById(R.id.znajomiProfilET);
        umiejetnosciET = (TextView) findViewById(R.id.umiejetnosciProfilET);

        znajomiTV = (TextView) findViewById(R.id.znajomiProfilTV);
        umiejetnosciTV = (TextView) findViewById(R.id.umiejetnosciProfilTV);

        umiejetnosciLayout = (LinearLayout) findViewById(R.id.umiejetnosciSzczegoly);
        znajomiLayout = (LinearLayout) findViewById(R.id.znajomiButtonyLayout);

        schowajUmiejetnosci = (ImageView) findViewById(R.id.umiejetnosciSchowajImg);
        schowajZnajomych = (ImageView) findViewById(R.id.znajomiSchowajImg);

        znajomiLayout.setVisibility(GONE);
        umiejetnosciLayout.setVisibility(GONE);
        schowajZnajomych.setVisibility(GONE);
        schowajUmiejetnosci.setVisibility(GONE);

        znajomiTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otworzZnajomi();
            }
        });

        schowajZnajomych.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otworzZnajomi();
            }
        });

        znajomiET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otworzZnajomi();
            }
        });

        umiejetnosciTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otworzUmiejetnosci();
            }
        });

        umiejetnosciET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otworzUmiejetnosci();
            }
        });

        schowajUmiejetnosci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otworzUmiejetnosci();
            }
        });

    }



    private void otworzUmiejetnosci() {
        if(u==0)
        {
            umiejetnosciLayout.setVisibility(View.VISIBLE);
            schowajUmiejetnosci.setVisibility(View.VISIBLE);
            u++;
        }
        else
        {
            umiejetnosciLayout.setVisibility(GONE);
            schowajUmiejetnosci.setVisibility(GONE);
            u=0;

        }
    }

    private void otworzZnajomi() {
        if(z==0)
        {
            znajomiLayout.setVisibility(View.VISIBLE);
            schowajZnajomych.setVisibility(View.VISIBLE);
            z++;
        }
        else
        {
            znajomiLayout.setVisibility(GONE);
            schowajZnajomych.setVisibility(GONE);
            z=0;

        }
    }

    public void click(View view) {
//        Button button = (Button) findViewById(view.getId());
//        button.setTextColor(Color.RED);

        switch (view.getId())
        {
            case R.id.nogaNiski:
                pilkaL.setTextColor(Color.RED);
                pilkaH.setTextColor(Color.BLACK);
                pilkaM.setTextColor(Color.BLACK);
                break;

            case R.id.nogaSredni:
                pilkaM.setTextColor(Color.RED);
                pilkaL.setTextColor(Color.BLACK);
                pilkaH.setTextColor(Color.BLACK);
                break;

            case R.id.nogaWysoki:
                pilkaH.setTextColor(Color.RED);
                pilkaL.setTextColor(Color.BLACK);
                pilkaM.setTextColor(Color.BLACK);
                break;

            case R.id.koszNiski:
                koszL.setTextColor(Color.RED);
                koszM.setTextColor(Color.BLACK);
                koszH.setTextColor(Color.BLACK);
                break;

            case R.id.koszSredni:
                koszM.setTextColor(Color.RED);
                koszL.setTextColor(Color.BLACK);
                koszH.setTextColor(Color.BLACK);
                break;

            case R.id.koszWysoki:
                koszH.setTextColor(Color.RED);
                koszM.setTextColor(Color.BLACK);
                koszL.setTextColor(Color.BLACK);
                break;

        }
    }
}
