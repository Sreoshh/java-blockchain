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
import java.util.ArrayList;

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

    public static void saveWallets(ArrayList<Wallet> wallets) {

    try (FileWriter writer = new FileWriter("wallets.json")) {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        ArrayList<WalletData> dataList = new ArrayList<>();

        for (Wallet wallet : wallets) {

            WalletData data = new WalletData();

            data.publicKey = Base64.getEncoder()
                    .encodeToString(wallet.publicKey.getEncoded());

            data.privateKey = Base64.getEncoder()
                    .encodeToString(wallet.privateKey.getEncoded());

            dataList.add(data);
        }

        gson.toJson(dataList, writer);

    } catch (Exception e) {
        throw new RuntimeException("Error saving wallets.", e);
    }
}

    public static ArrayList<Wallet> loadWallets() {

    ArrayList<Wallet> wallets = new ArrayList<>();

    try {

        File file = new File("wallets.json");

        if (!file.exists()) {
            return wallets;
        }

        Gson gson = new Gson();

        ArrayList<WalletData> dataList =
                gson.fromJson(
                        new FileReader(file),
                        new com.google.gson.reflect.TypeToken<ArrayList<WalletData>>(){}.getType()
                );

        if (dataList == null) {
            return wallets;
        }

        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        for (WalletData data : dataList) {

            Wallet wallet = new Wallet();

            wallet.publicKey = keyFactory.generatePublic(
                    new X509EncodedKeySpec(
                            Base64.getDecoder().decode(data.publicKey)
                    )
            );

            wallet.privateKey = keyFactory.generatePrivate(
                    new PKCS8EncodedKeySpec(
                            Base64.getDecoder().decode(data.privateKey)
                    )
            );

            wallets.add(wallet);
        }

        return wallets;

    } catch (Exception e) {
        throw new RuntimeException("Error loading wallets.", e);
    }
}
}