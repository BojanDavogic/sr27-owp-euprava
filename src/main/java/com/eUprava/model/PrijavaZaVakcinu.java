package com.eUprava.model;

import java.time.LocalDateTime;

public class PrijavaZaVakcinu {
    private Long id;
    private LocalDateTime datumIVremePrijave;
    private Korisnik pacijent;
    private Vakcina vakcina;
    private boolean jeObrisan;

    public PrijavaZaVakcinu() {
    }

    public PrijavaZaVakcinu(Long id, LocalDateTime datumIVremePrijave, Korisnik pacijent, Vakcina vakcina, boolean jeObrisan) {
        this.id = id;
        this.datumIVremePrijave = datumIVremePrijave;
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

    public LocalDateTime getDatumIVremePrijave() {
        return datumIVremePrijave;
    }

    public void setDatumIVremePrijave(LocalDateTime datumIVremePrijave) {
        this.datumIVremePrijave = datumIVremePrijave;
    }

    public Korisnik getPacijent() {
        return pacijent;
    }

    public void setPacijent(Korisnik pacijent) {
        this.pacijent = pacijent;
    }

    public Vakcina getVakcina() {
        return vakcina;
    }

    public void setVakcina(Vakcina vakcina) {
        this.vakcina = vakcina;
    }

    public boolean isJeObrisan() {
        return jeObrisan;
    }

    public void setJeObrisan(boolean jeObrisan) {
        this.jeObrisan = jeObrisan;
    }

    @Override
    public String toString() {
        return "PrijavaZaVakcinu{" +
                "id=" + id +
                ", datumIVremePrijave=" + datumIVremePrijave +
                ", pacijent=" + pacijent +
                ", vakcina=" + vakcina +
                ", jeObrisan=" + jeObrisan +
                '}';
    }
}
