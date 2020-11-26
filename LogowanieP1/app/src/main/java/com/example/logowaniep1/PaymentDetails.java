package com.example.logowaniep1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PaymentDetails extends AppCompatActivity {

    TextView textId,textAmount,textStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        textId = (TextView) findViewById(R.id.textId);
        textAmount = (TextView) findViewById(R.id.textAmount);
        textStatus = (TextView) findViewById(R.id.textStatus);

        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount)
    {
        try {
            textId.setText(response.getString("id"));
            textStatus.setText(response.getString("state"));
            textAmount.setText(paymentAmount+"PLN");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
