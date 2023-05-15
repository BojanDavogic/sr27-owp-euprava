package com.eUprava.dao;

import com.eUprava.model.VestOObolelima;

import java.util.List;

public interface VestOObolelimaDAO {
    public VestOObolelima findVestOObolelima(Long id);
    public List<VestOObolelima> findSveVestiOObolelima();
    public Boolean save(VestOObolelima vestObolelima);
    public Boolean update(VestOObolelima vestOObolelima);
    public Boolean delete(Long id);
}
