// REGISTER
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

    let savedUser = localStorage.getItem("user");
    if (username === savedUser) {
        alert("Username already exists!");
        return false;
    }

    localStorage.setItem("user", username);
    localStorage.setItem("pass", password);

    alert("Registration Successful");
    window.location.href = "vote.html";
}

// LOGIN
function loginUser(e) {
    e.preventDefault();

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let savedUser = localStorage.getItem("user");
    let savedPass = localStorage.getItem("pass");

    if (username === savedUser && password === savedPass) {
        alert("Login Successful");
        window.location.href = "vote.html";
    } else {
        alert("Invalid Credentials");
    }
}

// VOTE
function vote(party) {
    let voted = localStorage.getItem("voted");

    if (voted === "yes") {
        alert("You already voted!");
        return;
    }

    let count = localStorage.getItem(party) || 0;
    localStorage.setItem(party, parseInt(count) + 1);
    localStorage.setItem("voted", "yes");

    alert("Vote Successful");
}

// RESULTS
function loadResults() {
    let bjp = localStorage.getItem("bjp") || 0;
    let brs = localStorage.getItem("brs") || 0;
    let congress = localStorage.getItem("congress") || 0;

    document.getElementById("bjpCount").innerText = bjp;
    document.getElementById("brsCount").innerText = brs;
    document.getElementById("congressCount").innerText = congress;

    let winner = "BJP";
    let max = bjp;

    if (brs > max) {
        winner = "BRS";
        max = brs;
    }
    if (congress > max) {
        winner = "CONGRESS";
    }

    document.getElementById("winner").innerText = winner;
}