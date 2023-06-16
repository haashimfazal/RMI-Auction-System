import javax.crypto.SealedObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Item extends Remote {


    /**
     * This code was used for Stage 1 of the coursework.
     * This function returns the auction item with the auction ID (itemId) supplied by the client user. It is used by the original client.
     * @param itemId this is an integer for the item ID that the user wants to see the details of
     * @param clientId this is the client id of the user
     * @return the auction item that matches the given ID
     */
    public AuctionItem getSpec(int itemId, int clientId) throws RemoteException;

    /**
     * This method is used by the seller client. It creates an auction item and adds it to the list of items actively being auctioned.
     * @param username the username of the user who creates the auction item
     * @param password the password of the user who creates the auction item
     * @param title the title of the item
     * @param description the description of the item
     * @param startingPrice the starting price of the item
     * @param reservePrice the item reserve price
     * @return returns the auctionID of the item that was created
     */
    public int createAuctionItem(String username, String password, String title, String description, int startingPrice, int reservePrice) throws RemoteException;

    /**
     * This method is used by the seller client to close the auction of the item given by the user (via the auction ID)
     * We ensure that this is the same user that created the auction by also getting the username and password of the user who tried to close the auction.
     * @param username the username of the user trying to close the auction
     * @param password the password of the user trying to close the auction
     * @param auctionIdInput the auction ID of the item that the user wants to close
     * @return
     */
    public String closeItemAuction(String username, String password, int auctionIdInput) throws RemoteException;

    /**
     * This method is used by the buyer client to browse all the items currently active in the auction list
     * @return returns all the active items in the auction list in a string format
     */
    public String browseItems() throws RemoteException;

    /**
     * This method is used by the buyer client to bid for a particular item.
     * @param username the username of the user who is bidding for the item
     * @param emailAddress the email address of the user who is bidding for the item
     * @param auctionId the auctionID for the item that they would like to bid on
     * @param bidAmount the amount that they would like to bid
     * @return returns the response from the server in a string format, e.g. the current highest bid along with the amount bidded, or any exceptions
     */
    public String bidForItem(String username, String emailAddress, int auctionId, int bidAmount) throws RemoteException;

}
