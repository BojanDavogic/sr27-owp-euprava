package com.eUprava.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class Vest {
    private Long id;
    private String nazivVesti;
    private String sadrzaj;
    private LocalDateTime datumIVremeObjavljivanja;

    public Vest() {
    }

    public Vest(Long id, String nazivVesti, String sadrzaj, LocalDateTime datumIVremeObjavljivanja) {
        this.id = id;
        this.nazivVesti = nazivVesti;
        this.sadrzaj = sadrzaj;
        this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getDatumIVremeObjavljivanja() {
        return datumIVremeObjavljivanja;
    }

    public void setDatumIVremeObjavljivanja(LocalDateTime datumIVremeObjavljivanja) {
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
