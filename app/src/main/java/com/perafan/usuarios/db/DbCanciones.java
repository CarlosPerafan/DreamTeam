package com.perafan.usuarios.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.RegistroSongActivity;
import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class DbCanciones extends DbHelper  {
    Context context;

    FirebaseFirestore firedb;
    private static final String TAG = RegistroSongActivity.class.getName();

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

    public void insertarsongfire(String titulo, String artista,String album, String genero, Float precio){
        /* Se conserva si por algo
        Cursor id;
        int sec =0;
        id = this.getReadableDatabase().rawQuery("SELECT max(idLC) FROM "+TABLE_LISTACANCIONES,null);
        id.moveToFirst();
        sec = id.getInt(0);
        sec= sec+1;

        Map<String, Object> song = new HashMap<>();
        song.put ("idLC", sec);
        song.put("titulo", titulo);
        song.put("artista", artista);
        song.put("album", album);
        song.put("genero", genero);
        song.put("precio", precio);
*/

        firedb = FirebaseFirestore.getInstance();
        ArrayList<Canciones> songList = new ArrayList<>();
        firedb.collection("Canciones")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Canciones> List = task.getResult().toObjects(Canciones.class);
                            for (Canciones song : List) {
                                songList.add(song);
                            }
                            int tam = songList.size();
                            tam = tam+1;

                            Map<String, Object> song = new HashMap<>();
                            song.put ("idLC", tam);
                            song.put("titulo", titulo);
                            song.put("artista", artista);
                            song.put("album", album);
                            song.put("genero", genero);
                            song.put("precio", precio);

                            firedb.collection("Canciones").document(String.valueOf(tam))
                                    .set(song)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                           // Toast.makeText(context,"Se INSERTO el registro ",Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //Toast.makeText(context,"No INSERTO el registro",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                            //Toast.makeText(context,"La COLLECTION NO Existe !!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }



    public ArrayList<Canciones>  listarcanciones()
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ArrayList<Canciones> listacanciones = new ArrayList<>();
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

    public void editarfirecancion(int id,String titulo, String artista,String album, String genero, Float precio) {

        Map<String, Object> song = new HashMap<>();
        song.put("idLC", id);
        song.put("titulo", titulo);
        song.put("artista", artista);
        song.put("album", album);
        song.put("genero", genero);
        song.put("precio", precio);

        firedb = FirebaseFirestore.getInstance();

        firedb.collection("Canciones").document(String.valueOf(id))
                .update(song)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Se ACTUALIZO correctamente ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "NO se ACTUALIZO", Toast.LENGTH_LONG).show();
                    }
                });
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


    public void eliminarfireCancion(int id) {

        firedb = FirebaseFirestore.getInstance();
        firedb.collection("Canciones").document(String.valueOf(id))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Registro se ELIMINO correctamente ",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Registro NO se ELIMINO ",Toast.LENGTH_LONG).show();
                    }
                });
    }

}
