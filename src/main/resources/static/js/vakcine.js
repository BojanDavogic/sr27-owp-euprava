document.addEventListener("DOMContentLoaded", function() {
    var kriterijumSelect = document.getElementById("kriterijum");
    var minKolicinaInput = document.getElementById("minKolicina");
    var maxKolicinaInput = document.getElementById("maxKolicina");
    var vrednostInput = document.getElementsByName("vrednost")[0]; // Prvi input sa imenom "vrednost"

    function toggleKolicinaInputs() {
        if (kriterijumSelect.value === "kolicina") {
            minKolicinaInput.style.display = "inline-block";
            maxKolicinaInput.style.display = "inline-block";
            vrednostInput.style.display = "none";
        } else {
            minKolicinaInput.style.display = "none";
            maxKolicinaInput.style.display = "none";
            vrednostInput.style.display = "inline-block";
        }
    }

    // Postavite inicijalnu vidljivost polja na osnovu trenutnog izbora
    toggleKolicinaInputs();

    // Dodajte event listener koji će reagovati na promene u dropdown listi "kriterijum"
    kriterijumSelect.addEventListener("change", toggleKolicinaInputs);


    var noviProizvodjacLink = document.getElementById("noviProizvodjacLink");
    var noviProizvodjacForma = document.getElementById("noviProizvodjacForma");
    var proizvodjacSelect = document.getElementById("proizvodjacVakcine");
    var btnDodajVakcinu = document.getElementById("btnDodajVakcinu");

    noviProizvodjacLink.addEventListener("click", function() {
        noviProizvodjacForma.style.display = "block";
        proizvodjacSelect.disabled = true;
        btnDodajVakcinu.disabled = true;
        btnDodajVakcinu.style.background = "grey";
    });

    // potvrdiPrijava();
});

// function potvrdiPrijava() {
//     if (confirm("Da li zaista želite da pošaljete prijavu za vakcinaciju?")) {
//
//         setTimeout(() => {
//             document.getElementById("prijavaForm").submit();
//         }, 1000);
//     }
// }