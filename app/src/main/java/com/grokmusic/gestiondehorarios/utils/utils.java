package com.grokmusic.gestiondehorarios.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.grokmusic.gestiondehorarios.Clases.Horario;
import com.grokmusic.gestiondehorarios.Clases.HorarioDef;
import com.grokmusic.gestiondehorarios.Clases.Tarifas;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.Fragments.fragmentCreaTarifa;
import com.grokmusic.gestiondehorarios.Fragments.fragmentCreaUsuario;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.db.DbHorarioDef;
import com.grokmusic.gestiondehorarios.db.DbHorarios;
import com.grokmusic.gestiondehorarios.db.DbTarifas;
import com.grokmusic.gestiondehorarios.db.DbUsuarios;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public   class utils {
    public static  void addUsuario(Activity activity){
        AlertDialog.Builder alertU = new AlertDialog.Builder(activity);
        alertU.setTitle("No Hay Usuarios");
        alertU.setCancelable(false);
        alertU.setMessage("No Existen usuarios Definidos.\r\n¡Vamos a crearlo!");
        alertU.setPositiveButton("ADELANTE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                utils.changeFragment(activity,new fragmentCreaUsuario(0,"",0,"0","0","0"),"Crear Usuario");;
            }
        });
        alertU.show();
    }
    public static void addTarifa(Activity activity){
        AlertDialog.Builder alertT = new AlertDialog.Builder(activity);
        alertT.setTitle("No Hay Tarifas");
        alertT.setCancelable(false);
        alertT.setMessage("No Existen tarifas actualmente definidas.\r\n¡Vamos a crearlas!");
        alertT.setPositiveButton("ADELANTE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                utils.changeFragment(activity,new fragmentCreaTarifa(), "Crear  Tarifas");
            }
        });
        alertT.show();
    }
    public static double calcularTiempo(String hora1, String hora2,int para) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        double tiempo=0;
        try {
            Date desde = format.parse(hora1);
            Date hasta = format.parse(hora2);
            long diff = Math.abs(hasta.getTime() - desde.getTime());
            System.out.println(diff);
            tiempo = (diff-(para*60*1000))/ (60*60*1000.00);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return tiempo;
    }
    public static String calculaDoubleAHora(double horas) {
        String hora ="";
        long myLong = ((long) (horas *60 *60* 1000));
        Date itemDate = new Date(myLong);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        hora = df.format(itemDate);
        if(horas>0){return hora;}else{return "00:00";}
    }
    public static void changeFragment(Activity activity, Fragment fragment,String titulo) {
        ((AppCompatActivity)activity).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,fragment)
               // .addToBackStack("GestionHorarios")
                .commit();
        ((AppCompatActivity)activity).getSupportActionBar().setTitle(titulo);
    }
    public static void checkInBlank(Context context,String texto){
        /*AlertDialog.Builder error = new AlertDialog.Builder(context);
        error.setCancelable(false);
        error.setTitle("ERROR, "+texto);
        error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        error.show();*/
        utils ut = new utils();
        ut.toastError(context,texto);
    }
    public static boolean checkHora(Context context,String entra,String sale){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        boolean vale = true;
        if(entra.equals("00:00") && sale.equals("00:00")){
            vale = true;
        }else{
            try {
                Date ent = df.parse(entra); long e = ent.getTime();
                Date sal = df.parse(sale); long s = sal.getTime();
                // Toast.makeText(context, e+" "+s,Toast.LENGTH_SHORT).show();
                if(s>e){ vale = true;}else{
                    utils u = new utils();
                    u.toastError(context,"La hora en entrada no puede ser superior a la de salida");
                    vale =false;
                }

            } catch (ParseException e) {            throw new RuntimeException(e);        }
        }


        return vale;
    }

    public static void ckeckVersion(Context context){
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            int code = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public  static void crearDbHorarios(Context context){
        DbHorarios dtb = new DbHorarios(context);
        SQLiteDatabase db = dtb.getWritableDatabase();
        if(db != null){
            System.out.println("Base Creada correctamente");
        }else{
            System.out.println("No se ha podido crear la base de datos");
        }
        db.close();
    }
    public  static void crearDbHorarioDef(Context context){
        DbHorarioDef dtb = new DbHorarioDef(context);
        SQLiteDatabase db = dtb.getWritableDatabase();
        /*ContentValues val = new ContentValues();
        val.put("idUsuario",usr); val.put("HentraM",9);val.put("MentraM",0); val.put("HsaleM",14);val.put("MsaleM",0);
        val.put("HentraT",15); val.put("MentraT",30); val.put("HsaleT",19); val.put("MsaleT",30);
        db.insert(dtb.getDatabaseName(),null,val);
        utils.changeFragment(activity,new GestionHorarios(),"Gestion Horarios");*/
        if(db != null){
            System.out.println("Base Creada correctamente");
        }else{
            System.out.println("No se ha podido crear la base de datos");
        }
        db.close();
    }
    public  static void crearDbUsuarios(Context context){
        DbUsuarios dtb = new DbUsuarios(context);
        SQLiteDatabase db = dtb.getWritableDatabase();
        if(db != null){
            System.out.println("Base Creada correctamente");
        }else{
            System.out.println("No se ha podido crear la base de datos");
        }
        db.close();
    }
    public  static void crearDbTarifas(Context context){
        DbTarifas dtb = new DbTarifas(context);
        SQLiteDatabase db = dtb.getWritableDatabase();
        if(db != null){
            System.out.println("Base Creada correctamente");
        }else{
            System.out.println("No se ha podido crear la base de datos");
        }
        db.close();
    }

    public static void crearPDF(Context context,String usr,String nameUsr,int year, int mes){
        PdfDocument pdfDocument = new PdfDocument();
        DecimalFormat df = new DecimalFormat("0.00");
        String tit = "Nomina del mes "+utils.getMes(mes)+" año "+year+", "+nameUsr;
       // Toast.makeText(context,tit,Toast.LENGTH_SHORT).show();
        ArrayList<Horario> nomina = utDb.getNominaMesHoras(context,usr,year,mes);
        Paint paint = new Paint();
        TextPaint titulo = new TextPaint();
        TextPaint desc = new TextPaint();
        Bitmap bitmap,bitmapEscala ;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1054,816,1).create();

        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo);
        Canvas canvas = page1.getCanvas();


        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.toolbar_gestion);
        bitmapEscala = Bitmap.createScaledBitmap(bitmap,400,120,false);
        canvas.drawBitmap(bitmapEscala,18,20,paint);
        desc.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        desc.setTextSize(26);
        int y = 200;
        canvas.drawText(tit,18,y,desc);


        desc.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        desc.setTextSize(21);
        //String[] arrDesc = texto.split("\r\n");
        y = 240;
        canvas.drawText("Fecha",18,y,desc);
        canvas.drawText("Entra M.",120,y,desc); canvas.drawText("Sale M.",205,y,desc); canvas.drawText("Para",280,y,desc);
        canvas.drawText("Entra T.",335,y,desc); canvas.drawText("Sale T.",415,y,desc); canvas.drawText("Para",490,y,desc);
        canvas.drawText("T.Horas",540,y,desc); canvas.drawText("H.Norm",625,y,desc);
        canvas.drawText("H.Extra",710,y,desc);canvas.drawText("H.Fiesta",790,y,desc);

         y = 260;
        desc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        desc.setTextSize(16);
        double subt =0.00, ss =0.00, irp =0.00,tot=0.00;
        for(int i=0; i< nomina.size(); i++){
            String eM =""; String eT=""; String  sM=""; String sT=""; String hN=""; String hE=""; String hF="";
            if(nomina.get(i).getEntraM().equals("00:00")){eM="N.T.";}else{eM=nomina.get(i).getEntraM();}
            if(nomina.get(i).getSaleM().equals("00:00")){sM="N.T.";}else{sM=nomina.get(i).getSaleM();}
            if(nomina.get(i).getEntraT().equals("00:00")){eT="N.T.";}else{eT=nomina.get(i).getEntraT();}
            if(nomina.get(i).getSaleT().equals("00:00")){sT="N.T.";}else{sT=nomina.get(i).getSaleT();}
            if(nomina.get(i).getTotNorm().equals("00:00")){hN="";}else{hN=nomina.get(i).getTotNorm();}
            if(nomina.get(i).getTotExtra().equals("00:00")){hE="";}else{hE=nomina.get(i).getTotExtra();}
            if(nomina.get(i).getTotFiesta().equals("00:00")){hF="";}else{hF=nomina.get(i).getTotFiesta();}
            canvas.drawText(nomina.get(i).getFecha(),18,y,desc);
            canvas.drawText(eM,130,y,desc); canvas.drawText(sM,210,y,desc); canvas.drawText(nomina.get(i).getParaM()+"",290,y,desc);
            canvas.drawText(eT,340,y,desc); canvas.drawText(sT,420,y,desc); canvas.drawText(nomina.get(i).getParaT()+"",500,y,desc);
            canvas.drawText(nomina.get(i).getTotHor(),550,y,desc); canvas.drawText(hN,640,y,desc);
            canvas.drawText(hE,720,y,desc); canvas.drawText(hF,800,y,desc);
            subt+=nomina.get(i).getSubTotal(); ss += nomina.get(i).getTotSegSoc(); irp += nomina.get(i).getTotIrpf(); tot += nomina.get(i).getTotDia();
            y += 20;
        }

        pdfDocument.finishPage(page1);
        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(1054,816,1).create();
        PdfDocument.Page page2 = pdfDocument.startPage(pageInfo2);
        Canvas canvas2 = page2.getCanvas();
        canvas2.drawBitmap(bitmapEscala,18,20,paint);
        y = 240;
        desc.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        desc.setColor(Color.BLACK);
        desc.setTextSize(21);
        canvas2.drawText("SUBTOTAL",18,y,desc);desc.setColor(context.getResources().getColor(R.color.colorPrimary)); canvas2.drawText(df.format(subt)+" €",200,y,desc);
        y = 270;
        desc.setColor(Color.BLACK);
        canvas2.drawText("Seg. Social",18,y,desc);desc.setColor(context.getResources().getColor(R.color.colorFailDeep));canvas2.drawText(df.format(ss)+" €",200,y,desc);
        y = 300;
        desc.setColor(Color.BLACK);
        canvas2.drawText("I.R.P.F",18,y,desc);desc.setColor(context.getResources().getColor(R.color.colorFailDeep));canvas2.drawText(df.format(irp)+" €",200,y,desc);
        y = 330;
        desc.setColor(Color.BLACK);
        canvas2.drawText("Total Mes ",18,y,desc);desc.setColor(context.getResources().getColor(R.color.teal_700));canvas2.drawText(df.format(tot)+" €",200,y,desc);
        pdfDocument.finishPage(page2);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"Horas_pdf_mes_"+mes+"_"+year+".pdf");

        try{
            file.delete();
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(context,"PDF creado ",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder abrir = new AlertDialog.Builder(context);
            View vv = LayoutInflater.from(context).inflate(R.layout.layout_toast_ok,null);
            TextView tv = vv.findViewById(R.id.tituloExito); TextView mv = vv.findViewById(R.id.textoExito);
            tv.setText("Horas "+utils.getMes(mes)+" "+year);
            mv.setText("¿Deseas abrir el PDF generado?");
            abrir.setView(vv);
            abrir.setCancelable(false);
            abrir.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    builder.detectFileUriExposure();
                    Intent open = new Intent(Intent.ACTION_VIEW);
                    open.setData(Uri.fromFile(file));
                    context.startActivity(open);
                }
            });
            abrir.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            abrir.show();


        }catch (Exception e){
            Toast.makeText(context,"PDF ERROR "+e.getMessage(),Toast.LENGTH_SHORT).show();
            System.out.println("PDF ERROR "+e.getMessage());
        }
    }
    public  static  String formatearMinutosAHoraMinuto(int minutos) {
        String formato = "%02d:%02d";
        long horasReales = TimeUnit.MINUTES.toHours(minutos);
        long minutosReales = TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutos));
        return String.format(formato, horasReales, minutosReales);
    }

    public static String getIdUsuarioActual(SharedPreferences prefs){
        return prefs.getString("idUsuario", "");
    }
    public static String getMes(int mes){
        String m ="";
        switch (mes){
            case 1: m= "Enero"; break;
            case 2: m=  "Febrero"; break;
            case 3: m=  "Marzo"; break;
            case 4: m=  "Abril"; break;
            case 5: m=  "Mayo"; break;
            case 6: m=  "Junio"; break;
            case 7: m=  "Julio"; break;
            case 8: m=  "Agosto"; break;
            case 9: m=  "Septiembre"; break;
            case 10: m=  "Octubre"; break;
            case 11: m=  "Noviembre"; break;
            case 12: m=  "Diciembre"; break;
        }
        return m;
    }
    public static String getUsuarioActual(SharedPreferences prefs){
        return prefs.getString("usuario", "");
    }
    public static String getIdTarifaActual(SharedPreferences prefs){
        return prefs.getString("tarifa", "");
    }

    public static  ArrayList<Horario> getHorario(Context context, int idUsuario,String fecha){
        DbHorarios dtb = new DbHorarios(context);
        ArrayList<Horario> list = new ArrayList<Horario>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+dtb.getDatabaseName()+" WHERE idUsuario="+idUsuario+" and fecha='"+fecha+"'",null);
        if(cs.moveToFirst()){
            do {
                list.add(new Horario(cs.getInt(0),cs.getInt(1),cs.getString(2),cs.getString(3),cs.getString(4),cs.getString(5),cs.getInt(6),
                        cs.getString(7),cs.getString(8),cs.getInt(9),cs.getString(10),cs.getString(11),cs.getString(12),cs.getString(13),
                        cs.getDouble(14),cs.getDouble(15),cs.getDouble(16),cs.getDouble(17),cs.getInt(18),cs.getDouble(19),cs.getDouble(20)
                        ,cs.getDouble(21),cs.getDouble(22),cs.getString(23),cs.getString(24),cs.getString(25),cs.getString(26),cs.getString(27),
                        cs.getInt(28),cs.getInt(29)));
            }while(cs.moveToNext());
        }
        cs.close();
        return list;
    }
    public static  ArrayList<String> getHorarios(Context context, int idUsuario,int mes,int year){
        DbHorarios dtb = new DbHorarios(context);
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select fecha from "+dtb.getDatabaseName()+" WHERE idUsuario="+idUsuario+" and mesHorario="+mes+" and yearHorario="+year+"",null);
        if(cs.moveToFirst()){
            do {
                list.add(cs.getString(0));
            }while(cs.moveToNext());
        }
        cs.close();
        return list;
    }
    public static  ArrayList<Usuarios> getUsuarios(Context context){
        DbUsuarios dtb = new DbUsuarios(context);
        ArrayList<Usuarios> list = new ArrayList<Usuarios>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from t_usuarios",null);
        if(cs.moveToFirst()){
            do {
                list.add(new Usuarios(cs.getInt(0),cs.getString(1),cs.getInt(2),cs.getFloat(3),cs.getFloat(4),cs.getFloat(5)));
                //Toast.makeText(context,cs.getString(1),Toast.LENGTH_LONG).show();
            }while(cs.moveToNext());

        }
        cs.close();
        return list;
    }
    public static  ArrayList<Usuarios> getUsuario(Context context,int id){
        DbUsuarios dtb = new DbUsuarios(context);
        ArrayList<Usuarios> list = new ArrayList<Usuarios>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from t_usuarios WHERE id="+id,null);
        if(cs.moveToFirst()){
            do {
                list.add(new Usuarios(cs.getInt(0),cs.getString(1),cs.getInt(2),cs.getDouble(3),cs.getFloat(4),cs.getFloat(5)));
                // Toast.makeText(context,cs.getString(1)+" "+cs.getInt(2)+" "+cs.getDouble(3),Toast.LENGTH_LONG).show();
            }while(cs.moveToNext());

        }
        cs.close();
        return list;
    }
    public static  ArrayList<Tarifas> getTarifas(Context context){
        DbTarifas dtb = new DbTarifas(context);
        ArrayList<Tarifas> list = new ArrayList<Tarifas>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+dtb.getDatabaseName(),null);
        if(cs.moveToFirst()){
            do {
                //Toast.makeText(context,cs.getString(1),Toast.LENGTH_LONG).show();
                list.add(new Tarifas(cs.getInt(0),cs.getString(1),cs.getDouble(2),cs.getDouble(3),cs.getDouble(4),cs.getDouble(5),cs.getDouble(6),cs.getDouble(7)));
                // Toast.makeText(context,cs.getString(1),Toast.LENGTH_LONG).show();
            }while(cs.moveToNext());

        }
        cs.close();
        return list;
    }
    public static  ArrayList<Tarifas> getTarifa(Context context, int id){
        DbTarifas dtb = new DbTarifas(context);
        ArrayList<Tarifas> list = new ArrayList<Tarifas>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+dtb.getDatabaseName()+" WHERE ID="+id,null);
        if(cs.moveToFirst()){
            do {
                list.add(new Tarifas(cs.getInt(0),cs.getString(1),cs.getDouble(2),cs.getDouble(3),cs.getDouble(4),cs.getDouble(5),cs.getDouble(6),cs.getDouble(7)));
            }while(cs.moveToNext());
        }
        cs.close();
        return list;
    }
    public static  ArrayList<HorarioDef> getHorDef(Context context, int id){
        DbHorarioDef dtb = new DbHorarioDef(context);
        ArrayList<HorarioDef> list = new ArrayList<HorarioDef>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+dtb.getDatabaseName()+" WHERE idUsuario="+id,null);
        if(cs.moveToFirst()){
                list.add(new HorarioDef(cs.getInt(0),cs.getInt(1),cs.getInt(2),cs.getInt(3),cs.getInt(4),cs.getInt(5),cs.getInt(6),cs.getInt(7),cs.getInt(8),cs.getInt(9)));
                // Toast.makeText(context,cs.getString(1),Toast.LENGTH_LONG).show();
        }else{
           // Toast.makeText(context,"NO hay datos para el idUsuario "+id,Toast.LENGTH_LONG).show();
        }
        cs.close();
        return list;
    }
    public static  ArrayList<HorarioDef> getHorDef(Context context){
        DbHorarioDef dtb = new DbHorarioDef(context);
        ArrayList<HorarioDef> list = new ArrayList<HorarioDef>();
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+dtb.getDatabaseName()+"",null);
        if(cs.moveToFirst()){
            do {
                list.add(new HorarioDef(cs.getInt(0),cs.getInt(1),cs.getInt(2),cs.getInt(3),cs.getInt(4),cs.getInt(5),cs.getInt(6),cs.getInt(7),cs.getInt(8),cs.getInt(9)));
                //Toast.makeText(context,cs.getString(1),Toast.LENGTH_LONG).show();
            }while(cs.moveToNext());

        }else{
            //Toast.makeText(context,"NO hay datos ",Toast.LENGTH_LONG).show();
        }
        cs.close();
        return list;
    }
    public static  String getTarifaName(Context context, int id){
        DbTarifas dtb = new DbTarifas(context);
        String nombre ="";
        SQLiteDatabase db = dtb.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+dtb.getDatabaseName()+" WHERE ID="+id,null);
        if(cs.moveToFirst()){
            do {
               // list.add(new Tarifas(cs.getInt(0),cs.getString(1),cs.getDouble(2),cs.getDouble(3),cs.getDouble(4),cs.getDouble(5)));
                nombre = cs.getString(1);
                // Toast.makeText(context,cs.getString(1),Toast.LENGTH_LONG).show();
            }while(cs.moveToNext());

        }else{nombre = "sel. tarifa";}
        cs.close();
        return nombre;
    }


    public void toastError(Context context,String texto){
        AlertDialog.Builder error = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_error,null);
        TextView tit = view.findViewById(R.id.tituloError); TextView txt = view.findViewById(R.id.textoError);
        // error.setCancelable(false);
        // error.setTitle("ERROR, "+texto);
        tit.setText("ERROR ");
        txt.setText(texto);
        error.setView(view);
        error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        error.show();
    }
    public void toastExito(Context context,String texto){
        AlertDialog.Builder error = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_ok,null);
        TextView tit = view.findViewById(R.id.tituloExito); TextView txt = view.findViewById(R.id.textoExito);
        // error.setCancelable(false);
        // error.setTitle("ERROR, "+texto);
        tit.setText("ERROR");
        txt.setText(texto);
        error.setView(view);
        error.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        error.show();
    }
    public static  void setPreferences(SharedPreferences preferences, String key, String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

}
