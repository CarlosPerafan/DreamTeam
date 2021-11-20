package com.perafan.usuarios;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.perafan.usuarios.db.DbCanciones;
import com.perafan.usuarios.entidades.Canciones;
import com.perafan.usuarios.adaptadores.listSongAdapter;

import java.util.ArrayList;

public class ListatiendaActivity extends AppCompatActivity {

    RecyclerView listacanciones;
    ArrayList<Canciones> listArraycanciones;

    FloatingActionButton fabAdicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listatienda);

        listacanciones = findViewById(R.id.listacanciones);
        listacanciones.setLayoutManager(new LinearLayoutManager(this));

        DbCanciones dbcanciones = new DbCanciones(ListatiendaActivity.this);
        listArraycanciones = new ArrayList<>();
        listSongAdapter adapter = new listSongAdapter(dbcanciones.listarcanciones());
        listacanciones.setAdapter(adapter);

        fabAdicionar = findViewById(R.id.fabAdicionar);

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListatiendaActivity.this, RegistroSongActivity.class);
                startActivity(intent);
            }
        });

    }


}