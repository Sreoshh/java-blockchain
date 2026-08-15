package com.javablockchain;
import java.security.PublicKey;

public class TransactionOutput {

    // Unique ID of this transaction output
    public String id;

    // New owner of these coins
    public PublicKey recipient;

    // Amount of coins
    public float value;

    // Transaction that created this output
    public String parentTransactionId;

    // Constructor
    public TransactionOutput(PublicKey recipient, float value, String parentTransactionId) {

        this.recipient = recipient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;

        this.id = StringUtil.applySha256( StringUtil.getStringFromKey(recipient)+ Float.toString(value) + parentTransactionId);
    }

    // Check if these coins belong to a given public key
    public boolean isMine(PublicKey publicKey) {

        return publicKey.equals(recipient);

    }

}