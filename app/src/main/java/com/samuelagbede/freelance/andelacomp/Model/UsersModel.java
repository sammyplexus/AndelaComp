package com.samuelagbede.freelance.andelacomp.Model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Agbede Samuel D on 3/6/2017.
 */

public class UsersModel implements Serializable{
    private String username;
    private String image_url;
    private String profile_url;
    private int user_id;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UsersModel(){

    }

    public UsersModel(int user_id, String username, String image_url, String profile_url){
        this.username = username;
        this.image_url = image_url;
        this.profile_url = profile_url;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getProfile_url() {
        return profile_url;
    }


    public String getImage_url() {
        return image_url;
    }


    public String getUsername() {
        return username;
    }




}
