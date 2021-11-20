package com.perafan.usuarios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;

public class EditarCancionActivity extends AppCompatActivity {

    EditText eTTitulo,eTArtista,eTAlbum,eTGenero,eTPrecio;
    Button bGuardar;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto= false;
    Canciones cancion;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_cancion);

        eTTitulo    = findViewById(R.id.eTTitulo);
        eTArtista   = findViewById(R.id.eTArtista);
        eTAlbum     = findViewById(R.id.eTAlbum);
        eTGenero    = findViewById(R.id.eTGenero);
        eTPrecio    = findViewById(R.id.eTPrecio);
        bGuardar    = findViewById(R.id.bguardar);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if(savedInstanceState== null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                id = Integer.parseInt(null);
            }else
            {
                id = extras.getInt("idLC");
            }
        }else
        {
            id= (int) savedInstanceState.getSerializable("idLC");
        }

        DbCanciones dbcanciones = new DbCanciones(EditarCancionActivity.this);
        cancion = dbcanciones.Fichacancion(id);
        if (cancion != null)
        {
            eTTitulo.setText(cancion.getTitulo());
            eTArtista.setText(cancion.getArtista());
            eTAlbum.setText(cancion.getAlbum());
            eTGenero.setText(cancion.getGenero());
            eTPrecio.setText(String.valueOf(cancion.getPrecio()));
        }
        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!eTTitulo.getText().toString().equals("") && !eTArtista.getText().toString().equals("") && !eTAlbum.getText().toString().equals("") && !eTGenero.getText().toString().equals("") && Float.parseFloat(eTPrecio.getText().toString())!=0)
                {
                    correcto = dbcanciones.editarcancion(id,eTTitulo.getText().toString(),eTArtista.getText().toString(),eTAlbum.getText().toString(),eTGenero.getText().toString(),Float.parseFloat(eTPrecio.getText().toString()));
                    if(correcto== true)
                    {
                        Toast.makeText(EditarCancionActivity.this,"REGISTRO ACTUALIZADO",Toast.LENGTH_LONG).show();
                        presentaregistro();
                    }else
                    {
                        Toast.makeText(EditarCancionActivity.this,"ERROR AL ACTUALIZAR REGISTRO",Toast.LENGTH_LONG).show();
                    }
                }else
                {
                    Toast.makeText(EditarCancionActivity.this,"DEBE INGRESAR LOS CAMPOS OBLIGATORIOS",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void presentaregistro()
    {
        Intent intent = new Intent(this,ListatiendaActivity.class);
        intent.putExtra("idLC",id);
        startActivity(intent);
    }
}
