package com.ezen.wannaseeamovie.model2;

public class MovieSearchDTO {
    String title, releaseDate, language, img_path;
    int id;

    public MovieSearchDTO() {

    }

    public MovieSearchDTO(String title, String releaseDate, String language, String img_path, int id) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.language = language;
        this.img_path = img_path;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
