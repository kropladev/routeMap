<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<div id="navigationMenu">
	<div id="splitLine"  class="fl_left"></div>
	<ul class="topnav">
		<li><a href="/routeMap3/mainPage.htm"><img id="img1" src="<s:url value="/resources" />/images/icons/menu/ic_menu_home.png" alt="Home" /></a>
		<div id="userData_div" class="border_radius"
				style="display: none; position: absolute; top: 51px;left:0px;  background-color: #444; padding:3px; height: 15px; font-size: 11px;">
				<label>user</label>
			</div></li>
		<%-- <li><a href="/routeMap3/statistic.htm"><img id="img2" src="<s:url value="/resources" />/images/icons/menu/ic_menu_preferences.png" alt="Options" title="Opcje"/></a></li> --%>
		<li><a href="/routeMap3/statistic.htm"><img id="img2" src="<s:url value="/resources" />/images/icons/menu/list_grid3.png" alt="Orders" title="Zamówienia"/></a></li>
		<li><a href="/routeMap3/driversAccount.htm"><img id="img3" src="<s:url value="/resources" />/images/icons/menu/accounts_list.png" alt="Drivers" title="Kierowcy" /></a></li>
		<%-- <li><a id="login_link" href="#"><img id="img4" src="<s:url value="/resources" />/images/icons/menu/loggin.png" alt="Login" title="Logowanie" /></a> --%>
		</li>
	</ul>
</div>

<div id="loginForm" style="display: none">
	<p><label>Username:</label><input id="login_userName_input" type="text" name="demo1" /></p>
	<p><label>Password:</label><input type="text" name="demo1" /></p>
	<div id="submitLogin_div" class="fl_left" style="margin-bottom: 10px;">
		<a id="submitLogin_link" href="#" class="buttonkropla border_radius">Zaloguj</a>
		<a id="submitLogin_back_link" href="#" class="buttonkropla border_radius">Cofnij</a>
	</div>
</div>