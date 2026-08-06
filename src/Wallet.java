import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {

    // Public key (wallet address)
    public PublicKey publicKey;

    // Private key (keep this secret!)
    public PrivateKey privateKey;

    // Unspent transaction outputs owned by this wallet
    public HashMap<String, TransactionOutput> UTXOs = new HashMap<>();

    // Constructor
    public Wallet() {
        generateKeyPair();
    }

    // Generates a public/private key pair
    public void generateKeyPair() {

        try {

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");

            SecureRandom random = SecureRandom.getInstanceStrong();

            keyGen.initialize(256, random);

            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    public float getBalance(Blockchain blockchain) {

    float total = 0;

    UTXOs.clear();

    for (Map.Entry<String, TransactionOutput> item : blockchain.UTXOs.entrySet()) {

        TransactionOutput UTXO = item.getValue();

        if (UTXO.isMine(publicKey)) {

            UTXOs.put(UTXO.id, UTXO);

            total += UTXO.value;
        }
    }

    return total;
}


    public Transaction sendFunds(Blockchain blockchain, PublicKey recipient, float value) {

    if (getBalance(blockchain) < value) {

        System.out.println("Not enough funds.");
        return null;

    }

    ArrayList<TransactionInput> inputs = new ArrayList<>();

    float total = 0;

    for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()) {

        TransactionOutput UTXO = item.getValue();

        total += UTXO.value;

        inputs.add(new TransactionInput(UTXO.id));

        if (total >= value)
            break;

    }

    Transaction newTransaction = new Transaction(publicKey, recipient, value, inputs);

    newTransaction.generateSignature(privateKey);

    for (TransactionInput input : inputs) {

        UTXOs.remove(input.transactionOutputId);

    }

    return newTransaction;

}

}