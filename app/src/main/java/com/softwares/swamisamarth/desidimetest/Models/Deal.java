package com.softwares.swamisamarth.desidimetest.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Narendra on 3/20/2016.
 */
public class Deal extends Item {

    @SerializedName("title")
    private String title;

    @SerializedName("image_thumb")
    private String imagePath;

    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
