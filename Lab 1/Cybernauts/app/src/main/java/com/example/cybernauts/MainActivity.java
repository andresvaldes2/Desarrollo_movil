// MainActivity.java
package com.example.cybernauts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button boton = null;
    EditText textoImprimir = null;
    CheckBox Check = null;
    CalendarView calendarView = null;
    String fechaSeleccionada = "";
    ImageButton imgButton = null;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textoImprimir = findViewById(R.id.texto);
        Check = findViewById(R.id.checking);
        boton = findViewById(R.id.botonHolaM);
        calendarView = findViewById(R.id.calendarView);
        imgButton = findViewById(R.id.imageBtn);

        fechaSeleccionada = formatoFecha.format(new Date());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
        });

        boton.setOnClickListener(v -> {
            String horaActual = formatoHora.format(new Date());
            Toast.makeText(getApplicationContext(),
                    textoImprimir.getText() + ", " +
                            Check.isChecked() + ", " +
                            horaActual + ", " +
                            fechaSeleccionada,
                    Toast.LENGTH_SHORT).show();
            Snackbar snack = Snackbar.make(v, "Hola", Snackbar.LENGTH_LONG);
        });

        imgButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }
}
