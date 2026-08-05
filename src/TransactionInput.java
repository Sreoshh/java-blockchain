public class TransactionInput {

    // Reference to a previous TransactionOutput
    public String transactionOutputId;

    // The actual unspent output
    public TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId) {

        this.transactionOutputId = transactionOutputId;

    }

}