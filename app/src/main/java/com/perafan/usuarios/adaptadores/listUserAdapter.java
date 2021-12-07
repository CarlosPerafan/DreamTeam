package com.perafan.usuarios.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.perafan.usuarios.FichaUsuarioActivity;
import com.perafan.usuarios.R;
import com.perafan.usuarios.entidades.Usuarios;

import java.util.ArrayList;

public class listUserAdapter extends RecyclerView.Adapter<listUserAdapter.ContactoViewHolder> {

    ArrayList<Usuarios> listausuarios;
    public listUserAdapter(ArrayList<Usuarios> listausuarios)
        {
        this.listausuarios = listausuarios;
        }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_item_user,null,false);
            return new ContactoViewHolder(view);
            }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder contactoViewHolder, int i) {
        //contactoViewHolder.tVid.setText(listausuarios.get(i).getIdU());
            contactoViewHolder.tVNombre.setText(listausuarios.get(i).getNombre());
            contactoViewHolder.tVApellidos.setText("  "+listausuarios.get(i).getApellidos());
            contactoViewHolder.tVEmail.setText(listausuarios.get(i).getEmail());
            contactoViewHolder.tVTelefono.setText("/"+listausuarios.get(i).getTelefono());

            }

    @Override
    public int getItemCount() {
            return listausuarios.size();
            }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView tVNombre,tVApellidos,tVEmail,tVPassword, tVTelefono, tVid;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            //tVid = itemView.findViewById(R.id.tVid);
            tVNombre    = itemView.findViewById(R.id.tVNombre);
            tVApellidos = itemView.findViewById(R.id.tVApellidos);
            tVEmail     = itemView.findViewById(R.id.tVEmail);
            tVPassword  = itemView.findViewById(R.id.tVPassword);
            tVTelefono  = itemView.findViewById(R.id.tVTelefono);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, FichaUsuarioActivity.class);

                    //Toast.makeText(context,"Este es el IdU que selecciono "+listausuarios.get(getAdapterPosition()).getIdU(),Toast.LENGTH_LONG);
                    intent.putExtra("idU",listausuarios.get(getAdapterPosition()).getIdU());
                    context.startActivity(intent);
                }
            });
        }
    }

}
