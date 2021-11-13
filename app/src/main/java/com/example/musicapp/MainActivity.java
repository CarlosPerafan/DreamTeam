package com.example.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView T1;
    Button B1;
    EditText ET1, ET2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        T1=(TextView) findViewById(R.id.t1);
        B1=(Button) findViewById(R.id.b1);
        ET1=(EditText) findViewById(R.id.et1);
        ET2=(EditText) findViewById(R.id.et2);
        /*B1.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                T1.setText(ET1.getText().toString());
            }
        });*/
    }

    public void Abrir2Activity(View view) {
        if (ET1.getText().toString().equals("felipe.patino@gmail.com")&& ET2.getText().toString().equals("Marivale"))
        {
            Intent SA=new Intent(view.getContext(), Activity2.class);
            startActivityForResult(SA, 0);
        }
        else
        {
            T1.setText(R.string.msj2);
        }
    }
}