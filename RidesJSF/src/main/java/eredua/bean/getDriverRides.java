package eredua.bean;

import java.util.List;
import java.util.Vector;

import businessLogic.BLFacade;
import domain.Ride;

public class getDriverRides {
	private List<String> drivers;
	private String driver;
	private BLFacade facadeBL;
	private List<Ride> rides= new Vector<Ride>();
	private static String bilatzekoa;
	
	public getDriverRides () {
		try {
			facadeBL = FacadeBean.getBusinessLogic();
		} catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
		}
		drivers=facadeBL.getDrivers();
		driver=drivers.getFirst();
	}
	
	public String bilatu() {
		bilatzekoa=driver;
		getRidesWithEmail(bilatzekoa);
		return "table";
	}
	
	public void getRidesWithEmail(String driver) {
		rides=facadeBL.getRidesWithEmail(driver);
	}

	public List<String> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<String> drivers) {
		this.drivers = drivers;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public static String getBilatzekoa() {
		return bilatzekoa;
	}

	public static void setBilatzekoa(String bilatzekoa) {
		getDriverRides.bilatzekoa = bilatzekoa;
	}
	
	public String goBack() {
		return "atzera";
	}
	
	public String goBack2() {
		return "atzera2";
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	
}
