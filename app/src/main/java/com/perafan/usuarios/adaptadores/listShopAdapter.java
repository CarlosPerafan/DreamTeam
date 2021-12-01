package com.perafan.usuarios.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.perafan.usuarios.CarroCompraActivity;
import com.perafan.usuarios.R;
import com.perafan.usuarios.entidades.Canciones;

import java.io.Serializable;
import java.util.ArrayList;

public class  listShopAdapter extends RecyclerView.Adapter<listShopAdapter.ContactoViewHolder> {

    ArrayList<Canciones> listacanciones;

    Context context;
    ArrayList<Canciones> carroCompras;
    TextView eTCant;
    FloatingActionButton fabVerCart;

    public listShopAdapter(Context context, ArrayList<Canciones> listacanciones,TextView eTCant, ArrayList<Canciones> carroCompras,FloatingActionButton fabVerCart)
    {
        this.context = context;
        this.listacanciones = listacanciones;
        this.carroCompras = carroCompras;
        this.eTCant = eTCant;
        this.fabVerCart= fabVerCart;
    }

    @NonNull
    @Override
    public listShopAdapter.ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_item_shopping,null,false);  //Revisar que quite el parent.getContexto por viewGroup.getContext
        return new listShopAdapter.ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ContactoViewHolder contactoViewHolder, @SuppressLint("RecyclerView") int i) {
        contactoViewHolder.tVTitulo.setText(listacanciones.get(i).getTitulo());
        contactoViewHolder.tVArtista.setText(listacanciones.get(i).getArtista());
        contactoViewHolder.tVAlbum.setText("/"+listacanciones.get(i).getAlbum());
        contactoViewHolder.tVGenero.setText("/"+listacanciones.get(i).getGenero());
        contactoViewHolder.tVPrecio.setText("/$ "+String.valueOf(listacanciones.get(i).getPrecio()));  //Revisar el parseo


        contactoViewHolder.chksong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (contactoViewHolder.chksong.isChecked()== true){

                    int cant = Integer.parseInt(eTCant.getText().toString().trim())+1;
                    eTCant.setText(""+String.valueOf(cant));
                    carroCompras.add(listacanciones.get(i));
                }else if(contactoViewHolder.chksong.isChecked() == false) {
                    eTCant.setText(""+(Integer.parseInt(eTCant.getText().toString().trim()) - 1));
                    carroCompras.remove(listacanciones.get(i));
                }
            }
        });

        fabVerCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CarroCompraActivity.class);
                intent.putExtra("carroCompras", (Serializable) carroCompras);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listacanciones.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView tVTitulo,tVArtista,tVAlbum,tVGenero,tVPrecio;
        CheckBox chksong;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            tVTitulo = itemView.findViewById(R.id.tVTitulo);
            tVArtista= itemView.findViewById(R.id.tVArtista);
            tVAlbum  = itemView.findViewById(R.id.tVAlbum);
            tVGenero = itemView.findViewById(R.id.tVGenero);
            tVPrecio = itemView.findViewById(R.id.tVPrecio);
            chksong = itemView.findViewById(R.id.chksong);

            //
            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, FichaCancionActivity.class);
                    intent.putExtra("idLC",listacanciones.get(getAdapterPosition()).getIdLC());
                    context.startActivity(intent);
                }
            });*/

        }
    }
}

