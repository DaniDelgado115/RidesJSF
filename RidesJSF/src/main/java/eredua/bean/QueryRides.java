package eredua.bean;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import businessLogic.BLFacade;
import domain.Ride;

public class QueryRides {
	private BLFacade facadeBL;
	private List<Ride> rides= new Vector<Ride>();
	private List<String> dcities;
	private List<String> acities;
	private String dcity;
	private String acity;
	private Date data;
	
	
	public QueryRides() {
		try {
			facadeBL = FacadeBean.getBusinessLogic();
		} catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
		}
		dcities=facadeBL.getDepartCities();
		dcity=dcities.getFirst();
		acities=facadeBL.getDestinationCities(dcity);
		acity=acities.getFirst();
	}
	public void onLoad() {
		dcities=facadeBL.getDepartCities();
		dcity=dcities.getFirst();
    }
	
	
	
	public List<Ride> getRides() {
		return rides;
	}



	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}



	public List<String> getDcities() {
		return dcities;
	}



	public void setDcities(List<String> dcities) {
		this.dcities = dcities;
	}



	public List<String> getAcities() {
		return acities;
	}



	public void setAcities(List<String> acities) {
		this.acities = acities;
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

	public void updateAC(AjaxBehaviorEvent event) {
		acities=facadeBL.getDestinationCities(dcity);
		acity=acities.getFirst();
	}

	public void bilatu() {
		try {
			rides=facadeBL.getRides(dcity, acity, data);
		} catch (Exception e) {
			
		}
	}

	public Date getData() {
		return data;
	}



	public void setData(Date data) {
		this.data = data;
	}
	
	public String goBack() {
		return "atzera";
	}
	
}
