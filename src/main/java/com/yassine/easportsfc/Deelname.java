package com.yassine.easportsfc;

public class Deelname {
    private Player speler;
    private Toernooi toernooi;

    // Constructor
    public Deelname(Player speler, Toernooi toernooi) {
        this.speler = speler;
        this.toernooi = toernooi;
    }

    // Getters en setters
    public Player getSpeler() {
        return speler;
    }

    public void setSpeler(Player speler) {
        this.speler = speler;
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
    }
}
