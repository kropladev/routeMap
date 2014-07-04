package dev.kropla.controllers;

import static dev.kropla.tools.Configs.log;
import static dev.kropla.tools.Configs.logDev;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

import dev.kropla.dao.Database;
import dev.kropla.dao.IOrdersDao;
import dev.kropla.dto.OrdersAndroidObject;
import dev.kropla.model.ProjectManager;

@Controller
public class AndroidConnections {
	
	private Sender sender;
	/***
	 * www test url:  http://localhost:8080/routeMap3/gsmsendmsg.htm?msg=test2
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/gcmsendmsg.htm", method = RequestMethod.GET)
	public @ResponseBody
	String sendGcmMessage(@RequestParam(value = "orderId") String orderId,@RequestParam(value = "vehicleId") String vehicleId)  {
		log.info("[CONTROLLER2] method sendGcmMessage");
		int vehId=Integer.parseInt(vehicleId);
        ProjectManager pm= new ProjectManager();
        Database database = new Database();
        String registrationId;
        Result result = null;
		try {
			registrationId = pm.getRegIdForUserByVehicleId(database.getConnection(),vehId);
			log.debug("REGID:"+registrationId);
		
			//String registrationId = "APA91bEwV5oWgzUyrm93EY6b5TOPh0gp10msK2XiyJlAMrHWpHMP9xrVXZJVUEoolUyHF4GAX3n4NRT1PON3Cclo6xm957syw3DNkOHx0jZ54cRyOwrdVZ-6f8bqSEcfsD25p6J2zv5f4grXhGwEnfGQ98fFkG4AkA";
			Message msg = new Message.Builder().addData("type", "order").addData("orderId", orderId).addData("vehicleId", vehicleId).build();
        	log.debug(msg.toString());
        	sender = new Sender("AIzaSyBkGb9UzriP495DseWMbyabMOD4e1vDhI0");
			result = sender.send(msg, registrationId, 5);
			log.debug("resultMessageId::"+result.getMessageId());
			log.debug("resultErrCode::"+result.getErrorCodeName());
			log.debug("result obj.ToString()" +result.toString());
		} catch (IOException e) {
			//log.error(result.getErrorCodeName());
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
       // results = Arrays.asList(result);
		return "OK";
	}
	
	@RequestMapping(value="/confirmOrder.htm",method=RequestMethod.POST)
	public @ResponseBody String confirmOrder(@RequestParam(value="orderId") String orderId, @RequestParam(value="vehicleId") String vehicleId, @RequestParam(value="status") int status ){
		log.info("[CONTROLLER] method confirmOrder - START with params:: orderId:"+orderId+"|vehicleId:"+vehicleId+"|status:"+status);
		//Gson gson = new Gson();
		String jsonReturnText="";
		try {
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			//userAcc.setRegId(regId);
			jsonReturnText = projectManager.confirmOrder(
					database.getConnection(), Integer.parseInt(orderId),Integer.parseInt(vehicleId), status);
			log.debug("[CONTROLLER] method confirmOrder result:"+jsonReturnText);
			
		} catch(Exception e){
			logDev.error(e.getMessage());
			return null;
		}
		return jsonReturnText;
	}
	
	@RequestMapping(value="/orderDataById.htm",method=RequestMethod.POST)
	public @ResponseBody String dataOfOrderById(@RequestParam(value="orderId") String orderId){
		log.info("[CONTROLLER] method dataOfOrderById - START with params:: regId:"+orderId);
		Gson gson = new Gson();
		//OrdersAndroidObject = gson.fromJson(userAccStr, UserAccount.class);
		OrdersAndroidObject orderObj= new OrdersAndroidObject();
		orderObj.setOrderId(Integer.parseInt(orderId));
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		IOrdersDao ordersDao = (IOrdersDao)context.getBean("ordersDao");
		//String jsonReturnText="";
		try {
/*			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			orderObj = projectManager.orderDataAndroid(
					database.getConnection(), orderObj);*/
			//InitialContext ctx = new InitialContext();
			//IOrdersDao orders = new OrdersDao();
			
			orderObj=ordersDao.dataOfOrderById(Integer.parseInt(orderId));
			log.debug("[CONTROLLER] method dataOfOrderById result:"+orderObj.toString());
			
		} catch(Exception e){
			logDev.error("ERROR");
			e.printStackTrace();
			return null;
		}
		
		return gson.toJson(orderObj);
	}
	
	
	/***
	 * storing registered new android account/device with reqKey from GCM server
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/register.htm",method=RequestMethod.POST)
	public @ResponseBody String registerAccount(@ModelAttribute(value="account") UserAccount account){
		log.info("[CONTROLLER] method registerAccount - [/register.htm POST] ");
		//String returnText="OK";
		logDev.debug("register account app: " + account.getEmail() );
		
		try {
			//res.setStatus("SUCCESS");
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			/*String result =*/ projectManager.insertNewUserAccount(
					database.getConnection(), account);
		} catch(Exception e){
			logDev.error(e.getMessage());
		}
		
		return null;
	}
	
	/***
	 * storing registered new android account/device with reqKey from GCM server
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/registerRegId.htm",method=RequestMethod.POST)
	public @ResponseBody String registerGCMAccount(@RequestParam(value="regId") String regId, @RequestParam(value="userAcc") String userAccStr){
		log.info("[CONTROLLER] method registerGCMAccount - START with params:: regId:"+regId+ " || userAcc: "+userAccStr);
		Gson gson = new Gson();
		UserAccount userAcc = gson.fromJson(userAccStr, UserAccount.class);
		log.info("[CONTROLLER] method registerGCMAccount - after deserialisation userAcc.DriversId:"+ userAcc.getDriversId());
		log.info("[CONTROLLER] method registerGCMAccount - [/registerRegId.htm POST] ");
		String returnText="";
	//	logDev.debug("register account app: " + account.getEmail() );
		
		try {
			//res.setStatus("SUCCESS");
			//logDev.debug(orderForm.toString());
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			userAcc.setRegId(regId);
			returnText = projectManager.registerGCM(
					database.getConnection(), userAcc);
			log.debug("[CONTROLLER] method registerGCMAccount result:"+returnText);
		} catch(Exception e){
			logDev.error(e.getMessage());
			return returnText;
		}
		
		return returnText;
	}
	
	/***
	 * storing registered new android account/device with reqKey from GCM server
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/userAccountData.htm",method=RequestMethod.POST)
	public @ResponseBody String userAccountInfo(@RequestParam("accountId") int accountId){
		log.info("[CONTROLLER] method userAccountInfo - [/userAccountData.htm POST] ");
		System.out.println("[CONTROLLER] method userAccountInfo - [/userAccountData.htm POST] ");
		String objectList="";
		try {
			UserAccount userAccountData=null;
			Database database = new Database();
			ProjectManager projectManager = new ProjectManager();
			userAccountData = projectManager.userAccountInfo(
					database.getConnection(), accountId);
			Gson gson = new Gson();
			objectList = gson.toJson(userAccountData);
			//objectList="{\"UserAccountData\":".concat(objectList).concat("}");
			System.out.println("userAccountInfo: "+ objectList);
		} catch(Exception e){
			logDev.error(e.getMessage());
		}

		return objectList;
	}
	

}
