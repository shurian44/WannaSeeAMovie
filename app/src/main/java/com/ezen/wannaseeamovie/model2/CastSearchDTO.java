package com.ezen.wannaseeamovie.model2;

public class CastSearchDTO {
    String character, name, profile_path, credit_id;
    int cast_id, gender, id, order;

    public CastSearchDTO() {

    }

    public CastSearchDTO(String character, String name, String profile_path, String credit_id, int cast_id, int gender, int id, int order) {
        this.character = character;
        this.name = name;
        this.profile_path = profile_path;
        this.credit_id = credit_id;
        this.cast_id = cast_id;
        this.gender = gender;
        this.id = id;
        this.order = order;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
