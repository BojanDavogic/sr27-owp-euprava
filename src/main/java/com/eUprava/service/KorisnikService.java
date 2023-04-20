package com.eUprava.service;

import com.eUprava.model.Korisnik;

import java.util.List;

public interface KorisnikService {
    Korisnik findKorisnik(Long id);
    Korisnik findKorisnikByEmail(String email);
    Korisnik findKorisnikByEmailAndPassword(String email, String lozinka);
    List<Korisnik> findKorisnici();
    Korisnik save(Korisnik korisnik);
    Korisnik update(Korisnik korisnik);
    Korisnik delete(Long id);
}
