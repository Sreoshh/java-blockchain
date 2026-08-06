import java.security.MessageDigest;
import java.security.*;
import java.util.Base64;
import java.util.ArrayList;

public class StringUtil {

    // Applies SHA-256 hashing to a string
    public static String applySha256(String input) {

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {

                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    public static byte[] applyECDSASig(PrivateKey privateKey, String input) {

    try {

        Signature dsa = Signature.getInstance("SHA256withECDSA");

        dsa.initSign(privateKey);

        dsa.update(input.getBytes());

        return dsa.sign();

    } catch (Exception e) {

        throw new RuntimeException(e);

    }

}

public static boolean verifyECDSASig(
        PublicKey publicKey,
        String data,
        byte[] signature) {

    try {

        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");

        ecdsaVerify.initVerify(publicKey);

        ecdsaVerify.update(data.getBytes());

        return ecdsaVerify.verify(signature);

    } catch (Exception e) {

        throw new RuntimeException(e);

    }

}

public static String getStringFromKey(Key key) {

    return Base64.getEncoder().encodeToString(key.getEncoded());

}

public static String getMerkleRoot(ArrayList<Transaction> transactions) {

    int count = transactions.size();

    ArrayList<String> previousTreeLayer = new ArrayList<>();

    for (Transaction transaction : transactions) {
        previousTreeLayer.add(transaction.transactionId);
    }

    ArrayList<String> treeLayer = previousTreeLayer;

    while (count > 1) {

        treeLayer = new ArrayList<>();

        for (int i = 1; i < previousTreeLayer.size(); i += 2) {

            treeLayer.add(applySha256( previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));

        }

        // Handle odd number of transactions
        if (previousTreeLayer.size() % 2 == 1) {

            treeLayer.add(
                    applySha256(previousTreeLayer.get(previousTreeLayer.size() - 1) + previousTreeLayer.get(previousTreeLayer.size() - 1)));

        }

        count = treeLayer.size();

        previousTreeLayer = treeLayer;

    }

    return (treeLayer.size() == 1) ? treeLayer.get(0) : "";

}

}