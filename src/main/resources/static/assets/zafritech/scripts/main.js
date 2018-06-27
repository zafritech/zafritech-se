/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/admin/applications/list",
        dataType: "json",
        cache: false
    })
    .done(function (data) {

        var applications = data;
        var menus  =  '';
        
        $.each(applications, function (key, index) {
            
            menus = menus + '<li><a href="/app/' + 
                            index.applicationName + 
                            '"><i class="fa ' + 
                            index.faIcon + 
                            ' m-r-1"></i>' + 
                            index.applicationTitle + 
                            '</a></li>';
        });
        
        $('#apps-dropdon-menu').html(menus);
    });
});

function checkLogin() {
    
    $.ajax({
        
        global: false,
        url: '/api/login/check',
        type: "GET",
        dataType: "json",
        success: function() {
            
            window.location.replace('/login');
        }
    });
}