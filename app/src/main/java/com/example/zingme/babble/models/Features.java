package com.example.zingme.babble.models;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

public class Features {
    String title,description,api;

    public Features(String title, String description, String api) {
        this.title = title;
        this.description = description;
        this.api = api;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getApi() {
        return api;
    }
}
