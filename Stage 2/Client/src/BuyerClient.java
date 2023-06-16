/**
 * This is the byer client class, this class is used by the user to bid on auctions and browse active auctions.
 */


import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class BuyerClient {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        try {
            // Connects client to server
            String name = "myserver";
            Registry registry = LocateRegistry.getRegistry("localhost");
            Item server = (Item) registry.lookup(name);

            System.out.println("Enter your username:");
            String username = in.nextLine();

            System.out.println("Enter your email address:");
            String emailAddress = in.nextLine();

            // Gets the active auctions and displays them to the client user
            String activeAuctions = server.browseItems();
            System.out.println(activeAuctions);



            label:
            while(true) {
                System.out.println("\nType \"bid\" to bid for an item, or \"browse\" to get an updated version of the active auctions or \"exit\" to exit client.");
                String userResponse = in.nextLine();

                switch (userResponse) {
                    // Used to bid on auctions
                    case "bid":

                        System.out.println("\nEnter auction ID of the item you would like to bid for:");
                        int auctionIdBid = Integer.parseInt(in.nextLine());

                        System.out.println("Enter how much you would like to bid:");
                        int amountToBid = Integer.parseInt(in.nextLine());

                        System.out.println(server.bidForItem(username, emailAddress, auctionIdBid, amountToBid));
                        break;
                    // Used to browse auctions
                    case "browse":
                        activeAuctions = server.browseItems();
                        System.out.println(activeAuctions);
                        break;
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

