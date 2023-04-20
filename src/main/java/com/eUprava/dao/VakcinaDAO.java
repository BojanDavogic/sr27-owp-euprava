package com.eUprava.dao;

import com.eUprava.model.Vakcina;

import java.util.List;

public interface VakcinaDAO {
    public Vakcina findOne(Long id);
    public Vakcina findOneByNaziv(String nazivProizvodjaca);
    public Vakcina findOneByDrzava(String drzavaProizvodnje);
    public Vakcina findOneByKolicina(int minKolicina, int maxKolicina);
    public List<Vakcina> sortAll(String sort);
    public List<Vakcina> findAll();
    public Boolean save(Vakcina vakcina);
    public Boolean update(Vakcina vakcina);
    public Boolean delete(Long id); //moze koristiti int ili boolean
}
