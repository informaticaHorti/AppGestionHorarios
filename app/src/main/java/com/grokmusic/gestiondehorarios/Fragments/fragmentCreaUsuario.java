package com.grokmusic.gestiondehorarios.Fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grokmusic.gestiondehorarios.Adapters.AdapterRecyclerTarifas;
import com.grokmusic.gestiondehorarios.Adapters.AdapterRecyclerUsuarios;
import com.grokmusic.gestiondehorarios.Clases.Tarifas;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.db.DbHorarios;
import com.grokmusic.gestiondehorarios.db.DbUsuarios;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class fragmentCreaUsuario extends Fragment {

    private  TextView id;
    private EditText nombre,antig,irpf,segSoc;
    private TextView idTarifa,nmTarifa;
    ArrayList<Usuarios> list = new ArrayList<Usuarios>();
    private AdapterRecyclerUsuarios adapter;
    private String cdUsuario="0",nmUsuario="",tarUsuario="0",antUsuario="0.00",impIrfp="0.00",impSeg="0.00";
    private  double ifrpUsr=0.00,segsocUsr=0.00, antgUsr=0.00;
    private Button btnSave,btnDel;
    private SharedPreferences prefs;
    public fragmentCreaUsuario() {
        // Required empty public constructor
    }
    public fragmentCreaUsuario(int cd,String name, int tarifa,String antg,String Irpf,String SegSoc) {
        // Required empty public constructor
        if(cd >0){cdUsuario =  String.valueOf(cd);}else{cdUsuario =  "";}
        nmUsuario = name;
        tarUsuario = String.valueOf(tarifa);
        antUsuario = antg; antgUsr = Double.parseDouble(antUsuario.replace(",","."));
        impIrfp = Irpf; ifrpUsr = Double.parseDouble(impIrfp.replace(",","."));
        impSeg = SegSoc; segsocUsr = Double.parseDouble(impSeg.replace(",","."));



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crea_usuario, container, false);
        prefs =  getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        DecimalFormat df = new DecimalFormat("0.00");
        bind(view);
        functions(view);
        id.setText(cdUsuario); nombre.setText(nmUsuario); idTarifa.setText(tarUsuario);
        nmTarifa.setText(utils.getTarifaName(getContext(),Integer.parseInt(tarUsuario)));
        antig.setText(df.format(antgUsr).replace(",",".")); irpf.setText(df.format(ifrpUsr).replace(",","."));
        segSoc.setText(df.format(segsocUsr).replace(",","."));
        return view;
    }
    private  void bind(View view){
        id = view.findViewById(R.id.cdUsr);
        nombre = view.findViewById(R.id.nombreUsr);
        idTarifa = view.findViewById(R.id.idTarifa);
        nmTarifa = view.findViewById(R.id.nmTarifa);
        btnSave = view.findViewById(R.id.btnSaveUsuario);
        btnDel = view.findViewById(R.id.btnDelUsuario);
        antig = view.findViewById(R.id.antUsr);
        irpf = view.findViewById(R.id.irpf);
        segSoc=view.findViewById(R.id.segsoc);
    }

    private  void delUsr(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("¡ ATENCION !");
        alert.setMessage("¿Seguro que quieres borrar éste usuario y las jornadas ya registradas?\r\nUna vez borrado no se podrá recuperar");
        alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbUsuarios dtb = new DbUsuarios(view.getContext());
                DbHorarios dth = new DbHorarios(view.getContext());
                SQLiteDatabase db = dtb.getWritableDatabase();
                SQLiteDatabase dh = dth.getReadableDatabase();
                String idu = id.getText().toString();
                db.delete(dtb.getDatabaseName(), "id = "+idu,null);
                dh.delete(dth.getDatabaseName(),"idUsuario="+idu,null);
                if(db != null && dh != null){
                    utils.changeFragment(getActivity(),new GestionUsuarios(),"Gestión de Usuarios");
                }
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }
    private  void functions(View view){
        nmTarifa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                ArrayList<Tarifas> tarifas =  utils.getTarifas(view.getContext());
                if(tarifas.size()>0){
                    View dialogo = LayoutInflater.from(alert.getContext()).inflate(R.layout.layout_recycler_alert,null);
                    RecyclerView recycler = dialogo.findViewById(R.id.recycler_alert);
                    recycler.setLayoutManager(new LinearLayoutManager(alert.getContext(), LinearLayoutManager.VERTICAL, false));
                    alert.setTitle("SELECCIONA TARIFA");
                    alert.setView(dialogo);
                    AlertDialog dialog = alert.create();
                    AdapterRecyclerTarifas adapter = new AdapterRecyclerTarifas(view.getContext(), tarifas,dialog,idTarifa,nmTarifa);
                    recycler.setAdapter(adapter);
                    registerForContextMenu(recycler);
                    dialog.show();
                }else{
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());
                    dialogo.setCancelable(false);
                    View vista = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_toast_error,null);
                    TextView tit = vista.findViewById(R.id.tituloError); TextView txt = vista.findViewById(R.id.textoError);
                    tit.setText("ERROR EN TARIFAS"); txt.setText("No Hay ninguna tarifa registrada.\r\nPor favor, crea una tarifa antes de continuar.");
                    dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            utils.changeFragment(getActivity(),new fragmentCreaTarifa(),"Crear Tarifa");
                        }
                    });
                    dialogo.setView(vista);
                    dialogo.show();
                }


            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().isEmpty()){
                    utils.checkInBlank(getContext(),"El Nombre del usuario no puede estar en blanco");
                    return;
                }
                if(!idTarifa.getText().toString().isEmpty()){
                    if(Double.parseDouble(idTarifa.getText().toString())==0){
                        utils.checkInBlank(getContext(),"No has seleccionado ninguna tarifa\r\nPor favor, revísalo");
                        return;
                    }
                }
                if(!antig.getText().toString().isEmpty()){
                    if(Double.parseDouble(antig.getText().toString())>100){
                        utils.checkInBlank(getContext(),"¿"+antig.getText().toString()+"€ más por hora?\r\nEl valor de la antigüedad por hora es excesivo\r\nPor favor, revísalo");
                        return;
                    }
                }
                if(!irpf.getText().toString().isEmpty()){
                    if(Double.parseDouble(irpf.getText().toString())>=100){
                        utils.checkInBlank(getContext(),"¿"+irpf.getText().toString()+"% del salario?\r\nEl valor del I.R.P.F excesivo\r\nPor favor, revísalo");
                        return;
                    }
                }
                if(!segSoc.getText().toString().isEmpty()){
                    if(Double.parseDouble(segSoc.getText().toString())>=100){
                        utils.checkInBlank(getContext(),"¿"+segSoc.getText().toString()+"% del salario?\r\nEl valor de la aportación a la Seguridad Social es excesivo\r\nPor favor, revísalo");
                        return;
                    }
                }
                if(!irpf.getText().toString().isEmpty() && !segSoc.getText().toString().isEmpty()){
                    double tot = Double.parseDouble(irpf.getText().toString())+Double.parseDouble(segSoc.getText().toString());
                    if(tot >=100){
                        utils.checkInBlank(getContext(),"¿"+tot+"% del salario?\r\nEl valor para las aportaciones de impuestos es excesivo\r\nPor favor, revísalo");
                        return;
                    }
                }
                if(!irpf.getText().toString().isEmpty() && !segSoc.getText().toString().isEmpty()){
                    double tot = Double.parseDouble(irpf.getText().toString())+Double.parseDouble(segSoc.getText().toString());
                    if(tot == 0){
                        utils.checkInBlank(getContext(),"¿Solo "+tot+"% del salario?\r\nEl valor para las aportaciones de impuestos es 0€\r\nPor favor, revísalo");
                        return;
                    }
                }
                saveUsr(view);
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString() !="0" && !id.getText().toString().isEmpty()){delUsr(view);}else{
                    utils.checkInBlank(getContext(),"No hay Usuario que borrar");
                }

            }
        });
    }
    private void saveUsr(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("¡ ATENCIÓN !");
        String datos = "Nombre: "+nombre.getText()+"\r\nTarifa: "+nmTarifa.getText()+"\r\nAntigüedad: "+antig.getText()+"\r\nI.R.P.F.: "+irpf.getText()+
                " %,  Seg.Soc.: "+segSoc.getText()+" %";
        alert.setMessage("¿Seguro que quieres grabar los datos:\r\n"+datos+"?");
        alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DbUsuarios dtb = new DbUsuarios(view.getContext());
                SQLiteDatabase db = dtb.getWritableDatabase();
                ContentValues values = new ContentValues();
                String nm = nombre.getText().toString();
                int idu = 0;
                int idt=0;
                double ant=0.00;
                double Irpf = 0.00;
                double segsoc = 0.00;
                try{

                    idt = Integer.parseInt(idTarifa.getText().toString());}catch (Exception ex){
                    Toast.makeText(view.getContext(),"La Tarifa no puede estar en blanco",Toast.LENGTH_SHORT).show();
                }
                try{
                    ant = Double.parseDouble(antig.getText().toString().replace(",","."));
                    Irpf = Double.parseDouble(irpf.getText().toString().replace(",","."));
                    segsoc = Double.parseDouble(segSoc.getText().toString().replace(",","."));
                }catch (Exception ex){
                }
                try{
                    idu = Integer.parseInt(id.getText().toString());

                }catch (Exception ex){
                }
                if(idt>0 && nm.length()>0){
                    String antig = String.valueOf(ant).replace(",",".");
                    String irp = String.valueOf(Irpf).replace(",",".");
                    String ss = String.valueOf(segsoc).replace(",",".");
                    values.put("nombre",nm); values.put("tarifa",idt); values.put("antig",antig.replace(",","."));
                    values.put("irpf",irp.replace(",",".")); values.put("segSoc",ss.replace(",","."));
                }
                ArrayList<Usuarios> lst = utils.getUsuario(view.getContext(),idu);
                if(lst.size()>0){
                    db.update(dtb.getDatabaseName(),values,"id = "+idu,null);
                    if(db != null){
                        utils.changeFragment(getActivity(), new CambioUsuario(),"Selecciona Usuario");
                    }
                }
                else{
                    db.insert(dtb.getDatabaseName(),null,values);
                    if(db != null){
                        utils.changeFragment(getActivity(), new CambioUsuario(),"Selecciona Usuario");
                    }
                }

                if(db != null){
                    utils.changeFragment(getActivity(), new CambioUsuario(),"Seleccionz" +
                            " de Usuarios");
                }else{
                }
                db.close();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }
}