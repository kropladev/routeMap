package dev.kropla.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

import dev.kropla.dao.Database;
import dev.kropla.dao.OrderTypesDao;
import dev.kropla.dto.DicOrderTypes;
import dev.kropla.dto.DicStatuses;
import dev.kropla.dto.OrdersObject;
import dev.kropla.dto.ShortStats;
import dev.kropla.dto.VehicleDetailsObject;
import dev.kropla.dto.VehicleObjects;
import dev.kropla.dto.VehiclePositionObjects;
import dev.kropla.model.ProjectManager;
import dev.kropla.tools.Configs;
import static dev.kropla.tools.Configs.log;
import static dev.kropla.tools.Configs.logDev;

@Controller
public class MainController {

	
	
	public OrderFormController orderForm= new OrderFormController();
	private String lastListOrdersStatus="active";
	private String lastListOrdersPeriod="lastWeek";	
	// The SENDER_ID here is the "Browser Key" that was generated when I
	// created the API keys for my Google APIs project.
	private static final String SENDER_ID = "AIzaSyBkGb9UzriP495DseWMbyabMOD4e1vDhI0";
	
	// This is a *cheat*  It is a hard-coded registration ID from an Android device
	// that registered itself with GCM using the same project id shown above.
	private static final String ANDROID_DEVICE = "APA91bGHWUabNS9PDoYH3VXpqRQT4jI1wy9RzVR4iLVxn5sl-glvptnX0ULpRVYjyuxDzKdPN4JwpurHqFiu2jovCVDfL4b688jPrNVRQY5ygv-b1UBGzOf2GvSpbIMnP-3cyoriGgZx39mtSY6o-YuyfobKHSrOfQ";
		
	// This array will hold all the registration ids used to broadcast a message.
	// for this demo, it will only have the ANDROID_DEVICE id that was captured 
	// when we ran the Android client app through Eclipse.
	private List<String> androidTargets = new ArrayList<String>();

    @RequestMapping("/test")
     public String welcome()
     {
    	log.info("[CONTROLLER] method welcome - [/test] ");
      return "test";
     }
    
    
    @RequestMapping("/") 
    public String welcome (Model model)
    {
    	log.info("[CONTROLLER] method welcome - [/] ");
    	return "redirect:mainPage"; 
    }
    
    @RequestMapping("/statistic") 
    public String statisticPage (Model model)
    {
    	log.info("[CONTROLLER] method welcome - [/statistic] ");
    	return "statistic";
    }
    
    @RequestMapping("/driversAccount") 
    public String driversAccountPage (Model model)
    {
    	log.info("[CONTROLLER] method welcome - [/driversAccount] ");
    	return "driversAccount";
    }
    
    @RequestMapping("/mainPage")
    public String welcome2(Model model)
    {
    	log.info("[CONTROLLER] method welcome2 - [/mainPage] ");
    	Database db = new Database();
    	try {
    		OrderTypesDao orders= new OrderTypesDao();
    		
    		model.addAttribute("ordersList", orders.loadAllSymbols(db.getConnection()));
    		model.addAttribute("orderForm",new OrderFormController());
    	} catch (Exception e) {

			e.printStackTrace();
    	}
     return "mainPage";
    }
    
	@RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
	public @ResponseBody int orderSubmit(
			@ModelAttribute(value = "orderForm") OrderFormController orderForm,
			Model model) {
		log.info("[CONTROLLER] method orderSubmit - [/makeOrder] ");
		////obsluga Push Notification
		//TODO zmiana targets na wybrany z listy
		androidTargets.clear();
		//TODO pobranie REG_ID z bazy danych z tablicy rm_driver_account
		androidTargets.add(ANDROID_DEVICE);
		
		// We'll collect the "CollapseKey" and "Message" values from our JSP page
		String collapseKey = "";
		String userMessage = "";
		
		collapseKey=orderForm.getOrderName();
		//TODO: utworzyc klase/metode dla uzupelnienia wiadomosci na podstawie danych z formularza- orderForm
		userMessage= "ZAMOWIENIE: "+orderForm.getOrderAddressFrom();
		logDev.debug("orderForm values: "+orderForm.toString());
		logDev.debug("userMessage="+userMessage);
		// Instance of com.android.gcm.server.Sender, that does the
		// transmission of a Message to the Google Cloud Messaging service.
		Sender sender = new Sender(SENDER_ID);
		logDev.debug("  Sender created");
		// This Message object will hold the data that is being transmitted
		// to the Android client devices.  For this demo, it is a simple text
		// string, but could certainly be a JSON object.
		try{
			logDev.debug("   Creating message");
		Message message = new Message.Builder()		
		// If multiple messages are sent using the same .collapseKey()
		// the android target device, if it was offline during earlier message
		// transmissions, will only receive the latest message for that key when
		// it goes back on-line.
		.collapseKey(collapseKey)
		.timeToLive(30)
		.delayWhileIdle(true)
		.addData("message", userMessage)
		.build();
		logDev.debug("    Message created");

	//	try {
			// use this for multicast messages.  The second parameter
			// of sender.send() will need to be an array of register ids.
			logDev.debug("Before sending");
			MulticastResult result = sender.send(message, androidTargets, 1);
			logDev.debug("result: "+result.getResults().toString());
			if (result.getResults() != null) {
				logDev.debug(" getResults() != null ");
				//int canonicalRegId = result.getCanonicalIds();
				int canonicalRegId = result.getCanonicalIds();
				logDev.debug("canonicalRegId:"+canonicalRegId);
				//if (canonicalRegId != null) {
					logDev.debug("canonicalRegId:"+canonicalRegId);
				//}
			} else {
				int error = result.getFailure();
					 logDev.debug("device not registered; error="+error);
			}
				 
			
		} catch(Exception e){
			logDev.error(e.getMessage());
		}
		
		int resultOrderPK = 0;
			try {
				//res.setStatus("SUCCESS");
				logDev.debug(orderForm.toString());
				Database database = new Database();
				ProjectManager projectManager = new ProjectManager();
				resultOrderPK = projectManager.insertNewOrder(
						database.getConnection(), orderForm);
			} catch(Exception e){
				logDev.error(e.getMessage());
			}
		return resultOrderPK;
	}
		
	@RequestMapping(value="/GetListOfDriversForOrder",method=RequestMethod.GET)
	public @ResponseBody String getListOfDriversForOrder(@RequestParam("position") String position) {
		log.info("[CONTROLLER] method getListOfDriversForOrder - [/GetListOfDriversForOrder] ");
		String driversList, status="free";
		try{
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<VehiclePositionObjects> vehiclesData = null;
			vehiclesData = projectManager.GetVehiclePositions(database.getConnection(),status,position);
			Gson gson = new Gson();
			logDev.debug(gson.toJson(vehiclesData));
			driversList = gson.toJson(vehiclesData);
			driversList="{\"DriverList\":".concat(driversList).concat("}");
		}catch (Exception ex) {
			driversList="DB error";
			ex.printStackTrace();
		}
		return driversList;
	}
	/**
	 * deprecated
	 * @param status
	 * @param periodArg
	 * @return
	 */
	@RequestMapping(value="/GetAllOrders",method=RequestMethod.GET)
	public @ResponseBody String getListOfOrders(@RequestParam("status") String status,@RequestParam("period") String periodArg) {
		log.info("[CONTROLLER] method getListOfOrders - [/GetAllOrders] ");
		String objectList;
		if (status!=null){this.lastListOrdersStatus=status;}
		if(periodArg!=null){this.lastListOrdersPeriod=periodArg;}
		
		try{
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<OrdersObject> objectsData = null;
			objectsData = projectManager.GetAllOrdersByDateRange(database.getConnection(),this.lastListOrdersStatus,this.lastListOrdersPeriod);
			Gson gson = new Gson();
			logDev.debug(gson.toJson(objectsData));
			//logger.debug(gson.toJson(vehiclesData));
			objectList = gson.toJson(objectsData);
			objectList="{\"Orders\":".concat(objectList).concat("}");
		}catch (Exception ex) {
			objectList="DB error";
			ex.printStackTrace();
		}
		return objectList;
	}
	
	/**
	 * zwraca liste statusow pojazdow do filtra listy pojazdow w lewym menu przy mapie
	 * @return
	 */
	@RequestMapping(value="/GetDicStatuses.htm",method=RequestMethod.GET)
	public @ResponseBody String getDicStatuses(@RequestParam("statusType") String statusType) {
		log.info("[CONTROLLER] method getDicStatuses - [/GetDicStatuses] ");
		String dicStatuses;
		try {
			logDev.debug("GetDicStatuses servlet START");
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<DicStatuses> vehiclesData = null;
			vehiclesData = projectManager.getDicStatuses(database.getConnection(), statusType);
			
			Gson gson = new Gson();
			logDev.debug(gson.toJson(vehiclesData));
			dicStatuses = gson.toJson(vehiclesData);
			dicStatuses="{\"StatusList\":".concat(dicStatuses).concat("}");
			
			logDev.debug("GetDicStatuses servlet FINISH");
		} catch (Exception ex) {
			dicStatuses="DB error";
			ex.printStackTrace();
		}
		return dicStatuses;
	}
    
	/**
	 * zwraca liste pojazdow do wyswietlenia na mapie
	 * @return
	 */
	@RequestMapping(value="/GetVehicles.htm",method=RequestMethod.GET)
	public @ResponseBody String getVehicleList() {
		log.info("[CONTROLLER] method getVehicleList - [/GetVehicles] ");
		String vehicleList;
		try {
			logDev.debug("GetVehicles servlet START");
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			ArrayList<VehicleObjects> vehiclesData = null;
			vehiclesData = projectManager.GetVehicles(database.getConnection());
			
			Gson gson = new Gson();
			logDev.debug(gson.toJson(vehiclesData));
			vehicleList = gson.toJson(vehiclesData);
			vehicleList="{\"VehicleList\":".concat(vehicleList).concat("}");
			logDev.debug("GetVehicles servlet FINISH");
		} catch (Exception ex) {
			vehicleList="DB error";
			ex.printStackTrace();
		}
		return vehicleList;
	}
	
	/**
	 * zwraca liste pojazdow do wyswietlenia na mapie
	 * @return
	 */
	@RequestMapping(value="/shortstat.htm",method=RequestMethod.GET)
	public @ResponseBody String shortStatistics() {
		log.info("[CONTROLLER] method shortStatistics - [/shortstat] ");
		ShortStats stats= null;
		try {
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			
			stats = projectManager.countShortStats(database.getConnection());	
			Gson gson = new Gson();
			
			logDev.debug("STATS PACK:"+gson.toJson(stats));
			logDev.debug("shortStatistics servlet FINISH");
			return gson.toJson(stats);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "DB error";
		}
	}
	
	
	@RequestMapping(value="/GetAllVehiclesPosition.htm",method=RequestMethod.GET)
	public @ResponseBody String getAllVehiclesPosition() {
		log.info("[CONTROLLER] method getAllVehiclesPosition - [/GetAllVehiclesPosition] ");
		String vehiclePositions;
		try {
			logDev.debug("getAllVehiclesPosition servlet START");
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			 ArrayList<VehiclePositionObjects> vehiclePositionData = null;
			vehiclePositionData = projectManager.GetAllVehiclePositions(database.getConnection());
			
			Gson gson = new Gson();
			logDev.debug("11 getAllVehiclesPosition:"+ gson.toJson(vehiclePositionData));
			vehiclePositions = gson.toJson(vehiclePositionData);
			vehiclePositions="{\"markers\":".concat(vehiclePositions).concat("}");
		} catch (Exception ex) {
			vehiclePositions="DB error";
		}
		return vehiclePositions;
	}

	@RequestMapping(value="/GetDetailInfoVehicle.htm",method=RequestMethod.GET)
	public @ResponseBody String getDetailInfoAboutSingleVehicle(@RequestParam("vehicleId") int vehicleId, Model model) {			
		log.info("[CONTROLLER] method getDetailInfoAboutSingleVehicle - [/GetDetailInfoVehicle.htm] ");
		String vehicleDetailInfo;	
		try {
				logDev.debug("GetDetailInfoVehicleNEW servlet START");
				Database database = new Database();
				ProjectManager projectManager = new ProjectManager();
				VehicleDetailsObject vehicleData = null;
				vehicleData = projectManager.GetVehicleDetailInfo(database.getConnection(),vehicleId);

				Gson gson = new Gson();
				vehicleDetailInfo = gson.toJson(vehicleData);
			} catch (Exception ex) {
				vehicleDetailInfo="DB error";
			}
		logDev.debug("DEBUG  vehicleDetailInfo="+vehicleDetailInfo);
		return vehicleDetailInfo;
		}
	
	//ChangeFilter.htm
	@RequestMapping(value="/ChangeFilter.htm",method=RequestMethod.GET)
	public @ResponseBody String setFilterOptions(@RequestParam("filterOption") String filterOptionsArg, Model model) {			
		log.info("[CONTROLLER] method setFilterOptions - [/ChangeFilter.htm] ");
		String filterOptions="OK";	
	try {
			logDev.debug("setFilterOptions servlet START");
			Configs.filterStatusOption=filterOptionsArg;
		} catch (Exception ex) {
			filterOptions="error";
		}
	logDev.debug("DEBUG  setFilterOptions="+filterOptions);
	return filterOptions;
	}


	
    @SuppressWarnings("unchecked")
	//@ModelAttribute("orderTypes")
    public Map<String, String> populateDepartments(@SuppressWarnings("rawtypes") Map model) {
		log.info("[CONTROLLER] method populateDepartments - [] ");
    	Database db = new Database();
    	try {
    		OrderTypesDao orders= new OrderTypesDao();
    		DicOrderTypes dicOrderTypes=new DicOrderTypes();
    		dicOrderTypes.setRtDicAttribs(orders.loadAllSymbols(db.getConnection()));
    		model.put("orderFormArgs", dicOrderTypes);
    		
    		//model.put("orderTypes", orders.loadAllSymbols(db.getConnection()));
			return orders.loadAllSymbols(db.getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	
}
