package com.eUprava.controller;

import com.eUprava.model.Korisnik;
import com.eUprava.model.PrijavaZaVakcinu;
import com.eUprava.model.PrimljenaDoza;
import com.eUprava.model.Vakcina;
import com.eUprava.service.PrijavaZaVakcinuService;
import com.eUprava.service.PrimljenaDozaService;
import com.eUprava.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PrimljenaDozaController {

    @Autowired
    private PrimljenaDozaService primljenaDozaService;

    @Autowired
    private PrijavaZaVakcinuService prijavaZaVakcinuService;

    @Autowired
    private VakcinaService vakcinaService;

    @PostMapping(value = "/vakcine/dajVakcinu")
    public String dajVakcinu(@RequestParam Long prijavaId, RedirectAttributes redirectAttributes) {
        PrijavaZaVakcinu prijavaZaVakcinu = prijavaZaVakcinuService.findPrijavaZaVakcinu(prijavaId);
        Korisnik pacijent = prijavaZaVakcinu.getPacijent();
        String duplicateErrorMessage = null;

        List<PrimljenaDoza> primljeneDozePacijenta = primljenaDozaService.findPrimljeneDozeByPacijent(pacijent.getId());
        int brojPrimljenihDoza = primljeneDozePacijenta.size();

        if (brojPrimljenihDoza >= 4) {
            duplicateErrorMessage = "duplicate_" + prijavaId + "Pacijent je primio maksimalan broj doza.";
            redirectAttributes.addFlashAttribute("duplicateErrorMessage", duplicateErrorMessage);
            return "redirect:/vakcine";
        }

        if (brojPrimljenihDoza == 0 ||
                (brojPrimljenihDoza == 1 && Duration.between(primljeneDozePacijenta.get(0).getDatumIVremeDobijanjaDoze(), LocalDateTime.now()).toMinutes() >= 3) || // 3 meseca = 3 * 30 * 24 * 60
                (brojPrimljenihDoza == 2 && Duration.between(primljeneDozePacijenta.get(1).getDatumIVremeDobijanjaDoze(), LocalDateTime.now()).toMinutes() >= 6) || // 6 meseci = 6 * 30 * 24 * 60
                (brojPrimljenihDoza == 3 && Duration.between(primljeneDozePacijenta.get(2).getDatumIVremeDobijanjaDoze(), LocalDateTime.now()).toMinutes() >= 3)) { // 3 meseca = 3 * 30 * 24 * 60

            Vakcina vakcina = prijavaZaVakcinu.getVakcina();
            vakcina.setDostupnaKolicina(vakcina.getDostupnaKolicina() - 1);
            vakcinaService.update(vakcina);

            // Obriši sve druge prijave za istog korisnika za druge vakcine
            List<PrijavaZaVakcinu> svePrijaveZaKorisnika = prijavaZaVakcinuService.findPrijavaByImePrezimeAndJmbg(pacijent.getIme(), pacijent.getPrezime(), pacijent.getJmbg());
            for (PrijavaZaVakcinu prijava : svePrijaveZaKorisnika) {
                if (!prijava.getId().equals(prijavaId)) {
                    prijavaZaVakcinuService.delete(prijava.getId());
                }
            }

            // Dodaj novu dozu vakcine
            PrimljenaDoza novaDozaVakcine = new PrimljenaDoza();
            novaDozaVakcine.setDoza(brojPrimljenihDoza + 1);
            novaDozaVakcine.setDatumIVremeDobijanjaDoze(LocalDateTime.now());
            novaDozaVakcine.setPacijent(pacijent);
            novaDozaVakcine.setVakcina(prijavaZaVakcinu.getVakcina());
            novaDozaVakcine.setJeObrisan(false);

            primljenaDozaService.save(novaDozaVakcine);

            prijavaZaVakcinuService.delete(prijavaId);
//            return "redirect:/vakcine";
        } else {
            duplicateErrorMessage = "duplicate_" + prijavaId + "Pacijent još uvek nema pravo primanja doze.";
            redirectAttributes.addFlashAttribute("duplicateErrorMessage", duplicateErrorMessage);
            return "redirect:/vakcine";
        }
        return "redirect:/vakcine";
    }
}
