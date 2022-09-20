package com.rctem.pokemon;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rctem.pokemon.PokemonRetrofit;
import com.rctem.pokemon.adapter.ListaAdapter;
import com.rctem.pokemon.util.ConstantUtil;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listaPokemonsRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private PokemonRetrofit retrofit;
    private ListaAdapter adapter;
    private ImageView arrowBack;
    private int offset;
    private Button btnCarregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ListaAdapter(this);
        retrofit = new PokemonRetrofit(this);

        createRecyclerView();
        createRetrofit();
        createList();
    }

    private void createRetrofit() {
        retrofit.createRetrofit();
        offset = 0;
        getInformacao(offset);
    }

    private void createRecyclerView() {
        arrowBack = findViewById(R.id.iv_retorna);
        btnCarregar = findViewById(R.id.btnCarregar);
        listaPokemonsRecyclerView = findViewById(R.id.rv_pokemon);
        listaPokemonsRecyclerView.setAdapter(adapter);
        listaPokemonsRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        listaPokemonsRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void createList() {
        btnCarregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset += 10;
                if(arrowBack.getVisibility() == GONE)
                    getInformacao(offset);
            }
        });
    }

    private void getInformacao(int offset) {
        arrowBack.setVisibility(View.GONE);
        retrofit.getListAPI(offset, adapter);
    }

    public void retornaParaListaPrincipal(View view){
        adapter.limpaLista();
        offset = 0;
        getInformacao(offset);
        Toast.makeText(this, ConstantUtil.RETURN, Toast.LENGTH_SHORT).show();
    }

    public void scrollUp(View view) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) listaPokemonsRecyclerView.getLayoutManager();
        layoutManager.smoothScrollToPosition(listaPokemonsRecyclerView, null, 0);
        adapter.limpaLista();
        createRecyclerView();
        createRetrofit();
    }
}