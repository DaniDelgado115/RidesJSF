package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;

@ManagedBean(name = "signinBean")
@RequestScoped
public class SignIn {
	private BLFacade facadeBL;
    private String username;
    private String password;
    
    public SignIn () {
    	try {
			facadeBL = FacadeBean.getBusinessLogic();
		} catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
		}
    }

    public String signIn() {
    	if (username.length()==0 || password.length()==0) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Izena eta pasahitza sartu behar dira"));
    		return null;
    	}
    	if (isUsernameTaken(username)) {
    		System.out.println("erabiltzailea existitzen da");
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("erabiltzaile izena existitzen da"));
    		return null;
    	} else {
    		facadeBL.createUser(username, password);
    		return "ok";
    	}
    }

    public String goBack() {
		return "atzera";
	}
    
    
    private boolean isUsernameTaken(String username) {
        return facadeBL.userExists(username);
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}