package com.eUprava.dao.impl;

import com.eUprava.dao.ProizvodjacVakcineDAO;
import com.eUprava.dao.VestDAO;
import com.eUprava.model.ProizvodjacVakcine;
import com.eUprava.model.Vakcina;
import com.eUprava.model.Vest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Repository
public class VestDAOImpl implements VestDAO {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class VestRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, Vest> vesti = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String naziv = rs.getString(index++);
            String sadrzaj = rs.getString(index++);
            LocalDateTime datumIVremeObjavljivanja = rs.getTimestamp(index++).toLocalDateTime();

            Vest vest = vesti.get(id);
            if(vest == null) {
                vest = new Vest(id, naziv, sadrzaj, datumIVremeObjavljivanja);
                vesti.put(vest.getId(), vest);
            }
        }
        public List<Vest> getVesti() {
            return new ArrayList<>(vesti.values());
        }
    }
    @Override
    public Vest findVest(Long id) {
        String query = "SELECT id, naziv, sadrzaj, datumIVremeObjavljivanja FROM vesti" +
                        " WHERE id = ?" +
                        "ORDER BY id";

        VestDAOImpl.VestRowCallBackHandler vestRowCallBackHandler = new VestDAOImpl.VestRowCallBackHandler();
        jdbcTemplate.query(query, vestRowCallBackHandler, id);

        return vestRowCallBackHandler.getVesti().get(0);
    }

    @Override
    public List<Vest> findSveVesti() {
        String query = "SELECT id, naziv, sadrzaj, datumIVremeObjavljivanja FROM vesti" +
                        " ORDER BY id";

        VestDAOImpl.VestRowCallBackHandler vestRowCallBackHandler = new VestDAOImpl.VestRowCallBackHandler();
        jdbcTemplate.query(query, vestRowCallBackHandler);

        return vestRowCallBackHandler.getVesti();
    }

    @Override
    public Boolean save(Vest vest) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query = "INSERT INTO vesti (naziv, sadrzaj, datumIVremeObjavljivanja) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1 ;
                preparedStatement.setString(index++, vest.getNazivVesti());
                preparedStatement.setString(index++, vest.getSadrzaj());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(vest.getDatumIVremeObjavljivanja()));


                return preparedStatement;

            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }

    @Override
    public Boolean delete(Long id) {
        String query = "DELETE FROM vesti WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }

    @Override
    public Boolean update(Vest vest) {
        String query = " UPDATE vesti SET naziv = ?, sadrzaj = ?, datumIVremeObjavljivanja = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(query, vest.getNazivVesti(), vest.getSadrzaj(), vest.getDatumIVremeObjavljivanja(), vest.getId());
        return uspeh > 0;
    }
}
