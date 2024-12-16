package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


import dataAccess.HibernateDataAccess;
import domain.Ride;
import domain.Driver;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
//@WebServie(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	HibernateDataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new HibernateDataAccess();
		    
		//dbManager.close();

		
	}
	
    /*public BLFacadeImplementation(HibernateDataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}*/
    
    
	
	/**
     * {@inheritDoc}
     */ public boolean login(String iz, String ps){	
		
		 return dbManager.login(iz, ps);
    	
    }
    /**
     * {@inheritDoc}
     */ public List<String> getDepartCities(){	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */ public List<String> getDestinationCities(String from){
		 List<String> targetCities=dbManager.getArrivalCities(from);		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail);		
		
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	public List<Ride> getRides(String from, String to, Date date){
		List<Ride>  rides=dbManager.getRides(from, to, date);
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		
		return dates;
	}
	
	
	/*public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}*/

	/**
	 * {@inheritDoc}
	 */
	public void initializeBD(){
    	
		dbManager.initializeDB();
	}

	@Override
	public boolean userExists(String username) {
		return dbManager.userExists(username);
	}

	@Override
	public void createUser(String username, String password) {
		
		dbManager.createUser(username, password);
	}

	@Override
	public boolean deleteUser(String username) {
		return dbManager.deleteUser(username);
	}

	@Override
	public List<String> getDrivers() {
		return dbManager.getDrivers();
	}

	@Override
	public List<Ride> getRidesWithEmail(String driver) {
		return dbManager.getRidesWithEmail(driver);
	}	

}

