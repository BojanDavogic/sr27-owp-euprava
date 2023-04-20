package com.eUprava.model;

public class ProizvodjacVakcine {
    private int id;
    private String proizvodjac;
    private String drzavaProizvodnje;

    public ProizvodjacVakcine() {
    }

    public ProizvodjacVakcine(int id, String proizvodjac, String drzavaProizvodnje) {
        this.id = id;
        this.proizvodjac = proizvodjac;
        this.drzavaProizvodnje = drzavaProizvodnje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
