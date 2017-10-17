package TechnicalServices.Logging;

/**
 * Created by Void on 12/10/2017 for the YachtClubManagement project.
 */
public class Authenticate {

    private static final String username = "admin";
    private static final String password = "admin";
    private boolean logged = false;

    public boolean authenticate(String username, String password){
        if (Authenticate.username.equals(username) && Authenticate.password.equals(password)){
            setLogged();
            return true;
        }
        else return false;
    }

    private void setLogged() {
        this.logged = true;
    }

    public boolean isLogged() {
        return logged;
    }
}
