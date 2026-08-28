# Changelog

All notable changes to this project will be documented in this file.

## [0.1.0] - Initial Blockchain Implementation

### Added
- Implemented `StringUtil` for SHA-256 hashing.
- Created the `Block` class with:
  - Block hash
  - Previous hash
  - Data
  - Timestamp
  - Nonce
- Implemented block hash calculation using SHA-256.
- Created the `Blockchain` class to manage a chain of blocks.
- Added blockchain validation to detect data tampering.
- Implemented Proof of Work (PoW) mining with configurable difficulty.
- Added Genesis Block creation.
- Linked blocks using previous block hashes.
- Demonstrated blockchain integrity verification and tamper detection.

## [0.2.0] - Wallets & Digital Signatures

### Added
- Implemented ECDSA digital signature utilities.
- Added key encoding utility for readable public/private keys.
- Created `Wallet` class with automatic EC key pair generation.
- Implemented `Transaction` class.

<img width="1920" height="1032" alt="Screenshot 2026-08-04 115952" src="https://github.com/user-attachments/assets/507ad29a-0cbd-4531-94ba-84e464fc2858" />


- Added transaction signing using the sender's private key.
- Added signature verification using the sender's public key.
- Successfully verified valid transactions and detected tampered transactions.
<img width="1920" height="1032" alt="Screenshot 2026-08-04 121722" src="https://github.com/user-attachments/assets/59a3f66d-4e1e-44a4-b0a3-f946732e6f64" />


## [0.3.0] - Cryptocurrency Transactions & UTXO Model

### Added
- Implemented `TransactionInput` and `TransactionOutput` classes.
- Introduced the UTXO (Unspent Transaction Output) model for tracking spendable coins.
- Added global UTXO management to the blockchain.
- Enhanced `Wallet` to:
  - Calculate wallet balance.
  - Collect UTXOs for transactions.
  - Create and sign transactions.
- Enhanced `Transaction` to:
  - Process transactions.
  - Validate digital signatures.
  - Generate unique transaction IDs.
  - Create new transaction outputs.
  - Return change to the sender.
  - Update the global UTXO set.
- Updated `Block` to store and process transactions.
- Implemented the Genesis Transaction.
- Enabled secure coin transfers between wallets.
- Successfully demonstrated wallet balance updates after transactions.

What just happened?
- Genesis Transaction
- Instead of magically giving Wallet A money, we created the very first transaction:
- Coinbase -> Wallet A
- 100 Coins
- Wallet A Sends 40
- Wallet A owns 100 Coins
- She sends 40 Coins
- The blockchain creates Wallet B = 40 Coins
- and Wallet A = 60 Coins (Change)
- Notice that Wallet A's original 100-coin output no longer exists. It has been spent and replaced with two new outputs.
<img width="1920" height="1032" alt="image" src="https://github.com/user-attachments/assets/e9b2cb4a-a1a4-4c61-8a28-b3d0938ba851" />

## [0.4.0] - Merkle Tree & Blockchain Explorer

### Added
- Implemented Merkle Tree generation for block transactions.
- Updated block hashing to use the Merkle Root instead of raw block data.
- Added Merkle Root calculation before block mining.
- Created a console based Blockchain Explorer to display:
  - Block hash
  - Previous hash
  - Merkle Root
  - Nonce
  - Timestamp
  - Transaction details
- Improved blockchain visualization and debugging.
<img width="1920" height="1032" alt="image" src="https://github.com/user-attachments/assets/220b1e92-e7ca-41fa-af20-9f335c80969a" />

### Added
- Migrated the project to a standard Maven/Spring Boot directory structure.
- Added `com.javablockchain` package structure across Java classes.
- Added Spring Boot application entry point.
- Verified successful Maven compilation and Spring Boot startup on port 8080.

### Added
- Integrated Spring Boot into the blockchain project.
- Added `Application.java` as the Spring Boot entry point.
- Added `BlockchainController` for REST API access.
- Added blockchain status, validation, and blockchain retrieval endpoints.
- Added a mining endpoint to create and mine new blocks.
- Added a persistent miner wallet for mining rewards.

### Verified
- Spring Boot application starts successfully.
- Mining endpoint successfully creates a block.
- Blockchain status endpoint reflects the new block.
- Blockchain validation returns `true`.
<img width="1049" height="586" alt="Screenshot 2026-08-27 100529" src="https://github.com/user-attachments/assets/6a433c2d-6cbb-4ef0-b3fc-f5dbedb6fe14" />
Useful endpoints for reference:

- GET  /api/blockchain
- GET  /api/blockchain/validate
- GET  /api/blockchain/status
- POST /mine

### Added
- Added UTXO-based transaction handling.
- Added wallet fund-transfer functionality with digital signatures.
- Added mining reward transactions.
- Added transaction processing and validation.
- Added transaction input/output validation.
- Added blockchain difficulty adjustment based on mining time.
- Added REST endpoints for blockchain status, validation, mining, and transactions.
### Changed
- Updated blockchain validation to verify transaction signatures and Merkle roots.
- Updated block mining to include mining reward transactions.
- Updated UTXO management when transactions are processed.
- Updated blockchain controller to support transaction creation and mining.