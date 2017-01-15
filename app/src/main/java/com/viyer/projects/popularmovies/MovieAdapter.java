package com.viyer.projects.popularmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by vijaya on 1/6/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Movie[] mMovieData;
    MovieAdapterOnClickHandler mClickHandler;
    public MovieAdapter() {

    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMovieView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieView = (ImageView) view.findViewById(R.id.posterView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieData[adapterPosition];
            mClickHandler.onClick(movie);
        }

    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_thumbnail;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movie = mMovieData[position];
        String moviePosterUrl = movie.getPosterUrl();
        Picasso.with(movieAdapterViewHolder.itemView.getContext())
                .load(moviePosterUrl).into(movieAdapterViewHolder.mMovieView);

    }


    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }


    public void setWeatherData(Movie[] movieData) {
        this.mMovieData = movieData;
        notifyDataSetChanged();
    }
}
