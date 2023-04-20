package com.eUprava.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class Korisnik {
    private Long id;
    private String email;
    private String lozinka;
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    private String jmbg;
    private String adresa;
    private int brojTelefona;
    private LocalDateTime datumIVremeRegistracije;
    private Uloga uloga;

    public Korisnik() {
    }

    public Korisnik(Long id, String email, String lozinka, String ime, String prezime,
                    Date datumRodjenja, String jmbg, String adresa, int brojTelefona,
                    LocalDateTime datumIVremeRegistracije, Uloga uloga) {
        this.id = id;
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.jmbg = jmbg;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.datumIVremeRegistracije = datumIVremeRegistracije;
        this.uloga = uloga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(int brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public LocalDateTime getDatumIVremeRegistracije() {
        return datumIVremeRegistracije;
    }

    public void setDatumIVremeRegistracije(LocalDateTime datumIVremeRegistracije) {
        this.datumIVremeRegistracije = datumIVremeRegistracije;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "Id=" + id +
                ", Email='" + email + '\'' +
                ", Lozinka='" + lozinka + '\'' +
                ", Ime='" + ime + '\'' +
                ", Prezime='" + prezime + '\'' +
                ", DatumRodjenja=" + datumRodjenja +
                ", Jmbg='" + jmbg + '\'' +
                ", Adresa='" + adresa + '\'' +
                ", BrojTelefona=" + brojTelefona +
                ", DatumIVremeRegistracije=" + datumIVremeRegistracije +
                ", Uloga=" + uloga +
                '}';
    }
}
