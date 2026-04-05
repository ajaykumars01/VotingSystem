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