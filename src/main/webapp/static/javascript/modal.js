var modal = document.getElementById('myModal');
var amount = document.getElementById("amount");
var total = document.getElementById("total");
var form = document.getElementsByName("myForm")[0];
var span = document.getElementsByClassName("close")[0];


modal.style.display = "none";

form.addEventListener("submit", function(event) {
    console.log("amount value"+ amount.value);
    console.log("total value"+ total.getAttribute("data-value"));
    if (parseFloat(amount.value) !== parseFloat(total.getAttribute("data-value"))) {
        console.log("validation faild");
        event.preventDefault();
        modal.style.display = "block";
        return false;
    }
    console.log("validation success");
    return true;
},true);

modal.onclick = function(event) {
    modal.style.display = "none";

};

