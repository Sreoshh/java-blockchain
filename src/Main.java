public class Main {

    public static void main(String[] args) {

        Block firstBlock = new Block("Hello Blockchain", "0");

        System.out.println("Block Data : " + firstBlock.data);
        System.out.println("Previous Hash : " + firstBlock.previousHash);
        System.out.println("Current Hash : " + firstBlock.hash);

    }
}