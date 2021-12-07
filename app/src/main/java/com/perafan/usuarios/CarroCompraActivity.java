package com.perafan.usuarios;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.perafan.usuarios.adaptadores.shopCartAdapter;
import com.perafan.usuarios.entidades.Canciones;

import java.util.ArrayList;

public class CarroCompraActivity extends AppCompatActivity {

    ArrayList<Canciones> carroCompras = new ArrayList<>();
    RecyclerView listacarro;
    TextView eTTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro_compra);
        //getSupportActionBar().hide();

        carroCompras = (ArrayList<Canciones>)getIntent().getSerializableExtra("carroCompras");

        listacarro = findViewById(R.id.listacarro);
        listacarro.setLayoutManager(new LinearLayoutManager(this));
        eTTotal = findViewById(R.id.eTTotal);

        shopCartAdapter adapter = new shopCartAdapter(CarroCompraActivity.this,carroCompras,eTTotal);
        listacarro.setAdapter(adapter);


    }
}