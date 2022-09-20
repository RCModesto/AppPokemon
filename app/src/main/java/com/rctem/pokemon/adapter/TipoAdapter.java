package com.rctem.pokemon.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rctem.pokemon.R;
import com.rctem.pokemon.model.PokemonDetalhes;
import com.rctem.pokemon.util.ColorUtil;
import com.rctem.pokemon.util.ConstantUtil;

import java.util.ArrayList;

public class TipoAdapter extends RecyclerView.Adapter<TipoAdapter.ViewHolder> {
    private ArrayList<PokemonDetalhes.Types> listType;

    public TipoAdapter(){
        this.listType = new ArrayList<>();
    }

    @NonNull
    @Override
    public TipoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_detalhes, parent, false);
        return new TipoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.typeTextView.setText(listType.get(position).getType().getName().toUpperCase());
        int type = listType.get(position).getType().getNumber();
        setColorType(holder, type);
    }

    @Override
    public int getItemCount() {
        return listType.size();
    }

    private void setColorType(ViewHolder holder, int type){
        Drawable background = holder.typeImageView.getBackground();
        int viewColor = ColorUtil.getForegroundViewColor(type);
        background.setColorFilter(ColorUtil.getBackgroundColor(type), PorterDuff.Mode.MULTIPLY);
        holder.typeTextView.setTextColor(viewColor);
    }

    public void addList(ArrayList<PokemonDetalhes.Types> lista) {
        if (lista != null) {
            this.listType.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.ERRO, lista.toString());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView typeImageView;
        private TextView typeTextView;

        public ViewHolder(View view){
            super(view);

            typeTextView = view.findViewById(R.id.tv_informacao);
            typeImageView = view.findViewById(R.id.iv_informacao);
        }
    }
}
