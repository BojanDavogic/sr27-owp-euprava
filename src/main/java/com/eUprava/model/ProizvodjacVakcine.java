package com.eUprava.model;

public class ProizvodjacVakcine {
    private Long id;
    private String proizvodjac;
    private String drzavaProizvodnje;
    private boolean jeObrisan;

    public ProizvodjacVakcine() {
    }

    public ProizvodjacVakcine(Long id, String proizvodjac, String drzavaProizvodnje, boolean jeObrisan) {
        this.id = id;
        this.proizvodjac = proizvodjac;
        this.drzavaProizvodnje = drzavaProizvodnje;
        this.jeObrisan = jeObrisan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getDrzavaProizvodnje() {
        return drzavaProizvodnje;
    }

    public void setDrzavaProizvodnje(String drzavaProizvodnje) {
        this.drzavaProizvodnje = drzavaProizvodnje;
    }

    public boolean isJeObrisan() {
        return jeObrisan;
    }

    public void setJeObrisan(boolean jeObrisan) {
        this.jeObrisan = jeObrisan;
    }

    @Override
    public String toString() {
        return "ProizvodjacVakcine{" +
                "id=" + id +
                ", proizvodjac='" + proizvodjac + '\'' +
                ", drzavaProizvodnje='" + drzavaProizvodnje + '\'' +
                ", jeObrisan=" + jeObrisan +
                '}';
    }
}
