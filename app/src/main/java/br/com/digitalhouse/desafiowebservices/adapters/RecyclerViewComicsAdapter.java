package br.com.digitalhouse.desafiowebservices.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.digitalhouse.desafiowebservices.view.DetalheActivity;
import br.com.digitalhouse.desafiowebservices.R;
import br.com.digitalhouse.desafiowebservices.model.Result;

public class RecyclerViewComicsAdapter extends RecyclerView.Adapter<RecyclerViewComicsAdapter.ViewHolder> {

    private List<Result> results;

    public RecyclerViewComicsAdapter(List<Result> results) {
        this.results = results;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comics_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Result result = results.get(position);
        viewHolder.bind(result);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String transitionName = "image_" + position;
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetalheActivity.class);
                intent.putExtra("comic", (Parcelable) result);
                intent.putExtra("transitionName", transitionName);

                viewHolder.imagemComic.setTransitionName(transitionName);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) viewHolder.itemView.getContext(),
                        viewHolder.imagemComic, transitionName);

                viewHolder.itemView.getContext().startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {

        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagemComic;
        private TextView textNumeroComic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemComic = itemView.findViewById(R.id.imageViewComic);
            textNumeroComic = itemView.findViewById(R.id.textViewNumeroComic);
        }

        void bind(Result result) {

            //if (result.getThumbnail() != null){
            //    Picasso.get().setIndicatorsEnabled(true);
                Picasso.get()
                        .load(result.getThumbnail().getPath() + "/portrait_incredible." + result.getThumbnail().getExtension())
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imagemComic);

                textNumeroComic.setText(result.getTitle());

        }
        }
 //   }

    public void update(List<Result> results){

        if (this.results.isEmpty()){
            this.results = results;
        } else {
            this.results.addAll(results);
        }
        notifyDataSetChanged();
    }

}




