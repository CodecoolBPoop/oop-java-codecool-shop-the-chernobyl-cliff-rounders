var urlmenu = document.getElementById( 'choseProduct' );
// console.log(urlmenu);
urlmenu.onchange = function() {
    window.location.href=("?category=" + this.options[ this.selectedIndex ].value.toLowerCase());
};

// // window.onload = function(){
// //     location.href=document.getElementById("choseProduct").value.toLowerCase();
// // }

