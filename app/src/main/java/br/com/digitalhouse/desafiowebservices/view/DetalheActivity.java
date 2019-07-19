package br.com.digitalhouse.desafiowebservices.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    //private Toolbar toolbar;
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


       // setSupportActionBar(toolbar);

        result = getIntent().getParcelableExtra("comic");

        String transitionName = getIntent().getStringExtra("transitionName");
        imagemHeroi.setTransitionName(transitionName);


        textTitle.setText(result.getTitle());
        textViewDescription.setText(Html.fromHtml(result.getDescription()));
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

        // Adicionamos o evendo se click na imagem para irmos para tela
        // que mostra a imagem inteira
        imagemHeroi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalheActivity.this, ImagemActivity.class);
                intent.putExtra("image", result.getThumbnail().getPath() + "/detail." + result.getThumbnail().getExtension());
                startActivity(intent);
            }
        });

        // Adicionamos o evento de click para finalizarmos a activity
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });


        // Adicionamos o evento de scroll, para mostrar ou não a imagem pequena do quadrinho
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    imagemHeroi.setVisibility(View.VISIBLE);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    imagemHeroi.setVisibility(View.GONE);
                    //toolbar.setTitle(result.getTitle());
                } else {
                    imagemHeroi.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void inicializaViewsDetalhe() {
        imageBack = findViewById(R.id.imageBack);
        imagemHeroi = findViewById(R.id.imageComic);
        //appBarLayout = findViewById(R.id.app_bar);
        textTitle = findViewById(R.id.textTitle);
        textViewDescription = findViewById(R.id.textDescription);
        textViewPublished = findViewById(R.id.textPublished);
        textViewPrice = findViewById(R.id.textPrice);
        textViewPages = findViewById(R.id.textPages);
        imageHeader = findViewById(R.id.imageHeader);
    }
}
