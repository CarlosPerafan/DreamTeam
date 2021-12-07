package com.perafan.usuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.List;

public class RegistroSongActivity extends AppCompatActivity {

    FirebaseFirestore firedb;
    private static final String TAG = RegistroUsuarioActivity.class.getName();
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
                boolean existe = false;

                firedb = FirebaseFirestore.getInstance();
                firedb.collection("Canciones")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<Canciones> SongsList = task.getResult().toObjects(Canciones.class);
                                    boolean existe= false;
                                    for (Canciones song: SongsList) {
                                        if(song.getTitulo().equalsIgnoreCase(tit.getText().toString())&& (song.getArtista().equalsIgnoreCase(art.getText().toString())) &&(song.getGenero().equalsIgnoreCase(gen.getText().toString()))){
                                            existe = true;
                                        }
                                        // Toast.makeText(getApplicationContext(), "Este es el email "+user.getEmail()+" --> "+corr.getText().toString(), Toast.LENGTH_LONG).show();
                                    }

                                    if(!existe) {

                                        try {
                                            dbCanciones.insertarsongfire(tit.getText().toString(),
                                                    art.getText().toString(),
                                                    alb.getText().toString(),
                                                    gen.getText().toString(),
                                                    Float.parseFloat(prc.getText().toString()));

                                            Toast.makeText(getApplicationContext(), "Canción Almacenada Exitosamente ", Toast.LENGTH_LONG).show();
                                            reset();
                                            listar();

                                        } catch (Exception ex) {
                                            Toast.makeText(getApplicationContext(), "Canción NO fue Almacenada ", Toast.LENGTH_LONG).show();
                                            ex.toString();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "La Canción YA existe en la COLLECTION.", Toast.LENGTH_LONG).show();
                                        reset();
                                        listar();
                                    }
                                } else {
                                    //Log.d(TAG, "Error getting documents: ", task.getException());
                                    Toast.makeText(getApplicationContext(), "No existe la COLLECTION", Toast.LENGTH_LONG).show();
                                    irmain();

                                }
                            }
                        });




                /* SQLite
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
                }*/
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

    private void irmain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }



}