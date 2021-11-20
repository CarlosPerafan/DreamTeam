package com.perafan.usuarios;

import android.content.Intent;
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

import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.db.DbHelper;

public class RegistroSongActivity extends AppCompatActivity {

    Button guardar;
    EditText tit,art,alb,gen,prc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_song);

        guardar = (Button) findViewById(R.id.bguardar);
        tit     = (EditText) findViewById(R.id.eTTitulo);
        art     = (EditText) findViewById(R.id.eTArtista);
        alb     = (EditText) findViewById(R.id.eTAlbum);
        gen     = (EditText) findViewById(R.id.eTGenero);
        prc     = (EditText) findViewById(R.id.eTPrecio);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbCanciones dbCanciones = new DbCanciones(RegistroSongActivity.this);
                long idrow;
                try {
                    idrow = dbCanciones.insertarcancion(tit.getText().toString(),
                            art.getText().toString(),
                            alb.getText().toString(),
                            gen.getText().toString(),
                            Float.parseFloat(prc.getText().toString()));

                    if (idrow > 0)
                    {
                        Toast.makeText(getApplicationContext(),"Canción Almacenada Exitosamente "+idrow,Toast.LENGTH_LONG).show();
                        reset();
                        listar();

                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Canción NO fue Almacenada "+idrow,Toast.LENGTH_LONG).show();
                    }

                }catch(Exception ex)
                {
                    ex.toString();
                }
            }
        });

    }
    private void reset()
    {
        tit.setText("");
        art.setText("");
        alb.setText("");
        gen.setText("");
        prc.setText("");
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ppal,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuCancion:
                irlistaCancion();
                return true;
            case R.id.menUsuario:
                irlistaUsuario();
                return true;

            default: return super.onOptionsItemSelected(item);

        }

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

    private void listar()
    {
        Intent intent = new Intent(this,ListatiendaActivity.class); //ojo
        startActivity(intent);
    }


}