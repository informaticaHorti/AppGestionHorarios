package com.grokmusic.gestiondehorarios.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHorarios extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "gestion.db";
    private static final String TABLE = "t_horarios";
    public DbHorarios(@Nullable Context context) {
        super(context, TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "idUsuario INT NOT NULL,"+
                "fecha TEXT NOT NULL,"+
                "festivoSN TEXT NOT NULL,"+
                "horaEntraM TEXT NOT NULL,"+
                "horaSaleM TEXT NOT NULL,"+
                "desayunoM INTEGER NOT NULL,"+
                "horaEntraT TEXT NOT NULL,"+
                "horaSaleT TEXT NOT NULL,"+
                "meriendaT INTEGER NOT NULL,"+
                "totalHorasNorm TEXT NOT NULL,"+
                "totalHorasExtra TEXT NOT NULL,"+
                "totalHorasFiesta TEXT NOT NULL,"+
                "totalHoras TEXT NOT NULL,"+
                "totalHorasDec DECIMAL(6,4) NOT NULL,"+
                "totalHorasNormDec DECIMAL(6,4) NOT NULL,"+
                "totalHorasExtraDec DECIMAL(6,4) NOT NULL,"+
                "totalHorasFiestaDec DECIMAL(6,4) NOT NULL," +
                "totalParadas INTEGER NOT NULL," +
                "subTotal DECIMAL(6,4) NOT NULL," +
                "totalIrpF DECIMAL(6,4) NOT NULL," +
                "totalSecSoc DECIMAL(6,4) NOT NULL," +
                "totalDia DECIMAL(6,4)," +
                "valorHorNm TEXT NOT NULL," +
                "valorHorEX TEXT NOT NULL," +
                "valorHorFI TEXT NOT NULL," +
                "valorDescansos TEXT NOT NULL," +
                "valorTransporte TEXT NOT NULL," +
                "mesHorario INT NOT NULL," +
                "yearHorario INT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE);
        onCreate(sqLiteDatabase);
    }
}
