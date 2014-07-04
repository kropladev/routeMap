<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script type="text/javascript"
	src='<s:url value="/resources" />/js/table/jquery.dataTables.js'></script>
<script type="text/javascript"
	src='<s:url value="/resources" />/js/table/shCore.js'></script>


<style type="text/css" title="currentStyle">
@import "<s:url value='/resources' />/styles/demo_table.css";
</style>
<!-- 1- new, 2- negocjacje, 3- przyjete, 4- w realizacji, 5- zakonczone, 6- odrzucone -->
<div id="activeOrdersDiv" class="fl_left">

<div id="messageBox" class="border_radius"></div>

	<div id="ordersListTable_contener">
		<div id="tabs">
		  <ul>
		    <li onclick="setPeriod('today');"><a href="#tabs-1"><img id="calendarToday"src='<s:url value="/resources" />/images/dataTables/calendar/date_link.png' alt="dzisiaj" title="Zlecenia dzisiejsze"/></a></li>
		    <li onclick="setPeriod('yesterday');"><a href="#tabs-1"><img id="calendarYesterday"src='<s:url value="/resources" />/images/dataTables/calendar/date_yesterday.png' alt="wczoraj" title="Zlecenia wczorajsze"/></a></li>
		    <li onclick="setPeriod('last3days');"><a href="#tabs-1"><img id="calendarLast3days"src='<s:url value="/resources" />/images/dataTables/calendar/date_previous3days.png' alt="ostatnie 3 dni" title="Zlecenia z ostatnich 3 dni"/></a></li>
		    <li onclick="setPeriod('lastweek');"><a href="#tabs-1"><img id="calendarLastWeek"src='<s:url value="/resources" />/images/dataTables/calendar/date_previousWeek.png' alt="ostatni tydzien" title="Zlecenia z ostatniego tygodnia"/></a></li>
		    <li onclick="setPeriod('lastmonth');"><a href="#tabs-1"><img id="calendarLastMonth"src='<s:url value="/resources" />/images/dataTables/calendar/date_lastMonth.png' alt="ostatni miesiąc" title="Zlecenia z ostatniego miesiąca"/></a></li>
		    <li onclick="setPeriod('nextweek');"><a href="#tabs-1"><img id="calendarNextWeek"src='<s:url value="/resources" />/images/dataTables/calendar/date_nextWeek.png' alt="przyszły tydzien" title="Zlecenia do wykonania w następnym tygodniu"/></a></li>
		    <li onclick="setPeriod('futureall');"><a href="#tabs-1"><img id="calendarNextAll"src='<s:url value="/resources" />/images/dataTables/calendar/date_nextAll.png' alt="przyszle zlecenia" title="Zlecenia przyszłe"/></a></li>
		    <li onclick="setPeriod('all');"><a href="#tabs-1" ><img id="calendarAll"src='<s:url value="/resources" />/images/dataTables/calendar/calendar2.png' alt="wszystkie zlecenia" title="Wszystkie zlecenia"/></a></li>
		  </ul>
			<div id="tabs-1" style="width: 100%;" class="ex_highlight">
				<!-- <table id="listOfOrdersTable"  class="display"></table> -->
				<table id="example" cellpadding="0" cellspacing="0" border="0" class="display" width="100%"  style="width: 100%;">
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			//getAllOrders('active');
			setPeriod('all');
			$( "#tabs" ).tabs({active:7 });
			//$('#listOfOrdersTable').dataTable();
			/* $('#table_id').dataTable(); */
		});
	</script>
<style type="text/css" title="currentStyle">
@import "<s:url value='/resources' />/styles/shCore.css";
</style>
</div>

