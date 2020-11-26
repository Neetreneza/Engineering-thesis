package com.example.logowaniep1;


// R = rejestracja
// RT =  String wyjety z pol edycyjnych rejestracji | do sprawdzenia poprawnosci wpisywanych tresci
// TODO polaczenie z baza DONE
// TODO wymogi co do wpisywanych tresci, hashowanie hasla
// TODO automatyczne logowanie po udanej rejestracji
// TODO OPTYMALIZACJA szczegolnie przy hashowaniu,najpierw wyszukac pasujacy email, dopiero potem sprawdzic czy dla asujace maila, haslo tez jest dobre


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Rejestracja extends AppCompatActivity {

    ProgressDialog progressDialog;

    EditText emailR, nazwaR, nazwiskoR, dataUrodzeniaR, hasloR, haslo2R;
    Button rejestruj;
    ImageView pokazHaslo1, pokazHaslo2;
    Boolean isSuccessRejestracja = false , blad = false, PH = false;
    String b;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        emailR = (EditText) findViewById(R.id.emailR);
        hasloR = (EditText) findViewById(R.id.hasloR);
        haslo2R = (EditText) findViewById(R.id.haslo2R);
        nazwaR = (EditText) findViewById(R.id.nazwaR);
        nazwiskoR = (EditText) findViewById(R.id.nazwiskoR);
        dataUrodzeniaR = (EditText) findViewById(R.id.dataUrodzeniaR);
        rejestruj = (Button) findViewById(R.id.rejestracjaB);
        pokazHaslo1 = (ImageView) findViewById(R.id.pokazHasloB1);
        pokazHaslo2 = (ImageView) findViewById(R.id.pokazHasloB2);




        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressDialog = new ProgressDialog(this);

        pokazHaslo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazHasloR();
            }
        });

        pokazHaslo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazHasloR();
            }
        });

        rejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               doRejestracja dorejestracja = new doRejestracja();
               dorejestracja.execute();



            }
        });
    }

    public void pokazHasloR()
    {
        if(!PH){
            hasloR.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            haslo2R.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            PH = true;
        }
        else
        {
            hasloR.setTransformationMethod(PasswordTransformationMethod.getInstance());
            haslo2R.setTransformationMethod(PasswordTransformationMethod.getInstance());
            PH = false;

        }
    }

    private class doRejestracja extends AsyncTask<String,String,String> {

        String emailRT= emailR.getText().toString();
        String nazwaRT= nazwaR.getText().toString();
        String hasloRT= hasloR.getText().toString();
        String haslo2RT = haslo2R.getText().toString();
        String dataUrodzeniaRT = dataUrodzeniaR.getText().toString();
        String nazwiskoRT = nazwiskoR.getText().toString();
        String z = "";

        @Override
        protected void onPreExecute() {


            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();


            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            boolean hasloB, imieB, nazwiskoB, dataUrodzeniaB, emailB;
            hasloB = imieB = nazwiskoB = dataUrodzeniaB = emailB = false;

            if(nazwaRT.trim().equals("") ||nazwiskoRT.trim().equals(""))
                z = "Proszę uzupełnić wszystke pola \n";

            else
                imieB = nazwiskoB = true;

            b = z; //testowanie bledu

            //Sprawdzanie hasla
            String hasloPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,30}$";
            if(hasloRT.matches(haslo2RT)&& hasloRT.matches(hasloPattern));
            else
                b+="Hasło musi zawierać: \n conajmniej 8 znaków, \n jedną małą i wielką literę, \n cyfrę i znak specjalny (@#$&*) itp.\n";


            String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            if(emailRT.matches(emailPattern));
            else
                b+="Proszę podać poprawny e-mail";

            String dataUrodzeniaPattern = "^\\d{4}-\\d{2}-\\d{2}$";
            if(dataUrodzeniaRT.matches(dataUrodzeniaPattern));
            else
                b+="Datę należy podać w formacie YYYY-MM-DD\n";








            if(!b.isEmpty())
            {
                blad = true;
            }

            else
            {

                try {

                    connection = ConnectionManager.getConnection();
                    statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery("SELECT email FROM uzytkownik");
                    boolean powtarzajacyEmail = false;
                    while (resultSet.next()) {
                        Log.i("email", "MOJ EMAIL: " + emailRT + " :: " + resultSet.getString(1));
                        if (emailRT.equals(resultSet.getString(1)))  {
                            powtarzajacyEmail = true;
                            b += "Konto z podanym adresem e-mail już istnieje. \n";
                        }
                    }
                    if (powtarzajacyEmail == false) {
                        Calendar calendar = Calendar.getInstance();
                        java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

                        //TWORZENIE SOLI, KONF SHA512 z solą, generowanie hasła
                        SecureRandom random = new SecureRandom();
                        //byte[] salt = new byte[16];
                        //random.nextBytes(salt);
                        //KeySpec keySpec = new PBEKeySpec(hasloRT.toCharArray(), 'B[@12345', 65536, 128);
                        //SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSha1");
                        //byte[] hashowaneHaslo = secretKeyFactory.generateSecret(keySpec).getEncoded();

                        String query = "insert into uzytkownik (imie, nazwisko, email, haslo, data_urodzenia, czas_utworzenia, sol, typ) values (?,?,?,?,?,?,5,0)";

                        PreparedStatement preparedStmt = connection.prepareStatement(query);
                        preparedStmt.setString(1, nazwaRT);
                        preparedStmt.setString(2, nazwiskoRT);
                        preparedStmt.setString(3, emailRT);
                        preparedStmt.setString(4, hasloRT);
                        preparedStmt.setString(5, dataUrodzeniaRT);
                        preparedStmt.setDate(6, startDate);
                        //preparedStmt.setString(7, salt.toString());

                        //Log.i("dane", "haslo = " + hashowaneHaslo + " sol = " + salt);

                        preparedStmt.execute();

                        connection.close();
                        isSuccessRejestracja = true;

                    }
                }
                catch(Exception e)

                {
                    System.err.println("Błąd: ");
                    System.err.println(e.getMessage());
                }

            }
            return z;        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(getBaseContext(),""+z, Toast.LENGTH_LONG).show();

                if(!b.equals(""))
                Toast.makeText(getBaseContext(),"Bledy: " +b, Toast.LENGTH_LONG).show();

            progressDialog.hide();
            if(isSuccessRejestracja) {

                //Log.i("Baza","Wynik to "+z);
                Intent resultIntent = new Intent();

                resultIntent.putExtra("result", isSuccessRejestracja);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        }
    }
}

