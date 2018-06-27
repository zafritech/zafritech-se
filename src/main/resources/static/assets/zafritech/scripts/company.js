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

function CompanyBasicProfileUpdate(companyId) {
      
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/company/company-update-profile.html',
        success: function (data) {

            var box = bootbox.confirm({

                closeButton: false,
                title: 'Update Company Profile',
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
                            url: "/api/admin/companies/update/profile",
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
                
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/admin/companies/" + companyId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var company = data;
                    console.log(company);
            
                    $(e.currentTarget).find('input[name="companyId"]').prop('value', companyId);
                    $(e.currentTarget).find('input[name="companyName"]').prop('value', company.companyName);
                    $(e.currentTarget).find('input[name="companyShortName"]').prop('value', company.companyShortName);
                    $(e.currentTarget).find('input[name="companyCode"]').prop('value', company.companyCode);
                    $(e.currentTarget).find('input[name="companyRoleDescription"]').prop('value', company.companyRoleDescription);
                });
            });
            
            box.modal('show');
        }
    });
}

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

function companyChangeLogoImage(companyId) {
    
    $.ajax({
            
        global: false,
        type: "GET",
        url: '/modals/company/company-upload-logo.html',
        success: function (data) {
            
           var box = bootbox.confirm({

                closeButton: false,
                message: data,
                title: "Upload Company Logo",
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
                        
                        var form = $('#logoUploadForm')[0];
                        var data = new FormData(form);
                        
                        $.ajax({
                            type: "POST",
                            enctype: 'multipart/form-data',
                            url: "/api/admin/companies/logo/update",
                            data: data,
                            processData: false,
                            contentType: false,
                            cache: false,
                            timeout: 600000,
                            success: function (data) {
                                
                                 swal({

                                    title: "Success!",
                                    text: "Company logo has been successfully created.",
                                    type: "success"
                                });
                                
                                setTimeout(function() { window.location.reload(); }, 2000);
                            },
                            error: function (e) {

                                swal({

                                    title: "Error updating company logo!",
                                    text: e.responseText,
                                    type: "error"
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $(e.currentTarget).find('input[name="itemId"]').prop('value', companyId);
            });
            
            box.modal('show');
        }
    });
}

function CompanyContactDetailsUpdate(companyId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/company/company-contacts-update.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Update Company Contact Details',
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
                        
                        var data = {};
                        
                        data['companyId'] = document.getElementById('companyId').value;
                        data['phone'] = document.getElementById('phone').value;
                        data['mobile'] = document.getElementById('mobile').value;
                        data['email'] = document.getElementById('email').value;
                        data['website'] = document.getElementById('website').value;
                        data['address'] = document.getElementById('address').value;
                        data['country'] = document.getElementById('country').value;
                        data['state'] = document.getElementById('state').value;
                        data['city'] = document.getElementById('city').value;
                        data['postCode'] = document.getElementById('postCode').value;
                        
                        $.ajax({
                            
                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/admin/companies/contact/save/" + companyId,
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    title: "Contact updated!",
                                    text: "Contact details have been successfully updated.",
                                    type: "success"
                                },
                                function() {
                                   
                                    window.location.reload();
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function() {
                
                $.ajax({

                    global: false,
                    type: "GET",
                    contentType: "application/json",
                    url: "/api/contacts/countries/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                   
                    var selectCountryOptions = '';
                    
                    $.each(data, function (key, index) {

                        selectCountryOptions = selectCountryOptions + '<option value="' + index.iso3 + '">' + index.countryName+ '</option>';
                    });
                    
                    $('#country').empty();
                    $('#country').append(selectCountryOptions);
                
//                    GetUserContactDetails(companyId);

                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/admin/company/contact/" + companyId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        if (data.phone !== null) { $('#phone').prop('value', data.phone); }
                        if (data.mobile !== null) { $('#mobile').prop('value', data.mobile); }
                        if (data.address !== null) { $('#address').prop('value', data.address); }
                        if (data.email !== null) { $('#email').prop('value', data.email); }
                        if (data.website !== null) { $('#website').prop('value', data.website); }
                        if (data.country !== null) { $('#country').prop('value', data.country.iso3); }
                        if (data.state !== null) { $('#state').prop('value', data.state); }
                        if (data.city !== null) { $('#city').prop('value', data.city); }
                        if (data.city !== null) { $('#postCode').prop('value', data.postCode); }
                    });
                });
            });
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
