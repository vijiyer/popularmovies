package com.viyer.projects.popularmovies;

import android.os.AsyncTask;
import android.view.View;

import com.viyer.projects.popularmovies.utilities.MovieJSONUtils;
import com.viyer.projects.popularmovies.utilities.NetworkUtils;

import java.net.URL;

/**
 * Created by vijaya on 1/15/2017.
 */

public class FetchMoviesTask extends AsyncTask<Object, Void, Movie[]> {
    FetchMoviesCallback mFetchMoviesCallback;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mFetchMoviesCallback != null) {
            mFetchMoviesCallback.onPreExecuteFetchMovies();
        }
   }

    @Override
    protected Movie[] doInBackground(Object... params) {
        Integer sortBy = (Integer)params[0];
        String apiKey = (String) params[1];
        this.mFetchMoviesCallback = (FetchMoviesCallback) params[2];

        try {
            URL moviesUrl= null;
            if (sortBy == R.id.action_sortbyMostPopular) {
                moviesUrl = NetworkUtils.buildUrl("popular", apiKey);
            }
            else if (sortBy == R.id.action_sortbyTopRated) {
                moviesUrl = NetworkUtils.buildUrl("top_rated", apiKey);
            }

            String jsonResponse = NetworkUtils
                    .getResponseFromHttpUrl(moviesUrl);

            Movie[] movieData = MovieJSONUtils.getPopularMoviesFromJson(jsonResponse);

            return movieData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(Movie[] movieData) {
        if (mFetchMoviesCallback != null) {
            mFetchMoviesCallback.onFetchMoviesCallback(movieData);
        }
   }


}
