package com.perafan.usuarios;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.perafan.usuarios.db.DbHelper;
import com.perafan.usuarios.db.DbUsuarios;

public class MainActivity extends AppCompatActivity {

    Button b_crear;

    Button b_registrar;
    Button b_ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_registrar = (Button) findViewById(R.id.b_Reg);
        b_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reg = new Intent (getApplicationContext(),RegistroUsuarioActivity.class);
                startActivityForResult(reg,0);

            }
        });
        b_ingresar = (Button) findViewById(R.id.b_Log);
        b_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont=0;
                EditText txtusu = (EditText) findViewById(R.id.eTName);
                EditText txtpass = (EditText) findViewById(R.id.eTPass);
                Toast.makeText(getApplicationContext(),"Usuario: "+txtusu.getText().toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Clave: "+txtpass.getText().toString(),Toast.LENGTH_LONG).show();

                DbUsuarios dbusuario = new DbUsuarios(MainActivity.this);
                try {

                    Cursor cursor = dbusuario.ConsultarAdmin(txtusu.getText().toString(), txtpass.getText().toString());
                    cont=cursor.getCount();
                    if (cursor.getCount()>0)
                    {
                        //Intent reg = new Intent (getApplicationContext(),RegistroSongActivity.class);
                        Intent reg = new Intent(getApplicationContext(), ListaUsuarioActivity.class);
                        startActivityForResult(reg,0);
                    }else{
                        cursor = dbusuario.ConsultarComprador(txtusu.getText().toString(), txtpass.getText().toString());
                        cont=cursor.getCount();
                        if (cursor.getCount()>0) {

                            Intent reg = new Intent(getApplicationContext(), ListaCompradorActivity.class);
                            startActivityForResult(reg, 0);
                        }else {
                            Toast.makeText(getApplicationContext(), "Usuario y/o Clave incorrectos" + cont, Toast.LENGTH_LONG).show();
                        }
                    }
                    txtusu.setText("");
                    txtpass.setText("");
                    txtusu.findFocus();

                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });




       //  Lineas que crean la Base de Datos por Primera vez

        /*b_crear = (Button) findViewById(R.id.b_crear);
        b_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbhelper = new DbHelper(MainActivity.this);
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                if(db != null){
                    Toast.makeText(MainActivity.this,"BASE DE DATOS CREADA",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"FALLO LA CREACION DE BASE DE DATOS",Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }
}