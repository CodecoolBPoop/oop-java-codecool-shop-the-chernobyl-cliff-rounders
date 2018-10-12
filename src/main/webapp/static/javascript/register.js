$(function() {
    $(".register-btn").click(() => {
        let username = $("#uname").val();
        let email = $("#email").val();
        let password = $("#pw").val();
        let password2 = $("#pw2").val();

        $.ajax({
            url: '/register',
            method: 'POST',
            data: JSON.stringify({ username, email, password, password2 }),
            contentType: 'application/json'
        }).done((_, __, response) => {
            let errors = response.getResponseHeader("errors");
            if (errors === null) window.location.href = "http://0.0.0.0:8080/";
            else {
                console.log(errors);
                errors.split("\\n").forEach(error => {
                    $(".reg-form").append(`<h4 class='error'>${error}</h4>`);
                });
            }
        });
    });
});