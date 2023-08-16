package com.eUprava.service.impl;

import com.eUprava.dao.VakcinaDAO;
import com.eUprava.dao.VestDAO;
import com.eUprava.model.Vakcina;
import com.eUprava.model.Vest;
import com.eUprava.service.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class DBVestServiceImpl implements VestService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private VestDAO vestDAO;
    @Override
    public Vest findVest(Long id) {
        return vestDAO.findVest(id);
    }

    @Override
    public List<Vest> findSveVesti() {
        return vestDAO.findSveVesti();
    }

    @Override
    public Vest save(Vest vest) {
        vestDAO.save(vest);
        return vest;
    }

    @Override
    public Vest delete(Long id) {
        Vest vest = findVest(id);
        if(vest != null){
            vestDAO.delete(id);
        }
        return vest;
    }

    @Override
    public Vest update(Vest vest) {
        vestDAO.update(vest);
        return vest;
    }
}
