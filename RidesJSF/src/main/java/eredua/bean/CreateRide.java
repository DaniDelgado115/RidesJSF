package eredua.bean;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Ride;


public class CreateRide {
	private BLFacade facadeBL;
	private String dcity;
	private String acity;
	private int seats;
	private float price;
	private Date data;
	private List<String> drivers;
	private String driver;
	

	public CreateRide() {
		try {
			facadeBL = FacadeBean.getBusinessLogic();
		} catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
		}
		drivers=facadeBL.getDrivers();
		driver=drivers.getFirst();
	}

	public String getDcity() {
		return dcity;
	}

	public void setDcity(String dcity) {
		this.dcity = dcity;
	}

	public String getAcity() {
		return acity;
	}

	public void setAcity(String acity) {
		this.acity = acity;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String egiaztatu() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (acity == null || acity.length() < 1 || 
                dcity == null || dcity.length() < 1) {
                context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "dena bete behar da"));
                return null;
            }

            Ride r = facadeBL.createRide(dcity, acity, data, seats, price, driver);
            context.addMessage(null, 
                new FacesMessage("Ride sortuta: " + r.toString()));
        } catch (Exception e) {
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Error", e.getMessage()));
            return null;
        }
		return null;
    }
	
	public String goBack() {
		return "atzera";
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
	
	/*public void onDateSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data aukeratua: " + event.getObject()));
	}*/

}
