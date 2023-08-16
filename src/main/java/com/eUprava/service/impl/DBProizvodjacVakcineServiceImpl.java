package com.eUprava.service.impl;

import com.eUprava.dao.KorisnikDAO;
import com.eUprava.dao.ProizvodjacVakcineDAO;
import com.eUprava.model.ProizvodjacVakcine;
import com.eUprava.service.ProizvodjacVakcineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class DBProizvodjacVakcineServiceImpl implements ProizvodjacVakcineService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ProizvodjacVakcineDAO proizvodjacVakcineDAO;
    @Override
    public ProizvodjacVakcine findProizvodjacVakcine(Long id) {
        return proizvodjacVakcineDAO.findProizvodjacVakcine(id);
    }

    @Override
    public List<ProizvodjacVakcine> findSviProizvodjaciVakcine() {
        return proizvodjacVakcineDAO.findSviProizvodjaciVakcine();
    }

    @Override
    public ProizvodjacVakcine save(ProizvodjacVakcine proizvodjacVakcine) {
        proizvodjacVakcineDAO.save(proizvodjacVakcine);
        return proizvodjacVakcine;
    }

    @Override
    public ProizvodjacVakcine update(ProizvodjacVakcine proizvodjacVakcine) {
        proizvodjacVakcineDAO.update(proizvodjacVakcine);
        return proizvodjacVakcine;
    }

    @Override
    public ProizvodjacVakcine delete(Long id) {
        ProizvodjacVakcine proizvodjacVakcine = findProizvodjacVakcine(id);
        if(proizvodjacVakcine != null){
            proizvodjacVakcineDAO.delete(id);
        }
        return proizvodjacVakcine;
    }
}
