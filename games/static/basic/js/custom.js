$(document).ready(function() {
    $(document).on('click', "div.foundry-listing-vertical-thumbnail div.item-game a.toggle-characters-hide", function() {
        console.log('toggle vis');
        $(this).toggle();
        $("div.foundry-listing-vertical-thumbnail div.item-game a.toggle-characters-show").toggle()
        $('ul', $(this).closest('div.characters')).toggle();
        return false;
    });
});