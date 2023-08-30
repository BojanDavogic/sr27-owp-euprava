package com.eUprava.dao;

import com.eUprava.model.PrijavaZaVakcinu;

import java.util.List;

public interface PrijavaZaVakcinuDAO {
    public PrijavaZaVakcinu findPrijavaZaVakcinu(Long id);
    public boolean existsPrijavaZaPacijentaIVakcinu(Long pacijentId, Long vakcinaId);
    public List<PrijavaZaVakcinu> findSvePrijaveZaVakcinu ();
    public List<PrijavaZaVakcinu> findPrijavaByImePrezimeAndJmbg(String ime, String prezime, String jmbg);
    public Boolean save(PrijavaZaVakcinu prijavaZaVakcinu);
    public Boolean update(PrijavaZaVakcinu prijavaZaVakcinu);
    public Boolean delete(Long id);
}
