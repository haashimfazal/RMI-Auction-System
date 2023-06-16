import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try {
            String name = "myserver";
            Registry registry = LocateRegistry.getRegistry("localhost");
            Item server = (Item) registry.lookup(name);

            System.out.println("Enter ID of item you'd like to look up the specification of: ");
            int itemId = Integer.parseInt(in.nextLine());
            AuctionItem result = server.getSpec(itemId, 1);
            System.out.println(result.toString());
        }
        catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }
}
