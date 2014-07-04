var example2editInProgress=0;
var  selectAccStatusOptions= new Array();
var selectAccStatusString='';

function getAllDrivers(statusArg) {	
	prepareAccStatusSelect();
	//console.log('period:'+period+ "|status:"+status);
	status=statusArg;
	var oTable;

	$.ajax( { type:'Get',
		 url:serverUrlWS1+'?method=getListOfDrivers',
		 dataType: "xml",
		 success:function(xmlData) {
			 var jsonData= $(xmlData).find('getListOfDriversReturn').text();
	        // console.log(jsonData);
	         var data = $.parseJSON(jsonData);
	         //console.log('AAAAAAAAAAAA');
	        // console.log(data);
		objectArrayOrdersList=data.Drivers;
		var driversListTable=$('#example2');
		driversListTable.empty();
		//driversListTable.addClass("ex_highlight");
		var headRow=createRow();
		headRow=addHeaderCell(headRow, "lp","liczba porządkowa");
		//headRow=addHeaderCell(headRow, "id", "Id kierowcy");
		//headRow=addHeaderCell(headRow, "id", "Id samochodu");
		headRow=addHeaderCell(headRow,"login","Login kierowcy");
		headRow=addHeaderCell(headRow,"imię","Imię kierowcy");
		headRow=addHeaderCell(headRow,"nazwisko","Nazwisko kierowcy");
		headRow=addHeaderCell(headRow,"tel.","Telefon kierowcy");
		headRow=addHeaderCell(headRow,"email","Adres skrzynki elektronicznej");
		headRow=addHeaderCell(headRow,"status","Ostatni zgłoszony status");		
		headRow=addHeaderCell(headRow,"klucz","Klucz do usługi Google");
		headRow=addHeaderCell(headRow,"konto","status konta");
		
		headRow=addHeaderCell(headRow,"","zmiana danych");
		headRow=addHeaderCell(headRow,"","usunięcie wiersza");
		
		driversListTable.append($('<thead></thead>').append(headRow));
		var counterAll=0;
		$.each(data.Drivers, function(i, driverObj) {
			//console.log('LOOP');
			counterAll++;
			var tableRow=createRow();
			
			tableRow.hover(
					function(){tableRow.addClass('highlighted');},
					function(){tableRow.removeClass('highlighted');});
			
			tableRow.addClass("gradeA");
			tableRow=addTableCell(tableRow, i+1);
			tableRow=addTableCell(tableRow, driverObj.rdLogin);//1
			tableRow=addTableCell(tableRow, driverObj.rdFirstName);//2
			tableRow=addTableCell(tableRow, driverObj.rdLastName);//3
			tableRow=addTableCell(tableRow, driverObj.rdaPhone);//4
			tableRow=addTableCell(tableRow, driverObj.rdaMail);//5
			tableRow=addTableCell(tableRow, driverObj.rdsStatDescAdd);//6
			tableRow=addTableCell(tableRow, (driverObj.rdaRegId).substr(0,20)+"...");//7
			tableRow=addTableCell(tableRow, driverObj.rdaStatus);//8
		//	tableRow=addTableCell(tableRow, driverObj.rds_stat_desc_add);
		//	tableRow=addTableCell(tableRow, driverObj.rds_stat_desc_add);
			tableRow=addTableCellAsLinkWithIco(tableRow,"edit");//9
			//tableRow=addTableCellAsLink(tableRow, "usuń");
			tableRow=addTableCellAsLinkWithIco(tableRow,"delete");//<img src="resources/images/dataTables/delete" alt="delete"/>
			tableRow=addTableCell(tableRow, driverObj.rdaId);//11
			tableRow=addTableCell(tableRow, driverObj.rdid);//12
			tableRow=addTableCell(tableRow, driverObj.rvId);//13
			tableRow=addTableCell(tableRow, driverObj.rdaStatusId);//14
			tableRow=addTableCell(tableRow, driverObj.rdsStatusId);//15
			//tableRow=addTableCell(tableRow, driverObj.rdaRegId);//16
		//	tableRow=addTableCell(tableRow, driverObj.rvLastLoginTime);//16
			driversListTable.append(tableRow);
			//console.log('LOOP2');
		});
		
		 oTable= $('#example2').dataTable({
			"iDisplayLength": 20,
			"aLengthMenu": [[20, 30, 50, -1], [20, 30, 50, "All"]],
			 "aoColumnDefs": [ { "sWidth": "30px", "aTargets": [0] },{ "sWidth": "100px", "aTargets": [8] },
			                  { "bSortable": false, "aTargets": [7,9,10] }, 
			                  { "bVisible": false, "aTargets": [11,12,13,14,15] } ],
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
		    //console.log(colCounter);
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

	$('#example2 a.delete').live('click', function (e) {
	    e.preventDefault();
	     
	    var nRow = $(this).parents('tr')[0];
	    example2removeRow(oTable,nRow);
	    oTable.fnDeleteRow( nRow );
	} );
	
	 var nEditing = null;
	$('#example2 a.edit').live('click', function (e) {
	    e.preventDefault();
	     
	    /* Get the row as a parent of the link that was clicked on */
	    var nRow = $(this).parents('tr')[0];
	     
	    if ( nEditing !== null && nEditing != nRow ) {
	        /* A different row is being edited - the edit should be cancelled and this row edited */
	    	example2restoreRow( oTable, nEditing );
	        example2editRow( oTable, nRow );
	        nEditing = nRow;
	    }
	   // else if ( nEditing == nRow && this.innerHTML == "zapisz" ) {
	    else if ( nEditing == nRow && this.innerHTML.indexOf('save')!=-1 ) {
	        /* This row is being edited and should be saved */
	    	example2saveRow( oTable, nEditing );
	        nEditing = null;
	    }
	    else {
	        /* No row currently being edited */
	    	example2editRow( oTable, nRow );
	        nEditing = nRow;
	    }
	});
}

function prepareAccStatusSelect(){
	 $.ajax( {
		 type:'Get',
		 url:serverUrlWS1+'?method=getDicStatuses&statusType=account',
		 dataType: "xml",
		 success:function(xmlData) {
			 var jsonData= $(xmlData).find('getDicStatusesReturn').text();
	         console.log(jsonData);
	         var obj = $.parseJSON(jsonData);
	         //selectTypesOptions=obj.StatusList;
	         $.each(obj.StatusList, function(i, orderObj){       
	        	 selectAccStatusString+='<option value="'+orderObj.statusId+'">'+orderObj.statusDesc+'</option>';
	        	 selectAccStatusOptions.push(orderObj.statusId,orderObj.statusDesc);
	 		}); 
		 }});
}


function example2editRow ( oTable, nRow ){
	example2editInProgress=1;
	//console('edit');
    var aData = oTable.fnGetData(nRow);
    var jqTds = $('>td', nRow);

    if (selectAccStatusString.search(aData[8])>2){
    	var posOfSearchData=selectAccStatusString.search(aData[8])-1;
    	selectAccStatusString=selectAccStatusString.substring(0,posOfSearchData )+" selected "+selectAccStatusString.substring(posOfSearchData);
	}else{
		selectAccStatusString='<option value="" selected ></option>'+selectAccStatusString;
	}
    
    //console.log("editRow2:"+vehSelectOptions);
    jqTds[1].innerHTML = '<input type="text" size="15"  value="'+aData[1]+'">';
    jqTds[2].innerHTML = '<input type="text" size="15" value="'+aData[2]+'">';
    jqTds[3].innerHTML = '<input type="text" size="15" value="'+aData[3]+'">';
    jqTds[4].innerHTML = '<input type="text" size="15" value="'+aData[4]+'">';
    jqTds[5].innerHTML = '<input type="text" size="15" value="'+aData[5]+'">';
    //jqTds[6].innerHTML = '<input type="text" size="13" value="'+aData[6]+'">';
   // jqTds[8].innerHTML = '<input type="text" size="9" value="'+aData[8]+'">';
    jqTds[8].innerHTML = '<select>'+selectAccStatusString+'</select>';

    jqTds[9].innerHTML = addLinkWithIco('save');//'<a class="edit" href="">zapisz</a>';
    console.log('aData[5]:'+aData[13]);
    //setDatePicker();
}

function example2saveRow ( oTable, nRow ){
    var jqInputs = $('input', nRow);
    var jqSelects=$('select', nRow);
   // var nTds = $('td', nRow);
    var nTds= oTable.fnGetData(nRow);
   // var vehicleId = $(nTds[13]);
  //  alert  (orderId);
    //oTable.fnUpdate( jqInputs[1].value, nRow, 1, false );
    oTable.fnUpdate( jqInputs[0].value, nRow, 1, false );
    oTable.fnUpdate( jqInputs[1].value, nRow, 2, false );
    oTable.fnUpdate( jqInputs[2].value, nRow, 3, false );
    oTable.fnUpdate( jqInputs[3].value, nRow, 4, false );
    oTable.fnUpdate( jqInputs[4].value, nRow, 5, false );
    
    var pos=selectAccStatusOptions.indexOf(jqSelects[0].value); 
    oTable.fnUpdate(selectAccStatusOptions[pos+1], nRow, 8, false );
    oTable.fnUpdate( addLinkWithIco('edit'), nRow, 9, false );
    oTable.fnDraw();
       
    $.ajax( { type:'Get',
		 url:serverUrlWS1+'?method=updateDriver&vehicleId='+nTds[13]+'&rdaId='+nTds[11]
		 +'&firstName='+jqInputs[1].value+'&lastName='+jqInputs[2].value+'&login='+jqInputs[0].value+'&mail='+jqInputs[4].value
		 +'&phone='+jqInputs[3].value+'&accStatusId='+jqSelects[0].value
    });
		 
    editInProgress=0;  
}

function example2removeRow(oTable,nRow){
    //var nTds = $('td', nRow);
    var nTds= oTable.fnGetData(nRow);
    //var orderId = $(nTds[13]).text();
	 $.ajax( { type:'Get',
		 url:serverUrlWS1+'?method=removeDriver&vehicleId='+nTds[13]
    });
}

function example2restoreRow ( oTable, nRow ){
	var aData = oTable.fnGetData(nRow);
	var jqTds = $('>td', nRow);	
	for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
		oTable.fnUpdate( aData[i], nRow, i, false );
	}
	oTable.fnDraw();
}

