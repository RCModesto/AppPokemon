package com.rctem.pokemon;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.rctem.pokemon.PokemonCallBack;
import com.rctem.pokemon.model.Pokemon;
import com.rctem.pokemon.adapter.ListaAdapter;
import com.rctem.pokemon.service.ApiService;
import com.rctem.pokemon.util.ConstantUtil;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonRetrofit {
    private boolean responseState;
    private static Retrofit retrofit;
    private static ApiService apiService;
    private Context context;

    public PokemonRetrofit(Context context){
        this.context = context;
    }

    public Retrofit createRetrofit() {
        return this.retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getListAPI(int offset, ListaAdapter adapter) {
        apiService = retrofit.create(ApiService.class);
        Call<PokemonCallBack> callback = apiService.getListaPokemons(10, offset);
        callback.enqueue(new Callback<PokemonCallBack>() {
            @Override
            public void onResponse(Call<PokemonCallBack> call, Response<PokemonCallBack> response) {
                if(response.isSuccessful()){
                    responseState = true;
                    PokemonCallBack body = response.body();
                    ArrayList<Pokemon> result = body.getResults();
                    adapter.addList(result);
                } else {
                    responseState = false;
                    Log.i(ConstantUtil.ERRO_AO_CARREGAR_LISTA, "-> onResponse: " + response.errorBody());
                    Toast.makeText(context, ConstantUtil.ERRO_AO_CARREGAR_LISTA, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PokemonCallBack> call, Throwable t) {
                responseState = false;
                Log.i(ConstantUtil.ERRO_AO_CARREGAR_LISTA, "-> onFailure: " + t.getMessage());
                Toast.makeText(context, ConstantUtil.ERRO_AO_CARREGAR_LISTA, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String formatMedida(int atributo, String medida){
        String format = "" + atributo;
        if(format.length() == 1)
            return "0," + atributo + medida;
        return format.substring(0, format.length()-1) + "," + format.charAt(format.length()-1) + medida;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
