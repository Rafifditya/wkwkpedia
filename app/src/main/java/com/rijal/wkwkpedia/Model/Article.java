package com.rijal.wkwkpedia.Model;

public class Article {
    private int id;
    private String judul;
    private String description;

    public Article(int id, String judul, String description) {
        this.id = id;
        this.judul = judul;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
