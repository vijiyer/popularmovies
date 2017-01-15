package com.viyer.projects.popularmovies;

/**
 * Created by vijaya on 1/15/2017.
 */

public interface FetchMoviesCallback {
    void onPreExecuteFetchMovies();
    void onFetchMoviesCallback(Movie[] movieData) ;

}
