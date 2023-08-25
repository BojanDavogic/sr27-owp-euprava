package com.eUprava.model;

public class Vakcina {
    private Long id;
    private String ime;
    private int dostupnaKolicina;
    private ProizvodjacVakcine proizvodjac;
    private boolean jeObrisan;

    public Vakcina() {
    }

    public Vakcina(Long id, String ime, int dostupnaKolicina, ProizvodjacVakcine proizvodjac, boolean jeObrisan) {
        this.id = id;
        this.ime = ime;
        this.dostupnaKolicina = dostupnaKolicina;
        this.proizvodjac = proizvodjac;
        this.jeObrisan = jeObrisan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isJeObrisan() {
        return jeObrisan;
    }

    public void setJeObrisan(boolean jeObrisan) {
        this.jeObrisan = jeObrisan;
    }

    @Override
    public String toString() {
        return "Vakcina{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", dostupnaKolicina=" + dostupnaKolicina +
                ", proizvodjac=" + proizvodjac +
                ", jeObrisan=" + jeObrisan +
                '}';
    }
}
