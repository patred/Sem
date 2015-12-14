<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript" src="${it.homepath}/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="${it.staticpath}/js/common.js"></script>
	</head>
	
	<body>
		<div id="header"></div>
		<div id="body"></div>
		<div id="footer">
			<input type="submit" value="Aggiungi" onclick="add();">
			<input type="submit" value="Modifica" onclick="edit();">
			<input type="submit" value="Elimina" onclick="del();">
		</div>
	</body>
</html>