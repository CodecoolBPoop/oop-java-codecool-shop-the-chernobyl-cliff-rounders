$(function() {
   $(".more").click(() => {
       let count = $(this.activeElement).closest("tr").find(".count");
       count.text(+count.text() + 1);
   });
    $(".less").click(() => {
        let count = $(this.activeElement).closest("tr").find(".count");
        count.text(Math.max(+count.text() - 1, 0));
    });
});