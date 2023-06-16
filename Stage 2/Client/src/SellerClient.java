/**
 * This is the seller client class, this class is used by the user to create and closes auctions.
 */

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class SellerClient {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Get the username and password of the user trying to create the auction (used for verification that this is the same user who closes the auction)
        System.out.println("Enter username:");
        String username = in.nextLine();

        System.out.println("Enter password:");
        String password = in.nextLine();

        try {

            // Connecting client to server
            String name = "myserver";
            Registry registry = LocateRegistry.getRegistry("localhost");
            Item server = (Item) registry.lookup(name);

            label:
            while(true) {
                System.out.println("\nType \"create\" to create an auction item, \"close\" to close an auction item, or \"exit\" to exit the client.");
                String userResponse = in.nextLine();

                switch (userResponse) {
                    // Creating an auction
                    case "create":
                        System.out.println("Enter title:");
                        String title = in.nextLine();

                        System.out.println("Enter description: ");
                        String description = in.nextLine();

                        System.out.println("Enter starting price: ");
                        int startPrice = Integer.parseInt(in.nextLine());

                        System.out.println("Enter reserve price: ");
                        int reservePrice = Integer.parseInt(in.nextLine());

                        // Creates the auction item by calling this method in server class, returns the auction ID
                        int auctionId = server.createAuctionItem(username, password, title, description, startPrice, reservePrice);
                        System.out.println("\nCreated auction item, auction ID is: " + auctionId);
                        break;
                    // Closing an auction
                    case "close":
                        System.out.println("Enter auction ID of the auction you would like to close: ");
                        int closeAuctionId = Integer.parseInt(in.nextLine());
                        // Returns response from user when trying to close the auction, ensures verification of correct user
                        System.out.println(server.closeItemAuction(username, password, closeAuctionId));
                        break;
                    // Exiting client
                    case "exit":
                        break label;
                    default:
                        System.out.println("Not a valid input. Try again.");
                        break;
                }


            }

        } catch (Exception e) {

            e.printStackTrace();

        }



    }
}

