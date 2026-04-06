function registerUser(e) {
    e.preventDefault();

    let username = document.getElementById("username").value.trim();
    let password = document.getElementById("password").value.trim();

    if (username === "" || password === "") {
        alert("Fill all fields!");
        return;
    }

    let users = JSON.parse(localStorage.getItem("users")) || [];

    let exists = users.find(u => u.username === username);
    if (exists) {
        alert("Username already exists!");
        return;
    }

    users.push({ username: username, password: password, voted: false });

    localStorage.setItem("users", JSON.stringify(users));

    alert("Registration Successful!");

    // ✅ Redirect to login
    setTimeout(() => {
        window.location.href = "login.html";
    }, 300);
}

// ================= LOGIN =================
function loginUser(e) {
    e.preventDefault();

    let username = document.getElementById("username").value.trim();
    let password = document.getElementById("password").value.trim();

    let users = JSON.parse(localStorage.getItem("users")) || [];

    let user = users.find(u => u.username === username && u.password === password);

    if (user) {
        localStorage.setItem("currentUser", username);

        alert("Login Successful!");
        window.location.href = "vote.html";
    } else {
        alert("Invalid Credentials!");
    }
}


// ================= VOTE =================
function vote(party) {

    let currentUser = localStorage.getItem("currentUser");

    if (!currentUser) {
        alert("Login first!");
        window.location.href = "login.html";
        return;
    }

    let users = JSON.parse(localStorage.getItem("users")) || [];

    let user = users.find(u => u.username === currentUser);

    if (user.voted) {
        alert("You already voted!");
        return;
    }

    let count = localStorage.getItem(party) || 0;
    localStorage.setItem(party, parseInt(count) + 1);

    user.voted = true;
    localStorage.setItem("users", JSON.stringify(users));

    alert("Vote submitted successfully!");

    // ✅ NO REDIRECT HERE
}


  
    let count = localStorage.getItem(party) || 0;
    localStorage.setItem(party, parseInt(count) + 1);

    // Mark user as voted
    user.voted = true;

    localStorage.setItem("users", JSON.stringify(users));

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


// ================= LOGOUT =================
function logout() {
    localStorage.removeItem("currentUser");
    alert("Logged out!");
    window.location.href = "login.html";
}