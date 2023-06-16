/**
 * This is the class used to create a user who is bidding
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String username;
    private String emailAddress;
    private int amountBid;
    private HashMap<String, String> currentUsers = new HashMap<>();

    public User() {

    }

    /**
     * @param username the username of the user
     * @param emailAddress the email address of the user
     * @param auctionId the auction id (item) that they are bidding on
     * @param amountBid the amount they are bidding on
     */
    public User(String username, String emailAddress, int auctionId, int amountBid) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.amountBid = amountBid;
    }

    public void addUsers(String username, String password) {
        currentUsers.put(username, password);
    }

    // This method checks if the buyer exists
    public String doesUserExist(String username, String password) {
        for(String s: currentUsers.keySet()) {
            if(s.equals(username)) {
                if(currentUsers.get(s).equals(password)) {
                    return "Successfully logged in with existing username and password.";
                } else {
                    return "The username you are trying to login with exists, but the password is incorrect.";
                }
            } else {
                break;
            }
        }
        return "";
    }


    public int getAmountBid() {
        return this.amountBid;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmailAddress() { return this.emailAddress; }
}
