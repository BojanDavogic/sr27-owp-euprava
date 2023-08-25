function openModal(naziv, sadrzaj, vestId) {
    const modal = document.getElementById("modal");
    const idInput = document.getElementById("vestId");
    const nazivInput = document.getElementById("nazivIzmena");
    const sadrzajInput = document.getElementById("sadrzajIzmena");

    idInput.value = vestId;
    nazivInput.value = naziv;
    sadrzajInput.value = sadrzaj;

    const izmenaForma = document.getElementById("izmenaForma");
    izmenaForma.action = "izmeni-vest/" + vestId;

    modal.style.display = "flex";
}

function closeModal() {
    const modal = document.getElementById("modal");
    modal.style.display = "none";
}

document.addEventListener("DOMContentLoaded", function (){
    const izmeni = document.getElementsByClassName("izmena-dugme");

    for (let i = 0; i < izmeni.length; i++) {
        izmeni[i].addEventListener("click", function() {
            const vest = this.closest(".objava");
            const nazivVesti = vest.querySelector("h3").textContent;
            const sadrzaj = vest.querySelector("p").textContent;
            const vestId = vest.getAttribute("data-id");

            openModal(nazivVesti, sadrzaj, vestId);
        });

    }
});

function openModalStatistika(oboleli, testirani, hospitalizovani, pacijentiNaRespiratoru, statistikaId) {
    const modalStatistika = document.getElementById("modalStatistika");
    const statistikaIdInput = document.getElementById("statistikaId");
    const oboleliInput = document.getElementById("oboleliIzmena");
    const testiraniInput = document.getElementById("testiraniIzmena");
    const hospitalizovaniInput = document.getElementById("hospitalizovaniIzmena");
    const pacijentiNaRespiratoruInput = document.getElementById("pacijentiNaRespiratoruIzmena");

    statistikaIdInput.value = statistikaId;
    oboleliInput.value = oboleli;
    testiraniInput.value = testirani;
    hospitalizovaniInput.value = hospitalizovani;
    pacijentiNaRespiratoruInput.value = pacijentiNaRespiratoru;

    const izmenaStatistikaForma = document.getElementById("izmenaStatistikaForma");
    izmenaStatistikaForma.action = "izmeni-statistiku/" + statistikaId;

    modalStatistika.style.display = "flex";
}

function closeModalStatistika() {
    const modalStatistika = document.getElementById("modalStatistika");
    modalStatistika.style.display = "none";
}

document.addEventListener("DOMContentLoaded", function (){
    const izmeniStatistika = document.getElementsByClassName("izmena-dugme-statistika");

    for (let i = 0; i < izmeniStatistika.length; i++) {
        izmeniStatistika[i].addEventListener("click", function() {
            const statistika = this.closest(".objava");
            const oboleli = statistika.querySelector(".broj-obolelih").textContent;
            const testirani = statistika.querySelector(".broj-testiranih").textContent;
            const hospitalizovani = statistika.querySelector(".broj-hospitalizovanih").textContent;
            const pacijentiNaRespiratoru = statistika.querySelector(".broj-pacijenti-na-respiratoru").textContent;
            const statistikaId = statistika.getAttribute("data-id");

            openModalStatistika(oboleli, testirani, hospitalizovani, pacijentiNaRespiratoru, statistikaId);
        });
    }
});

function obrisiVest(buttonElement) {
    if (confirm("Da li ste sigurni da želite da obrišete ovu vest?")) {
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        const vestId = buttonElement.getAttribute("data-id");
        form.setAttribute("action", "obrisi-vest/" + vestId);

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "_method");
        hiddenField.setAttribute("value", "POST");
        form.appendChild(hiddenField);

        document.body.appendChild(form);
        form.submit();

    }
}

