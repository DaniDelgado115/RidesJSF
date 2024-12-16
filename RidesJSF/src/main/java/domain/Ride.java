package domain;

import java.io.*;
import java.util.Date;

import javax.persistence.*;


@SuppressWarnings("serial")
//@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Ride implements Serializable {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rideNumber;
	private String nondik;
	private String too;
	private int nPlaces;
	private Date data;
	private float price;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_email", referencedColumnName = "email", nullable = false)
	private Driver driver;  
	
	public Ride(){
		super();
	}
	
	public Ride(Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver) {
		super();
		this.rideNumber = rideNumber;
		this.nondik = from;
		this.too = to;
		this.nPlaces = nPlaces;
		this.data=date;
		this.price=price;
		this.driver = driver;
	}

	

	public Ride(String from, String to,  Date date, int nPlaces, float price, Driver driver) {
		super();
		this.nondik = from;
		this.too = to;
		this.nPlaces = nPlaces;
		this.data=date;
		this.price=price;
		this.driver = driver;
	}
	
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */
	
	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}


	/**
	 * Get the origin  of the ride
	 * 
	 * @return the origin location
	 */

	public String getNondik() {
		return nondik;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */	
	
	public void setNondik(String origin) {
		this.nondik = origin;
	}

	/**
	 * Get the destination  of the ride
	 * 
	 * @return the destination location
	 */

	public String getToo() {
		return too;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */	
	public void setToo(String destination) {
		this.too = destination;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */
	
	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getData() {
		return data;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setData(Date date) {
		this.data = date;
	}

	
	public float getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  nPlaces places to be set
	 */

	public void setBetMinimum(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}



	public String toString(){
		return rideNumber+";"+nondik+";"+too+";"+data;  
	}




	
}
