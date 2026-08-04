# Changelog

All notable changes to this project will be documented in this file.

## [0.1.0] - Initial Blockchain Implementation

### Added
- Project setup using Java in VS Code.
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

