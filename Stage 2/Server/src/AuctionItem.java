/**
 * This class is used to create the auction item object.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AuctionItem implements Serializable {
    private String itemTitle;
    private String itemDescription;
    private int itemStartPrice;
    private int itemReservePrice;
    private int itemAuctionId;
    private int currentHighestBid = 0; // Current bid always set to 0 on initialization, as there is no bid when the user creates the item
    private String sellerUsername;
    private String sellerPassword;

    /* The details of this method are explained in detail in the interface, above the createAuctionItem method.
     * This constructor initializes all the variables of the instance with the inputs given in the parameters. */
    public AuctionItem(String username, String password, String title, String description, int startingPrice, int reservePrice, int auctionId) {
        this.sellerUsername = username;
        this.sellerPassword = password;
        this.itemTitle = title;
        this.itemDescription = description;
        this.itemStartPrice = startingPrice;
        this.itemReservePrice = reservePrice;
        this.itemAuctionId = auctionId;
    }

    /**
     * This method allows the user to bid the selected amount. Changes the amount of the currentHighestBid on certain boundaries defined when this function is called in ItemListing.
     * @param amount the amount that the user wants to bid
     */
    public void userBid(int amount) {
        this.currentHighestBid = amount;
    }


    public int getCurrentHighestBid() {
        return this.currentHighestBid;
    }

    public int getItemReservePrice() { return this.itemReservePrice; }

    public String getItemTitle() {
        return this.itemTitle;
    }

    public String getSellerUsername() {
        return this.sellerUsername;
    }

    public String getSellerPassword() {
        return this.sellerPassword;
    }

    public int getItemAuctionId() {
        return this.itemAuctionId;
    }


    /**
     * @return returns the details of this specific auction item
     */
    public String toString() {
    return "\n" + "Item title: " + this.itemTitle + "\n" + "Item Description: " + this.itemDescription + "\n" + "Item Start Price: " +
            this.itemStartPrice + "\n" + "Current Highest Bid: " + this.currentHighestBid + "\n" + "Auction ID: " + this.itemAuctionId + "\n";
}




}
