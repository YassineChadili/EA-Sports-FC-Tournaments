package com.yassine.easportsfc;

public class Player {
    private int id;
    private String naam;
    private int rating;

    // Constructor
    public Player(int id, String naam, int rating) {
        this.id = id;
        this.naam = naam;
        this.rating = rating;
    }

    // Getters en setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
