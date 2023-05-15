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

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

            Vakcina vakcina = vakcine.get(id);
            if(vakcina == null) {
                vakcina = new Vakcina(id, ime, dostupnaKolicina, proizvodjac);
                vakcine.put(vakcina.getId(), vakcina);
            }
        }
        public List<Vakcina> getVakcine() {
            return new ArrayList<>(vakcine.values());
        }
    }

    @Override
    public Vakcina findVakcina(Long id) {
        String query = "SELECT v.id, v.ime, v.dostupnaKolicina, p.proizvodjacVakcineId FROM vakcine v, proizvodjacivakcine p " +
                "WHERE v.id=? " +
                "ORDER BY v.id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, vakcinaRowCallBackHandler, id);

        return vakcinaRowCallBackHandler.getVakcine().get(0);
    }

    @Override
    public Vakcina findVakcinaByNaziv(String nazivProizvodjaca) {
        String sql = "SELECT v.id, v.ime, v.dostupnaKolicina, v.proizvodjacVakcineId FROM vakcine v, proizvodjacivakcine p " +
                "WHERE v.proizvodjacVakcineId = p.id  AND p.proizvodjac = ? " +
                "ORDER BY v.id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, vakcinaRowCallBackHandler, nazivProizvodjaca);

        return vakcinaRowCallBackHandler.getVakcine().get(0);
    }

    @Override
    public Vakcina findVakcinaByDrzava(String drzavaProizvodnje) {
        String sql = "SELECT v.id, v.ime, v.dostupnaKolicina, v.proizvodjacVakcineId FROM vakcine v, proizvodjacivakcine p " +
                "WHERE v.proizvodjacVakcineId = p.id  AND p.drzavaProizvodnje = ? " +
                "ORDER BY v.id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, vakcinaRowCallBackHandler, drzavaProizvodnje);

        return vakcinaRowCallBackHandler.getVakcine().get(0);
    }

    @Override
    public Vakcina findVakcinaByKolicina(int minKolicina, int maxKolicina) {
        ArrayList<Object> listaArgumenata = new ArrayList<>();

        String query = "SELECT id, ime, dostupnaKolicina, proizvodjacVakcineId FROM vakcine";
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
            query=query + "ORDER BY v.id";
        System.out.println(query);

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, vakcinaRowCallBackHandler);
        return vakcinaRowCallBackHandler.getVakcine().get(0);
    }

    @Override
    public List<Vakcina> sortVakcine(String sort) {
        return null;
    }

    @Override
    public List<Vakcina> findSveVakcine() {
        String query = "SELECT id, ime, dostupnaKolicina, proizvodjacVakcineId FROM vakcine" +
                "order by v.id";

        VakcinaRowCallBackHandler vakcinaRowCallBackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(query, vakcinaRowCallBackHandler);

        return vakcinaRowCallBackHandler.getVakcine();
    }

    @Override
    public Boolean save(Vakcina vakcina) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query = "INSERT INTO vakcine (ime, dostupnaKolicina, proizvodjacVackineId) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1 ;
                preparedStatement.setString(index++, vakcina.getIme());
                preparedStatement.setInt(index++, vakcina.getDostupnaKolicina());
                preparedStatement.setLong(index++, vakcina.getProizvodjac().getId());


                return preparedStatement;

            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }

    @Override
    public Boolean update(Vakcina vakcina) {
        String sql = "UPDATE vakcina SET ime=?, dostupnaKolicina=?, proizvodjacVakcineId=? WHERE id=?";
        int uspeh = jdbcTemplate.update(sql, vakcina.getIme(), vakcina.getDostupnaKolicina(), vakcina.getProizvodjac().getId());
        return uspeh > 0;
    }

    @Override
    public Boolean delete(Long id) {
        String query = "DELETE FROM vakcine WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
