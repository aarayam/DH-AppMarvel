package br.com.digitalhouse.desafiowebservices.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.digitalhouse.desafiowebservices.R;
import br.com.digitalhouse.desafiowebservices.model.Result;

public class DetalheActivity extends AppCompatActivity {

    private ImageView imagemHeroi;
    private ImageView imageHeader;
    private ImageView imageBack;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private Result result;
    private TextView textTitle;
    private TextView textViewDescription;
    private TextView textViewPublished;
    private TextView textViewPrice;
    private TextView textViewPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_detalhe);

        inicializaViewsDetalhe();


        setSupportActionBar(toolbar);

        result = getIntent().getParcelableExtra("comic");

        String transitionName = getIntent().getStringExtra("transitionName");
        imagemHeroi.setTransitionName(transitionName);


        textTitle.setText(result.getTitle());
        //textViewDescription.setText(HtmlCompat.fromHtml(result.getDescription()));

        textViewPrice.setText("$" + result.getPrices().get(0).getPrice());
        textViewPages.setText(result.getPageCount().toString());


        Picasso.get().load(result.getThumbnail().getPath() + "/portrait_incredible." + result.getThumbnail().getExtension())
                .placeholder(R.drawable.marvel)
                .error(R.drawable.marvel)
                .into(imagemHeroi);

        if (!result.getImages().isEmpty()) {
            Picasso.get().load(result.getImages().get(0).getPath() + "/landscape_incredible." + result.getImages().get(0).getExtension())
                    .placeholder(R.drawable.marvel)
                    .error(R.drawable.marvel)
                    .into(imageHeader);
        }

        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
            Date date = formatDate.parse(result.getDates().get(0).getDate());
            String dateString = format.format(date);
            textViewPublished.setText(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        imagemHeroi.setOnClickListener(view -> {
            Intent intent = new Intent(DetalheActivity.this, ImagemActivity.class);
            intent.putExtra("image", result.getThumbnail().getPath() + "/detail." + result.getThumbnail().getExtension());
            startActivity(intent);
        });

        imageBack.setOnClickListener(v -> supportFinishAfterTransition());


        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
                imagemHeroi.setVisibility(View.VISIBLE);
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                imagemHeroi.setVisibility(View.GONE);
                toolbar.setTitle(result.getTitle());
            } else {
                imagemHeroi.setVisibility(View.VISIBLE);
            }
        });
    }

    private void inicializaViewsDetalhe() {
        toolbar = findViewById(R.id.toolbar);
        imageBack = findViewById(R.id.imageBack);
        imagemHeroi = findViewById(R.id.imageComic);
        appBarLayout = findViewById(R.id.appBar);
        textTitle = findViewById(R.id.textTitle);
        textViewDescription = findViewById(R.id.textDescription);
        textViewPublished = findViewById(R.id.textPublished);
        textViewPrice = findViewById(R.id.textPrice);
        textViewPages = findViewById(R.id.textPages);
        imageHeader = findViewById(R.id.imageHeader);
    }
}
