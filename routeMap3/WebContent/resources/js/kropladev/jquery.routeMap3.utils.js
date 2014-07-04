/**
 * @author makro
 * @returns table row tag "tr"
 */
var serverUrlWS1="http://kropladev.pl/routeMapWS/services/PositionWS"

function createRow(){
	return $('<tr></tr>'); 
}


/**
 * 
 * @param tableRowArg - table Row which will be returned
 * @param textArg	- text argument of the text node of the new cell
 * @returns table row (with table cell with inserted text node inside - from parameters)
 */
function addTableCell(tableRowArg, textArg){
	return tableRowArg.append($('<td></td>').addClass('common').text(textArg));
}



function addTableCellAsLink(tableRowArg, textArg){
	var actionClass;
	if(textArg=='edytuj'){
		actionClass='edit';
	}else if (textArg=='usuń'){
		actionClass='delete';
	}	
	return tableRowArg.append($('<td></td>').append($('<a />').attr("href","").addClass(actionClass).text(textArg)));
}

function addTableCellAsLinkWithIco(tableRowArg, textArg){
	var actionClass, resource;
	if(textArg=='edit'){
		actionClass='edit';
		resource="resources/images/dataTables/edit.png";
	}else if (textArg=='delete'){
		actionClass='delete';
		resource="resources/images/dataTables/delete_icon.png";
	}	
	return tableRowArg.append($('<td></td>').append($('<a />').attr("href","").addClass(actionClass).append($('<img/>').attr('src',resource).attr('alt',textArg))));
}

function addLinkWithIco(type){
	var resource;
	if(type==='save'){
		resource="resources/images/dataTables/save_icon.png";
	}else if (type==="edit"){
		resource="resources/images/dataTables/edit.png";
	}
	return '<a class="edit" href=""><img src="'+resource+'" alt="'+type+'"/></a>';
}

//<img src="resources/images/dataTables/delete" alt="delete"/>

function addHeaderCell(tableRowArg, textArg,titleArg){
	return tableRowArg.append($("<th></th>").text(textArg).attr("title",titleArg));
}


function showMessageBox(message){
	var messageBox=$("<div></div>").attr("id","messageBox").addClass("border_radius").addClass("messageBox");
	//var messageBox=$('#messageBox');
	$("#second_tier2").after(messageBox);
	messageBox.append($("<div></div>").addClass('fl_left').append($('<img />').attr('src',resourceImgInfoDetail).attr('alt','info_icon')));
	messageBox.append($("<div></div>").addClass('fl_left').append($('<div></div>').html(message.replace("\n", "<br/>")).css("margin-top",messageBox.height()/3)));
	messageBox.fadeIn(1000);
	var height=	$(window).height()/2-messageBox.height()/2;
	var width= $(window).width()/2-messageBox.width()/2;	
	messageBox.css('left',width);
	messageBox.css('top',height);
	setTimeout(function() {
		messageBox.hide();
	}, 10000);
	messageBox.click(function(){
		if (messageBox.not(':hidden')){
			messageBox.hide();
		}
	});
}

function showMessageBox(message, parent){
	var messageBox=$("<div></div>").attr("id","messageBox2").addClass("border_radius").addClass("messageBox");
	//var messageBox=$('#messageBox');
	$("#"+parent).after(messageBox);
	messageBox.append($("<div></div>").addClass('fl_left').append($('<img />').attr('src',resourceImgInfoDetail).attr('alt','info_icon')));
	messageBox.append($("<div></div>").addClass('fl_left').append($('<div></div>').html(message.replace("\n", "<br/>")).css("margin-top",messageBox.height()/3)));
	messageBox.fadeIn(1000);
	var height=	$(window).height()/2-messageBox.height()/2;
	var width= $(window).width()/2-messageBox.width()/2;	
	messageBox.css('left',width);
	messageBox.css('top',height);
	setTimeout(function() {
		messageBox.hide();
	}, 10000);
	messageBox.click(function(){
		if (messageBox.not(':hidden')){
			messageBox.hide();
		}
	});
}


function showInfoInDetailBox(objectArg,idArg, timeoutArg){
	console.log('showInfoInDetailBox function start');
	//console.log('objectArg:'+objectArg);
	console.log('idArg:'+idArg);//Login
	var detailBox=$("<div></div>").attr("id","infoBox").addClass("border_radius").addClass("messageBoxBlock");
	//var messageBox=$('#messageBox');
	$('#listOfDriversForOrderTable').find('tr').off('click','tr');
	$("#second_tier2").after(detailBox);
	var objectInfo=$.grep(objectArg, function(n,i){ return n.vehicleName==idArg;});
	detailBox.append($('<div/>').text("Detale zlecenia"));
	console.log('objectInfo:'+objectInfo);
	var insideTable=$('<table/>').addClass('fl_left');
	
		insideTable.append($("<tr/>").append($('<td />').text("Nazwa zlecenia:")).append($('<td />').addClass("orderDetailC").text(orderJSON.name)));
		insideTable.append($("<tr/>").append($('<td />').text("Typ zlecenia:")).append($('<td />').addClass("orderDetailC").text("["+orderJSON.type+"] "+orderJSON.typeDesc)));
		insideTable.append($("<tr/>").append($('<td />').text("Adres spotk. z klient.:")).append($('<td />').addClass("orderDetailC").text(orderJSON.address)));
		insideTable.append($("<tr/>").append($('<td />').text("Czas spotkania:")).append($('<td />').addClass("orderDetailC").text(orderJSON.time)));
		insideTable.append($("<tr/>").append($('<td />').text(" ")).append($('<td />').addClass("orderDetailC").text(" ")));
		insideTable.append($("<tr/>").append($('<td />').text("Klient:")).append($('<td />').addClass("orderDetailC").text(orderJSON.customer)));
		insideTable.append($("<tr/>").append($('<td />').text(" imię:")).append($('<td />').addClass("orderDetailC").text(orderJSON.custFirstName)));
		insideTable.append($("<tr/>").append($('<td />').text(" nazwisko:")).append($('<td />').addClass("orderDetailC").text(orderJSON.custLastName)));
		insideTable.append($("<tr/>").append($('<td />').text(" telefon:")).append($('<td />').addClass("orderDetailC").text(orderJSON.custPhone)));
		insideTable.append($("<tr/>").append($('<td />').text(" ")).append($('<td />').addClass("orderDetailC").text(" ")));
	
		insideTable.append($("<tr/>").append($('<td />').text("Kierowca:")).append($('<td />').addClass("orderDetailC").text(objectInfo[0].vehicleName)));
		//TODO: zamienic ten dystans na dystans z gmap3
		insideTable.append($("<tr/>").append($('<td />').text("Dystans:")).append($('<td />').addClass("orderDetailC").text(objectInfo[0].distance+" km")));
		
		//TODO dodac butony akcji rozpoczecia negocjacji z klientem
		//insideTable.append(insideRow);
		detailBox.append(insideTable);	
		startNegotiationButton=$('<div/>').attr('id','start_negotiation_div')
		.append($('<a/>').attr('href','#').addClass('buttonkropla border_radius').text('Wyślij').attr('title','wyślij propozycję kursu do kierowcy'));
		var counter=0;
		dummyButton=$('<div/>').attr('id','dummy_div')
		.append($('<a/>').attr('href','#').addClass('buttonkropla border_radius').text('Anuluj').attr('title','Nie wysyłaj propozycji'));
		detailBox.append(startNegotiationButton);
		detailBox.append(dummyButton);
		$('#start_negotiation_div').click(function(){
			counter++;
			
			//TODO implementacja negocjacji z kierowca - makeOrder
			//alert('driver Id '+objectInfo[0].vehicleId);
			var response= sendGcmMessage(orderJSON.orderId,objectInfo[0].vehicleId);
			hideLeftDriverListTable();
			if ($('#second_tier').is(':hidden')) {
				$('#second_tier3').hide();
				if($('#second_tier2').not(':hidden')){
					$('#second_tier2').hide();
				}
				$('#second_tier input').val('');
				$('#order_fields input').val('');
				//$('#second_tier').fadeIn(1000);
				
			//	getDriverListForOrder();
			}
		});
	
	detailBox.fadeIn(1000);
	if(timeoutArg>0){
		setTimeout(function() {
			detailBox.remove();
		}, timeoutArg);
	}
	detailBox.click(function(){
			$.unblockUI({ 
                onUnblock: function(){ detailBox.remove();} 
            });
	});
	
	 console.log('showInfoInDetailBox function end');
}


function showInfoInDetailCustomBox(boxTitle,htmlParentElem, timeoutArg, JsonElems,tableName){
	console.log('showInfoInDetailBox function start');
	var detailBox=$("<div></div>").attr("id","infoBox").addClass("border_radius").addClass("messageBoxBlock");
	
	$("#"+htmlParentElem).append(detailBox);

	detailBox.append($('<div/>').text(boxTitle));
	var insideTable=$('<table/>').addClass('fl_left');
	var statusValue="",vehicleName=""; 
	
	$.each(JsonElems.arrayElems, function(index,elem){
		//console.log(elem.label +" "+elem.value);
		var newRow=$("<tr/>");
		newRow.append($('<td />').text(elem.label)).append($('<td />').text(elem.value));
		
/*		if (tableName=='tableOrderDet'){
			if (elem.label=='Status zlecenia:'){
				statusValue=elem.value;
			}else if (elem.label=='Nazwa pojazdu obsługującego:'){
				vehicleName=elem.value;
			}
		}*/
		if(index%2==1){newRow.addClass("zebraInfoBox");};
		insideTable.append(newRow);
	});
		console.log(insideTable.html());
		detailBox.append(insideTable);	
		optionButton=$('<div/>').attr('id','buttonOK').css('margin-left','0').append($('<a/>').attr('href','#').addClass('buttonkropla border_radius').text('OK').attr('title','Zamknij'));
		optionButton.click(function(){
			$.unblockUI({ onUnblock: function(){ detailBox.remove();} }); });
		
		detailBox.append(optionButton);
		if (tableName=='tableOrderDet'){
			var orderId= JsonElems.arrayElems[0].value;
			vehicleName= JsonElems.arrayElems[4].value;
			statusValue= JsonElems.arrayElems[5].value;
			if (vehicleName.length>1 && (statusValue=='Nowe'||statusValue=='Negocjacje')){
				optionButton2=$('<div/>').attr('id','buttonNegoc').css('margin-left','0').append($('<a/>').attr('href','#').addClass('buttonkropla border_radius').text('Wyślij zlecenie do kierowcy').attr('title','Wyślij propozycję do kierowcy'));
				var pos=selectVehiclesOptions.indexOf(vehicleName);
				var vehicleId=selectVehiclesOptions[pos-1];
				optionButton2.click(function(){
					
					var response= sendGcmMessage(orderId,vehicleId);
					
					$.unblockUI({ onUnblock: function(){ detailBox.remove();} });
					
					});
				detailBox.append(optionButton2);
				
				//console.log('dane do wywoalania f-cji:vehicleId'+vehicleId+'|orderId:'+orderId);
			}	
		}
		var counter=0;
		//$('#infoBox').css('top',top).css('left',left).css('right',right);
	// $.blockUI.defaults.css = {};
	 $.blockUI({ message: $('#infoBox'),
	 		onBlock: function() { $(".blockPage").addClass("alertBox").addClass("border_radius");}  });
	 
	 }

/**
 * wyslanie propozycji zlecenia do kierowcy
 * @param orderId
 * @param vehicleId
 */
function sendGcmMessage(orderId,vehicleId){
	//http://localhost:8080/routeMap3/gcmsendmsg.htm?orderId=89&vehicleId=52
	 $.ajax( {
		 type:'Get',
		 url:'/routeMap3/gcmsendmsg.htm?orderId='+orderId+'&vehicleId='+vehicleId,
		 success:function(confirmation) {
			 var infoResult;
				//console.log('response:'+response);
				if (confirmation=='OK'){
					infoResult="Propozycja zlecenia id ["+orderId+"] została poprawnie przesłana do kierowcy id "+vehicleId;
				}else {
					infoResult="Wystąpił problem z przesyłaniempropozycji zlecenia do kierowcy";
				}
				showMessageBox(infoResult,'messageBox');
		 }});
}



function returnDate() {
	var d = new Date();
	var weekday = new Array(7);
	weekday[0] = "niedziela";
	weekday[1] = "poniedziałek";
	weekday[2] = "wtorek";
	weekday[3] = "środa";
	weekday[4] = "czwartek";
	weekday[5] = "piątek";
	weekday[6] = "sobotą";
	var month = d.getMonth() + 1;
	month = checkTime(month);
	var hour = d.getHours();
	hour = checkTime(hour);
	var min = d.getMinutes();
	min = checkTime(min);
	var sec = d.getSeconds();
	sec = checkTime(sec);
	var data = d.getDate() + "-" + (month) + "-" + (1900 + d.getYear()) + " ";
	$('#status_info_date').text(
			data + weekday[d.getDay()] + " " + hour + ":" + min + ":" + sec);
	setTimeout(function() {
		returnDate();
	}, 500);
}

function formatDate(){
	var d = new Date();
	//d.
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}

function blockButtons(){
	$('#save_order_div').block({ message: null,onBlock: function() { 
		$(".blockUI").addClass("border_radius").css("background-color","#444");
	}}); 
	$('#advance_order_div').block({ message: null,onBlock: function() { 
		$(".blockUI").addClass("border_radius").css("background-color","#444");
	}});
	$('#save_order_link').block({ message: null ,onBlock: function() { 
		$(".blockUI").addClass("border_radius").addClass("blocked_button");
	}  });
	$('#advance_order_link').block({ message: null ,onBlock: function() { 
		$(".blockUI").addClass("border_radius").addClass("blocked_button");
	}  });
}

function blockSaveButton(){
	$('#save_order_div').block({ message: null,onBlock: function() { 
		$(".blockUI").addClass("border_radius").css("background-color","#444");
	}}); 

	$('#save_order_link').block({ message: null ,onBlock: function() { 
		$(".blockUI").addClass("border_radius").addClass("blocked_button");
	}  });
}

function toggleblockMainButtons(action){
	if (action=='block'){
		$('#order_buttons').block({ message: null,onBlock: function() { 
			$(".blockUI").addClass("border_radius").css("background-color","#444");
		}}); 
	}else if (action=='activate'){
		$('#order_buttons').unblock();
	}
}
		
function unblockButtons(){
	$('#save_order_div').unblock(); 
	$('#advance_order_div').unblock(); 
	$('#save_order_link').unblock(); 
	$('#advance_order_link').unblock(); 
}

/**
 * wywołana dwa razy - przy naciśnięciu button 'zapisz'(save_order_div.click) i 'kierowcy' (advance_order_div.click)
 */
function setOrderJsonValue(){
	orderJSON.address		=$('#order_address_input').val();
	orderJSON.addressGoal	=$('#goal_address_input').val();
	orderJSON.custFirstName	=$('#customer_firstName_input').val();
	orderJSON.custLastName	=$('#customer_lastName_input').val();
	orderJSON.customer		=$('#customer_id_input').val();
	orderJSON.custPhone		=$('#customer_phone_input').val();
	orderJSON.name			=$('#goal_customer').val();
	orderJSON.time			=$('#goal_time_input').val();
	orderJSON.type			=$('#orderType').val();
	orderJSON.typeDesc			=$('#orderType option:selected').text();
}

function loginInFunction(){
	$('#login_link').on('click',function(){
		 $.blockUI({ message: $('#loginForm') });
	});
}

function setCoordinatesOfOrder(){
	var latlon=$('#latLng').val();
	//alert ("latLon"+ latlon);
	var arrayCoordinate= latlon.split('|');
	var lat=arrayCoordinate[0];
	var lon=arrayCoordinate[1];
	//alert("setCoordinates() " + lat + "  "+lon);
	$('#longitudeS').val(lon);
	$('#latitudeS').val(lat);
}

function setOrderInfoShortPanel(){
	var divOrderInfoShort=$('#second_tier3');
	divOrderInfoShort.empty();
	var emptyValue="<brak>";
	var result='OK';
	
	var infoTable=($("<table/>"));
	if (orderJSON.address==''){
		infoTable.append($("<tr/>").addClass("orderDetailA").append($('<td />').text("Zlecenie nie zostało zapisane.")));
		infoTable.append($("<tr/>").addClass("orderDetailA").append($('<td />').text(" Wyłącz to okno (kliknij w nie) i zapisz zlecenie")));
		infoTable.append($("<tr/>").addClass("orderDetailA").append($('<td />').text("   lub ponownie wybierz opcję 'Kierowcy'")));
		result= 'ERROR';
	}else{
		divOrderInfoShort.append($("<h4 />").text("Dane zlecenia"));
		divOrderInfoShort.append($("<h7 />").text("(wybierz kierowcę z listy po lewej)"));
		var orName=orderJSON.name, orType=orderJSON.typeDesc,orCust=orderJSON.customer, orAdd=orderJSON.address,orTime=orderJSON.time ;
		if (orName==""){orName=emptyValue;}
		if (orType==""){orType=emptyValue;}
		if (orCust==""){orCust=emptyValue;}
		if (orAdd==""){orAdd=emptyValue;}
		if (orTime==""){orTime=emptyValue;}

		infoTable.append($("<tr/>").append($('<td />').text("Nazwa zlecenia:")).append($('<td />').addClass("orderDetailC").text(orName)).append($('<td />').text("Typ zlecenia:").css("padding-left","10px")).append($('<td />').addClass("orderDetailC").text(orType)));
		infoTable.append($("<tr/>").append($('<td />').text("Klient:")).append($('<td />').addClass("orderDetailC").text(orCust)).append($('<td />').text("Adres:").css("padding-left","10px")).append($('<td />').addClass("orderDetailC").text(orAdd)));
		infoTable.append($("<tr/>").append($('<td />').text("Czas spotkania:")).append($('<td />').addClass("orderDetailC").text(orTime)));
	}
	divOrderInfoShort.append(infoTable);
	//$("div#second_tier3 td").css("padding-left","5px");
	return result;
}

