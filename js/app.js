// REGISTER
function registerUser(e) {
    e.preventDefault();

    let username = document.getElementById("username").value.trim();
    let password = document.getElementById("password").value.trim();

    if (username === "" || password === "") {
        alert("Fill all fields!");
        return false;
    }

    localStorage.setItem("user", username);
    localStorage.setItem("pass", password);

    alert("Registration Successful!");
    window.location.href = "login.html";
}

// LOGIN
function loginUser(e) {
    e.preventDefault();

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let savedUser = localStorage.getItem("user");
    let savedPass = localStorage.getItem("pass");

    if (username === savedUser && password === savedPass) {
        alert("Login Successful!");
        window.location.href = "vote.html";
    } else {
        alert("Invalid Credentials!");
    }
}