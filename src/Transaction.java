import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction {

    // Transaction hash
    public String transactionId;

    // Sender's public key
    public PublicKey sender;

    // Receiver's public key
    public PublicKey recipient;

    // Amount to transfer
    public float value;

    // Digital signature
    public byte[] signature;

    // Constructor
    public Transaction(PublicKey from,
                       PublicKey to,
                       float value) {

        this.sender = from;
        this.recipient = to;
        this.value = value;

    }

    // Creates the data to be signed
    private String getData() {

        return StringUtil.getStringFromKey(sender)
                + StringUtil.getStringFromKey(recipient)
                + Float.toString(value);

    }

    // Sign this transaction
    public void generateSignature(PrivateKey privateKey) {

        String data = getData();

        signature = StringUtil.applyECDSASig(privateKey, data);

    }

    // Verify the signature
    public boolean verifySignature() {

        String data = getData();

        return StringUtil.verifyECDSASig(
                sender,
                data,
                signature
        );

    }

}