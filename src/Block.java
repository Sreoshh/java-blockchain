import java.util.Date;
import java.util.ArrayList;

public class Block {

    public String hash;
    public String previousHash;
    public String data;
    public long timeStamp;
    public int nonce;
    public ArrayList<Transaction> transactions = new ArrayList<>();

    public Block(String data, String previousHash) {

        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash();
    }

    public String calculateHash() {

        String calculatedHash = StringUtil.applySha256( previousHash + Long.toString(timeStamp) +Integer.toString(nonce) + data);

        return calculatedHash;
    }

    public void mineBlock(int difficulty) {

        String target = new String(new char[difficulty]).replace('\0', '0');

        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }

        System.out.println("Block Mined! : " + hash);
    }
    public boolean addTransaction(Transaction transaction, Blockchain blockchain) {

    if (transaction == null)
        return false;

    if (!previousHash.equals("0")) {

        if (!transaction.processTransaction(blockchain)) {

            System.out.println("Transaction failed.");
            return false;

        }

    }

    transactions.add(transaction);

    System.out.println("Transaction added successfully.");

    return true;

}
}