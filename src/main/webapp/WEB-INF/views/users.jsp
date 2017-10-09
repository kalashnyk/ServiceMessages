<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<spring:url value="/resources/scripts/common_users.js"
    var="common_users_url" />

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

<script src="${common_users_url}" type="text/javascript">
    <jsp:text/>
</script>

<style type="text/css">
.ui-widget {
    font-size: 100%;
}
</style>
<title><spring:message code="label.title_page_users" /></title>
</head>
<body>
    <div id="dialog_msg" name="dialog_msg" style="display: none" title="">
        <div id="msg" name="msg"></div>
    </div>
    <div id="dialog_role" name="dialog_role" style="display: none" title="">
        <center>
            <table border="0" cellspacing="0" cellpadding="0" width="350px"
                id="table_headers2" name="table_headers2">
                <tr>
                    <td align="right"><spring:message code="label.role" />&nbsp;</td>
                    <td><select id="e_role" name="e_role">
                            <option value="ROLE_ADMIN"><spring:message
                                    code="label.administrator" /></option>
                            <option value="ROLE_USER"><spring:message
                                    code="label.user" /></option>
                    </select></td>
                </tr>
                <tr>
                    <td colspan="2" valign="middle" height="30px" style="color: red"></td>
                </tr>
            </table>
        </center>
    </div>
    <div id="dialog_user" name="dialog_user" style="display: none"
        title="<spring:message code="label.adduser"/>">
        <center>
            <table border="0" cellspacing="0" cellpadding="0" width="350px"
                id="table_headers" name="table_headers">
                <tr>
                    <td align="right"><spring:message code="label.fio" />&nbsp;</td>
                    <td><input type="text" size=30 id="n_fio" name="n_fio" /></td>
                </tr>
                <tr>
                    <td align="right"><spring:message code="label.email" />&nbsp;</td>
                    <td><input type="text" size=30 id="n_email" name="n_email" /></td>
                </tr>
                <tr>
                    <td align="right"><spring:message code="label.login" />&nbsp;</td>
                    <td><input type="text" size=30 id="n_login" name="n_login" /></td>
                </tr>
                <tr>
                    <td align="right"><spring:message code="label.password" />&nbsp;</td>
                    <td><input type="password" size=30 id="n_password"
                        name="n_password" /></td>
                </tr>
                <tr>
                    <td align="right"><spring:message code="label.role" />&nbsp;</td>
                    <td><select id="n_role" name="n_role">
                            <option value="ROLE_ADMIN"><spring:message
                                    code="label.administrator" /></option>
                            <option value="ROLE_USER" selected><spring:message
                                    code="label.user" /></option>
                    </select></td>
                </tr>
                <tr>
                    <td colspan="2" valign="middle" height="80px" style="color: red"></td>
                </tr>
            </table>
        </center>
    </div>

    <div style="float: right;">
        <a style="cursor: pointer" onClick="showAddUserDialog();"><spring:message
                code="label.titleadd" /></a>&nbsp;|&nbsp; <a
            href="<c:url value='/messages' />"><spring:message
                code="label.messages" /></a>&nbsp;|&nbsp; <a
            href="<c:url value='/login?logout' />"><spring:message
                code="label.logout" /></a>&nbsp;
    </div>

    <div align="center">
        <h1>
            <spring:message code="label.listusers" />
        </h1>
        <table border="1" cellspacing="0" cellpadding="0" width="800px"
            id="listusers" name="listusers">
            <th><spring:message code="label.nn" /></th>
            <th><a href="users?sort=fio&order=${order}"><spring:message
                        code="label.fio" /></a></th>
            <th><a href="users?sort=email&order=${order}"><spring:message
                        code="label.email" /></a></th>
            <th><a href="users?sort=login&order=${order}"><spring:message
                        code="label.login" /></a></th>
            <th><a href="users?sort=role&order=${order}"><spring:message
                        code="label.role" /></a></th>
            <th><spring:message code="label.actions" /></th>
            <c:forEach var="user" items="${userList}" varStatus="status">
                <tr id="row${user.id}" name="row${user.id}">
                    <!--td>${status.index + 1}</td-->
                    <td>${user.id}</td>
                    <td>${user.fio}</td>
                    <td>${user.email}</td>
                    <td>${user.login}</td>
                    <td id="role${user.id}" name="role${user.id}"><c:if
                            test="${user.role == 'ROLE_ADMIN'}">
                            <spring:message code="label.administrator" />
                        </c:if> <c:if test="${user.role == 'ROLE_USER'}">
                            <spring:message code="label.user" />
                        </c:if></td>
                    <td><a href="<c:url value='/admin/delete/${user.id}' />">
                            <spring:message code="label.deleteuser" />
                    </a> &nbsp;|&nbsp;<a style="cursor: pointer"
                        onClick="showEditUserDialog(${user.id}, '${user.role}');"><spring:message
                                code="label.edituser" /></a></td>

                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
