package com.perafan.usuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.db.DbUsuarios;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button b_crear;

    Button b_registrar;
    Button b_ingresar;

    FirebaseFirestore firedb;
    private static final String TAG = RegistroUsuarioActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_registrar = (Button) findViewById(R.id.b_Reg);
        b_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reg = new Intent(getApplicationContext(), RegistroUsuarioActivity.class);
                startActivityForResult(reg, 0);

            }
        });

        b_ingresar = (Button) findViewById(R.id.b_Log);


        b_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cont = 0;
                EditText txtusu = (EditText) findViewById(R.id.eTName);
                EditText txtpass = (EditText) findViewById(R.id.eTPass);
                //Toast.makeText(getApplicationContext(), "Usuario: " + txtusu.getText().toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "Clave: " + txtpass.getText().toString(), Toast.LENGTH_LONG).show();

                DbUsuarios dbusuario = new DbUsuarios(MainActivity.this);


                firedb = FirebaseFirestore.getInstance();
                firedb.collection("Usuarios")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<Usuarios> UsuariosList = task.getResult().toObjects(Usuarios.class);
                                    int Cliente = 0;
                                    int rol = 0;
                                    boolean existe = false;
                                    String login = txtusu.getText().toString();
                                    String clave = txtpass.getText().toString();
                                    for (Usuarios user : UsuariosList) {
                                        if ((user.getEmail().equalsIgnoreCase(login)) && (login.contains("@adm")) ) {
                                             existe = true;
                                             if (user.getClave().equals(clave)){
                                                 rol = 10;
                                             }else{
                                                 rol = 1;
                                             }

                                            Cliente = user.getIdU();
                                            //Toast.makeText(getApplicationContext(), "Este es el email "+user.getEmail(), Toast.LENGTH_LONG).show();

                                        } else if (user.getEmail().equalsIgnoreCase(txtusu.getText().toString())) {

                                                existe = true;
                                                if (user.getClave().equals(clave)){
                                                    rol = 20;
                                                }else{
                                                    rol = 1;
                                                }

                                            Cliente = user.getIdU();
                                        }
                                    }

                                    switch (rol) {
                                        case 0:
                                            Toast.makeText(getApplicationContext(), "Usuario no Existe!! Registrate !!", Toast.LENGTH_LONG).show();
                                            txtusu.setText("");
                                            txtpass.setText("");
                                            txtusu.findFocus();
                                            break;
                                        case 1:
                                            Toast.makeText(getApplicationContext(), "Clave ingresada no es correcta !!", Toast.LENGTH_LONG).show();

                                            txtpass.setText("");
                                            txtpass.findFocus();
                                            break;
                                        case 10:
                                            Intent regAdmon = new Intent(getApplicationContext(), ListaUsuarioActivity.class);
                                            startActivityForResult(regAdmon, 0);
                                            break;

                                        case 20:
                                            Intent regShopper = new Intent(getApplicationContext(), ListaCompradorActivity.class);

                                            // Mandar el cliente
                                            //regShopper.putExtra("idU",Cliente);

                                            startActivityForResult(regShopper, 0);
                                            break;
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Usuario NO EXISTE, registrate!", Toast.LENGTH_LONG).show();

                                }// Cierra el IF




                            }
                        });


                    /*Cursor cursorvalidar = dbusuario.ValidarUsuario(txtusu.getText().toString());
                    if (cursorvalidar.getCount()>0) {
                        Cursor cursor = dbusuario.ConsultarAdmin(txtusu.getText().toString(), txtpass.getText().toString());
                        cont = cursor.getCount();
                        if (cursor.getCount() > 0) {
                            //Intent reg = new Intent (getApplicationContext(),RegistroSongActivity.class);
                            Intent reg = new Intent(getApplicationContext(), ListaUsuarioActivity.class);
                            startActivityForResult(reg, 0);
                        } else {
                            cursor = dbusuario.ConsultarComprador(txtusu.getText().toString(), txtpass.getText().toString());
                            cont = cursor.getCount();
                            if (cursor.getCount() > 0) {

                                Intent reg = new Intent(getApplicationContext(), ListaCompradorActivity.class);
                                startActivityForResult(reg, 0);
                            } else {
                                Toast.makeText(getApplicationContext(), "Usuario y/o Clave incorrectos" + cont, Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Usuario no EXISTE, registrate!" + cont, Toast.LENGTH_LONG).show();
                    }*/


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
        });
    }

    //Metodo que controla el click en el boton atras del dispositivo
    // Revisar metodo no cierra del todo la app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
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

        return super.onKeyDown(keyCode, event);
    }



}