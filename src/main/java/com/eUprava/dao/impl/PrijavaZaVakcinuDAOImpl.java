package com.eUprava.dao.impl;

import com.eUprava.dao.KorisnikDAO;
import com.eUprava.dao.PrijavaZaVakcinuDAO;
import com.eUprava.dao.VakcinaDAO;
import com.eUprava.model.Korisnik;
import com.eUprava.model.PrijavaZaVakcinu;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Repository
public class PrijavaZaVakcinuDAOImpl implements PrijavaZaVakcinuDAO {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KorisnikDAO korisnikDAO;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private VakcinaDAO vakcinaDAO;

    private class PrijavaZaVakcinuRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, PrijavaZaVakcinu> prijaveZaVakcine = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            LocalDateTime datumIVremePrijave = rs.getTimestamp(index++).toLocalDateTime();
            Long pacijentId = rs.getLong(index++);
            Korisnik pacijent = korisnikDAO.findKorisnik(pacijentId);
            Long vakcinaId = rs.getLong(index++);
            Vakcina vakcina = vakcinaDAO.findVakcina(vakcinaId);
            boolean jeObrisan = Boolean.valueOf(rs.getString(index++));

            PrijavaZaVakcinu prijavaZaVakcinu = prijaveZaVakcine.get(id);
            if(prijavaZaVakcinu == null) {
                prijavaZaVakcinu = new PrijavaZaVakcinu(id, datumIVremePrijave, pacijent, vakcina, jeObrisan);
                prijaveZaVakcine.put(prijavaZaVakcinu.getId(), prijavaZaVakcinu);
            }
        }
        public List<PrijavaZaVakcinu> getPrijaveZaVakcinu() {
            return new ArrayList<>(prijaveZaVakcine.values());
        }
    }

    @Override
    public PrijavaZaVakcinu findPrijavaZaVakcinu(Long id) {
        String query = "SELECT id, datumIVremePrijave, pacijentId, vakcinaId, jeObrisan FROM prijaveZaVakcine " +
                "WHERE id = ? AND jeObrisan = 0 " +
                "ORDER BY id";

        PrijavaZaVakcinuDAOImpl.PrijavaZaVakcinuRowCallBackHandler prijavaZaVakcinuRowCallBackHandler = new PrijavaZaVakcinuDAOImpl.PrijavaZaVakcinuRowCallBackHandler();
        jdbcTemplate.query(query, prijavaZaVakcinuRowCallBackHandler, id);

        return prijavaZaVakcinuRowCallBackHandler.getPrijaveZaVakcinu().get(0);
    }

    @Override
    public boolean existsPrijavaZaPacijentaIVakcinu(Long pacijentId, Long vakcinaId) {
        String query = "SELECT COUNT(*) FROM prijaveZaVakcine WHERE pacijentId = ? AND vakcinaId = ? AND jeObrisan = 0 ";
        int count = jdbcTemplate.queryForObject(query, Integer.class, pacijentId, vakcinaId);
        return count == 1;
    }

    @Override
    public List<PrijavaZaVakcinu> findSvePrijaveZaVakcinu() {
        String query = "SELECT p.id, p.datumIVremePrijave, p.pacijentId, p.vakcinaId, k.ime, k.prezime, k.jmbg, v.ime, p.jeObrisan FROM prijaveZaVakcine p, korisnici k, vakcine v " +
                "WHERE p.pacijentId = k.id AND p.vakcinaId = v.id AND p.jeObrisan = 0 " +
                "ORDER BY p.id";

        PrijavaZaVakcinuDAOImpl.PrijavaZaVakcinuRowCallBackHandler prijavaZaVakcinuRowCallBackHandler = new PrijavaZaVakcinuDAOImpl.PrijavaZaVakcinuRowCallBackHandler();
        jdbcTemplate.query(query, prijavaZaVakcinuRowCallBackHandler);

        return prijavaZaVakcinuRowCallBackHandler.getPrijaveZaVakcinu();
    }

    @Override
    public List<PrijavaZaVakcinu> findPrijavaByImePrezimeAndJmbg(String ime, String prezime, String jmbg) {
        String query = "SELECT p.id, p.datumIVremePrijave, p.pacijentId, p.vakcinaId, k.ime, k.prezime, k.jmbg, v.ime, p.jeObrisan FROM prijaveZaVakcine p, korisnici k, vakcine v " +
                "WHERE p.pacijentId = k.id AND p.vakcinaId = v.id AND k.ime = ? AND k.prezime = ? AND k.jmbg = ? AND p.jeObrisan = 0 " +
                "ORDER BY p.id";

        PrijavaZaVakcinuDAOImpl.PrijavaZaVakcinuRowCallBackHandler prijavaZaVakcinuRowCallBackHandler = new PrijavaZaVakcinuDAOImpl.PrijavaZaVakcinuRowCallBackHandler();
        jdbcTemplate.query(query, prijavaZaVakcinuRowCallBackHandler, ime, prezime, jmbg);

        return prijavaZaVakcinuRowCallBackHandler.getPrijaveZaVakcinu();
    }
    @Transactional
    @Override
    public Boolean save(PrijavaZaVakcinu prijavaZaVakcinu) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query = "INSERT INTO prijaveZaVakcine (datumIVremePrijave, pacijentId, vakcinaId, jeObrisan) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1 ;
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(prijavaZaVakcinu.getDatumIVremePrijave()));
                preparedStatement.setLong(index++, prijavaZaVakcinu.getPacijent().getId());
                preparedStatement.setLong(index++, prijavaZaVakcinu.getVakcina().getId());
                preparedStatement.setBoolean(index++, prijavaZaVakcinu.isJeObrisan());


                return preparedStatement;
            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean update(PrijavaZaVakcinu prijavaZaVakcinu) {
        String query = "UPDATE prijaveZaVakcine SET datumIVremePrijave = ?, pacijentId = ?, vakcinaId = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(query, prijavaZaVakcinu.getDatumIVremePrijave(), prijavaZaVakcinu.getPacijent().getId(), prijavaZaVakcinu.getVakcina().getId(), prijavaZaVakcinu.getId());
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean delete(Long id) {
        String query = "UPDATE prijaveZaVakcine SET jeObrisan = 1 WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
