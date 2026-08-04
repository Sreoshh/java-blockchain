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

