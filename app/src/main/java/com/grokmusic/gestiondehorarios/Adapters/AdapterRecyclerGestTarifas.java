package com.grokmusic.gestiondehorarios.Adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.grokmusic.gestiondehorarios.Clases.Tarifas;
import com.grokmusic.gestiondehorarios.Fragments.fragmentCreaTarifa;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.util.ArrayList;

public class AdapterRecyclerGestTarifas extends RecyclerView.Adapter<AdapterRecyclerGestTarifas.ViewHolder> {
    private final Context context;
    private final ArrayList<Tarifas> arrayList;



    public AdapterRecyclerGestTarifas(Context context, ArrayList<Tarifas> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_list_tarifa,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.asignData(arrayList.get(position));
        holder.layout.setOnClickListener(view -> {
            int id = arrayList.get(holder.getAdapterPosition()).getIdTarifa();
           // Toast.makeText(context,id+" ",Toast.LENGTH_LONG).show();
            utils.changeFragment((Activity)context,new fragmentCreaTarifa(id),"Edita Tarifa" );
            //utils.changeFragment(context,new fragmentCreaTarifa(),"Edita Tarifa");
        });


    }

    @Override
    public int getItemCount() {        return arrayList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, nombre;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idTarifa);
            nombre = itemView.findViewById((R.id.nombreTarifa));
            layout = itemView.findViewById(R.id.itemTarifa);
        }

        public void asignData( Tarifas tar){
            id.setText(String.valueOf(tar.getIdTarifa()));
            nombre.setText(tar.getTarifa());
        }

    }


}
