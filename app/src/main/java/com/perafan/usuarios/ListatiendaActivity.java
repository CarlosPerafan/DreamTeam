package com.perafan.usuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.adaptadores.listSongAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListatiendaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore firedb;

    RecyclerView listacanciones;
    ArrayList<Canciones> listArraycanciones;

    FloatingActionButton fabAdicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listatienda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout_tienda);

        //Dibuja las 3 lineas del menu
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.apertura,R.string.cierre);
        toogle.syncState();
        NavigationView navigationview= (NavigationView) findViewById(R.id.nav_view);
        navigationview.setNavigationItemSelectedListener(this);



        listacanciones = findViewById(R.id.listacanciones);
        //Determina un tamaño fijo
        listacanciones.setHasFixedSize(true);
        listacanciones.setLayoutManager(new LinearLayoutManager(this));

        DbCanciones dbcanciones = new DbCanciones(ListatiendaActivity.this);
        listArraycanciones = new ArrayList<>();
        // Estas lineas deben habilitarse para que lea de la base de datos SQLite
        //listSongAdapter adapter = new listSongAdapter(dbcanciones.listarcanciones());
        /** Codigo para leer desde la Coleccion de Firestore */
        firedb = FirebaseFirestore.getInstance();
        ArrayList<Canciones> songList = new ArrayList<>();

        firedb.collection("Canciones")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Canciones> CancionesList = task.getResult().toObjects(Canciones.class);
                            for (Canciones cancion : CancionesList) {
                                songList.add(cancion);
                            }
                            listSongAdapter adapter = new listSongAdapter(songList);
                            listacanciones.setAdapter(adapter);
                            listacanciones.addItemDecoration(new DividerItemDecoration(ListatiendaActivity.this,DividerItemDecoration.VERTICAL));
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        /** Fin del Codifo para Firestore **/
        //listacanciones.setAdapter(adapter);  ---> Deshabilitar si se desea regresar a SQLite
        //Linea que coloca el separador entre registros
        //listacanciones.addItemDecoration(new DividerItemDecoration(ListatiendaActivity.this,DividerItemDecoration.VERTICAL));  ---> Deshabilitar si se desea regresar a SQLite

        fabAdicionar = findViewById(R.id.fabAdicionar);

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListatiendaActivity.this, RegistroSongActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.menUsuario:

                Intent intent1 = new Intent(this,ListaUsuarioActivity.class);
                startActivity(intent1);
                break;
            case R.id.menuCancion:

                Intent intent2 = new Intent(this,ListatiendaActivity.class);
                startActivity(intent2);
                break;
            case R.id.logout:

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout_tienda);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}