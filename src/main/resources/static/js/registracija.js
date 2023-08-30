document.addEventListener("DOMContentLoaded", function () {

const form = document.getElementById('registrationForm');
const registracijaDugme = document.getElementById("btnRegistracija");

let email = document.getElementById("emailRegistracija");
let lozinka = document.getElementById("lozinkaRegistracija");
let ponovljenaLozinka = document.getElementById("ponovljenaLozinkaRegistracija");
let ime = document.getElementById("imeRegistracija");
let prezime = document.getElementById("prezimeRegistracija");
let datumRodjenja = document.getElementById("datumRodjenjaRegistracija");
let jmbg = document.getElementById("jmbgRegistracija");
let adresa = document.getElementById("adresaRegistracija");
let telefon = document.getElementById("telefonRegistracija");

let errorEmail = document.querySelector(".errorEmail");
let errorLozinka = document.querySelector(".errorLozinka");
let errorPonovljenaLozinka = document.querySelector(".errorPonovljenaLozinka");
let errorIme = document.querySelector(".errorIme");
let errorPrezime = document.querySelector(".errorPrezime");
let errorDatumRodjenja = document.querySelector(".errorDatumRodjenja");
let errorJmbg = document.querySelector(".errorJmbg");
let errorAdresa = document.querySelector(".errorAdresa");
let errorTelefon = document.querySelector(".errorTelefon");

let danas = new Date().toISOString().split("T")[0];
datumRodjenja.setAttribute("max", danas);

registracijaDugme.addEventListener("click", function (e) {
    e.preventDefault();
    let validnaRegistracija = true;

    let greske = document.querySelectorAll(".error-message");

    for(let p of greske){
        p.textContent = "";
    }

    if(email.value.trim() === ""){
        errorEmail.textContent = "Popunite polje za unos email-a";
        validnaRegistracija = false;
    }
    let patternEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(patternEmail.test(email.value) === false) {
        errorEmail.textContent = "Unesite ispravan format email adrese";
        validnaRegistracija = false;
    }
    if(lozinka.value.trim() === ""){
        errorLozinka.textContent = "Popunite polje za unos lozinke";
        validnaRegistracija = false;
    }
    if(ponovljenaLozinka.value.trim() === ""){
        errorPonovljenaLozinka.textContent = " Popunite polje za unos ponovljene lozinke";
        validnaRegistracija = false;
    }
    if(ime.value.trim() === ""){
        errorIme.textContent = "Popunite polje za unos imena";
        validnaRegistracija = false;
    }
    if(prezime.value.trim() === ""){
        errorPrezime.textContent = "Popunite polje za unos prezimena";
        validnaRegistracija = false;
    }
    if(datumRodjenja.value.trim() === ""){
        errorDatumRodjenja.textContent = "Popunite polje za unos datuma rodjenja";
        validnaRegistracija = false;
    }
    if(jmbg.value.trim() === ""){
        errorJmbg.textContent = "Popunite polje za unos jmbg-a";
        validnaRegistracija = false;
    }
    if(adresa.value.trim() === ""){
        errorAdresa.textContent = "Popunite polje za unos adrese";
        validnaRegistracija = false;
    }
    if(telefon.value.trim() === ""){
        errorTelefon.textContent = "Popunite polje za unos broja telefona";
        validnaRegistracija = false;
    }

    if(ponovljenaLozinka.value !== lozinka.value){
        errorPonovljenaLozinka.textContent = "Lozinke se ne podudaraju";
        validnaRegistracija = false;
    }

    if(validnaRegistracija){
        greske.textContent = "";
        alert("Uspesno ste se registrovali!");

        setTimeout(() => {
            form.submit();
        }, 1000);
    }
})
});
