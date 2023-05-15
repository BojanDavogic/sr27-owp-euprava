package com.eUprava.model;

import java.time.LocalDateTime;
import java.util.Date;

public class VestOObolelima {
    private Long id;
    private int oboleliUDanu;
    private int testiraniUDanu;
    private int ukupnoOboleli;
    private int hospitalizovani;
    private int pacijentiNaRespiratoru;
    private LocalDateTime datumIVremeObjavljivanja;

    public VestOObolelima() {
    }

    public VestOObolelima(Long id, int oboleliUDanu, int testiraniUDanu, int ukupnoOboleli,
                          int hospitalizovani, int pacijentiNaRespiratoru, LocalDateTime datumIVremeObjavljivanja) {
        this.id = id;
        this.oboleliUDanu = oboleliUDanu;
        this.testiraniUDanu = testiraniUDanu;
        this.ukupnoOboleli = ukupnoOboleli;
        this.hospitalizovani = hospitalizovani;
        this.pacijentiNaRespiratoru = pacijentiNaRespiratoru;
        this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOboleliUDanu() {
        return oboleliUDanu;
    }

    public void setOboleliUDanu(int oboleliUDanu) {
        this.oboleliUDanu = oboleliUDanu;
    }

    public int getTestiraniUDanu() {
        return testiraniUDanu;
    }

    public void setTestiraniUDanu(int testiraniUDanu) {
        this.testiraniUDanu = testiraniUDanu;
    }

    public int getUkupnoOboleli() {
        return ukupnoOboleli;
    }

    public void setUkupnoOboleli(int ukupnoOboleli) {
        this.ukupnoOboleli = ukupnoOboleli;
    }

    public int getHospitalizovani() {
        return hospitalizovani;
    }

    public void setHospitalizovani(int hospitalizovani) {
        this.hospitalizovani = hospitalizovani;
    }

    public int getPacijentiNaRespiratoru() {
        return pacijentiNaRespiratoru;
    }

    public void setPacijentiNaRespiratoru(int pacijentiNaRespiratoru) {
        this.pacijentiNaRespiratoru = pacijentiNaRespiratoru;
    }

    public LocalDateTime getDatumIVremeObjavljivanja() {
        return datumIVremeObjavljivanja;
    }

    public void setDatumIVremeObjavljivanja(LocalDateTime datumIVremeObjavljivanja) {
        this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
    }


    @Override
    public String toString() {
        return "VestOObolelima{" +
                "Id=" + id +
                ", OboleliUDanu=" + oboleliUDanu +
                ", TestiraniUDanu=" + testiraniUDanu +
                ", UkupnoOboleli=" + ukupnoOboleli +
                ", Hospitalizovani=" + hospitalizovani +
                ", PacijentiNaRespiratoru=" + pacijentiNaRespiratoru +
                ", DatumIVremeObjavljivanja=" + datumIVremeObjavljivanja +
                '}';
    }
}
