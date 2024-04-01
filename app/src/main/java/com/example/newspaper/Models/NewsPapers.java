package com.example.newspaper.Models;

public class NewsPapers {
    int image;
    String name,link;

    public NewsPapers(int image, String name, String link) {
        this.image = image;
        this.name = name;
        this.link = link;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
