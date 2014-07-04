<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="<s:url value="/resources" />/styles/ordersStatisticPage.css" type="text/css">
<div id="left_menu_orders_container" class="fl_left">
<%-- 	<div id='toggle'> 
		<img src='<s:url value="/resources" />/images/icon_collapse_rtl.gif'
			id='leftMenuExpandCollapseToggle' alt="collapse" />
	</div> --%>
	<div id='leftMenuOrders_wrap'>
		<div id='leftMenuAction_orders'>
			<div id='orders_content'></div>
			<div id="orders_list">
				<div id="orders_list_h">Zlecenia</div>
				<div id='menuOrders_contener'>
					<ul class="menuOrders_vertical">
  						<li><a href="#" class="menuOrders_verticalElem" onclick="getAllOrders('active'); setMenuElemActive($(this));"><span>Aktywne</span></a></li>
  						<li><a href="#" class="menuOrders_verticalElem" onclick="getAllOrders('execute');setMenuElemActive($(this));"><span>W trakcie</span></a></li>
  						<li><a href="#" class="menuOrders_verticalElem" onclick="getAllOrders('future');setMenuElemActive($(this));"><span>Zamówione</span></a></li>
  						<li><a href="#" class="menuOrders_verticalElem" onclick="getAllOrders('history');setMenuElemActive($(this));"><span>Archiwum</span></a></li>
  						<li><a href="#" class="active menuOrders_verticalElem" onclick="getAllOrders('all');setMenuElemActive($(this));"><span>Wszystkie</span></a></li>
					</ul>
				</div>
			</div>
			<script type="text/javascript">
				function setMenuElemActive(elem){
					$('.menuOrders_verticalElem').each(function(){
						$(this).removeClass('active');
					});
					$(elem).addClass('active');
				} 
			</script>
			<!-- <select style="width: 60%;" id="listOfVehicles"></select> -->
		</div>
	</div>
</div>