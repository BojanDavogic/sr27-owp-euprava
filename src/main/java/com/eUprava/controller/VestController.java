package com.eUprava.controller;

import com.eUprava.model.Vest;
import com.eUprava.service.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class VestController {
    @Autowired
    private VestService vestService;

    @GetMapping("/unos-vesti")
    public String prikaziFormu(Model model) {
        model.addAttribute("novaVest", new Vest());
        return "unos-vesti";
    }

    @PostMapping("/dodavanjeVesti")
    public String postUnosVesti(@RequestParam String naziv, @RequestParam String sadrzaj) {
        Vest novaVest = new Vest();
        novaVest.setNaziv(naziv);
        novaVest.setSadrzaj(sadrzaj);
        novaVest.setDatumIVremeObjavljivanja(LocalDateTime.now());

        vestService.save(novaVest);

        return "redirect:/korisnici/pocetna";
    }

    @GetMapping("/izmeni-vest/{vestId}")
    public String prikaziFormuZaIzmenu(@PathVariable Long vestId, Model model) {
        Vest vest = vestService.findVest(vestId);
        model.addAttribute("vest", vest);

        return "izmenaVesti";
    }

    @PostMapping("/izmeni-vest/{vestId}")
    public String sacuvajIzmene(@RequestParam Long vestId, @RequestParam String nazivIzmena, @RequestParam String sadrzajIzmena) {
        Vest vest = vestService.findVest(vestId);

        vest.setNaziv(nazivIzmena);
        vest.setSadrzaj(sadrzajIzmena);

        vestService.update(vest);

        return "redirect:/korisnici/pocetna";
    }

    @PostMapping("/obrisi-vest/{vestId}")
    public String obrisiVest(@PathVariable Long vestId) {
        vestService.delete(vestId);
        return "redirect:/korisnici/pocetna";
    }

}
