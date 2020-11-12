package com.example.logowaniep1;

//TODO Jesli beda przygotowane activity SzczegolyObiektu przejsc przy zakladce Obiekt, po wybraniu konkretnego obiektu, w jego szczegoly
//TODO przy usuwaniu wybranego obiektu(jesli zaimplementowane) zastanowic sie nad zmiennymi idObiektu, idDyscyplina itp( wstawic nulla?) zeby nie zostaly po usunietym obiekcie przez przypadek

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DodajWydarzenie extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText godzina, godzinaKoniec, opis;
    String headUser, dataDoBazy, wybranaData, tempDate;
    Button dodajWydarzenie, szukajObiektu, wybierzDate;
    ProgressDialog progressDialog;
    Spinner spinner;
    List<ListaDodajWydarzenie> dyscypliny;
    TextView obiekt, dataTV;
    int idObiektu, idDyscyplina, headUserId;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        wybranaData = DateFormat.getDateInstance().format(calendar.getTime());

        tempDate = wybranaData.substring(wybranaData.length()-4)+ "-";

        switch(wybranaData.substring(0,3).toLowerCase()) {
            case "january":
            case "jan":
                tempDate += "01";
                break;

            case "febuary":
            case "feb":
                tempDate += "02";
                break;

            case "march":
            case "mar":
                tempDate += "03";
                break;

            case "april":
            case "apr":
                tempDate += "04";
                break;

            case "may":
                tempDate += "05";
                break;

            case "june":
            case "jun":
                tempDate += "06";
                break;

            case "july":
            case "jul":
                tempDate += "07";
                break;

            case "august":
            case "aug":
                tempDate += "08";
                break;

            case "september":
            case "sep":
            case "sept":
                tempDate += "09";
                break;

            case "october":
            case "oct":
                tempDate += "10";
                break;

            case "november":
            case "nov":
                tempDate += "11";
                break;

            case "december":
            case "dec":
                tempDate += "12";
                break;
        }
        String dzien="";
        if(wybranaData.trim().length()<=11)

            dzien = "-0"+wybranaData.substring(wybranaData.trim().length()-7,wybranaData.trim().length()-6);
        else
            dzien ="-"+ wybranaData.substring(wybranaData.trim().length()-8,wybranaData.trim().length()-6);


        tempDate += dzien;
        dataTV.setText(wybranaData+" | " + tempDate);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wydarzenie);

        dodajWydarzenie = (Button) findViewById(R.id.dodajWydarzenieButton1);
        szukajObiektu = (Button) findViewById(R.id.znajdzObiektDoWydarzeniaB);
        wybierzDate = (Button) findViewById(R.id.wybierzDateDodajWydarzenieB);
        obiekt = (TextView) findViewById(R.id.numerObiektuSzczegolyWydarzenia);
        dataTV = (TextView) findViewById(R.id.dataDodajWydarzenieET);
        godzina = (EditText) findViewById(R.id.godzinaStartDodajWydarzenieET);
        godzinaKoniec = (EditText) findViewById(R.id.godzinaKoniecDodajWydarzenieET);
        opis = (EditText) findViewById(R.id.opisDodajWydarzenieET);
        spinner = (Spinner) findViewById(R.id.wyborDyscyplinySpinner);

        wybierzDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DataPickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        headUser = Singleton.getInstance().getuzytkownikEmail();
        headUserId = Singleton.getInstance().getUzytkownikID();

        dodajWydarzenie.setText("Dodaj");

        dodajWydarzenie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodajWydarzenie();
            }
        });
        szukajObiektu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DodajWydarzenie.this,Obiekty.class);
                startActivityForResult(intent, 1);
            }
        });

        progressDialog = new ProgressDialog(this);


    }

    private void dodajWydarzenie() {



        DodawanieWydarzenia dodawanieWydarzenia  = new DodawanieWydarzenia ();
        dodawanieWydarzenia.execute();

    }

    private class DodawanieWydarzenia extends AsyncTask<String,String,String>
    {
        String godzinaStart = godzina.getText().toString();
        String godzinaKoniecS = godzinaKoniec.getText().toString();
        String opisS = opis.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();
            godzinaStart = godzina.getText().toString();
            godzinaKoniecS = godzinaKoniec.getText().toString();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                Calendar calendar = Calendar.getInstance();
                java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

                connection = ConnectionManager.getConnection();
                statement = connection.createStatement();

                String query = "insert into wydarzenie (obiekt_id, dyscyplina_id, data, czas_rozpoczecia, czas_zakonczenia, czas_utworzenia, organizator_id, opis, status ) values (?,?,?,?,?,?,?,?,0)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, idObiektu);
                preparedStmt.setInt(2, idDyscyplina);
                preparedStmt.setString(3, tempDate);
                preparedStmt.setString(4, godzinaStart);
                preparedStmt.setString(5, godzinaKoniecS);
                preparedStmt.setDate(6, startDate);
                preparedStmt.setInt(7, Singleton.getInstance().getUzytkownikID());
                preparedStmt.setString(8, opisS);
                preparedStmt.execute();





                connection.close();


            }
            catch(Exception e) {
                System.err.println("Błąd: ");
                System.err.println(e.getMessage());

            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
        }
    }

    public String ustalHeadUser()
    {
        //funkcja ustalania nazwy uzytkownika ktory zaklada
        return "PawelG98";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                idObiektu = data.getIntExtra("result",0);
                Log.i("Klik", String.valueOf(idObiektu));
                obiekt.setText(String.valueOf(idObiektu));

                ListaDyscyplin listaDyscyplin = new ListaDyscyplin();
                listaDyscyplin.execute();

            }
        }

    }

    private class ListaDyscyplin extends  AsyncTask<String,String,String>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();
            dyscypliny = new ArrayList<>();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.100:3306/aplikacja", "andro", "andro");
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select obiekt_cennik.dyscyplina_id, obiekt_cennik.obiekt_id, dyscyplina.nazwa, obiekt_cennik.cena from obiekt_cennik, dyscyplina where obiekt_cennik.dyscyplina_id = dyscyplina.dyscyplina_id and obiekt_id ="+idObiektu);
                while (resultSet.next()) {
                    ListaDodajWydarzenie listaDodajWydarzenie = new ListaDodajWydarzenie(resultSet.getString(3), resultSet.getInt(1));
                    dyscypliny.add(listaDodajWydarzenie);
                    Log.i("Klik w wydarzeniu", resultSet.getString(3));
                }


            }
            catch(Exception e) {
                System.err.println("Błąd: ");
                System.err.println(e.getMessage());
            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
            ArrayAdapter<ListaDodajWydarzenie> adapter = new ArrayAdapter<ListaDodajWydarzenie>(DodajWydarzenie.this, android.R.layout.simple_spinner_item, dyscypliny);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ListaDodajWydarzenie listaDodajWydarzenie = (ListaDodajWydarzenie) parent.getSelectedItem();
                    idDyscyplina = listaDodajWydarzenie.id;
                    Log.i("Klik w wydarzeniu", "Nazwa dyscypliny: "+listaDodajWydarzenie.nazwa+listaDodajWydarzenie.id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
    }


    //IMPORTANT To co jest wysweitlane w spinerze jest ustawiane w klasie ListaDodajWydarzenie w metodzie toString()

    public void getSelectedDyscyplina(View v)
    {
        ListaDodajWydarzenie listaDodajWydarzenie = (ListaDodajWydarzenie) spinner.getSelectedItem();
    }

    private void WyswietlDyscypliny(ListaDodajWydarzenie listaDodajWydarzenie)
    {

    }

}

