<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="<s:url value="/resources" />/styles/ordersStatisticPage.css" type="text/css">
<div id="left_menu_drivers_container" class="fl_left">
	<div id='leftMenuOrders_wrap'>
		<div id='leftMenuAction_orders'>
			<div id='orders_content'></div>
			<div id="orders_list">
				<div id="orders_list_h"></div>
				<div id='menuOrders_contener'>
					<%-- <ul class="menuOrders_vertical">
  						<li><a href="#" class="menuOrders_verticalElem" ><span>Aktywne</span></a></li>
  						<li><a href="#" class="menuOrders_verticalElem" ><span>Rejestracja</span></a></li>
  						<li><a href="#" class="menuOrders_verticalElem" ><span>Zablokowane</span></a></li>
  						<li><a href="#" class="active menuOrders_verticalElem" ><span>Wszystkie</span></a></li>
					</ul> --%>
				</div>
			</div>
			<!-- <select style="width: 60%;" id="listOfVehicles"></select> -->
		</div>
	</div>
</div>