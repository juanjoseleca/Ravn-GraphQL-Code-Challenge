package com.example.ravngraphqlcodechallenge;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaramos las variables para obtener las ids---------

        EditText search_editText = (EditText) findViewById(R.id.search_editText); //id del buscador
        ListView listview2=(ListView) findViewById(R.id.list_repositorios); //id del listado

        //-------------------------------------------------------

        search_editText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //Esta función se activa cuando apretamos la tecla 'enter'

                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String content = search_editText.getText().toString();

                    Log.d("respuesta:",content);
                    //Realizamos la consulta mediante apollo al api de Github
                    MyApolloClient.getMyApolloClient().query(MiRepoQuery.builder().midato(content).build()).enqueue(new ApolloCall.Callback<MiRepoQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<MiRepoQuery.Data> response) {
                        //En caso obtengamos datos en nuestra consulta al api se ejecutara lo siguiente

                            //Ejecutamos en el hilo principal
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Es necesario ejecutar las siguientes instrucciones en el hilo principal
                                    //para que no exista una sobrecarga
                                    ArrayAdapter<String> adapter;
                                    ArrayList<String> names = new ArrayList<String>();
                                    String t1=response.data().repositoryOwner().repositories().nodes().get(0).name();
                                    names.add(t1);
                                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names);
                                    listview2.setAdapter(adapter);
                                }
                            });



                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {

                        }

                    });


                    return true;
                }
                return false;
            }
        });
    }


}

