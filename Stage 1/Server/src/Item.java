import javax.crypto.SealedObject;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Item extends Remote {
    /**
     * This method returns the item (the auction item) as a sealed (encrypted) object
     * @param itemId this is the item id of the item we want as a sealed object
     * @param clientReq this is the client request (or ID) as a sealed object
     * @return returns the item as a sealed object
     */
    public SealedObject getSpec(int itemId, SealedObject clientReq) throws RemoteException;
}
