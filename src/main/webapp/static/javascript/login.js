$(function() {
    $("#login").click(function() {
        let loginButton = $("#login");
        $("<li><input type='text' name='username' placeholder='Username'></li>" +
            "<li><input type='password' name='password' placeholder='Password'></li>").insertBefore(loginButton.parent());
        loginButton.off();
        loginButton.on("click", () => $(".login-form").submit());
    });
});