package com.eUprava.dao.impl;

import com.eUprava.dao.ProizvodjacVakcineDAO;
import com.eUprava.model.Korisnik;
import com.eUprava.model.ProizvodjacVakcine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Repository
public class ProizvodjacVakcineDAOImpl implements ProizvodjacVakcineDAO {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ProizvodjacVakcineRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, ProizvodjacVakcine> proizvodjaciVakcine = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String proizvodjac = rs.getString(index++);
            String drzavaProizvodnje = rs.getString(index++);

            ProizvodjacVakcine proizvodjacVakcine = proizvodjaciVakcine.get(id);
            if(proizvodjacVakcine == null){
                proizvodjacVakcine = new ProizvodjacVakcine(id, proizvodjac, drzavaProizvodnje);
                proizvodjaciVakcine.put(proizvodjacVakcine.getId(), proizvodjacVakcine);
            }
        }
        public List<ProizvodjacVakcine> getProizvodjaciVakcine() {
            return new ArrayList<>(proizvodjaciVakcine.values());
        }
    }

    @Override
    public ProizvodjacVakcine findProizvodjacVakcine(Long id) {
        String query = "SELECT * FROM proizvodjacivakcine" +
                        "WHERE id = ? " +
                        "ORDER BY id";
        ProizvodjacVakcineRowCallBackHandler proizvodjacVakcineRowCallBackHandler = new ProizvodjacVakcineRowCallBackHandler();
        jdbcTemplate.query(query, proizvodjacVakcineRowCallBackHandler, id);
        return proizvodjacVakcineRowCallBackHandler.getProizvodjaciVakcine().get(0);
    }

    @Override
    public List<ProizvodjacVakcine> findSviProizvodjaciVakcine() {

        String query = "SELECT * FROM proizvodjacivakcine" +
                        "ORDER BY id";
        ProizvodjacVakcineRowCallBackHandler proizvodjacVakcineRowCallBackHandler = new ProizvodjacVakcineRowCallBackHandler();
        jdbcTemplate.query(query, proizvodjacVakcineRowCallBackHandler);
        return proizvodjacVakcineRowCallBackHandler.getProizvodjaciVakcine();
    }

    @Override
    public Boolean save(ProizvodjacVakcine proizvodjacVakcine) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query = "INSERT INTO proizvodjacivakcine (proizvodjac, drzavaProizvodnje)" +
                                "VALUES (?, ?)";

                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, proizvodjacVakcine.getProizvodjac());
                preparedStatement.setString(index++, proizvodjacVakcine.getDrzavaProizvodnje());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }

    @Override
    public Boolean update(ProizvodjacVakcine proizvodjacVakcine) {
        String query = " UPDATE proizvodjacivakcine SET proizvodjac = ?, drzavaProizvodnje = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(query, proizvodjacVakcine.getProizvodjac(), proizvodjacVakcine.getDrzavaProizvodnje(), proizvodjacVakcine.getId());
        return uspeh > 0;
    }

    @Override
    public Boolean delete(Long id) {
        String query = "DELETE FROM proizvodjacivakcine WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
