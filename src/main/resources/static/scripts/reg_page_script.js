$(document).ready(function () {

    const loginIn = $("#loginInputId");
    const nameIn = $("#nameInputId");
    const lastNameIn = $("#lastNameInputId");
    const passwordInput = $("#passwordInputId");
    const confirmPasswordInput = $("#confirmPasswordInputId");
    const notEqAlert = $("#NotEquals");
    const notEqAlertDiv = $("#NotEquals > div");

    $("form>button").on("click", function () {
        // console.log("hello");
        const passVal = $(passwordInput).val();
        const confPassVal = $(confirmPasswordInput).val();

        // console.log(passVal);
        // console.log(confPassVal);

        if(loginIn.val() === "") {
            // console.log("1");
            notEqAlertDiv.text("Вы не указали логин. Выявлено у клиента через JS");
            notEqAlert.css("display", "block");
            return false;
        } else if(passVal !== confPassVal) {
            // console.log("2");
            notEqAlertDiv.text("Пароли не совпрадают. Выявлено у клиента через JS");
            notEqAlert.css("display", "block");
            return false;
        } else if(passwordInput.val() === "") {
            // console.log("3");
            notEqAlertDiv.text("Вы не указали пароль. Выявлено у клиента через JS");
            notEqAlert.css("display", "block");
            return false;
        } else if(nameIn.val() === "") {
            // console.log("4");
            notEqAlertDiv.text("Вы не указали имя. Выявлено у клиента через JS");
            notEqAlert.css("display", "block");
            return false;
        } else if(lastNameIn.val() === "") {
            // console.log("5");
            notEqAlertDiv.text("Вы не указали фамилию. Выявлено у клиента через JS");
            notEqAlert.css("display", "block");
            return false;
        }
    })
});

// $(document).ready(function () {
//
//     const h1 = $("#h1_id");
//     const h2 = $("#h2_id");
//     const button1 = $("#button1_id");
//
//     button1.on("click", function () {
//         // console.log("теперь джикверри");
//         console.log($(h1).text());
//         console.dir(h1);
//         if($(h1).text() === "") {
//             $(h1).text($(h2).text());
//             $(h2).text("");
//         } else {
//             $(h2).text($(h1).text());
//             $(h1).text("");
//         }
//
//
//     });
//
// });



// document.getElementById("button1_id").addEventListener("click", fun1);
//
// function fun1() {
//     console.log("Нажаль!");
// }


/*
const button1 = document.getElementById("button1_id");
const h1 = document.getElementById("h1_id");
const h2 = document.getElementById("h2_id");

button1.addEventListener("click", function () {
    // console.log("нажал!");
    // h1.innerText = "Ты нажал! я засек ;)"
    if(h1.innerText === "Заголовочек!") {
        h2.innerText = h1.innerText;
        h1.innerText = "";
        h2.style.backgroundColor = "#04f7ff";
    } else {
        h1.innerText = h2.innerText;
        h2.innerText = "";
        h1.style.backgroundColor = "#ff0488";
    }
})
*/
