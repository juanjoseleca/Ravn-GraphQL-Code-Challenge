package com.example.ravngraphqlcodechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class api_key extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_key);
        BottomNavigationView menu_navegacion = findViewById(R.id.navegador_id);
        menu_navegacion.setSelectedItemId(R.id.action_key);
        menu_navegacion.setOnNavigationItemSelectedListener(navListener);

    }
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
