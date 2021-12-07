package com.perafan.usuarios;

import android.content.DialogInterface;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.List;

public class FichaCancionActivity extends AppCompatActivity {

    FirebaseFirestore firedb;
    private static final String TAG = RegistroUsuarioActivity.class.getName();

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

                                                    try{
                                                        dbcanciones.eliminarfireCancion(id);
                                                        listar();

                                                    }catch(Exception e){
                                                        e.toString();
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

                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(FichaCancionActivity.this, "No hay domentos en la Collection !", Toast.LENGTH_SHORT).show();
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

    private void listar()
    {
        Intent intent = new Intent(this,ListatiendaActivity.class);
        startActivity(intent);
    }

}
