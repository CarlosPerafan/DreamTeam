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
import com.perafan.usuarios.R;
import com.perafan.usuarios.entidades.Canciones;

import java.util.ArrayList;

public class listSongAdapter extends RecyclerView.Adapter<listSongAdapter.ContactoViewHolder> {

    ArrayList<Canciones> listacanciones;
    public listSongAdapter(ArrayList<Canciones> listacanciones)
    {
        this.listacanciones = listacanciones;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_item_song,null,false);  //Revisar que quite el parent.getContexto por viewGroup.getContext
              return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder contactoViewHolder, int i) {

        contactoViewHolder.tVTitulo.setText(listacanciones.get(i).getTitulo());
        contactoViewHolder.tVArtista.setText(listacanciones.get(i).getArtista());
        contactoViewHolder.tVAlbum.setText("/"+listacanciones.get(i).getAlbum());
        contactoViewHolder.tVGenero.setText("/"+listacanciones.get(i).getGenero());
        contactoViewHolder.tVPrecio.setText("/$ "+String.valueOf(listacanciones.get(i).getPrecio()));  //Revisar el parseo

    }

    @Override
    public int getItemCount() {
        return listacanciones.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView tVTitulo,tVArtista,tVAlbum,tVGenero,tVPrecio;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            tVTitulo = itemView.findViewById(R.id.tVTitulo);
            tVArtista= itemView.findViewById(R.id.tVArtista);
            tVAlbum  = itemView.findViewById(R.id.tVAlbum);
            tVGenero = itemView.findViewById(R.id.tVGenero);
            tVPrecio = itemView.findViewById(R.id.tVPrecio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, FichaCancionActivity.class);
                    intent.putExtra("idLC",listacanciones.get(getAdapterPosition()).getIdLC());
                    context.startActivity(intent);
                }
            });
        }
    }
}
