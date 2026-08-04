public class Main {

    public static void main(String[] args) {

        Blockchain blockchain = new Blockchain();

        Block genesis = new Block("Genesis Block", "0");
        blockchain.addBlock(genesis);

        Block second = new Block("Second Block", genesis.hash);
        blockchain.addBlock(second);

        Block third = new Block("Third Block", second.hash);
        blockchain.addBlock(third);

        blockchain.chain.get(1).data = "Hacked Block";
        
        System.out.println("Is blockchain valid? " + blockchain.isChainValid());

    }

}