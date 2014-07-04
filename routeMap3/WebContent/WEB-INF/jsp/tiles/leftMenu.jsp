<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="left_menu_container" class="fl_left">
	<div id='toggle'>
		<img src='<s:url value="/resources" />/images/icon_collapse_rtl.gif'
			id='leftMenuExpandCollapseToggle' alt="collapse" />
	</div>
	<div id='leftMenu'>
		<div id='leftMenuAction'>
			<div id='content'></div>
			<div id="driver_list">
				<div id="driver_list_h">Pojazdy</div>

				<div id="pager" class="pager">
					<form action="">
						<img src="<s:url value="/resources" />/images/table/first.png"
							class="first" alt="first" /> <img
							src="<s:url value="/resources" />/images/table/prev.png"
							class="prev" alt="prev" /> <input type="text" class="pagedisplay"
							alt="pageDisplay" /><img
							src="<s:url value="/resources" />/images/table/next.png"
							class="next" alt="next" /> <img
							src="<s:url value="/resources" />/images/table/last.png"
							class="last" alt="last" /> <select class="pagesize">
							<option selected="selected" value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
							<option value="25">25</option>
						</select>
					</form>
				</div>
			</div>
			<!-- <select style="width: 60%;" id="listOfVehicles"></select> -->
		</div>
		<div id='leftMenuAction2'>
			<div id='content2'></div>
			<div id="filterOptions">
				<div id="statusFilter"></div>
				<select id="statusFilterSelect"></select>
			</div>

			<!-- <select style="width: 60%;" id="listOfVehicles"></select> -->
		</div>

		<div id='leftMenuAction3'>
			<div id='content3'></div>
			<div id="driver_list3">
				<div id="driver_list_h3">Lista zleceń</div>
			</div>
			<!-- <select style="width: 60%;" id="listOfVehicles"></select> -->
		</div>
		<div id="test2">test2</div>
	</div>
</div>