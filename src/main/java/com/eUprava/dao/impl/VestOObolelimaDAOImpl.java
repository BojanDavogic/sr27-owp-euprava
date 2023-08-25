package com.eUprava.dao.impl;

import com.eUprava.dao.VestOObolelimaDAO;
import com.eUprava.model.Vest;
import com.eUprava.model.VestOObolelima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Repository
public class VestOObolelimaDAOImpl implements VestOObolelimaDAO {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class VestOObolelimaRowCallBackHandler implements RowCallbackHandler {
        private Map<Long, VestOObolelima> vestiOObolelima = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            int oboleliUDanu = rs.getInt(index++);
            int testiraniUDanu = rs.getInt(index++);;
            int ukupnoOboleli = rs.getInt(index++);;
            int hospitalizovani = rs.getInt(index++);;
            int pacijentiNaRespiratoru = rs.getInt(index++);;
            LocalDateTime datumIVremeObjavljivanja = rs.getTimestamp(index++).toLocalDateTime();
            boolean jeObrisan = Boolean.valueOf(rs.getString(index++));

            VestOObolelima vestOObolelima = vestiOObolelima.get(id);
            if(vestOObolelima == null) {
                vestOObolelima = new VestOObolelima(id, oboleliUDanu, testiraniUDanu, ukupnoOboleli, hospitalizovani, pacijentiNaRespiratoru, datumIVremeObjavljivanja, jeObrisan);
                vestiOObolelima.put(vestOObolelima.getId(), vestOObolelima);
            }
        }
        public List<VestOObolelima> getVestiOObolelima() {
            return new ArrayList<>(vestiOObolelima.values());
        }
    }

    @Override
    public VestOObolelima findVestOObolelima(Long id) {
        String query = "SELECT id, oboleliUDanu, testiraniUDanu, getUkupnoOboleli(id), hospitalizovani, pacijentiNaRespiratoru, datumIVremeObjavljivanja, jeObrisan FROM vestiOObolelima" +
                " WHERE id = ? AND jeObrisan = 0 " +
                " ORDER BY id";

        VestOObolelimaDAOImpl.VestOObolelimaRowCallBackHandler vestOObolelimaRowCallBackHandler = new VestOObolelimaDAOImpl.VestOObolelimaRowCallBackHandler();
        jdbcTemplate.query(query, vestOObolelimaRowCallBackHandler, id);

        return vestOObolelimaRowCallBackHandler.getVestiOObolelima().get(0);
    }

    @Override
    public List<VestOObolelima> findSveVestiOObolelima() {
        String query = "SELECT id, oboleliUDanu, testiraniUDanu, getUkupnoOboleli(id), hospitalizovani, pacijentiNaRespiratoru, datumIVremeObjavljivanja, jeObrisan FROM vestiOObolelima " +
                        "WHERE jeObrisan = 0 " +
                        "ORDER BY id";

        VestOObolelimaDAOImpl.VestOObolelimaRowCallBackHandler vestOObolelimaRowCallBackHandler = new VestOObolelimaDAOImpl.VestOObolelimaRowCallBackHandler();
        jdbcTemplate.query(query, vestOObolelimaRowCallBackHandler);

        return vestOObolelimaRowCallBackHandler.getVestiOObolelima();
    }
    @Transactional
    @Override
    public Boolean save(VestOObolelima vestObolelima) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query = "INSERT INTO vestiOObolelima (oboleliUDanu, testiraniUDanu, hospitalizovani, pacijentiNaRespiratoru, datumIVremeObjavljivanja, jeObrisan) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1 ;
                preparedStatement.setInt(index++, vestObolelima.getOboleliUDanu());
                preparedStatement.setInt(index++, vestObolelima.getTestiraniUDanu());
                preparedStatement.setInt(index++, vestObolelima.getHospitalizovani());
                preparedStatement.setInt(index++, vestObolelima.getPacijentiNaRespiratoru());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(vestObolelima.getDatumIVremeObjavljivanja()));
                preparedStatement.setBoolean(index++, vestObolelima.isJeObrisan());


                return preparedStatement;

            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean update(VestOObolelima vestOObolelima) {
        String query = " UPDATE vestiOObolelima SET oboleliUDanu = ?, testiraniUDanu = ?, hospitalizovani = ?, pacijentiNaRespiratoru = ?, datumIVremeObjavljivanja = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(query, vestOObolelima.getOboleliUDanu(), vestOObolelima.getTestiraniUDanu(), vestOObolelima.getHospitalizovani(), vestOObolelima.getPacijentiNaRespiratoru(), vestOObolelima.getDatumIVremeObjavljivanja(), vestOObolelima.getId());
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean delete(Long id) {
        String query = "UPDATE vestiOObolelima SET jeObrisan = 1 WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
