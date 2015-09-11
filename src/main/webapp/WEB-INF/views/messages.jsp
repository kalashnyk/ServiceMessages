<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<spring:url value="/resources/scripts/jquery-1.11.1.js" var="jquery_url" />
<spring:url value="/resources/scripts/jquery-ui-1.10.4.custom.min.js"
	var="jquery_ui_url" />
<spring:url
	value="/resources/styles/custom-theme/jquery-ui-1.10.4.custom.css"
	var="jquery_ui_theme_css" />
<spring:url value="/resources/scripts/common_messages.js"
	var="common_messages_url" />

<link rel="stylesheet" type="text/css" media="screen"
	href="${jquery_ui_theme_css}" />

<spring:url value="/resources/styles/common.css" var="common_css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${common_css}" />

<script src="${jquery_url}" type="text/javascript">
	<jsp:text/>
</script>
<script src="${jquery_ui_url}" type="text/javascript">
	<jsp:text/>
</script>

<script src="${common_messages_url}" type="text/javascript">
	<jsp:text/>
</script>

<style type="text/css">
.ui-widget {
	font-size: 90%;
}
</style>
<title><spring:message code="label.title_page_messages" /></title>
</head>
<body>
	<div id="dialog_view_msg" name="dialog_view_msg" style="display: none"
		title="">
		<div id="view_msg" name="view_msg"></div>
	</div>
	<div id="dialog_msg" name="dialog_msg" style="display: none" title="">
		<div id="msg" name="msg"></div>
	</div>

	<div id="dialog_new_letter" name="dialog_new_letter"
		style="display: none"
		title="<spring:message code="label.newletter" />">
		<table border="0" cellspacing="0" cellpadding="0" width="400px">
			<tr>
				<td><spring:message code="label.whom" /></td>
				<td><input type="hidden" id="nl_whom_id" name="nl_whom_id">
					<input type="hidden" id="nl_whom_login" name="nl_whom_login">
					<input type="text" id="nl_whom_name" name="nl_whom_name" size="60"></td>
			</tr>
			<tr>
				<td><spring:message code="label.subject" /></td>
				<td><input type="text" id="nl_subject" name="nl_subject"
					size="60"></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2"><textarea rows="6" cols="60" id="nl_content"
						name="nl_content"></textarea></td>
			</tr>
		</table>
	</div>

	<div id="dialog_address_book" name="dialog_address_book"
		style="display: none"
		title="<spring:message code="label.addressbook" />">
		<table border="0" cellspacing="0" cellpadding="0" width="400px">
			<tr>
				<td><nobr>
						<input type="text" id="add_contact" name="add_contact" size="60">
						<input type="button" value="Add contact" onClick="addContact();">
					</nobr></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table border="1" cellspacing="0" cellpadding="0" width="500px"
						id="listaddressbook" name="listaddressbook">
						<th><spring:message code="label.fio" /></th>
						<th><spring:message code="label.actions" /></th>
						<c:forEach var="addressbook" items="${addressBookList}"
							varStatus="status">
							<tr id="rowab_${addressbook.addressbook_id}"
								name="rowab_${addressbook.addressbook_id}">
								<td width="350px">${addressbook.contact.fio}</td>
								<td><nobr>
										<input type="button"
											value="<spring:message code="label.delete" />"
											onclick="deleteContact(${addressbook.addressbook_id});">
										<input type="button"
											value="<spring:message code="label.write" />"
											onclick="writeContact(${addressbook.contact.id}, '${addressbook.contact.fio}', '${addressbook.contact.login}');">
									</nobr></td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>

	<div id="dialog_cp" name="dialog_cp" style="display: none"
		title="<spring:message code="label.changepassword" />">
		<center>
			<table border="0" cellspacing="0" cellpadding="0" width="400px"
				id="table_cp" name="table_cp">
				<tr>
					<td align="right"><spring:message code="label.currentpassword" />&nbsp;</td>
					<td><input type="password" size=30 id="cur_password"
						name="cur_password" /></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="label.newpassword" />&nbsp;</td>
					<td><input type="password" size=30 id="new_password"
						name="new_password" /></td>
				</tr>
				<tr>
					<td align="right"><spring:message
							code="label.repeannewpassword" />&nbsp;</td>
					<td><input type="password" size=30 id="new_password2"
						name="new_password2" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="30px" style="color: red"></td>
				</tr>
			</table>
		</center>
	</div>
	<div style="float: right;">
		<a style="cursor: pointer" onClick="showAddressBookDialog();"><spring:message
				code="label.addressbook" /></a> &nbsp;|&nbsp; <a
			style="cursor: pointer" onClick="showChangePasswordDialog();"><spring:message
				code="label.changepassword" /></a>&nbsp;|&nbsp;
		<c:if test="${isAdmin == true}">
			<a href="<c:url value='/admin/users' />"><spring:message
					code="label.users" /></a>&nbsp;|&nbsp; 
				</c:if>
		<a href="<c:url value='/login?logout' />"><spring:message
				code="label.logout" /></a>
	</div>
	<div align="center">
		<h1>
			<spring:message code="label.listmessages" />
		</h1>
		<table border="1" cellspacing="0" cellpadding="0" width="1000px"
			id="listmessages" name="listmessages">
			<th><spring:message code="label.nn" /></th>
			<th><a href="messages?sort=from_user&order=${order}"><spring:message
						code="label.from" /></a></th>
			<c:if test="${isAdmin == true}">
				<th><a href="messages?sort=to_user&order=${order}"><spring:message
							code="label.to" /></a></th>
			</c:if>
			<th><a href="messages?sort=date_time&order=${order}"><spring:message
						code="label.date_time" /></a></th>
			<th><a href="messages?subject=login&order=${order}"><spring:message
						code="label.subject" /></a></th>
			<th><spring:message code="label.actions" /></th>
			<c:forEach var="message" items="${messageList}" varStatus="status">
				<tr id="row${message.id}" name="row${message.id}">
					<!--td>${status.index + 1}</td-->
					<td>${message.id}</td>
					<td>${message.from_user.fio}</td>
					<c:if test="${isAdmin == true}">
						<td>${message.to_user.fio}</td>
					</c:if>
					<td><fmt:formatDate value="${message.date_time}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${message.subject}</td>
					<td><a href="<c:url value='/deletemessage/${message.id}' />">
							<spring:message code="label.deletemessage" />
					</a>&nbsp;|&nbsp; <a style="cursor: pointer"
						onClick="showViewMsgDialog('${message.from_user.login}, <fmt:formatDate value="${message.date_time}"
							pattern="yyyy-MM-dd" />, ${message.subject}', ${message.id});">
							<spring:message code="label.view" />
					</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
