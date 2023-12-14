package tech.wolfcompany.tpandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {

    long total;
    long page;
    long pages;
    @SerializedName("tv_shows")
    List<Movie> movies;

    public MovieList(long total, long page, long pages, List<Movie> movies) {
        this.total = total;
        this.page = page;
        this.pages = pages;
        this.movies = movies;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
