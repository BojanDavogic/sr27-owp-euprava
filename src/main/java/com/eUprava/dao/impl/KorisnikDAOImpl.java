package com.eUprava.dao.impl;

import com.eUprava.dao.KorisnikDAO;
import com.eUprava.model.Korisnik;
import com.eUprava.model.Uloga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.awt.font.NumericShaper;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class KorisnikDAOImpl implements KorisnikDAO {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private class KorisnikRowCallBackHandler implements RowCallbackHandler{
        private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;
            Long id = rs.getLong(index++);
            String email = rs.getString(index++);
            String lozinka = rs.getString(index++);
            String ime = rs.getString(index++);
            String prezime = rs.getString(index++);
            Date datumrodjenja = rs.getDate(index++);
            String jmbg = rs.getString(index++);
            String adresa = rs.getString(index++);
            int brojTelefona = rs.getInt(index++);
            LocalDateTime datumIVremeRegistracije = rs.getTimestamp(index++).toLocalDateTime();
            Uloga uloga = Uloga.valueOf(rs.getString(index++));

            Korisnik korisnik = korisnici.get(id);

            if (korisnik == null){
                korisnik = new Korisnik(id, email, lozinka, ime, prezime, datumrodjenja, jmbg, adresa, brojTelefona, datumIVremeRegistracije, uloga);
                korisnici.put(korisnik.getId(), korisnik);
            }
        }
        public List<Korisnik> getKorisnici() {
            return new ArrayList<>(korisnici.values());
        }
    }
    @Override
    public Korisnik findKorisnik(Long id)
    {
        String query =  " SELECT * FROM korisnici " +
                        " WHERE id = ? " +
                        "ORDER BY id";
        KorisnikRowCallBackHandler korisnikRowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(query, korisnikRowCallBackHandler, id);
        return korisnikRowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findKorisnikByEmail(String email) {

        String query =  " SELECT * FROM korisnici " +
                        " WHERE email = ? " +
                        "ORDER BY id";
        KorisnikRowCallBackHandler korisnikRowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(query, korisnikRowCallBackHandler, email);
        return korisnikRowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findKorisnikByEmailAndPassword(String email, String lozinka) {

        String query =  " SELECT * FROM korisnici " +
                        " WHERE email = ?" + " AND " + "lozinka = ?" +
                        "ORDER BY id";
        KorisnikRowCallBackHandler korisnikRowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(query, korisnikRowCallBackHandler, email, lozinka);
        if(korisnikRowCallBackHandler.getKorisnici().size() == 0){
            return null;
        }
        return korisnikRowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public List<Korisnik> findSviKorisnici() {

        String query =  " SELECT * FROM korisnici " +
                        "ORDER BY id";
        KorisnikRowCallBackHandler korisnikRowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(query, korisnikRowCallBackHandler);
        return korisnikRowCallBackHandler.getKorisnici();
    }
    @Transactional
    @Override
    public Boolean save(Korisnik korisnik) {

        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String query =  "INSERT INTO korisnici (email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona, datumIVremeRegistracije, uloga" +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, korisnik.getEmail());
                preparedStatement.setString(index++, korisnik.getLozinka());
                preparedStatement.setString(index++, korisnik.getIme());
                preparedStatement.setString(index++, korisnik.getPrezime());
                preparedStatement.setDate(index++, (Date) korisnik.getDatumRodjenja());
                preparedStatement.setString(index++, korisnik.getJmbg());
                preparedStatement.setInt(index++, korisnik.getBrojTelefona());
                preparedStatement.setString(index++, korisnik.getEmail());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(korisnik.getDatumIVremeRegistracije()));
                preparedStatement.setString(index++, korisnik.getUloga().name());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int uspeh = jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return uspeh > 0;
    }
    @Transactional
    @Override
    public Boolean update(Korisnik korisnik) {

        String query = " UPDATE korisnici SET ime = ?, prezime = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(query, korisnik.getIme(), korisnik.getPrezime(), korisnik.getId());
        return uspeh > 0;
    }

    @Transactional
    @Override
    public Boolean delete(Long id) {
        String query = "DELETE FROM korisnici WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
