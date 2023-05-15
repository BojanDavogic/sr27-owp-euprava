package com.eUprava.dao;

import com.eUprava.model.Vakcina;

import java.util.List;

public interface VakcinaDAO {
    public Vakcina findVakcina(Long id);
    public Vakcina findVakcinaByNaziv(String nazivProizvodjaca);
    public Vakcina findVakcinaByDrzava(String drzavaProizvodnje);
    public Vakcina findVakcinaByKolicina(int minKolicina, int maxKolicina);
    public List<Vakcina> sortVakcine(String sort);
    public List<Vakcina> findSveVakcine();
    public Boolean save(Vakcina vakcina);
    public Boolean update(Vakcina vakcina);
    public Boolean delete(Long id);
}
