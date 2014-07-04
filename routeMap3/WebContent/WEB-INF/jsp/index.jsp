<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
 <%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">
<HTML>

<HEAD>
<title>GeoLogistic map service</title>
<link rel="stylesheet" href="<s:url value="/resources" />/styles/layout.css" type="text/css">
<link rel="stylesheet" href="<s:url value="/resources" />/styles/tableSorterStyle.css" type="text/css">
<link rel="stylesheet" href="<s:url value="/resources" />/styles/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="<s:url value="/resources" />/styles/jquery-autocomplete.css" type="text/css">
<link rel="stylesheet" href="<s:url value="/resources" />/styles/topMenu.css" type="text/css">
<link rel="stylesheet" href="<s:url value="/resources" />/styles/leftMenu.css" type="text/css">
<link rel="stylesheet" href="<s:url value="/resources" />/styles/topMenu_navigation.css" type="text/css">
<link rel="stylesheet" href="<s:url value="/resources" />/styles/timepicker.css" type="text/css">

<script type="text/javascript" src='<s:url value="/resources" />/js/core/jquery.min.js'></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/core/jquery.pulse.js'></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/kropladev/jquery.routeMap3.actions.js'></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/kropladev/jquery.routeMap3.utils.js'></script>
<%-- <script type="text/javascript" src='<s:url value="/resources" />/js/jquery-validate.js' charset="utf-8"></script> --%>
<script type="text/javascript" src='<s:url value="/resources" />/js/kropladev/navigationMenu.js' charset="utf-8"></script>
 <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script> 
<script type="text/javascript" src="<s:url value="/resources" />/js/core/jquery.ui.map.js"></script>
 <script type="text/javascript" src="<s:url value="/resources" />/js/other/jquery.blockUI.js"></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/other/jquery-autocomplete.js'></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/table/jquery.tablesorter.js'></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/table/jquery.tablesorter.pager.js'></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/core/jquery-ui.js'></script>

<script type="text/javascript" src='<s:url value="/resources" />/js/other/jquery-ui-timepicker-addon.js'></script>

<script type="text/javascript" src='<s:url value="/resources" />/js/kropladev/leftMenuJquery.js' charset="utf-8"></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/kropladev/topMenuJquery.js' charset="utf-8"></script>
<script type="text/javascript" src="<s:url value="/resources" />/js/core/gmap3.js"></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/kropladev/gmapaction.js' charset="utf-8"></script>
<script type="text/javascript" src='<s:url value="/resources" />/js/kropladev/orders.js' charset="utf-8"></script>
<%-- <script type="text/javascript" src='<s:url value="/resources" />/js/kropladev/drivers.js' charset="utf-8"></script> --%>
</HEAD>
<BODY>
	<div id="header" class="clear">
		<div id="logo_background" class="fl_left">
			<div id="logo" title="GeoLogistic, map routing"></div>
		</div>
		<div id="splitLine1" class="fl_left"></div>
		<tiles:insertAttribute name="navMenu" />
		<tiles:insertAttribute name="extraMenuPanel" />
	</div>
	<!-- content -->
	<div id="container" class="wrapper">
		<hr />
		<tiles:insertAttribute name="leftMenu" />
		<tiles:insertAttribute name="container" />
		
	</div>

	<!-- Footer -->
	<tiles:insertAttribute name="footer" />
</BODY>
</HTML>
