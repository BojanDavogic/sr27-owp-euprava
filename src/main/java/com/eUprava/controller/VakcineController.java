package com.eUprava.controller;

import com.eUprava.model.Vakcina;
import com.eUprava.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VakcineController {
    @Autowired
    private VakcinaService vakcinaService;

    @GetMapping(value = "/vakcine")
    public String vakcineStranica(@RequestParam(name = "kriterijum", required = false) String kriterijum,
                                  @RequestParam(name = "vrednost", required = false) String vrednost,
                                  @RequestParam(name = "minKolicina", required = false) Integer minKolicina,
                                  @RequestParam(name = "maxKolicina", required = false) Integer maxKolicina,
                                  @RequestParam(name = "sortiranje", required = false) String sortiranje,
                                  Model model) {
        List<Vakcina> vakcine = new ArrayList<>();

        if (kriterijum != null && vrednost != null) {
            switch (kriterijum) {
                case "ime":
                    vakcine = vakcinaService.findVakcinaByNaziv(vrednost);
                    System.out.println("Prikazane su vakcine po nazivu");
                    System.out.println(vakcine);
                    break;
                case "proizvodjac":
                    vakcine = vakcinaService.findVakcinaByNazivProizvodjaca(vrednost);
                    System.out.println("Prikazane su vakcine po nazivu proizvodjaca");
                    System.out.println(vakcine);
                    break;
                case "drzava":
                    vakcine = vakcinaService.findVakcinaByDrzava(vrednost);
                    System.out.println("Prikazane su vakcine po drzavi");
                    System.out.println(vakcine);
                    break;
                case "kolicina":
                    if (minKolicina != null && maxKolicina != null) {
                        vakcine = vakcinaService.findVakcinaByKolicina(minKolicina, maxKolicina);
                        System.out.println("Prikazane su sve vakcine po kolicini");
                        System.out.println(vakcine);
                    }
                    break;
            }
        } else {
            vakcine = vakcinaService.findSveVakcine();
            System.out.println("Prikazane su sve vakcine");
            System.out.println(vakcine);
        }

        System.out.println("Sortiranje::: " + sortiranje);

        // Sortiranje vakcina
        if (sortiranje != null) {
            vakcine = vakcinaService.sortVakcine(vakcine, sortiranje);
            System.out.println("Sortiranje vakcina");
            System.out.println(vakcine);
        }

        model.addAttribute("vakcine", vakcine);
        return "vakcine.html";
    }



    @GetMapping(value = "/vakcine/{id}")
    public String detaljiOVakcini(@PathVariable Long id, Model model) {
        Vakcina vakcina = vakcinaService.findVakcina(id);
        if (vakcina == null) {
            // Ovde možete postaviti odgovarajući odgovor za nepostojeću vakcinu
            return "redirect:/vakcine"; // Na primer, vraćanje na stranicu sa vakcinama
        }
        model.addAttribute("vakcina", vakcina);
        return "detalji_vakcine.html"; // Ovo je fiktivno ime, prilagodite stvarnom imenu
    }
}
