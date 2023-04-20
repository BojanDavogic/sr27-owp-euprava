package com.eUprava.dao;

import com.eUprava.model.Korisnik;

import java.util.List;

public interface KorisnikDAO {
    public Korisnik findKorisnik(Long id);
    public Korisnik findKorisnikByEmail(String email);
    public Korisnik findKorisnikByEmailAndPassword(String email, String lozinka);
    public List<Korisnik> findKorisnici();
    public Boolean save(Korisnik korisnik);
    public Boolean update(Korisnik korisnik);
    public Boolean delete(Long id);
}
