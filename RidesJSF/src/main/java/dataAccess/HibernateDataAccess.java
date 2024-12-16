package dataAccess;

import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;

import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import domain.User;
import eredua.HibernateUtil;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class HibernateDataAccess {
	// private EntityManager db;
	// private EntityManagerFactory emf;

	// ConfigXML c=ConfigXML.getInstance();

	public HibernateDataAccess() {
		/*
		 * if (c.isDatabaseInitialized()) { String fileName=c.getDbFilename();
		 * 
		 * File fileToDelete= new File(fileName); if(fileToDelete.delete()){ File
		 * fileToDeleteTemp= new File(fileName+"$"); fileToDeleteTemp.delete();
		 * 
		 * System.out.println("File deleted"); } else {
		 * System.out.println("Operation failed"); } } open(); if
		 * (c.isDatabaseInitialized())initializeDB();
		 * 
		 * System.out.println("DataAccess created => isDatabaseLocal: "+c.
		 * isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());
		 * 
		 * close();
		 */
		initializeDB();

	}

	/*
	 * public HibernateDataAccess(EntityManager db) { this.db=db; }
	 */

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	
	  public void initializeDB(){
	  
		  //db.getTransaction().begin();
		  Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		  session.beginTransaction();
	  	  try {
	  
	  		  Calendar today = Calendar.getInstance();
	  
	  		  int month=today.get(Calendar.MONTH); 
	  		  int year=today.get(Calendar.YEAR); 
	  		  if(month==12) {
	  			  month=1; 
	  			  year+=1;
	  		  }
	  		  
	  
	  		  //Create drivers 
	  		  Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez"); 
	  		  Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga"); 
	  		  Driver driver3=new Driver("driver3@gmail.com","Test driver");
	  		  
	  		  //Create users
	  		  User u1= new User("Dani", "password", "mota"); //sartuta
	  		  User u2= new User("admin", "admin", "mota");  //sartuta
	  
	  
	  		  //Create rides 
	  		  driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7); 
	  		  driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8); 
	  		  driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);
	  
	  		  driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
	  
  		      driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
  		      driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
	  		  driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);
	  
	  		  driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);
	  		  
	  		  //session.persist(u1);
	  		  //session.persist(u2);
	  		  
	  		  //session.persist(driver1);
	  		 // session.persist(driver2);  
	  		  //session.persist(driver3);
	  		  
	  		  session.getTransaction().commit(); 
	  		  System.out.println("Db initialized"); 
	  	  } catch (Exception e){ 
	  		  System.out.println("Petó"); 
	  		  e.printStackTrace(); 
	  	  } 
	}

	public boolean login(String izena, String pasahitza) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery(
				"select e.username from User e where username= :izena and passwd= :pasahitza");
		q.setParameter("izena", izena);
		q.setParameter("pasahitza", pasahitza);
		List result = q.list();
		session.getTransaction().commit();
		return !result.isEmpty();
	}
	
	public boolean userExists(String izena) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("FROM User WHERE username= :erab");
		query.setParameter("erab", izena);
		List<User> users = query.list();
		try {
			User u=users.getFirst();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().commit();
			return false;
			
		}
	}

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	public List<String> getDepartCities() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		// DBekiko eragiketak (transakzioa osatzen dutenak)
		Query query = session.createQuery("SELECT DISTINCT nondik FROM Ride ORDER BY nondik");
		List<String> cities = query.list();
		session.getTransaction().commit();
		return cities;
	}

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		// DBekiko eragiketak (transakzioa osatzen dutenak)
		Query query = session.createQuery("SELECT DISTINCT r.too FROM Ride r WHERE r.nondik= :from ORDER BY r.too");
		query.setParameter("from", from);
		List<String> arrivingCities = query.list();
		session.getTransaction().commit();
		return arrivingCities;
	}

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from        the origin location of a ride
	 * @param to          the destination location of a ride
	 * @param date        the date of the ride
	 * @param nPlaces     available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		System.out.println(">> DataAccess: createRide=> nondik= " + from + " to= " + to + " driver=" + driverEmail
				+ " date " + date);
		try {
			if (new Date().compareTo(date) > 0) {
				throw new RideMustBeLaterThanTodayException(
						ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}

			session.beginTransaction();
			// DBekiko eragiketak (transakzioa osatzen dutenak)

			Query query = session.createQuery("FROM Driver WHERE email= :email");
			query.setParameter("email", driverEmail);
			List<Driver> drivers = query.list();
			Driver driver = drivers.getFirst();
			if (driver.doesRideExists(from, to, date)) {
				session.getTransaction().commit();
				throw new RideAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			session.persist(driver);
			session.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			session.getTransaction().commit();
			return null;
		}

	}

	/**
	 * This method retrieves the rides from two locations on a given date
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	
	public List<Ride> getRides(String from, String to, Date date) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();
		// DBekiko eragiketak (transakzioa osatzen dutenak)
		System.out.println(">> DataAccess: getRides=> nondik= " + from + " to= " + to + " date " + date);

		List<Ride> res = new Vector<>();
		Query query = session.createQuery("SELECT r FROM Ride r WHERE r.nondik= :from AND r.too= :too AND r.data= :data");
		query.setParameter("from", from);
		query.setParameter("too", to);
		query.setParameter("data", date);
		List<Ride> rides = query.list();
		for (Ride ride : rides) {
			System.out.println(ride.toString());
			res.add(ride);
		}
		session.getTransaction().commit();
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		// DBekiko eragiketak (transakzioa osatzen dutenak)

		Query query = session.createQuery(
				"SELECT DISTINCT r.date FROM Ride r WHERE r.nondik=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4");

		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.list();
		for (Date d : dates) {
			res.add(d);
		}
		session.getTransaction().commit();
		return res;
	}

	public void createUser(String username, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User u= new User(username, password, "mota");
		session.persist(u);
		session.getTransaction().commit();
	}

	public boolean deleteUser(String username) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.createQuery("delete from User where username = :id")
			.setParameter("id", username)
	        .executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().commit();
			return false;
		}
		
	}

	public List<String> getDrivers() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query=session.createQuery("SELECT email FROM Driver");			
			List<String> drivers=query.list();
			session.getTransaction().commit();
			return drivers;
		} catch(Exception e) {
			session.getTransaction().commit();
			return null;
		}
	}

	public List<Ride> getRidesWithEmail(String driver) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		System.out.println(driver);
		List<Ride> rides = new Vector<>();
		Query query = session.createQuery("FROM Ride WHERE driver_email= :mail");
		query.setParameter("mail", driver);
		rides = query.list();
		session.getTransaction().commit();
		return rides;
	}

	/*
	 * public void open(){
	 * 
	 * String fileName=c.getDbFilename(); if (c.isDatabaseLocal()) { emf =
	 * Persistence.createEntityManagerFactory("objectdb:"+fileName); db =
	 * emf.createEntityManager(); } else { Map<String, String> properties = new
	 * HashMap<>(); properties.put("javax.persistence.jdbc.user", c.getUser());
	 * properties.put("javax.persistence.jdbc.password", c.getPassword());
	 * 
	 * emf =
	 * Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+
	 * c.getDatabasePort()+"/"+fileName, properties); db =
	 * emf.createEntityManager(); }
	 * System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal
	 * ());
	 * 
	 * 
	 * }
	 */

	/*
	 * public void close(){ db.close(); System.out.println("DataAcess closed"); }
	 */

}
