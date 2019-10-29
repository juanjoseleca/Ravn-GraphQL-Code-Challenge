package com.example.ravngraphqlcodechallenge;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.io.OutputStreamWriter;
import java.io.IOException;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class api_key extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_key);
        //Declaramos las variables para obtener los ids---------
        BottomNavigationView menu_navegacion = findViewById(R.id.navegador_id);
        menu_navegacion.setSelectedItemId(R.id.action_key);
        menu_navegacion.setOnNavigationItemSelectedListener(navListener);
        EditText clave = (EditText) findViewById(R.id.clave_id);
        Button clickButton = (Button) findViewById(R.id.guardar_id);
        ImageView img = (ImageView) findViewById(R.id.imagen_id);
        //------------------------------------------------------

        //Esta funcion se ejecuta cuando hacemos clic en 'Guardar'
        //La cual nos permite escribir en un archivo la clave para posteriormente leerla
        clickButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtenemos el valor del editor de texto
                String s_clave = clave.getText().toString();
                try {
                    //Escribimos en el archivo el string 's_clave'
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(api_key.this.openFileOutput("config.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(s_clave);
                    outputStreamWriter.close();
                    Toast.makeText(api_key.this,"La clave se guardó correctamente",
                            Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                    Toast.makeText(api_key.this,"La clave no se ha guardado correctamente",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //Esta funcion se activa al hacer clic en la imagen(icono de Github)
        //el cual nos redirige a una pagina donde obtendremos el API Key
        img.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/settings/tokens"));
                startActivity(browserIntent);
            }
        });

    }

    //Esta funcion se ejecuta cuando hacemos clic en algun boton de la barra de navegacion

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NotNull MenuItem item) {


                    switch (item.getItemId()) {
                        case R.id.action_search:
                            Intent abrir_actividad_api_key = new Intent(api_key.this,MainActivity.class);
                            api_key.this.startActivity(abrir_actividad_api_key);
                            Log.d("ACTIVIDAD","Se hizo click en action search");
                            break;
                        case R.id.action_key:

                            Log.d("ACTIVIDAD","Se hizo click en action key");
                            break;

                    }

                    return true;
                }
            };
}
