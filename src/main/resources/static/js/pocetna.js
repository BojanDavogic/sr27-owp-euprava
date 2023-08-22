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
