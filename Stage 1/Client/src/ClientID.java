/**
 * This class handles the details of the client ID object
 */

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.io.Serializable;

public class ClientID implements Serializable {
    private int clientID;

    public ClientID(int id) {
        this.clientID = id;
    }

    /**
     * This method seals an object
     * @param s The object to be sealed
     * @return the sealed object
     */
    public SealedObject sealClientID(Serializable s) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            SecretKey aesKey = kgen.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            SealedObject sealedObject = new SealedObject(s, cipher);
            return sealedObject;
        } catch (Exception e) {

        }
        return null;
    }
}
