package com.eUprava.service.impl;

import com.eUprava.dao.ProizvodjacVakcineDAO;
import com.eUprava.dao.VakcinaDAO;
import com.eUprava.model.ProizvodjacVakcine;
import com.eUprava.model.Vakcina;
import com.eUprava.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class DBVakcinaServiceImpl implements VakcinaService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private VakcinaDAO vakcinaDAO;
    @Override
    public Vakcina findVakcina(Long id) {
        return vakcinaDAO.findVakcina(id);
    }

    @Override
    public Vakcina findVakcinaByNaziv(String nazivProizvodjaca) {
        return vakcinaDAO.findVakcinaByNaziv(nazivProizvodjaca);
    }

    @Override
    public Vakcina findVakcinaByDrzava(String drzavaProizvodnje) {
        return vakcinaDAO.findVakcinaByDrzava(drzavaProizvodnje);
    }

    @Override
    public Vakcina findVakcinaByKolicina(int minKolicina, int maxKolicina) {
        return vakcinaDAO.findVakcinaByKolicina(minKolicina, maxKolicina);
    }

    @Override
    public List<Vakcina> sortVakcine(String sort) {
        return null;
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
