package com.example.ravngraphqlcodechallenge;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText search_editText = (EditText) findViewById(R.id.search_editText);

        search_editText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button

                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String content = search_editText.getText().toString();
                    Log.d("respuesta:",content);
                    MyApolloClient.getMyApolloClient().query(MiRepoQuery.builder().midato(content).build()).enqueue(new ApolloCall.Callback<MiRepoQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<MiRepoQuery.Data> response) {

                            Log.d("HERE", "regresamos con info papi: "+response.data());
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            Log.d("HERE", "gg papi ta fallando la wea: ");
                        }
                    });

                    //...
                    // Perform your action on key press here
                    // ...
                    Log.d("HERE", "onCreate() Restoring previous state");
                    return true;
                }
                return false;
            }
        });
    }


}

