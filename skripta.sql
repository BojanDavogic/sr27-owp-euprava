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
    brojTelefona INT,
    datumIVremeRegistracije DATETIME NOT NULL,
    uloga ENUM("MedicinskoOsoblje", "Pacijent", "Administrator") NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE eUprava.ProizvodjaciVakcine (
	id BIGINT AUTO_INCREMENT,
	proizvodjac VARCHAR(75) NOT NULL,
    drzavaProizvodnje VARCHAR(30) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE eUprava.Vakcine (
	id BIGINT AUTO_INCREMENT,
    ime VARCHAR(50) NOT NULL,
    dostupnaKolicina INT NOT NULL,
    proizvodjacVakcineId BIGINT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(proizvodjacVakcineId) REFERENCES ProizvodjaciVakcine(id)
		ON DELETE CASCADE
);

CREATE TABLE eUprava.Vesti (
    id BIGINT auto_increment,
    naziv VARCHAR(50) NOT NULL,
    sadrzaj VARCHAR(600) NOT NULL,
    datumIVremeObjavljivanja datetime not null,
    PRIMARY KEY(id)
);

CREATE TABLE eUprava.VestiOObolelima (
	id BIGINT AUTO_INCREMENT,
    oboleliUDanu int not null,
    testiraniUDanu int not null,
    ukupnoOboleli int not null,
    hospitalizovani int not null,
    pacijentiNaRespiratoru int not null,
    datumIVremeObjavljivanja datetime not null,
	PRIMARY KEY (id)
);



CREATE TABLE eUprava.PrimljeneDoze (
	id BIGINT AUTO_INCREMENT,
    doza INT NOT NULL,
    datumIVremeDobijanjaDoze DATETIME NOT NULL,
    pacijentId BIGINT NOT NULL,
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
    Status VARCHAR(255),
    FOREIGN KEY (medicinskoOsobljeId) REFERENCES Korisnici(id) ON DELETE CASCADE,
    FOREIGN KEY (vakcinaId) REFERENCES Vakcine(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);



CREATE TABLE eUprava.PrijaveZaVakcine(
	id BIGINT NOT NULL,
    datumIVremePrijave DATETIME NOT NULL,
    pacijentId BIGINT NOT NULL,
    vakcinaId BIGINT NOT NULL,
    FOREIGN KEY (pacijentId) REFERENCES Korisnici(id) ON DELETE CASCADE,
    FOREIGN KEY (vakcinaId) REFERENCES Vakcine(id) ON DELETE CASCADE,
	PRIMARY KEY (id)
);




INSERT INTO Korisnici VALUES(1, 'boki@gmail.com', "boki123", "Bojan", "Davogic", '2001-10-14', "1410001345496", "Dositejeva 22, Novi Sad", 062456322, now(), "Administrator");
INSERT INTO Korisnici VALUES(2, 'peraperic@gmail.com', "pera123", "Petar", "Peric", '2001-12-12', "1212001345496", "Safarikova 3, Novi Sad", 062333444, now(), "MedicinskoOsoblje");
INSERT INTO Korisnici VALUES(3, 'adam@gmail.com', "adam123", "Adam", "Adamovic", '2001-12-21', "2112001345496", "Marsilijeva 21, Novi Sad", 062333555, now(), "Pacijent");

INSERT INTO ProizvodjaciVakcine VALUES(1, "Hemofarm", "Nemacka");
INSERT INTO ProizvodjaciVakcine VALUES(2, "Acimadem", "Srbija");

INSERT INTO Vakcine VALUES (1, "Pulteni", 500, 1);
INSERT INTO Vakcine VALUES (2, "Kokerens", 1000, 2);

INSERT INTO Vesti VALUES (1, "Berentiko", "Ovaj tekst je sadrzaj vesti", now());

INSERT INTO VestiOObolelima VALUES (1, 12, 250, 60, 20, 2, now());

INSERT INTO PrimljeneDoze VALUES(1, 1, now(), 3);

INSERT INTO NabavkaVakcina VALUES (1, 500, "Nestasica", now(), 2, 1, "Nedovoljan broj podataka", "Odbijen");

INSERT INTO PrijaveZaVakcine VALUES (1, now(), 1, 1);