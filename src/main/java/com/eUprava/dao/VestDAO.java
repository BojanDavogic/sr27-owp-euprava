package com.eUprava.dao;

import com.eUprava.model.Vest;

import java.util.List;

public interface VestDAO {
    public Vest findOne(Long id);
    public List<Vest> findAll();
    public Boolean save(Vest vest);
    public Boolean delete(Long id);
    public Boolean update(Vest vest);
}
