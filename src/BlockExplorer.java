public class BlockExplorer {

    public static void printBlock(Block block, int index) {

        System.out.println("\n========================================");
        System.out.println("BLOCK #" + index);
        System.out.println("========================================");

        System.out.println("Hash:");
        System.out.println(block.hash);

        System.out.println("\nPrevious Hash:");
        System.out.println(block.previousHash);

        System.out.println("\nMerkle Root:");
        System.out.println(block.merkleRoot);

        System.out.println("\nNonce:");
        System.out.println(block.nonce);

        System.out.println("\nTimestamp:");
        System.out.println(block.timeStamp);

        System.out.println("\nTransactions:");

        if (block.transactions.isEmpty()) {

            System.out.println("None");

        } else {

            for (Transaction tx : block.transactions) {

                System.out.println("--------------------------------");

                System.out.println("Transaction ID:");
                System.out.println(tx.transactionId);

                System.out.println("Amount: " + tx.value);

                System.out.println("Sender:");
                System.out.println(StringUtil.getStringFromKey(tx.sender)
                );

                System.out.println("Receiver:");
                System.out.println(StringUtil.getStringFromKey(tx.recipient));

            }

        }

        System.out.println("========================================");

    }

}