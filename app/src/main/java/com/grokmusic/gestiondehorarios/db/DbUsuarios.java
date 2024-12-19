package com.grokmusic.gestiondehorarios.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbUsuarios extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "gestion.db";
    private static final String TABLE_USUARIOS = "t_usuarios";
    public DbUsuarios(@Nullable Context context) {
        super(context, TABLE_USUARIOS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_USUARIOS+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre TEXT NOT NULL,"+
                "tarifa INTEGER NOT NULL," +
                "antig DECIMAL(6,4) NOT NULL,"+
                "irpf DECIMAL(6,4) NOT NULL," +
                "segSoc DECIMAL (6,4) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_USUARIOS);
        onCreate(sqLiteDatabase);
    }
}
