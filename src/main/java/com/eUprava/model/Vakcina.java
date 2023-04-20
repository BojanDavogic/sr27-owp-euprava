package com.eUprava.model;

public class Vakcina {
    private int id;
    private String ime;
    private int dostupnaKolicina;
    private ProizvodjacVakcine proizvodjac;

    public Vakcina() {
    }

    public Vakcina(int id, String ime, int dostupnaKolicina, ProizvodjacVakcine proizvodjac) {
        this.id = id;
        this.ime = ime;
        this.dostupnaKolicina = dostupnaKolicina;
        this.proizvodjac = proizvodjac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getDostupnaKolicina() {
        return dostupnaKolicina;
    }

    public void setDostupnaKolicina(int dostupnaKolicina) {
        this.dostupnaKolicina = dostupnaKolicina;
    }

    public ProizvodjacVakcine getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(ProizvodjacVakcine proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    @Override
    public String toString() {
        return "Vakcina{" +
                "Id=" + id +
                ", Ime='" + ime + '\'' +
                ", DostupnaKolicina=" + dostupnaKolicina +
                ", Proizvodjac=" + proizvodjac +
                '}';
    }
}
