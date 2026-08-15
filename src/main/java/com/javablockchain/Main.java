package com.javablockchain;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Blockchain blockchain = new Blockchain();

        Wallet coinbase = new Wallet();
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();
        Wallet miner = new Wallet();

        // Genesis Transaction
        Transaction genesisTransaction = new Transaction( coinbase.publicKey, walletA.publicKey,100f, new ArrayList<>());

        genesisTransaction.generateSignature(coinbase.privateKey);

        genesisTransaction.transactionId = "0";

        genesisTransaction.outputs.add( new TransactionOutput( genesisTransaction.recipient, genesisTransaction.value, genesisTransaction.transactionId));

        blockchain.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        System.out.println("Creating Genesis Block...");

        Block genesis = new Block("Genesis Block", "0");
        genesis.addTransaction(genesisTransaction, blockchain);
        blockchain.addBlock(genesis, coinbase);

        System.out.println();

        System.out.println("Wallet A Balance: " + walletA.getBalance(blockchain));

        System.out.println("Wallet B Balance: " + walletB.getBalance(blockchain));

        System.out.println();

        System.out.println("Wallet A is Sending 40 coins to Wallet B...");

        Block block1 = new Block("Block 1", genesis.hash);

        block1.addTransaction(walletA.sendFunds(blockchain, walletB.publicKey,40f), blockchain);

        blockchain.addBlock(block1, miner);

        System.out.println();

        System.out.println("Wallet A Balance: " + walletA.getBalance(blockchain));

        System.out.println("Wallet B Balance: " + walletB.getBalance(blockchain));

        System.out.println("\n\n========== BLOCKCHAIN ==========");
        for (int i = 0; i < blockchain.chain.size(); i++) {
            BlockExplorer.printBlock(blockchain.chain.get(i), i);
}
        Blockchain loadedBlockchain = BlockchainStorage.load();

        if (loadedBlockchain != null) {
        System.out.println("Loaded blocks: " + loadedBlockchain.chain.size());
}
        Transaction reward = blockchain.createMiningReward(miner);

        System.out.println("Mining Reward: " + reward.value);
        System.out.println("Miner Balance: " + miner.getBalance(blockchain));
    }
}