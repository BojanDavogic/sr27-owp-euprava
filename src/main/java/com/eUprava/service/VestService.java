package com.eUprava.service;

import com.eUprava.model.Vest;

import java.util.List;

public interface VestService {
    Vest findVest(Long id);
    List<Vest> findSveVesti();
    Vest save(Vest vest);
    Vest delete(Long id);
    Vest update(Vest vest);
}
