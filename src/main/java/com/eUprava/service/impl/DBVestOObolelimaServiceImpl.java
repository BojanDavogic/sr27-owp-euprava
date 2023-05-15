package com.eUprava.service.impl;

import com.eUprava.dao.VestDAO;
import com.eUprava.dao.VestOObolelimaDAO;
import com.eUprava.model.Vest;
import com.eUprava.model.VestOObolelima;
import com.eUprava.service.VestOObolelimaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DBVestOObolelimaServiceImpl implements VestOObolelimaService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private VestOObolelimaDAO vestOObolelimaDAO;
    @Override
    public VestOObolelima findVestOObolelima(Long id) {
        return vestOObolelimaDAO.findVestOObolelima(id);
    }

    @Override
    public List<VestOObolelima> findSveVestiOObolelima() {
        return vestOObolelimaDAO.findSveVestiOObolelima();
    }

    @Override
    public VestOObolelima save(VestOObolelima vestOObolelima) {
        vestOObolelimaDAO.save(vestOObolelima);
        return vestOObolelima;
    }

    @Override
    public VestOObolelima update(VestOObolelima vestOObolelima) {
        return vestOObolelima;
    }

    @Override
    public VestOObolelima delete(Long id) {
        VestOObolelima vestOObolelima = findVestOObolelima(id);
        if(vestOObolelima != null){
            vestOObolelimaDAO.delete(id);
        }
        return vestOObolelima;
    }
}
