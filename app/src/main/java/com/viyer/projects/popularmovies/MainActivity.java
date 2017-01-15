package com.viyer.projects.popularmovies;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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



public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, FetchMoviesCallback{

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
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
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
            String apiKey = getApplicationContext().getString(R.string.api_key);
            new FetchMoviesTask().execute(sortBy, apiKey, this);
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

    /*
        methods implementing FetchMoviesCallback
     */
    @Override
    public void onFetchMoviesCallback(Movie[] movieData) {
        if (movieData != null) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            showMoviesDataView();
            mMovieAdapter.setWeatherData(movieData);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onPreExecuteFetchMovies() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onClick(Movie movie) {
         Intent intent = new Intent(this, DetailActivity.class);
         intent.putExtra(Movie.MOVIE, movie);
         startActivity(intent);
    }
}
