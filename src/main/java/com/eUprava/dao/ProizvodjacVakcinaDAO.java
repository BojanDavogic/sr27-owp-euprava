package com.eUprava.dao;

import com.eUprava.model.ProizvodjacVakcine;

import java.util.List;

public interface ProizvodjacVakcinaDAO {
    public ProizvodjacVakcine findOne(Long id);
    public List<ProizvodjacVakcine> findAll();
    public Boolean save(ProizvodjacVakcine proizvodjacVakcine);
    public Boolean update(ProizvodjacVakcine proizvodjacVakcine);
    public Boolean delete(Long id);
}
