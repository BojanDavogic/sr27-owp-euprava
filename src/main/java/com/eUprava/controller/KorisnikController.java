package com.eUprava.controller;

import com.eUprava.model.Korisnik;
import com.eUprava.model.Uloga;
import com.eUprava.service.KorisnikService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDateTime;

@Controller
@RequestMapping("korisnici")
public class KorisnikController {
    public static final String KORISNIK_KEY = "korisnik";

    private String url;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void init() {
        this.url = servletContext.getContextPath() + "/";
    }

    @GetMapping(value = "/prijava")
    public String login() {
        return "prijava.html";
    }

//    @GetMapping(path = "/pocetna")
//    public String prikaziPocetnuStranicu() {
//        return "pocetna.html";
//    }


    @PostMapping(value = "/prijava")
    public String postLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String lozinka, HttpSession httpSession, RedirectAttributes redirectAttributes) throws IOException {
        String greska = null;
        Korisnik korisnik = korisnikService.findKorisnikByEmailAndPassword(email, lozinka);

        if (korisnik == null) {
            greska = "Neispravan email ili lozinka";
            System.out.println(greska);
            redirectAttributes.addFlashAttribute("greska", greska);
            return "redirect:/korisnici/prijava";
        }
        System.out.println("Korisnik postavljen u sesiju: " + korisnik.getUloga());
        httpSession.setAttribute(KORISNIK_KEY, korisnik);
        return "redirect:/korisnici/pocetna";
    }

    @GetMapping("/odjava")
    public String odjava(HttpSession httpSession) {
        httpSession.invalidate();

        return "redirect:/";
    }

    @GetMapping(value = "/registracija")
    public String showRegistrationForm() {
        return "registracija.html";
    }

    @PostMapping(value = "/registracija")
    public String registerUser(@RequestParam String email, @RequestParam String lozinka, @RequestParam String ime, @RequestParam String prezime, @RequestParam Date datumRodjenja, @RequestParam String jmbg, @RequestParam String adresa, @RequestParam int brojTelefona, HttpSession httpSession, RedirectAttributes redirectAttributes) throws IOException {
        Korisnik existingKorisnik = korisnikService.findKorisnikByEmail(email);
        if (existingKorisnik != null) {
            String greska = "Korisnik sa unetim email-om već postoji";
            redirectAttributes.addFlashAttribute("greska", greska);
            return "redirect:/korisnici/registracija";
        }

        // Kreiranje novog korisnika
        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setEmail(email);
        noviKorisnik.setLozinka(lozinka);
        noviKorisnik.setIme(ime);
        noviKorisnik.setPrezime(prezime);
        noviKorisnik.setDatumRodjenja(datumRodjenja);
        noviKorisnik.setJmbg(jmbg);
        noviKorisnik.setAdresa(adresa);
        noviKorisnik.setBrojTelefona(brojTelefona);
        noviKorisnik.setDatumIVremeRegistracije(LocalDateTime.now());
        noviKorisnik.setUloga(Uloga.Pacijent);

        // Čuvanje novog korisnika u bazi podataka
        korisnikService.save(noviKorisnik);

        // Postavljanje korisnika u sesiju (opciono, u zavisnosti od vaših potreba)
        httpSession.setAttribute(KORISNIK_KEY, noviKorisnik);

        // Redirekcija na željenu stranicu nakon uspešne registracije (npr. na stranicu za prijavu)
        return "redirect:/korisnici/prijava";
    }

    @GetMapping(value = "/profil")
    public String profilKorisnika(Model model, Principal principal){
        String email = principal.getName();
        Korisnik korisnik = korisnikService.findKorisnikByEmail(email);
        model.addAttribute("korisnik", korisnik);
        return "user";
    }
}
