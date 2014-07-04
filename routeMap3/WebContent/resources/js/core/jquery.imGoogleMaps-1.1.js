
/*
 * 	 imGoogleMaps - A JQuery Google Maps Implementation
 * 	 @author Les Green
 * 	 Copyright (C) 2008-2010 Intriguing Minds, Inc.
 * 
 *   Version 1.1 - 24 June 2012
 *   1. Fixed error that displayed when click on marker on map
 *   
 *   Version 1.0 - 8 Sep 2010
 *   1. Upgraded to Google Maps V3
 *   	1. API key is no longer needed - <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
 *   	2. Street View button control must be opted when map is initialized (show_streetview_overlay) 
 *   2. Removed auto mode (mode option no longer needed)
 *   3. Removed directions option - now popup window only
 *   4. Removed show_directions_menu option - now show_directions (in infoWindow)
 *   5. Changed imBusinessName to infoWindowBusinessName and imDescription to infoWindowDescription (css)
 *   6. json is now the default data_type option (was 'text')
 *   7. Removed menu_class, print_class, button_class, and search options.
 *   8. Added URL to info window.  class = infoWindowUrl (see item #9)
 *   9. Added show_infowindow_url option. If true (the default), url will display in info window (if exists).
 *      If false, url link will be added to business name (or name). 
 *      When false, class = .infoWindowBusinessName a
 *  10. Removed mapIconMaker - http://groups.google.com/group/google-chart-api/web/map-pins-sticky-notes-information-bubbles-text-with-outline  
 *  11. Added show_traffic and show_bicycling
 *  12. Added kml and georss support
 *      1. data_type = 'layer'
 *      2. For multiple layers, add: is_layer_url = true to json record
 * 
 *     
 *   Version 0.9.4 - 3 July 2010
 *   1. Removed top control bar when in manual mode. 
 *   2. Can get directions from infoWindow.
 *   3. Added instructions option (click on a marker to get directions)
 *   4. Added Zoom Level Option
 *   5. Bug fix - error when loading a single json record
 *   6. Separate Street View button from street view in info window (show_streetview_button)
 *       
 *      
 *   Version 0.9.3 - 8 Jan 2010
 *   1. Bug fix - markers not displaying in ie
 *   2. Add images to infoWindow 
 *   
 *   
 *   Version 0.9.2 - 30 Dec 2009
 *   
 *   1. Bug fix - Print displaying null on screen
 *   2. Bug fix - Received error when clicking on infoWindow if full address was not given
 *   3. Changed infoWindow so that is does not display street view link if 
 *   	StreetViewPanorama is not available for given point. 
 *   	And Street View Overlay will not open to Street View if address not available
 *   4. Added option: geocode_request_rate. 
 *   	Geocode Requests limited to 15000 per IP address per day (which works out at
		an average of one every 5.76 seconds). Had to create delayed request - 1 every 5 seconds.
		
		The default for geocode_request_rate is 1000 (1 sec). If address amount is over 10, 
		set this at a higher rate - 5000 (5 secs) and use progress_bar option 
		
	 5.	Added progress_bar option. Uses imProgressBar plugin. Because of the delay, this will
	 	let the user know that the requests are processing.
	 	
	 6. Added phone (phone number) and desc (description) option to json record. Will be displayed in infoWindow.
 *      Phone Number style same as address. 
 *      Description - Style = span.imDescription
 *   7. Added custom_marker option - http://gmaps-utility-library.googlecode.com/svn/trunk/mapiconmaker/1.1/src/
 *   	custom_marker can optionally be added to json record - if you want each marker to have a custom color
 *   	Can also use Custom Marker that don't use the file - http://code.google.com/p/google-maps-icons/    	
 *   
 *   
 *   Version 0.9.1 - 24 Dec 2009
 *   
 *   1. Fixed bug with Street View Control. Button did not exit street view
 *   
 *   Version 0.9 - 18 Dec 2009
 *   1. Fixed manual mode bug
 *   2. Added option for manual mode - remove 'Get Directions' label and directions div.
 *   	show_directions_menu: false
 *   	
 *   	The following options are not needed when show_directions_menu == false
 *   		directions
 *   		search
 *   		button_class
 *    
 *   3. Can press enter when doing a search (if on from or to field). No longer have to click search button   
 *   4. Added Street view (Panorama)
 *   	Requires the following options:
 *   
 *    	street_view - the div that holds the street view
 *    	street_close_loc - the location of the close button for street view.
 *      
 *            
 *   5. Ability to plot multiple addresses. 
 *   	When getting multiple addresses via the data_url option, the data_type must be set to json
 *      Also added 'name' option to json record, so name and address can be displayed in infoWindow.
 *      Style = span.imBusinessName 
 *   
 *     
 *   
 *   
 *   version 1.0 - may add the following:
 *   1. http://www.geocodezip.com/mapStreetviewTabs.html - directions in tab
 *   2. Adsense for Maps - http://code.google.com/apis/maps/documentation/services.html#Advertising
 *   3. Traffic Overlay
 *   4. Microsoft Virtual Earth - http://www.mashedworld.com/DualMaps.aspx
 *  
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.

 *   Demo and Documentation can be found at:   
 *   http://www.grasshopperpebbles.com
 *   
 */

//imGMapObj (map object) and imStreetViewPoint (LatLng) must be set outside object 
//due to closure issue with map.getInfoWindow in Javascript call
var imGMapObj = null;
var imStreetViewPoint = [];
//used to hold each marker so directions options can be displayed for the relevant marker
//var imDirectionsMarker = [];
var from_htmls = [];
var to_htmls = [];
var imMapObj = new Object();
//var gdir = null;
//var imgmap = '';

//used to toggle overlay
var imSVOverlay = null;
//geocode requests have a rate limit of 1.7 seconds per query
//this var will hold the setTimeout object
var imGeocodeRequestDelay = null;

function imLatLngs(latlngLoc, format_adr, adr_comp, bus_name, phone, url, desc, images) {
	this.latlngLoc = latlngLoc;
	this.format_adr = format_adr;
	this.adr_comp = adr_comp;
	this.bus_name = bus_name;
	this.phone = phone;
	this.url = url;
	this.desc = desc;
	this.images = images;
}
//used with custom markers loaded from json file
function imIconOptions(icon_options) {
	this.icon_options = icon_options;
}

;(function($) {
	$.fn.extend({
        imGoogleMaps: function(options) { 
        	opts = $.extend({}, $.googleMaps.defaults, options);
			return this.each(function() {
				new $.googleMaps(this, opts);
			});
        }
    });	

$.googleMaps = function(obj, opts) {
	var geocoder, panorama, currentLayer;
	var $this = $(obj);
	var toAddress = (opts.address);
	var aLatLng = new Array();
	var aIconOptions = new Array();
	var aRequestedAdress = '';
	init();
	
	function init() {
		createMap();
		var tf = showMap();
		
		if (opts.data_type != 'layer') {
			if (opts.progress_bar) {
				$this.imProgressBar({
					progress_bar: {
						'container': opts.progress_bar.container,
						'bar_back_class': opts.progress_bar.bar_back_class,
						'bar_class': opts.progress_bar.bar_class
					},
					display: {
						'type': 'inline',
						'insert_type': 'before',
						'element': '#' + opts.map
					},
					animate_duration: 1500
				});
			}
			
			if (opts.data_url) {
				var d = getDataString();
				doAjax('GET', opts.data_url, d, '', loadAddress);
			}
			else 
				if (opts.address) {
					loadAddress(opts.address);
				}
				else 
					if (opts.lat_lng) {
						loadAddress(opts.lat_lng);
					}
					else {
						alert('Address must be specified!');
					}
		}
	};
	
	function createMap() {
		imMapObj.mapId = opts.map;
		imMapObj.streetId = opts.street_view;
		//objMapDir.dirId = opts.directions;
		/*if (opts.directions != 'popup') {
			$('<div></div>').attr("id", opts.directions).appendTo($this);
		}
		$this.append($('<div></div>').attr("id", opts.map));
		if (opts.directions != 'popup') {
			//used to change map size in getDirections function
			var mw = $('#'+opts.map).outerWidth(true);
			var dw = $('#'+opts.directions).outerWidth(true);
			objMapDir.mapNewW = mw - (dw + 15) + 'px';
		}*/
		$this.append($('<div></div>').attr("id", opts.map));
		if (opts.instructions) {
			if (opts.instructions.location == 'top') {
				//var op = (opts.directions != 'popup') ? opts.directions : opts.map;
				var op = opts.map;
				$('<div></div>').attr("id", opts.instructions.container).html(opts.instructions.label).insertBefore('#' + op);
			}
			else {
				$('<div></div>').attr("id", opts.instructions.container).html(opts.instructions.label).insertAfter('#' + opts.map);
			}
			$('<div></div>').attr("id", opts.instructions.container).html(opts.instructions.label);
		}	
		if (opts.street_view) {
			$('<div></div>').attr("id", "imStreetViewClose").append($('<a></a>').attr({
				"id": "imStreetCloseBtn",
				'title': 'Exit Street View'
			}).append($('<img></img>').attr('src', opts.street_close_loc))).insertAfter("#" + opts.map);
			$('<div></div>').attr("id", opts.street_view).insertAfter("#imStreetViewClose");
			$('#imStreetCloseBtn').click(function(){
				//imStreetView.toggleStreetView(false);
				toggleStreetView(-1);
			});
		}
	};
	
	function showMap() {
		var layer;
		var ltlg = new google.maps.LatLng(0,0);
		var mpOptions = new Object();
		mpOptions.zoom = (opts.zoom_level) ? opts.zoom_level : 1;
		
		mpOptions.center = ltlg;
		mpOptions.mapTypeId = google.maps.MapTypeId.ROADMAP;
		if (opts.show_streetview_overlay) {
			mpOptions.streetViewControl = true;
		}
		//var mpOptions = (opts.zoom_level) ? { zoom: opts.zoom_level, center: myLatlng, mapTypeId: google.maps.MapTypeId.ROADMAP } : { center: myLatlng, mapTypeId: google.maps.MapTypeId.ROADMAP }; 
        imGMapObj = new google.maps.Map(document.getElementById(opts.map), mpOptions);
		if (opts.show_traffic) {
			layer = new google.maps.TrafficLayer();
			layer.setMap(imGMapObj);
		}
		if (opts.show_bicycling) {
			layer = new google.maps.BicyclingLayer();
			layer.setMap(imGMapObj);
		}
		if (opts.data_type == 'layer') {
			layer = new google.maps.KmlLayer(opts.data_url);
			layer.setMap(imGMapObj);
		}
		if (opts.show_more_button) {
			var trCh = (opts.show_traffic);
			var bCh = (opts.show_bicycling);
			var layers = [
				{ name: "Traffic", obj:  new google.maps.TrafficLayer(), checked: trCh },
				{ name: "Bicycling", obj: new google.maps.BicyclingLayer(), checked: bCh }
			];
			var moreControlDiv = document.createElement('DIV');
		  	var moreControl = new imGMapMoreControl(moreControlDiv, layers);
		  	moreControlDiv.index = 1;
		  	imGMapObj.controls[google.maps.ControlPosition.TOP_RIGHT].push(moreControlDiv);
			$('#imGMapChkControl0').live('click', function() {
				toggleLayer(this, 'traffic');
			});
			$('#imGMapChkControl1').live('click', function() {
				toggleLayer(this, 'bicycle');
			});
		}
		/*if (opts.progress_bar) {
			var pbar = document.createElement('DIV');
			$(pbar).attr('id', opts.progress_bar.container);
			var pb = new imGMapProgressBar(pbar, opts.progress_bar.bar_back_class, opts.progress_bar.bar_class);
			imGMapObj.controls[google.maps.ControlPosition.RIGHT].push(pbar);

			$this.imProgressBar({
				progress_bar: {'container': opts.progress_bar.container, 'bar_back_class' : opts.progress_bar.bar_back_class, 'bar_class': opts.progress_bar.bar_class},
				display: {'type': 'inline', 'insert_type': 'before', 'element': '#'+opts.map},
				animate_duration: 1500
			});
		}*/
		geocoder = new google.maps.Geocoder();
	
		return true;
    };
	
	function toggleLayer(el, name) {
		var layer;
		if (el.checked) {
			if (name == 'traffic') {
				layer = new google.maps.TrafficLayer();
			} else if (name == 'bicycle') {
				layer = new google.maps.BicyclingLayer();
			}
			currentLayer = layer;
			layer.setMap(imGMapObj);
		} else {
			currentLayer.setMap(null);
		}
	};
	
	function loadKmlLayer(cnt) {
		var adr_cnt, url, layer;
		if (aRequestedAdress.is_layer) {
			adr_cnt = 1;
			url = aRequestedAdress.address;
		} else if (aRequestedAdress[cnt].is_layer_url) {
			adr_cnt = aRequestedAdress.length;
			url = aRequestedAdress[cnt].address;
		}
		layer = new google.maps.KmlLayer(url);
		layer.setMap(imGMapObj);
		cnt++;
		if (opts.progress_bar) {
			$(window).trigger('updateProgress');
		}
		if (cnt < adr_cnt) {
			imGeocodeRequestDelay = setTimeout(function(){
				getAdrLocation(cnt);
			}, 1000);
		} else {
			clearTimeout(imGeocodeRequestDelay);
		
		}
	};
	
	function getAdrLocation(cnt) {
		var adr, bName, phone, url, desc, img, adr_cnt, iOptions = '';
		if ((aRequestedAdress.is_layer) || (aRequestedAdress[cnt].is_layer)) {
			loadKmlLayer(cnt);
		}
		else {
			if (aRequestedAdress.lat) {
				adr = new GLatLng(aRequestedAdress.lat, aRequestedAdress.lng);
				adr_cnt = 1;
			}
			else 
				if (aRequestedAdress.address) {
					adr_cnt = 1;
					adr = aRequestedAdress.address;
					bName = (aRequestedAdress.name) ? aRequestedAdress.name : '';
					bName = (aRequestedAdress.business_name) ? aRequestedAdress.business_name : bName;
					phone = (aRequestedAdress.phone) ? aRequestedAdress.phone : '';
					url = (aRequestedAdress.url) ? aRequestedAdress.url : '';
					desc = (aRequestedAdress.desc) ? aRequestedAdress.desc : '';
					img = (aRequestedAdress.images) ? aRequestedAdress.images : '';
					iOptions = (aRequestedAdress.custom_marker) ? aRequestedAdress.custom_marker : '';
				}
				else 
					if (aRequestedAdress[0].address) {
						adr_cnt = aRequestedAdress.length;
						adr = aRequestedAdress[cnt].address;
						bName = (aRequestedAdress[cnt].name) ? aRequestedAdress[cnt].name : '';
						bName = (aRequestedAdress[cnt].business_name) ? aRequestedAdress[cnt].business_name : bName;
						phone = (aRequestedAdress[cnt].phone) ? aRequestedAdress[cnt].phone : '';
						url = (aRequestedAdress[cnt].url) ? aRequestedAdress[cnt].url : '';
						desc = (aRequestedAdress[cnt].desc) ? aRequestedAdress[cnt].desc : '';
						img = (aRequestedAdress[cnt].images) ? aRequestedAdress[cnt].images : '';
						iOptions = (aRequestedAdress[cnt].custom_marker) ? aRequestedAdress[cnt].custom_marker : '';
					}
					else 
						if (aRequestedAdress[0].lat) {
							adr_cnt = aRequestedAdress.length;
							adr = new GLatLng(aRequestedAdress[cnt].lat, aRequestedAdress[cnt].lng);
						}
						else {
							adr = aRequestedAdress;
							adr_cnt = 1;
						}
			//url = checkHttpPrefix(url, 'add');
			geocoder.geocode({
				'address': adr
			}, function(results, status){
				if (status == google.maps.GeocoderStatus.OK) {
					aLatLng[cnt] = new imLatLngs(results[0].geometry.location, results[0].formatted_address, results[0].address_components[0], bName, phone, url, desc, img);
					if (iOptions) {
						aIconOptions[cnt] = iOptions;
					}
					cnt++;
					if (opts.progress_bar) {
						$(window).trigger('updateProgress');
					}
					if (cnt < adr_cnt) {
						//gecode has a rate limit for requests
						imGeocodeRequestDelay = setTimeout(function(){
							getAdrLocation(cnt);
						}, opts.geocode_request_rate);
					}
					else {
						clearTimeout(imGeocodeRequestDelay);
						mapAddress();
					}
				}
				else {
					alert("Sorry, we are unable to gecode address: " + adr + " for the following reason: " + status);
				}
			});
		}
	};
	
	function getAddressCount(address) {
		var adr_cnt = 0;
		if ((address.lat) || (address.address)) {
			adr_cnt = 1; 
		} else if (address[0].address) {
			adr_cnt = address.length;
		} else {
			adr_cnt = 1;
		}
		return adr_cnt;
	};
	
	function loadAddress(address) {
		if (geocoder) {
			var total_recs = getAddressCount(address);
			aRequestedAdress = address; 
			//initialize progress bar
			if (opts.progress_bar) {
				$(window).trigger('startProgress', [total_recs]);
			}
			getAdrLocation(0);
		}				
	};

	function mapAddress() {
		//set bounds	
		var bounds = new google.maps.LatLngBounds();
		$.each(aLatLng, function(i, itm){
			var point = itm.latlngLoc;
			var marker = createMarker(point, i, itm.format_adr, itm.adr_comp, itm.bus_name, itm.phone, itm.url, itm.desc, itm.images);
			//imDirectionsMarker[i] = marker;
	      	//imGMapObj.addOverlay(marker);
			bounds.extend(point);
		});
		imGMapObj.fitBounds(bounds);
  		imGMapObj.setCenter(bounds.getCenter());
		/*if (aLatLng.length == 1) {
			if (opts.zoom_level) {
				
			}
		}*/
    };
	
	function createMarker(point, index, format_adr, adr_comp, bus_name, phone, url, desc, images) {
		var markerOptions = getMarkerOptions(index, point);
	  	//markerOptions = { icon: icon[0], shadow: icon[1], draggable:opts.draggable, position: point, map: imGMapObj};
	  	var mrkr = new google.maps.Marker(markerOptions);
		google.maps.event.addListener(mrkr, "click", function() {
			var html = getInfowWindowContent(point, index, format_adr, adr_comp, bus_name, phone, url, desc, images);
		    var infowindowOptions = {
		      content: html
		    }
		    var infowindow = new google.maps.InfoWindow(infowindowOptions);
		    //cm_setInfowindow(infowindow);
		    infowindow.open(imGMapObj, mrkr);
		    //marker.setIcon(markerImageOut);
	  	});
	  	return mrkr;
	};
	
	function getInfowWindowContent(point, index, format_adr, adr_comp, bus_name, phone, url, desc, images) {
		var addr, bname = '', u;
		if (adr_comp.street_address) {
			var str = adr_comp.street_address;
			var cty = adr_comp.locality;
			var ste = adr_comp.administrative_area_level_1;
			var zip = adr_comp.postal_code;	
			addr = str + '<br />' + cty + ', ' + ste + ' ' + zip;
		} else {
			addr = format_adr;
		}
		
		if (phone) {
			addr += '<br />' + phone;
		}
		u = checkHttpPrefix(url, 'add');
		if (url && opts.show_infowindow_url) {
			addr += '<br /><a href="' + u +'" target="_blank" class="infoWindowUrl">' + checkHttpPrefix(url, 'remove') + '</a>';
		}
		if (desc) {
			addr += '<br /><span class="infoWindowDescription">' + desc +'</span>';
		}
		if (bus_name) {
			bname = '<span class="infoWindowBusinessName">'+bus_name+'</span><br />';
			if (url && opts.show_infowindow_url == false) {
				bname = '<span class="infoWindowBusinessName"><a href="' + u +'" target="_blank">' + bus_name + '</a></span><br />';
			} 
		}
	    
		var mhtml = '<p class="infoWindowStreetAddress">';
		mhtml += bname + addr;
		
		if (opts.street_view) {
			imStreetViewPoint[index] = point;
			mhtml += '<br /><a href="javascript:void(0)" onclick="toggleStreetView('+index+');">Street View</a>';
		} 
		if (opts.show_directions) {
			var mode = '<select id="imdir-travel-mode"><option value="DRIVING">Driving</option> <option value="WALKING">Walking</option><option value="BICYCLING">Bicycling</option></select>';
			
			mhtml += '<br /><span id="imDirectionsSearchCntnr'+index+'">Directions: <a href="javascript:showDirectionsToSearch('+index+')">To here</a> - <a href="javascript:showDirectionsFromSearch('+index+')">From here</a></span>';
			
			to_htmls[index] = 'Start Address:<form action="javascript:imGMapDirections.getDirections()">' +
			'<input type="text" SIZE=40 MAXLENGTH=40 name="saddr" id="saddr" value="" /><br>' + mode +
			'<INPUT value="Show Directions" TYPE="SUBMIT"><br><input type="hidden" id="daddr" value="' + format_adr + '"/>' +
			'<br /> <a href="javascript:showDirectionsFromSearch('+index+')">Get Directions From Here</a>';
			
			// The info window version with the "from here" form open
			from_htmls[index] = 'End Address:<form action="javascript:imGMapDirections.getDirections()">' +
			'<input type="text" SIZE=40 MAXLENGTH=40 name="daddr" id="daddr" value="" /><br>' + mode +
			'<INPUT value="Show Directions" TYPE="SUBMIT"><br><input type="hidden" id="saddr" value="' + format_adr + '"/>' +
			'<br /> <a href="javascript:showDirectionsToSearch('+index+')">Get Directions To Here</a>';
		} 
		if (images) {
			var im = '';
			var theImage = '';
			$.each(images, function(i, itm){
				var cls = (itm.class_name) ? 'class="'+itm.class_name+'" ' : '';
				if ((itm.width) && (itm.height)) {
					theImage = '<img ' + cls + 'src="' + itm.image_loc + '" width=' + itm.width + ' height=' + itm.height + ' />';
				} else {
					theImage = '<img ' + cls + 'src="' + itm.image_loc + ' />';
				}	
				im += (itm.url) ? '<a href="' + itm.url + '" target="_blank">' + theImage + '</a>' : theImage;	
				if (itm.new_line) {
					im += '<br />';
				}
			});
			mhtml += '<br />' + im + '</p>';
		} else {
			mhtml += '</p>';
		}	
		return mhtml;
	};
	
	function checkHttpPrefix(url, action) {
		var u = url;
		if (url.length != 0) {
			if (action == 'add') {
				if (url.substr(0, 7) != 'http://') {
					u = 'http://' + url;
				}
			} else if (action == 'remove') {
				if (url.substr(0, 7) == 'http://') {
					u = url.substr(7, url.length);
				}
			}
		}	
		return u;
	};
	
	function getMarkerOptions(index, point) {
		//var iconOptions = {};
		var icon, shadow, imageMap, shape, mOptions;
		var base_url = 'http://chart.apis.google.com/chart?chst=';
		//var il = 'd_map_pin_letter';
		//var ii = 'd_map_pin_icon';
		var width, height, s_width, s_height, iconColor, starColor, label, labelColor, colors, icon_url;
		if (aIconOptions.length != 0) {
			mOptions = aIconOptions[index][0];
		} else if (opts.custom_marker) {
			mOptions = opts.custom_marker;
		} else {
			return {draggable: opts.draggable, position: point, map: imGMapObj};
		} 
		//width = (mOptions.width) ? mOptions.width : 21;23
		//height = (mOptions.height) ? mOptions.height : 34;39
		iconColor = (mOptions.icon_color) ? stripHex(mOptions.icon_color) : 'FD766A';
		starColor = (mOptions.star_color) ? stripHex(mOptions.star_color) : 'FFFFFF';
		//label = MapIconMaker.escapeUserText_(opts.label) || "";
		label = getIconLabel(index, mOptions);
  		labelColor = (mOptions.label_color) ? stripHex(mOptions.label_color) : '000000';
		colors = '|' + iconColor;
		if (mOptions.type == 'standard') {
			colors += '|' + labelColor;
			icon_url = (mOptions.star_color) ? base_url + 'd_map_xpin_letter&chld=pin_star|' : base_url + 'd_map_pin_letter&chld=';
			icon_url += label + colors; 
			if (mOptions.star_color) {
				width = 23;
				height = 39;
				s_width = 45;
				s_height = 42;
				icon_url += '|' + starColor;
			} else {
				width = 21;
				height = 34;
				s_width = 40;
				s_height = 37;
			}
			var shadow_url = (mOptions.star_color) ? base_url + 'd_map_xpin_shadow&chld=pin_star' : base_url + 'd_map_pin_shadow';
			icon = new google.maps.MarkerImage(icon_url, 
					new google.maps.Size(width, height),
					new google.maps.Point(0,0),
					new google.maps.Point(width/2, height));
			shadow = new google.maps.MarkerImage(shadow_url, 
					new google.maps.Size(s_width, s_height),
					new google.maps.Point(0,0),
					new google.maps.Point(0, s_height));
			
			shape = getImageMapShape(mOptions.type, width, height, '');
			return { icon: icon, shadow: shadow, shape: shape, draggable:opts.draggable, position: point, map: imGMapObj};
		} else if (mOptions.type == 'flat') {
			width = (mOptions.width) ? mOptions.width : 32;
			height = (mOptions.height) ? mOptions.height : 32;
			//var shadowColor = "#000000";
			colors = "&chco=" + iconColor + "," + "000000" + "ff,ffffff01";
			label = (label == '+') ? '' : label;
			labelSize = (mOptions.label_size) ? mOptions.label_size : 14;
			shape = (mOptions.shape) ? mOptions.shape : "circle"; //roundrect
			var shapeCode = (shape == "circle") ? "it" : "itr";
			base_url = "http://chart.apis.google.com/chart?cht=" + shapeCode;
			icon_url = base_url + "&chs=" + width + "x" + height + colors + "&chl=" + label + "&chx=" + labelColor + "," + labelSize + "&chf=bg,s,00000000" + "&ext=.png";
			
			icon = new google.maps.MarkerImage(icon_url, 
					new google.maps.Size(width, height),
					new google.maps.Point(0, 0),
					new google.maps.Point(width/2, height/2));
			
			//shape = getImageMapShape(mOptions.type, width, height, shapeCode);
			return { icon: icon, draggable:opts.draggable, position: point, map: imGMapObj};
		} else if (mOptions.type == 'user_image') {
			if ((mOptions.width) && (mOptions.height)) {
				icon = new google.maps.MarkerImage(mOptions.image_loc, 
				new google.maps.Size(mOptions.width, mOptions.height),
				new google.maps.Point(0,0),
				new google.maps.Point(mOptions.width, mOptions.height/2));
			} else {
				icon = new google.maps.MarkerImage(mOptions.image_loc);
			}
			return { icon: icon, draggable: opts.draggable, position: point, map: imGMapObj};
		} 
	};
	
	function stripHex(c) {
		return c.replace("#", "");
	};
	
	function getIconLabel(index, mOptions) {
		if (aLatLng.length > 99) {
			return '+';
		}
		return (mOptions.label == 'alpha') ? String.fromCharCode("A".charCodeAt(0) + index) : index;  
	};
	
	function getImageMapShape(t, width, height, shapeCode) {
		var imageMap = [], shape;
		switch (t) {
			case 'standard':
				imageMap = [
				    width / 2, height,
				    (7 / 16) * width, (5 / 8) * height,
				    (5 / 16) * width, (7 / 16) * height,
				    (7 / 32) * width, (5 / 16) * height,
				    (5 / 16) * width, (1 / 8) * height,
				    (1 / 2) * width, 0,
				    (11 / 16) * width, (1 / 8) * height,
				    (25 / 32) * width, (5 / 16) * height,
				    (11 / 16) * width, (7 / 16) * height,
				    (9 / 16) * width, (5 / 8) * height
				];	
				for (var i = 0; i < imageMap.length; i++) {
				    imageMap[i] = parseInt(imageMap[i]);
				}
				shape = {
					coord: imageMap,
					type: 'poly'
				};
				break;
			case 'flat':
				if (shapeCode == "itr") {
				    imageMap = [0, 0, width, 0, width, height, 0, height];
					shape = {
						coord: imageMap,
						type: 'poly'
					};
				} else {
				    var polyNumSides = 8;
				    var polySideLength = 360 / polyNumSides;
				    var polyRadius = Math.min(width, height) / 2;
				    for (var a = 0; a < (polyNumSides + 1); a++) {
				      var aRad = polySideLength * a * (Math.PI / 180);
				      var pixelX = polyRadius + polyRadius * Math.cos(aRad);
				      var pixelY = polyRadius + polyRadius * Math.sin(aRad);
				      imageMap.push(parseInt(pixelX), parseInt(pixelY));
				    }
					shape = {
						coord: imageMap,
						type: 'circle'
					};
				} 
				break;	
		}
		return shape;
	};
	
	function getDataString() {
		var str = '';
		if (opts.data) {
			$.each(opts.data, function(i, itm) {
				str += itm.name + "=" + itm.value + "&";							
			});
			//remove last "&"
			str = str.substr(0, (str.length-1));
		}
		return str;
	};
		
	function doAjax(t, u, d, fnBefore, fnSuccess) {
		$.ajax({
			type: t,
			url: u,
			data: d,
			dataType: opts.data_type,
			beforeSend: fnBefore, 
			success: fnSuccess
	 	}); //close $.ajax(
	};
	
	function getLink(t, f, p) {
		var a = document.createElement('a');
		$(a)
		//$('<a></a>)')
			.attr("href", "#")
			.append(t)
			.click(function() {
				var fn = eval(f);
				if (p) {
					fn(p);
				} else {
					fn();
				}
				return false;
			});
			if (opts.mode == 'auto') $(a).css({"color": opts.menu_bar.text, lineHeight: "26px", marginLeft: "5px", textDecoration: "none"});
		return a;
	};
};
	
$.googleMaps.defaults = {
	//mode: 'auto',//manual
	data_url: '',
	data: '',
	data_type: 'json', //text, layer
	address: '',
	map: '',
	lat_lng: '',
	show_directions: true,
	draggable: false, //whether markers are draggable
	street_view: '',
	street_close_loc: '',
	show_streetview_overlay: false,
	show_traffic: false,
	show_bicycling: false,
	show_more_button: false,
	progress_bar: '',//{container: 'imProgBarCntnr', bar_back_class: '', bar_class: ''},
	geocode_request_rate: 500,
	custom_marker: '',//{'type': 'labeledmarker', 'width': '32', 'height': '32', 'primaryColor': '#FFD766A', 'strokeColor': '#000000', 'labelColor': '#000000', 'starPrimaryColor': '', 'starStrokeColor': '', 'cornerColor': '#FFFFFF', 'shape': 'circle'}
	instructions: {'container': 'googInstruct', 'label': 'Click on the marker to get directions', 'location': 'top'},//bottom
	zoom_level: '',
	show_infowindow_url: true	 
};

})(jQuery);		

imGMapProgressBar = function(cntnr, barBack, pbar) {
	var bb = document.createElement('DIV');
	bb.setAttribute('class', barBack);
	var pb = document.createElement('DIV');
	pb.setAttribute('class', pbar);
	pb.setAttribute('id', pbar);
	pb.style.width = '0px';
	bb.appendChild(pb);
	cntnr.appendChild(bb);
};

// functions that open the directions forms
function showDirectionsToSearch(i) {
	//imDirectionsMarker[i].infowindow.setContent(to_htmls[i]);
	var sp = document.getElementById('imDirectionsSearchCntnr'+i);
	sp.innerHTML = to_htmls[i];
}
function showDirectionsFromSearch(i) {
	//imDirectionsMarker[i].openInfoWindowHtml(from_htmls[i]);
	var sp = document.getElementById('imDirectionsSearchCntnr'+i);
	sp.innerHTML = from_htmls[i];
}
var imGMapDirections = {

	getSelectedTravelMode: function() {
		var r = '';
		var tr = document.getElementById('imdir-travel-mode');
		var value = tr.options[tr.selectedIndex].value;
		if (value == 'DRIVING') {
			r = google.maps.DirectionsTravelMode.DRIVING;
		} else if (value == 'BICYCLING') {
			r = google.maps.DirectionsTravelMode.BICYCLING;
		} else if (value == 'WALKING') {
			r = google.maps.DirectionsTravelMode.WALKING;
		} else {
			alert('Unsupported travel mode.');
		}
		return r;
	},

 /* getSelectedUnitSystem: function() {
    return Demo.unitInput.options[Demo.unitInput.selectedIndex].value == 'metric' ?
        google.maps.DirectionsUnitSystem.METRIC :
        google.maps.DirectionsUnitSystem.IMPERIAL;
  },*/

	getDirections: function() {
		var self = this;
		var fromStr = document.getElementById('saddr').value;
		var toStr = document.getElementById('daddr').value;
		var dirRequest = {
		  origin: fromStr,
		  destination: toStr,
		  travelMode: self.getSelectedTravelMode(),
		  //unitSystem: Demo.getSelectedUnitSystem(),
		  provideRouteAlternatives: true
		};
		var directionsService = new google.maps.DirectionsService();
		var directionsDisplay = new google.maps.DirectionsRenderer();
		directionsService.route(dirRequest, function(response, status){
			if (status != google.maps.DirectionsStatus.OK) {
				alert('Directions failed: ' + status);
				return;
			}
			// Show directions
			var w = window.open("", "w", "width=600,height=500,scrollbars=yes,menubar=yes,location=no,status=no");
			var div = w.document.createElement('div');
			div.setAttribute('id', 'googDirections');
			div.style.width = '600px';
			w.document.body.appendChild(div);
			directionsDisplay.setMap(imGMapObj);			
			directionsDisplay.setPanel(w.document.getElementById('googDirections'));
			directionsDisplay.setDirections(response);
		});
	}
};

imGMapMoreControl.prototype.createCheckBox = function( name, id, check, layer ) { 
	var checkInput;
	var self = this; 
	try { 
		var checkHtml = (check) ? '<input type="checkbox" id="'+id+'" name="'+name+'" checked>' : '<input type="checkbox" id="'+id+'" name="'+name+'">'; 
		checkInput = document.createElement(checkHtml); 
	} catch( err ) { 
		checkInput = document.createElement('input'); 
		checkInput.setAttribute('type', 'checkbox'); 
		checkInput.setAttribute('name', name);
		checkInput.setAttribute('id', id);
		if ( check ) { 
			checkInput.setAttribute('checked', 'checked'); 
		} 
	}
	/*radioInput.onclick = function() {
			console.log('hello');
		    //self.toggleLayer(this, layers[i].obj);
		}*/ 
	return checkInput;
};

function imGMapMoreControl(cntnr, layers) {
	var self = this;
	var ln = layers.length;
	var ch;

	var controlUI = document.createElement('DIV');
	controlUI.style.backgroundColor = 'white';
	controlUI.style.width = '6em';
	controlUI.style.borderStyle = 'solid';
	controlUI.style.borderWidth = '1px';
	controlUI.style.marginTop = '6px';
	controlUI.style.cursor = 'pointer';
	controlUI.style.textAlign = 'center';
	controlUI.title = 'Show/Hide Layers';
	
	var controlText = document.createElement('DIV');
	controlText.style.fontFamily = 'Arial,sans-serif';
	controlText.style.fontSize = '12px';
	controlText.style.paddingLeft = '4px';
	controlText.style.paddingRight = '4px';
	controlText.innerHTML = '<b>More...</b>';
	controlUI.appendChild(controlText);
	cntnr.appendChild(controlUI);
	
	var checkCntnr = document.createElement('DIV');
	checkCntnr.setAttribute('id', 'imGMapCheckCntnr');
	checkCntnr.style.position = 'absolute';
	checkCntnr.style.top = '24px'; 
	checkCntnr.style.left = '0px';
	checkCntnr.style.marginTop = '-1px';
	checkCntnr.style.fontSize = '12px';
	checkCntnr.style.padding = '6px 4px';
	checkCntnr.style.width = '120px';
	checkCntnr.style.backgroundColor = '#fff';
	checkCntnr.style.color =  '#000';
	checkCntnr.style.border = '1px solid gray';
	checkCntnr.style.borderTop = '1px solid #e2e2e2';
	checkCntnr.style.display = 'none';
	checkCntnr.style.cursor = 'default';
	cntnr.appendChild(checkCntnr);
	
	
	for (var i = 0; i < ln; i++) {
		rd = this.createCheckBox('imGMapChkControl', 'imGMapChkControl'+i, layers[i].checked, layers[i].obj);
		checkCntnr.appendChild(rd);
		
		checkCntnr.innerHTML += layers[i].name;
		if (i < ln - 1) {
			checkCntnr.innerHTML += '<br />';
		}
	}
	
	google.maps.event.addDomListener(cntnr, 'click', function() {
	    self.toggleMenu();
	});

}

imGMapMoreControl.prototype.toggleLayer = function(el, layer) {
	if(el.checked) {
		layer.setMap(imGMapObj);
	} else {
		//layer.setMap(imGMapObj);
	}
}
	
imGMapMoreControl.prototype.toggleMenu = function() {
	var el = document.getElementById('imGMapCheckCntnr');
	el.style.display = (el.style.display == 'block') ? 'none' : 'block';
}


/* Street view */
function toggleStreetView(index) {
	if (index == -1) {
		document.getElementById( imMapObj.streetId ).style.display = 'none';
		document.getElementById( 'imStreetViewClose' ).style.display = 'none';
        document.getElementById( imMapObj.mapId ).style.display = 'block';
	} else {
		//var panorama = new google.maps.StreetViewPanorama(document.getElementById(imMapObj.streetId));
		var streetview_srvc = new google.maps.StreetViewService();
		streetview_srvc.getPanoramaByLocation(imStreetViewPoint[index], 30, function (data, status) {
			if (status == 'OK') {
				document.getElementById( imMapObj.streetId ).style.display = 'block';
				document.getElementById( 'imStreetViewClose' ).style.display = 'block';
	            document.getElementById( imMapObj.mapId ).style.display = 'none';  
				var panoramaOptions = {
				position: data.location.latLng,
					pov: {
					    heading: 0,
					    pitch: 0,
					    zoom: 1
					}
				};
				var panorama = new google.maps.StreetViewPanorama(document.getElementById( imMapObj.streetId ), panoramaOptions);
				imGMapObj.setStreetView(panorama);
			} else {
				alert('Street View is not available for this location');
			}
		});
	}
}