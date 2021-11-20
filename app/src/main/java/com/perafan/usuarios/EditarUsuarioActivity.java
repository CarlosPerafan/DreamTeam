package com.perafan.usuarios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.perafan.usuarios.db.DbUsuarios;
import com.perafan.usuarios.entidades.Usuarios;

public class EditarUsuarioActivity  extends AppCompatActivity {

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
   private void presentaregistro()
    {
        Intent intent = new Intent(this,ListaUsuarioActivity.class);
        intent.putExtra("idU",id);
        startActivity(intent);
    }

}
