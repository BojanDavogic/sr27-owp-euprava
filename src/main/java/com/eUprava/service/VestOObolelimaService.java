package com.eUprava.service;

import com.eUprava.model.VestOObolelima;

import java.util.List;

public interface VestOObolelimaService {
    VestOObolelima findVestOObolelima(Long id);
    List<VestOObolelima> findSveVestiOObolelima();
    VestOObolelima save(VestOObolelima vestOObolelima);
    VestOObolelima update(VestOObolelima vestOObolelima);
    VestOObolelima delete(Long id);
}
