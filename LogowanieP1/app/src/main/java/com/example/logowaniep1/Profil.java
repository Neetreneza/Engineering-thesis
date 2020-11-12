package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static android.view.View.GONE;

//TODO sprawdzic przy edycji czy zmieniany email istnieje w bazie

public class Profil extends AppCompatActivity {

    TextView znajomiTV, ocenaUzytkownika, umiejetnosciTV;
    EditText nazwaUzytkownika, emailUzytkownika, dataUrodzeniaUzytkownika, telefon, nazwiskoUzytkownika;
    TextView znajomiET, umiejetnosciET;
    LinearLayout umiejetnosciLayout, znajomiLayout;
    ImageView schowajZnajomych, schowajUmiejetnosci;
    Button pilkaL, pilkaM, pilkaH,  koszL, koszM, koszH, zapisz;
    String  nazwaRT, emailRT, dataUrodzeniaRT, telefonRT, nazwiskoRT, identyfikatorUzytkownikaEmail;
    ImageView ustawienia;
    int licz=1, identyfikatorUzytkownika;

    ProgressDialog progressDialog;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    int z=0,u=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        progressDialog = new ProgressDialog(this);

        identyfikatorUzytkownikaEmail = Singleton.getInstance().getuzytkownikEmail();
        identyfikatorUzytkownika = Singleton.getInstance().getUzytkownikID();

        pilkaL = (Button) findViewById(R.id.nogaNiski);
        pilkaM = (Button) findViewById(R.id.nogaSredni);
        pilkaH = (Button) findViewById(R.id.nogaWysoki);

        koszL = (Button) findViewById(R.id.koszNiski);
        koszM = (Button) findViewById(R.id.koszSredni);
        koszH = (Button) findViewById(R.id.koszWysoki);

        zapisz = (Button) findViewById(R.id.zapiszUstawieniaProfiluB) ;
        zapisz.setVisibility(GONE);


        znajomiET = (TextView) findViewById(R.id.znajomiProfilET);
        umiejetnosciET = (TextView) findViewById(R.id.umiejetnosciProfilET);

        znajomiTV = (TextView) findViewById(R.id.znajomiProfilTV);
        umiejetnosciTV = (TextView) findViewById(R.id.umiejetnosciProfilTV);

        umiejetnosciLayout = (LinearLayout) findViewById(R.id.umiejetnosciSzczegoly);
        znajomiLayout = (LinearLayout) findViewById(R.id.znajomiButtonyLayout);

        schowajUmiejetnosci = (ImageView) findViewById(R.id.umiejetnosciSchowajImg);
        schowajZnajomych = (ImageView) findViewById(R.id.znajomiSchowajImg);

        nazwaUzytkownika = (EditText) findViewById(R.id.nazwaProfilET);
        emailUzytkownika = (EditText) findViewById(R.id.emailProfilET);
        ocenaUzytkownika = (TextView) findViewById(R.id.ocenaProfilET);
        dataUrodzeniaUzytkownika = (EditText) findViewById(R.id.dataUrodzeniaProfilET);
        telefon = (EditText) findViewById(R.id.telefonET);
        nazwiskoUzytkownika = (EditText) findViewById(R.id.nazwiskoProfilET);

        ustawienia = (ImageView) findViewById(R.id.ustawieniaProfil);

        nazwaUzytkownika.setEnabled(false);
        emailUzytkownika.setEnabled(false);
        dataUrodzeniaUzytkownika.setEnabled(false);
        telefon.setEnabled(false);
        nazwiskoUzytkownika.setEnabled(false);

        znajomiLayout.setVisibility(GONE);
        umiejetnosciLayout.setVisibility(GONE);
        schowajZnajomych.setVisibility(GONE);
        schowajUmiejetnosci.setVisibility(GONE);



        Profilowanie profilowanie = new Profilowanie();
        profilowanie.execute();


    }

    private class Profilowanie extends AsyncTask<String,String,String>
    {
        String nazwaU,dataU,emailU,ocenaU, telefonU,nazwiskoU;

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

                ResultSet resultSet = statement.executeQuery("SELECT imie, nazwisko, email, telefon, ocena, data_urodzenia FROM uzytkownik where email ='"+identyfikatorUzytkownika+"'");

                while(resultSet.next())
                {
                    nazwaU = (resultSet.getString(1));
                    dataU = (resultSet.getString(6));
                    emailU = (resultSet.getString(3));
                    ocenaU = (resultSet.getString(5));
                    telefonU = (resultSet.getString(4));
                    nazwiskoU = (resultSet.getString(2));
                    nazwaUzytkownika.setText(nazwaU);
                    dataUrodzeniaUzytkownika.setText(dataU);
                    emailUzytkownika.setText(emailU);
                    ocenaUzytkownika.setText(ocenaU);
                    telefon.setText(telefonU);
                    nazwiskoUzytkownika.setText(nazwiskoU);

                }





            }
            catch (Exception e){
                System.err.println("Błąd: ");
                System.err.println(e.getMessage());
            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.hide();

            //ustawOcene(); TODO DO PORPAWY




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

            ustawienia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    otworzUstawienia();
                }
            });

            zapisz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nazwaRT = nazwaUzytkownika.getText().toString();
                    nazwiskoRT = nazwiskoUzytkownika.getText().toString();
                    emailRT = emailUzytkownika.getText().toString();
                    telefonRT = telefon.getText().toString();
                    dataUrodzeniaRT = dataUrodzeniaUzytkownika.getText().toString();
                    String blad="";

                    if(nazwaRT.trim().equals("") ||nazwiskoRT.trim().equals(""))
                        blad = "Proszę uzupełnić wszystke pola \n";


                    //Sprawdzanie hasla
//            String hasloPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,30}$";
//            if(hasloRT.matches(haslo2RT)&& hasloRT.matches(hasloPattern));
//            else
//                blad+="Hasło musi zawierać: \n conajmniej 8 znaków, \n jedną małą i wielką literę, \n cyfrę i znak specjalny (@#$&*) itp.\n";


                    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                    if(emailRT.matches(emailPattern));
                    else
                        blad+=emailRT+"Proszę podać poprawny e-mail\n";

                    String dataUrodzeniaPattern = "^\\d{4}-\\d{2}-\\d{2}$";
                    if(dataUrodzeniaRT.matches(dataUrodzeniaPattern));
                    else
                        blad+="Datę należy podać w formacie YYYY-MM-DD\n";

                    String telefonPattern = "^\\+48\\d{9}$";
                    if(telefonRT.matches(telefonPattern));
                    else
                        blad+=telefonRT+"Telefon należy podać w formacie +48 CCCCCCCCC (9 cyfr)\n";

                    if(blad.equals(""))
                    {
                        ZamienWBazie zamienWBazie = new ZamienWBazie();
                        zamienWBazie.execute();
                    }
                    else
                        Toast.makeText(getBaseContext(),blad, Toast.LENGTH_LONG).show();



                }
            });

        }

        private void ustawOcene() {
            int ocena = Integer.valueOf(ocenaUzytkownika.getText().toString());
            if(ocena > 0)
            {
                ocenaUzytkownika.setText("+"+ocena);
                ocenaUzytkownika.setTextColor(Color.GREEN);
            }
            else
            {
                if(ocena < 0 ) ocenaUzytkownika.setText("-"+ocena);
                ocenaUzytkownika.setTextColor(Color.RED);
            }


        }
    }

    private void otworzUstawienia()
    {
        if(licz==1)
        {
            nazwaUzytkownika.setEnabled(true);
            emailUzytkownika.setEnabled(true);
            dataUrodzeniaUzytkownika.setEnabled(true);
            telefon.setEnabled(true);
            nazwiskoUzytkownika.setEnabled(true);
            zapisz.setVisibility(View.VISIBLE);
            licz = 0;
        }
        else
        {
            nazwaUzytkownika.setEnabled(false);
            emailUzytkownika.setEnabled(false);
            dataUrodzeniaUzytkownika.setEnabled(false);
            telefon.setEnabled(false);
            nazwiskoUzytkownika.setEnabled(false);
            zapisz.setVisibility(GONE);
            licz = 1;
        }






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

        switch (view.getId()) {
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

    private class ZamienWBazie extends AsyncTask<String,String,String>
    {
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

                String query = "update uzytkownik set email =?, telefon =?, data_urodzenia=?, imie=?, nazwisko=? where email =?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, emailRT);
                preparedStmt.setString(2, telefonRT);
                preparedStmt.setString(3, dataUrodzeniaRT);
                preparedStmt.setString(4, nazwaRT);
                preparedStmt.setString(5, nazwiskoRT);
                preparedStmt.setString(6, Singleton.getInstance().getuzytkownikEmail());
                preparedStmt.execute();

                Singleton.getInstance().setUzytkownikEmail(emailRT);

                connection.close();

            }
            catch (Exception e){
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
}
