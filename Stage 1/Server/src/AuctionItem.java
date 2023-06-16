/**
 * This is the class for each auction item object and it's defined variables/details.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class AuctionItem implements Serializable {
    private int itemId;
    private String itemTitle;
    private String itemDescription;


    public AuctionItem(int itemId, String title, String description) {
        this.itemId = itemId;
        this.itemTitle = title;
        this.itemDescription = description;
    }

    /**
     * @return the details of the auction item
     */
    public String toString() {
        return "Item ID: " + this.itemId +"\n" + "Item Title: " + this.itemTitle + "\n" + "Item Description: "
                + this.itemDescription + "\n";
    }

}
