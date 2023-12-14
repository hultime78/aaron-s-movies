package tech.wolfcompany.tpandroid.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.wolfcompany.tpandroid.R;
import tech.wolfcompany.tpandroid.model.Movie;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    Context context;
    List<Movie> movieList;
    ItemClickListener itemClickListener;

    public MovieRecyclerAdapter(Context context, List<Movie> movieList, ItemClickListener itemClickListener) {
        this.context = context;
        this.movieList = movieList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        holder.movieNetwork.setText(movieList.get(position).getNetwork());

        if(movieList.get(position).getStart_date()!=null){
            holder.movieReleaseDate.setText(movieList.get(position).getStart_date());
        }
        holder.movieTitle.setText(movieList.get(position).getName());
        //Affichage de l'image
        Glide.with(context)
                .load(movieList.get(position).getImage_thumbnail_path()).centerCrop()
                .into(holder.movieImage);
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(movieList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface ItemClickListener{
        void onItemClick(Movie movie);
    }
}
