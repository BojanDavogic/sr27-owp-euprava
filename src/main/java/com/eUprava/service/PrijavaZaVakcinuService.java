package com.eUprava.service;

import com.eUprava.model.PrijavaZaVakcinu;

import java.util.List;

public interface PrijavaZaVakcinuService {
    PrijavaZaVakcinu findPrijavaZaVakcinu(Long id);
    boolean existsPrijavaZaPacijentaIVakcinu(Long pacijentId, Long vakcinaId);
    List<PrijavaZaVakcinu> findSvePrijaveZaVakcinu ();
    List<PrijavaZaVakcinu> findPrijavaByImePrezimeAndJmbg(String ime, String prezime, String jmbg);
    PrijavaZaVakcinu save(PrijavaZaVakcinu prijavaZaVakcinu);
    PrijavaZaVakcinu update(PrijavaZaVakcinu prijavaZaVakcinu);
    PrijavaZaVakcinu delete(Long id);
}
