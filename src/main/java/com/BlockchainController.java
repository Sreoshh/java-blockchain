package com.javablockchain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@RestController
public class BlockchainController {

    private final Blockchain blockchain;
    private final Wallet miner;
    private final ArrayList<Wallet> wallets;

    public BlockchainController() {
        Wallet loadedMiner = WalletStorage.load();
        if (loadedMiner != null) {
    miner = loadedMiner;
    } else {
    miner = new Wallet();
    WalletStorage.save(miner);
}
    wallets = WalletStorage.loadWallets();

    if (wallets.isEmpty()) {
    wallets.add(miner);
    WalletStorage.saveWallets(wallets);
}
        Blockchain loadedBlockchain = BlockchainStorage.load();
        if (loadedBlockchain != null) {
            blockchain = loadedBlockchain;
            } else {
            blockchain = new Blockchain();
            }
            Transaction genesisTransaction = new Transaction( null,miner.publicKey,100f,new ArrayList<>());
            genesisTransaction.transactionId = "GENESIS";
        
        genesisTransaction.outputs.add(new TransactionOutput(miner.publicKey, 100f, genesisTransaction.transactionId));
    
    blockchain.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
}
    

    @GetMapping("/")
    public String home() {
        return "Java Blockchain API is running!";
    }

    @GetMapping("/api/blockchain")
    public Blockchain getBlockchain() {
        return blockchain;
    }

    @GetMapping("/api/blockchain/validate")
    public boolean validateBlockchain() {
        return blockchain.isChainValid();
    }

    @GetMapping("/api/blockchain/status")
    public String status() {
    return "Blockchain is running. Blocks: " + blockchain.chain.size();
}

    @PostMapping("/mine")
    public String mineBlock() {

    Block newBlock = new Block( blockchain.chain.isEmpty()? "0": blockchain.chain.get(blockchain.chain.size() - 1).hash, "Mined Block");

    blockchain.addBlock(newBlock, miner);
    BlockchainStorage.save(blockchain);

    return "Block mined successfully. Hash: " + newBlock.hash;
}

    @PostMapping("/transaction")
    public String createTransaction(
    @RequestParam ("amount")float amount,
    @RequestParam("recipient") String recipientAddress) {
        
    Wallet recipientWallet = null;

    for (Wallet wallet : wallets) {
        if (StringUtil.getStringFromKey(wallet.publicKey)
                .equals(recipientAddress)) {
            recipientWallet = wallet;
            break;
        }
    }

    if (recipientWallet == null) {
        return "Recipient wallet not found.";
    }
    Transaction transaction = miner.sendFunds(blockchain, recipientWallet.publicKey, amount);

    if (transaction == null) {
        return "Transaction failed.";
    }
    blockchain.pendingTransactions.add(transaction);
    BlockchainStorage.save(blockchain);
    return "Transaction created successfully.";
}

    @GetMapping("/transactions")
    public ArrayList<Transaction> getTransactions() {

    ArrayList<Transaction> transactions = new ArrayList<>();

    for (Block block : blockchain.chain) {
        transactions.addAll(block.transactions);
    }

    return transactions;
}
    @GetMapping("/pending-transactions")
    public ArrayList<Transaction> getPendingTransactions() {
    return blockchain.pendingTransactions;
}

    @GetMapping("/balance")
    public float getMinerBalance() {
    return miner.getBalance(blockchain);
}

    @GetMapping("/wallet/address")
    public String getWalletAddress() {
    return StringUtil.getStringFromKey(miner.publicKey);
}

    @PostMapping("/wallet/create")
    public String createWallet() {
    Wallet wallet = new Wallet();
    wallets.add(wallet);
    WalletStorage.saveWallets(wallets);
    return StringUtil.getStringFromKey(wallet.publicKey);
}

    @GetMapping("/wallets")
    public ArrayList<String> getWallets() {
        ArrayList<String> addresses = new ArrayList<>();

    for (Wallet wallet : wallets) {
        addresses.add(StringUtil.getStringFromKey(wallet.publicKey));
    }
    return addresses;
}
}