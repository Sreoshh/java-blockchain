package com.javablockchain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockchainController {

    private final Blockchain blockchain;

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
}