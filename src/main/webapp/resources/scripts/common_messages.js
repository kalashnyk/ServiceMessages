var label_close = 'Close';
var label_change = 'Change';
var label_title_cp = 'Change password';
var label_title_address_book = 'Address book';
var label_write = 'Write';
var title_get_msg_con = "Get message contents";
var title_add = 'Add contact';
var label_send = 'Send';
var title_send = 'Send message';
var label_delete = 'Delete';
var title_delete = 'Delete contact';
var app_name = 'ServiceMessages-1.0.0-BUILD-SNAPSHOT';
var url_changepassword = "/"+app_name+"/changepassword";
var url_getmsgcontents = "/"+app_name+"/getmsgcontents";
var url_addmessage = "/"+app_name+"/addmessage";
var url_addcontact = "/"+app_name+"/addcontact";
var url_deletecontact = "/"+app_name+"/deletecontact";

function clearFields() {
	$("#cur_password").val("");
	$("#new_password").val("");
	$("#new_password2").val("");
}

function validateFields() {
	var msg = "";

    $("#msg").text("");

	if (!$("#cur_password").val()) {
		msg = "The field 'Current password' isn't filled<br>";
	}
	if (!$("#new_password").val()) {
		msg += "The field 'New password' isn't filled<br>";
	}
	if (!$("#new_password2").val()) {
		msg += "The field 'Repeat the new password' isn't filled<br>";
	}

	$("#table_cp td:last").html(msg);

	if (msg === '') return true;

	return false;
}

function showChangePasswordDialog() {
	clearFields();

	$(function() {
		$("#dialog_cp").dialog({
			title : label_title_cp,
			width : 450,
			height : 300,
			resizable : false,
			draggable : true,
			modal : true,
			autoOpen : true,
			position : "center",
			buttons : [ {
				text : label_change,
				click : changePassword
			} ]
		});
	});

	$("#dialog_cp").dialog("open");
}

function closeChangePasswordDialog() {
	$("#dialog_cp").dialog("close");
}

function changePassword() {
	if (!validateFields()) {
		return;
	}

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var cur_password = $("#cur_password").val();
	var new_password = $("#new_password").val();
	var new_password2 = $("#new_password2").val();

	if (new_password != new_password2) {
		$("#table_headers td:last").html('The new password is correct not');
		return;
	}

	var json = {
		"cur_password" : cur_password,
		"new_password" : new_password
	};

	$.ajax({
		type : "POST",
		url : url_changepassword,
		dataType : 'json',
		data : JSON.stringify(json),
		contentType : 'application/json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(user) {
			if (user !== 'undefined' && user !== null) {
				if (user.error === 1 || user.id === null) {
					$("#table_headers td:last").html(user.message);
				} else {
					closeChangePasswordDialog();
				}
			}
		},
		error : function(data, status, er) {
			$("#table_cp td:last").html("Mistake at change of the password");
			showMsgDialog(er, data.responseText);
		}
	});
}

function showMsgDialog(title, msg) {

	$("#msg").html(msg);

	$(function() {
		$("#dialog_msg").dialog({
			title : title,
			width : 700,
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

function showViewMsgDialog(label_title_view, msg_id) {

	$("#view_msg").html("");

	$(function() {
		$("#dialog_view_msg").dialog({
			title : label_title_view,
			width : 700,
			height : 400,
			resizable : false,
			draggable : true,
			modal : true,
			autoOpen : true,
			position : "center",
			buttons : [ {
				text : label_close,
				click : function() {
					closeViewMsgDialog();
				}
			} ]
		});
	});

	$("#dialog_view_msg").dialog("open");

	getMsg(msg_id);
}

function closeViewMsgDialog() {
	$("#dialog_view_msg").dialog("close");
}

function getMsg(msg_id) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var json = {
		"id" : msg_id
	};

	$.ajax({
		type : "POST",
		url : url_getmsgcontents,
		dataType : 'json',
		data : JSON.stringify(json),
		contentType : 'application/json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(msg) {
			if (msg !== 'undefined' && msg !== null) {
				if (msg.error === 1 || msg.id === null) {
					showMsgDialog(title_get_msg_con, msg.message);
				} else {
					$("#view_msg").html(msg.content);
				}
			}
		},
		error : function(data, status, er) {
			showMsgDialog(er, data.responseText);
		}
	});
}

function showAddressBookDialog() {
	$(function() {
		$("#dialog_address_book").dialog({
			title : label_title_address_book,
			width : 700,
			height : 400,
			resizable : false,
			draggable : true,
			modal : true,
			autoOpen : true,
			position : "center",
			buttons : [ {
				text : label_close,
				click : function() {
					closeAddressBookDialog();
				}
			} ]
		});
	});

	$("#dialog_address_book").dialog("open");
}

function closeAddressBookDialog() {
	$("#dialog_address_book").dialog("close");
}

function showNewLetterDialog() {

	$(function() {
		$("#dialog_new_letter").dialog({
			width : 700,
			height : 400,
			resizable : false,
			draggable : true,
			modal : true,
			autoOpen : true,
			position : "center",
			buttons : [ {
				text : label_send,
				click : function() {
					sendNewLetter();
				}
			} ]
		});
	});

	$("#dialog_new_letter").dialog("open");
}

function closeNewLetterDialog() {
	$("#dialog_new_letter").dialog("close");
}

function writeContact(user_id, user_name, user_login) {
	closeAddressBookDialog();

	$("#nl_whom_id").val(user_id);
	$("#nl_whom_name").val(user_name);
	$("#nl_whom_login").val(user_login);

	showNewLetterDialog();
}

function sendNewLetter() {

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var nl_whom_id = $("#nl_whom_id").val();
	var nl_whom_name = $("#nl_whom_name").val();
	var nl_whom_login = $("#nl_whom_login").val();
	var nl_subject = $("#nl_subject").val();
	var nl_content = $("#nl_content").val();

	var json = {
		"to_user" : nl_whom_login,
		"subject" : nl_subject,
		"message" : nl_content
	};

	$.ajax({
		type : "POST",
		url : url_addmessage,
		dataType : 'json',
		data : JSON.stringify(json),
		contentType : 'application/json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(msg) {
			if (msg !== 'undefined' && msg !== null) {
				if (msg.error == 1) {
					showMsgDialog(title_send, msg.message);
				} else {
					closeNewLetterDialog();
					location.reload();
				}
			}
		},
		error : function(data, status, er) {
			showMsgDialog(er, data.responseText);
		}
	});
}

function addContact() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var add_contact = $("#add_contact").val();

	$.ajax({
		type : "POST",
		url : url_addcontact,
		data : "contact=" + add_contact,
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(adbook) {
			if (adbook !== 'undefined' && adbook !== null) {
				if (adbook.error === 1) {
					showMsgDialog(title_add, adbook.message);
				} else {
					$('#listaddressbook').append(
							"<tr id='rowab_" + adbook.id + "' name='rowab_"
									+ adbook.id + "'>"
									+ "<td width='350px'>" + adbook.fio
									+ "</td><td><nobr>"
									+ "<input type='button' value="+label_delete+" "
									+ "onclick='deleteContact("+adbook.id+");'>&nbsp;"
									+ "<input type='button' value="+label_write+" "
									+ "onclick='writeContact("
									+ adbook.user_id + ", \"" + adbook.fio
									+ "\", \"" + adbook.login + "\");'>"
									+ "</nobr></td></tr>");
				}
			}
		},
		error : function(data, status, er) {
			showMsgDialog(er, data.responseText);
		}
	});
}

function deleteContact(id) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$.ajax({
		type : "POST",
		url : url_deletecontact,
		data : "id=" + id,
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(adbook) {
			if (adbook !== 'undefined' && adbook !== null) {
				if (adbook.error === 1) {
					showMsgDialog(title_delete, adbook.message);
				} else {
					$('#rowab_' + id).remove();
				}
			}
		},
		error : function(data, status, er) {
			showMsgDialog(er, data.responseText);
		}
	});
}
