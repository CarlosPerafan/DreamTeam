package com.perafan.usuarios;

import android.content.DialogInterface;
import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;

public class FichaCancionActivity extends AppCompatActivity {

    EditText eTTitulo,eTArtista,eTAlbum,eTGenero,eTPrecio;
    Button bGuardar;
    FloatingActionButton fabEditar, fabEliminar;

    Canciones cancion;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_cancion);

        eTTitulo = findViewById(R.id.eTTitulo);
        eTArtista = findViewById(R.id.eTArtista);
        eTAlbum = findViewById(R.id.eTAlbum);
        eTGenero = findViewById(R.id.eTGenero);
        eTPrecio = findViewById(R.id.eTPrecio);
        bGuardar = findViewById(R.id.bguardar);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("idLC");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("idLC");
        }

        DbCanciones dbcanciones = new DbCanciones(FichaCancionActivity.this);
        cancion = dbcanciones.Fichacancion(id);
        if (cancion != null) {
            eTTitulo.setText(cancion.getTitulo());
            eTArtista.setText(cancion.getArtista());
            eTAlbum.setText(cancion.getAlbum());
            eTGenero.setText(cancion.getGenero());
            eTPrecio.setText(String.valueOf(cancion.getPrecio()));
            bGuardar.setVisibility(View.INVISIBLE);
            eTTitulo.setInputType(InputType.TYPE_NULL);
            eTArtista.setInputType(InputType.TYPE_NULL);
            eTAlbum.setInputType(InputType.TYPE_NULL);
            eTGenero.setInputType(InputType.TYPE_NULL);
            eTPrecio.setInputType(InputType.TYPE_NULL);
        }
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FichaCancionActivity.this, EditarCancionActivity.class);
                intent.putExtra("idLC", id);  //Vlaidar el id
                startActivity(intent);
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FichaCancionActivity.this);
                builder.setMessage("¿Desea eliminar esta cancion?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 if (dbcanciones.eliminarCancion(id))
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

    private void listar()
    {
        Intent intent = new Intent(this,ListatiendaActivity.class);
        startActivity(intent);
    }

    }
