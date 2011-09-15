<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ubos.yawl.sms.utils.SmsUser"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SMS APP</title>
<style type="text/css">
body {
	margin: 0px;
}

.header {
	border-bottom: solid 1px #666666;
}

.contentHeaderText {
	font-size: 18px;
	font-weight: bold;
}

.logout {
	font-size: 12px;
}

.logout a:link {
	color: #000099;
	text-decoration: none;
}

.logout a:visited {
	color: #000099;
	text-decoration: none;
}

.logout a:hover {
	color: #000099;
	font-size: 13px;
	text-decoration: none;
}

.logout a:active {
	color: #000099;
	text-decoration: none;
}

.contentText {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-weight: bold;
	background: #FFFFFF;
	border-top: solid 1px #666666;
}

.contentText tr#top {
	background: #FFFECF;
}

.contentText a:link {
	color: #000099;
}

.contentText a:visited {
	color: #000099;
}

.contentText a:hover {
	color: #000099;
}

.contentText a:active {
	color: #000099;
}

.contentText th {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	border-bottom: solid 1px #666666;
}

.texfield {
	border: 1px double #666666;
	padding: 5px 5px;
	height: 12px;
	width: 150px;
}

.saveBtn {
	width: 60px;
	height: 30px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	border: solid 1px #000000;
}

.contentSave {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 10px;
	font-weight: bold;
	background: #E5E5E5;
	border: dotted 1px #333333;
	padding: 5px 5px;
}

.saveMsgText {
	width: 140px;
	margin-left: 90px;
	color: #ff0000;
	font-family: "Courier New", Courier, monospace;
	font-size: 14px;
	padding-bottom: 5px;
	padding-left: 5px;
}
.contentText tr#rover{
background-color: #E3E3E1;
}
.contentText tr#rover:HOVER{
background-color: #4EC4D4;
}
</style>
<script type="text/javascript">
function validateSaveFields(username, phoneNo, confirmPhoneNo) {

	if(username.value == "" || phoneNo.value == ""){
		alert('Before saving provide all fields!');
		return false;
		}else{
				return true;
		}
}


function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function confirmDelete(){
        var a = confirm("Are you sure you want to delete");
    if(a==true) return true;
    else return false;
}
</script>
</head>
<body>
<%
	String thisUser = (String) session.getAttribute("USER");
	if (thisUser != null) {
%>
<table width="100%" height="401" border="0" align="center"
	cellpadding="0" cellspacing="0">
	<tr>
		<td width="12%" align="center" valign="middle" class="header"></td>
		<td height="27" colspan="2" align="left" valign="middle"
			class="header">
		<table width="728" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="70" align="left" valign="middle">Welcome:</td>
				<td width="62" align="left" valign="middle"><%=thisUser%></td>
				<td align="left" valign="middle" class="logout">[<a href="login.htm?logout=1"
					title="Logout">logout</a>]</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="19" colspan="3" align="center" valign="bottom">
		<h2>SMS APPLICATION</h2>
		</td>
	</tr>
	<tr>
		<td align="center" valign="bottom">&nbsp;</td>
		<td height="35" align="left" valign="middle" class="contentHeaderText">::
		View ::</td>
		<td height="35" align="left" valign="middle" class="contentHeaderText">
		<div style="float: left;">:: Add ::</div>
		<div class="saveMsgText"><%=session.getAttribute("saveMsg") == null? "": session.getAttribute("saveMsg")%></div>
		</td>
	</tr>
	<tr>
		<td height="293" align="center" valign="middle">&nbsp;</td>
		<td width="49%" align="left" valign="top">
		<%
			List<SmsUser> smsUserList = (List<SmsUser>) session
						.getAttribute("userList");
				if (smsUserList != null) {
		%>
                <form method="post" action="save.htm" onsubmit="return confirmDelete();" >
		<table width="403" height="45" border="0" cellpadding="0"
			cellspacing="0" class="contentText">
			<tr id="top">
				<th width="25" height="22" align="left" valign="middle">No</th>
				<th width="123" height="22" align="left" valign="middle">Username</th>
				<th width="133" height="22" align="left" valign="middle">Password</th>
				<th width="64" align="left" valign="middle"></th>
				<th width="58" align="left" valign="middle"></th>
			</tr>
			<%
				int i = 0;
						for (SmsUser smsUser : smsUserList) {
							i++;
			%>
			<tr id="rover">
				<td height="19" style="padding-left:5px;"><%=i%></td>
				<td><%=smsUser.getUsername()%></td>
				<td><%=smsUser.getPhoneNo()%></td>
				<td><a href="update.htm?username=<%=smsUser.getUsername()%>" title="Update">update</a></td>
                                <td><input type="checkbox" value="<%=smsUser.getUsername()%>" name="phoneUser"/></td>
			</tr>
			<%
				}
			%>
                        <tr><td><input type="submit" name="action" value="delete" /></td></tr>
		</table>
                </form>
		<%
			} else {
		%>
		<table width="410" border="0" cellspacing="0" cellpadding="0"
			class="contentText">
			<tr>
				<td width="81" height="31" align="left" valign="middle"
					bgcolor="#F5F5F5">No records found!</td>
			</tr>
		</table>
		<%
			}
		%>
		</td>
		<td width="39%" align="left" valign="top">
		<form action="save.htm" method="POST" name="savePhoneUser"
			onsubmit="return validateSaveFields(username,phoneNo,confirmPhoneNo)">
		<table width="290" height="139" border="0" cellpadding="0"
			cellspacing="0" class="contentSave">
			<tr>
				<td width="102" height="31" align="center" valign="middle">name<input type="hidden" name="oldusername" value="<%=request.getAttribute("username") == null? "": request.getAttribute("username") %>" /></td>
				<td width="186"><input type="text" name="username"
					class="texfield" value="<%= request.getAttribute("username") == null? "": request.getAttribute("username")  %>"/></td>
			</tr>
			<tr>
				<td height="35" align="center" valign="middle">phoneNo</td>
				<td><input type="text" name="phoneNo" class="texfield" onkeypress="return isNumberKey(event)" maxlength="10" value="<%= request.getAttribute("phone") == null? "": request.getAttribute("phone") %>"/></td>
			</tr>
			<tr>
				<td height="38"></td>
				<td><input type="submit" name="Submit" value="Submit" /></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
	<tr>
		<td align="center" valign="middle">&nbsp;</td>
		<td colspan="2" align="center" valign="middle">&nbsp;</td>
	</tr>
</table>
<%
	} else {
%>
<h2>You must be logged in to access this page</h2>
<a href="index.jsp">Go to login</a>
<%
	}
%>

</body>
</html>