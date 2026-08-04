import java.security.*;

public class Wallet {

    // Public key (wallet address)
    public PublicKey publicKey;

    // Private key (keep this secret!)
    public PrivateKey privateKey;

    // Constructor
    public Wallet() {
        generateKeyPair();
    }

    // Generates a public/private key pair
    public void generateKeyPair() {

        try {

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");

            SecureRandom random = SecureRandom.getInstanceStrong();

            keyGen.initialize(256, random);

            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}