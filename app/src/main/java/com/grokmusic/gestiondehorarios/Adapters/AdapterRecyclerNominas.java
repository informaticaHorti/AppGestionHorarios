package com.grokmusic.gestiondehorarios.Adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.grokmusic.gestiondehorarios.Clases.MesesNomina;
import com.grokmusic.gestiondehorarios.Clases.NominaDiaria;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utDb;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterRecyclerNominas extends RecyclerView.Adapter<AdapterRecyclerNominas.ViewHolder> {
    private final Context context;
    private   Activity activity;
    private final ArrayList<MesesNomina> arrayList;
    private String usr,nameUsr;



    public AdapterRecyclerNominas(Context context, ArrayList<MesesNomina> arrayList, Activity activ,String usr,String nameUsr) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activ;
        this.usr = usr;
        this.nameUsr = nameUsr;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_list_nomina_mes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.asignData(arrayList.get(position));
        holder.layout.setOnClickListener(view -> {
            DecimalFormat df = new DecimalFormat("0.00");
            AlertDialog.Builder alert = new AlertDialog.Builder(context,R.style.my_dialog);
          //  alert.setTitle(arrayList.get(holder.getAdapterPosition()).getNameMes()+" "+arrayList.get(holder.getAdapterPosition()).getYear());
            View dialogo = LayoutInflater.from(alert.getContext()).inflate(R.layout.layout_recycler_nom_alert,null);
            int mes = arrayList.get(holder.getAdapterPosition()).getMes();
            int year = arrayList.get(holder.getAdapterPosition()).getYear();
            RecyclerView recycler = dialogo.findViewById(R.id.recycler_alert);
            TextView titulo = dialogo.findViewById(R.id.titAlert);
            Button exportar = dialogo.findViewById(R.id.exportPDF);
            Button ok = dialogo.findViewById(R.id.okalert);
            titulo.setText(arrayList.get(holder.getAdapterPosition()).getNameMes()+" "+arrayList.get(holder.getAdapterPosition()).getYear());
            recycler.setLayoutManager(new LinearLayoutManager(alert.getContext(), LinearLayoutManager.VERTICAL, false));

            ArrayList<NominaDiaria> nominaDiaria = utDb.getNominaMes(context,usr,arrayList.get(holder.getAdapterPosition()).getYear(),arrayList.get(holder.getAdapterPosition()).getMes());
            AdapterRecyclerNomina adapter = new AdapterRecyclerNomina(context,nominaDiaria,activity);
            recycler.setAdapter(adapter);
            activity.registerForContextMenu(recycler);
            alert.setView(dialogo);
            AlertDialog alertDialog = alert.create();
            exportar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    utils.crearPDF(exportar.getContext(),usr,nameUsr,year,mes);
                    alertDialog.dismiss();
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            //AlertDialog dialog = alert.create();
            //dialog.getWindow().setLayout(700, 1200);
            //dialog.show();

        });


    }

    @Override
    public int getItemCount() {        return arrayList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView year, idMes,mes,dias,importe;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            layout  = itemView.findViewById(R.id.itemNomina);
            year    = itemView.findViewById(R.id.yearN);
            idMes   = itemView.findViewById(R.id.idmesN);
            mes     = itemView.findViewById(R.id.mesN);
            dias    = itemView.findViewById(R.id.diasN);
            importe = itemView.findViewById(R.id.importeN);
        }

        public void asignData( MesesNomina m){
            year.setText(String.valueOf(m.getYear()));
            idMes.setText(String.valueOf(m.getMes()));
            mes.setText(m.getNameMes());
            dias.setText(String.valueOf(m.getDiasTrabajados()));
            importe.setText(String.valueOf(m.getImporteMes()));
        }

    }


}
