/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootbox */

var zMainDocTreeObj = null;
var curExpandNode = null;

$(document).ready(function () {
    
    zSBSTreeLoad();
});

function zSBSTreeLoad() {
    
    if ($('#sbsTreeValid').length > 0) {
        
        var zSBSTreeObj;
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0
                }
            },
            view: {
                dblClickExpand: true
            },
            callback: {

                beforeExpand: beforeExpand,
                onExpand: onExpand,
                beforeClick: zSBSBeforeClick
            }
        };
        
        $.ajax({

            global: false,
            url: '/api/integration/elements/tree/load',
            type: "GET",
            dataType: "json",
            cache: true,
            success: function (data) {
                
                zSBSTreeObj = $.fn.zTree.init($("#sbsElementsTree"), setting, data);
                zSBSTreeObj.expandAll(false);   // Collapse all nodes
                
                // Expand Table of Contents node (Node ID: 1)
                if ($('#sbsNodeId').length > 0 && $('#sbsNodeId').val().length !== 0) {
                    
                    var sbsNodeId =  document.getElementById('sbsNodeId').value;
                    var node = zSBSTreeObj.getNodeByParam("id", sbsNodeId, null);
                    
                    if (node !== null) {
                        
                        var nodes = node.getPath();
                        
                        zSBSTreeObj.expandAll(false);
                        
                        for (i = 0; i < nodes.length; i++) {

                            zSBSTreeObj.expandNode(nodes[i], true, false, true);
                        }

                        loadEntityElements(sbsNodeId);
                    }
                    
                } else {            

                    zSBSTreeObj.expandAll(false);
                    var firstNode = zSBSTreeObj.getNodeByParam("id", 1, null);
                    zSBSTreeObj.expandNode(firstNode, true, false, true);
                }
                
                // Disable browser Back button to protect dynamically generated states 
                history.pushState(null, null, location.href);
                window.onpopstate = function () { history.go(1); };
            }
        });
    }
}
             
function zSBSBeforeClick(treeId, treeNode) {
    
    $('#sbsNodeId').val(treeNode.id);
    $('#elementId').val(treeNode.linkId);
    
    if (treeNode.pId === 0) {               // Root node (Project node)
         
        // TBD: Display entities
         
    } else if (treeNode.pId === 1) {        // Entity nodes (Companies)
        
        loadEntityElements(treeNode.linkId);
        
    } else {

        if (treeNode.isParent) {
            
            loadElementSubElements(treeNode.linkId);
            
        } else {
            
            loadElementInterfaces(treeNode.linkId);
        }
    }
}

function loadEntityElements(entityId) {
    
    var url = "/api/integration/entity/elements/view/" + entityId;
    
    $('#systemIntegrationPageContent').load(url);
}

function loadElementSubElements(elementId) {
    
    var url = "/api/integration/element/elements/view/" + elementId;
    
    $('#systemIntegrationPageContent').load(url);
}

function loadElementInterfaces(elementId) {
 
    if (elementId === 0) {
        
        elementId = document.getElementById('elementId').value;
    }
    
    var url = "/api/integration/element/interfaces/view/" + elementId;
    
    $('#systemIntegrationPageContent').load(url);
}

function loadInterfaceDetails(interfaceId) {
   
    var url = "/api/integration/interface/view/" + interfaceId;
    
    $('#systemIntegrationPageContent').load(url);
}

function loadInterfaceIssue(issueId) {

    var url = "/api/integration/interface/issue/view/" + issueId;

    $('#systemIntegrationPageContent').load(url);
}

function IntegrationAddEntity(projectId) {
     
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/integration/integration-entity-add.html',
        success: function (data) {
            
            var box = bootbox.confirm({

                closeButton: false,
                title: 'Add Organisational Integration Entity',
                message: data,
                buttons: {
                    cancel: {
                        label: "Cancel",
                        className: "btn-danger btn-fixed-width-100"
                    },
                    confirm: {
                        label: "Ok",
                        className: "btn-success btn-fixed-width-100"
                    }
                },
                callback: function (result) {
                    
                    if (result) {
                        
                        var data = {};

                        data['id'] = projectId;
                        data['itemName'] = document.getElementById('companyId').value;
                        
                        $.ajax({

                            global: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/project/entity/add",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {
                              
                                swal({
                                    title: "Organisational Entity added!",
                                    text: "An organisational entity has been successfully created.",
                                    type: "success"
                                },
                                function () {

                                    window.location.reload();
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
                    url: '/api/projects/companies/byproject/' + projectId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    $(e.currentTarget).find('input[name="projectId"]').prop('value', projectId);
            
                    $(e.currentTarget).find('select[name="companyId"]').empty();

                    $.each(data, function (key, index) {

                        $(e.currentTarget).find('select[name="companyId"]').append('<option value="' + index.company.id + '">' + index.company.companyName + '</option>');
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function IntegrationRootElementCreate(entityId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/integration/integration-element-create-new-root.html',
        success: function (data) {
            
            var box = bootbox.confirm({
                
                closeButton: false,
                message: data,
                title: "Create New Element",
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
                        
                        data['sbs'] = document.getElementById('elementSBS').value;
                        data['name'] = document.getElementById('elementName').value;
                        data['parentId'] = null;
                        data['entityId'] = document.getElementById('entityId').value;
                        data['elementDescription'] = document.getElementById('elementDescription').value;
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/element/create/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    
                                    title: "Success!",
                                    text: "A new interface has been successfully created.",
                                    type: "success"
                                });
                            },
                            error: function (e) {

                                swal({

                                    title: "Error creating interface!",
                                    text: e.responseText,
                                    type: "error"
                                });
                            }
                        });
                    }
                }
            });
            
            box.on("shown.bs.modal", function(e) {
                
                $(e.currentTarget).find('input[name="entityId"]').prop('value', entityId);
            });
            
            box.modal('show');
        }
    });
}

function IntegrationElementCreate(elementId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/integration/integration-element-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({
                
                closeButton: false,
                message: data,
                title: "Create New System Element",
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
                        
                        data['sbs'] = document.getElementById('elementSBS').value;
                        data['name'] = document.getElementById('elementName').value;
                        data['parentId'] = document.getElementById('parentElementId').value;
                        data['entityId'] = document.getElementById('elementEntityId').value;
                        data['elementDescription'] = document.getElementById('elementDescription').value;
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/element/create/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    
                                    title: "Success!",
                                    text: "A new interface has been successfully created.",
                                    type: "success"
                                },
                                function () {

                                    $('sbsNodeId').prop('value', elementId);
                                    zSBSTreeLoad();
                                });
                            },
                            error: function (e) {

                                swal({

                                    title: "Error creating interface!",
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
                    url: "/api/integration/entity/elements/" + elementId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var element = data;
                
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/integration/elements/list",
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        var elements = data;

                        $.each(elements, function (key, index) {

                            $(e.currentTarget).find('select[name="parentElementId"]').append('<option value="' + index.id + '">' + index.sbs + " " + index.name + '</option>');
                        });
                        
                        $(e.currentTarget).find('select[name="parentElementId"]').prop('value', element.id);

                        $.ajax({

                            global: false,
                            type: "GET",
                            contentType: "application/json",
                            url: "/api/integration/entities/list",
                            dataType: "json",
                            cache: false
                        })
                        .done(function (data) {

                            var entities = data;

                            $.each(entities, function (key, index) {

                                $(e.currentTarget).find('select[name="elementEntityId"]').append('<option value="' + index.id + '">' + index.companyCode + '</option>');
                            });

                            $(e.currentTarget).find('select[name="elementEntityId"]').prop('value', element.entity.id);

                            if (elementId !== null) {

//                                onParentElementChange();
                            }
                        });

                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function IntegrationElementEdit(elementId) {
 
    var element;
    var entitiesList;
    var elementsList;
    
    $.ajax({
    
        global: false,
        type: "GET",
        url: '/modals/integration/integration-element-edit.html',
        success: function (data) {
            
            var box = bootbox.confirm({
                
                closeButton: false,
                message: data,
                title: "Edit System Element",
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
                        
                        data['id'] = document.getElementById('elementId').value;
                        data['sbs'] = document.getElementById('elementSBS').value;
                        data['name'] = document.getElementById('elementName').value;
                        data['parentId'] = document.getElementById('parentElementId').value;
                        data['entityId'] = document.getElementById('elementEntityId').value;
                        data['elementDescription'] = document.getElementById('elementDescription').value;
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/element/edit/update",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    
                                    title: "Success!",
                                    text: "Element successfully created.",
                                    type: "success"
                                },
                                function () {

                                    $('sbsNodeId').prop('value', elementId);
                                    zSBSTreeLoad();
                                });
                            },
                            error: function (e) {

                                swal({

                                    title: "Error creating interface!",
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
                        url: "/api/integration/entity/elements/" + elementId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {
                        
                        element = data;
                        
                        $.ajax({

                            global: false,
                            type: "GET",
                            contentType: "application/json",
                            url: "/api/integration/entities/list",
                            dataType: "json",
                            cache: false
                        })
                        .done(function (data) {

                            entitiesList = data;
                            
                            $.each(entitiesList, function (key, index) {

                                $(e.currentTarget).find('select[name="elementEntityId"]').append('<option value="' + index.id + '">' + index.companyCode + '</option>');
                            });
                            $(e.currentTarget).find('select[name="elementEntityId"]').prop('value', element.entity.id);
                            
                            $.ajax({

                                global: false,
                                type: "GET",
                                contentType: "application/json",
                                url: "/api/integration/entity/elements/list/" + element.entity.id,
                                dataType: "json",
                                cache: false
                            })
                            .done(function (data) {

                                elementsList = data;
                                
                                var parent;
                                if (element.parent === null) {
                                    parent = null;
                                } else {
                                    parent = element.parent.id;
                                }
                                
                                $('#parentElementId').empty();
                                
                                $(e.currentTarget).find('select[name="parentElementId"]').append('<option value="null">' + element.entity.company.companyName + '</option>');

                                $.each(elementsList, function (key, index) {

                                     $(e.currentTarget).find('select[name="parentElementId"]').append('<option value="' + index.id + '">' + index.sbs + " " + index.name + '</option>');
                                 });
                        
                                $(e.currentTarget).find('select[name="parentElementId"]').prop('value', parent);
                                $(e.currentTarget).find('input[name="elementSBS"]').prop('value', element.sbs);
                                $(e.currentTarget).find('input[name="elementName"]').prop('value', element.name);
                                $(e.currentTarget).find('textarea[name="elementDescription"]').prop('value', element.description);
                            });
                        });
                    });
            });
            
            box.modal('show'); 
        }
    });   
}

function IntegrationInterfaceCreate(elementId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/integration/integration-interface-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({
                
                closeButton: false,
                message: data,
                title: "Create New Interface",
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
                        
                        data['primaryElementId'] = elementId;
                        data['secondaryElementId'] = document.getElementById('secondaryElementId').value;
                        data['primaryEntityId'] = document.getElementById('primaryEntityId').value;
                        data['secondaryEntityId'] = document.getElementById('secondaryEntityId').value;
                        data['interfaceLevel'] = document.getElementById('interfaceLevel').value;
                        data['interfaceTitle'] = document.getElementById('interfaceTitle').value;
                        data['interfaceDescription'] = document.getElementById('interfaceDescription').value;
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/interfaces/create/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    
                                    title: "Success!",
                                    text: "A new interface has been successfully created.",
                                    type: "success"
                                });
                            },
                            error: function (e) {

                                swal({

                                    title: "Error creating interface!",
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
                    url: "/api/integration/entity/elements/" + elementId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    var primaryElement = data;
            
                    $(e.currentTarget).find('input[name="primaryEntityId"]').prop('value', primaryElement.entity.id);
                    $(e.currentTarget).find('input[name="primaryElementId"]').prop('value', primaryElement.id);
                    
                    $(e.currentTarget).find('input[name="primaryEntityName"]').prop('value', primaryElement.entity.company.companyCode);
                    document.getElementById('primaryEntityName').disabled = true;
            
                    $(e.currentTarget).find('input[name="primaryElementName"]').prop('value', primaryElement.sbs + ' ' + primaryElement.name);
                    document.getElementById('primaryElementName').disabled = true;
                    
                    $('#interfaceLevel').empty();
                    for (i = 1; i < 5; i++) {
                        
                        $('#interfaceLevel').append('<option value="' + i + '">' + "Level " + i + '</option>');
                    }
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/integration/entities/list/all",
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {
                        
                        var entities = data;
                
                        $.each(entities, function (key, index) {

                            $(e.currentTarget).find('select[name="secondaryEntityId"]').append('<option value="' + index.id + '">' + index.companyCode + '</option>');
                        });
                        
                        $(e.currentTarget).find('select[name="secondaryEntityId"]').prop('value', primaryElement.entity.id);
                        
                        onSecondaryEntityChange();
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function IntegrationInterfaceEdit(interfaceId) {
     
    var interface;
    var entities;
    var elements;
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/integration/integration-interface-edit.html',
        success: function (data) {
            
            var box = bootbox.confirm({
                
                closeButton: false,
                message: data,
                title: "Edit System Interface",
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
                        
                        data['id'] = interfaceId;
                        data['primaryEntityId'] = document.getElementById('primaryEntityId').value;
                        data['secondaryEntityId'] = document.getElementById('secondaryEntityId').value;
                        data['primaryElementId'] = document.getElementById('primaryElementId').value;
                        data['secondaryElementId'] = document.getElementById('secondaryElementId').value;
                        data['status'] = document.getElementById('interfaceStatus').value;
                        data['interfaceLevel'] = document.getElementById('interfaceLevel').value;
                        data['interfaceTitle'] = document.getElementById('interfaceTitle').value;
                        data['interfaceDescription'] = document.getElementById('interfaceDescription').value;
                        
                        console.log(data);
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/interfaces/edit/update",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function () {

                                swal({
                                    
                                    title: "Success!",
                                    text: "Interface successfully created.",
                                    type: "success"
                                },
                                function () {
                                    
                                    loadInterfaceDetails(interfaceId);
                                });
                            },
                            error: function (e) {

                                swal({

                                    title: "Error creating interface!",
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
                    url: "/api/integration/interface/" + interfaceId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                    
                    interface = data;
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/integration/entities/list/all",
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {

                        entities = data;

                        $(e.currentTarget).find('select[name="primaryEntityId"]').empty();
                        $.each(entities, function (key, index) {

                            $(e.currentTarget).find('select[name="primaryEntityId"]').append('<option value="' + index.id + '">' + index.companyCode + '</option>');
                        });

                        $(e.currentTarget).find('select[name="secondaryEntityId"]').empty();
                        $.each(entities, function (key, index) {

                            $(e.currentTarget).find('select[name="secondaryEntityId"]').append('<option value="' + index.id + '">' + index.companyCode + '</option>');
                        });
                        
                        $.ajax({

                            global: false,
                            type: "GET",
                            contentType: "application/json",
                            url: '/api/integration/entity/elements/list/' + interface.primaryElement.entity.id,
                            dataType: "json",
                            cache: false
                        })
                        .done(function (data) {

                            var elements = data;

                            $('#primaryElementId').empty();

                            $.each(elements, function (key, index) {

                                $('#primaryElementId').append('<option value="' + index.id + '">' + index.sbs  + " " + index.name + '</option>');
                            });
                            
                            $.ajax({

                                global: false,
                                type: "GET",
                                contentType: "application/json",
                                url: '/api/integration/entity/elements/list/' + interface.secondaryElement.entity.id,
                                dataType: "json",
                                cache: false
                            })
                            .done(function (data) {

                                var elements = data;

                                $('#secondaryElementId').empty();

                                $.each(elements, function (key, index) {

                                    $('#secondaryElementId').append('<option value="' + index.id + '">' + index.sbs  + " " + index.name + '</option>');
                                });
                                
                                $.ajax({

                                    global: false,
                                    type: "GET",
                                    contentType: "application/json",
                                    url: "/api/integration/interface/status/list",
                                    dataType: "json",
                                    cache: false
                                })
                                .done(function (data) {

                                    var statuses = data;
                                    
                                    $(e.currentTarget).find('select[name="interfaceStatus"]').empty();
                                    $.each(statuses, function (key, index) {

                                        $(e.currentTarget).find('select[name="interfaceStatus"]').append('<option value="' + index + '">' + index.replace('INTERFACE_STATUS_', '') + '</option>');
                                    });

                                    $(e.currentTarget).find('select[name="interfaceLevel"]').empty();
                                    for (i = 1; i < 5; i++) {

                                        $(e.currentTarget).find('select[name="interfaceLevel"]').append('<option value="' + i + '">' + "Level " + i + '</option>');
                                    }
                                    
                                    $(e.currentTarget).find('select[name="primaryEntityId"]').prop('value', interface.primaryEntity.id);
                                    $(e.currentTarget).find('select[name="secondaryEntityId"]').prop('value', interface.secondaryEntity.id);
                                    $(e.currentTarget).find('select[name="primaryElementId"]').prop('value', interface.primaryElement.id);
                                    $(e.currentTarget).find('select[name="secondaryElementId"]').prop('value', interface.secondaryElement.id);
                                    $(e.currentTarget).find('textarea[name="interfaceDescription"]').prop('value', interface.interfaceDescription);
                                    $(e.currentTarget).find('select[name="interfaceLevel"]').prop('value', interface.interfaceLevel);
                                    $(e.currentTarget).find('select[name="interfaceStatus"]').prop('value', interface.interfaceStatus);

                                    updateInterfaceTitle();
                                });
                            });
                        });
                     });
                });
            });
   
            box.modal('show');         
        }
    });
}

function IntegrationInterfaceIssueCreate(interfaceId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/integration/integration-issue-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({
                
                closeButton: false,
                message: data,
                title: "Create New Interface Issue",
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
                        
                        data['id'] = document.getElementById('interfaceId').value;
                        data['itemName'] = document.getElementById('issueTitle').value;
                        data['itemDescription'] = $('#summernote').summernote('code').replace('<p><br></p>', '');
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/interfaces/issue/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                    
                                swal({
                                    
                                    title: "Success!",
                                    text: "New issue has been successfully created.",
                                    type: "success"
                                });
                                
                                loadInterfaceDetails(interfaceId);
                            },
                            error: function (e) {

                                swal({

                                    title: "Issue creation error!",
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
                    url: "/api/integration/interface/" + interfaceId,
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    $(e.currentTarget).find('input[name="interfaceId"]').prop('value', interfaceId);
                    $(e.currentTarget).find('input[name="interfaceTitle"]').prop('value', data.interfaceTitle);

                    // Initialize summernote plugin
                    $('.summernote').summernote({
                        height: 180,
                        toolbar: [
                            ['font', ['fontname', 'fontsize', 'style', 'color']],
                            ['style', ['bold', 'italic', 'underline', 'clear']],
                            ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
                            ['codeview', ['codeview']]
                        ]
                    });

                    $(e.currentTarget).find('div[id="summernote"]').summernote();
                    $('#summernote').summernote({ focus: true });
                });
            });
            
            box.modal('show');
        }
    });
}
  
function IntegrationInterfaceIssueCommentCreate(projectId, issueId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/integration/integration-issue-comment-create-new.html',
        success: function (data) {
            
            var box = bootbox.confirm({
                
                closeButton: false,
                message: data,
                title: "Create New Issue Comment",
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
                        
                        data['issueId'] = document.getElementById('issueId').value;
                        data['comment'] = document.getElementById('issueComment').value;
                        data['action'] = document.getElementById('commentAction').value;
                        data['actionById'] = document.getElementById('commentById').value;
                        data['status'] = document.getElementById('issueStatus').value;
                        data['actionBy'] = document.getElementById('actionBy').value;
                        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/interfaces/issue/comment/save",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                    
                                swal({
                                    
                                    title: "Success!",
                                    text: "New comment has been successfully added.",
                                    type: "success"
                                });
                                
                                loadInterfaceIssue(issueId);
                            },
                            error: function (e) {

                                swal({

                                    title: "Comment creation error!",
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
                    url: "/api/integration/interface/status/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {

                    var statuses = data;
            
                    $(e.currentTarget).find('input[name="issueId"]').prop('value', issueId);
                    
                    // Populate Requirement Types SELECT
                    $.each(statuses, function (key, index) {

                        $(e.currentTarget).find('select[name="issueStatus"]').append('<option value="' + index + '">' + index.replace('INTERFACE_STATUS_', '') + '</option>');
                    });
                    
                    $('#commentAction').prop('value', 'Note');

                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/projects/companies/byproject/" + projectId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {
                        
                        var entities = data
                
                        // Deafult Comment Action
                        $(e.currentTarget).find('select[name="actionBy"]').append('<option value="All">All</option>');
                        
                        // Populate Integration Entities SELECT
                        $.each(entities, function (key, index) {

                            $(e.currentTarget).find('select[name="commentById"]').append('<option value="' + index.id + '">' + index.diplayCode + '</option>');
                            $(e.currentTarget).find('select[name="actionBy"]').append('<option value="' + index.diplayCode + '">' + index.diplayCode + '</option>');
                        });
                        
                        // Select default ActionBy
                        $(e.currentTarget).find('select[name="actionBy"]').prop('value', "All");
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function IntegrationInterfaceIssueCommentEdit(projectId, issueId, commentId) {
    
    $.ajax({
        
        global: false,
        type: "GET",
        url: '/modals/integration/integration-issue-comment-edit.html',
        success: function (data) {
            
            var box = bootbox.confirm({
                
                closeButton: false,
                message: data,
                title: "Create New Issue Comment",
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
                        
                        data['commentId'] = document.getElementById('commentId').value;
                        data['comment'] = document.getElementById('issueComment').value;
                        data['action'] = document.getElementById('commentAction').value;
                        data['commentById'] = document.getElementById('commentById').value;
                        data['creationDate'] = document.getElementById('creationDate').value;
                        data['status'] = document.getElementById('issueStatus').value;
                        data['actionBy'] = document.getElementById('actionBy').value;    
        
                        $.ajax({

                            closeButton: false,
                            type: "POST",
                            contentType: "application/json",
                            url: "/api/integration/interfaces/issue/comment/update",
                            data: JSON.stringify(data),
                            dataType: "json",
                            timeout: 60000,
                            success: function (data) {
                    
                                swal({
                                    
                                    title: "Success!",
                                    text: "New comment has been successfully updated.",
                                    type: "success"
                                },
                                function () {

                                    loadInterfaceIssue(issueId);
                                });
                            },
                            error: function (e) {

                                swal({

                                    title: "Comment creation error!",
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
                    url: "/api/integration/interface/status/list",
                    dataType: "json",
                    cache: false
                })
                .done(function (data) {
                
                    var statuses = data;
            
                    $(e.currentTarget).find('input[name="commentId"]').prop('value', commentId);
                    
                    // Populate Requirement Types SELECT
                    $.each(statuses, function (key, index) {

                        $(e.currentTarget).find('select[name="issueStatus"]').append('<option value="' + index + '">' + index.replace('INTERFACE_STATUS_', '') + '</option>');
                    });
                    
                    $.ajax({

                        global: false,
                        type: "GET",
                        contentType: "application/json",
                        url: "/api/projects/companies/byproject/" + projectId,
                        dataType: "json",
                        cache: false
                    })
                    .done(function (data) {
                        
                        var entities = data
                
                        // Deafult Comment Action
                        $(e.currentTarget).find('select[name="actionBy"]').append('<option value="All">All</option>');
                        
                        // Populate Integration Entities SELECT
                        $.each(entities, function (key, index) {

                            $(e.currentTarget).find('select[name="commentById"]').append('<option value="' + index.id + '">' + index.diplayCode + '</option>');
                            $(e.currentTarget).find('select[name="actionBy"]').append('<option value="' + index.diplayCode + '">' + index.diplayCode + '</option>');
                        });
                        
                        $.ajax({

                            global: false,
                            type: "GET",
                            contentType: "application/json",
                            url: "/api/integration/interfaces/issue/commentbyid/" + commentId,
                            dataType: "json",
                            cache: false
                        })
                        .done(function (data) {
                            
                            var comment = data;
                            var date = new Date (comment.creationDate).toISOString();
                            date = date.substring(0, 10);
                            
                            $(e.currentTarget).find('textarea[name="issueComment"]').prop('value', comment.comment);
                            $(e.currentTarget).find('textarea[name="commentAction"]').prop('value', comment.commentAction);
                            $(e.currentTarget).find('input[name="creationDate"]').prop('value', date);
                            $(e.currentTarget).find('select[name="commentById"]').prop('value', comment.commentBy.id);
                            $(e.currentTarget).find('select[name="actionBy"]').prop('value', comment.actionBy);
                            $(e.currentTarget).find('select[name="issueStatus"]').prop('value', comment.status);
                        });
                    });
                });
            });
            
            box.modal('show');
        }
    });
}

function onParentElementChange() {
    
    var parentElementId = document.getElementById('parentElementId').value;
    
    $.ajax({
        
        global: false,
        type: "GET",
        contentType: "application/json",
        url: '/api/integration/element/id/' + parentElementId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        var element = data;

        $('#elementEntityId').prop('value', element.entity.id);
    });
}

function onPrimaryEntityChange() {
    
    var entityId = document.getElementById('primaryEntityId').value;
    
    $.ajax({
        
        global: false,
        type: "GET",
        contentType: "application/json",
        url: '/api/integration/entity/elements/list/' + entityId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
       
        var elements = data;
      
        $('#primaryElementId').empty();
        
        $.each(elements, function (key, index) {

            $('#primaryElementId').append('<option value="' + index.id + '">' + index.sbs  + " " + index.name + '</option>');
        });
    });
}

function onSecondaryEntityChange() {
    
    var entityId = document.getElementById('secondaryEntityId').value;
    
    $.ajax({
        
        global: false,
        type: "GET",
        contentType: "application/json",
        url: '/api/integration/entity/elements/list/' + entityId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
       
        var elements = data;
      
        $('#secondaryElementId').empty();
        
        $.each(elements, function (key, index) {

            $('#secondaryElementId').append('<option value="' + index.id + '">' + index.sbs  + " " + index.name + '</option>');
        });
    });
}

function onPrimaryElementChange() {
    
    updateInterfaceTitle();
}

function onSecondaryElementChange() {
    
    updateInterfaceTitle();
}

function onElementEditEntityChange() {
    
    var entityId = document.getElementById('elementEntityId').value;
    
    $.ajax({
        
        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/integration/entity/elements/list/" + entityId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
       
        var elements = data;

         $.ajax({

            global: false,
            type: "GET",
            contentType: "application/json",
            url: "/api/integration/entity/full/id/" + entityId,
            dataType: "json",
            cache: false
        })
        .done(function (data) {
            
            var entity = data;

            $('#parentElementId').empty();

            $('#parentElementId').append('<option value="null">' + entity.company.companyName + '</option>');

            $.each(elements, function (key, index) {

                $('#parentElementId').append('<option value="' + index.id + '">' + index.sbs  + " " + index.name + '</option>');
            });
        });
    });
}

function updateInterfaceTitle() {
   
    var primaryElementId = document.getElementById('primaryElementId').value;
    var secondaryElementId = document.getElementById('secondaryElementId').value;
    
    $.ajax({
        
        global: false,
        type: "GET",
        contentType: "application/json",
        url: '/api/integration/element/id/' + primaryElementId,
        dataType: "json",
        cache: false
    })
    .done(function (data) {
        
        var primaryElement = data;

            $.ajax({

            global: false,
            type: "GET",
            contentType: "application/json",
            url: '/api/integration/element/id/' + secondaryElementId,
            dataType: "json",
            cache: false
        })
        .done(function (data) {
            
            var secondaryElement = data;
            var title = primaryElement.name + " - " + secondaryElement.name + " Interface";
            
            $('#interfaceTitle').empty();
            $('#interfaceTitle').prop('value', title);
        });
    }); 
}

function updateElementsSortOrder() {
    
    $.ajax({

        global: false,
        type: "GET",
        contentType: "application/json",
        url: "/api/integration/elements/sortorder/update",
        dataType: "text",
        cache: false,
        success: function () {

            swal({

                title: "Success!",
                text: "Elements successfully updated.",
                type: "success"
            });
        },
        error: function (e) {

            swal({

                title: "Error updating elements!",
                text: e.responseText,
                type: "error"
            });
        }
    });
}

function beforeExpand(treeId, treeNode) {
    
    var pNode = curExpandNode ? curExpandNode.getParentNode() : null;
    var treeNodeP = treeNode.parentTId ? treeNode.getParentNode() : null;
    var zTree = $.fn.zTree.getZTreeObj("sbsElementsTree");
    
    for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
        
        if (treeNode !== treeNodeP.children[i]) {
            
            zTree.expandNode(treeNodeP.children[i], false);
        }
    }
    while (pNode) {
        
        if (pNode === treeNode) {
            
            break;
        }
        pNode = pNode.getParentNode();
    }
    if (!pNode) {
        
        singlePath(treeNode);
    }
}

function singlePath(newNode) {
    
    if (newNode === curExpandNode)
        
        return;
    
    var zTree = $.fn.zTree.getZTreeObj("sbsElementsTree"),
            
            rootNodes, tmpRoot, tmpTId, i, j, n;
    
    if (!curExpandNode) {
        
        tmpRoot = newNode;
        
        while (tmpRoot) {
            
            tmpTId = tmpRoot.tId;
            tmpRoot = tmpRoot.getParentNode();
        }
        
        rootNodes = zTree.getNodes();
        
        for (i = 0, j = rootNodes.length; i < j; i++) {
            
            n = rootNodes[i];
            
            if (n.tId !== tmpTId) {
                
                zTree.expandNode(n, false);
            }
        }
    } else if (curExpandNode && curExpandNode.open) {
        
        if (newNode.parentTId === curExpandNode.parentTId) {
            
            zTree.expandNode(curExpandNode, false);
            
        } else {
            
            var newParents = [];
            
            while (newNode) {
                
                newNode = newNode.getParentNode();
                
                if (newNode === curExpandNode) {
                    
                    newParents = null;
                    break;
                    
                } else if (newNode) {
                    
                    newParents.push(newNode);
                }
            }
            if (newParents !== null) {
                
                var oldNode = curExpandNode;
                var oldParents = [];
                
                while (oldNode) {
                    
                    oldNode = oldNode.getParentNode();
                    
                    if (oldNode) {
                        
                        oldParents.push(oldNode);
                    }
                }
                if (newParents.length > 0) {
                    
                    zTree.expandNode(oldParents[Math.abs(oldParents.length - newParents.length) - 1], false);
                    
                } else {
                    
                    zTree.expandNode(oldParents[oldParents.length - 1], false);
                }
            }
        }
    }
    curExpandNode = newNode;
}

function onExpand(event, treeId, treeNode) {
    
    curExpandNode = treeNode;
}
