package com.branimir.example.recyclerviewexample;

public class Article {

    private String title;
    private String category;

    public Article(String title, String category) {
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }
}
