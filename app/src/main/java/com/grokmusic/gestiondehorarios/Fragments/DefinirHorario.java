package com.grokmusic.gestiondehorarios.Fragments;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.grokmusic.gestiondehorarios.Clases.HorarioDef;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.db.DbHorarioDef;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class DefinirHorario extends Fragment {

    ArrayList<HorarioDef> list = new ArrayList<HorarioDef>();
    private Button btnSave;
    private TextView entraM,saleM,entraT,saleT;
    private  int HentraM,MentraM,HsaleM,MsaleM,HentraT,MentraT,HsaleT,MsaleT;
    private SharedPreferences prefs;
    public DefinirHorario() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_horario_defecto, container, false);
        prefs = getActivity().getSharedPreferences("preferencias",MODE_PRIVATE);
        bind(view);
        //deleteDefault();
        setHorario(view);
        functions(view);
        return view;
    }
    private  void bind(View view){
        entraM = view.findViewById(R.id.HoraEntraM);
        saleM = view.findViewById(R.id.HoraSaleM);
        entraT = view.findViewById(R.id.HoraEntraT);
        saleT = view.findViewById(R.id.HoraSaleT);
        btnSave = view.findViewById(R.id.btnSaveHorarioDef);
    }
    private  void functions(View view){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        entraM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hora, minuto;
                        HentraM = hourOfDay; MentraM = minute;
                        if (hourOfDay < 10) {
                            hora = "0" + hourOfDay;
                        } else {
                            hora = String.valueOf(hourOfDay);
                        }
                        if (minute < 10) {
                            minuto = "0" + minute;
                        } else {
                            minuto = String.valueOf(minute);
                        }
                        entraM.setText(hora+":"+minuto);
                    }
                },7,0,true);
                picker.show();
            }
        });
        saleM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hora, minuto;
                        HsaleM = hourOfDay; MsaleM = minute;
                        if (hourOfDay < 10) {
                            hora = "0" + hourOfDay;
                        } else {
                            hora = String.valueOf(hourOfDay);
                        }
                        if (minute < 10) {
                            minuto = "0" + minute;
                        } else {
                            minuto = String.valueOf(minute);
                        }
                        saleM.setText(hora+":"+minuto);
                    }
                },7,0,true);
                picker.show();
            }
        });
        entraT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hora, minuto;
                        HentraT = hourOfDay; MentraT = minute;
                        if (hourOfDay < 10) {
                            hora = "0" + hourOfDay;
                        } else {
                            hora = String.valueOf(hourOfDay);
                        }
                        if (minute < 10) {
                            minuto = "0" + minute;
                        } else {
                            minuto = String.valueOf(minute);
                        }
                        entraT.setText(hora+":"+minuto);
                    }
                },7,0,true);
                picker.show();
            }
        });
        saleT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hora, minuto;
                        HsaleT = hourOfDay; MsaleT = minute;
                        if (hourOfDay < 10) {
                            hora = "0" + hourOfDay;
                        } else {
                            hora = String.valueOf(hourOfDay);
                        }
                        if (minute < 10) {
                            minuto = "0" + minute;
                        } else {
                            minuto = String.valueOf(minute);
                        }
                        saleT.setText(hora+":"+minuto);
                    }
                },7,0,true);
                picker.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setCancelable(false);
                alert.setTitle("¡ ATENCIÓN !");
                alert.setMessage("¿Seguro que quieres definir este horario por defecto?");
                alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<HorarioDef> hor = utils.getHorDef(view.getContext(),Integer.parseInt(utils.getIdUsuarioActual(prefs)));
                        DbHorarioDef dtb = new DbHorarioDef(view.getContext());
                        SQLiteDatabase db = dtb.getWritableDatabase();
                        ContentValues val = new ContentValues();
                         val.put("HentraM",HentraM);val.put("MentraM",MentraM);
                        val.put("HsaleM",HsaleM); val.put("MsaleM",MsaleM);
                        val.put("HentraT",HentraT); val.put("MentraT",MentraT); val.put("HsaleT",HsaleT); val.put("MsaleT",MsaleT);

                        if(hor!=null){
                            if(hor.size()>0 ){
                                db.update(dtb.getDatabaseName(),val,"idUsuario = "+Integer.parseInt(utils.getIdUsuarioActual(prefs))+"",null);
                            }else{
                                val.put("idUsuario",Integer.parseInt(utils.getIdUsuarioActual(prefs)));
                                db.insert(dtb.getDatabaseName(),null,val);
                                if(db != null){
                                    utils.changeFragment(getActivity(),new GestionHorarios(),"Gestion Horarios");
                                    Toast.makeText(getContext(),"Horario actualizado correctamente",Toast.LENGTH_SHORT).show();
                                }else{Toast.makeText(getContext(),"Error al grabar los datos",Toast.LENGTH_SHORT).show();}
                            }
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
        });
    }
    private  void setHorario(View view){
        //ArrayList<HorarioDef> horh = utils.getHorDef(view.getContext());
        ArrayList<HorarioDef> hor = utils.getHorDef(view.getContext(),Integer.parseInt(utils.getIdUsuarioActual(prefs)));
        if(hor != null){
            if(hor.size()>0){
                HorarioDef H = hor.get(0);
                HentraM=H.getHentraM(); MentraM = H.getMentraM(); HsaleM=H.getHsaleM(); MsaleM=H.getMsaleM();
                HentraT=H.getHentraT(); MentraT = H.getMentraT(); HsaleT=H.getHsaleT(); MsaleT=H.getMsaleT();
                entraM.setText(parseHora(H.getHentraM())+":"+parseHora(H.getMentraM()));
                saleM.setText(parseHora(H.getHsaleM())+":"+parseHora(H.getMsaleM()));
                entraT.setText(parseHora(H.getHentraT())+":"+parseHora(H.getMentraT()));
                saleT.setText(parseHora(H.getHsaleT())+":"+parseHora(H.getMsaleT()));

            }else{
                Toast.makeText(getContext(),"No Existe Horario Por defecto ",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String parseHora(int num){
        String valor = "";
        if(num>10){valor=""+num;}else{valor="0"+num;}
        return valor;
    }
    private void deleteDefault(){
        DbHorarioDef dtb = new DbHorarioDef(getContext());
        SQLiteDatabase db = dtb.getWritableDatabase();
        ContentValues values = new ContentValues();
        String idu = utils.getIdUsuarioActual(prefs);

        db.delete(dtb.getDatabaseName(), "idUsuario = "+idu+" ",null);
    }

}