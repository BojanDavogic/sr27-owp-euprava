package com.eUprava.dao;

import com.eUprava.model.VestOObolelima;

import java.util.List;

public interface VestOObolelimaDAO {
    public VestOObolelima findOne(Long id);
    public List<VestOObolelima> findAll();
    public Boolean save(VestOObolelima vestObolelima);
    public Boolean update(VestOObolelima vestObolelima);
    public Boolean delete(Long id);
}
