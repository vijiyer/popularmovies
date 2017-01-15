package com.viyer.projects.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Movie movie = (Movie)getIntent().getSerializableExtra(Movie.MOVIE);

        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        TextView title = (TextView) findViewById(R.id.original_title);
        TextView rating = (TextView) findViewById(R.id.rating);
        TextView synopsis = (TextView) findViewById(R.id.synopsis);
        ImageView posterView = (ImageView) findViewById(R.id.poster);
        if (movie != null) {
            title.setText(movie.getTitle());
            releaseDate.setText(new SimpleDateFormat("yyyy").format(movie.getReleaseDate()));
            rating.setText(movie.getRating());
            synopsis.setText(movie.getOverview());
            Picasso.with(this)
                    .load(movie.getPosterUrl()).into(posterView);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
