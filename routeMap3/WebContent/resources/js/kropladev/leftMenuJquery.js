				var collapseIcon = 'resources/images/toggle_collapse_alt.png';//'images/bullet_toggle_minus.png';
				var expandIcon = 'resources/images/toggle_expand_alt.png';//'images/bullet_toggle_plus.png';
				var leftMenuCollapseIcon = 'resources/images/icon_collapse_rtl.gif';
				var leftMenuExpandIcon = 'resources/images/icon_expand_ltr.gif';
				var collapseText="collapse";
				var expandText="expand";


	function showMess1(){
		alert("test");
	};
	var mapZoom=12;
	var lastVehicleLat,lastVehicleLon;

	
	function createInitialTable(){
		//przypisz wezly do obiektow
		//var driverListHeader=$('#driver_list');
		var leftMenuParam=$('#leftMenuAction');
		//var collapseIcon = 'resources/images/toggle_collapse_alt.png';//'images/bullet_toggle_minus.png';
		//var expandIcon = 'resources/images/toggle_expand_alt.png';//'images/bullet_toggle_plus.png';
		//var leftMenuCollapseIcon = 'resources/images/icon_collapse_rtl.gif';
		//var leftMenuExpandIcon = 'resources/images/icon_expand_ltr.gif';
		
		
		//wczytaj dane inicjalne do tablicy
		$.ajax({
			type : "GET",
			url : "/routeMap3/GetVehicles.htm", 
			dataType : "json",
			error : function (xhr, ajaxOptions, thrownError) {
				$('#test2').text(xhr.responseText);
		      },
			success : function(data) {
				//alert("2");
				//$('#content').text("jquery Test3");
				if (data.VehicleList.length) {
					//inicjacja tablicy
					/*var vehicleList=$('<table></table>').addClass('sortable').attr('id','listOfVehicles');*/
					var vehicleList=$('<table></table>').addClass("tablesorter").attr('id','listOfVehicles');
					leftMenuParam.append(vehicleList);
					
					//inincjacja pierwszej linii - naglowek
					//var theader=$('<thead></thead>');
					var trHead=$("<tr></tr>");
					//trHead.append($("<th></th>")); 
					trHead.append($("<th></th>").text('id').css('width','20%'));
					trHead.append($("<th></th>").text('Nazwa'));
					trHead.append($("<th></th>").text('Stat.'));
					//theader.append(trHead);
					vehicleList.append($('<thead></thead>').append(trHead));
					
					//foreach - iteracja po wszystkich danych z bazy
					$.each(data.VehicleList, function(i, data) {
						//$('#listOfVehicles').append($("<option></option>").attr("value",data.id).text(data.name));
						var row = $('<tr></tr>').attr('id','firstRow_'+data.id);
						
						//dodatkowa funkcjonalnosc - po kliknieciu na rekord w tablicy 
						//    dociagane sa dane dodatkowe na temat pojazdu
						//    wszystko wyswietlane jest w linii ponizej
						//row.click(getVehicleDetailedInfo(data.id));
						row.click(function(){
							getVehicleDetailedInfo(data.id);
						});
						
						row.hover(
								function(){row.addClass('tableSorterMouseAt');},
								function(){row.removeClass('tableSorterMouseAt');});

						row.append($("<td></td>").text(data.id));
						row.append($("<td></td>").text(data.name));
						row.append($("<td></td>").append($('<p></p>').text(data.statusDescAdd).hide()).append($('<img></img>').attr('src','resources/images/icons/small/taxi_'+data.statusColor+'_small.png').attr('alt',data.statusDescAdd)));
						
						$('#listOfVehicles').append(row);
						//var secondRow=$('<tr><td colspan="2">test</td></tr>').attr('id','secondRow_'+data.id);
						//secondRow.addClass('detailInfo').hide();
						//$('#listOfVehicles').append(secondRow);
						
					});
				} else {
					//alert("1");
					var vehicleList=$('<table></table>').addClass("tablesorter").attr('id','listOfVehicles');
					leftMenuParam.append(vehicleList);
					
					//inincjacja pierwszej linii - naglowek
					//var theader=$('<thead></thead>');
					var trHead=$("<tr></tr>");
					//trHead.append($("<th></th>")); 
					trHead.append($("<th></th>").text('id').css('width','20%'));
					trHead.append($("<th></th>").text('Nazwa'));
					trHead.append($("<th></th>").text('Stat.'));
					//theader.append(trHead);
					vehicleList.append($('<thead></thead>').append(trHead));
					var row = $('<tr></tr>').attr('id','firstRow_00');
					row.append($("<td></td>").text('--'));
					row.append($("<td></td>").text('brak danych'));
					row.append($("<td></td>").text('--'));
					$('#listOfVehicles').append(row);
					
					//$('#test2').text("No Results");
				/*	$('#listOfVehicles').append(
							$("<option></option>").Attr("value", "11")
									.Text("No Results"));*/
				}
				/* dodajemy sortowanie i podzial na strony */
				$("#listOfVehicles").tablesorter({widthFixed: true, widgets: ['zebra']}).tablesorterPager({container: $("#pager")});
				//alert('1');
			}
		});
	}
	
	function setMapCenterOnMarker(vehicleLon,vehicleLat){		
		var map=$('#map_canvas').gmap3('get'); //reference to native map object	
		if (lastVehicleLat==vehicleLat && lastVehicleLon==vehicleLon){
			if(mapZoom<18){
				mapZoom++;
			}else{
				mapZoom=12;
			}
		}else{
			map.setCenter(new google.maps.LatLng(vehicleLat,vehicleLon));
			mapZoom=12;
			lastVehicleLat=vehicleLat;
			lastVehicleLon=vehicleLon;
		}
		map.setZoom(mapZoom);
//		$('#map_canvas').gmap3('get').setCenter(new google.maps.LatLng(vehicleLat,vehicleLon));	
	}
	
	
	function getVehicleDetailedInfo(arg_id){
		//alert("nowa funkcja");
		var activeSecondRow=$("#detailedInfoTemp_"+arg_id);
	 	if(activeSecondRow.length==0 ){
	 		var vehicleLat=54.366958,vehicleLon=18.649292;
	 		//alert(vehicleLat);
			//activeSecondRow.fadeIn('slow');
			//pobierz dane odnosnie jednego pojazdu
			//TODO: kontrola pobranych danych - redukcja ilosci zapytan do bazy - jak raz sie dane zaciagnely to nie sciagamy ponownie
			 $.ajax({
					type : "GET",
					url : "/routeMap3/GetDetailInfoVehicle.htm?vehicleId="+arg_id,
					data : "json",
					//cache : false,
					success : function(data2) {
						//$("#test2").text(data2);
						//pobrane dane na temat pojedynczego pojazdu
						var obj = jQuery.parseJSON(data2);
						$vehicleLat=obj.latitude; 
						$vehicleLon=obj.longitude;
					//	alert(vehicleLat);
						var firstRow=$("#firstRow_"+arg_id);
						var detailInfo=$('<table></table>').attr('id',"detailInfoTable2").addClass('detailInfoTable');
						detailInfo.append($('<tr></tr>').addClass("detailInfo").append($('<td></td>').addClass("detailInfoLabel").text('Ostatnie logowanie:')).append($('<td></td>').text(obj.lastLoginTime)));
						detailInfo.append($('<tr></tr>').addClass("detailInfo").append($('<td></td>').addClass("detailInfoLabel").text('Status:')).append($('<td></td>').text(obj.statusDescription)));
						detailInfo.append($('<tr></tr>').addClass("detailInfo").click(function(){setMapCenterOnMarker(obj.longitude,obj.latitude);}).css("cursor",'pointer').append($('<td></td>').addClass("detailInfoLabel").text('Pozycja:')).append($('<td></td>').text(obj.longitude)));
						detailInfo.append($('<tr></tr>').addClass("detailInfo").click(function(){setMapCenterOnMarker(obj.longitude,obj.latitude);}).append($('<td></td>').addClass("detailInfoLabel").text(' ')).append($('<td></td>').text(obj.latitude)));
						
						firstRow.after($('<tr></tr>').attr('id',"detailedInfoTemp_"+arg_id).append($('<td></td>').attr('colspan',"3").append(detailInfo)));
						$("#detailInfoTable2>tbody>tr:even").addClass("even");
					}
				}); 
	 	}else{
	 		//tylko usun z widoku element
			activeSecondRow.fadeOut('slow');
			activeSecondRow.remove();
		} 
		$("#test2").text('testujemy');
		//showMess1();  
	}
	
	$(document).ready(
			function() {
				createInitialTable();
				initialiseAddressAutoComplete();
			
				
				//przypisz wezly do obiektow
				var driverListHeader=$('#driver_list');
				var leftMenuParam=$('#leftMenuAction');


				var clickCounter=0;
				$('#statusFilter').append($('<img></img>').attr('src','resources/images/icons/small/icon-filter.png').attr('alt','filtruj').attr('id','filtrImg'));
				$('#statusFilter').addClass('filterIcon');
				//po kliknieciu w ikone select opcji ma sie pojawic lub zniknac
				$('#statusFilter').click(
						function() {
							if (clickCounter==0){
								if ($('#statusFilterSelect').is(':hidden')) {
									$('#statusFilterSelect').removeAttr("multiple");
									$('#statusFilterSelect').fadeIn('slow');
								}
								clickCounter++;
							}else if(clickCounter==1){
								$('#statusFilterSelect').attr('multiple','true');
								clickCounter++;
							}else{
								clickCounter=0;
								$('#statusFilterSelect').fadeOut('fast');
							}
								
						}
				);
				$.getJSON( '/routeMap3/GetDicStatuses.htm?statusType=vehicle', function(data) { 
					$('#statusFilterSelect').append($('<option></option>').attr('value','').text('wszystkie'));
					$.each( data.StatusList, function(i, statusObj) {
						$('#statusFilterSelect')
							.append($('<option></option>').attr('value',statusObj.statusId).text(statusObj.statusDescAdd)								
							);
					});
				});
				
/*				$('#listOfVehicles tr').hover(
						//function(){alert("mouse at");},
						//function(){alert("mouse out");}
						);*/
				//obliczam wysokosc elem select - takby przesunac odpowiednio ikone filtra
				var work=$('#statusFilterSelect').height()-20;
				//przesun ikone filtr
				//$('#statusFilter').css('vertical-align',work);
				//obsluga wyboru opcji z select

				$('#statusFilterSelect').hide();

				
				
				/* hide all left menu panel on click '<' and show after click '>' */
				$('#toggle').click(function() {
					//alert("test2");
					if (leftMenuParam.is(':hidden')) {
						leftMenuParam.fadeIn('slow');
						$('#leftMenu').fadeIn('slow');
						$('#left_menu_container').width('16%');
						$('#map_container').width('84%');
						/*$(this).text('<');*/
						$('#leftMenuExpandCollapseToggle').attr('src',leftMenuCollapseIcon).css('z-index',101);
						 $(this).css('z-index',100);
					} else {
						leftMenuParam.fadeOut('slow');
						$('#leftMenu').fadeOut('slow');
						$('#left_menu_container').width('1%');
						$('#map_container').width('99%');
						/*$(this).text('>');*/
						$('#leftMenuExpandCollapseToggle').attr('src',leftMenuExpandIcon).css('z-index',100);
						$(this).css('z-index',101);
					}
				});
				

				/**
				 * hide table after click on icon'-' and show on '+'
				 */
				driverListHeader.prepend($('<img />').attr('src', collapseIcon)
						.attr('alt', collapseText).click(
								function() {
									if ($('#listOfVehicles').is(':hidden')) {
										$('#listOfVehicles').fadeIn('slow');
										$('#pager').fadeIn('slow');
										//zmien ikone
										$(this).attr('src', collapseIcon).attr(
												'alt', collapseText);
									} else {
										$('#listOfVehicles').fadeOut('fast');
										$('#pager').fadeOut('fast');
										$(this).attr('src', expandIcon).attr(
												'alt', expandText);
									}
								}
						));


				$('#statusFilterSelect').change(function(){
					//alert('change');
					var str="";
					$('#statusFilterSelect option:selected').each(function(){
						str+=$(this).val()+ ",";
					});	
					$('#test2').text(str);
					
					$.ajax({
						type : "GET",
						url : "/routeMap3/ChangeFilter.htm?filterOption="+str.substring(0,str.length-1), 
						dataType : "text",
						error : function (xhr, ajaxOptions, thrownError) {
							$('#test2').text(xhr.responseText);
					      },
						success : function(data) {
							$('#listOfVehicles').remove();
							createInitialTable();
							performRefreshMap();
						}
					});
				});
				
				
			});

	function hideLeftDriverListTable() {
		if ($('#listOfVehicles').is(':hidden')) {
			$('#listOfVehicles').fadeIn('slow');
			$('#pager').fadeIn('slow');
			//zmien ikone
			$(this).attr('src', collapseIcon).attr(
					'alt', collapseText);
		} else {
			$('#listOfVehicles').fadeOut('fast');
			$('#pager').fadeOut('fast');
			$(this).attr('src', expandIcon).attr(
					'alt', expandText);
		}
	}

function hideAllLevelMenu(){
		if ($('#leftMenuAction').not(':hidden')) {
			$('#leftMenuAction').fadeOut('slow');
			$('#leftMenu').fadeOut('slow');
			$('#left_menu_container').width('1%');
			$('#map_container').width('99%');
			$('#leftMenuExpandCollapseToggle').attr('src',leftMenuExpandIcon).css('z-index',100);
			$(this).css('z-index',101);
		}
}
