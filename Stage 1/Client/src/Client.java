/**
 * This is the client class which receives the encrypted (sealed) object and decrypts it using the key created by the server.
 */

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client{
    public static void main(String[] args) {
        try {
            // Create an instance of the client ID to be sealed & inserted as an argument to get our auction item
            ClientID c = new ClientID(1);
            SealedObject clientIDSealed = c.sealClientID(c); // Method to seal the client object

            // Get the item ID from the user of the item we want from the server
            System.out.println("Enter item id: ");
            Scanner in = new Scanner(System.in);
            int itemId = in.nextInt();

            // Client connection to server
            String name = "myserver";
            Registry registry = LocateRegistry.getRegistry("localhost");
            Item server = (Item) registry.lookup(name);

            String configFilePath = new File(System.getProperty("user.dir")).getPath();
            SealedObject result = server.getSpec(itemId, clientIDSealed); // Get our sealed (encrpyted) object by calling the method in server class
            // Get the key object by reading it from the 'key.txt' file
            FileInputStream fi = new FileInputStream(new File(System.getProperty("user.dir")).getPath() + "\\..\\..\\Server\\src\\key.txt"); // Has to locate the file in the server source dir
            ObjectInputStream oi = new ObjectInputStream(fi);
            SecretKey aesKey = (SecretKey) oi.readObject();
            AuctionItem item = (AuctionItem) result.getObject(aesKey); // Decrypt the sealed object
            System.out.println(item.toString());
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }

    }
}

