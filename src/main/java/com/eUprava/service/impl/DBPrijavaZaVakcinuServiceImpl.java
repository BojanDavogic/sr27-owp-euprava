package com.eUprava.service.impl;

import com.eUprava.dao.KorisnikDAO;
import com.eUprava.dao.PrijavaZaVakcinuDAO;
import com.eUprava.model.PrijavaZaVakcinu;
import com.eUprava.service.PrijavaZaVakcinuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DBPrijavaZaVakcinuServiceImpl implements PrijavaZaVakcinuService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PrijavaZaVakcinuDAO prijavaZaVakcinuDAO;
    @Override
    public PrijavaZaVakcinu findPrijavaZaVakcinu(Long id) {
        return prijavaZaVakcinuDAO.findPrijavaZaVakcinu(id);
    }

    @Override
    public boolean existsPrijavaZaPacijentaIVakcinu(Long pacijentId, Long vakcinaId) {
        return prijavaZaVakcinuDAO.existsPrijavaZaPacijentaIVakcinu(pacijentId, vakcinaId);
    }

    @Override
    public List<PrijavaZaVakcinu> findSvePrijaveZaVakcinu() {
        return prijavaZaVakcinuDAO.findSvePrijaveZaVakcinu();
    }

    @Override
    public List<PrijavaZaVakcinu> findPrijavaByImePrezimeAndJmbg(String ime, String prezime, String jmbg) {
        return prijavaZaVakcinuDAO.findPrijavaByImePrezimeAndJmbg(ime, prezime, jmbg);
    }

    @Override
    public PrijavaZaVakcinu save(PrijavaZaVakcinu prijavaZaVakcinu) {
        prijavaZaVakcinuDAO.save(prijavaZaVakcinu);
        return prijavaZaVakcinu;
    }

    @Override
    public PrijavaZaVakcinu update(PrijavaZaVakcinu prijavaZaVakcinu) {
        prijavaZaVakcinuDAO.update(prijavaZaVakcinu);
        return prijavaZaVakcinu;
    }

    @Override
    public PrijavaZaVakcinu delete(Long id) {
        PrijavaZaVakcinu prijavaZaVakcinu = findPrijavaZaVakcinu(id);
        if(prijavaZaVakcinu != null){
            prijavaZaVakcinuDAO.delete(id);
        }
        return prijavaZaVakcinu;
    }
}
