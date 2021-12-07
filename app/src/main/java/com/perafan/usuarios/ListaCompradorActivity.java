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
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.adaptadores.listShopAdapter;
import com.perafan.usuarios.adaptadores.listSongAdapter;
import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;

import java.util.ArrayList;
import java.util.List;

public class ListaCompradorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore firedb;

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

                            listShopAdapter adapter = new listShopAdapter(ListaCompradorActivity.this,songList,eTCant,carroCompras,fabVerCart);
                            listacanciones.setAdapter(adapter);
                            listacanciones.addItemDecoration(new DividerItemDecoration(ListaCompradorActivity.this,DividerItemDecoration.VERTICAL));
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        // listShopAdapter adapter = new listShopAdapter(ListaCompradorActivity.this,dbcanciones.listarcanciones(),eTCant,carroCompras,fabVerCart);
        //listacanciones.setAdapter(adapter);
        //Linea que coloca el separador entre registros





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