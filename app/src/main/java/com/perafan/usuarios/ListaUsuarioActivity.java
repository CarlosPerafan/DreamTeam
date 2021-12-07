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
import android.widget.Toast;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.adaptadores.listSongAdapter;
import com.perafan.usuarios.adaptadores.listUserAdapter;
import com.perafan.usuarios.db.DbUsuarios;
import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView listausuarios;
    ArrayList<Usuarios> listArrayusuarios;
    FloatingActionButton fabAdicionar;

    FirebaseFirestore firedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout_user);

        //Dibuja las 3 lineas del menu
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.apertura,R.string.cierre);
        toogle.syncState();
        NavigationView navigationview= (NavigationView) findViewById(R.id.nav_view);
        navigationview.setNavigationItemSelectedListener(this);


        listausuarios = findViewById(R.id.listausuarios);
        //Determina un tamaño fijo
        listausuarios.setHasFixedSize(true);


        listausuarios.setLayoutManager(new LinearLayoutManager(this));

        DbUsuarios dbusuarios = new DbUsuarios(ListaUsuarioActivity.this);
        listArrayusuarios = new ArrayList<>();

        // Estas lineas deben habilitarse para que lea de la base de datos SQLite
        // listUserAdapter adapter = new listUserAdapter(dbusuarios.listarusuarios());
        /** Codigo para leer desde la Coleccion de Firestore */
        firedb = FirebaseFirestore.getInstance();
        ArrayList<Usuarios> userList = new ArrayList<>();

        firedb.collection("Usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Usuarios> UsuariosList = task.getResult().toObjects(Usuarios.class);
                            for (Usuarios user : UsuariosList) {
                                userList.add(user);
                            }

                            listUserAdapter adapter = new listUserAdapter(userList);
                            listausuarios.setAdapter(adapter);
                            listausuarios.addItemDecoration(new DividerItemDecoration(ListaUsuarioActivity.this,DividerItemDecoration.VERTICAL));
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        /** Fin del Codigo para Firestore **/

        //listausuarios.setAdapter(adapter);  ---> Deshabilitar si se desea regresar a SQLite
        //Linea que coloca el separador entre registros
        //listausuarios.addItemDecoration(new DividerItemDecoration(ListaUsuarioActivity.this,DividerItemDecoration.VERTICAL));  ---> Deshabilitar si se desea regresar a SQLite


        fabAdicionar = findViewById(R.id.fabAdicionar);

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaUsuarioActivity.this,  RegistroUsuarioActivity.class);
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
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout_user);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}