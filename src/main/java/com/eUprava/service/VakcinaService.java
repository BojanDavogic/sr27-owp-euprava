package com.eUprava.service;

import com.eUprava.model.Vakcina;

import java.util.List;

public interface VakcinaService {
    Vakcina findVakcina(Long id);
    Vakcina findVakcinaByNaziv(String nazivProizvodjaca);
    Vakcina findVakcinaByDrzava(String drzavaProizvodnje);
    Vakcina findVakcinaByKolicina(int minKolicina, int maxKolicina);
    List<Vakcina> sortVakcine(String sort);
    List<Vakcina> findSveVakcine();
    Vakcina save(Vakcina vakcina);
    Vakcina update(Vakcina vakcina);
    Vakcina delete(Long id);
}
