package com.eUprava.dao;

import com.eUprava.model.Korisnik;
import com.eUprava.model.PrimljenaDoza;

import java.util.List;

public interface PrimljenaDozaDAO {
    public PrimljenaDoza findPrimljenaDoza(Long id);
    public List<PrimljenaDoza> findSvePrimljeneDoze();
    public List<PrimljenaDoza> findPrimljeneDozeByPacijent(Long pacijentId);
    public PrimljenaDoza findPoslednjaDozaZaPacijenta(Long pacijentId);
    public Boolean save(PrimljenaDoza primljenaDoza);
    public Boolean update(PrimljenaDoza primljenaDoza);
    public Boolean delete(Long id);
}
