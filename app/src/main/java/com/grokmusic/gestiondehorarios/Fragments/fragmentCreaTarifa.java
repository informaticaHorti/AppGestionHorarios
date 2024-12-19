package com.grokmusic.gestiondehorarios.Fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.grokmusic.gestiondehorarios.Clases.Tarifas;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.db.DbTarifas;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class fragmentCreaTarifa extends Fragment {

    private  TextView id;
    private EditText nombre;
    private EditText precNormal,precExtra,precFiesta,precDescanso,precTransporte,limite;
    private Button btnSave,btnDel;
    private  int idTar = 0;
    public fragmentCreaTarifa() {
        // Required empty public constructor
    }
    public fragmentCreaTarifa(int idtarifa) {
        System.out.println("IdTarifa "+idtarifa);
        idTar = idtarifa;
      //  Toast.makeText(getContext(),"Id Tarifa "+idT,Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crea_tarifa, container, false);
        bind(view);
        functions(view);
        if(idTar>0){ArrayList<Tarifas> tarifas = utils.getTarifa(getContext(),idTar);  Tarifas t = tarifas.get(0);   setTarifa(t);}
        return view;
    }
    private  void bind(View view){
        id = view.findViewById(R.id.cdTarifa);
        nombre = view.findViewById(R.id.nombreTarifa);
        precNormal = view.findViewById(R.id.preNormal);
        precExtra = view.findViewById(R.id.preExtra);
        precFiesta = view.findViewById(R.id.preFiesta);
        precDescanso = view.findViewById(R.id.preDescanso);
        precTransporte=view.findViewById(R.id.preTransporte);
        limite = view.findViewById(R.id.limiteHoras);
        btnSave = view.findViewById(R.id.btnSaveTarifa);
        btnDel = view.findViewById(R.id.btnDelTarifa);
    }
    private  void delTarifa(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("¡ Atención !");
        alert.setMessage("¿Seguro que queires borrar ésta tarifa?");
        alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbTarifas dtb = new DbTarifas(view.getContext());
                SQLiteDatabase db = dtb.getWritableDatabase();
                ContentValues values = new ContentValues();
                db.delete(dtb.getDatabaseName(),"id="+idTar,null);
                if(db !=null){
                    Toast.makeText(getContext(),"Tarifa Borrada Correctamente",Toast.LENGTH_SHORT).show();
                    utils.changeFragment(getActivity(),new GestionTarifas(),"Gestion de Tarifas");
                }
            }
        });
        alert.show();
    }

    private  void functions(View view){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombre.getText().toString().isEmpty()){
                    utils.checkInBlank(getContext(),"El Nombre de Tarifa no puede estar en blanco ");
                    return;
                }
                if(precNormal.getText().toString().isEmpty()){
                    utils.checkInBlank(getContext(),"No as indicado valor para las Horas Normales ");
                    return;
                }else{
                    if(Double.parseDouble(precNormal.getText().toString())>60){
                        utils.checkInBlank(getContext(),"El precio hora es demasiado alto\r\nPor favor, revisalo");
                        return;
                    }
                }
                if(precExtra.getText().toString().isEmpty()){
                    utils.checkInBlank(getContext(),"No as indicado valor para las Horas Extra");
                    return;
                }else{
                    if(Double.parseDouble(precNormal.getText().toString())>120){
                        utils.checkInBlank(getContext(),"El precio hora es demasiado alto\r\nPor favor, revisalo");
                        return;
                    }
                }
                if(precFiesta.getText().toString().isEmpty()){
                    utils.checkInBlank(getContext(),"No as indicado valor para las Horas Festivas");
                    return;
                }else{
                    if(Double.parseDouble(precNormal.getText().toString())>180){
                        utils.checkInBlank(getContext(),"El precio hora es demasiado alto\r\nPor favor, revisalo");
                        return;
                    }
                }
                if(precDescanso.getText().toString().isEmpty()){
                    utils.checkInBlank(getContext(),"No as indicado valor para los Descansos");
                    return;
                }else{
                    if(Double.parseDouble(precNormal.getText().toString())>60){
                        utils.checkInBlank(getContext(),"El precio hora es demasiado alto\r\nPor favor, revisalo");
                        return;
                    }
                }
                if(precTransporte.getText().toString().isEmpty()){
                    utils.checkInBlank(getContext(),"No as indicado valor para el Plus Transporte");
                    return;
                }else{
                    if(Double.parseDouble(precTransporte.getText().toString())>120){
                        utils.checkInBlank(getContext(),"El plus transporte diario demasiado alto\r\nPor favor, revisalo");
                        return;
                    }
                }
                if(limite.getText().toString().isEmpty()){
                    utils.checkInBlank(getContext(),"No as indicado valor para el Limite de Horas Extra");
                    return;
                }else{
                    if(Double.parseDouble(limite.getText().toString())>16){
                        utils.checkInBlank(getContext(),"El limite para empezar a cobrar extras es demasiado alto\r\nPor favor, revisalo");
                        return;
                    }
                    if(Double.parseDouble(limite.getText().toString())<5){
                        utils.checkInBlank(getContext(),"El limite para empezar a cobrar extras es demasiado bajo\r\nPor favor, revisalo");
                        return;
                    }
                }

                if(!nombre.getText().toString().isEmpty() && !precNormal.getText().toString().isEmpty() && !precExtra.getText().toString().isEmpty()
                        && ! precFiesta.getText().toString().isEmpty()){
                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("¡ ATENCION !");
                    alert.setMessage("¿Seguro que quieres guardar ésta tarifa?");
                    alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DbTarifas dtb = new DbTarifas(view.getContext());
                            SQLiteDatabase db = dtb.getWritableDatabase();
                            ContentValues values = new ContentValues();

                            values.put("tarifa",nombre.getText().toString()); values.put("horaNormal",Double.parseDouble(precNormal.getText().toString().replace(",",".")));
                            values.put("horaExtra",Double.parseDouble(precExtra.getText().toString().replace(",",".")));values.put("horaFiesta",Double.parseDouble(precFiesta.getText().toString().replace(",",".")));
                            values.put("precioDescanso",Double.parseDouble(precDescanso.getText().toString().replace(",",".")));
                            values.put("plusTransporte",Double.parseDouble(precTransporte.getText().toString().replace(",",".")));
                            values.put("limiteExtra",Double.parseDouble(limite.getText().toString().replace(",",".")));

                            if(idTar>0){

                                db.update(dtb.getDatabaseName(),values,"id="+idTar,null);
                            }else{
                                db.insert(dtb.getDatabaseName(),null,values);
                            }

                            if(db != null){
                                System.out.println("Base Creada correctamente");
                                Toast.makeText(getContext(),"Tarifa Creada Correctamente",Toast.LENGTH_LONG).show();
                                ArrayList<Usuarios> usuarios = utils.getUsuarios(getContext());
                                if(usuarios.size()>0){utils.changeFragment(getActivity(),new GestionTarifas(),"Gestion de Tarifas");}else{
                                    utils.addUsuario(getActivity());
                                }

                            }else{
                                System.out.println("No se ha podido crear la base de datos");
                            }
                            db.close();
                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // utils.changeFragment(getActivity(),new GestionTarifas());
                        }
                    });
                    alert.show();
                }else{
                    utils.checkInBlank(getContext(),"No puede haber datos en blanco");
                }


            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if(nombre.getText().toString().isEmpty()){
                        utils.checkInBlank(getContext(),"NO hay datos que eliminar");
                    }else{
                        delTarifa(view);
                    }
                }

            }
        });
    }
    private  void saveTarifa(){

    }
    private void setTarifa(Tarifas t){
        DecimalFormat df = new DecimalFormat("0.0000");
        id.setText(String.valueOf(idTar)); nombre.setText(t.getTarifa());
        precNormal.setText(df.format(t.getHoraNormal()).replace(",","."));
        precExtra.setText(df.format(t.getHoraExtra()).replace(",","."));
        precFiesta.setText(df.format(t.getHoraFestiva()).replace(",","."));
        precDescanso.setText(df.format(t.getPrecioDescanso()).replace(",","."));
        precTransporte.setText(df.format(t.getPlusTransporte()).replace(",","."));
        limite.setText(df.format(t.getLimite()).replace(",","."));
    }
}