var label_delete = 'Delete';
var label_edit = 'Edit';
var label_add = 'Add';
var label_close = 'Close';
var label_title_add = 'Add user';
var label_title_edit = 'Edit user';

var app_name = 'ServiceMessages-1.0.0-BUILD-SNAPSHOT';
var url_add = "/"+app_name+"/admin/add";
var url_changerole = "/"+app_name+"/admin/changerole";

function clearFields() {
    $("#n_fio").val("");
    $("#n_email").val("");
    $("#n_login").val("");
    $("#n_password").val("");
}

function validateFields() {
    var msg = "";

    $("#msg").text("");

    if (!$("#n_fio").val()) {
        msg = "The field 'FIO' isn't filled<br>";
    }
    if (!$("#n_email").val()) {
        msg += "The field 'Email' isn't filled<br>";
    }
    if (!$("#n_login").val()) {
        msg += "The field 'Login' isn't filled<br>";
    }
    if (!$("#n_password").val()) {
        msg += "The field 'Password' isn't filled";
    }

    $("#table_headers td:last").html(msg);

    if (msg == '') {
        return true;
    }

    return false;
}

function showAddUserDialog() {
    clearFields();

    $(function() {
        $("#dialog_user").dialog({
            title : label_title_add,
            width : 420,
            height : 340,
            resizable : false,
            draggable : true,
            modal : true,
            autoOpen : true,
            position : "center",
            buttons : [ {
                text : label_add,
                click : addUser
            } ]
        });
    });

    $("#dialog_user").dialog("open");
}

function closeUserDialog() {
    $("#dialog_user").dialog("close");
}

function addUser() {
    if (!validateFields()) {
        return;
    }

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var fio = $("#n_fio").val();
    var email = $("#n_email").val();
    var login = $("#n_login").val();
    var password = $("#n_password").val();
    var role = $("#n_role").val();

    var role_text = 'User';
    if (role == 'ROLE_ADMIN') {
        role_text = 'Administrator';
    }

    var json = {
        "fio" : fio,
        "email" : email,
        "login" : login,
        "password" : password,
        "role" : role,
        "enabled" : true
    };

    $.ajax({
        type : "POST",
        url : url_add,
        dataType : 'json',
        data : JSON.stringify(json),
        contentType : 'application/json',
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(user) {
            if (user != 'undefined' && user != null) {

                if (user.error == 1 || user.id == null) {
                    $("#table_headers td:last").html(user.message);
                } else {

                    $('#listusers')
                            .append(
                                    "<tr><td>"
                                            + user.id
                                            + "</td><td>"
                                            + fio
                                            + "</td><td>"
                                            + email
                                            + "</td><td>"
                                            + login
                                            + "</td><td id='role"
                                            + user.id
                                            + "' name='role"
                                            + user.id
                                            + "'>"
                                            + role_text
                                            + "</td><td><a href='deleteuser/"
                                            + user.id
                                            + "'> "
                                            + label_delete
                                            + "</a>&nbsp;|&nbsp;"
                                            + "<a style='cursor: pointer' onClick='showEditUserDialog("
                                            + user.id + ", " + role
                                            + ");'> " + label_edit
                                            + "</a></td></tr>");
                    closeUserDialog();
                }
            }
        },
        error : function(data, status, er) {
            $("#table_headers td:last").html(
                    "Mistake addition of the user");
            showMsgDialog(er, data.responseText);
        }
    });
}
var user_id;
var user_role;

function showEditUserDialog(id, role) {
    $("#e_role option[value='" + role + "']").prop('selected', true);

    user_id = id;

    $(function() {
        $("#dialog_role").dialog({
            title : label_title_edit,
            width : 420,
            height : 200,
            resizable : false,
            draggable : true,
            modal : true,
            autoOpen : true,
            position : "center",
            buttons : [ {
                text : label_edit,
                click : function() {
                    editUser(user_id);
                }
            } ]
        });
    });

    $("#dialog_role").dialog("open");
}

function editUser(id) {
    var role = $("#e_role").val();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    user_role = role;

    var json = {
        "id" : id,
        "role" : role
    };

    $.ajax({
        type : "POST",
        url : url_changerole,
        dataType : 'json',
        data : JSON.stringify(json),
        contentType : 'application/json',
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(user) {
            if (user != 'undefined' && user != null) {
                if (user.error == 1) {
                    $("#table_headers2 td:last").html(user.message);
                } else {
                    closeRoleDialog();
                    var new_role = 'Administrator';
                    if (user_role == 'ROLE_USER') {
                        new_role = 'User';
                    }
                    $('#role' + user_id).html(new_role);
                }
            }
        },
        error : function(data, status, er) {
            $("#table_headers2 td:last").html("Error editing of the user");
            showMsgDialog(er, data.responseText);
        }
    });
}

function closeRoleDialog() {
    $("#dialog_role").dialog("close");
}

function showMsgDialog(title, msg) {

    $("#msg").html(msg);

    $(function() {
        $("#dialog_msg").dialog({
            title : title,
            width : 600,
            height : 400,
            resizable : false,
            draggable : true,
            modal : true,
            autoOpen : true,
            position : "center",
            buttons : [ {
                text : label_close,
                click : function() {
                    $("#msg").html("");
                    $(this).dialog("close");
                }
            } ]
        });
    });

    $("#dialog_msg").dialog("open");
}