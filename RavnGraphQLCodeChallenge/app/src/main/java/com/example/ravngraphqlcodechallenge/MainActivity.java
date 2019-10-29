package com.example.ravngraphqlcodechallenge;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.api.cache.http.HttpCachePolicy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaramos las variables para obtener los ids---------

        EditText search_editText = (EditText) findViewById(R.id.search_editText); //id del buscador
        ListView listview2=(ListView) findViewById(R.id.list_repositorios); //id del listado

        //-------------------------------------------------------
        BottomNavigationView menu_navegacion = findViewById(R.id.navegador_id);
        menu_navegacion.setOnNavigationItemSelectedListener(navListener);

        //Esta funcion detecta cuando apretamos una tecla:

        search_editText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //Esta función se activa cuando apretamos la tecla 'enter'

                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String content = search_editText.getText().toString();

                    //Realizamos la consulta mediante apollo al api de Github

                    MyApolloClient.getMyApolloClient(MainActivity.this).query(MiRepoQuery.builder().midato(content).build()).httpCachePolicy(HttpCachePolicy.NETWORK_FIRST).enqueue(new ApolloCall.Callback<MiRepoQuery.Data>() {

                        @Override
                        public void onResponse(@NotNull Response<MiRepoQuery.Data> response) {
                            if(response.data().repositoryOwner()==null)
                            {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this,"No existe un usuario con dicho nombre",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else {
                                //En caso obtengamos datos en nuestra consulta al api se ejecutara lo siguiente
                                //En el hilo principal

                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Es necesario ejecutar las siguientes instrucciones en el hilo principal
                                        //para que no exista una sobrecarga

                                        //obtenemos los datos de consulta, llamando a la siguiente funcion
                                        List<List<String>>datos = obtener_datos_de_consulta(response);

                                        //Enviamos nuestra variable 'datos' y ejecutamos el siguiente 'Adapter'

                                        listview2.setAdapter(new Adaptador(MainActivity.this, datos, content));

                                        //Detectamos cuando exista un clic en algun elemento

                                        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                            @Override
                                            public void onItemClick(AdapterView<?>parent,View view,int position,long id)
                                            {
                                                Log.d("ACTIVIDAD","clic en item");
                                                Intent show_contributors = new Intent(MainActivity.this,topcontributors.class);
                                                show_contributors.putExtra("NOMBRE_USUARIO",search_editText.getText().toString() );
                                                show_contributors.putExtra("REPOSITORIO",datos.get(0).get(position));
                                                MyApolloClient.getMyApolloClient(MainActivity.this).query(Get_collaboratorsQuery.builder().midato(search_editText.getText().toString()).repository(datos.get(0).get(position)).build()).enqueue(new ApolloCall.Callback<Get_collaboratorsQuery.Data>() {
                                                    @Override
                                                    public void onResponse(@NotNull Response<Get_collaboratorsQuery.Data> response) {
                                                        //En caso obtengamos datos en nuestra consulta al api, se ejecutara lo siguiente:

                                                        if(response.data().repositoryOwner().repository().collaborators()==null)
                                                        {
                                                            Log.d("ERROR","La consulta es nula");
                                                            Intent show_contributors = new Intent(MainActivity.this,Cuenta_privada.class);
                                                            MainActivity.this.startActivity(show_contributors);
                                                        }
                                                        else
                                                        {
                                                            MainActivity.this.startActivity(show_contributors);
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(@NotNull ApolloException e) {
                                                        Log.d("ERROR:","La consulta no ha devuelto informacion");
                                                    }
                                                });

                                            }

                                        });

                                    }
                                });
                            }


                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            Log.d("ERROR:","La consulta no ha devuelto informacion2");
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Es necesario ejecutar las siguientes instrucciones en el hilo principal
                                    //para que no exista una sobrecarga
                                    Toast.makeText(MainActivity.this,"La clave para consultar al API no es correcta",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    });


                    return true;
                }
                return false;
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
                            Log.d("ACTIVIDAD","Se hizo click en action search");
                            break;
                        case R.id.action_key:
                            Log.d("ACTIVIDAD","Se hizo click en action key");
                            Intent abrir_actividad_api_key = new Intent(MainActivity.this,api_key.class);
                            MainActivity.this.startActivity(abrir_actividad_api_key);
                            break;

                    }

                    return true;
                }
            };
    public List<List<String>> obtener_datos_de_consulta(Response<MiRepoQuery.Data> response)
    {
        //Creamos la estructura datos que contiene la informacion de la consulta
        List<List<String>> datos = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<String> pr_counts = new ArrayList<>();

        //iteramos el 'Query' que obtuvimos mediante Apollo y GraphQl, y guardamos los datos
        //en las listas (List<String>)

        for (MiRepoQuery.Node entrada : response.data().repositoryOwner().repositories().nodes()) {
            names.add(String.valueOf((String) entrada.name()));
            descriptions.add(String.valueOf((String) entrada.description()));
            pr_counts.add(String.valueOf(entrada.pullRequests().totalCount()));
        }

        //Insertamos nuestras listas en la estructura 'datos'
        datos.add(names);
        datos.add(descriptions);
        datos.add(pr_counts);
        return  datos;
    }

}

