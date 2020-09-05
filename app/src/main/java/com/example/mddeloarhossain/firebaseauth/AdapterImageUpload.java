package com.example.mddeloarhossain.firebaseauth;

import com.google.firebase.database.Exclude;

/**
 * Created by MD. DELOAR HOSSAIN on 30-Jun-19.
 */

public class AdapterImageUpload {
    private String imageName;
    private String imageUrl;

    private String key;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public AdapterImageUpload(){

    }

    public AdapterImageUpload(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
