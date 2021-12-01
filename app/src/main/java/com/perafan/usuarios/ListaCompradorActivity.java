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
import android.widget.TextView;
import android.widget.Toast;

import com.perafan.usuarios.adaptadores.listShopAdapter;
import com.perafan.usuarios.adaptadores.listSongAdapter;
import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;

import java.util.ArrayList;

public class ListaCompradorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    RecyclerView listacanciones;
    ArrayList<Canciones> listArraycanciones;

    TextView eTCant;
    FloatingActionButton fabVerCart;
    ArrayList<Canciones> carroCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comprador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout_shopping);

        eTCant = findViewById(R.id.eTCant);

        //Dibuja las 3 lineas del menu
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.apertura,R.string.cierre);
        toogle.syncState();
        NavigationView navigationview= (NavigationView) findViewById(R.id.nav_view);
        navigationview.setNavigationItemSelectedListener(this);

        listacanciones = findViewById(R.id.listacanciones);
        fabVerCart = findViewById(R.id.fabVerCart);
        //Determina un tamaño fijo
        listacanciones.setHasFixedSize(true);
        listacanciones.setLayoutManager(new LinearLayoutManager(this));

        DbCanciones dbcanciones = new DbCanciones(ListaCompradorActivity.this);
        listArraycanciones = new ArrayList<>();
        carroCompras = new ArrayList<>();
        listShopAdapter adapter = new listShopAdapter(ListaCompradorActivity.this,dbcanciones.listarcanciones(),eTCant,carroCompras,fabVerCart);
        listacanciones.setAdapter(adapter);
        //Linea que coloca el separador entre registros
        listacanciones.addItemDecoration(new DividerItemDecoration(ListaCompradorActivity.this,DividerItemDecoration.VERTICAL));




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.menuCompra:

                Intent intent1 = new Intent(this,ListaCompradorActivity.class);
                startActivity(intent1);
                break;
            case R.id.menuLista:

                Intent intent2 = new Intent(this,ListaCompradorActivity.class);  //Reemplazar con la lista solo de presentacion
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout_shopping);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}