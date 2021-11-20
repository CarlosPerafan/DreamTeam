package com.perafan.usuarios;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.perafan.usuarios.adaptadores.listUserAdapter;
import com.perafan.usuarios.db.DbUsuarios;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.ArrayList;

public class ListaUsuarioActivity extends AppCompatActivity {

    RecyclerView listausuarios;
    ArrayList<Usuarios> listArrayusuarios;
    FloatingActionButton fabAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuario);

        listausuarios = findViewById(R.id.listausuarios);
        listausuarios.setLayoutManager(new LinearLayoutManager(this));

        DbUsuarios dbusuarios = new DbUsuarios(ListaUsuarioActivity.this);
        listArrayusuarios = new ArrayList<>();
        listUserAdapter adapter = new listUserAdapter(dbusuarios.listarusuarios());
        listausuarios.setAdapter(adapter);

        fabAdicionar = findViewById(R.id.fabAdicionar);

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaUsuarioActivity.this,  RegistroUsuarioActivity.class);
                startActivity(intent);
            }
        });

    }
}