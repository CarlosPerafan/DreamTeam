package com.perafan.usuarios.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.perafan.usuarios.entidades.Canciones;

import java.util.ArrayList;

public class DbCanciones extends DbHelper  {
    Context context;
    public DbCanciones(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarcancion(String titulo, String artista,String album, String genero, Float precio)
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long newRowId=0;
        try {
            ContentValues valores = new ContentValues();
            valores.put("titulo", titulo);
            valores.put("artista",artista);
            valores.put("album",  album);
            valores.put("genero", genero);
            valores.put("precio", precio);


            newRowId = db.insert(TABLE_LISTACANCIONES , null, valores);
        }catch(Exception ex)
        {
            ex.toString();
        }
        return newRowId;

    }

    public ArrayList<Canciones>  listarcanciones()
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Canciones>  listacanciones = new ArrayList<>();
        Canciones cancion = null;
        Cursor cursorcanciones = null;

        cursorcanciones = db.rawQuery("SELECT * FROM "+TABLE_LISTACANCIONES,null);

        if (cursorcanciones.moveToFirst())
        {
            do{
                    cancion = new Canciones();
                    cancion.setIdLC(cursorcanciones.getInt(0));
                    cancion.setTitulo(cursorcanciones.getString(1));
                    cancion.setArtista(cursorcanciones.getString(2));
                    cancion.setAlbum(cursorcanciones.getString(3));
                    cancion.setGenero(cursorcanciones.getString(4));
                    cancion.setPrecio(cursorcanciones.getFloat(5));

                    listacanciones.add(cancion);

            } while(cursorcanciones.moveToNext());
        }
        cursorcanciones.close();
        return listacanciones;

    }

    public Canciones  Fichacancion(int id)
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Canciones cancion = null;
        Cursor cursorcanciones = null;

        cursorcanciones = db.rawQuery("SELECT * FROM "+TABLE_LISTACANCIONES+" WHERE idLC = "+id +" LIMIT 1",null);

        if (cursorcanciones.moveToFirst())
        {
                cancion = new Canciones();
                cancion.setIdLC(cursorcanciones.getInt(0));
                cancion.setTitulo(cursorcanciones.getString(1));
                cancion.setArtista(cursorcanciones.getString(2));
                cancion.setAlbum(cursorcanciones.getString(3));
                cancion.setGenero(cursorcanciones.getString(4));
                cancion.setPrecio(cursorcanciones.getFloat(5));

        }
        cursorcanciones.close();
        return cancion;

    }
    public boolean editarcancion(int id,String titulo, String artista,String album, String genero, Float precio)
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        boolean correcto= false;
        try {
            db.execSQL("UPDATE " +TABLE_LISTACANCIONES + " SET titulo = '"+titulo +"', artista = '"+artista +"', album = '"+album +"', genero = '"+genero +"', precio = '"+precio+
                    "' WHERE idLC = '"+ id+"' ");
            correcto = true;
        }catch(Exception ex)
        {
            ex.toString();
            correcto = false;
        }finally
        {
            db.close();
        }
        return correcto;

    }
    public boolean eliminarCancion(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_LISTACANCIONES + " WHERE idLC = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }


}
