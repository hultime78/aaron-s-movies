package tech.wolfcompany.tpandroid.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.wolfcompany.tpandroid.R;
import tech.wolfcompany.tpandroid.model.Movie;
import tech.wolfcompany.tpandroid.model.MovieList;
import tech.wolfcompany.tpandroid.repository.MovieApi;
import tech.wolfcompany.tpandroid.ui.about.AboutActivity;
import tech.wolfcompany.tpandroid.ui.itempreview.PreviewMovieActivity;
import tech.wolfcompany.tpandroid.ui.main.GridSpacingItemDecoration;
import tech.wolfcompany.tpandroid.ui.main.MovieRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    MovieApi movieApi;
    Retrofit retrofit;
    List<Movie> movieList;
    RecyclerView movieRecycler;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imvoid;
    TextView txtVoid;
    MenuInflater menuInflater;
    private AlertDialog.Builder alertMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    public void initComponents(){

        swipeRefreshLayout=findViewById(R.id.swipeToRefresh);
        movieRecycler=findViewById(R.id.movie_recycler);
        imvoid=findViewById(R.id.imvoid);
        txtVoid=findViewById(R.id.txtvoid);
        movieList=new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        retrofit=new Retrofit.Builder()
                .baseUrl(getString(R.string.server_url))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        swipeRefreshLayout.setColorSchemeResources(R.color.primary);


        int spanCount = 3; // 3 colonnes
        int spacing = 30; // 50px de spacing
        boolean includeEdge = false;
        movieRecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        movieApi=retrofit.create(MovieApi.class);
        swipeRefreshLayout.setRefreshing(true);
        //Generation d'un nombre aléatoire pour la selection d'une page aléatoire dans l'api call
        Random r0 = new Random();
        int low0 = 1;
        int high0 = 100;
        int result0 = r0.nextInt(high0-low0) + low0;
        Call<MovieList> callMovies =movieApi.getPopularMovies(result0);

        callMovies.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.body()!=null){
                    imvoid.setVisibility(View.GONE);
                    txtVoid.setVisibility(View.GONE);
                    MovieList list= response.body();
                    movieList=list.getMovies() ;
                    MovieRecyclerAdapter adapter=new MovieRecyclerAdapter(getApplicationContext(),movieList,movie->{

                        Intent intent=new Intent(MainActivity.this, PreviewMovieActivity.class);

                        intent.putExtra("movieTitle",movie.getName());
                        intent.putExtra("movieDate",movie.getStart_date());
                        intent.putExtra("movieCountry",movie.getCountry());
                        intent.putExtra("movieImage",movie.getImage_thumbnail_path());
                        intent.putExtra("movieStatus",movie.getStatus());
                        startActivity(intent);
                        onPause();

                    });
                    GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),3);
                    movieRecycler.setItemAnimator(new DefaultItemAnimator());
                    movieRecycler.setAdapter(adapter);
                    movieRecycler.setLayoutManager(layoutManager);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                imvoid.setVisibility(View.VISIBLE);
                txtVoid.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Connexion impossible,vérifiez votre connexion et réessayez", Toast.LENGTH_LONG).show();
                Log.e("MVApp",t.getMessage());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() ->{
        swipeRefreshLayout.setRefreshing(false);

            Random r = new Random();
            int low = 1;
            int high = 100;
            int result = r.nextInt(high-low) + low;
            Call<MovieList> callMoviesAgain =movieApi.getPopularMovies(result);
            callMoviesAgain.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    txtVoid.setVisibility(View.GONE);
                    imvoid.setVisibility(View.GONE);

                    //Mise à jour de la liste de films
                    movieList.addAll(response.body().getMovies());
                    Collections.reverse(movieList);
                    MovieRecyclerAdapter adapter=new MovieRecyclerAdapter(getApplicationContext(),movieList,movie->{
                        Intent intent=new Intent(MainActivity.this,PreviewMovieActivity.class);

                        intent.putExtra("movieTitle",movie.getName());
                        intent.putExtra("movieDate",movie.getStart_date());
                        intent.putExtra("movieCountry",movie.getCountry());
                        intent.putExtra("movieImage",movie.getImage_thumbnail_path());
                        intent.putExtra("movieStatus",movie.getStatus());
                        startActivity(intent);
                        onPause();

                    });


                    GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),3);
                    movieRecycler.setItemAnimator(new DefaultItemAnimator());
                    movieRecycler.setAdapter(adapter);

                    movieRecycler.setLayoutManager(layoutManager);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    txtVoid.setVisibility(View.VISIBLE);
                    imvoid.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Connexion impossible,vérifiez votre connexion et réessayez", Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about_view:
                Intent intent=new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);

                break;
            case R.id.quitter_view:
                showMessage("Quitter", "Etes vous sûr de vouloir quitter ?");
                break;
        }


        return true;
    }

    public void showMessage(String title, String message) {
        alertMessage = new AlertDialog.Builder(this);
        alertMessage.setIcon(android.R.drawable.ic_dialog_alert);
        alertMessage.setTitle(title);
        alertMessage.setMessage(message);
        alertMessage.setNegativeButton(android.R.string.no, null);
        alertMessage.setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
            if (title == "Quitter") {
                finish();
            }
        });
        alertMessage.show();
    }
}