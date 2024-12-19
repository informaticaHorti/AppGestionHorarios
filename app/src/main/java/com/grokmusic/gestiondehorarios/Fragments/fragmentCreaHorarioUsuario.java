package com.grokmusic.gestiondehorarios.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.grokmusic.gestiondehorarios.Clases.Horario;
import com.grokmusic.gestiondehorarios.Clases.HorarioDef;
import com.grokmusic.gestiondehorarios.Clases.Tarifas;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.db.DbHorarios;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class fragmentCreaHorarioUsuario extends Fragment{

    private TextView fecha,entraM, saleM,paraM,entraT,saleT,paraT;
    private CheckBox festivo;
    private  double totalN = 0;
    private double limiteExtra = 0;
    private TextView horasTotales,horN,horE,horF,desc,irpf,sS,transp;
    private  TextView valorHN,valorHE,valorHF,valorDS,valorSub;
    private TextView valorTransp,valorIrpf,valorSs,valorDia;
    private String fech;
    private Button btnSave,btnDel,btnCalcular;
    private SharedPreferences prefs;
    private double precN,precE,precF,precD,preAnt,preIrpf,preSegSoc,precTransp;
    private double hN=0,EX=0,FI=0,P=0;
    private  double SUBT=0, TOT=0, THOR =0;

    private int HentraM=7,MentraM=0,HsaleM=14,MsaleM=0,HentraT=15,MentraT=0,HsaleT=20,MsaleT=0;
    public fragmentCreaHorarioUsuario() {
        // Required empty public constructor
    }
    public fragmentCreaHorarioUsuario(String date) {
        //fecha.setText(date);
        fech = date;
    }
    public fragmentCreaHorarioUsuario(int cd, String name, int tarifa,String date) {

        fech = date;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crea_horario_usuario, container, false);
        bind(view);
        DecimalFormat df = new DecimalFormat("0.00");
        prefs =  getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        if(!utils.getIdTarifaActual(prefs).isEmpty()){
            ArrayList<Tarifas> tarifas = utils.getTarifa(getContext(),Integer.parseInt(utils.getIdTarifaActual(prefs)));
            ArrayList<Usuarios> usuarios = utils.getUsuario(getContext(),Integer.parseInt(utils.getIdUsuarioActual(prefs)));
            fecha.setText(fech);
            if(tarifas.size()>0){
                precN = tarifas.get(0).getHoraNormal();
                precE = tarifas.get(0).getHoraExtra();
                precF = tarifas.get(0).getHoraFestiva();
                precD = tarifas.get(0).getPrecioDescanso();
                preAnt = usuarios.get(0).getAntig();
                preIrpf = usuarios.get(0).getIrpf();
                preSegSoc = usuarios.get(0).getSegsoc();
                precTransp = tarifas.get(0).getPlusTransporte();
                valorHN.setText("0 * "+(precN+preAnt)+" = 0");
                valorHE.setText("0 * "+precE+" = 0");
                valorHF.setText("0 * "+precF+" = 0");
                valorDS.setText("0 * "+precD+" = 0");
                irpf.setText(df.format(preIrpf)+" %"); sS.setText(df.format(preSegSoc)+" %");
                valorTransp.setText("1 * "+df.format(precTransp)+"="+df.format(1*precTransp)+"");
                limiteExtra = tarifas.get(0).getLimite();
            }
            setHorario(Integer.parseInt(utils.getIdUsuarioActual(prefs)),fech);
            functions(view);
        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setCancelable(false);
            View vista = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_toast_error,null);
            TextView tit = vista.findViewById(R.id.tituloError);
            TextView txt = vista.findViewById(R.id.textoError);

            tit.setText("Error en Usuario");
            txt.setText("No existe ningún usuario registrado.\r\nPor favor registra antes un usuario para poder grabar horarios");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    utils.changeFragment(getActivity(), new fragmentCreaUsuario(),"Crear Usuario");
                }
            });
            alert.setView(vista);
            alert.show();
        }
        setHorDefault(Integer.parseInt(utils.getIdUsuarioActual(prefs)));
        return view;
    }
    private  void bind(View view){
        fecha = view.findViewById(R.id.fechaHorario);
        festivo = view.findViewById(R.id.festivo);
        entraM = view.findViewById(R.id.entraM);
        entraT = view.findViewById(R.id.entraT);
        saleM = view.findViewById(R.id.saleM);
        saleT = view.findViewById(R.id.saleT);
        paraM = view.findViewById(R.id.paraM);
        paraT = view.findViewById(R.id.paraT);

        horN = view.findViewById(R.id.horNorm);
        horE = view.findViewById(R.id.horExt);
        horF = view.findViewById(R.id.horFest);
        desc = view.findViewById(R.id.totDescansos);
        irpf = view.findViewById(R.id.totIrpF);
        sS = view.findViewById(R.id.totSs);
        transp = view.findViewById(R.id.totTransp);

        valorHN = view.findViewById(R.id.valorHorN);
        valorHE = view.findViewById(R.id.valorHorExt);
        valorHF = view.findViewById(R.id.valorHorFest);
        valorDS = view.findViewById(R.id.valorDescansos);
        valorTransp = view.findViewById(R.id.valorTransp);
        valorSub = view.findViewById(R.id.subtotal);
        valorIrpf = view.findViewById(R.id.valorIrpF);
        valorSs = view.findViewById(R.id.valorSs);
        valorDia = view.findViewById(R.id.valorDia);

        btnSave = view.findViewById(R.id.btnSaveHorario);
        btnDel = view.findViewById(R.id.btnDelHorario);
        horasTotales = view.findViewById(R.id.totalHoras);
        btnCalcular = view.findViewById(R.id.btnCalcula);
    }

    private void calculaHoras(){
        DecimalFormat df = new DecimalFormat("0.00");
        double horas = utils.calcularTiempo(entraM.getText().toString(),saleM.getText().toString(),Integer.parseInt(paraM.getText().toString()));
        double horas1 = utils.calcularTiempo(entraT.getText().toString(),saleT.getText().toString(),Integer.parseInt(paraT.getText().toString()));
        totalN = horas+horas1;
        //Toast.makeText(getContext(),"TOTAL N "+totalN,Toast.LENGTH_SHORT).show();
        double pM = Integer.parseInt(paraM.getText().toString())/60.00;
        double pT = Integer.parseInt(paraT.getText().toString())/60.00;


        if(pM>0 && pT==0){desc.setText("1");}
        if(pM>0 && pT>0){desc.setText("2");}
        if(pM==0 && pT>0){desc.setText("1");}
        if(pM==0 && pT==0){desc.setText("0");}
        int paradas = Integer.parseInt(desc.getText().toString());
        if(festivo.isChecked()){
             hN = 0;
             EX = 0;
             FI = totalN;
            String N = utils.calculaDoubleAHora(hN);
            String E = utils.calculaDoubleAHora(EX);
            String F = utils.calculaDoubleAHora(FI);
            String HT = utils.calculaDoubleAHora(hN+EX+FI);
            THOR = hN+EX+FI;
            horasTotales.setText(HT);
            horN.setText(N);
            horE.setText(E);
            horF.setText(F);
            valorHN.setText(N+" * "+(precN+preAnt)+" = "+df.format(hN*(precN+preAnt)));
            valorHE.setText(E+" * "+precE+" = "+df.format(EX*precE));
            valorHF.setText(F+" * "+precF+" = "+df.format(FI*precF));
            valorDS.setText(paradas+" * "+precD+" = "+df.format(paradas*precD));

            calculaDia(hN,EX,FI,paradas);

        }else{
            if(totalN>=limiteExtra){
                 hN = limiteExtra;
                 //Toast.makeText(getContext(),"hN "+hN,Toast.LENGTH_SHORT).show();
                 EX = totalN-limiteExtra;
                 FI =0;
                String N = utils.calculaDoubleAHora(hN);
                String E = utils.calculaDoubleAHora(EX);
                String F = utils.calculaDoubleAHora(0);
                horN.setText(N);
                horE.setText(E);
                horF.setText(F);
                String HT = utils.calculaDoubleAHora(hN+EX);
                horasTotales.setText(HT);
                THOR = hN+EX+FI;
                valorHN.setText(N+" * "+(precN+preAnt)+" = "+df.format(hN*(precN+preAnt)));
                valorHE.setText(E+" * "+precE+" = "+df.format(EX*precE));
                valorHF.setText(F+" * "+precF+" = "+df.format(0*precF));
                valorDS.setText(paradas+" * "+precD+" = "+df.format(paradas*precD));
                calculaDia(hN,EX,0,paradas);

            }else{
                 hN = totalN-pM-pT;
                 EX = 0;
                 FI = 0;
                String N = utils.calculaDoubleAHora(hN);
                String E = utils.calculaDoubleAHora(EX);
                String F = utils.calculaDoubleAHora(0);
                horN.setText(N);
                horE.setText(E);
                horF.setText(F);
                String HT = utils.calculaDoubleAHora(hN+EX);
                horasTotales.setText(HT);
                THOR = hN+EX+FI;
                //Toast.makeText(getContext(),hN+" "+EX+" "+FI+" "+paradas,Toast.LENGTH_SHORT).show();
                valorHN.setText(N+" * "+(precN+preAnt)+" = "+df.format(hN*(precN+preAnt)));
                valorHE.setText(E+" * "+precE+" = "+df.format(EX*precE));
                valorHF.setText(F+" * "+precF+" = "+df.format(0*precF));
                valorDS.setText(paradas+" * "+precD+" = "+df.format(paradas*precD));
                calculaDia(hN,EX,0,paradas);
            }
        }
    }
    private  void calculaDia(double hN,double EX,double FI,double paradas){
        DecimalFormat df = new DecimalFormat("0.00");
        double subt = 0;
        subt = (hN*(precN+preAnt))+(EX*precE)+(FI*precF)+(paradas*precD)+precTransp;
        double irp = preIrpf*subt/100;
        double segs = preSegSoc*subt/100;
        double total = subt-irp-segs;
        valorSub.setText(df.format(subt));
        SUBT = subt;
        TOT = total;
        //TOT = Float.parseFloat(df.format(TOT));
        valorIrpf.setText(df.format(irp)); valorSs.setText(df.format(segs));
        valorDia.setText(df.format(total));
    }
    private  void functions(View view){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR); int month = c.get(Calendar.MONTH); int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String f = i+"-"+(i1+1)+"-"+i2;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = sdf.parse(f);
                            String ff = sdf.format(date); fecha.setText(ff);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },year,month,day);
                picker.show();
            }
        });
        entraM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hora, minuto;
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
                },HentraM,MentraM,true);
                picker.show();
            }
        });
        entraM.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                entraM.setText("00:00"); saleM.setText("00:00");
                calculaHoras();
                return false;
            }
        });
        saleM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hora, minuto;
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

                        if(entraT.getText().toString() != "00:00"){
                            if( utils.checkHora(getContext(),entraM.getText().toString(),saleM.getText().toString()) == true){
                             //   double horas = utils.calcularTiempo(entraM.getText().toString(),saleM.getText().toString(),Integer.parseInt(paraM.getText().toString()));
                             //   double horas1 = utils.calcularTiempo(entraT.getText().toString(),saleT.getText().toString(),Integer.parseInt(paraT.getText().toString()));
                             //   totalN = horas+horas1;
                                calculaHoras();
                            }

                        }
                        /*else{

                                double horas = utils.calcularTiempo(entraM.getText().toString(),saleM.getText().toString(),Integer.parseInt(paraM.getText().toString()));
                                totalN = horas;
                                calculaHoras();

                        }*/

                    }
                },HsaleM,MsaleM,true);
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
                },HentraT,MentraT,true);
                picker.show();
            }
        });
        entraT.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                entraT.setText("00:00"); saleT.setText("00:00");
                calculaHoras();
                return false;
            }
        });
        saleT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String hora, minuto;
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
                        if(entraM.getText().toString() != "00:00"){
                            double horas = 0,horas1=0;
                            /*if( utils.checkHora(getContext(),entraM.getText().toString(),saleM.getText().toString()) == true){
                                horas = utils.calcularTiempo(entraM.getText().toString(),saleM.getText().toString(),Integer.parseInt(paraM.getText().toString()));
                            }
                            if( utils.checkHora(getContext(),entraT.getText().toString(),saleT.getText().toString()) == true){
                                horas1 = utils.calcularTiempo(entraT.getText().toString(),saleT.getText().toString(),Integer.parseInt(paraT.getText().toString()));
                            }
                            totalN = horas+horas1;*/
                            calculaHoras();
                        }
                    }
                },HsaleT,MsaleT,true);
                picker.show();
            }
        });
        paraM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("SELECCIONA DURACION PARADA");
                View view = getActivity().getLayoutInflater().inflate(R.layout.layout_spinner_alert,null);
                NumberPicker picker = view.findViewById(R.id.number_alert);
                Button acept = view.findViewById(R.id.numberAcept);
                picker.setMinValue(0); picker.setMaxValue(11);
                String[] paradas = {"0","20","30","40","50","60","70","80","90","100","110","120"};
                picker.setDisplayedValues(paradas);
                /*picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        paraM.setText(paradas[i]);
                    }
                });*/
                dialog.setView(view);
                AlertDialog alert = dialog.create();
                acept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paraM.setText(paradas[picker.getValue()]);
                        alert.dismiss();
                        calculaHoras();
                    }
                });
                alert.show();
            }
        });
        paraT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("SELECCIONA DURACION PARADA");
                View view = getActivity().getLayoutInflater().inflate(R.layout.layout_spinner_alert,null);
                NumberPicker picker = view.findViewById(R.id.number_alert);
                Button acept = view.findViewById(R.id.numberAcept);
                picker.setMinValue(0); picker.setMaxValue(11);
                String[] paradas = {"0","20","30","40","50","60","70","80","90","100","110","120"};
                picker.setDisplayedValues(paradas);
                /*picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        paraT.setText(paradas[i]+"");
                    }
                });*/
                dialog.setView(view);
                AlertDialog alert = dialog.create();
                acept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                        paraT.setText(paradas[picker.getValue()]);
                        calculaHoras();
                    }
                });
                alert.show();
            }
        });
        festivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                calculaHoras();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat("0.00");
                ArrayList<Horario> hor = utils.getHorario(getContext(),Integer.parseInt(utils.getIdUsuarioActual(prefs)),fech);
                DbHorarios dtb = new DbHorarios(getContext());
                SQLiteDatabase db = dtb.getWritableDatabase();
                ContentValues values = new ContentValues();
                ArrayList<String> valores = getValues();
                values.put("idUsuario",Integer.parseInt(utils.getIdUsuarioActual(prefs)));
                values.put("fecha",fech);
                values.put("festivoSN",esFestivo().toString()); values.put("horaEntraM",entraM.getText().toString());
                values.put("horaSaleM",saleM.getText().toString()); values.put("desayunoM",String.valueOf(paraM.getText().toString()));
                values.put("horaEntraT",entraT.getText().toString()); values.put("horaSaleT",saleT.getText().toString()); values.put("meriendaT",String.valueOf(paraT.getText().toString()));


                values.put("totalHorasNorm",horN.getText().toString()); values.put("totalHorasExtra",horE.getText().toString()); values.put("totalHorasFiesta",horF.getText().toString());
                values.put("totalHoras",horasTotales.getText().toString());
                values.put("totalHorasDec",String.valueOf(THOR).replace(",","."));
        System.out.println("HorasN "+horN.getText()+", HorE "+horE.getText()+", HorF "+horF.getText()+" TotDec "+THOR);

                values.put("totalHorasNormDec",String.valueOf(hN).replace(",","."));
                values.put("totalHorasExtraDec",String.valueOf(EX).replace(",","."));
                values.put("totalHorasFiestaDec",String.valueOf(FI).replace(",","."));
                values.put("subTotal",df.format(SUBT).replace(",","."));
                values.put("totalIrpF",String.valueOf(valorIrpf.getText().toString()).replace(",","."));
                values.put("totalSecSoc",String.valueOf(valorSs.getText().toString()).replace(",","."));
                values.put("totalDia",df.format(TOT).replace(",","."));
                values.put("totalParadas",desc.getText().toString().replace(",","."));
        System.out.println("SubTotal"+SUBT+",TOT: "+TOT+" ,TotNormDec "+hN+", horEx "+EX+" horFI "+FI+" ");

                values.put("valorHorNm",valorHN.getText().toString().replace(",","."));
                values.put("valorHorEX",valorHE.getText().toString().replace(",","."));
                values.put("valorHorFI",valorHF.getText().toString().replace(",","."));
                values.put("valorDescansos",valorDS.getText().toString().replace(",","."));
                values.put("valorTransporte",valorTransp.getText().toString().replace(",","."));
        System.out.println("Valores HN"+valorHN.getText()+", HE "+valorHE.getText()+", HF "+valorHF.getText()+", Descanso "+valorDS.getText()+", Plus "+valorTransp.getText());
        String[] datos = fech.split("-");
        int mes = Integer.parseInt(datos[1]); int year = Integer.parseInt(datos[0]);
                values.put("mesHorario",mes); values.put("yearHorario",year);

               // Toast.makeText(getContext(),THOR+" "+horN.getText()+" "+hN+" "+horE.getText()+" "+EX+" "+horF.getText()+" "+FI+" "+desc.getText(),Toast.LENGTH_SHORT).show();
              //  Toast.makeText(getContext(),"SUB "+df.format(SUBT)+" "+valorIrpf.getText()+" "+valorSs.getText()+" "+df.format(TOT)+" "+P,Toast.LENGTH_SHORT).show();

                if(utils.checkHora(getContext(),entraM.getText().toString(),saleM.getText().toString())
                        && utils.checkHora(getContext(),entraT.getText().toString(),saleT.getText().toString())){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("¡ ATENCIÓN !");
                    alert.setMessage("¿seguro que quieres guardar el horario para éste usuario?");
                    alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(hor.size()>0){
                                db.update(dtb.getDatabaseName(),values,"idUsuario = "+utils.getIdUsuarioActual(prefs)+" and fecha = '"+fech+"'",null);
                                utils.changeFragment(getActivity(),new GestionHorarios(),"Gestion Horarios");
                            }else{
                                db.insert(dtb.getDatabaseName(),null,values);
                                utils.changeFragment(getActivity(),new GestionHorarios(),"Gestion Horarios");
                                if(db != null){
                                    System.out.println(values);
                                }else{
                                    Toast.makeText(getContext(),"ERROR Al insertar horario",Toast.LENGTH_SHORT).show();
                                    utils.changeFragment(getActivity(),new GestionHorarios(),"Gestion Horarios");
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

            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("¡ ATENCIÓN !");
                alert.setMessage("¿Seguro que quieres borrar éste horario?");
                alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbHorarios dtb = new DbHorarios(getContext());
                        SQLiteDatabase db = dtb.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        String idu = utils.getIdUsuarioActual(prefs);
                        String fecha = fech;
                        db.delete(dtb.getDatabaseName(), "idUsuario = "+idu+" and fecha='"+fecha+"'",null);
                        utils.changeFragment(getActivity(),new GestionHorarios(),"Gestion Horarios");
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
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(entraM.getText().toString() != "00:00"){
                    double horas = 0,horas1=0;
                    if( utils.checkHora(getContext(),entraM.getText().toString(),saleM.getText().toString()) == true){
                        horas = utils.calcularTiempo(entraM.getText().toString(),saleM.getText().toString(),Integer.parseInt(paraM.getText().toString()));
                    }
                    if( utils.checkHora(getContext(),entraT.getText().toString(),saleT.getText().toString()) == true){
                        horas1 = utils.calcularTiempo(entraT.getText().toString(),saleT.getText().toString(),Integer.parseInt(paraT.getText().toString()));
                    }
                    totalN = horas+horas1;
                    calculaHoras();
                }
            }
        });
    }
    private String esFestivo(){
        if(festivo.isChecked()){return "S";}else{return "N";}
    }
    private  void setHorario(int usuario,String f){

        ArrayList<Horario> horario = utils.getHorario(getContext(),usuario,f);
        if(horario.size()>0){
            Horario h = horario.get(0);
            fecha.setText(h.getFecha().toString());
            entraM.setText(h.getEntraM()); saleM.setText(h.getSaleM()); paraM.setText(String.valueOf(h.getParaM()));
            entraT.setText(h.getEntraT()); saleT.setText(h.getSaleT()); paraT.setText(String.valueOf(h.getParaT()));
            horasTotales.setText(h.getTotHor());
            horN.setText(h.getTotNorm()); horE.setText(h.getTotExtra()); horF.setText(h.getTotFiesta());
            desc.setText(String.valueOf(h.getParadas()));
            valorHN.setText(h.getValorHorNm());
            valorHE.setText(h.getValorHorEX());
            valorHF.setText(h.getValorHorFI());
            valorDS.setText(h.getValorDesc());

            valorIrpf.setText(String.valueOf(h.getTotIrpf()));
            valorSs.setText(String.valueOf(h.getTotSegSoc()));
            valorSub.setText(String.valueOf(h.getSubTotal()));
            valorDia.setText(String.valueOf(h.getTotDia()));
            valorTransp.setText(h.getValorTransp());

            if(h.getFestivo().trim().equals("S")){festivo.setChecked(true); btnCalcular.performClick();
                Toast.makeText(getContext(),fecha.getText()+" FESTIVA ",Toast.LENGTH_SHORT).show();
            }else{festivo.setChecked(false); btnCalcular.performClick();}
            //valorIrpf.setText(String.valueOf(h.getTotIrpf()));
            //valorSs.setText(String.valueOf(h.getTotSegSoc()));
            //valorSub.setText(String.valueOf(h.getSubTotal()));
           // valorDia.setText(String.valueOf(h.getTotDia()));
            //System.out.println("TOTALES "+h.getIdUsuario()+" , "+h.getFecha()+", "+h.getEntraM()+" - "+h.getSaleM()+" - "+h.getParaM());
            //System.out.println(h.getEntraT()+" - "+h.getSaleT()+" - "+h.getParaT());
            //System.out.println(h.getTotHoras()+" , "+h.getTotNorm()+" , "+h.getTotExtra()+" , "+h.getTotFiesta());
            //System.out.print("");
           // calculaHoras();
        }else{
           //Toast.makeText(getContext(),"No existe horario",Toast.LENGTH_SHORT).show();
            ArrayList<HorarioDef> hor = utils.getHorDef(getContext(),Integer.parseInt(utils.getIdUsuarioActual(prefs)));
            if(hor.size()>0){
                HorarioDef H = hor.get(0);
                entraM.setText(parseHora(H.getHentraM())+":"+parseHora(H.getMentraM())); saleM.setText(parseHora(H.getHsaleM())+":"+parseHora(H.getMsaleM()));
                entraT.setText(parseHora(H.getHentraT())+":"+parseHora(H.getMentraT())); saleT.setText(parseHora(H.getHsaleT())+":"+parseHora(H.getMsaleT()));
            }

        }
    }
    private void setHorDefault(int usuario){
        ArrayList<HorarioDef> hor = utils.getHorDef(getContext(),usuario);
        if(hor.size()>0 ){
            HorarioDef H = hor.get(0);
            HentraM=H.getHentraM(); MentraM=H.getMentraM(); HsaleM=H.getHsaleM(); MsaleM=H.getMsaleM();
            HentraT=H.getHentraT(); MentraT=H.getMentraT(); HsaleT=H.getHsaleT(); MsaleT=H.getMsaleT();
        }
    }
    private  ArrayList<String> getValues(){
        ArrayList<String> valores = new ArrayList<>();
        String fecha = fech;
        String eM = entraM.getText().toString();
        String sM = saleM.getText().toString();
        String pM = paraM.getText().toString();
        String eT = entraT.getText().toString();
        String sT = saleT.getText().toString();
        String pT = paraT.getText().toString();
        String totNorm = horN.getText().toString();
        String totExtra = horE.getText().toString();
        String totFiesta= horF.getText().toString();
        String fiesta =  "N";
        if(festivo.isChecked()){fiesta = "S";}else{fiesta = "N";}
        String subTot = valorSub.getText().toString();
        String IrpF = valorIrpf.getText().toString();
        String SeSoc = valorSs.getText().toString();
        String total = valorDia.getText().toString();
        String paradas = desc.getText().toString();
        valores.add(fecha); valores.add(fiesta); valores.add(eM); valores.add(sM); valores.add(pM); valores.add(eT); valores.add(sT);
        valores.add(pT); valores.add(totNorm); valores.add(totExtra); valores.add(totFiesta); valores.add(String.valueOf(hN));
        valores.add(String.valueOf(EX)); valores.add(String.valueOf(FI));
        valores.add(subTot); valores.add(IrpF); valores.add(SeSoc); valores.add(total); valores.add(paradas);
        return valores;
    }
    private String parseHora(int num){
        String valor = "";
        if(num>10){valor=""+num;}else{valor="0"+num;}
        return valor;
    }

}