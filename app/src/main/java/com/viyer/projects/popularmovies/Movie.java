package com.viyer.projects.popularmovies;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vijaya on 1/7/2017.
 */

public class Movie implements Serializable {
    public static String MOVIE = "MOVIE";
    private String title;
    private String overview;
    private String rating;
    private Date releaseDate;
    private String posterUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }



    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
