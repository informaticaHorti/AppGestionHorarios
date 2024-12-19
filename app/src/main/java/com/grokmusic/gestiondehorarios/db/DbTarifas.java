package com.grokmusic.gestiondehorarios.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbTarifas extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NOMBRE = "gestion.db";
    private static final String TABLE = "t_tarifas";
    public DbTarifas(@Nullable Context context) {
        super(context, TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "tarifa TEXT NOT NULL,"+
                "horaNormal DECIMAL(6,4) NOT NULL,"+
                "horaExtra DECIMAL(6,4) NOT NULL,"+
                "horaFiesta DECIMAL(6,4) NOT NULL,"+
                "precioDescanso DECIMAL(6,4) NOT NULL,"+
                "plusTransporte DECIMAL(6,4) NOT NULL,"+
                "limiteExtra DECIMAL(6,4) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE);
        onCreate(sqLiteDatabase);
    }
}
