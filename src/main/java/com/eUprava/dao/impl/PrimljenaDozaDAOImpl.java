package com.eUprava.dao.impl;

import com.eUprava.dao.KorisnikDAO;
import com.eUprava.dao.PrimljenaDozaDAO;
import com.eUprava.dao.VakcinaDAO;
import com.eUprava.model.Korisnik;
import com.eUprava.model.PrijavaZaVakcinu;
import com.eUprava.model.PrimljenaDoza;
import com.eUprava.model.Vakcina;
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
public class PrimljenaDozaDAOImpl implements PrimljenaDozaDAO {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KorisnikDAO korisnikDAO;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private VakcinaDAO vakcinaDAO;

    private class PrimljenaDozaRowCallBackHandler implements RowCallbackHandler {
        private Map<Long, PrimljenaDoza> primljenedoze = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            int doza = rs.getInt(index++);
            LocalDateTime datumIVremeDobijanjaDoze = rs.getTimestamp(index++).toLocalDateTime();
            Long pacijentId = rs.getLong(index++);
            Korisnik pacijent = korisnikDAO.findKorisnik(pacijentId);
            Long vakcinaId = rs.getLong(index++);
            Vakcina vakcina = vakcinaDAO.findVakcina(vakcinaId);
            boolean jeObrisan = Boolean.valueOf(rs.getString(index++));

            PrimljenaDoza primljenaDoza = primljenedoze.get(id);
            if(primljenaDoza == null) {
                primljenaDoza = new PrimljenaDoza(id, doza, datumIVremeDobijanjaDoze, pacijent, vakcina, jeObrisan);
                primljenedoze.put(primljenaDoza.getId(), primljenaDoza);
            }
        }
        public List<PrimljenaDoza> getPrimljeneDoze() {
            return new ArrayList<>(primljenedoze.values());
        }
    }

    @Override
    public PrimljenaDoza findPrimljenaDoza(Long id) {
        String query = "SELECT id, doza, datumIVremeDobijanjaDoze, pacijentId, vakcinaId, jeObrisan FROM primljeneDoze " +
                "WHERE id = ? AND jeObrisan = 0 " +
                "ORDER BY id";

        PrimljenaDozaDAOImpl.PrimljenaDozaRowCallBackHandler primljenaDozaRowCallBackHandler = new PrimljenaDozaDAOImpl.PrimljenaDozaRowCallBackHandler();
        jdbcTemplate.query(query, primljenaDozaRowCallBackHandler, id);

        return primljenaDozaRowCallBackHandler.getPrimljeneDoze().get(0);
    }

    @Override
    public List<PrimljenaDoza> findSvePrimljeneDoze() {
        String query = "SELECT id, doza, datumIVremeDobijanjaDoze, pacijentId, vakcinaId, jeObrisan FROM primljeneDoze " +
                "WHERE jeObrisan = 0 " +
                "ORDER BY id";

        PrimljenaDozaDAOImpl.PrimljenaDozaRowCallBackHandler primljenaDozaRowCallBackHandler = new PrimljenaDozaDAOImpl.PrimljenaDozaRowCallBackHandler();
        jdbcTemplate.query(query, primljenaDozaRowCallBackHandler);

        return primljenaDozaRowCallBackHandler.getPrimljeneDoze();
    }

    @Override
    public List<PrimljenaDoza> findPrimljeneDozeByPacijent(Long pacijentId) {
        String query = "SELECT id, doza, datumIVremeDobijanjaDoze, pacijentId, vakcinaId, jeObrisan FROM primljeneDoze " +
                "WHERE pacijentId = ? AND jeObrisan = 0 " +
                "ORDER BY id";

        PrimljenaDozaDAOImpl.PrimljenaDozaRowCallBackHandler primljenaDozaRowCallBackHandler = new PrimljenaDozaDAOImpl.PrimljenaDozaRowCallBackHandler();
        jdbcTemplate.query(query, primljenaDozaRowCallBackHandler, pacijentId);

        return primljenaDozaRowCallBackHandler.getPrimljeneDoze();
    }

    @Override
    public PrimljenaDoza findPoslednjaDozaZaPacijenta(Long pacijentId) {
        String query = "SELECT id, doza, datumIVremeDobijanjaDoze, pacijentId, vakcinaId, jeObrisan FROM primljeneDoze " +
                "WHERE pacijentId = ? AND jeObrisan = 0 " +
                "ORDER BY datumIVremeDobijanjaDoze DESC " +
                "LIMIT 1";

        PrimljenaDozaDAOImpl.PrimljenaDozaRowCallBackHandler primljenaDozaRowCallBackHandler = new PrimljenaDozaDAOImpl.PrimljenaDozaRowCallBackHandler();
        jdbcTemplate.query(query, primljenaDozaRowCallBackHandler, pacijentId);

        List<PrimljenaDoza> rezultati = primljenaDozaRowCallBackHandler.getPrimljeneDoze();
        if (rezultati.isEmpty()) {
            return null;
        }

        return rezultati.get(0);
    }


    @Transactional
    @Override
    public Boolean save(PrimljenaDoza primljenaDoza) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query = "INSERT INTO primljeneDoze (doza, datumIVremeDobijanjaDoze, pacijentId, vakcinaId, jeObrisan) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1 ;
                preparedStatement.setInt(index++, primljenaDoza.getDoza());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(primljenaDoza.getDatumIVremeDobijanjaDoze()));
                preparedStatement.setLong(index++, primljenaDoza.getPacijent().getId());
                preparedStatement.setLong(index++, primljenaDoza.getVakcina().getId());
                preparedStatement.setBoolean(index++, primljenaDoza.isJeObrisan());


                return preparedStatement;
            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean update(PrimljenaDoza primljenaDoza) {
        String query = "UPDATE primljeneDoze SET doza = ?, datumIVremePrimljeneDoze = ?, pacijentId = ?, vakcinaId = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(query, primljenaDoza.getDoza(), primljenaDoza.getDatumIVremeDobijanjaDoze(), primljenaDoza.getPacijent().getId(), primljenaDoza.getVakcina().getId(), primljenaDoza.getId());
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean delete(Long id) {
        String query = "UPDATE primljeneDoze SET jeObrisan = 1 WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
