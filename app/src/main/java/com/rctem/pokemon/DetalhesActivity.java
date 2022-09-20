package com.rctem.pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rctem.pokemon.service.ApiService;
import com.rctem.pokemon.PokemonRetrofit;
import com.rctem.pokemon.model.PokemonDetalhes;
import com.rctem.pokemon.adapter.HabilidadeAdapter;
import com.rctem.pokemon.adapter.TipoAdapter;
import com.rctem.pokemon.util.ConstantUtil;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesActivity extends AppCompatActivity {

    private static final String APP_TITLE_INFO = "Sobre o pokemon";
    private TextView nomePokemon, pesoAlturaPokemon;
    private RecyclerView habilidadesRecyclerview, typeRecyclerview;
    private ImageView imagemPokemon, fecharDetalhes;
    private PokemonRetrofit retrofit;
    private int pokemonId;
    private HabilidadeAdapter adapterAbility;
    private TipoAdapter adapterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        setTitle(APP_TITLE_INFO);
        getViews();
        getRetrofit();
        getPokemon();
        setFinishBtn();
    }

    private void getRetrofit() {
        retrofit = new PokemonRetrofit(this);
        retrofit.createRetrofit();
    }

    private void getPokemon() {
        if(getIntent().getSerializableExtra("extra_pokemon") != null){
            pokemonId = (int) getIntent().getSerializableExtra("extra_pokemon");
            setFotoPokemon();
            getInfosDaApi();
        } else {
            Toast.makeText(this,ConstantUtil.POKEMON_NOT_FOUND, Toast.LENGTH_SHORT).show();
        }
    }

    private void setFotoPokemon() {
        Glide.with(this)
                .load(ConstantUtil.IMAGE_BASE_URL + pokemonId + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagemPokemon);
    }

    private void getViews() {
        nomePokemon = findViewById(R.id.tv_nome_pokemon);
        pesoAlturaPokemon = findViewById(R.id.tv_peso_altura);
        imagemPokemon = findViewById(R.id.iv_pokemon);
        createRecyclerViews();
    }

    private void createRecyclerViews() {
        LinearLayoutManager linearLayoutManager;
        GridLayoutManager gridLayoutManager;

        adapterAbility = new HabilidadeAdapter(this);
        habilidadesRecyclerview = findViewById(R.id.rv_habilidade);
        habilidadesRecyclerview.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        habilidadesRecyclerview.setLayoutManager(linearLayoutManager);
        habilidadesRecyclerview.setAdapter(adapterAbility);

        adapterType = new TipoAdapter();
        typeRecyclerview = findViewById(R.id.rv_pokemon_tipo);
        typeRecyclerview.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        typeRecyclerview.setLayoutManager(gridLayoutManager);
        typeRecyclerview.setAdapter(adapterType);
    }

    private void getInfosDaApi() {
        ApiService apiService = retrofit.getRetrofit().create(ApiService.class);
        Call<PokemonDetalhes> callback = apiService.getDetalhePokemon(pokemonId);
        callback.enqueue(new Callback<PokemonDetalhes>() {
            @Override
            public void onResponse(Call<PokemonDetalhes> call, Response<PokemonDetalhes> response) {
                if(response.isSuccessful()){
                    PokemonDetalhes body = response.body();
                    String pesoAltura = "Peso: " + retrofit.formatMedida(body.getWeight(), "kg")
                            + " - Altura: " + retrofit.formatMedida(body.getHeight(), "m");

                    adapterAbility.addList((ArrayList<PokemonDetalhes.Ability>) body.getAbilities());
                    adapterType.addList((ArrayList<PokemonDetalhes.Types>) body.getTypes());
                    nomePokemon.setText(body.getName().toUpperCase());
                    pesoAlturaPokemon.setText(pesoAltura);
                } else {
                    Log.i(ConstantUtil.ERRO_AO_CARREGAR_LISTA, "-> onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<PokemonDetalhes> call, Throwable t) {
                Log.i(ConstantUtil.ERRO_AO_CARREGAR_LISTA, "-> onResponse: " + t.getMessage());
            }
        });
    }

    private void setFinishBtn() {
        fecharDetalhes = findViewById(R.id.iv_fechar);
        fecharDetalhes.setOnClickListener(view ->  {
            finish();
        });
    }
}