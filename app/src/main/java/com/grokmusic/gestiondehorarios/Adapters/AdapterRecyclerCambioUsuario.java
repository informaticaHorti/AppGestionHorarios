package com.grokmusic.gestiondehorarios.Adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.grokmusic.gestiondehorarios.Clases.HorarioDef;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.Fragments.DefinirHorario;
import com.grokmusic.gestiondehorarios.Fragments.GestionHorarios;
import com.grokmusic.gestiondehorarios.Fragments.fragmentCreaUsuario;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.util.ArrayList;

public class AdapterRecyclerCambioUsuario extends RecyclerView.Adapter<AdapterRecyclerCambioUsuario.ViewHolder> {
    private final Context context;
    private   Activity activity;
    private final ArrayList<Usuarios> arrayList;
    private SharedPreferences prefs;



    public AdapterRecyclerCambioUsuario(Context context, ArrayList<Usuarios> arrayList, Activity activ,SharedPreferences preferences) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activ;
        this.prefs = preferences;
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
            int id = arrayList.get(holder.getAdapterPosition()).getId();
            String name = arrayList.get(holder.getAdapterPosition()).getNombre();
            int taf = arrayList.get(holder.getAdapterPosition()).getTarifa();
            utils.setPreferences(prefs,"idUsuario",id+"");
            utils.setPreferences(prefs,"usuario",name);
            utils.setPreferences(prefs,"tarifa",taf+"");
            ArrayList<HorarioDef> deflt = utils.getHorDef(context,id);
            if(deflt.size()>0){
                utils.changeFragment(activity,new GestionHorarios(),"Gestion de Horarios");}else{
                AlertDialog.Builder dft = new AlertDialog.Builder(context);
                dft.setCancelable(false); dft.setTitle("Horarios por Defecto");
                dft.setMessage("No existe ningún horario definido por defecto.\r\n¿vamos a crearlo?");
                dft.setPositiveButton("Adelante", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        utils.changeFragment(activity,new DefinirHorario(),"Horario por defecto");
                    }
                });
                dft.show();
            }

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
