package com.perafan.usuarios.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perafan.usuarios.FichaCancionActivity;
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

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder contactoViewHolder, int i) {

            contactoViewHolder.tVNombre.setText(listausuarios.get(i).getNombre());
            contactoViewHolder.tVApellidos.setText(listausuarios.get(i).getApellidos());
            contactoViewHolder.tVEmail.setText(listausuarios.get(i).getEmail());
            contactoViewHolder.tVTelefono.setText("/"+listausuarios.get(i).getTelefono());

            }

    @Override
    public int getItemCount() {
            return listausuarios.size();
            }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView tVNombre,tVApellidos,tVEmail,tVPassword, tVTelefono;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

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
                    intent.putExtra("idU",listausuarios.get(getAdapterPosition()).getIdU());
                    context.startActivity(intent);
                }
            });
        }
    }
}
