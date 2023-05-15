package com.eUprava.dao;

import com.eUprava.model.ProizvodjacVakcine;

import java.util.List;

public interface ProizvodjacVakcineDAO {
    public ProizvodjacVakcine findProizvodjacVakcine(Long id);
    public List<ProizvodjacVakcine> findSviProizvodjaciVakcine();
    public Boolean save(ProizvodjacVakcine proizvodjacVakcine);
    public Boolean update(ProizvodjacVakcine proizvodjacVakcine);
    public Boolean delete(Long id);
}
