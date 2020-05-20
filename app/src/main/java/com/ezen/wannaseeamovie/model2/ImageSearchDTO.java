package com.ezen.wannaseeamovie.model2;

public class ImageSearchDTO {
    String file_path;
    String iso_639_1;
    Double vote_average;
    int vote_count;

    public ImageSearchDTO() {

    }

    public ImageSearchDTO(String file_path, String iso_639_1, Double vote_average, int vote_count) {
        this.file_path = file_path;
        this.iso_639_1 = iso_639_1;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
