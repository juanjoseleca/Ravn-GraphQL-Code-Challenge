package com.example.ravngraphqlcodechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class topcontributors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topcontributors);
        ListView listview2=(ListView) findViewById(R.id.contributors_id);
        String nombre_usuario= getIntent().getStringExtra("NOMBRE_USUARIO");
        String repositorio = getIntent().getStringExtra("REPOSITORIO");
        Log.d("ALERTA:","Ejecucion onCreate, topcontributors");
            //Log.d("POSIBLE ERRROR:",String.valueOf(MyApolloClient.getMyApolloClient(topcontributors.this).query(Get_collaboratorsQuery.builder().midato(nombre_usuario).repository(repositorio).build())) );

            MyApolloClient.getMyApolloClient(topcontributors.this).query(Get_collaboratorsQuery.builder().midato(nombre_usuario).repository(repositorio).build()).enqueue(new ApolloCall.Callback<Get_collaboratorsQuery.Data>() {


                @Override
                public void onResponse(@NotNull Response<Get_collaboratorsQuery.Data> response) {
                    //En caso obtengamos datos en nuestra consulta al api, se ejecutara lo siguiente:

                    if(response.data().repositoryOwner().repository().collaborators()==null)
                    {
                        Log.d("ERROR","La consulta es nula");
                        Intent show_contributors = new Intent(topcontributors.this,Cuenta_privada.class);

                        topcontributors.this.startActivity(show_contributors);


                    }
                    else
                    {
                        //Ejecutamos en el hilo principal
                        topcontributors.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Es necesario ejecutar las siguientes instrucciones en el hilo principal
                                //para que no exista una sobrecarga

                                ArrayAdapter<String> adapter;
                                List<List<String>> datos = new ArrayList<>();
                                List<String> names = new ArrayList<>();
                                List<String> locations = new ArrayList<>();
                                List<String> logins = new ArrayList<>();


                                for(Get_collaboratorsQuery.Node entrada : response.data().repositoryOwner().repository().collaborators().nodes())
                                {
                                    //En la siguiente linea obtengo el nombre del usuario y lo divido segun los espacios en blanco
                                    String [] name_parts = String.valueOf((String)entrada.name()).split(" ");
                                    //Obtengo el primer nombre del usuario
                                    names.add(name_parts[0]);
                                    locations.add(String.valueOf((String)entrada.location()) );
                                    logins.add(String.valueOf(entrada.login()));

                                }


                                datos.add(names);
                                datos.add(locations);
                                datos.add(logins);

                                listview2.setAdapter(new Adaptador_contributors(topcontributors.this,datos));
                            }
                        });
                    }




                }

                @Override
                public void onFailure(@NotNull ApolloException e) {
                    Log.d("ERROR:","La consulta no ha devuelto informacion");
                }

            });

        }

    }

