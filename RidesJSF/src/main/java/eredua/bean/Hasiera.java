package eredua.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "hasiera")
@SessionScoped
public class Hasiera {
	private static boolean QR=false;
	private static boolean CR=false;
	private static boolean LO=true;
	private static boolean SI=true;
	private static String activeUser;
	
	public String create() {
		return "create";
	}
	
	public String query() {
		return "query";
	}
	
	public String login() {
		return "login";
	}
	
	public String signIn() {
		return "signin";
	}
	
	public String delete() {
		return "delete";
	}
	
	
	public String informazioa() {
		if(this.activeUser=="admin")return "admininfo";
		else return "userinfo";
	}
	
	public String gidariarenBidaiak() {
		return "driverRides";
	}
	
	public String logOut() {
		this.setCR(false);
		this.setQR(false);
		this.setLO(true);
		this.setSI(true);
		return null;
	}

	public boolean isQR() {
		return QR;
	}

	public static void setQR(boolean qR) {
		QR = qR;
	}

	public boolean isCR() {
		return CR;
	}

	public static void setCR(boolean cR) {
		CR = cR;
	}

	public boolean isLO() {
		return LO;
	}

	public static void setLO(boolean lO) {
		LO = lO;
	}

	public boolean isSI() {
		return SI;
	}

	public static void setSI(boolean sI) {
		SI = sI;
	}

	public static String getActiveUser() {
		return activeUser;
	}

	public static void setActiveUser(String activeUser) {
		Hasiera.activeUser = activeUser;
	}

	

}
