package com.eUprava.dao.impl;

import com.eUprava.dao.KorisnikDAO;
import com.eUprava.model.Korisnik;
import com.eUprava.model.Uloga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.font.NumericShaper;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
@Repository
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
            String brojTelefona = rs.getString(index++);
            LocalDateTime datumIVremeRegistracije = rs.getTimestamp(index++).toLocalDateTime();
            Uloga uloga = Uloga.valueOf(rs.getString(index++));
            boolean jeObrisan = Boolean.valueOf(rs.getString(index++));

            Korisnik korisnik = korisnici.get(id);

            if (korisnik == null){
                korisnik = new Korisnik(id, email, lozinka, ime, prezime, datumrodjenja, jmbg, adresa, brojTelefona, datumIVremeRegistracije, uloga, jeObrisan);
                korisnici.put(korisnik.getId(), korisnik);
            } else {
                // Ažuriranje postojećeg korisnika sa dobijenim vrednostima
                korisnik.setEmail(email);
                korisnik.setLozinka(lozinka);
                korisnik.setIme(ime);
                korisnik.setPrezime(prezime);
                korisnik.setDatumRodjenja(datumrodjenja);
                korisnik.setJmbg(jmbg);
                korisnik.setAdresa(adresa);
                korisnik.setBrojTelefona(brojTelefona);
                korisnik.setDatumIVremeRegistracije(datumIVremeRegistracije);
                korisnik.setUloga(uloga);
                korisnik.setJeObrisan(jeObrisan);
            }
        }

        public List<Korisnik> getKorisnici() {
            return new ArrayList<>(korisnici.values());
        }
    }
    @Override
    public Korisnik findKorisnik(Long id)
    {
        String query =  "SELECT * FROM korisnici " +
                        "WHERE id = ? AND jeObrisan = 0 " +
                        "ORDER BY id";
        KorisnikRowCallBackHandler korisnikRowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(query, korisnikRowCallBackHandler, id);
        return korisnikRowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findKorisnikByEmail(String email) {

        String query =  " SELECT * FROM korisnici " +
                        " WHERE email = ? AND jeObrisan = 0 " +
                        "ORDER BY id";
        KorisnikRowCallBackHandler korisnikRowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(query, korisnikRowCallBackHandler, email);
        if(korisnikRowCallBackHandler.getKorisnici().size() == 0){
            return null;
        }
        return korisnikRowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findKorisnikByEmailAndPassword(String email, String lozinka) {

        String query =  "SELECT * FROM korisnici " +
                        "WHERE email = ? " + "AND " + "lozinka = ? AND jeObrisan = 0 " +
                        "ORDER BY id ";
        KorisnikRowCallBackHandler korisnikRowCallBackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(query, korisnikRowCallBackHandler, email, lozinka);
        if(korisnikRowCallBackHandler.getKorisnici().size() == 0){
            return null;
        }
        return korisnikRowCallBackHandler.getKorisnici().get(0);
    }

    @Override
    public List<Korisnik> findSviKorisnici() {

        String query =  "SELECT * FROM korisnici " +
                        "WHERE jeObrisan = 0 " +
                        "ORDER BY id ";
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
                String query =  "INSERT INTO korisnici (email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona, datumIVremeRegistracije, uloga, jeObrisan)" +
                                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

                PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, korisnik.getEmail());
                preparedStatement.setString(index++, korisnik.getLozinka());
                preparedStatement.setString(index++, korisnik.getIme());
                preparedStatement.setString(index++, korisnik.getPrezime());
                preparedStatement.setDate(index++, (Date) korisnik.getDatumRodjenja());
                preparedStatement.setString(index++, korisnik.getJmbg());
                preparedStatement.setString(index++, korisnik.getAdresa());
                preparedStatement.setString(index++, korisnik.getBrojTelefona());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(korisnik.getDatumIVremeRegistracije()));
                preparedStatement.setString(index++, korisnik.getUloga().name());
                preparedStatement.setBoolean(index++, korisnik.isJeObrisan());

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

        String query = " UPDATE korisnici SET email = ?, lozinka = ?, ime = ?, prezime = ?, datumRodjenja = ?, jmbg = ?, adresa = ?, brojTelefona = ? WHERE id = ?";
        int uspeh = jdbcTemplate.update(query, korisnik.getEmail(), korisnik.getLozinka(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getJmbg(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getId());
        return uspeh > 0;
    }

    @Transactional
    @Override
    public Boolean delete(Long id) {
        String query = " UPDATE korisnici SET jeObrisan = 1 WHERE id = ?";
        int obrisan = jdbcTemplate.update(query, id);
        return obrisan > 0;
    }
}
