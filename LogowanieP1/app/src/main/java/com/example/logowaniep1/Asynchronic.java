package com.example.logowaniep1;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Asynchronic extends AsyncTask<String,String,String>
{
    private ProgressDialog progressDialog;
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet resultSet = null;


    Asynchronic(ProgressDialog progressDialogWe)
    {
        progressDialog = progressDialogWe;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Przetwarzanie...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

//            con = ConnectionManager.getConnection();
//            stmt = con.createStatement();
//            resultSet = stmt.executeQuery("select uzytkownik_id from wydarzenie_uczestnicy where wydarzenie_id='"+identyfikatorWydarzenia+"'");
//
//            while(resultSet.next())
//            {
//                nazwa = resultSet.getString(1);
//                uzytkownicyLista.add(new UzytkownicyItem(R.drawable.domyslne, nazwa));
//
//            }
//            con.close();

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