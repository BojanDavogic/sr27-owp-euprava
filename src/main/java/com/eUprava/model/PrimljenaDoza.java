package com.eUprava.model;

import java.time.LocalDateTime;

public class PrimljenaDoza {
    private Long id;
    private int doza;
    private LocalDateTime datumIVremeDobijanjaDoze;
    private Korisnik pacijent;
    private Vakcina vakcina;
    private boolean jeObrisan;

    public PrimljenaDoza() {
    }

    public PrimljenaDoza(Long id, int doza, LocalDateTime datumIVremeDobijanjaDoze, Korisnik pacijent, Vakcina vakcina, boolean jeObrisan) {
        this.id = id;
        this.doza = doza;
        this.datumIVremeDobijanjaDoze = datumIVremeDobijanjaDoze;
        this.pacijent = pacijent;
        this.vakcina = vakcina;
        this.jeObrisan = jeObrisan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDoza() {
        return doza;
    }

    public void setDoza(int doza) {
        this.doza = doza;
    }

    public LocalDateTime getDatumIVremeDobijanjaDoze() {
        return datumIVremeDobijanjaDoze;
    }

    public void setDatumIVremeDobijanjaDoze(LocalDateTime datumIVremeDobijanjaDoze) {
        this.datumIVremeDobijanjaDoze = datumIVremeDobijanjaDoze;
    }

    public Korisnik getPacijent() {
        return pacijent;
    }

    public void setPacijent(Korisnik pacijent) {
        this.pacijent = pacijent;
    }

    public boolean isJeObrisan() {
        return jeObrisan;
    }

    public void setJeObrisan(boolean jeObrisan) {
        this.jeObrisan = jeObrisan;
    }

    public Vakcina getVakcina() {
        return vakcina;
    }

    public void setVakcina(Vakcina vakcina) {
        this.vakcina = vakcina;
    }

    @Override
    public String toString() {
        return "PrimljenaDoza{" +
                "id=" + id +
                ", doza=" + doza +
                ", datumIVremeDobijanjaDoze=" + datumIVremeDobijanjaDoze +
                ", pacijent=" + pacijent +
                ", vakcina=" + vakcina +
                ", jeObrisan=" + jeObrisan +
                '}';
    }
}
