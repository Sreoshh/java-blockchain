import java.util.ArrayList;
import java.util.HashMap;

public class Blockchain {

    public ArrayList<Block> chain;
    
    // Stores all unspent transaction outputs
    public HashMap<String, TransactionOutput> UTXOs;

    // Minimum transaction amount
    public float minimumTransaction = 0.1f;

    public int difficulty;
    public Blockchain() {
        chain = new ArrayList<>();
        UTXOs = new HashMap<>();
        difficulty = 4; // Set the mining difficulty
    }

    public void addBlock(Block newBlock) {
       newBlock.mineBlock(difficulty);
       chain.add(newBlock);
    }

    // Checks whether the blockchain has been tampered with
    public boolean isChainValid() {

        Block currentBlock;
        Block previousBlock;

        // Start from the second block because the Genesis Block has no previous block
        for (int i = 1; i < chain.size(); i++) {

            currentBlock = chain.get(i);
            previousBlock = chain.get(i - 1);

            // Recalculate the current block's hash and compare
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {

                System.out.println("Current hash is invalid!");
                return false;

            }

            // Checks if the previous hash matches
            if (!currentBlock.previousHash.equals(previousBlock.hash)) {

                System.out.println("Previous hash is invalid!");
                return false;

            }

        }

        return true;

    }

}