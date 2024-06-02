$(document).ready(function(){
    $('#profile_btn img').on('click', function(){
        let fon = $('#fon').fadeIn(500)
        let profile = $('#profile').fadeIn(500)
        $('#profile').load(function(){
            $(this).fadeIn(2000)
        })
        
    })
    $(document).click(function(event){
        var $target = $(event.target);
            if((!$target.closest('#profile_btn').length && !$target.closest('#profile').length) && $('#profile').is(":visible")){
                $('#profile').fadeOut(500)
                $('#fon').fadeOut(500)
            }
    })

    function Product(){
        window.open("file:///E:/Games/Sublime%20Text/Сайтик/PrP/pages/product.html");
    }
})