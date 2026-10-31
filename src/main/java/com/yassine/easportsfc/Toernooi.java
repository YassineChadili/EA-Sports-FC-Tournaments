package com.yassine.easportsfc;

import java.time.LocalDate;

public class Toernooi {
    private int id;
    private String naam;
    private LocalDate datum;

    // Constructor
    public Toernooi(int id, String naam, LocalDate datum) {
        this.id = id;
        this.naam = naam;
        this.datum = datum;
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

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}
