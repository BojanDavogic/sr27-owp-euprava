package com.eUprava.controller;

import com.eUprava.model.Vest;
import com.eUprava.service.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VestController {
    @Autowired
    private VestService vestService;

//    @GetMapping("/")
//    public String showVesti(Model model) {
//        List<Vest> sveVesti = vestService.findSveVesti();
//        List<Vest> sortiraneVesti = sveVesti.stream()
//                .sorted(Comparator.comparing(Vest::getDatumIVremeObjavljivanja).reversed())
//                .collect(Collectors.toList());
//
//        model.addAttribute("sveVesti", sortiraneVesti);
//        return "index";
//    }

    @GetMapping("/unos-vesti")
    public String prikaziFormu(Model model) {
        model.addAttribute("novaVest", new Vest()); // NovaVest je klasa koja predstavlja model za unos vesti
        return "unos-vesti";
    }

    @PostMapping("/dodavanjeVesti")
    public String postUnosVesti(@RequestParam String naziv, @RequestParam String sadrzaj) {
        Vest novaVest = new Vest();
        novaVest.setNaziv(naziv);
        novaVest.setSadrzaj(sadrzaj);
        // Postavljanje datuma i vremena objavljivanja na trenutni trenutak
        novaVest.setDatumIVremeObjavljivanja(LocalDateTime.now());

        // Čuvanje nove vesti u bazi podataka
        vestService.save(novaVest);

        // Redirekcija na stranicu sa vestima
        return "redirect:/korisnici/pocetna";
    }

    @GetMapping("/izmeni-vest/{vestId}")
    public String prikaziFormuZaIzmenu(@PathVariable Long vestId, Model model) {
        // Dohvatite vest sa datim ID-ijem iz baze podataka
        Vest vest = vestService.findVest(vestId);

        // Dodajte vest u model da bi je mogli koristiti u Thymeleaf templateu
        model.addAttribute("vest", vest);

        return "izmenaVesti"; // Naziv Thymeleaf templatea
    }

    @PostMapping("/izmeni-vest/{vestId}")
    public String sacuvajIzmene(@RequestParam Long vestId, @RequestParam String nazivIzmena, @RequestParam String sadrzajIzmena) {
        // Dohvatite postojeću vest iz baze podataka
        Vest vest = vestService.findVest(vestId);

        // Ažurirajte vrednosti vesti sa podacima iz izmenjenaVest
        vest.setNaziv(nazivIzmena);
        vest.setSadrzaj(sadrzajIzmena);

        // Sačuvajte ažuriranu vest u bazi podataka
        vestService.update(vest);

        return "redirect:/korisnici/pocetna"; // Redirect na listu vesti ili drugu željenu stranicu
    }
}
