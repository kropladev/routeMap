<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="orders_panel" class="fl_lpad1">
	<div id="first_tier">
		<div id="order_buttons" class="fl_lpad1">
			<div id="create_order" class="fl_lpad1">
				<a href="#" class="buttonkropla border_radius fl_lpad1">Nowe zlecenie</a>
				<!-- <img src="./images/icons/menu/ic_menu_add.png" alt="Zlecenie" title="Definiuj zlecenie">  -->
			</div>
			<div id="show_route" class="fl_lpad1">
				<a href="#" class="buttonkropla border_radius">Pokaż trasę</a>
			</div>
		</div>
		<div id="order_hiddenOptions" style="float: left;">
			<div id="orders_fields" class="fl_lpad1">
				<div id="order_address"
					style="padding: 1px; height: 20px; margin-top: 5px;">
					<label for="order_address_input" class="fl_lpad1"><span class="icon-asterisk"></span>Adres:</label> 
					<input type="text" id="order_address_input" size='20'
						title="Adres zamówienia" class="input fl_lpad1" data-required />
				</div>
				<div id="order_customer"
					style="padding: 1px; height: 20px; margin-top: 5px;">
					<label for="goal_customer" id="goal_customer_label"
						class="fl_lpad1"><span class="icon-asterisk"></span>Nazwa:</label> <input type="text"
						id="goal_customer" size='20' class="input fl_lpad1" />
				</div>
			</div>

			<div id="options2" class="fl_left">
				<div id="more_order_details" class="fl_lpad1">
					<a href="#"><img
						src="<s:url value="/resources" />/images/icons/menu/ic_menu_add.png"
						title="detale zlecenia" alt="more details" /></a>
				</div>

<%-- 				<div id="search_near_vehicle" class="fl_lpad1">
					<a href="#"><img
						src="<s:url value="/resources" />/images/icons/menu/ic_menu_mapmode.png"
						title="wyszukaj kierowcę" alt="search_near_vehicle" /></a>
				</div> --%>
			</div>
						<div id=" " class="fl_lpad1">
				<div id="search_address" class="orders_fields_extra">
					<a href="#"><img
						<%-- src="<s:url value="/resources" />/images/icons/menu/ic_menu_compass_25_25.png" --%>
						src="<s:url value="/resources" />/images/icons/menu/ic_menu_compass.png"
						title="wyszukaj adres na mapie" alt="search_address" style="margin-top:5px;" /></a>
				</div>
<%-- 				<div id="search_customer" class="orders_fields_extra">
					<a href="#"><img
						src="<s:url value="/resources" />/images/icons/menu/ic_menu_view_25_25.png"
						title="wyszukaj dane klienta w bazie" alt="search_customer" /></a>
				</div> --%>
			</div>
		</div>
		<div id="status_info_container" class="fl_left border_radius">
			<div id="status_info_logo" class="fl_left">
				<div id="status_info_date">19-03-2013</div>
			</div>
			<div id="status_info" class="status_info fl_left">
				<table id="info_table1">
				</table>
			</div>

			<div id="status_info2" class="status_info fl_left">
				<table id="info_table2">
				</table>
			</div>
			
			<div id="status_info3" class="status_info fl_left">
				<table id="info_table3">
				</table>
			</div>
		</div>
		<div id="find_route_container" class="fl_left">
			<div id="find_route_elements" class="fl_left">
				<div id="origin_point" class="points_containers">
					<div id="origin_point_icon" class="point_icons fl_left">
						<img class="point_icons_img"
							src="<s:url value="/resources" />/images/icons/dl6.png"
							style="top: -141px;" alt="pointA" />
					</div>
					<input id="origin_point_input" class="fl_left" />
				</div>
				<div id="goal_point" class="points_containers">
					<div id="goal_point_icon" class="point_icons fl_left">
						<img class="point_icons_img"
							src="<s:url value="/resources" />/images/icons/dl6.png"
							style="top: -72px;" alt="pointB" />
					</div>
					<input id="goal_point_input" class="fl_left" />
				</div>
			</div>
			<div id="find_route_buttons" class="fl_left">
				<div id="trigger_search_route_action" class="fl_left">
					<img
						src="<s:url value="/resources" />/images/icons/menu/ic_menu_compass.png"
						title="pokaż trasę" alt="search_address" />
				</div>
				<div id="trigger_info_route_action" class="fl_left">
					<img
						src="<s:url value="/resources" />/images/ic_menu_info_details_35.png"
						title="opis trasy" alt="search_address" />
				</div>
			</div>
		</div> 
	</div>
	<form:form modelAttribute="orderForm" method="POST" action="makeOrder.htm"> 
	<form:hidden path="orderAddressFrom"/>
	<form:hidden path="orderName"/>
	<form:hidden path="longitudeS"/>
	<form:hidden path="latitudeS"/>
	<form:hidden path="driverName"/>
	<form:hidden path="driverId"/>
	<input type="hidden" id="latLng"/>

	
	<div id="second_tier3" class="border_radius">
	</div>
	<div id="second_tier2" class="border_radius">
		<label>Lista kierowców</label>
		<br/>
		<table id="listOfDriversForOrderTable" >
		</table>
		
	</div>
	<div id="second_tier" class="border_radius">
		<div id="orders_types" class="second_tier_elements">
			<label for="orders_type_select" class="extra_order_options"><span class="icon-asterisk"></span>typ zlecenia:</label> 
				
				<form:select path="orderType" >
				 <form:options items="${ordersList}"  />
				 </form:select>
		</div>

		<div id="goal_address_div" class="second_tier_elements" style="margin-bottom: 20px;">
			<label for="goal_address_input" class="extra_order_options">Adres zlecenia(cel):</label> 
			<form:input path="orderAddressTo" id="goal_address_input" class="input fl_lpad1" />
			<div style="clear: both;"></div>
		</div>

		<div id="orderTimeStartControlGroup" class="second_tier_elements" style="margin-bottom: 20px;">
			<label for="goal_time_input" class="extra_order_options">Godzina:</label> 
			<form:input path="orderTimeStart" id="goal_time_input"/>
			<span class="help-inline"><form:errors path="orderTimeStart"/></span>
			<div style="clear: both;"></div>
		</div>

		<div id="customer_id_div" class="second_tier_elements_customer fl_lpad1">
			<label for="customer_id_input" class="extra_order_options"><span class="icon-asterisk"></span>Klient (nazwa):</label>
			<form:input path="orderCustName" id="customer_id_input" class="input" data-required="true" />
		</div>

		<div id="orderPhoneControlGroup" class="second_tier_elements_customer">
			<label for="customer_phone_input" class="extra_order_options"><span class="icon-asterisk"></span>Telefon(kontakt):</label>
			<form:input path="orderPhone" id="customer_phone_input" class="input" data-required="true" />
			<span class="help-inline"><form:errors path="orderPhone"/></span>
			<div style="clear: both;"></div>
		</div>

		<div style="clear: both;"></div>

		<div id="customer_firstName_div" class="second_tier_elements_customer fl_lpad1">
			<label for="customer_firstName_input" class="extra_order_options">Imię:</label>
			<form:input path="orderCustFirstName" type="text" id="customer_firstName_input" class="input" />
			<div style="clear: both;"></div>
		</div>

		<div id="customer_lastName_div" class="second_tier_elements_customer"
			style="margin-bottom: 20px;">
			<label for="customer_lastName_input" class="extra_order_options">Nazwisko:</label>
			<form:input path="orderCustLastName" type="text" id="customer_lastName_input" class="input" />
			<div style="clear: both;"></div>
		</div>

		<div style="clear: both;"></div>

		<div id="save_order_div" class="fl_left"
			style="margin-left: 20%; margin-bottom: 10px; ">
			<a id="save_order_link" href="#" class="buttonkropla border_radius">Zapisz</a> 
			<!-- onclick="document.getElementById('orderForm').submit();" -->
		</div>
		<div id="clear_order_div" class="fl_left"
			style="margin: 0 10px 10px 10px; ">
			<a href="#" class="buttonkropla border_radius">Nowe</a> 
			<!-- onclick="document.getElementById('orderForm').submit();" -->
		</div>
		 <div id="advance_order_div" class="fl_left"
			style=" margin-bottom: 10px; ">
			<a id="advance_order_link" href="#" class="buttonkropla border_radius">Kierowcy</a> 
			<!-- onclick="document.getElementById('orderForm').submit();" -->
		</div>

	</div>
	</form:form>
	<div id="find_route_description_container" class="border_radius">
		<div id="find_route_description" class="border_radius"></div>
	</div>
</div>
<div id="messagesPanel">
	<table id="messages"></table>
</div>