package com.perafan.usuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.adaptadores.listSongAdapter;

import java.util.ArrayList;

public class ListatiendaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        listSongAdapter adapter = new listSongAdapter(dbcanciones.listarcanciones());
        listacanciones.setAdapter(adapter);
        //Linea que coloca el separador entre registros
        listacanciones.addItemDecoration(new DividerItemDecoration(ListatiendaActivity.this,DividerItemDecoration.VERTICAL));

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