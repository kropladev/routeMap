<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script type="text/javascript"
	src='<s:url value="/resources" />/js/table/jquery.dataTables.js'></script>
<script type="text/javascript"
	src='<s:url value="/resources" />/js/table/shCore.js'></script>
<script type="text/javascript"
	src='<s:url value="/resources" />/js/kropladev/drivers.js'></script>


<style type="text/css" title="currentStyle">
@import "<s:url value='/resources' />/styles/demo_table.css";
</style>
<!-- drivers tablee -->
<div id="driversTableDiv" class="fl_left">

<div id="messageBox" class="border_radius"></div>

	<div id="driversListTable_contener">
		 <div id="tabs">
			<div id="tabs-1" style="width: 100%;" class="ex_highlight">
				<!-- <table id="listOfOrdersTable"  class="display"></table> -->
				<table id="example2" cellpadding="0" cellspacing="0" border="0" class="display" width="100%"  style="width: 100%;">
				</table>
		 	</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			getAllDrivers();
		});
	</script>
<style type="text/css" title="currentStyle">
@import "<s:url value='/resources' />/styles/shCore.css";
@import "<s:url value='/resources' />/styles/driverAccount.css";
</style>
</div>

