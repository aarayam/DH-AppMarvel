package br.com.digitalhouse.desafiowebservices.view;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ProgressBar;

import java.util.ArrayList;

import br.com.digitalhouse.desafiowebservices.R;
import br.com.digitalhouse.desafiowebservices.adapters.RecyclerViewComicsAdapter;
import br.com.digitalhouse.desafiowebservices.viewmodel.ComicsViewModel;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerComics;
    private ComicsViewModel viewModel;
    private RecyclerViewComicsAdapter comicsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        viewModel.getResultLiveData().observe(this, results -> {

        });

        viewModel.isLoading().observe(this, loading -> {

        });

    }

    private void initViews() {
        viewModel = ViewModelProviders.of(this).get(ComicsViewModel.class);
        recyclerComics = findViewById(R.id.recyclerComics);
        comicsAdapter = new RecyclerViewComicsAdapter(new ArrayList<>());
        recyclerComics.setHasFixedSize(true);
        recyclerComics.setItemViewCacheSize(20);
        recyclerComics.setAdapter(comicsAdapter);
        recyclerComics.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
