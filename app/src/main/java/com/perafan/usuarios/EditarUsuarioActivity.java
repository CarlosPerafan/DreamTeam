package com.perafan.usuarios;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.perafan.usuarios.db.DbUsuarios;
import com.perafan.usuarios.entidades.Usuarios;

public class EditarUsuarioActivity  extends AppCompatActivity {

    TextView tVuser;
    EditText eTNombre,eTApellido,eTEmail,eTPassword,eTPhone;
    Button bGuardar;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto= false;
    Usuarios usuario;
    int id = 0;
    CheckBox chk;
    int acepta;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_usuario);

        tVuser =(TextView) findViewById(R.id.tVuser);

        eTNombre = (EditText) findViewById(R.id.eTNombre);
        eTApellido = (EditText) findViewById(R.id.eTApellido);
        eTEmail = (EditText) findViewById(R.id.eTEmail);
        eTPassword = (EditText) findViewById(R.id.eTPassword);
        eTPhone = (EditText) findViewById(R.id.eTPhone);
        chk = (CheckBox) findViewById(R.id.chk);


        bGuardar = findViewById(R.id.bguardar);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("idU");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("idU");
        }

        DbUsuarios dbusuarios = new DbUsuarios(EditarUsuarioActivity.this);
        usuario = dbusuarios.FichaUsuario(id);
        if (usuario != null) {

            tVuser.setText("Editar Usuario");
            eTNombre.setText(usuario.getNombre());
            eTApellido.setText(usuario.getApellidos());
            eTEmail.setText(usuario.getEmail());
            eTPassword.setText(usuario.getClave());
            eTPhone.setText(String.valueOf(usuario.getTelefono()));
            if (usuario.getAcepta() == 1) {
                chk.setChecked(true);
            } else {
                chk.setChecked(false);
            }


        }

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acepta = chk.isChecked() ? 1 : 0;
                //acepta =1;

                if(!eTNombre.getText().toString().equals("") && !eTApellido.getText().toString().equals("") && !eTEmail.getText().toString().equals("") && !eTPassword.getText().toString().equals("") && !eTPhone.getText().toString().equals(""))
                {
                    correcto = dbusuarios.editarusuario(id,eTNombre.getText().toString(),eTApellido.getText().toString(),eTEmail.getText().toString(),eTPassword.getText().toString(),Integer.parseInt(eTPhone.getText().toString()),acepta);
                    if(correcto== true)
                    {
                        Toast.makeText(EditarUsuarioActivity.this,"REGISTRO ACTUALIZADO",Toast.LENGTH_LONG).show();
                        presentaregistro();
                    }else
                    {
                        Toast.makeText(EditarUsuarioActivity.this,"ERROR AL ACTUALIZAR REGISTRO",Toast.LENGTH_LONG).show();
                    }
                }else
                {
                    Toast.makeText(EditarUsuarioActivity.this,"DEBE INGRESAR LOS CAMPOS OBLIGATORIOS",Toast.LENGTH_LONG).show();
                }
            }
        });

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

   private void presentaregistro()
    {
        Intent intent = new Intent(this,ListaUsuarioActivity.class);
        intent.putExtra("idU",id);
        startActivity(intent);
    }

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
