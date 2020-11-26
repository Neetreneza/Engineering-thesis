package com.example.logowaniep1;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import org.w3c.dom.Text;

import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Logowanie extends AppCompatActivity {

    EditText email, haslo;
    Button loguj;
    boolean isSuccess=false, PH = false;
    ImageView pokazHaslo3;
    TextView przejdzDoRejestracji;
    //ConstraintLayout l1 = (ConstraintLayout) findViewById(R.id.layoutGlownyLogowanie);

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);
        //l1.setBackgroundResource(R.drawable.tlo);

        email = (EditText) findViewById(R.id.emailET);
        haslo = (EditText) findViewById(R.id.hasloET);
        loguj = (Button) findViewById(R.id.logowanieB);
        pokazHaslo3 = (ImageView) findViewById(R.id.pokazHasloB3);
        przejdzDoRejestracji = (TextView) findViewById(R.id.textView9);



        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressDialog = new ProgressDialog(this);

        przejdzDoRejestracji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent doRejestracji = new Intent(Logowanie.this, Rejestracja.class);
                    startActivity(doRejestracji);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        loguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogowanie dologowanie = new doLogowanie();
                dologowanie.execute();
            }
        });

        pokazHaslo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazHasloL();
            }
        });

    }

    public void pokazHasloL()
    {
        if(!PH){
            haslo.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            PH = true;
        }
        else
        {
            haslo.setTransformationMethod(PasswordTransformationMethod.getInstance());
            PH = false;

        }
    }

    private class doLogowanie extends AsyncTask<String,String,String>{


        String emailstr= email.getText().toString();
        String passstr= haslo.getText().toString();
        String z="";



        String em = "q";
        String password = "w";



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if (!isFinishing() && progressDialog!= null) {
//                progressDialog = ProgressDialog.show(Logowanie.this, "Please Wait",null, true, true);
//            }


            progressDialog.setMessage("Przetwarzanie...");
            progressDialog.show();



        }

        @Override
        protected String doInBackground(String... params) {
             byte[] hashHaslo;
            if (emailstr.trim().equals("") || passstr.trim().equals(""))
                z = "Proszę uzupełnić wszystke pola";
            else {
                try {

                    connection = ConnectionManager.getConnection();
                    statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery("SELECT email, haslo, uzytkownik_id FROM uzytkownik");
                    boolean wyjdz = false;
                    while (resultSet.next() && !wyjdz) {
//                        hashHaslo = resultSet.getBytes(2);
//                        KeySpec keySpec = new PBEKeySpec( hashHaslo.toString().toCharArray(),resultSet.getBytes(3), 65536, 128);
//                        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSha1");
//                        byte[] hashowaneHaslo = secretKeyFactory.generateSecret(keySpec).getEncoded();
//                        Log.i("email", "moj mail " + emailstr + " :: " + resultSet.getString(1)  +" || moj hash: " + hashowaneHaslo + " :: " + resultSet.getBytes(2) + " || sol: " + resultSet.getString(3).getBytes());
                        if (emailstr.equals(resultSet.getString(1)) && passstr.equals(resultSet.getString(2)))  {
                            isSuccess = true;
                            Log.i("email", "moj mail " + emailstr + " :: " + resultSet.getString(1)  +" || moj haslo: " + passstr + " :: " + resultSet.getString(2));
                           z = "Logowanie udane";

                           Singleton.getInstance().setUzytkownikEmail(resultSet.getString(1));
                           Singleton.getInstance().setUzytkownikID(resultSet.getInt(3));


                           wyjdz = true;
                        }
                        else
                            z = "Niepoprawny emial lub hasło";
                    }

                    connection.close();
                }

                catch (Exception e) {
                    System.err.println("Błąd: ");
                    System.err.println(e.getMessage());
                }



            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();


            Toast.makeText(getBaseContext(),""+z, Toast.LENGTH_LONG).show();
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//                progressDialog = null;
//            }
            if(isSuccess) {

            Log.i("Baza","Wynik to "+z);
            Intent resultIntent = new Intent();

                resultIntent.putExtra("result", isSuccess);

                setResult(RESULT_OK, resultIntent);
                finish();


                }


        }
    }

}
