DROP SCHEMA IF EXISTS eUprava;
CREATE SCHEMA eUprava DEFAULT CHARACTER SET utf8;
USE eUprava;

CREATE TABLE eUprava.Korisnici (
	id BIGINT AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    lozinka VARCHAR(50) NOT NULL,
	ime VARCHAR(20) NOT NULL,
	prezime VARCHAR(20) NOT NULL,
    datumRodjenja date NOT NULL,
    jmbg VARCHAR(13) NOT NULL UNIQUE,
    adresa VARCHAR(255) NOT NULL,
    brojTelefona VARCHAR(15),
    datumIVremeRegistracije DATETIME NOT NULL,
    uloga ENUM('MedicinskoOsoblje', 'Pacijent', 'Administrator') NOT NULL,
    jeObrisan BOOLEAN NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE eUprava.ProizvodjaciVakcine (
	id BIGINT AUTO_INCREMENT,
	proizvodjac VARCHAR(75) NOT NULL,
    drzavaProizvodnje VARCHAR(30) NOT NULL,
    jeObrisan BOOLEAN NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE eUprava.Vakcine (
	id BIGINT AUTO_INCREMENT,
    ime VARCHAR(50) NOT NULL,
    dostupnaKolicina INT NOT NULL,
    proizvodjacVakcineId BIGINT NOT NULL,
	PRIMARY KEY(id),
    jeObrisan BOOLEAN NOT NULL,
	FOREIGN KEY(proizvodjacVakcineId) REFERENCES ProizvodjaciVakcine(id)
		ON DELETE CASCADE
);

CREATE TABLE eUprava.Vesti (
    id BIGINT auto_increment,
    naziv VARCHAR(50) NOT NULL,
    sadrzaj VARCHAR(600) NOT NULL,
    datumIVremeObjavljivanja datetime not null,
    jeObrisan BOOLEAN NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE eUprava.VestiOObolelima (
	id BIGINT AUTO_INCREMENT,
    oboleliUDanu int not null,
    testiraniUDanu int not null,
    hospitalizovani int not null,
    pacijentiNaRespiratoru int not null,
    datumIVremeObjavljivanja datetime not null,
    jeObrisan BOOLEAN NOT NULL,
	PRIMARY KEY (id)
);



CREATE TABLE eUprava.PrimljeneDoze (
	id BIGINT AUTO_INCREMENT,
    doza INT NOT NULL,
    datumIVremeDobijanjaDoze DATETIME NOT NULL,
    pacijentId BIGINT NOT NULL,
    jeObrisan BOOLEAN NOT NULL,
    FOREIGN KEY (pacijentId) REFERENCES Korisnici (id)
    ON DELETE CASCADE,
    PRIMARY KEY (id)
);



CREATE TABLE eUprava.NabavkaVakcina (
	id BIGINT AUTO_INCREMENT,
    kolicinaVakcina INT NOT NULL,
    razlogNabavke VARCHAR(255),
    datumIVremeKreiranjaZahteva DATETIME NOT NULL,
    medicinskoOsobljeId BIGINT NOT NULL,
    vakcinaId BIGINT NOT NULL,
    razlogOdbijanjaZahteva VARCHAR(255),
    status VARCHAR(255),
    jeObrisan BOOLEAN NOT NULL,
    FOREIGN KEY (medicinskoOsobljeId) REFERENCES Korisnici(id) ON DELETE CASCADE,
    FOREIGN KEY (vakcinaId) REFERENCES Vakcine(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);



CREATE TABLE eUprava.PrijaveZaVakcine(
	id BIGINT NOT NULL,
    datumIVremePrijave DATETIME NOT NULL,
    pacijentId BIGINT NOT NULL,
    vakcinaId BIGINT NOT NULL,
    jeObrisan BOOLEAN NOT NULL,
    FOREIGN KEY (pacijentId) REFERENCES Korisnici(id) ON DELETE CASCADE,
    FOREIGN KEY (vakcinaId) REFERENCES Vakcine(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);




INSERT INTO eUprava.Korisnici VALUES(1, 'boki@gmail.com', 'boki123', 'Bojan', 'Davogic', '2001-10-14', '1410001345496', 'Dositejeva 22, Novi Sad', '062456322', now(), 'Administrator', false);
INSERT INTO eUprava.Korisnici VALUES(2, 'peraperic@gmail.com', 'pera123', 'Petar', 'Peric', '2001-12-12', '1212001345496', 'Safarikova 3, Novi Sad', '062333444', now(), 'MedicinskoOsoblje', false);
INSERT INTO eUprava.Korisnici VALUES(3, 'adam@gmail.com', 'adam123', 'Adam', 'Adamovic', '2001-12-21', '2112001345496', 'Marsilijeva 21, Novi Sad', '062333555', now(), 'Pacijent', false);

INSERT INTO eUprava.ProizvodjaciVakcine VALUES(1, 'Hemofarm', 'Nemacka', false);
INSERT INTO eUprava.ProizvodjaciVakcine VALUES(2, 'Acibadem', 'Srbija', false);

INSERT INTO eUprava.Vakcine VALUES (1, 'Fajzer', 500, 1, false);
INSERT INTO eUprava.Vakcine VALUES (2, 'Sinofarm', 1000, 2, false);

INSERT INTO eUprava.Vesti VALUES (1, 'Obavestenje', 'Mole se svi gradjani da se vakcinisu u sto kracem roku', now(), false);

INSERT INTO eUprava.VestiOObolelima VALUES (1, 12, 60, 20, 2, now(), false);

INSERT INTO eUprava.PrimljeneDoze VALUES(1, 1, now(), 3, false);

INSERT INTO eUprava.NabavkaVakcina VALUES (1, 500, 'Nestasica', now(), 2, 1, 'Nedovoljan broj podataka', 'Odbijen', false);

INSERT INTO eUprava.PrijaveZaVakcine VALUES (1, now(), 1, 1, false);

CREATE FUNCTION getUkupnoOboleli(vestId INT) RETURNS INT
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE ukupnoOboleli INT;

    SELECT SUM(oboleliUDanu)
    INTO ukupnoOboleli
    FROM VestiOObolelima
    WHERE DATE(datumIVremeObjavljivanja) <= (SELECT DATE(datumIVremeObjavljivanja) FROM eUprava.VestiOObolelima WHERE id = vestId);

    RETURN ukupnoOboleli;
END;

