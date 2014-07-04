<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function forwardPage(){
	// similar behavior as an HTTP redirect
	window.location.replace("mainPage.htm");

	// similar behavior as clicking on a link
	//window.location.href = "http://stackoverflow.com";
}
</script>
<title>GeoLogistic map service</title>
</head>
<body onload="forwardPage();"> 
	<%-- <jsp:forward page="/mainPage.htm"></jsp:forward> --%>
	<!-- <a href="mainPage.htm">Service issue. Click here to redirect.</a> -->
</body>
</html>