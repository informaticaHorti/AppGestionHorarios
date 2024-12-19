package com.grokmusic.gestiondehorarios.Adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grokmusic.gestiondehorarios.Clases.MesesNomina;
import com.grokmusic.gestiondehorarios.Clases.NominaDiaria;
import com.grokmusic.gestiondehorarios.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterRecyclerNomina extends RecyclerView.Adapter<AdapterRecyclerNomina.ViewHolder> {
    private final Context context;
    private   Activity activity;
    private final ArrayList<NominaDiaria> arrayList;



    public AdapterRecyclerNomina(Context context, ArrayList<NominaDiaria> arrayList, Activity activ) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activ;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_list_nomina_mes_detalle,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.asignData(arrayList.get(position));


    }

    @Override
    public int getItemCount() {        return arrayList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView fecha,horN,horE,horF,Para,Total;


        public ViewHolder(View v) {
            super(v);
            fecha = v.findViewById(R.id.dateD);
            horN  = v.findViewById(R.id.hND);
            horE  = v.findViewById(R.id.hED);
            horF  = v.findViewById(R.id.hFD);
            Para  = v.findViewById(R.id.pD);
            Total = v.findViewById(R.id.tTD);
        }
        public void asignData( NominaDiaria m){
            DecimalFormat df = new DecimalFormat("0.00");
            fecha.setText(m.getFecha());
            horN.setText(m.getHorasN());
            horE.setText(m.getHorasE());
            horF.setText(m.getHorasF());
            Para.setText(String.valueOf(m.getParadas()));
            Total.setText(df.format(m.getTotal())+" €");
        }

    }


}
