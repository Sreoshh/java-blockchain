package com.javablockchain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlockchainController {

    private final Blockchain blockchain;
    private final Wallet miner = new Wallet();

    public BlockchainController() {
        blockchain = new Blockchain();
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

    return "Block mined successfully. Hash: " + newBlock.hash;
}
}