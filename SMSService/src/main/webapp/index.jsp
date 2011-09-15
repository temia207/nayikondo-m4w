<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SMS APP: LOGIN</title>
<style type="text/css">
body {
	margin: 0px;
}

.loginTable {
	background: #DEDEDE;
	border: dotted 1px;
	height: 150px;
}

.loginText {
	font-family: "Courier New", Courier, monospace;
	font-size: 12px;
	font-weight: bold;
}

.loginTextField {
	border: 1px double #666666;
	padding: 5px 5px;
	height: 15px;
	width: 200px;
}

.loginBtn {
	width: 60px;
	height: 30px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
}

.errorText {
	color: #ff0000;
	font-family: "Courier New", Courier, monospace;
	font-size: 14px;
	padding-bottom: 5px;
}
</style>
</head>
<body onload="document.loginform.username.focus">
<table width="677" height="283" border="0" align="center"
	cellpadding="0" cellspacing="0">
	<tr>
		<td height="52" align="center" valign="bottom">
		<h1>SMS Login</h1>
		</td>
	</tr>
	<tr>
		<td height="53" align="center" valign="bottom" class="errorText">
		<%
			String msg = (String) request.getAttribute("msg");
			if (msg != null) {
		%> <%=msg%> <%
 	}
 %>
		</td>
	</tr>
	<tr>
		<td align="center" valign="middle">
		<form action="login.htm" method="POST" name="loginform">
		<table class="loginTable" width="425" height="119" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="163" height="28" align="center" valign="middle">&nbsp;</td>
				<td width="262">&nbsp;</td>
			</tr>
			<tr>
				<td height="31" align="center" valign="middle" class="loginText">Username</td>
				<td><input type="text" name="username" class="loginTextField" /></td>
			</tr>
			<tr>
				<td height="30" align="center" valign="middle" class="loginText">PhoneNo</td>
				<td><input type="password" name="phoneNo"
					class="loginTextField" /></td>
			</tr>
			<tr>
				<td align="center" valign="middle"></td>
				<td><input type="submit" name="Submit" value="Login"
					class="loginBtn" /></td>
			</tr>
			<tr>
				<td align="center" valign="middle"></td>
				<td></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
	<tr>
		<td align="center" valign="middle" class="loginText">2011. All
		Rights Reserved.</td>
	</tr>
</table>
</body>
</html>
