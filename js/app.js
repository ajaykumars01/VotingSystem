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

    alert("Registration Successful! Please login.");

    // ✅ NOW GO TO LOGIN PAGE
    window.location.href = "login.html";
}