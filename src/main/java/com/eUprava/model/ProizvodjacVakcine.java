package com.eUprava.model;

public class ProizvodjacVakcine {
    private Long id;
    private String proizvodjac;
    private String drzavaProizvodnje;

    public ProizvodjacVakcine() {
    }

    public ProizvodjacVakcine(Long id, String proizvodjac, String drzavaProizvodnje) {
        this.id = id;
        this.proizvodjac = proizvodjac;
        this.drzavaProizvodnje = drzavaProizvodnje;
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

    @Override
    public String toString() {
        return "ProizvodjacVakcine{" +
                "Id=" + id +
                ", Proizvodjac='" + proizvodjac + '\'' +
                ", DrzavaProizvodnje='" + drzavaProizvodnje + '\'' +
                '}';
    }
}
