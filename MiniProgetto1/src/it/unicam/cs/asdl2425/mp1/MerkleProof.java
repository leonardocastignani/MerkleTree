package it.unicam.cs.asdl2425.mp1;

/**
 * A class that represents a Merkle proof for a given Merkle tree and one of its
 * elements or branches. Objects of this class represent a self-contained
 * verification process, given by a sequence of MerkleProofHash objects
 * representing the steps required to validate a given element or branch in a
 * Merkle tree, as determined when constructing the proof.
 * 
 * @author Leonardo Castignani @UNICAM
 */
public class MerkleProof {

    private final HashLinkedList<MerkleProofHash> proof;
    private final String rootHash;
    private final int length;

    /**
     * Constructs a new Merkle proof for a given Merkle tree, specifying the
     * tree's root and the maximum length of the proof. The maximum length of
     * the proof is the number of hashes it contains when complete, beyond which
     * no further hashes can be added.
     *
     * @param rootHash  the hash of the root of the Merkle tree.
     * @param length  the maximum length of the proof.
     */
    public MerkleProof(String rootHash, int length) {
        if (rootHash == null) throw new IllegalArgumentException();
        
        this.proof = new HashLinkedList<MerkleProofHash>();
        this.rootHash = rootHash;
        this.length = length;
    }

    /**
     * Returns the maximum length of the proof, given by the number of hashes
     * that compose it when complete.
     *
     * @return the maximum length of the proof.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Adds a hash to the Merkle proof, specifying whether it should be left or
     * right concatenated during proof verification. If the proof is already
     * complete, meaning it has already reached the maximum number of hashes
     * decided upon during its construction, the hash is not added and the
     * function returns false.
     *
     * @param hash  the hash to add to the proof.
     * @param isLeft  true if the hash should be left-concatenated,
     * 				  false otherwise.
     * @return true if the hash was added successfully, false otherwise.
     */
    public boolean addHash(String hash, boolean isLeft) {
    	if (hash == null) throw new IllegalArgumentException();

        if (this.proof.getSize() >= this.length) return false;

        this.proof.addAtTail(new MerkleProofHash(hash, isLeft));
        
        return true;
    }

    /**
     * It represents a single step of a Merkle proof for validating a given
     * element.
     */
    public static class MerkleProofHash {

        private final String hash;
        private final boolean isLeft;

        public MerkleProofHash(String hash, boolean isLeft) {
            if (hash == null) throw new IllegalArgumentException();

            this.hash = hash;
            this.isLeft = isLeft;
        }

        /**
         * Returns the hash of the MerkleProofHash object.
         *
         * @return the hash of the MerkleProofHash object.
         */
        public String getHash() {
            return this.hash;
        }

        /**
         * Returns true if, during proof verification, the object's hash should
         * be left concatenated, false otherwise.
         *
         * @return true if the object's hash should be left-concatenated,
         *         false otherwise.
         */
        public boolean isLeft() {
            return this.isLeft;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;

            if (this == obj) return true;

            if (!(obj instanceof MerkleProofHash)) return false;

            MerkleProofHash otherMerkleProofHash = (MerkleProofHash) obj;
            
            return this.hash.equals(otherMerkleProofHash.getHash())
            	   && this.isLeft == otherMerkleProofHash.isLeft();
        }

        @Override
        public String toString() {
            return hash + (isLeft ? "L" : "R");
        }

        @Override
        public int hashCode() {
        	final int prime = 31;
        	int result = this.hash.hashCode();

            return prime * result + (this.isLeft ? 1 : 0);
        }
    }

    /**
     * Validate a given element for this Merkle proof. Verification is done by
     * combining the data's hash with the hash of the first MerkleProofHash
     * object to form a new hash, then combining the result with the next, and
     * so on until the last object, checking that the final hash matches that of
     * the root node of the original Merkle tree.
     *
     * @param data  the element to validate.
     * @return true if the data is valid according to the proof; false otherwise.
     * @throws IllegalArgumentException if the data is null.
     */
    public boolean proveValidityOfData(Object data) {
    	if (data == null) throw new IllegalArgumentException();

        String currentHash = HashUtil.dataToHash(data);

        for (MerkleProofHash hashProof : this.proof) {
            if (hashProof.isLeft())
            	currentHash = HashUtil.computeMD5(
            			      (hashProof.getHash() + currentHash).getBytes());
            else
            	currentHash = HashUtil.computeMD5(
            		          (currentHash + hashProof.getHash()).getBytes());
        }

        return currentHash.equals(this.rootHash);
    }

    /**
     * Validate a given branch for this Merkle proof. Verification is done by
     * combining the branch's hash with the hash of the first MerkleProofHash
     * object to form a new hash, then combining the result with the next, and
     * so on until the last object, checking that the final hash matches that
     * of the root node of the original Merkle tree.
     *
     * @param branch  the branch to validate.
     * @return true if the branch is valid according to the proof; false otherwise.
     * @throws IllegalArgumentException if the branch is null.
     */
    public boolean proveValidityOfBranch(MerkleNode branch) {
    	if (branch == null) throw new IllegalArgumentException();

        String currentHash = branch.getHash();

        for (MerkleProofHash hashProva : this.proof) {
            if (hashProva.isLeft())
            	currentHash = HashUtil.computeMD5(
            			      (hashProva.getHash() + currentHash).getBytes());
            else
            	currentHash = HashUtil.computeMD5(
            			      (currentHash + hashProva.getHash()).getBytes());
        }

        return currentHash.equals(this.rootHash);
    }
}