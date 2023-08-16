package com.eUprava.service.impl;

import com.eUprava.dao.KorisnikDAO;
import com.eUprava.model.Korisnik;
import com.eUprava.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DBKorisnikServiceImpl implements KorisnikService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KorisnikDAO korisnikDAO;
    @Override
    public Korisnik findKorisnik(Long id) {
        return korisnikDAO.findKorisnik(id);
    }

    @Override
    public Korisnik findKorisnikByEmail(String email) {
        return korisnikDAO.findKorisnikByEmail(email);
    }

    @Override
    public Korisnik findKorisnikByEmailAndPassword(String email, String lozinka) {
        return korisnikDAO.findKorisnikByEmailAndPassword(email, lozinka);
    }

    @Override
    public List<Korisnik> findSviKorisnici() {
        return korisnikDAO.findSviKorisnici();
    }

    @Override
    public Korisnik save(Korisnik korisnik) {
        korisnikDAO.save(korisnik);
        return korisnik;
    }

    @Override
    public Korisnik update(Korisnik korisnik) {
        korisnikDAO.update(korisnik);
        return korisnik;
    }

    @Override
    public Korisnik delete(Long id) {
        Korisnik korisnik = findKorisnik(id);
        if(korisnik != null){
            korisnikDAO.delete(id);
        }
        return korisnik;
    }
}
