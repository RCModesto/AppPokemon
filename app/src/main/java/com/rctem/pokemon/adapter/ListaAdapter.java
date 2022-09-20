package com.rctem.pokemon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rctem.pokemon.R;
import com.rctem.pokemon.model.Pokemon;
import com.rctem.pokemon.DetalhesActivity;
import com.rctem.pokemon.util.ColorUtil;
import com.rctem.pokemon.util.ConstantUtil;

import java.util.ArrayList;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {

    private ArrayList<Pokemon> listPokemon;
    private Context context;
    private Activity activity;

    public ListaAdapter(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.listPokemon = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pokemon pokemon = listPokemon.get(position);
        holder.nameView.setText(getNamePokemon(pokemon));
        holder.nameView.setText(listPokemon.get(position).getName().toUpperCase());
        int type = listPokemon.get(position).getNumber();

        setColorType(holder, type);
        Glide.with(context)
                .load(ConstantUtil.IMAGE_BASE_URL + pokemon.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetalhesActivity.class);
                intent.putExtra("extra_pokemon", pokemon.getNumber());
                activity.startActivity(intent);
            }
        });
    }

    private String getNamePokemon(Pokemon pokemon) {
        String namePokemon = pokemon.getName();
        namePokemon = namePokemon.substring(0,1).toUpperCase() + namePokemon.substring(1);
        return namePokemon;
    }

    @Override
    public int getItemCount() {
        return listPokemon.size();
    }

    private void setColorType(ListaAdapter.ViewHolder holder, int type){
        Drawable background = holder.cardView.getBackground();
        background.setColorFilter(ColorUtil.getBackgroundColor(type), PorterDuff.Mode.MULTIPLY);
    }

    public void addList(ArrayList<Pokemon> lista) {
        if (lista != null) {
            this.listPokemon.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.ERRO, lista.toString());
        }
    }

    public void limpaLista(){
        this.listPokemon.removeAll(listPokemon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView, container;
        private TextView nameView;
        private CardView cardView;

        public ViewHolder (View view){
            super(view);
            imageView = view.findViewById(R.id.iv_imagem_pokemon);
            nameView = view.findViewById(R.id.tv_nome_pokemon);
            container = view.findViewById(R.id.iv_container_pokemon);
            cardView = view.findViewById(R.id.cv_item_pokemon);
        }
    }
}
