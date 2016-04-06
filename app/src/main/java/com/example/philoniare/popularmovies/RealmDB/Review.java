package com.example.philoniare.popularmovies.RealmDB;

import io.realm.RealmObject;

public class Review extends RealmObject {
    private int id;
    private String author;
    private String content;
    private String url;
}
