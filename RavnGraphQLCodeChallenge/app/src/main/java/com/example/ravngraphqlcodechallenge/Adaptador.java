package com.example.ravngraphqlcodechallenge;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        /*
        Iterator<List<String>> iterador_lista = datos.iterator();
        int i=0;
        while (iterador_lista.hasNext()) {
            Iterator<String> iterador_sub = iterador_lista.next().iterator();
            while (iterador_sub.hasNext())
            {
                if(i==0)
                {

                }
            }
            i++;

        }
        */
        Log.d("Verificar",datos.get(0).get(position));
        name.setText(datos.get(0).get(position));
        description.setText(datos.get(1).get(position));
        pr_count.setText(datos.get(2).get(position));

        name.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v)
                {
                    Log.d("Click","elementos");
                    Intent show_contributors = new Intent(contexto,topcontributors.class);
                    show_contributors.putExtra("NOMBRE_USUARIO",nombre_usuario );
                    show_contributors.putExtra("REPOSITORIO",datos.get(0).get(position));
                    contexto.startActivity(show_contributors);
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
