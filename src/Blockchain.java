import java.util.ArrayList;
import java.util.HashMap;

public class Blockchain {

    public ArrayList<Block> chain;
    
    // Stores all unspent transaction outputs
    public HashMap<String, TransactionOutput> UTXOs;

    // Minimum transaction amount
    public float minimumTransaction = 0.1f;
    public Blockchain() {
        chain = new ArrayList<>();
        UTXOs = new HashMap<>();
    }
    public int difficulty = 4;
    public long targetBlockTime = 5000;
    public int adjustmentInterval = 3;


    public void adjustDifficulty() {

    if (chain.size() < adjustmentInterval) {
        return;
    }

    if (chain.size() % adjustmentInterval != 0) {
        return;
    }

    Block latestBlock = chain.get(chain.size() - 1);
    Block previousBlock = chain.get(chain.size() - adjustmentInterval);

    long actualTime = latestBlock.timeStamp - previousBlock.timeStamp;

    long expectedTime = targetBlockTime * adjustmentInterval;

    if (actualTime < expectedTime / 2) {

        difficulty++;

        System.out.println("Mining too fast. Difficulty increased to " + difficulty);

    } else if (actualTime > expectedTime * 2 && difficulty > 1) {

        difficulty--;

        System.out.println("Mining too slow. Difficulty decreased to " + difficulty);
    }
}
    public float miningReward = 50f;
    public Transaction createMiningReward(Wallet miner) {

    Transaction reward = new Transaction(null, miner.publicKey, miningReward, new java.util.ArrayList<>());

    reward.transactionId = "REWARD-" + System.currentTimeMillis();

    reward.outputs.add(new TransactionOutput(miner.publicKey, miningReward, reward.transactionId));

    blockchain.UTXOs.put(reward.outputs.get(0).id, reward.outputs.get(0));
    return reward;
}

    public void addBlock(Block newBlock, Wallet miner) {

    Transaction reward = createMiningReward(miner);

    newBlock.addTransaction(reward, this);

    newBlock.mineBlock(difficulty);

    chain.add(newBlock);

    adjustDifficulty();
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
            // Verify Merkle Root
            String recalculatedMerkle =
            StringUtil.getMerkleRoot(currentBlock.transactions);
            if (!recalculatedMerkle.equals(currentBlock.merkleRoot)) {
                System.out.println("Merkle Root is invalid!");
                return false;
            }
            // Validate every transaction in the block
for (Transaction transaction : currentBlock.transactions) {

    // Verify transaction signature
    if (transaction.sender != null) {
    if (!transaction.verifySignature()) {

        System.out.println("Transaction signature is invalid!");
        return false;

    }
    if (transaction.sender == null) {

    if (transaction.value != miningReward) {

        System.out.println("Invalid mining reward!");
        return false;

    }

    if (transaction.inputs != null &&
            !transaction.inputs.isEmpty()) {

        System.out.println("Mining reward cannot have inputs!");
        return false;

    }
}
    }

    // Check transaction inputs and outputs
    if (!transaction.inputs.isEmpty()) {

        float inputValue = transaction.getInputsValue();
        float outputValue = transaction.getOutputsValue();

        // Outputs must not exceed inputs
        if (outputValue > inputValue) {

            System.out.println("Transaction outputs exceed inputs!");
            return false;

        }

        // Every input must reference a valid UTXO
        for (TransactionInput input : transaction.inputs) {

            if (input.UTXO == null) {

                System.out.println("Invalid transaction input!");
                return false;

            }

        }
    }
}

        }

        return true;

    }

}