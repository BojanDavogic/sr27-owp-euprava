package com.eUprava.model;

import java.time.LocalDateTime;

public class NabavkaVakcine {
    private Long id;
    private int kolicinaVakcina;
    private String razlogNabavke;
    private LocalDateTime datumIVremeKreiranjaZahteva;
    private Korisnik medicinskoOsoblje;
    private Vakcina vakcina;
    private String razlogOdbijanjaZahteva;
    private String status;
    private boolean jeObrisan;

    public NabavkaVakcine() {
    }

    public NabavkaVakcine(Long id, int kolicinaVakcina, String razlogNabavke, LocalDateTime datumIVremeKreiranjaZahteva, Korisnik medicinskoOsoblje, Vakcina vakcina, String razlogOdbijanjaZahteva, String status, boolean jeObrisan) {
        this.id = id;
        this.kolicinaVakcina = kolicinaVakcina;
        this.razlogNabavke = razlogNabavke;
        this.datumIVremeKreiranjaZahteva = datumIVremeKreiranjaZahteva;
        this.medicinskoOsoblje = medicinskoOsoblje;
        this.vakcina = vakcina;
        this.razlogOdbijanjaZahteva = razlogOdbijanjaZahteva;
        this.status = status;
        this.jeObrisan = jeObrisan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getKolicinaVakcina() {
        return kolicinaVakcina;
    }

    public void setKolicinaVakcina(int kolicinaVakcina) {
        this.kolicinaVakcina = kolicinaVakcina;
    }

    public String getRazlogNabavke() {
        return razlogNabavke;
    }

    public void setRazlogNabavke(String razlogNabavke) {
        this.razlogNabavke = razlogNabavke;
    }

    public LocalDateTime getDatumIVremeKreiranjaZahteva() {
        return datumIVremeKreiranjaZahteva;
    }

    public void setDatumIVremeKreiranjaZahteva(LocalDateTime datumIVremeKreiranjaZahteva) {
        this.datumIVremeKreiranjaZahteva = datumIVremeKreiranjaZahteva;
    }

    public Korisnik getMedicinskoOsoblje() {
        return medicinskoOsoblje;
    }

    public void setMedicinskoOsoblje(Korisnik medicinskoOsoblje) {
        this.medicinskoOsoblje = medicinskoOsoblje;
    }

    public Vakcina getVakcina() {
        return vakcina;
    }

    public void setVakcina(Vakcina vakcina) {
        this.vakcina = vakcina;
    }

    public String getRazlogOdbijanjaZahteva() {
        return razlogOdbijanjaZahteva;
    }

    public void setRazlogOdbijanjaZahteva(String razlogOdbijanjaZahteva) {
        this.razlogOdbijanjaZahteva = razlogOdbijanjaZahteva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isJeObrisan() {
        return jeObrisan;
    }

    public void setJeObrisan(boolean jeObrisan) {
        this.jeObrisan = jeObrisan;
    }

    @Override
    public String toString() {
        return "NabavkaVakcine{" +
                "id=" + id +
                ", kolicinaVakcina=" + kolicinaVakcina +
                ", razlogNabavke='" + razlogNabavke + '\'' +
                ", datumIVremeKreiranjaZahteva=" + datumIVremeKreiranjaZahteva +
                ", medicinskoOsoblje=" + medicinskoOsoblje +
                ", vakcina=" + vakcina +
                ", razlogOdbijanjaZahteva='" + razlogOdbijanjaZahteva + '\'' +
                ", status='" + status + '\'' +
                ", jeObrisan=" + jeObrisan +
                '}';
    }
}
