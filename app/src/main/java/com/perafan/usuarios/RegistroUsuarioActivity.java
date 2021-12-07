package com.perafan.usuarios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.perafan.usuarios.adaptadores.listUserAdapter;
import com.perafan.usuarios.db.DbUsuarios;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.List;
import java.util.regex.Pattern;

public class RegistroUsuarioActivity extends AppCompatActivity {
    FirebaseFirestore firedb;
    private static final String TAG = RegistroUsuarioActivity.class.getName();


    Button guardar;
    CheckBox check;
    EditText nom,apell,corr,clave,tel;
    int acepta;

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrousuario);

        guardar = (Button) findViewById(R.id.bguardar);
        nom     = (EditText) findViewById(R.id.eTNombre);
        apell   = (EditText) findViewById(R.id.eTApellido);
        corr    = (EditText) findViewById(R.id.eTEmail);
        clave   = (EditText) findViewById(R.id.eTPassword);
        tel     = (EditText) findViewById(R.id.eTPhone);
        check   = (CheckBox) findViewById(R.id.chk);




        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbUsuarios dbUsuarios = new DbUsuarios(RegistroUsuarioActivity.this);
                long idrow;

                acepta = check.isChecked() ? 1 : 0;
                boolean existe = false;

                if( checkEmail(corr.getText().toString())){

                firedb = FirebaseFirestore.getInstance();
                firedb.collection("Usuarios")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<Usuarios> UsuariosList = task.getResult().toObjects(Usuarios.class);
                                     boolean existe= false;
                                    for (Usuarios user : UsuariosList) {
                                        if(user.getEmail().equalsIgnoreCase(corr.getText().toString())){
                                            existe = true;
                                        }
                                       // Toast.makeText(getApplicationContext(), "Este es el email "+user.getEmail()+" --> "+corr.getText().toString(), Toast.LENGTH_LONG).show();
                                    }

                                    if(!existe){
                                        try {
                                            dbUsuarios.insertaruserfire(nom.getText().toString(),
                                                    apell.getText().toString(),
                                                    corr.getText().toString(),
                                                    clave.getText().toString(),
                                                    Integer.parseInt(tel.getText().toString()),
                                                    acepta);  //Ojo resolver el problema
                                            irlistaUsuario();
                                            Toast.makeText(getApplicationContext(), "Usuario Almacenado Exitosamente ", Toast.LENGTH_LONG).show();

                                        }catch(Exception ex) {

                                            Toast.makeText(getApplicationContext(), "Ocurrió un error. Usuario NO fue Almacenado " , Toast.LENGTH_LONG).show();
                                            ex.toString();

                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "El usuario ya existe en la COLLECTION.", Toast.LENGTH_LONG).show();
                                        irmain();

                                    }


                                } else {
                                    //Log.d(TAG, "Error getting documents: ", task.getException());
                                    Toast.makeText(getApplicationContext(), "No existe la COLLECTION", Toast.LENGTH_LONG).show();
                                    irmain();

                                }
                            }
                        });

                }else{
                    Toast.makeText(getApplicationContext(), "El E-mail no es valido!!",Toast.LENGTH_LONG).show();
                }




                //Para SQLite
           /*     Cursor cursor = dbUsuarios.ValidarUsuario(corr.getText().toString());
                cont=cursor.getCount();
                if (cursor.getCount()== 0) {

                    try {
                        idrow = dbUsuarios.insertarusuario(nom.getText().toString(),
                                apell.getText().toString(),
                                corr.getText().toString(),
                                clave.getText().toString(),
                                Integer.parseInt(tel.getText().toString()),
                                acepta);  //Ojo resolver el problema

                        if (idrow > 0) {

                            Toast.makeText(getApplicationContext(), "Usuario Almacenado Exitosamente " + idrow + " - " + acepta, Toast.LENGTH_LONG).show();
                            irlistaUsuario();

                        } else {
                            Toast.makeText(getApplicationContext(), "Ocurrió un error. Usuario NO fue Almacenado " + idrow + " - ", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception ex) {
                        ex.toString();
                    }

                }else {  //Viene del if de validadcion de usuario

                    Toast.makeText(getApplicationContext(), "El usuario ya existe en la BD.", Toast.LENGTH_LONG).show();
                    irmain();
                }*/
            }
        });
    }



    private void irlistaCancion()
    {
        Intent intent = new Intent(this,ListatiendaActivity.class); //ojo
        startActivity(intent);
    }
    private void irlistaUsuario()
    {
        Intent intentUser = new Intent(this,ListaUsuarioActivity.class);
        startActivity(intentUser);
    }

    private void irmain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nivel_dos,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
                salir();
                return true;

            default: return super.onOptionsItemSelected(item);

        }

    }
    // Revisar metodo no cierra del todo la app

    public void salir()
    {
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

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }



    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}