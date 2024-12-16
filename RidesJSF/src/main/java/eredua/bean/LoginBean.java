package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;

public class LoginBean {
    private BLFacade facadeBL;

    private String username;
    private String password;
    
    public LoginBean() {
    	try {
			facadeBL = FacadeBean.getBusinessLogic();
		} catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
		}
    }

    public String login() {
    	if (username.length()==0 || password.length()==0) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Izena eta pasahitza sartu behar dira"));
    		return null;
    	}
    	if (!facadeBL.userExists(username)) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erabiltzaile izena ez da zuzena"));
    		return null;
    	}
        
    	if (facadeBL.login(username, password)) {
    		if (username.equals("admin")) {
          		Hasiera.setCR(true);
          		DeleteUser.setAdmin(true);
          	} else {
          		DeleteUser.setAdmin(false);
          	}
    		Hasiera.setLO(false);           
           	Hasiera.setQR(true);
           	Hasiera.setSI(false);
           	Hasiera.setActiveUser(username);
           	return "ok";
        } else {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pasahitza ez da zuzena"));
    		return null;
        }
                
    }
    
    public String goBack() {
		return "atzera";
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}