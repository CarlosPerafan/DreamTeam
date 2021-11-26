package com.perafan.usuarios.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "dreamusic.db";
    public static final String TABLE_USUARIOS   = "t_usuarios";
    public static final String TABLE_LISTACANCIONES  = "t_listacanciones";
    public static final String TABLE_LISTAUSUARIO  = "t_listausuario";



    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_USUARIOS+"(" +
                " idU INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nombre TEXT," +
                " apellidos TEXT," +
                " email TEXT," +
                " clave TEXT," +
                " telefono INTEGER," +
                " acepta INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_LISTACANCIONES+"(" +
                " idLC INTEGER PRIMARY KEY AUTOINCREMENT," +
                " titulo TEXT," +
                " artista TEXT," +
                " album TEXT," +
                " genero TEXT," +
                " precio DECIMAL(8,2));");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_LISTAUSUARIO+ " (" +
                " id_U INTEGER," +
                " id_LC INTEGER," +
                " FOREIGN KEY (id_U) REFERENCES t_usuarios(idU)," +
                " FOREIGN KEY (id_LC) REFERENCES t_listacanciones(IdLC));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LISTACANCIONES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LISTAUSUARIO);
        onCreate(db);
    }
}
