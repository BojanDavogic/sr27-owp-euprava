document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    const greska = document.getElementById("greskaProfil");
    form.addEventListener("submit", function(event) {
        let validno = true;
        const lozinkaInput = document.getElementById("lozinka");
        const potvrdaLozinkeInput = document.getElementById("potvrdaLozinke");

        if (lozinkaInput.value !== potvrdaLozinkeInput.value) {
            validno = false;
            event.preventDefault();
            greska.textContent = "Unete lozinke se ne poklapaju.";
        }
        if(validno){
            alert("Uspesno ste izmenili podatke")
        }
    });
});