<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript" src="${it.homepath}/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="${it.staticpath}/js/common.js"></script>
		<link type="text/css" rel="stylesheet" href="${it.staticpath}/css/main.css"></link>
	</head>
	
	<body>
		<div>ciao Mondo</div>
		<div>${it.homepath}</div>
		<div>${it.staticpath}</div>
	</body>
</html>