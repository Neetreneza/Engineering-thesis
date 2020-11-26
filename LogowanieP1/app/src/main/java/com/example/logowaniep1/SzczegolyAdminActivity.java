package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO aktualizować czas_aktualizacji obiektu w bazie

public class SzczegolyAdminActivity extends AppCompatActivity {

    @BindView(R.id.zdjecie)
    ImageView zdjecie;
    @BindView(R.id.wlascicielTextView2)
    TextView kierownikTV;
    @BindView(R.id.nazwaEditText)
    EditText nazwaET;
    @BindView(R.id.miejscowoscEditText)
    EditText miejscowoscET;
    @BindView(R.id.ulicaEditText)
    EditText ulicaET;
    @BindView(R.id.lokalEditText)
    EditText lokalET;
    @BindView(R.id.wojewodztwoEditText)
    EditText wojewodztwoET;
    @BindView(R.id.kodPocztowyEditText)
    EditText pocztaET;
    @BindView(R.id.emailTextView2)
    TextView emailTV;
    @BindView(R.id.telefonTextView2)
    TextView telefonTV;
    @BindView(R.id.rachunekTextView2)
    TextView rachunekTV;
    @BindView(R.id.opisEditText)
    TextView opisET;

    private int imageResource, obiekt_id, wojewodztwo;
    private String nazwa, miejscowosc, ulica, numer_lokalu, kod_pocztowy, email,  telefon, numer_rachunku, opis, kierownik_kontakt;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegoly_admin);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        ObiektDoWeryfikacji obiektDoWeryfikacji = intent.getParcelableExtra("obiekt");

        imageResource = obiektDoWeryfikacji.getImageResource();
        obiekt_id = obiektDoWeryfikacji.getObiekt_id();
        kierownik_kontakt = obiektDoWeryfikacji.getKierownik_kontakt();
        nazwa = obiektDoWeryfikacji.getNazwa();
        miejscowosc = obiektDoWeryfikacji.getMiejscowosc();
        ulica = obiektDoWeryfikacji.getUlica();
        numer_lokalu = obiektDoWeryfikacji.getNumer_lokalu();
        wojewodztwo = obiektDoWeryfikacji.getWojewodztwo();
        kod_pocztowy = obiektDoWeryfikacji.getKod_pocztowy();
        email = obiektDoWeryfikacji.getEmail();
        telefon = obiektDoWeryfikacji.getTelefon();
        numer_rachunku = obiektDoWeryfikacji.getNumer_rachunku();
        opis = obiektDoWeryfikacji.getOpis();

        this.setTitle("Szczegóły obiektu #" + obiekt_id);

        zdjecie.setImageResource(imageResource);
        kierownikTV.setText(kierownik_kontakt);
        nazwaET.setText(nazwa);
        miejscowoscET.setText(miejscowosc);
        ulicaET.setText(ulica);
        lokalET.setText(numer_lokalu);
        wojewodztwoET.setText(Integer.toString(wojewodztwo));
        pocztaET.setText(kod_pocztowy);
        emailTV.setText(email);
        telefonTV.setText(telefon);
        rachunekTV.setText(numer_rachunku);
        opisET.setText(opis);
    }

    @OnClick({R.id.akceptuj, R.id.odrzuc})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.akceptuj:
                try{
                    akceptowanieObiektu akceptowanie = new akceptowanieObiektu();
                    akceptowanie.execute();
                }
                catch (Exception e){
                    System.out.println("ERROR WTF: " + e.getMessage());
                }
                finish();
                break;
            case R.id.odrzuc:
                try{
                    odrzucenieObiektu odrzucenie = new odrzucenieObiektu();
                    odrzucenie.execute();
                }
                catch (Exception e){
                    System.out.println("ERROR WTF: " + e.getMessage());
                }
                finish();
                break;
        }
    }

    public class akceptowanieObiektu extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            //int id = obiektyList.get(a).getObiekt_id();
            try{
                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();
                String query = "UPDATE obiekt SET nazwa=?, miejscowosc=?, ulica=?, numer_lokalu=?, wojewodztwo_id=?, kod_pocztowy=?, opis=?, aktywny=1 WHERE obiekt.obiekt_id="+obiekt_id+";";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, nazwaET.getText().toString());
                preparedStmt.setString(2, miejscowoscET.getText().toString());
                preparedStmt.setString(3, ulicaET.getText().toString());
                preparedStmt.setString(4, lokalET.getText().toString());
                preparedStmt.setString(5, wojewodztwoET.getText().toString());
                preparedStmt.setString(6, pocztaET.getText().toString());
                preparedStmt.setString(7, opisET.getText().toString());

                preparedStmt.execute();
                connection.close();
            }
            catch (SQLException e){
                System.out.println("Wyjątek SQL: " + e);
            }
            Log.i("button","Zaakceptowano obiekt " + obiekt_id);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {

        }
    }

    public class odrzucenieObiektu extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            //int id = obiektyList.get(a).getObiekt_id();
            try{
                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();
                String query = "DELETE FROM obiekt WHERE obiekt.obiekt_id="+obiekt_id+";";
                statement.execute(query);
                connection.close();
            }
            catch (SQLException e){
                System.out.println("Wyjątek SQL: " + e);
            }
            Log.i("button","Odrzucono obiekt " + obiekt_id);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {

        }
    }

}