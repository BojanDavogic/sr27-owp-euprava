package com.eUprava.service.impl;

import com.eUprava.dao.NabavkaVakcineDAO;
import com.eUprava.dao.PrijavaZaVakcinuDAO;
import com.eUprava.model.NabavkaVakcine;
import com.eUprava.model.PrijavaZaVakcinu;
import com.eUprava.service.NabavkaVakcineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBNabavkaVakcineServiceImpl implements NabavkaVakcineService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private NabavkaVakcineDAO nabavkaVakcineDAO;
    @Override
    public NabavkaVakcine findNabavkaVakcine(Long id) {
        return nabavkaVakcineDAO.findNabavkaVakcine(id);
    }

    @Override
    public List<NabavkaVakcine> findSveNabavkeVakcine() {
        return nabavkaVakcineDAO.findSveNabavkeVakcine();
    }

    @Override
    public List<NabavkaVakcine> findSveNabavkeVakcineNaCekanju() {
        return nabavkaVakcineDAO.findSveNabavkeVakcineNaCekanju();
    }

    @Override
    public List<NabavkaVakcine> findSveOdobreneNabavkeVakcine() {
        return nabavkaVakcineDAO.findSveOdobreneNabavkeVakcine();
    }

    @Override
    public List<NabavkaVakcine> findSveOdbijeneNabavkeVakcine() {
        return nabavkaVakcineDAO.findSveOdbijeneNabavkeVakcine();
    }

    @Override
    public List<NabavkaVakcine> findSveNabavkeVakcineNaReviziji() {
        return nabavkaVakcineDAO.findSveNabavkeVakcineNaReviziji();
    }

    @Override
    public NabavkaVakcine save(NabavkaVakcine nabavkaVakcine) {
        nabavkaVakcineDAO.save(nabavkaVakcine);
        return nabavkaVakcine;
    }

    @Override
    public NabavkaVakcine update(NabavkaVakcine nabavkaVakcine) {
        nabavkaVakcineDAO.update(nabavkaVakcine);
        return nabavkaVakcine;
    }

    @Override
    public NabavkaVakcine delete(Long id) {
        NabavkaVakcine nabavkaVakcine = findNabavkaVakcine(id);
        if(nabavkaVakcine != null){
            nabavkaVakcineDAO.delete(id);
        }
        return nabavkaVakcine;
    }
}
