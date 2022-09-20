package com.rctem.pokemon.service;

import com.rctem.pokemon.PokemonCallBack;
import com.rctem.pokemon.model.PokemonDetalhes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @GET("pokemon")
    Call<PokemonCallBack> getListaPokemons(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{url}")
    Call<PokemonDetalhes> getDetalhePokemon(@Path("url") int url);

}
