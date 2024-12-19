package com.grokmusic.gestiondehorarios.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grokmusic.gestiondehorarios.Clases.Tarifas;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.R;

import java.util.ArrayList;

public class AdapterRecyclerTarifas extends RecyclerView.Adapter<AdapterRecyclerTarifas.ViewHolder> {
    private final Context context;
    private final ArrayList<Tarifas> arrayList;
    private AlertDialog dialog;
    private TextView txtTarifa,txtnmTarifa;



    public AdapterRecyclerTarifas(Context context, ArrayList<Tarifas> arrayList,AlertDialog alert,TextView id,TextView nm) {
        this.context = context;
        this.arrayList = arrayList;
        this.dialog = alert;
        this.txtTarifa = id;
        this.txtnmTarifa = nm;
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
            txtTarifa.setText(String.valueOf(arrayList.get(holder.getAdapterPosition()).getIdTarifa()));
            txtnmTarifa.setText(arrayList.get(holder.getAdapterPosition()).getTarifa());
            dialog.dismiss();
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
