package com.javablockchain;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.*;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class BlockchainStorage {

    private static final String FILE_NAME = "blockchain.json";

    public static void save(Blockchain blockchain) {

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            GsonBuilder builder = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeHierarchyAdapter(PublicKey.class,
                (JsonSerializer<PublicKey>) (src, type, context) ->
                        new JsonPrimitive(Base64.getEncoder().encodeToString(src.getEncoded())));
                        
                        builder.create().toJson(blockchain, writer);

            System.out.println("Blockchain saved.");

        } catch (IOException e) {

            System.out.println("Error saving blockchain.");
            e.printStackTrace();

        }
    }

    public static Blockchain load() {

        try (FileReader reader = new FileReader(FILE_NAME)) {

            GsonBuilder builder = new GsonBuilder()
        .registerTypeHierarchyAdapter(PublicKey.class,
                (JsonDeserializer<PublicKey>) (json, type, context) -> {
                    try {
                        byte[] keyBytes = Base64.getDecoder().decode(json.getAsString());
                        X509EncodedKeySpec keySpec =
                                new X509EncodedKeySpec(keyBytes);
                        return KeyFactory.getInstance("EC")
                                .generatePublic(keySpec);
                    } catch (Exception e) {
                        throw new JsonParseException(e);
                    }
                });
                
                Blockchain blockchain = builder.create().fromJson(reader, Blockchain.class);

            if (blockchain == null) {
                return null;
            }

            if (blockchain.chain == null) {
                blockchain.chain = new java.util.ArrayList<>();
            }

            if (blockchain.UTXOs == null) {
                blockchain.UTXOs =
                        new java.util.HashMap<>();
            }

            if (blockchain.pendingTransactions == null) {
                blockchain.pendingTransactions = new java.util.ArrayList<>();
}

            if (!blockchain.isChainValid()) {
                System.out.println("Loaded blockchain is invalid!");
                return null;
            }
            System.out.println("Blockchain loaded and validated.");
            return blockchain;

        } catch (IOException e) {

            System.out.println("No saved blockchain found.");

            return null;

        }
    }
}