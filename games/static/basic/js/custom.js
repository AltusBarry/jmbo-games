$(document).ready(function() {
    $(document).on('click', "div.foundry-listing-vertical-thumbnail div.item-game a.toggle-characters", function() {
        console.log('toggle vis');
        var div = $('ul', $(this).closest('div.characters'));
        div.toggle();
        var a = $(this);
        $('span', a).hide();
        (div.is(':visible')) ? $('span.hide', a).show() : $('span.show', a).show();
        /*if(div.is(':visible')) {
            $('span.hide', a).show();
        }else {
            $('span.show', a).show();
        }*/
        console.log(div.is(':visible'));
        return false;
    });
});
