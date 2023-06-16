import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Server implements Item {
    private SecretKey aesKey;
    private HashMap<Integer, AuctionItem> auctionItems = new HashMap<>();

    public Server() {
        super();
        populateItemsArray(); // Populate the array of auction items
    }

    /**
     * This method creates a hard coded set of auction items.
     */
    public void populateItemsArray() {
        for(int i = 0; i < 5; i ++) {
            auctionItems.put(i, new AuctionItem(i, "Item " + i, "An item."));
        }
    }

    /**
     * This method creates the AES key and stores it in a file ('key.txt') that can be read by the client.
     */
    public void createKey() {
        try {
            FileOutputStream f = new FileOutputStream(new File("key.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            this.aesKey = kgen.generateKey();
            o.writeObject(this.aesKey);
            o.close();
            f.close();
        } catch (Exception e) {

        }
    }

    public SealedObject getSpec(int itemId, SealedObject clientReq)  {
        try {
            this.createKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.aesKey); // Encrypts the object with the key
            return new SealedObject(auctionItems.get(itemId), cipher);
        } catch (Exception e) {

        }
        return null;
    }


    public static void main(String[] args) {
        try {
             // Setting up server
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