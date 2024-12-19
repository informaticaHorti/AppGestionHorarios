package com.grokmusic.gestiondehorarios.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHorarioDef extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "t_hora_defaul";
    public DbHorarioDef(@Nullable Context context) {
        super(context, TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "idUsuario INT NOT NULL,"+
                "HentraM INT NOT NULL,"+
                "MentraM INT NOT NULL,"+
                "HsaleM INT NOT NULL,"+
                "MsaleM INT NOT NULL,"+
                "HentraT INT NOT NULL,"+
                "MentraT INT NOT NULL,"+
                "HsaleT INT NOT NULL,"+
                "MsaleT INT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE);
        onCreate(sqLiteDatabase);
    }
}
