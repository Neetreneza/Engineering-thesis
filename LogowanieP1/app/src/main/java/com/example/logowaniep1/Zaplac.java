package com.example.logowaniep1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Zaplac extends AppCompatActivity {
    public static final String PAYPAL_CLIENT_ID = "ARN8LBD-ezXKaeRyzYBPIyx4TFlMV7qACZvaBvcto4fxs9BbE5ohEYNjqVEKZXn1_h1xJCgniUUDJGxE";
    public static final int PAYPAL_REQUEST_CODE = 7171;


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID);

    String kwota;
    Button zaplacB, sprawdzB;


    @Override
    protected void onDestroy() {
        stopService(new Intent(Zaplac.this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaplac);


        Intent intent = new Intent(Zaplac.this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        zaplacB = (Button) findViewById(R.id.potwierdzPlatnoscPAYPALB);
        sprawdzB = (Button) findViewById(R.id.sprawdzPaypalaB);

        sprawdzB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Zaplac.this, PaymentDetails.class);
                startActivity(intent);
            }
        });

        zaplacB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zaplac();
            }
        });


    }

    private void zaplac() {
        kwota = "249.99";
        PayPalPayment payPalPayment = new PayPalPayment
                (new BigDecimal(String.valueOf(kwota)),
                        "PLN", "Oplata za wydarzenie",
                        PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(Zaplac.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(Zaplac.this, PaymentDetails.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", kwota)
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(Zaplac.this, "Cancel", Toast.LENGTH_SHORT);
                }

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(Zaplac.this, "Invalid", Toast.LENGTH_SHORT);
        }
    }
}