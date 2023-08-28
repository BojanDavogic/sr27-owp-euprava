package com.eUprava.dao;

import com.eUprava.model.Vakcina;

import java.util.List;

public interface VakcinaDAO {
    public Vakcina findVakcina(Long id);
    public List<Vakcina> findVakcinaByNaziv(String naziv);
    public List<Vakcina> findVakcinaByNazivProizvodjaca(String nazivProizvodjaca);
    public List<Vakcina> findVakcinaByDrzava(String drzavaProizvodnje);
    public List<Vakcina> findVakcinaByKolicina(int minKolicina, int maxKolicina);
    public List<Vakcina> sortVakcine(List<Vakcina> vakcine, String sort);
    public List<Vakcina> findSveVakcine();
    public Boolean save(Vakcina vakcina);
    public Boolean update(Vakcina vakcina);
    public Boolean delete(Long id);
}
