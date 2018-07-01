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
        var menusSide  =  '';
        
        $.each(applications, function (key, index) {
            
            /* Top Nav Menu */
            menus = menus + '<li><a href="/app/' + 
                            index.applicationName + 
                            '"><i class="fa ' + 
                            index.faIcon + 
                            ' m-r-1"></i>' + 
                            index.applicationShortTitle + 
                            '</a></li>';
                 
            /* Side Nav Menu */
            menusSide = menusSide + '<li class="px-nav-item">' +
                                    '<a href="/app/' + index.applicationName + '">' +
                                    '<span class="px-nav-label">' + index.applicationShortTitle + '</span>' + 
                                    '</a></li>';
        });
        
        $('#apps-dropdon-menu').html(menus);
        $('#apps-dropdon-menu-side').html(menusSide);
    });
});

$(document).ready(function () {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/application/active",
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        var application = data;

        if (application.docCentric === true) {
          
            $('#applicationSideNavTitle1').text("Documents");
            $('#applicationSideNav1').show();
        }
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