package com.dicoding.hendropurwoko.mysubmission04contentprovider;

public class MovieModel {
    int id;
    String title, overview, release_date, popularity, poster;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieModel() {  }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public MovieModel(String title, String overview, String release_date, String popularity) {
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.popularity = popularity;
    }

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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
}
