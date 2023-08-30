package com.eUprava.service.impl;

import com.eUprava.dao.PrijavaZaVakcinuDAO;
import com.eUprava.dao.PrimljenaDozaDAO;
import com.eUprava.model.Korisnik;
import com.eUprava.model.PrijavaZaVakcinu;
import com.eUprava.model.PrimljenaDoza;
import com.eUprava.service.PrimljenaDozaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DBPrimljenaDozaServiceImpl implements PrimljenaDozaService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PrimljenaDozaDAO primljenaDozaDAO;
    @Override
    public PrimljenaDoza findPrimljenaDoza(Long id) {
        return primljenaDozaDAO.findPrimljenaDoza(id);
    }

    @Override
    public List<PrimljenaDoza> findSvePrimljeneDoze() {
        return primljenaDozaDAO.findSvePrimljeneDoze();
    }

    @Override
    public List<PrimljenaDoza> findPrimljeneDozeByPacijent(Long pacijentId) {
        return primljenaDozaDAO.findPrimljeneDozeByPacijent(pacijentId);
    }

    @Override
    public PrimljenaDoza findPoslednjaDozaZaPacijenta(Long pacijentId) {
        return primljenaDozaDAO.findPoslednjaDozaZaPacijenta(pacijentId);
    }

    @Override
    public PrimljenaDoza save(PrimljenaDoza primljenaDoza) {
        primljenaDozaDAO.save(primljenaDoza);
        return primljenaDoza;
    }

    @Override
    public PrimljenaDoza update(PrimljenaDoza primljenaDoza) {
        primljenaDozaDAO.update(primljenaDoza);
        return primljenaDoza;
    }

    @Override
    public PrimljenaDoza delete(Long id) {
        PrimljenaDoza primljenaDoza = findPrimljenaDoza(id);
        if(primljenaDoza != null){
            primljenaDozaDAO.delete(id);
        }
        return primljenaDoza;
    }
}
