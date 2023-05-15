package com.eUprava.service;

import com.eUprava.model.ProizvodjacVakcine;

import java.util.List;

public interface ProizvodjacVakcineService {
    ProizvodjacVakcine findProizvodjacVakcine(Long id);
    List<ProizvodjacVakcine> findSviProizvodjaciVakcine();
    ProizvodjacVakcine save(ProizvodjacVakcine proizvodjacVakcine);
    ProizvodjacVakcine update(ProizvodjacVakcine proizvodjacVakcine);
    ProizvodjacVakcine delete(Long id);
}
