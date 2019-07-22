package br.com.digitalhouse.desafiowebservices.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.digitalhouse.desafiowebservices.R;
import br.com.digitalhouse.desafiowebservices.model.Result;
import br.com.digitalhouse.desafiowebservices.view.DetalheActivity;
import io.reactivex.annotations.NonNull;

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
            public void onClick(View v) {

                String transitionName = "image_" + position;
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetalheActivity.class);
                intent.putExtra("comic", result);
                intent.putExtra("transitionName", transitionName);

                viewHolder.imagemComic.setTransitionName(transitionName);

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) viewHolder.itemView.getContext(),
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
        private TextView textNumberoComic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemComic = itemView.findViewById(R.id.imageViewComics);
            textNumberoComic = itemView.findViewById(R.id.textViewNumber);
        }

        private void bind(Result result) {
            Picasso.get().load(result.getThumbnail().getPath() + "/portrait_incredible." + result.getThumbnail().getExtension())
                    .placeholder(R.drawable.marvel)
                    .error(R.drawable.marvel)
                    .into(imagemComic);

            textNumberoComic.setText("# " + result.getIssueNumber());
        }
    }

    public void update(List<Result> resultList) {
        this.results = resultList;
        notifyDataSetChanged();
    }
}
