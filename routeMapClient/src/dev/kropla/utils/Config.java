package dev.kropla.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import dev.kropla.dto.UserAccount;

public class Config {
	public static final String MSG_LOGOFF_MESSAGE="Czy chcesz się wylogować z aplikacji?";
	public static final String MSG_LOGOFF_TITLE="Wyloguj";
	public static final String MSG_YES="Tak";
	public static final String MSG_NO="Nie";
	public static final String MSG_OK="Ok";
	public static final String STATUS_LOGGED="1"; //free
	public static final String STATUS_TAKEN="2"; 
	public static final String STATUS_LOGOFF="3"; 
	public static final String STATUS_REGISTER="4";
	
	public static final String URL_1="http://"; 
	//public static String hostAddress="kropladev.pl";
	//public static String hostAddress="10.0.1.12:8080";
	//public static final String HOST_ADDRESS="10.0.1.13:8080";
	public static final String HOST_ADDRESS="kropladev.pl";
	public static final String MSG_APP_ERROR_SERVLET_TITLE = "Błąd połączenia do serwera";
	public static final String MSG_APP_ERROR_SERVLET_MESSAGE = "Aplikacja nie może poprawnie połączyć się z serwerem. Spróbuj ponownie za chwilę.";
	public static final String MSG_PREPARE_LIST_ERROR = "Błąd parsowania listy zgłoszeń.\nProszę zgłosić sprawę administratorowi.";
	public static final String LABEL_LOGGED_NAME = "Zalogowany: ";
	public static final String MSG_CHANGE_STATUS_ERROR = "Błąd serwera. Zmiana statusu użytkownika nie powiodła się.";
	public static final String MSG_STATUS_CHANGED = "Status zmieniony na ";
	public static final String MSG_DEVICE = "Urządzenie ";
	public static final String MSG_DEVICE_DISABLED = " jest wyłączone. Włącz serwis w celu wysłania informacji o położeniu.";
	public static final String MSG_DEVICE_ENABLED = " jest aktywne.";
	public static final String MSG_USER_NOT_REGISTERED = "Login nieprawidłowy. Brak użytkownika w bazie. Proszę się zarejestrować";
	public static final String MSG_CONNECT_ERROR = "Wystąpił błąd przy połączeniu z serwisem. Spróbuj ponownie za jakiś czas lub skontaktuj się z administratorem systemu";
	public static final String MSG_UNKNOW_ANSWER = "Nieznana odpowiedź serwisu. Spróbuj ponownie za jakiś czas lub skontaktuj się z administratorem systemu.";
	public static final String MSG_LOGIN_IN_PROGRESS = "Logowanie...";
	public static final String MSG_LOGIN_UNAVAILABLE = "Login zajęty. Proszę wprowadzić inną wartość pola LOGIN";
	public static final String MSG_REGISTER_ERROR = "Wystąpił błąd w trakcie rejestracji.Proszę spróbować później. [";
	public static final String MSG_NEW_ACCOUNT_CONFIRMATION = "Nowe konto zarejestrowane z ID=";
	
	public static final String ORDER_STATUS_1="Przyjęte";
	public static final String ORDER_STATUS_2="W drodze do klienta";
	public static final String ORDER_STATUS_3="Na miejscu";
	public static final String ORDER_STATUS_4="W trakcie";
	public static final String ORDER_STATUS_5="Zakończone";
	public static final String ORDER_STATUS_6="Odrzucone";
	
	
	public static final String DRIVERS_ORDERS_LIST_TAG="driverListToDo"; //po tym tagu rozpoznajemy zamowienia do realizacji (select po grupie)
	//public static String ipNumber="10.0.1.10";//siec alfa_blue w domu
	//public static String ipNumber="10.0.2.2"; //emulator na laptopie
	//public static String ipNumber="192.168.43.2"; //android ruter -polaczenie wifi do androida; 
	//public static String ipNumber="192.168.43.2"; //ruter usb
/*    public static String loginName;
    public static String email;
    public static int userId; //driversId
    public static String firstName;
    public static String lastName;
    public static String phone;
    public static String regId;
    public static int vehicleId;*/
	public static UserAccount userAccount;
	public static boolean updateRoutesPosition=false;
	//public static int actualRouteId=0;
	public static Long locationPeriodMinutes=1l;//1 minuta
	public static float locationChnageDistance=0; //10 metrow
	public static int actualRouteId=0;
	public static String convertTime(String time, String format) {
		String[] work=time.split(" ");
		String[] date=work[0].split("-");
		String[] timeTable=work[1].split(":");
		
		//DateFormat df = new SimpleDateFormat(format);
		Calendar cal= new GregorianCalendar();
		Calendar cal2= new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
		String resultDate=timeTable[0].concat(":").concat(timeTable[1]);
		//Date dateNow=cal.getTime();
		
		if (cal2.get(Calendar.YEAR)!=cal.get(Calendar.YEAR) || 
				cal2.get(Calendar.MONTH)!=cal.get(Calendar.MONTH) || 
				cal2.get(Calendar.DAY_OF_MONTH)!=cal.get(Calendar.DAY_OF_MONTH)){
			//Log.d("CONFIG", "CAL2:"+cal2.toString());
		//	Log.d("CONFIG", "CAL:"+cal.toString());
			//String dateAfterFormat=df.format(cal2);
			resultDate=resultDate.concat(" ")
					.concat(Integer.toString(cal2.get(Calendar.DAY_OF_MONTH)))
					.concat("-")
					.concat(Integer.toString(cal2.get(Calendar.MONTH)))
					.concat("-")
					.concat(Integer.toString(cal2.get(Calendar.YEAR)));
		}
		
		return resultDate;
	}
}

