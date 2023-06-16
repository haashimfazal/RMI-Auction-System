/**
 * The ItemListing class manages the list of active auction items.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemListing implements Serializable {
    private HashMap<Integer, AuctionItem> itemList = new HashMap<>(); // This hashmap stores all the currently active auction items as the value, along with the auctionID as the key to uniquely identify each auction
    private HashMap<String, AuctionItem> userAuctions = new HashMap<>();
    private ArrayList<User> userCurrentAuctions = new ArrayList<>(); // An array of the users who are currently bidding

    public ItemListing() {
    }

    /**
     * This method is called by server to add items to the active item auction list (the itemList HashMap)
     * @param auctionId this is the key for the HashMap, each item has a unique auction ID so they are easily identified
     * @param item this is the item to be added to the list
     */
    public void addItem(int auctionId, AuctionItem item) {
        itemList.put(auctionId, item);
    }


    /**
     * This method allows the user by the buyer client to bid for selected items, but ensures that this is within certain boundaries.
     * @param username this is the username of the user who bids for the item, used to verify the winner and the bids
     * @param emailAddress this is the email address of the user who bids for the item
     * @param auctionId this is the auctionID of the item they are bidding on, used to identify the item
     * @param bid the amount the user wants to bid
     * @return
     */
    public String bidForItem(String username, String emailAddress, int auctionId, int bidAmount) {
        for(Integer i: itemList.keySet()) {
            if(i == auctionId) { // Iterate through the hashmap, checking if the auctionId entered by the user exists within the HashMap
                // Checks if the bid entered by the user is the same as the current highest bid / lower than the current highest bid
                if(itemList.get(i).getCurrentHighestBid() == bidAmount) {
                    return "\nThis bid is the same as the highest current bid. Enter another bid.";
                } else if(bidAmount < itemList.get(i).getCurrentHighestBid()) {
                    return "\nThis is lower than the current highest bid. Enter another bid.";
                }
                itemList.get(i).userBid(bidAmount); // Changing the current highest bid of that auction item to the bid entered by the user
                userCurrentAuctions.add(new User(username, emailAddress, auctionId, bidAmount)); // Adding the user who bidded to a list of users in current auctions, used to verify their deetails
                return "\nCurrent highest bid for this item is now: " + itemList.get(i).getCurrentHighestBid(); // Returns the current highest bid
            }
        }
        // The given id did not match any of those in the HashMap
        return "Bid did not complete successfully as an incorrect auction ID was entered.";
    }

    /**
     * This method is used by the seller client to close any listings entered by the user (remove the item from the item list of active items).
     * @param username this is the username of the user requesting the closure (used for verification)
     * @param password this is the password of the user requesting the closure (used for verification)
     * @param auctionId this is the auctionId of the item that they are requesting a closure for
     * @return
     */
    public String closeListing(String username, String password, int auctionId) {
        for(Integer i: itemList.keySet()) {
            if(i == auctionId) { // Check if the auctionID exists within the HashMap
                // If the auctionID does exist that the user wants to close, ensure that this is the correct user by matching their details against the user who opened the auction
                if(itemList.get(i).getSellerUsername().equals(username)) {
                    if(itemList.get(i).getSellerPassword().equals(password)) {
                        String itemTitle = itemList.get(i).getItemTitle(); // Get the title of the item with the respective auction ID
                        int finalHighestBid = itemList.get(i).getCurrentHighestBid(); // Get the final bid at time of closure

                        // Get the details of the user who had the highest bid
                        for(User u: userCurrentAuctions) {
                            if(finalHighestBid == u.getAmountBid() && itemList.get(i).getCurrentHighestBid() > itemList.get(i).getItemReservePrice()) { // Verifying details of user with the highest bid
                                // Details of the user
                                String highestBidderUsername = u.getUsername();
                                String highestBidderEmail = u.getEmailAddress();
                                itemList.remove(auctionId); // Removing the auction item from the active auction list (closing the listing)
                                return "\nListing closed for item \"" + itemTitle +"\" with the highest bid at: " + finalHighestBid + " by user " + highestBidderUsername + "." +
                                        "\nTheir email address is: " + highestBidderEmail; // String to provide client with a response and the winner of the auction
                            }
                        }


                        // If the highest bid is lower than the reserve price, we send this response to the user
                        if(itemList.get(i).getCurrentHighestBid() < itemList.get(i).getItemReservePrice()) {
                            int itemReservePrice = itemList.get(i).getItemReservePrice();
                            itemList.remove(auctionId); // Item is still removed
                            return "\nListing closed for item \"" + itemTitle +"\" with the highest bid at: " + finalHighestBid + ". However, this is lower than the reserve price of " + itemReservePrice + " so this item was not sold.";
                        }
                    }
                }

                // If the details of the user were not verified against those who created the auction, this response is given to the client
                return "\nYour username and password do not match those of the user who created this auction." +
                        "\nPlease try again by exiting this client and re-entering your username and password.";
            }
        }
        // The auction ID entered did not match any of those in the current active auction list
        return "\nAuction ID not found";
    }

    /**
     * Creates the string of all the active items being auctioned and their details. This is used by the buyer client to browse active items.
     * @return returns all the active items as a String
     */
    public String viewListing() {
        String stringOfItems = "";
        for(AuctionItem i: itemList.values()) {
            stringOfItems = stringOfItems + i.toString();
        }
        return "\nThe following items are currently being actively auctioned:\n" + stringOfItems;
    }

    /**
     * Returns the active item list
     * @return the itemList HashMap
     */
    public HashMap<Integer, AuctionItem> getItemList() {
        return this.itemList;
    }


}
