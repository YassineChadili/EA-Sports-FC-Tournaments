package com.yassine.easportsfc;

public class Partij {
    private int id;
    private Player speler1;
    private Player speler2;
    private Toernooi toernooi;
    private String uitslag;

    // Constructor
    public Partij(int id, Player speler1, Player speler2, Toernooi toernooi, String uitslag) {
        this.id = id;
        this.speler1 = speler1;
        this.speler2 = speler2;
        this.toernooi = toernooi;
        this.uitslag = uitslag;
    }

    // Getters en setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getSpeler1() {
        return speler1;
    }

    public void setSpeler1(Player speler1) {
        this.speler1 = speler1;
    }

    public Player getSpeler2() {
        return speler2;
    }

    public void setSpeler2(Player speler2) {
        this.speler2 = speler2;
    }

    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
    }

    public String getUitslag() {
        return uitslag;
    }

    public void setUitslag(String uitslag) {
        this.uitslag = uitslag;
    }
}
