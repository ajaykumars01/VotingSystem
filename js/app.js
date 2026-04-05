// ================= REGISTER =================
function registerUser(e) {
    e.preventDefault();

    let username = document.getElementById("username").value.trim();
    let password = document.getElementById("password").value.trim();

    if (username === "" || password === "") {
        alert("Username and Password cannot be empty!");
        return false;
    }

    if (username.length < 3 || password.length < 3) {
        alert("Minimum 3 characters required!");
        return false;
    }

    // Check duplicate user
    let savedUser = localStorage.getItem("user");
    if (username === savedUser) {
        alert("Username already exists!");
        return false;
    }

    // Save user
    localStorage.setItem("user", username);
    localStorage.setItem("pass", password);

    alert("Registration Successful! Please login.");
    window.location.href = "login.html";
}


// ================= LOGIN =================
function loginUser(e) {
    e.preventDefault();

    let username = document.getElementById("username").value.trim();
    let password = document.getElementById("password").value.trim();

    let savedUser = localStorage.getItem("user");
    let savedPass = localStorage.getItem("pass");

    if (username === savedUser && password === savedPass) {
        // Save current logged-in user
        localStorage.setItem("currentUser", username);

        alert("Login Successful!");
        window.location.href = "vote.html";
    } else {
        alert("Invalid Username or Password!");
    }
}


// ================= VOTE =================
function vote(party) {

    let currentUser = localStorage.getItem("currentUser");

    if (!currentUser) {
        alert("Please login first!");
        window.location.href = "login.html";
        return;
    }

    let voted = localStorage.getItem(currentUser + "_voted");

    if (voted === "true") {
        alert("You already voted!");
        return;
    }

    let count = localStorage.getItem(party);

    if (count === null) {
        count = 0;
    }

    localStorage.setItem(party, parseInt(count) + 1);

    // Mark this user as voted
    localStorage.setItem(currentUser + "_voted", "true");

    alert("Vote submitted successfully!");
    window.location.href = "results.html";
}


// ================= RESULTS =================
function loadResults() {

    let bjp = localStorage.getItem("BJP") || 0;
    let brs = localStorage.getItem("BRS") || 0;
    let congress = localStorage.getItem("CONGRESS") || 0;

    document.getElementById("bjpCount").innerText = bjp;
    document.getElementById("brsCount").innerText = brs;
    document.getElementById("congressCount").innerText = congress;

    let winner = "BJP";
    let max = bjp;

    if (brs > max) {
        max = brs;
        winner = "BRS";
    }

    if (congress > max) {
        winner = "CONGRESS";
    }

    document.getElementById("winner").innerText = winner;
}


// ================= LOGOUT (OPTIONAL) =================
function logout() {
    localStorage.removeItem("currentUser");
    alert("Logged out!");
    window.location.href = "login.html";
}