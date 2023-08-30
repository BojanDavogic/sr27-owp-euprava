package com.eUprava.controller;

import com.eUprava.model.*;
import com.eUprava.service.PrijavaZaVakcinuService;
import com.eUprava.service.PrimljenaDozaService;
import com.eUprava.service.ProizvodjacVakcineService;
import com.eUprava.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VakcinaController {
    @Autowired
    private VakcinaService vakcinaService;

    @Autowired
    private ProizvodjacVakcineService proizvodjacVakcineService;

    @Autowired
    private PrijavaZaVakcinuService prijavaZaVakcinuService;

    @Autowired
    private PrimljenaDozaService primljenaDozaService;

    @GetMapping(value = "/vakcine")
    public String vakcineStranica(
            @RequestParam(name = "kriterijum", required = false) String kriterijum,
            @RequestParam(name = "vrednost", required = false) String vrednost,
            @RequestParam(name = "minKolicina", required = false) Integer minKolicina,
            @RequestParam(name = "maxKolicina", required = false) Integer maxKolicina,
            @RequestParam(name = "sortiranje", required = false) String sortiranje,
            Model model,
            HttpSession httpSession,
            @RequestParam(name = "ime", required = false) String ime,
            @RequestParam(name = "prezime", required = false) String prezime,
            @RequestParam(name = "jmbg", required = false) String jmbg
    ) {
        List<Vakcina> vakcine = new ArrayList<>();

        if (kriterijum != null && vrednost != null) {
            switch (kriterijum) {
                case "ime":
                    vakcine = vakcinaService.findVakcinaByNaziv(vrednost);
                    break;
                case "proizvodjac":
                    vakcine = vakcinaService.findVakcinaByNazivProizvodjaca(vrednost);
                    break;
                case "drzava":
                    vakcine = vakcinaService.findVakcinaByDrzava(vrednost);
                    break;
                case "kolicina":
                    if (minKolicina != null && maxKolicina != null) {
                        vakcine = vakcinaService.findVakcinaByKolicina(minKolicina, maxKolicina);
                    }
                    break;
            }
        } else {
            vakcine = vakcinaService.findSveVakcine();
        }

        if (sortiranje != null) {
            vakcine = vakcinaService.sortVakcine(vakcine, sortiranje);
        }

        Korisnik korisnik = (Korisnik) httpSession.getAttribute("korisnik");

        if (korisnik != null && korisnik.getUloga() == Uloga.MedicinskoOsoblje) {
            List<PrijavaZaVakcinu> prijaveZaVakcinaciju = prijavaZaVakcinuService.findSvePrijaveZaVakcinu();

            if (ime != null || prezime != null || jmbg != null) {
                prijaveZaVakcinaciju = prijavaZaVakcinuService.findPrijavaByImePrezimeAndJmbg(ime, prezime, jmbg);
            }

            model.addAttribute("prijaveZaVakcinaciju", prijaveZaVakcinaciju);
        }

        model.addAttribute("vakcine", vakcine);

        List<ProizvodjacVakcine> proizvodjaciVakcineList = proizvodjacVakcineService.findSviProizvodjaciVakcine();
        model.addAttribute("proizvodjaci", proizvodjaciVakcineList);

        return "vakcine.html";
    }

    @GetMapping(value = "/vakcine/{id}")
    public String detaljiOVakcini(@PathVariable Long id, Model model) {
        Vakcina vakcina = vakcinaService.findVakcina(id);
        if (vakcina == null) {
            return "redirect:/vakcine";
        }
        model.addAttribute("vakcina", vakcina);
        return "detalji_vakcine.html";
    }

    @PostMapping(value = "/vakcine/dodaj")
    public String dodajVakcinu(@RequestParam("imeVakcine") String imeVakcine,
                               @RequestParam("proizvodjacVakcine") Long proizvodjacId) {

        if (proizvodjacId != null) {
            Vakcina novaVakcina = new Vakcina();
            novaVakcina.setIme(imeVakcine);
            novaVakcina.setDostupnaKolicina(0);

            ProizvodjacVakcine proizvodjacVakcine = proizvodjacVakcineService.findProizvodjacVakcine(proizvodjacId);
            novaVakcina.setProizvodjac(proizvodjacVakcine);
            novaVakcina.setJeObrisan(false);

            vakcinaService.save(novaVakcina);
        }

        return "redirect:/vakcine";
    }

    @PostMapping(value = "/proizvodjaci/dodaj")
    public String dodajProizvodjaca(@RequestParam("nazivProizvodjaca") String nazivProizvodjaca,
                               @RequestParam("drzavaProizvodjaca") String drzavaProizvodjaca) {
        ProizvodjacVakcine proizvodjacVakcine = new ProizvodjacVakcine();
        proizvodjacVakcine.setProizvodjac(nazivProizvodjaca);
        proizvodjacVakcine.setDrzavaProizvodnje(drzavaProizvodjaca);
        proizvodjacVakcine.setJeObrisan(false);

        proizvodjacVakcineService.save(proizvodjacVakcine);
        return "redirect:/vakcine";
    }

    @PostMapping(value = "/vakcine/izmeni/{vakcinaId}")
    public String izmeniVakcinu(@PathVariable Long vakcinaId, @RequestParam Long proizvodjacVakcineId, @RequestParam String imeVakcine, @RequestParam String nazivProizvodjaca, @RequestParam String drzavaProizvodjaca) {
        Vakcina existingVakcina = vakcinaService.findVakcina(vakcinaId);

        if (existingVakcina != null) {
            existingVakcina.setIme(imeVakcine);

            ProizvodjacVakcine proizvodjacVakcine = proizvodjacVakcineService.findProizvodjacVakcine(proizvodjacVakcineId);
            proizvodjacVakcine.setProizvodjac(nazivProizvodjaca);
            proizvodjacVakcine.setDrzavaProizvodnje(drzavaProizvodjaca);

            proizvodjacVakcineService.update(proizvodjacVakcine);

            vakcinaService.update(existingVakcina);
        }

        return "redirect:/vakcine/" + vakcinaId;
    }

    @PostMapping(value = "/vakcine/prijava")
    public String prijavaZaVakcinaciju(@RequestParam("vakcinaId") Long vakcinaId, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Korisnik pacijent = (Korisnik) session.getAttribute("korisnik");
        String duplicateErrorMessage = null;

        List<PrimljenaDoza> primljeneDozePacijenta = primljenaDozaService.findPrimljeneDozeByPacijent(pacijent.getId());
        int brojPrimljenihDoza = primljeneDozePacijenta.size();

        if (pacijent != null && pacijent.getUloga() == Uloga.Pacijent) {
            Vakcina vakcina = vakcinaService.findVakcina(vakcinaId);

            if (vakcina != null && vakcina.getDostupnaKolicina() > 0) {
                // Provera da li već postoji prijava za istog pacijenta i istu vakcinu
                boolean postojiPrijava = prijavaZaVakcinuService.existsPrijavaZaPacijentaIVakcinu(pacijent.getId(), vakcinaId);

                if(postojiPrijava){
                    duplicateErrorMessage = "duplicate_" + vakcinaId + "Već ste se prijavili za ovu vakcinu.";
                    redirectAttributes.addFlashAttribute("duplicateErrorMessage", duplicateErrorMessage);
                    return "redirect:/vakcine";
                }

                if (brojPrimljenihDoza >= 4) {
                    duplicateErrorMessage = "duplicate_" + vakcinaId + "Primili ste maksimalan broj doza.";
                    redirectAttributes.addFlashAttribute("duplicateErrorMessage", duplicateErrorMessage);
                    return "redirect:/vakcine";
                }

                if(brojPrimljenihDoza == 0 ||
                        (brojPrimljenihDoza == 1 && Duration.between(primljeneDozePacijenta.get(0).getDatumIVremeDobijanjaDoze(), LocalDateTime.now()).toMinutes() >= 3) ||
                        (brojPrimljenihDoza == 2 && Duration.between(primljeneDozePacijenta.get(1).getDatumIVremeDobijanjaDoze(), LocalDateTime.now()).toMinutes() >= 6) ||
                        (brojPrimljenihDoza == 3 && Duration.between(primljeneDozePacijenta.get(2).getDatumIVremeDobijanjaDoze(), LocalDateTime.now()).toMinutes() >= 3)) {

                    PrijavaZaVakcinu prijava = new PrijavaZaVakcinu();
                    prijava.setDatumIVremePrijave(LocalDateTime.now());
                    prijava.setPacijent(pacijent);
                    prijava.setVakcina(vakcina);
                    prijava.setJeObrisan(false);

                    prijavaZaVakcinuService.save(prijava);
                    return "redirect:/vakcine";
                } else {
                    duplicateErrorMessage = "duplicate_" + vakcinaId + "Još uvek nemate pravo da izvršite prijavu za vakcinu.";
                    redirectAttributes.addFlashAttribute("duplicateErrorMessage", duplicateErrorMessage);
                    return "redirect:/vakcine";
                }
            }
        }
        return "redirect:/vakcine";
    }

}
