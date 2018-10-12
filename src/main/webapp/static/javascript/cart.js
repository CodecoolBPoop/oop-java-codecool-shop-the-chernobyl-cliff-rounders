$(function() {
    $(".more").click(() => {
        let [ $row, $count, $id ] = locate(this.activeElement);
        updateDatabase($id.val(), "add");
        let $cartNum = $(".js-cartNum");
        $cartNum.text(+$cartNum.text() + 1);
        let oldCount = +$count.text();
        $count.text(oldCount + 1);
        updatePrice($row, oldCount + 1);
    });

    $(".less").click(() => {
        let [ $row, $count, $id ] = locate(this.activeElement);
        updateDatabase($id.val(), "remove");
        let oldCount = +$count.text();
        if (oldCount === 0) return;
        let $cartNum = $(".js-cartNum");
        $cartNum.text(+$cartNum.text() - 1);
        $count.text(oldCount - 1);
        updatePrice($row, oldCount - 1, true);
    });

    const updatePrice = ($row, newCount, subtract=false) => {
        let [ price, currency ] = $row.find(".price").text().split(" ");
        $row.find(".total").text(`${(+price * newCount).toFixed(1)} ${currency}`);
        let $grandTotal = $(".grand-total");
        let newGrandTotal = +$grandTotal.text() + (subtract ? -price : +price);
        $grandTotal.text(newGrandTotal.toFixed(1));
    };

    const locate = (element) => {
        let $row = $(element).closest("tr");
        let $count = $row.find(".count");
        let $id = $row.find(".product-id");
        return [ $row, $count, $id ];
    };

    const updateDatabase = (productId, action) => {
        $.ajax({
            url: '/updateCart',
            method: 'PUT',
            data: JSON.stringify({action : [productId, action]}),
            contentType: 'application/json'
        });
    }
});