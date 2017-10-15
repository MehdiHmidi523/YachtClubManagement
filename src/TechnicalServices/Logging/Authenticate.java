package TechnicalServices.Logging;

/**
 * Created by Void on 12/10/2017 for the YachtClubManagement project.
 */
public class Authenticate {

    private static final String username = "admin";
    private static final String password = "admin";
    private boolean logged = false;

    public boolean authenticate(String username, String password){
        if (username.equals(username) && password.equals(password)){
            setLogged(true);
            return true;
        }
        else return false;
    }

    private void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLogged() {
        return logged;
    }
}
