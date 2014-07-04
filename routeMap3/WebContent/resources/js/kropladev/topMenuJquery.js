var resourceloggedInIcon='resources/images/icons/menu/logged.png';
var resourceImgInfoDetail='resources/images/icons/menu/ic_menu_info_details.png';
var objectArrayDriverList;
var orderJSON={test:"",address:"", name:"",time:"",type:"",addressGoal:"", customer:"",custFirstName:"", custLastName:"", custPhone:"", driverId:"", driverName:"", orderId:""};
var statPackJSON;

function showStatisticsInDetailBox( timeoutArg){

	var detailBox=$("<div></div>").attr("id","infoBox2").addClass("border_radius").addClass("messageBox");

	$("#second_tier2").after(detailBox);
	detailBox.append($('<div/>').text("Statystyki"));
	var insideTable=$('<table/>').addClass('fl_left').css('margin-left','30px').css('margin-top','15px');;
		insideTable.append($("<tr/>").append($('<th />').text("Pojazdy w bazie")).append($('<th />').text(" "))/*.append($('<th />').text("Zamówienia")).append($('<th />').text(" "))*/);
		insideTable.append($("<tr/>").append($('<td />').text(" -wszyscy:")).append($('<td />').text(statPackJSON.driverVehiclesAll)));
		insideTable.append($("<tr/>").append($('<td />').text(" -aktywni:")).append($('<td />').text(statPackJSON.driversActive)));
		insideTable.append($("<tr/>").append($('<td />').text(" -w trasie:")).append($('<td />').text(statPackJSON.driversOnRoute)));
		insideTable.append($("<tr/>").append($('<td />').text(" -wolni:")).append($('<td />').text(statPackJSON.driversFree)));
		insideTable.append($("<tr/>").append($('<td />').text(" -zarejestrowani:")).append($('<td />').text(statPackJSON.driversRegistered)));
		
		detailBox.append(insideTable);	
		
		
		var insideTable2=$('<table/>').addClass('fl_left').css('margin-left','85px').css('margin-top','15px');
		insideTable2.append($("<tr/>").append($('<th />').text("Zamówienia")).append($('<th />').text(" ")));
		insideTable2.append($("<tr/>").append($('<td />').text(" -wszystkie:")).append($('<td />').text(statPackJSON.ordersAll)));
		insideTable2.append($("<tr/>").append($('<td />').text(" -zakończone:")).append($('<td />').text(statPackJSON.ordersDoneAll)));
		insideTable2.append($("<tr/>").append($('<td />').text(" -aktywne:")).append($('<td />').text(statPackJSON.ordersActive)));
		insideTable2.append($("<tr/>").append($('<td />').text(" -negocjowane:")).append($('<td />').text(statPackJSON.ordersNegoc)));
		insideTable2.append($("<tr/>").append($('<td />').text(" -odrzucone:")).append($('<td />').text(statPackJSON.ordersRejected)));
		insideTable2.append($("<tr/>").append($('<td />').text(" -nowe:")).append($('<td />').text(statPackJSON.ordersToDo)));
		insideTable2.append($("<tr/>").append($('<td />').text(" -dzisiejsze:")).append($('<td />').text(statPackJSON.ordersTodayAll)));
		detailBox.append(insideTable2);	
	/*	startNegotiationButton=$('<div/>').attr('id','start_negotiation_div')
		.append($('<a/>').attr('href','#').addClass('buttonkropla border_radius').text('Wyślij').attr('title','wyślij propozycję kursu do kierowcy'));*/
	/*	detailBox.append(startNegotiationButton);*/
		detailBox.css('height','200px');
		detailBox.css('left','195px');
		detailBox.css('top','75px');
		detailBox.css('width','450px');
	detailBox.fadeIn(1000);
	if(timeoutArg>0){
		setTimeout(function() {
			detailBox.remove();
			infoStatTurnOn=0;
		}, timeoutArg);
	}
	detailBox.click(function(){
		if (detailBox.not(':hidden')){
			detailBox.remove();
			infoStatTurnOn=0;
		}
	});
	
	 console.log('showInfoInDetailBox function end');
}


function refreshStats(){
	//console.log('refreshStats');
	$.getJSON( '/routeMap3/shortstat.htm', function(data) { 
		statPackJSON=data;
		//$.each( data, function(i, statusObj) {
			$('#info_table1').empty();

			$('#info_table1').append($('<tr></tr>').append($('<td></td>').text('Kierowcy:')).append($('<td></td>').text(data.driverVehiclesAll)));
			$('#info_table1').append($('<tr></tr>').append($('<td></td>').text('-w trasie:')).append($('<td></td>').text(data.driversOnRoute)));
			if(data.driversRegistered>0){
				$('#info_table1').append($('<tr></tr>').append($('<td></td>').append($('<a />').attr('href','driversAccount.htm').attr('id','pulseLink').text('-rejestracja:'))).append($('<td></td>').text(data.driversRegistered)));
				$('#pulseLink').pulse({ color : 'red'}, {pulses:-1, duration:4000,interval: 2000,returnDelay:1000 });
			}else{
				$('#info_table1').append($('<tr></tr>').append($('<td></td>').text('-rejestracja:')).append($('<td></td>').text(data.driversRegistered)));
			}
			$('#info_table2').empty();
			$('#info_table2').append($('<tr></tr>').append($('<td></td>').text('Zlecenia:')).append($('<td></td>').text(data.ordersAll)));
			$('#info_table2').append($('<tr></tr>').append($('<td></td>').text('-zakończone:')).append($('<td></td>').text(data.ordersDoneAll)));
			$('#info_table2').append($('<tr></tr>').append($('<td></td>').text('-negocjowane:')).append($('<td></td>').text(data.ordersNegoc)));
			
			$('#info_table3').empty();
			$('#info_table3').append($('<tr></tr>').append($('<td></td>').text(' ')).append($('<td></td>')));
			if(statPackJSON.ordersToDo>0){
				$('#info_table3').append($('<tr></tr>').append($('<td></td>').append($('<a />').attr('href','statistic.htm').attr('id','pulseLink2').text('-niepodjęte:'))).append($('<td></td>').text(statPackJSON.ordersToDo)));
				$('#pulseLink2').pulse({ color : 'red'}, {pulses:-1, duration:4000,interval: 2000,returnDelay:1000 });
			}else{
				$('#info_table3').append($('<tr></tr>').append($('<td></td>').text('-niepodjęte:')).append($('<td></td>').text(statPackJSON.ordersToDo)));
			}
			if(statPackJSON.ordersRejected>0){
				$('#info_table3').append($('<tr></tr>').append($('<td></td>').append($('<a />').attr('href','statistic.htm').attr('id','pulseLink3').text('-odrzucone:'))).append($('<td></td>').text(statPackJSON.ordersRejected)));
				$('#pulseLink3').pulse({ color : 'red'}, {pulses:-1, duration:4000,interval: 2000,returnDelay:1000 });
			}else{
				$('#info_table3').append($('<tr></tr>').append($('<td></td>').text('-odrzucone:')).append($('<td></td>').text(statPackJSON.ordersRejected)));
			}
		//});
	});
}
var infoStatTurnOn=0; //0- off, 1 -on.active
$(document).ready(function() {
	
	setAutorefresh();
	refreshStats();
	
	//enable date time picker functionality
	$('#status_info_logo').click(function(){
		if(infoStatTurnOn==0){
		//showInfoInDetailCustomBox('statystyki','second_tier2',5000, statPackJSON);
		showStatisticsInDetailBox(105000);
		infoStatTurnOn=1;
		}
	});
	

	
	blockButtons();
	//$('#messageBox').hide();
	var widthLabels = 50;
	var widthButton = 50;
	var enableSaveButton=true;
	$('#second_tier3').hide();
	/* ustalenie wartosci najdluzszego elementu button */
	$("a.buttonkropla").each(function() {
		if ($(this).width() > widthButton) {
			widthButton = $(this).width();
		}
	});

	$("a.buttonkropla").each(function() {
		$(this).width(widthButton);
	});

	/* ustalenie wartosci najdluzszego elementu label */
	$("label.fl_lpad1").each(function() {
		if ($(this).width() > widthLabels) {
			widthLabels = $(this).width();
		}
	});

	$("label.fl_lpad1").each(function() {
		$(this).width(widthLabels);
	});

	/* ustalenie wartosci najdluzszego elementu label */
	$("label.extra_order_options").each(function() {
		if ($(this).width() > widthLabels) {
			widthLabels = $(this).width();
		}
	});

	$("label.extra_order_options").each(function() {
		$(this).width(widthLabels);
	});

	/* pokaz/schowaj dodatkowe opcje zlecenia */
	$('#second_tier').hide();
	$('#second_tier2').hide();
	$('#more_order_details').click(function() {
		if ($('#second_tier').is(':hidden')) {
			toggleblockMainButtons('block');
			$('#second_tier').fadeIn('slow');
			enableSaveButton=true;
		} else {
			$('#second_tier').fadeOut('slow');
			toggleblockMainButtons('activate');
		}
	});

	$('#order_hiddenOptions').hide();
	$('#find_route_container').hide();
	// create_order
	$('#create_order').click(function() {
		$('#find_route_container').hide();
		if ($('#order_hiddenOptions').is(':hidden')) {
			$('#status_info_container').hide();/* fadeOut('fast'); */
			$('#order_hiddenOptions').fadeIn(1000);
		} else {
			$('#order_hiddenOptions').hide();/* fadeOut('fast'); */
			$('#status_info_container').fadeIn(1000);
		}
	});
	
	
	$('#show_route').click(function() {
		$("#origin_point_input").val($("#order_address_input").val());
		$('#order_hiddenOptions').hide();
		$('#second_tier').hide();
		if ($('#find_route_container').is(':hidden')) {
			$('#status_info_container').hide();
			$('#find_route_container').fadeIn(1000);
		} else {
			$('#find_route_container').hide();
			$('#status_info_container').fadeIn(1000);
		}
	});
	
	
	
	$('#search_address').click(function() {
		var addressArg = $("#order_address_input").val();
		findAddress(addressArg);
	});
	
	

	$("input.fl_lpad1").each(function() {
		$(this).attr('size', '45');
	});

	/*
	 * alert(screen.width); alert($(window).width());
	 */
	if (screen.width < 1024 || $(window).width() < 1024) {
		$('#status_info2').hide();
	} else {
		$('#status_info2').show();
	}

	returnDate();
	
	$("#trigger_search_route_action").click(function(){
		var addressOrigin = $("#origin_point_input").val();
		var addressGoal = $("#goal_point_input").val();
		searchRoute(addressOrigin,addressGoal);
	});
	
	$('#find_route_description_container').hide();
	$("#trigger_info_route_action").click(function(){
		if ($('#find_route_description_container').is(':hidden')) {
			$('#find_route_description_container').fadeIn(1000);
		}else{
			$('#find_route_description_container').hide();
		}
	});
	
	$('#order_address_input').change(function() {
		//alert($(this).val());
		$('#orderAddressFrom').val($(this).val());
		//alert($('#orderAddressFrom').val());
	});
	$('#order_address_input').focusout(function(){
		var inputValue=$(this).val();
		if (inputValue.length>4){
			unblockButtons();
		}
		//
	});

	$('#goal_customer').change(function() {
		$('#orderName').val($(this).val());
	});
	
	
	$("#clear_order_div").click(function(){
		enableSaveButton=true;
		//$('#save_order_div').unblock(); 
		//$('#save_order_link').unblock(); 
		$('div#second_tier input').val("");
		$('div#orders_fields input').val("");
		//TODO: implementation of clearing all elements values
	});
	
	//advance_order_div
	$("#advance_order_link").click(function(){
		//hideLeftDriverListTable();
		
		
		if ($('#second_tier2').is(':hidden')) {
			hideAllLevelMenu();
			$('#second_tier').hide();
			var result=setOrderInfoShortPanel();
			if (result=='OK'){
				getDriverListForOrder();
				$('#second_tier2').fadeIn(1000);
			}
			$('#second_tier3').fadeIn(1000);
			setOrderJsonValue();
			
		} else {
			$('#second_tier2').hide();
			$('#second_tier').fadeIn(1000);
			//$('#second_tier2').fadeIn(1000);
			//$('#status_info_container').fadeIn(1000);
		}
	});
	
	
	//klikniecie w div Dane zlecenia
	$("#second_tier3").click(function(){
		hideLeftDriverListTable();
		if ($('#second_tier').is(':hidden')) {
			$('#second_tier3').hide();
			if($('#second_tier2').not(':hidden')){
				$('#second_tier2').hide();
			}
			$('#second_tier').fadeIn(1000);
		//	getDriverListForOrder();
		}
	});
	
	$("#save_order_link").click(function(){
		console.log('save button clicked');
		setOrderJsonValue();
		enableSaveButton=true; //DEBUG OPTION
		if (enableSaveButton){
			enableSaveButton=false;
			
			makeorderJson();
		}
		blockSaveButton();
		//$("#orderForm").submit();
	});
	
	$("#customer_id_input").blur(function() { 
		if($("#customer_firstName_input").val()==""){
			$("#customer_firstName_input").val($("#customer_id_input").val());
		} 
	});
	
	$("#customer_lastName_input").blur(function() { 
		if($("#customer_id_input").val()==""){
			$("#customer_id_input").val($("#customer_firstName_input").val()+" "+$("#customer_lastName_input").val());
		} 
	});
	
	
	
	$("select#orderType").find("option[value=1]").attr("selected",true);
	//var testTemp=$("#orderType").find("option#4").val();
	/***
	 * logowanie
	 */
	loginInFunction();
	$('#submitLogin_link').on('click', function() {
		$.unblockUI();
		$('#userData_div').fadeIn(100);
		$('#userData_div').find('label').text($('#login_userName_input').val());
		var leftCssValue=$(window).width();
		var wdthOfTheElem=$('#userData_div').width();
		
		
		$('#img4').attr('src',resourceImgInfoDetail);
	});
	$("#goal_time_input").datetimepicker({hourGrid: 4,
	minuteGrid: 10,
	timeFormat: 'HH:mm',
	currentText: 'Teraz',
	closeText: 'OK',
	dateFormat: 'yy-mm-dd',
	timeText: "Czas",
	hourText: "Godzina",
	minuteText: "minuta",
	monthNames:["styczeń","luty","marzec","kwiecień","maj","czerwiec","lipiec","sierpień","wrzesień","październik","listopad","grudzień"],
	dayNames: ['poniedziałek','wtorek','środa','czwartek','piątek','sobota','niedziela'],
	dayNamesShort: ['pon','wto','śro','czw','pią','sob','niedz'],
	dayNamesMin: ['pn','wt','śr','cz','pt','sb','nd'],
	});
	
});


