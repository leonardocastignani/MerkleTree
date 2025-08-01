package it.unicam.cs.asdl2425.mp1;

/**
 * A class that represents a node in a Merkle tree.
 * 
 * @author Leonardo Castignani @UNICAM
 */
public class MerkleNode {
    private final String hash;
    private final MerkleNode left;
    private final MerkleNode right;

    /**
     * Constructs a leaf Merkle node with a hash value, hence, corresponding to
     * the hash of a given data item.
     *
     * @param hash  the hash associated with the node.
     */
    public MerkleNode(String hash) {
        this(hash, null, null);
    }

    /**
     * Constructs a Merkle node with a hash value and two children, hence,
     * corresponding to the hash of a branch.
     *
     * @param hash  the hash associated with the node.
     * @param left  the left son.
     * @param right  the right son.
     */
    public MerkleNode(String hash, MerkleNode left, MerkleNode right) {
        this.hash = hash;
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the hash associated with the node.
     *
     * @return the hash associated with the node.
     */
    public String getHash() {
        return this.hash;
    }

    /**
     * Returns the left child of the node.
     *
     * @return the left child of the node.
     */
    public MerkleNode getLeft() {
        return this.left;
    }

    /**
     * Returns the right child of the node.
     *
     * @return the right child of the node.
     */
    public MerkleNode getRight() {
        return this.right;
    }

    /**
     * Returns true if the node is a leaf, false otherwise.
     *
     * @return true if the node is a leaf, false otherwise.
     */
    public boolean isLeaf() {
    	return this.left == null && this.right == null;
    }

    @Override
    public String toString() {
        return this.hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (this == obj) return true;

        if (!(obj instanceof MerkleNode)) return false;

        MerkleNode otherNode = (MerkleNode) obj;

        return this.hash.equals(otherNode.getHash());
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
        int result = 1;

        return prime * result + (this.hash == null ? 0 : this.hash.hashCode());
    }
}