package com.javablockchain;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WalletStorage {

    private static final String FILE_NAME = "wallet.json";

    public static void save(Wallet wallet) {

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            WalletData data = new WalletData();

            data.publicKey = Base64.getEncoder()
                    .encodeToString(wallet.publicKey.getEncoded());

            data.privateKey = Base64.getEncoder()
                    .encodeToString(wallet.privateKey.getEncoded());

            gson.toJson(data, writer);

        } catch (Exception e) {

            throw new RuntimeException("Error saving wallet.", e);

        }
    }

    public static Wallet load() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return null;
            }

            Gson gson = new Gson();

            WalletData data = gson.fromJson(
                    new FileReader(file),
                    WalletData.class
            );

            KeyFactory keyFactory = KeyFactory.getInstance("EC");

            PublicKey publicKey = keyFactory.generatePublic(
                    new X509EncodedKeySpec(
                            Base64.getDecoder().decode(data.publicKey)
                    )
            );

            PrivateKey privateKey = keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(
                            Base64.getDecoder().decode(data.privateKey)
                    )
            );

            Wallet wallet = new Wallet();

            wallet.publicKey = publicKey;
            wallet.privateKey = privateKey;

            return wallet;

        } catch (Exception e) {

            throw new RuntimeException("Error loading wallet.", e);

        }
    }

    private static class WalletData {
        String publicKey;
        String privateKey;
    }
}