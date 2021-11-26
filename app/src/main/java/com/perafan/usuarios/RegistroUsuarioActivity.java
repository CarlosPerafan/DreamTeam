package com.perafan.usuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.perafan.usuarios.db.DbUsuarios;

public class RegistroUsuarioActivity extends AppCompatActivity {


    Button guardar;
    CheckBox check;
    EditText nom,apell,corr,clave,tel;
    int acepta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrousuario);

        guardar = (Button) findViewById(R.id.bguardar);
        nom     = (EditText) findViewById(R.id.eTNombre);
        apell   = (EditText) findViewById(R.id.eTApellido);
        corr    = (EditText) findViewById(R.id.eTEmail);
        clave   = (EditText) findViewById(R.id.eTPassword);
        tel     = (EditText) findViewById(R.id.eTPhone);
        check   = (CheckBox) findViewById(R.id.chk);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbUsuarios dbUsuarios = new DbUsuarios(RegistroUsuarioActivity.this);
                long idrow;
                int cont=0;
                acepta = check.isChecked() ? 1 : 0;
                boolean existe = false;

                Cursor cursor = dbUsuarios.ValidarUsuario(corr.getText().toString());
                cont=cursor.getCount();
                if (cursor.getCount()== 0) {

                    try {
                        idrow = dbUsuarios.insertarusuario(nom.getText().toString(),
                                apell.getText().toString(),
                                corr.getText().toString(),
                                clave.getText().toString(),
                                Integer.parseInt(tel.getText().toString()),
                                acepta);  //Ojo resolver el problema

                        if (idrow > 0) {

                            Toast.makeText(getApplicationContext(), "Usuario Almacenado Exitosamente " + idrow + " - " + acepta, Toast.LENGTH_LONG).show();
                            irlistaUsuario();

                        } else {
                            Toast.makeText(getApplicationContext(), "Ocurrió un error. Usuario NO fue Almacenado " + idrow + " - ", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception ex) {
                        ex.toString();
                    }

                }else {  //Viene del if de validadcion de usuario

                    Toast.makeText(getApplicationContext(), "El usuario ya existe en la BD.", Toast.LENGTH_LONG).show();
                    irmain();
                }
            }
        });
    }



    private void irlistaCancion()
    {
        Intent intent = new Intent(this,ListatiendaActivity.class); //ojo
        startActivity(intent);
    }
    private void irlistaUsuario()
    {
        Intent intent = new Intent(this,ListaUsuarioActivity.class);
        startActivity(intent);
    }

    private void irmain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nivel_dos,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
                salir();
                return true;

            default: return super.onOptionsItemSelected(item);

        }

    }
    // Revisar metodo no cierra del todo la app

    public void salir()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea salir de MusicApp ?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}