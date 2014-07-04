//pierwsze odpalenie


function initialiseMarkers(){
	//alert('start');
	$.getJSON( 'GetAllVehiclesPosition.htm', function(data) { 
		var markersTable=[];
		var markersIconTable=[];
		//NO INTERNET CONNECTION
			$.each( data.markers, function(i, marker) {
			//alert("12");
			var iconNr=new google.maps.MarkerImage('resources/images/icons/taxi_'+marker.statusColor+'2.png');
			var position = [ marker.latitude, marker.longitude];
			//var dataTable= $('<table></table>');
			//dataTable.append($('<tr></tr>').append($('<td></td>').text("Nazwa")));//.append($('<td></td>').text(marker.vehicleName)));
			//dataTable.append($('<tr></tr>').append($('<td></td>').text("Id")).append($('<td></td>').text(marker.vehicleId)));
			//dataTable.append($('<tr></tr>').append($('<td></td>').text("pozycja")).append($('<td></td>').text('['+marker.latitude+','+marker.longitude+'[')));
			
			//dataTable.append($('<tr></tr>').append($('<td></td>').text("Name")).append($('<td></td>').text(marker.vehicleName)));
			//dataTable=
			//dataTable=$('<div></div>').text("Nazwa:"+marker.vehicleName);
			var dataDiv = "<div><table><tr><td class='markerInfoLabel'>Nazwa:</td>   <td class='markerInfoValue'>"+marker.vehicleName+"</td></tr>"+
									  "<tr><td class='markerInfoLabel'>Id: </td>     <td class='markerInfoValue'>"+marker.vehicleId+"</td></tr>"+
									  "<tr><td class='markerInfoLabel'>pozycja: </td><td class='markerInfoValue'>{"+marker.latitude+","+marker.longitude+"}</td></tr>"+
							"</table></div>";
			//"<div style='font-style:italic;'><b>Nazwa: </b>"+marker.vehicleName+"</div>"
			//markersTable.push({ lat: marker.latitude, lng: marker.longitude, data: marker.vehicleName});
			markersTable.push({ latLng: position, data: dataDiv, options:{ icon: iconNr}, tag:"ac" });
		}); 
	  	  
	   	$('#map_canvas').gmap3({
	   		map:{
	   			options:{center:[54.366958,18.649292],zoom: 12}
	   		},  
	   		clear:{tag:"ac",all:true},
	   		marker:{values:markersTable,options:{draggable: false},
	   			events:{
	   				mouseover: function(marker, event, context){
	   					var map = $(this).gmap3("get"),
	   					infowindow = $(this).gmap3({get:{name:"infowindow"}});
	   					if (infowindow){
	   						infowindow.open(map, marker);
	   						infowindow.setContent(context.data);
	   					} else {
	   						$(this).gmap3({
	   							infowindow:{
	   								anchor:marker, 
	   								options:{content: context.data}
	   							}
	   						});
	   					}
	   				},
	   				mouseout: function(){
	   					var infowindow = $(this).gmap3({get:{name:"infowindow"}});
	   					if (infowindow){
	   						infowindow.close();
	   					}
	   				}
	   			}
	   		}
	   	}); 
	});
}

function performRefreshMap(){
	//console.log('MapRefresh');
	$.getJSON( 'GetAllVehiclesPosition.htm', function(data) { 
		var markersTable=[];
		$.each( data.markers, function(i, marker) {
			//alert("12");
			var iconNr=new google.maps.MarkerImage('resources/images/icons/taxi_'+marker.statusColor+'2.png');
			var position = [ marker.latitude, marker.longitude]; 
			//markersTable.push({ lat: marker.latitude, lng: marker.longitude, data: marker.vehicleName});
			markersTable.push({ latLng: position, options:{ data: marker.vehicleName,  icon: iconNr}, tag:"ac" });
			//alert(markersTable);
			//alert(position);
		}); 

		$('#map_canvas').gmap3( {
/* 						map:{
				options:{
					center:[54.366958,18.649292],
				    zoom: 12
				}
			},  */
			clear:{
				//name:"marker",
				tag:"ac",
				all:true
			},
			marker:{
				values:markersTable,
				options:{
					draggable: false
				},
				events:{
					mouseover: function(marker, event, context){
				    	var map = $(this).gmap3("get"),
				          infowindow = $(this).gmap3({get:{name:"infowindow"}});
				        if (infowindow){
				          infowindow.open(map, marker);
				          infowindow.setContent(context.data);
				        } else {
				          $(this).gmap3({
				            infowindow:{
				              anchor:marker, 
				              options:{content: context.data}
				            }
				          });
				        }
				      },
				      mouseout: function(){
				        var infowindow = $(this).gmap3({get:{name:"infowindow"}});
				        if (infowindow){
				          infowindow.close();
				        }
				      }
				}
			}

				  
		});
	});
	
}


/**
 * deprecated
 * uzywane automatycznie w autocomplete elementu input dla adresu
 * @param addressArg
 */
function findAddress(addressArg){
	//alert(addressArg);
	var latLon="";
	$('#map_canvas').gmap3( {
					clear:{
						//name:"marker",
						tag:["ad"]/*,
						all:true*/
					},
					marker:{
						address: addressArg,
						options:{
			/*				icon: new google.maps.MarkerImage(
								       "http://gmap3.net/skin/gmap/magicshow.png",
								       new google.maps.Size(32, 37, "px", "px")
								     ),*/
							draggable: false
						},
						tag:'ad',
						callback: function(marker){
							var map = $(this).gmap3("get");
							var position=marker.getPosition();
					        var lat=position.lat();
					        var lng=position.lng();
					       // alert ("CALLBACK "+position.lng());
					        latLon=lat+":"+lng;
					        $('#latLng').val(latLon);
					        setCoordinatesOfOrder();
					        //map.setCenter(new google.maps.LatLng(lat,lng));
						}
						,events:{
							mouseover: function(marker, event, context){
						    	var map = $(this).gmap3("get"),
						          infowindow = $(this).gmap3({get:{name:"infowindow"}});
						    	var position=marker.getPosition();
						        var lat=position.lat();
						        var lng=position.lng();
						       
						       // map.setCenter(new google.maps.LatLng(lat,lng));
						    	//$('#status_info1').text('szeroko��:'+position.lat()+'; dlugo��:'+position.lon());
						    	
						        if (infowindow){
						          infowindow.open(map, marker);
						          infowindow.setContent(addressArg+ '; szerokość:'+position.lat()+'; dlugość:'+position.lng());
						        } else {
						          $(this).gmap3({
						            infowindow:{
						              anchor:marker, 
						              options:{content: addressArg+ '; szerokość:'+position.lat()+'; dlugość:'+position.lng()}
						            }
						          });
						        }
						        
						      },
						      mouseout: function(){
						        var infowindow = $(this).gmap3({get:{name:"infowindow"}});
						        if (infowindow){
						          infowindow.close();
						        }
						      }
						}

					}
						  
				});
}

function initialiseAddressAutoComplete(){
    $("#order_address_input").autocomplete({
        source: function() {
            $("#map_canvas").gmap3({
                getaddress: {
                    address: $("#order_address_input").val(),
                    callback: function(results){
                        if (!results) return;
                        $("#order_address_input").autocomplete("display", results, false);
                        
                    }
                }
            });
        },
        cb:{
            cast: function(item){
                return item.formatted_address;
            },
            select: function(item) {
        		var addressArg = $("#order_address_input").val();
        		findAddress(addressArg);
               /* $("#map_canvas").gmap3({
                    clear: "orderAddressMarker",
                    marker: {
                        latLng: item.geometry.location,
                        tag: "orderAddressMarker"
                    },
                    map:{
                        options: {
                            center: item.geometry.location
                        }
                    }
                });*/
            }
        }
    }).focus();
	
	
	
}

function searchRoute(originArg, destinationArg){
	alert(originArg +" "+ destinationArg);
	$("#map_canvas").gmap3({
		clear:{
			name:"marker",
			tag:["ad","ac"],
			all:true
		},
		  getroute:{
		    options:{
		        origin:originArg,//"48 Pirrama Road, Pyrmont NSW",
		        destination:destinationArg,//"Bondi Beach, NSW",
		        travelMode: google.maps.DirectionsTravelMode.DRIVING
		    },
		    callback: function(results){
		      if (!results) return;
		      $("#map_canvas").gmap3({
		        map:{
		          options:{
		            zoom: 10
		           
		          }
		        },
		        directionsrenderer:{
		          container: $("#find_route_description"),
		          options:{
		            directions:results
		          } 
		        }
		      });
		    }
		  }
		});
	
}



function setAutorefresh(){
		//console.log('setAutorefresh');
	   	 //refresch co zadany okres czasu
	   	 	var auto_refresh = setInterval(function(){
	   	 		//console.log('setAutorefresh2');
	   	 		performRefreshMap();
	   	 		refreshStats();
	   	 		$('#listOfVehicles').remove();
	   	 		createInitialTable();
				
	   	 	},60000);
}
	   	 	