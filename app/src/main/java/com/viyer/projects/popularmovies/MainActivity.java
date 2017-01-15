package com.viyer.projects.popularmovies;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.viyer.projects.popularmovies.utilities.MovieJSONUtils;
import com.viyer.projects.popularmovies.utilities.NetworkUtils;


import java.net.URL;



public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);


        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.movie_loading_indicator);

    loadMovies(R.id.action_sortbyMostPopular);

    }




    private void showMoviesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

            loadMovies(Integer.valueOf(id));
            return true;

   }


    private void loadMovies(Integer sortBy) {
        if (isOnline())  {
            new FetchMoviesTask().execute(sortBy);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Internet Connection")
                    .setCancelable(true)
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        }


    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    public class FetchMoviesTask extends AsyncTask<Integer, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);

        }

        @Override
        protected Movie[] doInBackground(Integer... params) {
            Integer sortBy = params[0];

            try {
                URL moviesUrl= null;
                String apiKey = getApplicationContext().getString(R.string.api_key);
                if (sortBy == R.id.action_sortbyMostPopular) {
                   moviesUrl = NetworkUtils.buildUrl("popular", apiKey);
                }
                else if (sortBy == R.id.action_sortbyTopRated) {
                   moviesUrl = NetworkUtils.buildUrl("top_rated", apiKey);
                }

                String jsonResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesUrl);

                Movie[] movieData = MovieJSONUtils.getPopularMoviesFromJson(
                      MainActivity.this, jsonResponse);

                return movieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movieData) {

           if (movieData != null) {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    showMoviesDataView();
                    mMovieAdapter.setWeatherData(movieData);
                } else {
                    showErrorMessage();
                }



            }


    }



    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this,
                DetailActivity.class);
         intent.putExtra(Movie.MOVIE, movie);
        startActivity(intent);
    }
}
