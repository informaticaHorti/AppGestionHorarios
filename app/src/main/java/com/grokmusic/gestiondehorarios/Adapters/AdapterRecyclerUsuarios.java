package com.grokmusic.gestiondehorarios.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.Fragments.fragmentCreaUsuario;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterRecyclerUsuarios extends RecyclerView.Adapter<AdapterRecyclerUsuarios.ViewHolder> {
    private final Context context;
    private   Activity activity;
    private final ArrayList<Usuarios> arrayList;



    public AdapterRecyclerUsuarios(Context context, ArrayList<Usuarios> arrayList,Activity activ) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activ;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_list_usuario,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.asignData(arrayList.get(position));
        holder.layout.setOnClickListener(view -> {
            DecimalFormat df = new DecimalFormat("0.00");
            int id = arrayList.get(holder.getAdapterPosition()).getId();
            String name = arrayList.get(holder.getAdapterPosition()).getNombre();
            int tarifa = arrayList.get(holder.getAdapterPosition()).getTarifa();
            double antig = arrayList.get(holder.getAdapterPosition()).getAntig();
            double irpf = arrayList.get(holder.getAdapterPosition()).getIrpf();
            double seg = arrayList.get(holder.getAdapterPosition()).getSegsoc();
            //Toast.makeText(context,df.format(antig),Toast.LENGTH_SHORT).show();
            utils.changeFragment(activity,new fragmentCreaUsuario(id,name,tarifa,df.format(antig),df.format(irpf),df.format(seg)),"Crear Usuario");
        });


    }

    @Override
    public int getItemCount() {        return arrayList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, nombre,idt,tarifa;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idUsuario);
            nombre = itemView.findViewById((R.id.nombreUsuario));
            idt = itemView.findViewById(R.id.idTarifa);
            tarifa = itemView.findViewById(R.id.nmTarifa);
            layout = itemView.findViewById(R.id.itemUsuario);
        }

        public void asignData( Usuarios usr){
            id.setText(usr.getId()+"");
            nombre.setText(usr.getNombre());
            tarifa.setText(utils.getTarifaName(context,usr.getTarifa()));
            idt.setText(usr.getTarifa()+"");
        }

    }


}
