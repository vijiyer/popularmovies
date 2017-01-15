package com.viyer.projects.popularmovies.utilities;




    import android.content.Context;

    import com.viyer.projects.popularmovies.Movie;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.net.HttpURLConnection;
    import java.sql.Date;

/**
     * Utility functions to handle MovieDatabase JSON data.
     */
    public final class MovieJSONUtils {


        public static Movie[] getPopularMoviesFromJson(Context context, String movieJsonStr)
                throws JSONException {

             final String RESULTS = "results";

            final String ORIGINAL_TITLE = "original_title";
            final String OVERVIEW = "overview";
            final String POSTER_PATH = "poster_path";
            final String VOTE_AVERAGE = "vote_average";
            final String RELEASE_DATE = "release_date";


            final String MESSAGE_CODE = "cod";


            JSONObject movieJson = new JSONObject(movieJsonStr);


            if (movieJson.has(MESSAGE_CODE)) {
                int errorCode = movieJson.getInt(MESSAGE_CODE);

                switch (errorCode) {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                        return null;
                    default:
                    /* Server probably down */
                        return null;
                }
            }

            JSONArray moviesArray = movieJson.getJSONArray(RESULTS);
            Movie[] parsedMovieData = new Movie[moviesArray.length()];
            for(int i=0; i<moviesArray.length(); i++)  {
                Movie movie = new Movie();
                JSONObject movieJSONObject = moviesArray.getJSONObject(i);
                String title  = movieJSONObject.getString(ORIGINAL_TITLE);
                String posterPath = movieJSONObject.getString(POSTER_PATH);
                String posterURL = NetworkUtils.MOVIE_POSTER_URL + posterPath;
                String rating  = movieJSONObject.getString(VOTE_AVERAGE) + "/10";
                String releaseDate = movieJSONObject.getString(RELEASE_DATE);

               movie.setTitle(title);
                movie.setPosterUrl(posterURL);
                movie.setOverview(movieJSONObject.getString(OVERVIEW));
                movie.setRating(rating);
                movie.setReleaseDate(Date.valueOf(releaseDate));
                parsedMovieData[i] = movie;
            }

          return parsedMovieData;
        }


    }