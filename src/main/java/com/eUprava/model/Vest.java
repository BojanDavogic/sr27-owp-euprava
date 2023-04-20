package com.eUprava.model;

import org.springframework.format.annotation.DateTimeFormat;

public class Vest {
    private int id;
    private String nazivVesti;
    private String sadrzaj;
    private DateTimeFormat datumIVremeObjavljivanja;

    public Vest() {
    }

    public Vest(int id, String nazivVesti, String sadrzaj, DateTimeFormat datumIVremeObjavljivanja) {
        this.id = id;
        this.nazivVesti = nazivVesti;
        this.sadrzaj = sadrzaj;
        this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivVesti() {
        return nazivVesti;
    }

    public void setNazivVesti(String nazivVesti) {
        this.nazivVesti = nazivVesti;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public DateTimeFormat getDatumIVremeObjavljivanja() {
        return datumIVremeObjavljivanja;
    }

    public void setDatumIVremeObjavljivanja(DateTimeFormat datumIVremeObjavljivanja) {
        this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
    }

    @Override
    public String toString() {
        return "Vest{" +
                "Id=" + id +
                ", NazivVesti='" + nazivVesti + '\'' +
                ", Sadrzaj='" + sadrzaj + '\'' +
                ", DatumIVremeObjavljivanja=" + datumIVremeObjavljivanja +
                '}';
    }
}
