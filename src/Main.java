public class Main {

    public static void main(String[] args) {

        // Create two wallets
        Wallet alice = new Wallet();
        Wallet bob = new Wallet();

        // Alice sends 50 coins to Bob
        Transaction transaction = new Transaction(
                alice.publicKey,
                bob.publicKey,
                50f
        );

        // Alice signs the transaction
        transaction.generateSignature(alice.privateKey);

        // Verify the signature
        System.out.println("Signature Valid: " +
                transaction.verifySignature());

    }

}