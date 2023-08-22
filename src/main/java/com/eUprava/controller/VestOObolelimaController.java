package com.eUprava.controller;

import com.eUprava.model.Vest;
import com.eUprava.model.VestOObolelima;
import com.eUprava.service.VestOObolelimaService;
import com.eUprava.service.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class VestOObolelimaController {

    @Autowired
    private VestOObolelimaService vestOObolelimaService;
    @Autowired
    private VestService vestService;

    @GetMapping("")
    public String showIndex(Model model) {
        // Dobijanje svih vesti
        List<Vest> sveVesti = vestService.findSveVesti();
        List<Vest> sortiraneVesti = sveVesti.stream()
                .sorted(Comparator.comparing(Vest::getDatumIVremeObjavljivanja).reversed())
                .collect(Collectors.toList());

        model.addAttribute("sveVesti", sortiraneVesti);

        // Dobijanje statistike
        List<VestOObolelima> statistikaVesti = vestOObolelimaService.findSveVestiOObolelima();

        // Filtriranje statistike za trenutni datum
        LocalDate danas = LocalDate.now();
        Optional<VestOObolelima> statistikaZaDanas = statistikaVesti.stream()
                .filter(vest -> vest.getDatumIVremeObjavljivanja().toLocalDate().equals(danas))
                .findFirst();

        // Obeležite statistiku za trenutni datum kao istaknutu (prvu vest)
        statistikaZaDanas.ifPresent(istaknutaVest -> model.addAttribute("istaknutaVest", istaknutaVest));

        return "index";
    }

    @GetMapping("korisnici/pocetna")
    public String showPocetna(Model model) {
        // Dobijanje svih vesti
        List<Vest> sveVesti = vestService.findSveVesti();
        List<Vest> sortiraneVesti = sveVesti.stream()
                .sorted(Comparator.comparing(Vest::getDatumIVremeObjavljivanja).reversed())
                .collect(Collectors.toList());

        model.addAttribute("sveVesti", sortiraneVesti);

        // Dobijanje statistike
        List<VestOObolelima> statistikaVesti = vestOObolelimaService.findSveVestiOObolelima();

        // Filtriranje statistike za trenutni datum
        LocalDate danas = LocalDate.now();
        Optional<VestOObolelima> statistikaZaDanas = statistikaVesti.stream()
                .filter(vest -> vest.getDatumIVremeObjavljivanja().toLocalDate().equals(danas))
                .findFirst();

        // Obeležite statistiku za trenutni datum kao istaknutu (prvu vest)
        statistikaZaDanas.ifPresent(istaknutaVest -> model.addAttribute("istaknutaVest", istaknutaVest));

        return "pocetna";
    }
}
