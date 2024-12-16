package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;


public class DeleteUser  {
    private BLFacade facadeBL;
    private static boolean admin;
    private String username;
    private boolean showMessage = false; // Para activar el temporizador
    private String redirectPage = "hasiera.xhtml"; // Página a redirigir

    public DeleteUser() {
        try {
            facadeBL = FacadeBean.getBusinessLogic();
        } catch (Exception e) {
            System.out.println("FacadeBean: negozioaren logika sortzean errorea: " + e.getMessage());
        }
    }
    
    public String delete() {
        if (!admin) {
            username = Hasiera.getActiveUser();
            System.out.println(username);
        } else {
            if (username.length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Izena sartu behar da"));
                return null;
            }
            if (!facadeBL.userExists(username)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erabiltzaile izena ez da zuzena"));
                return null;
            }
        }
        if (facadeBL.deleteUser(username)) {
            if (!admin) {
                Hasiera.setCR(false);
                Hasiera.setLO(true);
                Hasiera.setQR(false);
                Hasiera.setSI(true);
                Hasiera.setActiveUser(username);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zure kontua ezabatua izan da"));
                
                showMessage = true;
                return null; 
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erabiltzailea ezabatua izan da"));
            showMessage = true;
            return null;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pasahitza ez da zuzena"));
            return null;
        }
    }

    public void redirectToPage() {
        if (showMessage) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(redirectPage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Getters y setters
    public boolean isShowMessage() {
        return showMessage;
    }

    public void setShowMessage(boolean showMessage) {
        this.showMessage = showMessage;
    }

    public String getRedirectPage() {
        return redirectPage;
    }

    public void setRedirectPage(String redirectPage) {
        this.redirectPage = redirectPage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static boolean getAdmin() {
        return admin;
    }

    public boolean isAdmin() {
        return DeleteUser.getAdmin();
    }

    public static void setAdmin(boolean admin) {
        DeleteUser.admin = admin;
    }
    
    public String goBack() {
    	return "atzera";
    }
}
