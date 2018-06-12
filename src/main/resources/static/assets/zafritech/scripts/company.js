/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox */

function CompanyCreateNew() {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/company/company-create-new.html',
        success: function (data) {

            var box = bootbox.confirm({

                closeButton: false,
                title: 'New Company',
                size: 'large',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Save",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var form = $('#companyCreateForm')[0];
                        var data = new FormData(form);
                        
                        $.ajax({
                            type: "POST",
                            enctype: 'multipart/form-data',
                            url: "/api/admin/companies/create/new",
                            data: data,
                            processData: false,
                            contentType: false,
                            cache: false,
                            timeout: 600000,
                            success: function () {
                                
                                 swal({

                                    title: "Success!",
                                    text: "Company successfully created.",
                                    type: "success"
                                },
                                function () {

                                    window.location.reload();
                                });
                            },
                            error: function (e) {

                                swal({

                                    title: "Error uploading file!",
                                    text: e.responseText,
                                    type: "error"
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                // Do nothing
            });
            
            box.modal('show');
        }
    });
}

//function CompanyCreateNewBackup() {
//    
//    $.ajax({
//        
//        global: false,
//        type: "GET",
//        url: '/modals/company/company-create-new.html',
//        success: function (data) {
//
//            var box = bootbox.confirm({
//
//                closeButton: false,
//                title: 'New Company',
//                size: 'large',
//                message: data,
//                buttons: {
//                    cancel: {
//                        label: "Cancel",
//                        className: "btn-danger btn-fixed-width-100"
//                    },
//                    confirm: {
//                        label: "Save",
//                        className: "btn-success btn-fixed-width-100"
//                    }
//                },
//                callback: function (result) {
//                    
//                    if (result) {
//                        
//                        var data = [];
//                        
//                        data[companyName] = document.getElementById('companyName').value;
//                        data[companyRole] = document.getElementById('companyRole').value;
//                        data[companyCode] = document.getElementById('companyCode').value;
//                        data[companyPostcode] = document.getElementById('companyPostcode').value;
//                        data[companyCountryId] = document.getElementById('companyCountryId').value;
//                        data[countryState] = document.getElementById('countryState').value;
//                        data[companyRoleDescription] = document.getElementById('').value;
//                        data[companyShortName] = document.getElementById('companyShortName').value;
//                        data[companyLogo] = document.getElementById('companyLogo').value;
//                        
//                        
//                    }
//                }
//            });
//            
//            box.on("shown.bs.modal", function(e) {
//                
//                $.ajax({
//
//                    global: false,
//                    type: "GET",
//                    contentType: "application/json",
//                    url: "/api/admin/companies/roles/list",
//                    dataType: "json",
//                    cache: false
//                })
//                .done(function (data) {
//
//                    var roles = data;
//            
//                    $(e.currentTarget).find('select[name="companyRole"]').empty();
//                    
//                    $.each(roles, function (key, index) {
//
//                        $(e.currentTarget).find('select[name="companyRole"]').append('<option value="' + index + '">' + index.replace("_ORGANISATION", "") + '</option>');
//                    });
//                    
//                    $.ajax({
//
//                        global: false,
//                        type: "GET",
//                        contentType: "application/json",
//                        url: "/api/contacts/countries/list",
//                        dataType: "json",
//                        cache: false
//                    })
//                    .done(function (data) {
//                    
//                        var countries = data;
//            
//                        console.log(countries);
//                        
//                        $(e.currentTarget).find('select[name="companyCountryId"]').empty();
//
//                        $.each(countries, function (key, index) {
//
//                            $(e.currentTarget).find('select[name="companyCountryId"]').append('<option value="' + index.id + '">' + index.countryName + '</option>');
//                        });
//                    });
//                });
//            });
//            
//            box.modal('show');
//        }
//    });
//}

function CompanyContactsView(uuId, orgName) {

    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/company/company-contacts-view.html',
        success: function (data) {

            var box = bootbox.alert({

                closeButton: false,
                title: orgName + ' Contacts',
                message: data,
                buttons: {
                    ok: {
                        label: "Close",
                        className: "btn-primary btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        // Do nothing - just close
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/admin/companies/contacts/" + uuId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var contacts =  '<div class="row">' +
                                    '<div class="col-md-12" style="padding-bottom: 10px;"><span style="font-size:18px; font-weight:bold;">' + data.companyName + '</span></div>' +
                                    '<div class="col-md-12">' + data.contact.address + '</div>' +
                                    '<div class="col-md-12" style="padding-bottom: 10px;">' + data.contact.city + ', ' + data.contact.country + ' ' + data.contact.postCode  + '</div>' +
                                    '<div class="col-md-4">Contact</div>' +
                                    '<div class="col-md-8">' + data.contact.firstName + ' ' + data.contact.lastName + '</div>' +
                                    '<div class="col-md-4">Telephone</div>' +
                                    '<div class="col-md-8">' + data.contact.phone + '</div>' +
                                    '<div class="col-md-4">Mobile</div>' +
                                    '<div class="col-md-8">' + data.contact.mobile + '</div>' +
                                    '<div class="col-md-4">Email</div>' +
                                    '<div class="col-md-8"><a href="mailto:' + data.contact.email + '">' + data.contact.email + '</a></div>' +
                                    '<div class="col-md-4">Website</div>' +
                                    '<div class="col-md-8"><a href="http://' + data.contact.website + '">' + data.contact.website + '</a></div>' +
                                    '<div class="row">';
                            
                    $('#company-contacts').append(contacts);

                });
            });
            
            box.modal('show');
        }
    });
}

function onLogoFileSelect() {
  
    document.getElementById('selectedFileRepeater').value = document.getElementById('selectedFile').value;
}

function companyCreateNew() {
    
    
}

function companyEditDetails(id) {
    
    console.log(id);
}
