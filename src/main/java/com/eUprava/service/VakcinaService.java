package com.eUprava.service;

import com.eUprava.model.Vakcina;

import java.util.List;

public interface VakcinaService {
    Vakcina findVakcina(Long id);
    List<Vakcina> findVakcinaByNaziv(String naziv);
    List<Vakcina> findVakcinaByNazivProizvodjaca(String nazivProizvodjaca);
    List<Vakcina> findVakcinaByDrzava(String drzavaProizvodnje);
    List<Vakcina> findVakcinaByKolicina(int minKolicina, int maxKolicina);
    List<Vakcina> sortVakcine(List<Vakcina> vakcine, String sort);
    List<Vakcina> findSveVakcine();
    Vakcina save(Vakcina vakcina);
    Vakcina update(Vakcina vakcina);
    Vakcina delete(Long id);
}
