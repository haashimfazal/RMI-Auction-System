/**
 * @author Haashim Fazal
 * This is the main server class. Here we set up the RMI for the server and this main method is where we create the auction.
 * This method uses instances of other classes in our server directory to create a list of auction items, close auction items, bid on them and display them to the user.
 * Documentation for functions for this class are futher documented in the class interface Item.
 */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Server implements Item {
    private ItemListing listOfItems = new ItemListing(); // This initialises the ItemListing class (which is used to create the list of items)
    private HashMap<String, String> userDetails = new HashMap<>();
    private int auctionId = 0; // The auction ID of each item

    // Constructor is not used in this program.
    public Server() {
        super();
    }

    public AuctionItem getSpec(int itemId, int clientId) {
        return listOfItems.getItemList().get(itemId);
    }

    public int createAuctionItem(String username, String password, String title, String description, int startingPrice, int reservePrice) {
        auctionId++; // We increment this auction ID by 1 everytime a new auction is created, this ensures a unique ID is set for every auction item
        listOfItems.addItem(this.auctionId, new AuctionItem(username, password, title, description, startingPrice, reservePrice, auctionId)); // Add the item to the list of active items with the auction ID as key
        return auctionId;
    }

    public String closeItemAuction(String username, String password, int auctionIdInput) {
        return listOfItems.closeListing(username, password, auctionIdInput);
    }

    public String browseItems() {
        return listOfItems.viewListing();
    }


    public String bidForItem(String username, String emailAddress, int auctionId, int bidAmount) {
        return listOfItems.bidForItem(username, emailAddress, auctionId, bidAmount);
    }

    public static void main(String[] args) {
        try {
            // Setting up the server so that the client can connect
            Server s = new Server();
            String name = "myserver";
            Item stub = (Item) UnicastRemoteObject.exportObject(s, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Server ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}