package com.rctem.pokemon.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rctem.pokemon.R;
import com.rctem.pokemon.model.PokemonDetalhes;
import com.rctem.pokemon.util.ConstantUtil;

import java.util.ArrayList;

public class HabilidadeAdapter extends RecyclerView.Adapter<HabilidadeAdapter.ViewHolderAbility> {
    private ArrayList<PokemonDetalhes.Ability> listAbility;
    private Activity activity;
    private Context context;

    public HabilidadeAdapter(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.listAbility = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderAbility onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_detalhes, parent, false);
        return new ViewHolderAbility(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAbility holder, int position) {
        holder.abilityTextView.setText(listAbility.get(position).getAbilityInfo().getName().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return listAbility.size();
    }

    public void addList(ArrayList<PokemonDetalhes.Ability> lista) {
        if (lista != null) {
            this.listAbility.addAll(lista);
            notifyDataSetChanged();
        } else {
            Log.i(ConstantUtil.ERRO, lista.toString());
        }
    }

    class ViewHolderAbility extends RecyclerView.ViewHolder{
        private TextView abilityTextView;

        public ViewHolderAbility(View view){
            super(view);
            abilityTextView = view.findViewById(R.id.tv_informacao);
        }
    }
}
