package com.eUprava.service;

import com.eUprava.model.NabavkaVakcine;

import java.util.List;

public interface NabavkaVakcineService {
    NabavkaVakcine findNabavkaVakcine(Long id);
    List<NabavkaVakcine> findSveNabavkeVakcine();
    List<NabavkaVakcine> findSveNabavkeVakcineNaCekanju();
    List<NabavkaVakcine> findSveOdobreneNabavkeVakcine();
    List<NabavkaVakcine> findSveOdbijeneNabavkeVakcine();
    List<NabavkaVakcine> findSveNabavkeVakcineNaReviziji();
    NabavkaVakcine save(NabavkaVakcine nabavkaVakcine);
    NabavkaVakcine update(NabavkaVakcine nabavkaVakcine);
    NabavkaVakcine delete(Long id);
}
