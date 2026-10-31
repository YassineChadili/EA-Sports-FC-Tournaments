package com.yassine.easportsfc;

public class Deelname {
    private Speler speler;
    private Toernooi toernooi;

    // Constructor
    public Deelname(Speler speler, Toernooi toernooi) {
        this.speler = speler;
        this.toernooi = toernooi;
    }

    // Getters en setters
    public Speler getSpeler() {
        return speler;
    }

    public void setSpeler(Speler speler) {
        this.speler = speler;
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
    }
}
