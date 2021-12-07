package com.perafan.usuarios.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.RegistroUsuarioActivity;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUsuarios extends DbHelper {
    Context context;

    FirebaseFirestore firedb;
    private static final String TAG = RegistroUsuarioActivity.class.getName();


    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertarusuario(String nombre, String apellidos,String email, String clave, Integer telefono,Integer acepta)
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long newRowId=0;
        try {
            ContentValues valores = new ContentValues();
            valores.put("nombre", nombre);
            valores.put("apellidos", apellidos);
            valores.put("email", email);
            valores.put("clave", clave);
            valores.put("telefono", telefono);
            valores.put("acepta", acepta);

            newRowId = db.insert(TABLE_USUARIOS, null, valores);
        }catch(Exception ex)
        {
            ex.toString();
        }
        return newRowId;

    }

    public void insertaruserfire(String nombre, String apellidos,String email, String clave, Integer telefono,Integer acepta){
/* Se conserva si por algo
        Cursor id;
        int sec =0;
        id = this.getReadableDatabase().rawQuery("SELECT max(idU) FROM "+TABLE_USUARIOS,null);
        id.moveToFirst();
        sec = id.getInt(0);
        sec= sec+1;

        Map<String, Object> user = new HashMap<>();
        user.put ("idU", sec);
        user.put("nombre", nombre);
        user.put("apellidos", apellidos);
        user.put("email", email);
        user.put("clave", clave);
        user.put("telefono", telefono);
        user.put("acepta", acepta);

*/
        firedb = FirebaseFirestore.getInstance();

        ArrayList<Usuarios> userList = new ArrayList<>();

        firedb.collection("Usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Usuarios> UsuariosList = task.getResult().toObjects(Usuarios.class);
                            for (Usuarios user : UsuariosList) {
                                userList.add(user);
                            }
                            int tam = userList.size();
                            tam = tam+1;
                            //Toast.makeText(context,"El tamano de la coleccion "+userList.size(),Toast.LENGTH_LONG).show();

                            Map<String, Object> user = new HashMap<>();
                            user.put ("idU", tam);
                            user.put("nombre", nombre);
                            user.put("apellidos", apellidos);
                            user.put("email", email);
                            user.put("clave", clave);
                            user.put("telefono", telefono);
                            user.put("acepta", acepta);

                            firedb.collection("Usuarios").document(String.valueOf(tam))
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Toast.makeText(context,"Se INSERTO el registro ",Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                           // Toast.makeText(context,"No INSERTO el registro",Toast.LENGTH_LONG).show();
                                        }
                                    });


                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(context,"La COLLECTION NO Existe !!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public Cursor ValidarUsuario(String email){

        // Para SQLite
        Cursor micursor = null;
        try {

            micursor = this.getReadableDatabase().query(TABLE_USUARIOS, null,"Email like '" + email + "'"  , null, null, null, null);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return micursor;

    }
    //Validar si el usuario existe y es administrador
    public Cursor ConsultarAdmin(String user, String pass) throws SQLException
    {
        Cursor micursor = null;
        try {
            String[] campos = {"idU", "nombre", "apellidos", "email", "clave", "telefono","acepta"};
            micursor = this.getReadableDatabase().query(TABLE_USUARIOS, null,"Email like '" + user + "'  and email like '%@adm%' and Clave like  '" + pass + "'", null, null, null, null);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return micursor;
    }

    //Validar si el usuario existe
    public Cursor ConsultarComprador(String user, String pass) throws SQLException
    {
        Cursor micursor = null;
        try {
            String[] campos = {"idU", "nombre", "apellidos", "email", "clave", "telefono","acepta"};
            micursor = this.getReadableDatabase().query(TABLE_USUARIOS, null,"Email like '" + user + "'  and Clave like  '" + pass + "'", null, null, null, null);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return micursor;
    }

    public ArrayList<Usuarios> listarusuarios()
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Usuarios>  listausuarios = new ArrayList<>();
        Usuarios usuario = null;
        Cursor cursorusuarios = null;

        cursorusuarios = db.rawQuery("SELECT * FROM "+TABLE_USUARIOS,null);

        if (cursorusuarios.moveToFirst())
        {
            do{
                usuario = new Usuarios();
                usuario.setIdU(cursorusuarios.getInt(0));
                usuario.setNombre(cursorusuarios.getString(1));
                usuario.setApellidos(cursorusuarios.getString(2));
                usuario.setEmail(cursorusuarios.getString(3));
                usuario.setClave(cursorusuarios.getString(4));
                usuario.setTelefono(cursorusuarios.getInt(5));
                usuario.setAcepta(cursorusuarios.getInt(6));


                listausuarios.add(usuario);

            } while(cursorusuarios.moveToNext());
        }
        cursorusuarios.close();
        return listausuarios;

    }

    public Usuarios  FichaUsuario(int id)
    {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Usuarios usuario = null;
        Cursor cursorusuarios = null;

        cursorusuarios = db.rawQuery("SELECT * FROM "+TABLE_USUARIOS+" WHERE idU = "+id +" LIMIT 1",null);

        if (cursorusuarios.moveToFirst())
        {
            usuario = new Usuarios();
            usuario.setIdU(cursorusuarios.getInt(0));
            usuario.setNombre(cursorusuarios.getString(1));
            usuario.setApellidos(cursorusuarios.getString(2));
            usuario.setEmail(cursorusuarios.getString(3));
            usuario.setClave(cursorusuarios.getString(4));
            usuario.setTelefono(cursorusuarios.getInt(5));
            usuario.setAcepta(cursorusuarios.getInt(6));


        }
        cursorusuarios.close();
        return usuario;

    }

    public boolean editarusuario(int id,String nombre, String apellidos,String email,String clave, int telefono, int acepta)
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        boolean correcto= false;
        try {
            db.execSQL("UPDATE " +TABLE_USUARIOS + " SET nombre = '"+nombre +"', apellidos = '"+apellidos +"', email = '"+email +"', clave = '"+clave+"', telefono = '"+telefono +"', acepta = '"+acepta+"' WHERE idU = '"+ id+"' ");
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

    public void editarfireusuario(int id,String nombre, String apellidos,String email,String clave, int telefono, int acepta)
    {

        firedb = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put ("idU", id);
        user.put("nombre", nombre);
        user.put("apellidos", apellidos);
        user.put("email", email);
        user.put("clave", clave);
        user.put("telefono", telefono);
        user.put("acepta", acepta);


        firedb.collection("Usuarios").document(String.valueOf(id))
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context,"Se ACTUALIZO correctamente ",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context,"NO se ACTUALIZO",Toast.LENGTH_LONG).show();
                    }
                });

    }

    public boolean eliminarUsuario(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_USUARIOS + " WHERE idU = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public void eliminarfireUsuario(int id) {

        firedb = FirebaseFirestore.getInstance();

        firedb.collection("Usuarios").document(String.valueOf(id))
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
