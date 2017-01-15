package com.viyer.projects.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Movie movie = getIntent().getParcelableExtra(Movie.MOVIE);
        TextView releaseDate = ButterKnife.findById(this, R.id.releaseDate);
        TextView title = ButterKnife.findById(this,R.id.original_title);
        TextView rating = ButterKnife.findById(this,R.id.rating);
        TextView synopsis = ButterKnife.findById(this,R.id.synopsis);
        ImageView posterView = ButterKnife.findById(this, R.id.poster);
        if (movie != null) {
            title.setText(movie.getTitle());
            releaseDate.setText(new SimpleDateFormat("yyyy").format(movie.getReleaseDate()));
            rating.setText(movie.getRating());
            synopsis.setText(movie.getOverview());
            Picasso.with(this)
                    .load(movie.getPosterUrl())
                    .placeholder(R.color.colorPrimaryDark)
                    .error(R.color.colorAccent).into(posterView);
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
