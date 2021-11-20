package com.perafan.usuarios.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.ArrayList;

public class DbUsuarios extends DbHelper {
    Context context;
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


}
