package com.eUprava.model;

import java.time.LocalDateTime;

public class Vest {
    private Long id;
    private String naziv;
    private String sadrzaj;
    private LocalDateTime datumIVremeObjavljivanja;
    private boolean jeObrisan;
    public Vest() {
    }

    public Vest(Long id, String naziv, String sadrzaj, LocalDateTime datumIVremeObjavljivanja, boolean jeObrisan) {
        this.id = id;
        this.naziv = naziv;
        this.sadrzaj = sadrzaj;
        this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
        this.jeObrisan = jeObrisan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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

    public boolean isJeObrisan() {
        return jeObrisan;
    }

    public void setJeObrisan(boolean jeObrisan) {
        this.jeObrisan = jeObrisan;
    }

    @Override
    public String toString() {
        return "Vest{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", sadrzaj='" + sadrzaj + '\'' +
                ", datumIVremeObjavljivanja=" + datumIVremeObjavljivanja +
                ", jeObrisan=" + jeObrisan +
                '}';
    }
}
