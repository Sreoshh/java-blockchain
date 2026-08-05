import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {

    // Transaction hash
    public String transactionId;

    // Sender's public key
    public PublicKey sender;

    // Receiver's public key
    public PublicKey recipient;

    // Amount to transfer
    public float value;

    // Transaction inputs
    public ArrayList<TransactionInput> inputs = new ArrayList<>();

    // Transaction outputs
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();

    // Sequence to ensure unique transaction IDs
    private static int sequence = 0;

    // Digital signature
    public byte[] signature;

    // Constructor
    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {

    this.sender = from;
    this.recipient = to;
    this.value = value;
    this.inputs = inputs;

}

    // Creates the data to be signed
    private String getData() {
        return StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
         }

    private String calculateHash() {

    sequence++;

    return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value) + sequence);
    }

    // Sign this transaction
    public void generateSignature(PrivateKey privateKey) {

        String data = getData();

        signature = StringUtil.applyECDSASig(privateKey, data);

    }

    public float getInputsValue() {

    float total = 0;

    for (TransactionInput input : inputs) {

        if (input.UTXO == null)
            continue;

        total += input.UTXO.value;

    }

    return total;

}
    public float getOutputsValue() {

    float total = 0;

    for (TransactionOutput output : outputs) {

        total += output.value;

    }

    return total;

}

    // Verify the signature
    public boolean verifySignature() {

        String data = getData();

        return StringUtil.verifyECDSASig( sender, data, signature);

    }

}