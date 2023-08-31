package com.eUprava.dao;

import com.eUprava.model.NabavkaVakcine;

import java.util.List;

public interface NabavkaVakcineDAO {
    public NabavkaVakcine findNabavkaVakcine(Long id);
    public List<NabavkaVakcine> findSveNabavkeVakcine();
    public List<NabavkaVakcine> findSveNabavkeVakcineNaCekanju();
    public List<NabavkaVakcine> findSveOdobreneNabavkeVakcine();
    public List<NabavkaVakcine> findSveOdbijeneNabavkeVakcine();
    public List<NabavkaVakcine> findSveNabavkeVakcineNaReviziji();
    public Boolean save(NabavkaVakcine nabavkaVakcine);
    public Boolean update(NabavkaVakcine nabavkaVakcine);
    public Boolean delete(Long id);
}
