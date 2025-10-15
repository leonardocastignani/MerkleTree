<h2 align="center">
  University of Camerino<br>
  School of Science and Technology<br>
  Degree in Computer Science<br>
  Algorithms and Data Structures Course 2024/2025<br>
  Laboratory Part (6 CFU)<br>
  <br>
    Instructions for Project 1<br>
  <br>
  Project 1: Implementation and Validation of a Merkle Tree-Based System
</h2>

---

### **Merkle Tree**
A **Merkle Tree** is a binary tree data structure used to ensure the integrity and authenticity of a data set. It is composed of nodes, each of which contains a hash value:
- **Leaves**: These represent the hashes of the original data.
- **Intermediate Nodes**: These represent the hashes obtained by combining the hashes of the child nodes (left and right).
- **Root Hash**: This is the topmost node in the tree, obtained by combining the intermediate hashes.

#### **Merkle Tree Example**
1. The hashes of the leaves are calculated (e.g. `H1 = Hash(D1)`, `H2 = Hash(D2)`, ecc.).
2. The leaf hashes are combined to obtain intermediate hashes (e.g. `H12 = Hash(H1 + H2)`).
3. The root hash is calculated (e.g. `Hroot = Hash(H12 + H34)`).

---

### **Merkle Proof**
A **Merkle Proof** is a sequence of hashes that allows you to verify whether a piece of data belongs to a Merkle Tree without having to examine all the data. Verification occurs by reconstructing the root hash from the data and the hashes provided in the proof, comparing it to the root of the tree.

#### **Merkle Proof Example**
1. The hash of the data to be verified is provided (e.g. `H1`) and the hashes needed to trace the root (e.g. `H2` e `H34`).
2. The hashes are combined to reconstruct the root hash (e.g. `H12 = Hash(H1 + H2)`, `Hroot = Hash(H12 + H34)`).
3. If the calculated root hash matches that of the tree, the data is valid.

---

### **Merkle Tree with number of nodes not power of 2**
When the number of leaf nodes is not a power of 2, a strategy is adopted to manage single nodes:
- The single node hash is recalculated (e.g. `Hash(Hash(E))`).
- We proceed by combining the hashes as usual.

---

### **Advantages of Merkle Tree and Merkle Proof**
1. **Efficiency**: Quickly verify data with a small number of hashes.
2. **Integrity**: Immediate detection of manipulations.
3. **Scalability**: Suitable for very large data sets.

---

### **Applications**
- **Blockchain**: Transaction verification.
- **Distributed file systems**: Verify file integrity.
- **Database**: Ensure that the data has not been altered.

---

### **Note on hashing**
The project uses the **MD5** algorithm for hash calculation:
- **MD5**: Produces a 128-bit (16 byte) hash represented as a 32-character hexadecimal string.
- **Methods**:
  - `dataToHash(Object data)`: Calculate the hash of an object.
  - `computeMD5(byte[] input)`: Calculate the hash of a byte array.
- **Limits**: MD5 is vulnerable to collisions and is not secure for modern cryptographic applications. More secure alternatives include SHA-256.

---

### **Classes and Project Components**
1. **HashUtil**: Provides methods for calculating MD5 hashes.
2. **HashLinkedList**: A linked list that holds MD5 hashes of the elements.
3. **MerkleNode**: Represents a node in a Merkle Tree (leaf or intermediate node).
4. **MerkleProof**: It handles Merkle proofs to verify the membership of a given data point or branch in the tree.
5. **MerkleTree**: Represents and manages a complete Merkle tree, with methods for data validation and proof generation.

---

### **Project Purpose**
Implement a Merkle Tree-based system to ensure data integrity and authenticity across a larger data set, using the classes described above.
