package com.grokmusic.gestiondehorarios.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.grokmusic.gestiondehorarios.Clases.Horario;
import com.grokmusic.gestiondehorarios.Clases.MesesNomina;
import com.grokmusic.gestiondehorarios.Clases.NominaDiaria;
import com.grokmusic.gestiondehorarios.db.DbHorarios;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class utDb {
    public static ArrayList<MesesNomina> getNominas(Context context,String id){
        ArrayList<MesesNomina> lista = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");
        DbHorarios dtb = new DbHorarios(context);
        SQLiteDatabase db = dtb.getReadableDatabase();
        String[] meses = {"SIN MES","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        if(!id.isEmpty()){
            Cursor cs = db.rawQuery("select mesHorario mes ,yearHorario year ,count(fecha)dias,sum(totalDia)importeMes from "+dtb.getDatabaseName()+" " +
                    "where idUsuario="+id+" group by yearHorario,mesHorario order by yearHorario desc, mesHorario desc ",null);
            if(cs.moveToFirst()){
                do{
                    String mes = meses[cs.getInt(0)];
                    MesesNomina nomina = new MesesNomina(cs.getInt(1),cs.getInt(0),mes,cs.getInt(2),Double.parseDouble(df.format(cs.getDouble(3)).replace(",",".")));
                    lista.add(nomina);
                }while (cs.moveToNext());
            }else{
                Toast.makeText(context,"No hay datos de jornadas en el sistema",Toast.LENGTH_SHORT).show();
                return null;
            }
        }else{
            Toast.makeText(context,"No hay datos de jornadas en el sistema",Toast.LENGTH_SHORT).show();
        }
        return  lista;
    }
    public static ArrayList<NominaDiaria> getNominaMes(Context context, String usr, int year, int mes){
        DbHorarios dtb = new DbHorarios(context);
        ArrayList<Horario> list = new ArrayList<Horario>();
        ArrayList<NominaDiaria> nomina = new ArrayList<>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+dtb.getDatabaseName()+" WHERE idUsuario="+usr+" and yearHorario="+year+" and mesHorario="+mes+" order by fecha asc ",null);
        Cursor cs2 = db.rawQuery("select sum(totalParadas)paradas,sum(totalDia)total from "+dtb.getDatabaseName()+" WHERE idUsuario="+usr+" and yearHorario="+year+" and mesHorario="+mes+" ",null);
        if(cs.moveToFirst()){
            do {
                Horario hor = new Horario(cs.getInt(0),cs.getInt(1),cs.getString(2),cs.getString(3),cs.getString(4),cs.getString(5),cs.getInt(6),
                        cs.getString(7),cs.getString(8),cs.getInt(9),cs.getString(10),cs.getString(11),cs.getString(12),cs.getString(13),
                        cs.getDouble(14),cs.getDouble(15),cs.getDouble(16),cs.getDouble(17),cs.getInt(18),cs.getDouble(19),cs.getDouble(20)
                        ,cs.getDouble(21),cs.getDouble(22),cs.getString(23),cs.getString(24),cs.getString(25),cs.getString(26),cs.getString(27),
                        cs.getInt(28),cs.getInt(29));
                String[] datos = hor.getFecha().split("-");
                NominaDiaria nom = new NominaDiaria(datos[2],hor.getTotNorm(),hor.getTotExtra(),hor.getTotFiesta(),hor.getParadas(),hor.getSubTotal(),
                        hor.getTotIrpf(),hor.getTotSegSoc(),hor.getTotDia());
                //Toast.makeText(context,"NOMINA "+hor.getFecha()+" "+hor.getTotDia()+" "+hor.getParadas(),Toast.LENGTH_SHORT).show();
                nomina.add(nom);
            }while(cs.moveToNext());
        }
        cs.close();

        if(cs2.moveToFirst()){
            System.out.println("CS2 "+cs2.getInt(0)+" "+cs2.getDouble(1));
                int para = cs2.getInt(0);
                Double total = cs2.getDouble(1);
                NominaDiaria nom = new NominaDiaria(" "," "," ","TOTAL",para,0.0,0.0,0.0,total);
                nomina.add(nom);
        }
        cs2.close();

        return nomina;
    }
    public static ArrayList<Horario> getNominaMesHoras(Context context, String usr, int year, int mes){
        DbHorarios dtb = new DbHorarios(context);
        ArrayList<Horario> list = new ArrayList<Horario>();
        ArrayList<NominaDiaria> nomina = new ArrayList<>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+dtb.getDatabaseName()+" WHERE idUsuario="+usr+" and yearHorario="+year+" and mesHorario="+mes+" order by fecha asc ",null);
        Cursor cs2 = db.rawQuery("select sum(totalParadas)paradas,sum(totalDia)total from "+dtb.getDatabaseName()+" WHERE idUsuario="+usr+" and yearHorario="+year+" and mesHorario="+mes+" ",null);
        if(cs.moveToFirst()){
            do {
                Horario hor = new Horario(cs.getInt(0),cs.getInt(1),cs.getString(2),cs.getString(3),cs.getString(4),cs.getString(5),cs.getInt(6),
                        cs.getString(7),cs.getString(8),cs.getInt(9),cs.getString(10),cs.getString(11),cs.getString(12),cs.getString(13),
                        cs.getDouble(14),cs.getDouble(15),cs.getDouble(16),cs.getDouble(17),cs.getInt(18),cs.getDouble(19),cs.getDouble(20)
                        ,cs.getDouble(21),cs.getDouble(22),cs.getString(23),cs.getString(24),cs.getString(25),cs.getString(26),cs.getString(27),
                        cs.getInt(28),cs.getInt(29));
                list.add(hor);
            }while(cs.moveToNext());
        }
        cs.close();

        return list;
    }
}
