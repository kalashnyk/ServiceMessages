<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="true"%>
<html>
<head>
<title><spring:message code="label.title_page_login" /></title>
<spring:url value="/resources/styles/common.css" var="common_css" />
<link rel="stylesheet" type="text/css" media="screen"
    href="${common_css}" />
</head>
<body>
    <div id="login-box">
        <h3><spring:message code="label.login_text" /></h3>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>
        <form name='loginForm'
            action="<c:url value='/j_spring_security_check' />" method='POST'>

            <table border="0" cellpadding="0" cellspacing="0" width="300px">
                <tr>
                    <td><spring:message code="label.login" />:</td>
                    <td><input type='text' name='username' id='username' size="28"></td>
                </tr>
                <tr>
                    <td><spring:message code="label.password" />:</td>
                    <td><input type='password' name='password' id='password' size="28"/></td>
                </tr>
                <tr>
                    <td colspan='2'>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="right"><input name="submit" id="submit" type="submit"
                        value="<spring:message code="label.login_button" />" />
                        <input name="reset" id="reset" type="reset"
                        value="<spring:message code="label.reset_button" />" /></td>
                </tr>
                <tr>
                    <td colspan='2'>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan='2'><a href="<c:url value='/registration' />"><spring:message
                                code="label.registration" /></a></td>
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />
        </form>
    </div>
</body>
</html>