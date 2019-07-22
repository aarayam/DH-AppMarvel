package br.com.digitalhouse.desafiowebservices.view;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import br.com.digitalhouse.desafiowebservices.R;
import br.com.digitalhouse.desafiowebservices.adapters.RecyclerViewComicsAdapter;
import br.com.digitalhouse.desafiowebservices.model.Result;
import br.com.digitalhouse.desafiowebservices.viewmodel.ComicsViewModel;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerComics;
    private RecyclerViewComicsAdapter comicsAdapter;
   private List<Result> results = new ArrayList<>();
    private ComicsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializaViews();

        viewModel.getComics();

        viewModel.getResults().observe(this, results -> comicsAdapter.update(results));

    }

    private void inicializaViews() {
        viewModel = ViewModelProviders.of(this).get(ComicsViewModel.class);
        recyclerComics = findViewById(R.id.recyclerComics);
        comicsAdapter = new RecyclerViewComicsAdapter(results);
        recyclerComics.setHasFixedSize(true);
        recyclerComics.setItemViewCacheSize(20);
        recyclerComics.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerComics.setAdapter(comicsAdapter);
    }
}


