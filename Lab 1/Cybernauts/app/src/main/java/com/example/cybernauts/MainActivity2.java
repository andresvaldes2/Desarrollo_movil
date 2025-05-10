package com.example.cybernauts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    Button closeButton, whatsappButton;
    EditText numberInput;

    // Variables para validar longitud
    int limiteCodeLocal = 3;
    int limitePhone = 8;
    String defaultLocal = "507";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        closeButton = findViewById(R.id.closeBtn);
        whatsappButton = findViewById(R.id.whatsappBtn);
        numberInput = findViewById(R.id.numberInput);

        // Botón para volver a MainActivity
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        })

        whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = numberInput.getText().toString().trim();

                if (input.length() == (limiteCodeLocal + limitePhone)) {
                    Toast.makeText(getApplicationContext(), "Número ingresado: " + input, Toast.LENGTH_SHORT).show();
                    sendMessageWhatsapp(input);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Por favor ingrese un número válido de " + (limiteCodeLocal + limitePhone) + " dígitos",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendMessageWhatsapp(String phone) {
        String numeroPhone = phone.substring(phone.lastIndexOf(defaultLocal));
        System.out.println("numeroPhone = " + numeroPhone);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                String url = "https://api.whatsapp.com/send?phone=" + phone;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                finish();
            }
        });
    }
}
