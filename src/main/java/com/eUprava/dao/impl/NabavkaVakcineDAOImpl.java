package com.eUprava.dao.impl;

import com.eUprava.dao.KorisnikDAO;
import com.eUprava.dao.NabavkaVakcineDAO;
import com.eUprava.dao.VakcinaDAO;
import com.eUprava.model.Korisnik;
import com.eUprava.model.NabavkaVakcine;
import com.eUprava.model.PrijavaZaVakcinu;
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
public class NabavkaVakcineDAOImpl implements NabavkaVakcineDAO {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KorisnikDAO korisnikDAO;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private VakcinaDAO vakcinaDAO;

    private class NabavkaVakcineRowCallBackHandler implements RowCallbackHandler {
        private Map<Long, NabavkaVakcine> nabavkeVakcine = new LinkedHashMap<>();
        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            int kolicinaVakcina = rs.getInt(index++);
            String razlogNabavke = rs.getString(index++);
            LocalDateTime datumIVremeKreiranjaZahteva = rs.getTimestamp(index++).toLocalDateTime();
            Long medicinskoOsobljeId = rs.getLong(index++);
            Korisnik medicinskoOsoblje = korisnikDAO.findKorisnik(medicinskoOsobljeId);
            Long vakcinaId = rs.getLong(index++);
            Vakcina vakcina = vakcinaDAO.findVakcina(vakcinaId);
            String razlogOdbijanjaZahteva = rs.getString(index++);
            String status = rs.getString(index++);
            boolean jeObrisan = Boolean.valueOf(rs.getString(index++));

            NabavkaVakcine nabavkaVakcine = nabavkeVakcine.get(id);
            if(nabavkaVakcine == null) {
                nabavkaVakcine = new NabavkaVakcine(id, kolicinaVakcina, razlogNabavke, datumIVremeKreiranjaZahteva, medicinskoOsoblje, vakcina, razlogOdbijanjaZahteva, status, jeObrisan);
                nabavkeVakcine.put(nabavkaVakcine.getId(), nabavkaVakcine);
            }
        }
        public List<NabavkaVakcine> getNabavkeVakcine() {
            return new ArrayList<>(nabavkeVakcine.values());
        }
    }

    @Override
    public NabavkaVakcine findNabavkaVakcine(Long id) {
        String query = "SELECT id, kolicinaVakcina, razlogNabavke, datumIVremeKreiranjaZahteva, medicinskoOsobljeId, vakcinaId, razlogOdbijanjaZahteva, status, jeObrisan FROM nabavkaVakcina " +
                        "WHERE id = ? AND jeObrisan = 0 " +
                        "ORDER BY id";

        NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler nabavkaVakcineRowCallBackHandler = new NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler();
        jdbcTemplate.query(query, nabavkaVakcineRowCallBackHandler, id);

        return nabavkaVakcineRowCallBackHandler.getNabavkeVakcine().get(0);
    }

    @Override
    public List<NabavkaVakcine> findSveNabavkeVakcine() {
        String query = "SELECT id, kolicinaVakcina, razlogNabavke, datumIVremeKreiranjaZahteva, medicinskoOsobljeId, vakcinaId, razlogOdbijanjaZahteva, status, jeObrisan FROM nabavkaVakcina " +
                        "WHERE jeObrisan = 0 " +
                        "ORDER BY id";

        NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler nabavkaVakcineRowCallBackHandler = new NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler();
        jdbcTemplate.query(query, nabavkaVakcineRowCallBackHandler);

        return nabavkaVakcineRowCallBackHandler.getNabavkeVakcine();
    }

    @Override
    public List<NabavkaVakcine> findSveNabavkeVakcineNaCekanju() {
        String query = "SELECT id, kolicinaVakcina, razlogNabavke, datumIVremeKreiranjaZahteva, medicinskoOsobljeId, vakcinaId, razlogOdbijanjaZahteva, status, jeObrisan FROM nabavkaVakcina " +
                "WHERE status = 'Na čekanju' AND jeObrisan = 0 " +
                "ORDER BY id";

        NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler nabavkaVakcineRowCallBackHandler = new NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler();
        jdbcTemplate.query(query, nabavkaVakcineRowCallBackHandler);

        return nabavkaVakcineRowCallBackHandler.getNabavkeVakcine();
    }

    @Override
    public List<NabavkaVakcine> findSveOdobreneNabavkeVakcine() {
        String query = "SELECT id, kolicinaVakcina, razlogNabavke, datumIVremeKreiranjaZahteva, medicinskoOsobljeId, vakcinaId, razlogOdbijanjaZahteva, status, jeObrisan FROM nabavkaVakcina " +
                "WHERE status = 'Odobren' AND jeObrisan = 0 " +
                "ORDER BY id";

        NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler nabavkaVakcineRowCallBackHandler = new NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler();
        jdbcTemplate.query(query, nabavkaVakcineRowCallBackHandler);

        return nabavkaVakcineRowCallBackHandler.getNabavkeVakcine();
    }

    @Override
    public List<NabavkaVakcine> findSveOdbijeneNabavkeVakcine() {
        String query = "SELECT id, kolicinaVakcina, razlogNabavke, datumIVremeKreiranjaZahteva, medicinskoOsobljeId, vakcinaId, razlogOdbijanjaZahteva, status, jeObrisan FROM nabavkaVakcina " +
                "WHERE status = 'Odbijen' AND jeObrisan = 0 " +
                "ORDER BY id";

        NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler nabavkaVakcineRowCallBackHandler = new NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler();
        jdbcTemplate.query(query, nabavkaVakcineRowCallBackHandler);

        return nabavkaVakcineRowCallBackHandler.getNabavkeVakcine();
    }

    @Override
    public List<NabavkaVakcine> findSveNabavkeVakcineNaReviziji() {
        String query = "SELECT id, kolicinaVakcina, razlogNabavke, datumIVremeKreiranjaZahteva, medicinskoOsobljeId, vakcinaId, razlogOdbijanjaZahteva, status, jeObrisan FROM nabavkaVakcina " +
                "WHERE status = 'Na reviziji' AND jeObrisan = 0 " +
                "ORDER BY id";

        NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler nabavkaVakcineRowCallBackHandler = new NabavkaVakcineDAOImpl.NabavkaVakcineRowCallBackHandler();
        jdbcTemplate.query(query, nabavkaVakcineRowCallBackHandler);

        return nabavkaVakcineRowCallBackHandler.getNabavkeVakcine();
    }

    @Transactional
    @Override
    public Boolean save(NabavkaVakcine nabavkaVakcine) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query = "INSERT INTO nabavkaVakcina (kolicinaVakcina, razlogNabavke, datumIVremeKreiranjaZahteva, medicinskoOsobljeId, vakcinaId, razlogOdbijanjaZahteva, status, jeObrisan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1 ;
                preparedStatement.setInt(index++, nabavkaVakcine.getKolicinaVakcina());
                preparedStatement.setString(index++, nabavkaVakcine.getRazlogNabavke());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(nabavkaVakcine.getDatumIVremeKreiranjaZahteva()));
                preparedStatement.setLong(index++, nabavkaVakcine.getMedicinskoOsoblje().getId());
                preparedStatement.setLong(index++, nabavkaVakcine.getVakcina().getId());
                preparedStatement.setString(index++, nabavkaVakcine.getRazlogOdbijanjaZahteva());
                preparedStatement.setString(index++, nabavkaVakcine.getStatus());
                preparedStatement.setBoolean(index++, nabavkaVakcine.isJeObrisan());


                return preparedStatement;
            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean update(NabavkaVakcine nabavkaVakcine) {
        String query = "UPDATE nabavkaVakcina SET kolicinaVakcina = ?, razlogNabavke = ?, datumIVremeKreiranjaZahteva = ?, medicinskoOsobljeId = ?, vakcinaId = ?, razlogOdbijanjaZahteva = ?, status = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(query, nabavkaVakcine.getKolicinaVakcina(), nabavkaVakcine.getRazlogNabavke(), nabavkaVakcine.getDatumIVremeKreiranjaZahteva(), nabavkaVakcine.getMedicinskoOsoblje().getId(), nabavkaVakcine.getVakcina().getId(), nabavkaVakcine.getRazlogOdbijanjaZahteva(), nabavkaVakcine.getStatus(), nabavkaVakcine.getId());
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean delete(Long id) {
        String query = "UPDATE nabavkaVakcina SET jeObrisan = 1 WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
