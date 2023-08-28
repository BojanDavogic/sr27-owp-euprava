package com.eUprava.dao.impl;

import com.eUprava.dao.ProizvodjacVakcineDAO;
import com.eUprava.dao.VakcinaDAO;
import com.eUprava.model.ProizvodjacVakcine;
import com.eUprava.model.Vakcina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Repository
public class VakcinaDAOImpl implements VakcinaDAO {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ProizvodjacVakcineDAO proizvodjacVakcineDAO;

    private class VakcinaRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, Vakcina> vakcine = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String ime = rs.getString(index++);
            int dostupnaKolicina = rs.getInt(index++);
            Long proizvodjacId = rs.getLong(index++);
            ProizvodjacVakcine proizvodjac = proizvodjacVakcineDAO.findProizvodjacVakcine(proizvodjacId);
            boolean jeObrisan = Boolean.valueOf(rs.getString(index++));

            Vakcina vakcina = vakcine.get(id);
            if(vakcina == null) {
                vakcina = new Vakcina(id, ime, dostupnaKolicina, proizvodjac, jeObrisan);
                vakcine.put(vakcina.getId(), vakcina);
            }
        }
        public List<Vakcina> getVakcine() {
            return new ArrayList<>(vakcine.values());
        }
    }

    @Override
    public Vakcina findVakcina(Long id) {
        String query = "SELECT v.id, v.ime, v.dostupnaKolicina, v.proizvodjacVakcineId, v.jeObrisan FROM vakcine v, proizvodjaciVakcine p " +
                "WHERE v.id = ? AND v.jeObrisan = 0 " +
                "ORDER BY v.id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, vakcinaRowCallBackHandler, id);

        return vakcinaRowCallBackHandler.getVakcine().get(0);
    }

    @Override
    public List<Vakcina> findVakcinaByNaziv(String naziv) {
        System.out.println("Pozvan je findVakcinaByNaziv");
        System.out.println("Naziv vakcine " + naziv);
        String query = "SELECT v.id, v.ime, v.dostupnaKolicina, v.proizvodjacVakcineId, v.jeObrisan FROM vakcine v, proizvodjaciVakcine p " +
                "WHERE v.ime = ? AND v.jeObrisan = 0 " +
                "ORDER BY v.id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, vakcinaRowCallBackHandler, naziv);

        return vakcinaRowCallBackHandler.getVakcine();
    }

    @Override
    public List<Vakcina> findVakcinaByNazivProizvodjaca(String nazivProizvodjaca) {
        System.out.println("Pozvan je findVakcinaByNazivProizvodjaca");
        System.out.println("Naziv proizvodjaca " + nazivProizvodjaca);
        String query = "SELECT v.id, v.ime, v.dostupnaKolicina, v.proizvodjacVakcineId, v.jeObrisan, p.proizvodjac FROM vakcine v, proizvodjaciVakcine p " +
                "WHERE v.proizvodjacVakcineId = p.id  AND p.proizvodjac = ? AND v.jeObrisan = 0 " +
                "ORDER BY v.id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, vakcinaRowCallBackHandler, nazivProizvodjaca);

        return vakcinaRowCallBackHandler.getVakcine();
    }

    @Override
    public List<Vakcina> findVakcinaByDrzava(String drzavaProizvodnje) {
        System.out.println("Pozvan je findVakcinaByDrzava");
        System.out.println("Drzava proizvodnje " + drzavaProizvodnje);
        String query = "SELECT v.id, v.ime, v.dostupnaKolicina, v.proizvodjacVakcineId, v.jeObrisan, p.drzavaProizvodnje FROM vakcine v, proizvodjaciVakcine p " +
                "WHERE v.proizvodjacVakcineId = p.id  AND p.drzavaProizvodnje = ? AND v.jeObrisan = 0 " +
                "ORDER BY v.id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, vakcinaRowCallBackHandler, drzavaProizvodnje);

        return vakcinaRowCallBackHandler.getVakcine();
    }

    @Override
    public List<Vakcina> findVakcinaByKolicina(int minKolicina, int maxKolicina) {
        System.out.println("Pozvan je findVakcinaByKolicina");
        System.out.println("Min Kolicina " + minKolicina + " Max kolicina " + maxKolicina);
        ArrayList<Object> listaArgumenata = new ArrayList<>();

        String query = "SELECT v.id, v.ime, v.dostupnaKolicina, v.proizvodjacVakcineId, v.jeObrisan FROM vakcine v";
        StringBuffer whereQuery = new StringBuffer(" WHERE ");
        boolean argumenti = false;

        if(minKolicina != 0){
            if(argumenti)
                whereQuery.append(" AND ");
            whereQuery.append("v.dostupnaKolicina >= ?");
            argumenti = true;
            listaArgumenata.add(minKolicina);
        }

        if(maxKolicina != 0){
            if(argumenti)
                whereQuery.append(" AND ");
            whereQuery.append("v.dostupnaKolicina <= ?");
            argumenti = true;
            listaArgumenata.add(maxKolicina);
        }

        if(argumenti)
            query=query + whereQuery.toString() + " ORDER BY v.id";
        else
            query=query + " ORDER BY v.id";
        System.out.println(query);

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, listaArgumenata.toArray(), vakcinaRowCallBackHandler);
        return vakcinaRowCallBackHandler.getVakcine();
    }


    @Override
    public List<Vakcina> sortVakcine(List<Vakcina> vakcine, String sort) {
        System.out.println("Pozvan je sortVakcine");
        System.out.println(sort + " Sortiranje");

        vakcine.sort((v1, v2) -> {
            if (sort.equals("imeASC")) {
                return v1.getIme().compareTo(v2.getIme());
            } else if (sort.equals("imeDESC")) {
                return v2.getIme().compareTo(v1.getIme());
            } else if (sort.equals("kolicinaASC")) {
                return Integer.compare(v1.getDostupnaKolicina(), v2.getDostupnaKolicina());
            } else if (sort.equals("kolicinaDESC")) {
                return Integer.compare(v2.getDostupnaKolicina(), v1.getDostupnaKolicina());
            } else if (sort.equals("proizvodjacASC")) {
                return v1.getProizvodjac().getProizvodjac().compareTo(v2.getProizvodjac().getProizvodjac());
            } else if (sort.equals("proizvodjacDESC")) {
                return v2.getProizvodjac().getProizvodjac().compareTo(v1.getProizvodjac().getProizvodjac());
            } else if (sort.equals("drzavaProizvodjacaASC")) {
                return v1.getProizvodjac().getDrzavaProizvodnje().compareTo(v2.getProizvodjac().getDrzavaProizvodnje());
            } else if (sort.equals("drzavaProizvodjacaDESC")) {
                return v2.getProizvodjac().getDrzavaProizvodnje().compareTo(v1.getProizvodjac().getDrzavaProizvodnje());
            }
            return 0;
        });

        return vakcine;
    }



    @Override
    public List<Vakcina> findSveVakcine() {
        System.out.println("Pozvan je findSveVakcine");
        String query = "SELECT v.id, v.ime, v.dostupnaKolicina, v.proizvodjacVakcineId, p.proizvodjac FROM vakcine v, proizvodjaciVakcine p " +
                        "WHERE v.proizvodjacVakcineId = p.id AND v.jeObrisan = 0 " +
                        "ORDER BY id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, vakcinaRowCallBackHandler);

        return vakcinaRowCallBackHandler.getVakcine();
    }
    @Transactional
    @Override
    public Boolean save(Vakcina vakcina) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query = "INSERT INTO vakcine (ime, dostupnaKolicina, proizvodjacVakcineId, jeObrisan) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1 ;
                preparedStatement.setString(index++, vakcina.getIme());
                preparedStatement.setInt(index++, vakcina.getDostupnaKolicina());
                preparedStatement.setLong(index++, vakcina.getProizvodjac().getId());
                preparedStatement.setBoolean(index++, vakcina.isJeObrisan());


                return preparedStatement;

            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean update(Vakcina vakcina) {
        String sql = "UPDATE vakcine SET ime = ?, dostupnaKolicina = ?, proizvodjacVakcineId = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(sql, vakcina.getIme(), vakcina.getDostupnaKolicina(), vakcina.getProizvodjac().getId(), vakcina.getId());
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean delete(Long id) {
        String query = "UPDATE vakcine SET jeObrisan = 1 WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
