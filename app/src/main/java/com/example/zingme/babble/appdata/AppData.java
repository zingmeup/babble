package com.example.zingme.babble.appdata;

import com.example.zingme.babble.models.Features;

import java.util.ArrayList;

public class AppData {
    private static AppData data;
    public ArrayList<Features> features;
    public ArrayList<String> resultList;


    public AppData() {
        this.features = new ArrayList<>();
        resultList=new ArrayList<>();
        populateFeatures();
    }

    private void populateFeatures() {
        features.add(new Features("Means like", "words with a meaning similar to ringing in the ears", "https://api.datamuse.com/words?ml="));
        features.add(new Features("Related to","words related to duck","https://api.datamuse.com/words?ml=[arg0]&sp=b*&max=100"));
        features.add(new Features("Sounds like", "words that sound like elefint", "https://api.datamuse.com/words?sl="));
        features.add(new Features("Rhymes with", "words that rhyme with forgetful", "https://api.datamuse.com/words?rel_rhy="));
        features.add(new Features("Describes", "adjectives that are often used to describe ocean", "https://api.datamuse.com/words?rel_jjb="));
    }

    public static AppData getData() {
        if (data==null){
            data=new AppData();
        }
        return data;
    }

    public ArrayList<String> getResultList() {
        return resultList;
    }
}
