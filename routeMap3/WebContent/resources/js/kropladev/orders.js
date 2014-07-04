/**
 * gets list of all orders
 */
var period='all';
var status='all';
var vehSelectOptions='';
var statusSelectOptions='';
var editInProgress=0; //0- no edition,1- edition inprogress
var  selectTypesOptions= new Array();
var  selectVehiclesOptions= new Array();
var editingRow=0; 
var editClick=0;
var tempCounter=0;
function setPeriod(periodArg){
	period=periodArg;
	getAllOrders(status);
	prepareVehicleSelect();
	prepareStatusSelect();
}

function prepareStatusSelect(){
	 //var selectString="";
	 console.log("before calling WS");
	//$.getJSON('/routeMap3/GetDicStatuses.htm?statusType=order', function(data) {
	// $.getJSON('http://localhost:8080/routeMapWS/services/PositionWS?method=getDicStatuses&statusType=order', function(data) {
	 $.ajax( {
		 type:'Get',
		 url:serverUrlWS1+'?method=getDicStatuses&statusType=order',
		 dataType: "xml",
		 success:function(xmlData) {
			 var jsonData= $(xmlData).find('getDicStatusesReturn').text();
	         console.log(jsonData);
	         var obj = $.parseJSON(jsonData);
	         //selectTypesOptions=obj.StatusList;
	         $.each(obj.StatusList, function(i, orderObj){       
	        	 statusSelectOptions+='<option value="'+orderObj.statusId+'">'+orderObj.statusDesc+'</option>';
	        	 selectTypesOptions.push(orderObj.statusId,orderObj.statusDesc);
	 		}); 
		 }});
	 
}

function prepareVehicleSelect(/*optionSel*/){
	vehSelectOptions='<option value="empty" selected ></option>';
	$.getJSON('/routeMap3/GetVehicles.htm', function(data) {
		$.each(data.VehicleList, function(i, orderObj){
			console.log(orderObj.name);
			vehSelectOptions+='<option value="'+orderObj.id+'">'+orderObj.name+'</option>';
			selectVehiclesOptions.push((orderObj.id+" ").trim(),orderObj.name);
		});
	});
}



function editRow ( oTable, nRow )
{
	editInProgress=1;
    var aData = oTable.fnGetData(nRow);
    var jqTds = $('>td', nRow);
    var selected=' selected ';
    vehSelectOptions=vehSelectOptions.replace(selected, '');
    statusSelectOptions=statusSelectOptions.replace(selected, '');
    
    if (vehSelectOptions.search(aData[5])>2){
    	var posOfSearchData=vehSelectOptions.search(aData[5])-1;
    	vehSelectOptions=vehSelectOptions.substring(0, posOfSearchData)+" selected "+vehSelectOptions.substring(posOfSearchData);
	}else{
		var posOfEmptyString=vehSelectOptions.search("empty")+6;
		vehSelectOptions=vehSelectOptions.substring(0,posOfEmptyString)+" selected "+vehSelectOptions.substring(posOfEmptyString);
	}
    
    if (statusSelectOptions.search(aData[6])>2){
    	var posOfSearchData=statusSelectOptions.search(aData[6])-1;
    	statusSelectOptions=statusSelectOptions.substring(0,posOfSearchData )+" selected "+statusSelectOptions.substring(posOfSearchData);
	}else{
		statusSelectOptions='<option value="" selected ></option>'+statusSelectOptions;
	}
    
    jqTds[2].innerHTML = '<input type="text" id="order_time_edit" size="18" value="'+aData[2]+'">';
    jqTds[3].innerHTML = '<input type="text" size="13" value="'+aData[3]+'">';
    jqTds[4].innerHTML = '<input type="text" size="26" value="'+aData[4]+'">';
    jqTds[5].innerHTML = '<select>'+vehSelectOptions+'</select>';
    jqTds[6].innerHTML = '<select>'+statusSelectOptions+'</select>';
    jqTds[7].innerHTML = addLinkWithIco('save');//'<a class="edit" href="">zapisz</a>';
    setDatePicker();
}

function saveRow ( oTable, nRow )
{
    var jqInputs = $('input', nRow);
    var jqSelects=$('select', nRow);
    var nTds = $('td', nRow);
    var orderId = $(nTds[1]).text();
  //  alert  (orderId);
    //oTable.fnUpdate( jqInputs[1].value, nRow, 1, false );
    oTable.fnUpdate( jqInputs[0].value, nRow, 2, false );
    oTable.fnUpdate( jqInputs[1].value, nRow, 3, false );
    oTable.fnUpdate( jqInputs[2].value, nRow, 4, false );
    
    var pos=selectVehiclesOptions.indexOf(jqSelects[0].value);
    var vehicleName='';
    if (pos>-1){
    	vehicleName= selectVehiclesOptions[pos+1]; 
    }
    oTable.fnUpdate( vehicleName, nRow, 5, false ); 
    var  position= selectTypesOptions.indexOf(jqSelects[1].value);
    oTable.fnUpdate( selectTypesOptions[position+1], nRow, 6, false );
    oTable.fnUpdate( addLinkWithIco('edit'), nRow, 7, false );
    oTable.fnDraw();
    
    
    $.ajax( { type:'Get',
		 url:serverUrlWS1+'?method=changeOrdersData&orderId='+orderId+'&roaTimeOrderStart='+jqInputs[0].value
		 +'&orderName='+jqInputs[1].value+'&custStreet_from='+jqInputs[2].value+'&driverName='+vehicleName+'&orderStatusId='+jqSelects[1].value
    });
		 
    editInProgress=0;
    
}

function removeRow(nRow){
    var nTds = $('td', nRow);
    var orderId = $(nTds[1]).text();
	 $.ajax( { type:'Get',
		 url:serverUrlWS1+'?method=removeOrder&orderId='+orderId
    });
}

function restoreRow ( oTable, nRow )
{
	var aData = oTable.fnGetData(nRow);
	var jqTds = $('>td', nRow);
	
	for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
		oTable.fnUpdate( aData[i], nRow, i, false );
	}
	
	oTable.fnDraw();
}

function getAllOrders(statusArg) {	
	tempCounter++;
	console.log('period:'+period+ "|status:"+status);
	status=statusArg;
	var oTable;

	$.ajax( { type:'Get',
		 url:serverUrlWS1+'?method=getListOfOrders&status='+statusArg+'&period='+period,
		 dataType: "xml",
		 success:function(xmlData) {
			 var jsonData= $(xmlData).find('getListOfOrdersReturn').text();
	        // console.log(jsonData);
	         var data = $.parseJSON(jsonData);
	//$.getJSON('/routeMap3/GetAllOrders.htm?status='+statusArg+'&period='+period, function(data) {
		objectArrayOrdersList=data.Orders;
		var listOrdersTable=$('#example');
		listOrdersTable.empty();
		//listOrdersTable.addClass("ex_highlight");
		var headRow=createRow();
		headRow=addHeaderCell(headRow, "lp","liczba porządkowa");
		headRow=addHeaderCell(headRow, "id", "Id zlecenia");
		headRow=addHeaderCell(headRow,"czas","Czas rozpoczęcia usługi");
		headRow=addHeaderCell(headRow,"nazwa","nazwa zlecenia");
		headRow=addHeaderCell(headRow,"adres","adres rozpoczęcia zlecenia");
		headRow=addHeaderCell(headRow,"operator","pojazd obsługujący");
		headRow=addHeaderCell(headRow,"status","status zlecenia");
		headRow=addHeaderCell(headRow,"","zmiana danych");
		headRow=addHeaderCell(headRow,"","usunięcie wiersza");
		
		listOrdersTable.append($('<thead></thead>').append(headRow));
		var counterAll=0;
		$.each(data.Orders, function(i, orderObj) {
			counterAll++;
			var tableRow=createRow();
			
			tableRow.hover(
					function(){tableRow.addClass('highlighted');},
					function(){tableRow.removeClass('highlighted');});
			
			
			tableRow.addClass("gradeA");
			tableRow=addTableCell(tableRow, i+1);
			tableRow=addTableCell(tableRow, orderObj.orderId);
			tableRow=addTableCell(tableRow, orderObj.roaTimeOrderStart);
			tableRow=addTableCell(tableRow, orderObj.orderName);
			tableRow=addTableCell(tableRow, orderObj.custStreet_from);
			tableRow=addTableCell(tableRow, orderObj.driverName);
			tableRow=addTableCell(tableRow, orderObj.status);
			tableRow=addTableCellAsLinkWithIco(tableRow,"edit");
			//tableRow=addTableCellAsLink(tableRow, "usuń");
			tableRow=addTableCellAsLinkWithIco(tableRow,"delete");//<img src="resources/images/dataTables/delete" alt="delete"/>
			listOrdersTable.append(tableRow);
			
		});
		
		 oTable= $('#example').dataTable({
			"iDisplayLength": 20,
			"aLengthMenu": [[20, 30, 50, -1], [20, 30, 50, "All"]],
			 "bJQueryUI": true,
			 "bDestroy":true,
		     "sPaginationType": "full_numbers",
		        "oLanguage": {
		           "sUrl": "resources/language/pl_PL.txt"
		        },
		});
		
		var colCounter=1;
		$(function() {
			colCounter = 0;
		    oTable.find('tr:nth-child(1) td').each(function () {
		        if ($(this).attr('colspan')) {
		        	colCounter += +$(this).attr('colspan');
		        } else {
		        	colCounter++;
		        }
		    });
		    console.log(colCounter);
		});
		
		
		$('td', oTable.fnGetNodes()).hover( function() {
		//	console.log(colCounter);
			var iCol = $('td').index(this) % colCounter;
		//	console.log("test"+iCol);
			var nTrs = oTable.fnGetNodes();
			//console.log("test"+nTrs);
			$('td:nth-child('+(iCol+1)+')', nTrs).addClass( 'highlighted' );
		}, function() {
			$('td.highlighted', oTable.fnGetNodes()).removeClass('highlighted');
		} );
		 
		 }
	});

	if (editClick==0){
	$('#example a.delete').click('click', function (e) {
		editClick=editClick+1;
		e.preventDefault();
	     
	    var nRow = $(this).parents('tr')[0];
	    removeRow(nRow);
	    oTable.fnDeleteRow( nRow );
	} );
	
	 var nEditing = null;
	$('#example a.edit').live('click', function (e) {
		editClick=editClick+1;
	    e.preventDefault();
	     
	    /* Get the row as a parent of the link that was clicked on */
	    var nRow = $(this).parents('tr')[0];
	     
	    if ( nEditing !== null && nEditing != nRow ) {
	        /* A different row is being edited - the edit should be cancelled and this row edited */
	        restoreRow( oTable, nEditing );
	        editRow( oTable, nRow );
	        nEditing = nRow;
	    }
	   // else if ( nEditing == nRow && this.innerHTML == "zapisz" ) {
	    else if ( nEditing == nRow && this.innerHTML.indexOf('save')!=-1 ) {
	        /* This row is being edited and should be saved */
	        saveRow( oTable, nEditing );
	        nEditing = null;
	    }
	    else {
	        /* No row currently being edited */
	        editRow( oTable, nRow );
	        nEditing = nRow;
	    }
	});
	
	$('#example td.common').live('click', function () {
		editClick=editClick+1;
		if (editInProgress==0){
			console.log('bleble');
		    var nTds = $('td', this.parentElement);
		    //console.log('CLICKon order:'+nTds.toString());
		    var sId = $(nTds[1]).text();
		    var sTime = $(nTds[2]).text();
		    var sName = $(nTds[3]).text();
		    var sAdress = $(nTds[4]).text();
		    var sOperator = $(nTds[5]).text();
		    var sStatus = $(nTds[6]).text(); 
		    
		    var jsonArray={"arrayElems":[
		           {"label":"Identyfikator:","value":sId},
		           {"label":"Nazwa:","value":sName},
		           {"label":"Czas:","value":sTime},
		           {"label":"Adres rozpoczęcia:","value":sAdress},
		           {"label":"Nazwa pojazdu obsługującego:","value":sOperator},
		           {"label":"Status zlecenia:","value":sStatus}]};
		        showInfoInDetailCustomBox("Szczegóły zlecenia","messageBox",135000,jsonArray,"tableOrderDet",0,0,0);
		}
	});
	}
	
}

function setDatePicker(){
$("#order_time_edit").datetimepicker({hourGrid: 4,
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
}


