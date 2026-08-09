import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BlockchainStorage {

    private static final String FILE_NAME = "blockchain.json";

    public static void save(Blockchain blockchain) {

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            GsonBuilder builder = new GsonBuilder()
                    .setPrettyPrinting();

            builder.create().toJson(blockchain, writer);

            System.out.println("Blockchain saved.");

        } catch (IOException e) {

            System.out.println("Error saving blockchain.");
            e.printStackTrace();

        }
    }

    public static Blockchain load() {

        try (FileReader reader = new FileReader(FILE_NAME)) {

            GsonBuilder builder = new GsonBuilder();

            Blockchain blockchain =
                    builder.create().fromJson(
                            reader,
                            Blockchain.class
                    );

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