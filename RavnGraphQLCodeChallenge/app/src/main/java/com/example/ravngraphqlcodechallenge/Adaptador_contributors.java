package com.example.ravngraphqlcodechallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

public class Adaptador_contributors extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    List<List<String>> datos;
    public Adaptador_contributors(Context contexto, List<List<String>>datos)
    {
        this.contexto=contexto;
        this.datos = datos;
        inflater= (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_contributors,null);
        TextView name = (TextView) vista.findViewById(R.id.name_id);
        TextView location = (TextView) vista.findViewById(R.id.location_id);
        TextView login = (TextView) vista.findViewById(R.id.login_id);
        ImageView imagen_usuario = (ImageView) vista.findViewById(R.id.imagen_id);

        //TextView commits_count = (TextView) vista.findViewById(R.id.login_id);
        name.setText(datos.get(0).get(position));
        location.setText(datos.get(1).get(position));
        login.setText(datos.get(2).get(position));


        Picasso.get().load("https://avatars1.githubusercontent.com/u/8674244?v=4").into(imagen_usuario);

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
