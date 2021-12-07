package com.perafan.usuarios;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;

import java.util.List;

public class EditarCancionActivity extends AppCompatActivity {

    FirebaseFirestore firedb;
    private static final String TAG = RegistroUsuarioActivity.class.getName();

    TextView tVsong;
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

        tVsong =(TextView) findViewById(R.id.tVsong);

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
        //cancion = dbcanciones.Fichacancion(id);

        firedb = FirebaseFirestore.getInstance();

        firedb.collection("Canciones")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Canciones> SongsList = task.getResult().toObjects(Canciones.class);
                            Canciones cancion = new Canciones();
                            for (Canciones song : SongsList) {
                                if (song.getIdLC()== id){
                                    cancion = song;
                                }
                            }

                            if (cancion != null)
                            {
                                tVsong.setText("Editar Cancion");

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
                                        try{
                                            dbcanciones.editarfirecancion(id,eTTitulo.getText().toString(),eTArtista.getText().toString(),eTAlbum.getText().toString(),eTGenero.getText().toString(),Float.parseFloat(eTPrecio.getText().toString()));
                                            Toast.makeText(EditarCancionActivity.this,"REGISTRO ACTUALIZADO",Toast.LENGTH_LONG).show();
                                            presentaregistro();

                                        }catch(Exception e){

                                            Toast.makeText(EditarCancionActivity.this,"ERROR AL ACTUALIZAR REGISTRO",Toast.LENGTH_LONG).show();
                                            e.toString();
                                        }

                                    }else
                                    {
                                        Toast.makeText(EditarCancionActivity.this,"DEBE INGRESAR LOS CAMPOS OBLIGATORIOS",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(EditarCancionActivity.this, "No hay domentos en la Collection !", Toast.LENGTH_SHORT).show();
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

    private void presentaregistro()
    {
        Intent intent = new Intent(this,ListatiendaActivity.class);
        intent.putExtra("idLC",id);
        startActivity(intent);
    }
}
