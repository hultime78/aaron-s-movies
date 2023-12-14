package tech.wolfcompany.tpandroid.ui.itempreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;

import tech.wolfcompany.tpandroid.R;

public class PreviewMovieActivity extends AppCompatActivity {


    TextView movieTitle,movieDate,movieCountry,movieStatus;
    ImageView movieImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_movie);
        initComponents();
    }

    public void initComponents(){

        movieTitle=findViewById(R.id.movie_title_txt);
        movieDate=findViewById(R.id.movie_start_date_txt);
        movieCountry=findViewById(R.id.movie_country_txt);
        movieStatus=findViewById(R.id.movie_status_txt);
        movieImage=findViewById(R.id.movie_im);


        String movieTitleI="Titre de la série: "+getIntent().getStringExtra("movieTitle");
        String movieDateI="Date de sortie: "+getIntent().getStringExtra("movieDate");
        String movieCountryI="Pays: "+getIntent().getStringExtra("movieCountry");
        String imageUrlI=getIntent().getStringExtra("movieImage");
        String movieStatusI="Status: "+getIntent().getStringExtra("movieStatus");

        movieTitle.setText(movieTitleI);
        movieDate.setText(movieDateI);
        movieCountry.setText(movieCountryI);
        movieStatus.setText(movieStatusI);

        Glide.with(this)
                .load(imageUrlI).centerCrop()
                .into(movieImage);
    }
}