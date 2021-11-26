package com.perafan.usuarios;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.db.DbUsuarios;
import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.entidades.Usuarios;

public class FichaUsuarioActivity extends AppCompatActivity {

    EditText eTNombre,eTApellido,eTEmail,eTPassword,eTPhone;
    Button bGuardar;
    FloatingActionButton fabEditar, fabEliminar;

    Usuarios usuario;
    int id = 0;

    CheckBox check;
    int acepta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_usuario);

        eTNombre = findViewById(R.id.eTNombre);
        eTApellido = findViewById(R.id.eTApellido);
        eTEmail = findViewById(R.id.eTEmail);
        eTPassword= findViewById(R.id.eTPassword);
        eTPhone = findViewById(R.id.eTPhone);
        check   = (CheckBox) findViewById(R.id.chk);

        bGuardar = findViewById(R.id.bguardar);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

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

        DbUsuarios dbusuarios = new DbUsuarios(FichaUsuarioActivity.this);
        usuario = dbusuarios.FichaUsuario(id);
        acepta = check.isChecked() ? 1 : 0;
        if (usuario != null) {
            eTNombre.setText(usuario.getNombre());
            eTApellido.setText(usuario.getApellidos());
            eTEmail.setText(usuario.getEmail());
            eTPassword.setText(usuario.getClave());
            eTPhone.setText(String.valueOf(usuario.getTelefono()));
            if (usuario.getAcepta() == 1) {
                check.setChecked(true);
            } else {
                check.setChecked(false);
            }

            bGuardar.setVisibility(View.INVISIBLE);
            eTNombre.setInputType(InputType.TYPE_NULL);
            eTApellido.setInputType(InputType.TYPE_NULL);
            eTPassword.setInputType(InputType.TYPE_NULL);
            eTEmail.setInputType(InputType.TYPE_NULL);
            eTPhone.setInputType(InputType.TYPE_NULL);

        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FichaUsuarioActivity.this, EditarUsuarioActivity.class);
                intent.putExtra("idU", id);
                startActivity(intent);
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FichaUsuarioActivity.this);
                builder.setMessage("¿Desea eliminar este Usuario?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dbusuarios.eliminarUsuario(id))
                                {
                                    listar();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
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


    private void listar()
    {
        Intent intent = new Intent(this,ListaUsuarioActivity.class);
        startActivity(intent);
    }

    public void salir()
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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