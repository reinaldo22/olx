package com.lixo.reinaldo.ollixo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixo.reinaldo.ollixo.R;
import com.lixo.reinaldo.ollixo.model.Anuncio;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAnuncios extends RecyclerView.Adapter<AdapterAnuncios.MyViewHolder>{

    private List<Anuncio> anuncios;
    private Context context;

    public AdapterAnuncios(List<Anuncio> anuncios, Context context) {
        this.anuncios = anuncios;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_anuncio,viewGroup,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int posicao) {

        Anuncio anuncio = anuncios.get(posicao);
        myViewHolder.titulo.setText(anuncio.getTitulo());
        myViewHolder.valor.setText(anuncio.getValor());

        //Pega a primeira imagem da lista
        List<String> urlFotos = anuncio.getFotos();
        String urlCapa = urlFotos.get(0);

        Picasso.get().load(urlCapa).into(myViewHolder.foto);


    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        TextView valor;
        ImageView foto;

        public MyViewHolder(View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            valor = itemView.findViewById(R.id.textPreco);
            foto = itemView.findViewById(R.id.imageAnuncio);



        }
    }

}
