
A simple blockchain and cryptocurrency implementation built from scratch in Java. The project started from a basic blockchain implementation and has been progressively extended with transactions, wallets, UTXOs, Merkle Trees, mining rewards, dynamic difficulty, persistence, and blockchain validation.

## Features

* SHA 256 hashing
* Proof of Work mining
* Blockchain validation
* ECDSA digital signatures
* Public/private key wallets
* UTXO based transactions
* Mining rewards
* Dynamic mining difficulty
* Merkle Tree support

## Requirements

Install the following before running the project:

* **Java 21 JDK**
* **Apache Maven**
* **Git**
* **VS Code** or another Java IDE

Check the installations:

```powershell
java --version
javac --version
mvn -version
git --version
```

Java should report version 21 or compatible, and Maven should be available from the terminal.

## Getting Started

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd JavaBlockchain
```

### 2. Project Structure

```text
JavaBlockchain/
│
├── pom.xml
├── CHANGELOG.md
├── README.md
│
└── src/
    ├── Main.java
    ├── Block.java
    ├── Blockchain.java
    ├── BlockchainStorage.java
    ├── BlockExplorer.java
    ├── Transaction.java
    ├── TransactionInput.java
    ├── TransactionOutput.java
    ├── Wallet.java
    └── StringUtil.java
```

### 3. Build the project

Run from the project root:

```powershell
mvn clean compile
```

Maven automatically downloads the required dependencies including Gson.

### 4. Run the blockchain

```powershell
mvn exec:java "-Dexec.mainClass=Main"
```

The program will create wallets, create transactions, mine blocks, validate the blockchain, display wallet balances, and show blockchain information.

## Development Workflow

After making changes:

```powershell
mvn clean compile
```

Then run:

```powershell
mvn exec:java "-Dexec.mainClass=Main"
```

## Persistence

The blockchain can be saved to and loaded from:

```text
blockchain.json
```

Loaded blockchain data is validated before being accepted.

## Blockchain Architecture

```text
Wallet
  │
Transaction
  │
   Inputs
   Outputs
        │
        UTXO
        │
        Block
        │
         Transactions
         Merkle Root
         Previous Hash
         Nonce
         Timestamp
        │
        
    Blockchain
```

## Running Notes

Run Maven commands from the directory containing `pom.xml`:

```text
JavaBlockchain/
└── pom.xml
```

Do not run the project using:

```powershell
javac Main.java
```

because Maven manages the project's external dependencies.

Use:

```powershell
mvn clean compile
mvn exec:java "-Dexec.mainClass=Main"
```

instead.
