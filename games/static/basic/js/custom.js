$(document).ready(function() {
    $(document).on('click', "div.foundry-listing-vertical-thumbnail div.item-game a.toggle-characters", function() {
        console.log('toggle vis');
        var div = $('ul', $(this).closest('div.characters'));
        div.toggle();
        var a = $(this);
        $('span', s).hide();
        div.is(':visible') ? $('span.hide', a).show() : $('span.show', a).hide();
        return false;
    });
});
