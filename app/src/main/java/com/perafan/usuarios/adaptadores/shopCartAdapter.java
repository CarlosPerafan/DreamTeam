package com.perafan.usuarios.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.perafan.usuarios.R;
import com.perafan.usuarios.entidades.Canciones;

import java.io.Serializable;
import java.util.ArrayList;

public class shopCartAdapter extends RecyclerView.Adapter<shopCartAdapter.ContactoViewHolder> {

    Context context;
    ArrayList<Canciones>  carroCompras;
    TextView eTTotal;
    float total = 0;

    public shopCartAdapter (Context context, ArrayList<Canciones> carroCompras, TextView eTTotal) {
        this.context = context;
        this.carroCompras = carroCompras;
        this.eTTotal = eTTotal;

        for(int i = 0 ; i < carroCompras.size() ; i++) {
            total = total + Float.parseFloat(""+carroCompras.get(i).getPrecio());
        }

        eTTotal.setText(""+total);

    }


    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_items_shop_sel,null,false);  //Revisar que quite el parent.getContexto por viewGroup.getContext
        return new shopCartAdapter.ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder contactoViewHolder, @SuppressLint("RecyclerView") int i) {

        contactoViewHolder.tVTitulo.setText(carroCompras.get(i).getTitulo());
        contactoViewHolder.tVArtista.setText(carroCompras.get(i).getArtista());
        contactoViewHolder.tVAlbum.setText("/"+carroCompras.get(i).getAlbum());
        contactoViewHolder.tVGenero.setText("/"+carroCompras.get(i).getGenero());
        contactoViewHolder.tVPrecio.setText("/$ "+String.valueOf(carroCompras.get(i).getPrecio()));
        contactoViewHolder.chksong_sel.setChecked(true);

        contactoViewHolder.chksong_sel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (contactoViewHolder.chksong_sel.isChecked()== true){

                    total = total + Float.parseFloat(""+carroCompras.get(i).getPrecio());
                    eTTotal.setText(""+total);
                }else if(contactoViewHolder.chksong_sel.isChecked() == false) {

                    total = total - Float.parseFloat(""+carroCompras.get(i).getPrecio());
                    eTTotal.setText(""+total);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return carroCompras.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView tVTitulo,tVArtista,tVAlbum,tVGenero,tVPrecio;
        CheckBox chksong_sel;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            tVTitulo = itemView.findViewById(R.id.tVTitulo);
            tVArtista= itemView.findViewById(R.id.tVArtista);
            tVAlbum  = itemView.findViewById(R.id.tVAlbum);
            tVGenero = itemView.findViewById(R.id.tVGenero);
            tVPrecio = itemView.findViewById(R.id.tVPrecio);

            chksong_sel= itemView.findViewById(R.id.chksong_sel);
        }
    }
}

