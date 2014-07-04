
/**
 * used by $("#save_order_link").click() inside topMenuJquery.js
 */
function makeorderJson(){
	$.post( '/routeMap3/makeOrder.htm',$('#orderForm').serialize(), function(response) { 
		/*$('#customer_id_input').val('test');*/
		var test1=$('#order_address_input').val();
		var test2=$('#goal_customer').val();
		//console.log(' *order_address_input:'+test1);
		//console.log(' *goal_customer(Name):'+test2);
		//console.log('makeorderJson triggered');
		console.log('ORDER ID ::'+response);
		
		$('#orderAddressFrom').val(test1);
		$('#orderName').val(test2);
		orderJSON.orderId=response;
		/*if (response=='0'){
			for (var i = 0; i < response.result.length; i++) {
				var item = response.result[i];
				var $controlGroup = $('#' + item.fieldName + 'ControlGroup');
				//$controlGroup.addClass('error');
				$controlGroup.find('.help-inline').html(item.message);
			}
		}*/
		//goal_customer,order_address_input
		/*$('#statusFilterSelect').append($('<option></option>').attr('value','').text('wszystkie'));
		$.each( data.StatusList, function(i, statusObj) {
			$('#statusFilterSelect')
				.append($('<option></option>').attr('value',statusObj.statusId).text(statusObj.statusDescAdd)								
				);
		});*/
	},'json');
	
}


/**
 * gets list of all active drivers with distance to the goal position of the order 
 * if order is null controller returns 0 and on the view it is show as  '--' symbol
 */
function getDriverListForOrder() {	
	$.getJSON('/routeMap3/GetListOfDriversForOrder.htm?position='+$('#latLng').val(), function(data) {
		objectArrayDriverList=data.DriverList;
		var listDriversTable=$('#listOfDriversForOrderTable');
		listDriversTable.empty();
		listDriversTable.addClass("tablesorter");
		var headRow=createRow();
		headRow=addHeaderCell(headRow, "L","liczba porządkowa");
		headRow=addHeaderCell(headRow, "Nazwa", "Nazwa taksówki");
		headRow=addHeaderCell(headRow,"Dyst.linia","Odległość do celu");
		headRow=addHeaderCell(headRow,"Do celu","Dokładna");
		listDriversTable.append($('<thead></thead>').append(headRow));
		
		var counterGoodDist=0;
		var counterAll=0;
		$.each(data.DriverList, function(i, driverObj) {
			var distArg="--";
			var distAccurate='--';
			if (driverObj.distance!=0){
				driverObj.distance=(driverObj.distance/1000).toFixed(1);
				distArg= driverObj.distance+" km";
				counterGoodDist++;
			}
			counterAll++;
			var tableRow=createRow();
			//blokuj cały ekran
			
			
			
			tableRow.click(function(){
				orderJSON.driverId=driverObj.vehicleId;
				orderJSON.driverName=driverObj.vehicleName;
				$('#driverName').val(orderJSON.driverName);
				$('#driverId').val(orderJSON.driverId);
				showInfoInDetailBox(objectArrayDriverList,orderJSON.driverName,0);
				 $.blockUI({ message: $('#infoBox'), 
					 		onBlock: function() { 
					 				$(".blockPage").addClass("border_radius");
					 			}  
				 });  //unblock w definicji funkcji: detailBox.click
			});
			
/*			$("#map_canvas").gmap3({
				  getdistance:{
				    options:{ 
				      origin:[$('#latLng').val().split(":")[0],$('#latLng').val().split(":")[1]], 
				      destination:[driverObj.latitude, driverObj.longitude],
				      travelMode: google.maps.TravelMode.DRIVING
				    },
				    callback: function(results, status){
				     // var html = "";
				      if (results){
				        for (var i = 0; i < results.rows.length; i++){
				          var elements = results.rows[i].elements;
				          for(var j=0; j<elements.length; j++){
				            switch(elements[j].status){
				              case "OK":
				            	  distAccurate += elements[j].distance.text + " (" + elements[j].duration.text + ")";
				                break;
				            }
				          }
				        } 
				      } 
				    }
				  }
				});*/
			
			
			tableRow.hover(
					function(){
						tableRow.addClass('tableSorterMouseAt'); 
						// latLng: [48.8620722, 2.352047]
						$("#map_canvas").gmap3({ 
							  getroute:{
							    options:{
							        origin:[$('#latLng').val().split(":")[0],$('#latLng').val().split(":")[1]],
							        destination:[driverObj.latitude, driverObj.longitude],
							        travelMode: google.maps.DirectionsTravelMode.DRIVING
							    },
							    callback: function(results){
							      if (!results) return;
							      $(this).gmap3({
							        directionsrenderer:{
							          options:{
							            directions:results
							          } 
							        }
							      });
							    }
							  } 	
							});
					},
					function(){tableRow.removeClass('tableSorterMouseAt');
					var todo = { clear:'directionrenderer' };
						$("#map_canvas").gmap3(todo);
					}
			);
			tableRow=addTableCell(tableRow, i+1);
			tableRow=addTableCell(tableRow, driverObj.vehicleName);
			tableRow=addTableCell(tableRow,distArg);
			tableRow=addTableCell(tableRow,distAccurate);
			listDriversTable.append(tableRow);
			
		});
		//check if data of distance is ok
		if (counterGoodDist==0){
			showMessageBox("Brak wskazania miejsca spotkania z klientem \n Odległości pojazdów do celu nie mogły być obliczone!");
		}else if(counterGoodDist!=counterAll){
			showMessageBox("Nie wszystkie odległości zostały prawidłowo obliczone");
		}
		$('#listOfDriversForOrderTable').tablesorter({widthFixed: true, widgets: ['zebra']});
	});
}


