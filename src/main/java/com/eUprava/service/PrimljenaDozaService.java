package com.eUprava.service;

import com.eUprava.model.Korisnik;
import com.eUprava.model.PrimljenaDoza;

import java.util.List;

public interface PrimljenaDozaService {
    PrimljenaDoza findPrimljenaDoza(Long id);
    List<PrimljenaDoza> findSvePrimljeneDoze();
    List<PrimljenaDoza> findPrimljeneDozeByPacijent(Long pacijentId);
    PrimljenaDoza findPoslednjaDozaZaPacijenta(Long pacijentId);
    PrimljenaDoza save(PrimljenaDoza primljenaDoza);
    PrimljenaDoza update(PrimljenaDoza primljenaDoza);
    PrimljenaDoza delete(Long id);
}
