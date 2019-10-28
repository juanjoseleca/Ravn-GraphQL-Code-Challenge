package com.example.ravngraphqlcodechallenge;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    List<List<String>> datos;
    String nombre_usuario;

    public Adaptador(Context contexto, List<List<String>> datos, String nombre_usuario)
    {
        this.contexto=contexto;
        this.datos=datos;
        this.nombre_usuario=nombre_usuario;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_lista,null);

        TextView name = (TextView) vista.findViewById(R.id.name_id);
        TextView description = (TextView) vista.findViewById(R.id.description_id);
        TextView pr_count = (TextView) vista.findViewById(R.id.prcount_id);
        Log.d("Verificar",datos.get(0).get(position));
        String nombre_repositorio = datos.get(0).get(position);
        String descripcion_repositorio = datos.get(1).get(position);
        String contador_pullrequest = datos.get(2).get(position);
        name.setText(nombre_repositorio);
        if(descripcion_repositorio=="null")
        {
            description.setText("[sin descripción]");
        }
        else
        {
            description.setText(descripcion_repositorio);
        }

        pr_count.setText(contador_pullrequest);

        name.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v)
                {
                    Log.d("Click","elementos");
                    Intent show_contributors = new Intent(contexto,topcontributors.class);
                    show_contributors.putExtra("NOMBRE_USUARIO",nombre_usuario );
                    show_contributors.putExtra("REPOSITORIO",datos.get(0).get(position));

                    MyApolloClient.getMyApolloClient(contexto).query(Get_collaboratorsQuery.builder().midato(nombre_usuario).repository(datos.get(0).get(position)).build()).enqueue(new ApolloCall.Callback<Get_collaboratorsQuery.Data>() {


                        @Override
                        public void onResponse(@NotNull Response<Get_collaboratorsQuery.Data> response) {
                            //En caso obtengamos datos en nuestra consulta al api, se ejecutara lo siguiente:

                            if(response.data().repositoryOwner().repository().collaborators()==null)
                            {
                                Log.d("ERROR","La consulta es nula");
                                Intent show_contributors = new Intent(contexto,Cuenta_privada.class);
                                contexto.startActivity(show_contributors);


                            }
                            else
                            {

                                contexto.startActivity(show_contributors);
                            }




                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            Log.d("ERROR:","La consulta no ha devuelto informacion");
                        }

                    });

                }

        });

        return vista;
    }

    @Override
    public int getCount() {
        return datos.get(0).size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
