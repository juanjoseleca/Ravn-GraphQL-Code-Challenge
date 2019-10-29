package com.example.ravngraphqlcodechallenge;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    List<List<String>> datos;
    String nombre_usuario;

    public Adaptador(Context contexto, List<List<String>> datos, String nombre_usuario)
    {
        this.contexto=contexto;
        //datos es la estructura que contiene los resultados de la consulta al API
        this.datos=datos;
        this.nombre_usuario=nombre_usuario;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_lista,null);

        //Declaramos las variables para obtener los ids---------
        TextView name = (TextView) vista.findViewById(R.id.name_id);
        TextView description = (TextView) vista.findViewById(R.id.description_id);
        TextView pr_count = (TextView) vista.findViewById(R.id.prcount_id);
        //-------------------------------------------------------

        //obtenemos los datos de cada repositorio
        String nombre_repositorio = datos.get(0).get(position);
        String descripcion_repositorio = datos.get(1).get(position);
        String contador_pullrequest = datos.get(2).get(position);

        //mostramos los datos en los 'TextView'----------
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
        //------------------------------------------------



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
