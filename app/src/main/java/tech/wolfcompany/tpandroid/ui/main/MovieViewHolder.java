package tech.wolfcompany.tpandroid.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tech.wolfcompany.tpandroid.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    ImageView movieImage;
    TextView movieTitle,movieReleaseDate,movieNetwork;
    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        movieImage=itemView.findViewById(R.id.image_url);
        movieTitle=itemView.findViewById(R.id.movie_title);
        movieReleaseDate=itemView.findViewById(R.id.movie_release_date);
        movieNetwork=itemView.findViewById(R.id.movie_network);
    }
}
