package com.javablockchain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockchainController {

    @GetMapping("/")
    public String home() {
        return "Java Blockchain API is running!";
    }
}