package com.eUprava.service.impl;

import com.eUprava.dao.VakcinaDAO;
import com.eUprava.model.Vakcina;
import com.eUprava.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DBVakcinaServiceImpl implements VakcinaService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private VakcinaDAO vakcinaDAO;
    @Override
    public Vakcina findVakcina(Long id) {
        return vakcinaDAO.findVakcina(id);
    }

    @Override
    public List<Vakcina> findVakcinaByNaziv(String naziv) {
        return vakcinaDAO.findVakcinaByNaziv(naziv);
    }

    @Override
    public List<Vakcina> findVakcinaByNazivProizvodjaca(String nazivProizvodjaca) {
        return vakcinaDAO.findVakcinaByNazivProizvodjaca(nazivProizvodjaca);
    }

    @Override
    public List<Vakcina> findVakcinaByDrzava(String drzavaProizvodnje) {
        return vakcinaDAO.findVakcinaByDrzava(drzavaProizvodnje);
    }

    @Override
    public List<Vakcina> findVakcinaByKolicina(int minKolicina, int maxKolicina) {
        return vakcinaDAO.findVakcinaByKolicina(minKolicina, maxKolicina);
    }

    @Override
    public List<Vakcina> sortVakcine(List<Vakcina> vakcine, String sort) {
        return vakcinaDAO.sortVakcine(vakcine, sort);
    }

    @Override
    public List<Vakcina> findSveVakcine() {
        return vakcinaDAO.findSveVakcine();
    }

    @Override
    public Vakcina save(Vakcina vakcina) {
        vakcinaDAO.save(vakcina);
        return vakcina;
    }

    @Override
    public Vakcina update(Vakcina vakcina) {
        vakcinaDAO.update(vakcina);
        return vakcina;
    }

    @Override
    public Vakcina delete(Long id) {
        Vakcina vakcina = findVakcina(id);
        if(vakcina != null){
            vakcinaDAO.delete(id);
        }
        return vakcina;
    }
}
