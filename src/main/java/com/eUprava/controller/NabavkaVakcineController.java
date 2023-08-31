package com.eUprava.controller;

import com.eUprava.model.Korisnik;
import com.eUprava.model.NabavkaVakcine;
import com.eUprava.model.Vakcina;
import com.eUprava.service.NabavkaVakcineService;
import com.eUprava.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NabavkaVakcineController {

    @Autowired
    private VakcinaService vakcinaService;

    @Autowired
    private NabavkaVakcineService nabavkaVakcineService;


    @PostMapping("/vakcine/nabavka-forma")
    public String kreirajZahtevZaNabavkom(
            @RequestParam("vakcina") Long vakcinaId,
            @RequestParam("kolicina") Integer kolicina,
            @RequestParam("razlog") String razlog,
            HttpSession session
    ) {
        Korisnik medicinskoOsoblje = (Korisnik) session.getAttribute("korisnik");

        NabavkaVakcine zahtev = new NabavkaVakcine();
        zahtev.setVakcina(vakcinaService.findVakcina(vakcinaId));
        zahtev.setKolicinaVakcina(kolicina);
        zahtev.setRazlogNabavke(razlog);
        zahtev.setMedicinskoOsoblje(medicinskoOsoblje);
        zahtev.setDatumIVremeKreiranjaZahteva(LocalDateTime.now());
        zahtev.setStatus("Na čekanju");

        nabavkaVakcineService.save(zahtev);

        return "redirect:/vakcine";
    }

    @PostMapping("/admin/odobri-zahtev")
    public String odobriZahtev(@RequestParam("zahtevId") Long zahtevId) {
        NabavkaVakcine nabavkaVakcine = nabavkaVakcineService.findNabavkaVakcine(zahtevId);
        nabavkaVakcine.setStatus("Odobren");
        Vakcina vakcina = vakcinaService.findVakcina(nabavkaVakcine.getVakcina().getId());
        int dostupnaKolicina = vakcina.getDostupnaKolicina();
        vakcina.setDostupnaKolicina(dostupnaKolicina + nabavkaVakcine.getKolicinaVakcina());
        vakcinaService.update(vakcina);
        nabavkaVakcineService.update(nabavkaVakcine);
        return "redirect:/vakcine";
    }

    @PostMapping("/admin/odbij-zahtev")
    public String odbijZahtev(@RequestParam("zahtevId") Long zahtevId, @RequestParam("razlogOdbijanja") String razlogOdbijanja) {
        NabavkaVakcine nabavkaVakcine = nabavkaVakcineService.findNabavkaVakcine(zahtevId);
        nabavkaVakcine.setStatus("Odbijen");
        nabavkaVakcine.setRazlogOdbijanjaZahteva(razlogOdbijanja);

        nabavkaVakcineService.update(nabavkaVakcine);
        return "redirect:/vakcine";
    }

    @PostMapping("/admin/vrati-na-reviziju")
    public String vratiNaReviziju(@RequestParam("zahtevId") Long zahtevId, @RequestParam("komentar") String komentar) {
        NabavkaVakcine nabavkaVakcine = nabavkaVakcineService.findNabavkaVakcine(zahtevId);
        nabavkaVakcine.setStatus("Na reviziji");
        nabavkaVakcine.setRazlogOdbijanjaZahteva(komentar);
        nabavkaVakcineService.update(nabavkaVakcine);
        return "redirect:/vakcine";
    }

    @PostMapping("/admin/nabavka-azuriranje")
    public String azuriranjeZahtevaZaNabavku(@RequestParam("zahtevId") Long zahtevId,
                                             @RequestParam("novaKolicina") Integer novaKolicina,
                                             @RequestParam("noviRazlog") String noviRazlog) {
        NabavkaVakcine zahtev = nabavkaVakcineService.findNabavkaVakcine(zahtevId);
        zahtev.setKolicinaVakcina(novaKolicina);
        zahtev.setRazlogNabavke(noviRazlog);
        zahtev.setRazlogOdbijanjaZahteva("");
        zahtev.setStatus("Na čekanju");
        nabavkaVakcineService.update(zahtev);
        return "redirect:/vakcine";
    }


}
