package tech.wolfcompany.tpandroid.repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tech.wolfcompany.tpandroid.model.Movie;
import tech.wolfcompany.tpandroid.model.MovieList;

public interface MovieApi {

    @GET("/api/most-popular")
    Call<MovieList> getPopularMovies(@Query("page") int page);
}
