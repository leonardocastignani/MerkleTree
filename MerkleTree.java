package it.unicam.cs.asdl2425.mp1;

import java.util.*;

/**
 * A Merkle Tree, also known as a binary hash tree, is a data structure for
 * efficiently verifying the integrity and authenticity of data within a larger
 * data set. It is constructed by recursively hashing pairs of data
 * (cryptographic hash values) until a single root hash is obtained. In this
 * implementation, data verification is performed using MD5 hashes.
 * 
 * @author Leonardo Castignani @UNICAM
 *
 * @param <T>  the type of data on which the tree is built.
 */
public class MerkleTree<T> {

    private final MerkleNode root;
    private final int width;

    /**
     * Constructs a Merkle tree from a HashLinkedList object, using the hashes
     * in the list directly to construct the leaves. Note that the hashes of the
     * intermediate nodes should be obtained from the lower ones by
     * concatenating adjacent hashes two by two and directly applying the MD5
     * hash function to the result of the concatenation in bytes.
     *
     * @param hashList  a HashLinkedList object containing the data and its
     *                  hashes.
     * @throws IllegalArgumentException if the list is null or empty.
     */
    public MerkleTree(HashLinkedList<T> hashList) {
    	if(hashList == null || hashList.getSize() == 0)
    		throw new IllegalArgumentException();
    	
        List<MerkleNode> leafNodes = new ArrayList<MerkleNode>();
        
        for (T data : hashList) {
            String hash = HashUtil.dataToHash(data);
            leafNodes.add(new MerkleNode(hash));
        }
        
        List<MerkleNode> nodes = leafNodes;
        
        while (nodes.size() > 1) {
            List<MerkleNode> parentsNodes = new ArrayList<MerkleNode>();
            
            for (int i = 0; i < nodes.size(); i += 2) {
                if (i + 1 < nodes.size()) {
                    MerkleNode left = nodes.get(i);
                    
                    MerkleNode right = nodes.get(i + 1);
                    
                    String parent = HashUtil.computeMD5((left.getHash()
                    		        + right.getHash()).getBytes());
                    
                    parentsNodes.add(new MerkleNode(parent, left, right));
                }
                else {
                    MerkleNode onlyChild = nodes.get(i);
                    
                    String parent = HashUtil.computeMD5(
                    		        (onlyChild.getHash() + "").getBytes());
                    
                    parentsNodes.add(new MerkleNode(parent, onlyChild, null));
                }
            }
            nodes = parentsNodes;
        }
        
        this.root = nodes.get(0);
        this.width = leafNodes.size();
    }

    /**
     * Returns the root node of the tree.
     *
     * @return the root node.
     */
    public MerkleNode getRoot() {
        return this.root;
    }

    /**
     * Returns the width of the tree.
     *
     * @return the width of the tree.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the tree.
     *
     * @return the height of the tree.
     */    
    public int getHeight() {
    	int height = 0;
    	
        MerkleNode current = this.root;
        
        while (current.getLeft() != null || current.getRight() != null) {
            height++;
            current = current.getLeft() != null
            		  ? current.getLeft() : current.getRight();
        }
        
        return height;
    }

    /**
     * Returns the index of a given element according to the Merkle tree
     * described by a given branch. The provided indices start at 0 and
     * correspond to the order of the hashes corresponding to the elements in
     * the last level of the tree from left to right. If the provided branch
     * corresponds to the root of a subtree, the provided index represents a
     * relative index to that subtree, that is, an offset from the index of the
     * first element in the data block it represents. If the element's hash is
     * not present in the tree, -1 is returned.
     *
     * @param branch  the root of the Merkle tree.
     * @param data  the item to search for.
     * @return the index of the data in the tree; -1 if the hash of the data is
     *         not present.
     * @throws IllegalArgumentException if the branch or data is null or if the
     *         branch is not part of the tree.
     */
    public int getIndexOfData(MerkleNode branch, T data) {
    	if (branch == null || data == null)
    		throw new IllegalArgumentException();
    	
        String hashData = HashUtil.dataToHash(data);
        
        return getIndexOfDataRec(branch, hashData, 0);
    }
    
    /**
     * Recursively searches for the index of the node containing the specified
     * data in a Merkle tree. This method explores the Merkle tree starting from
     * the given node, comparing the hash of each node with the hash of the data
     * being searched for. If the hash of the current node matches the hash of
     * the data, the current index is returned. If the data is not found in the
     * current node, the method continues the recursive search in the left and
     * right children of the node. The index of each child node is calculated by
     * multiplying the index of the current node by 2 (for the left child) and
     * by 2 plus 1 (for the right child).
     *
     * @param node  the current node in the Merkle tree to search for the data. 
     * @param hashData  the hash of the data to search for. 
     * @param index   the current index of the node in the tree structure.
     * @return the index of the node containing the specified data, or -1 if the
     *         data is not found.
     * @throws IllegalArgumentException if the provided node is null or if the
     *         data hash is null.
     */
    private int getIndexOfDataRec(MerkleNode node, String hashData, int index) {
        if (node == null) return -1;
        
        if (node.getHash().equals(hashData)) return index;
        
        int leftIndex = this.getIndexOfDataRec(
                        node.getLeft(), hashData, index * 2);
        
        if (leftIndex != -1) return leftIndex;
        
        return this.getIndexOfDataRec(node.getRight(), hashData, index * 2 + 1);
    }

    /**
     * Returns the index of an element according to this Merkle tree. The
     * supplied indices start at 0 and correspond to the order of the hashes
     * corresponding to the elements in the last level of the tree from left to
     * right (and therefore the order of the elements supplied during
     * construction). If the element's hash is not present in the tree, -1 is
     * returned.
     *
     * @param data  the item to search for.
     * @return the index of the data in the tree; -1 if the data is not present.
     * @throws IllegalArgumentException if the data is null.
     */
    public int getIndexOfData(T data) {
    	if (data == null) throw new IllegalArgumentException();
    	
        String hashData = HashUtil.dataToHash(data);
        
        return this.getIndexOfDataRec(this.root, hashData, 0);
    }

    /**
     * Validates a supplied element to see if it belongs to the Merkle tree by
     * checking whether its hash is part of the tree as a hash of a leaf node.
     *
     * @param data  the element to validate.
     * @return true if the element's hash is part of the tree; false otherwise.
     */
    public boolean validateData(T data) {
    	if (data == null) throw new IllegalArgumentException();
    	
        String hashData = HashUtil.dataToHash(data);
        
        return this.validateDataRec(this.root, hashData);
    }

    /**
     * Recursively checks whether a data item with the specified hash exists in
     * a Merkle tree. This method explores the Merkle tree starting at the given
     * node, comparing the hash of each node with the hash of the data item
     * being checked. If the hash of the current node matches the hash of the
     * data item, true is returned. If the current node is null or if the hash
     * of the current node does not match, the method continues the recursive
     * search of the left and right children of the node.
     *
     * @param node  the current node in the Merkle tree to search for the data.
     * @param hashData  the hash of the data to be verified.
     * @return true if the data hash is found at a node in the tree, false
     *         otherwise.
     * @throws IllegalArgumentException if the node or hash of the data is null.
     */
    private boolean validateDataRec(MerkleNode node, String hashData) {
        if (node == null) return false;
        
        if (node.getHash().equals(hashData)) return true;
        
        return this.validateDataRec(node.getLeft(), hashData)
        	   || this.validateDataRec(node.getRight(), hashData);
    }

    /**
     * Validates a given Merkle subtree, corresponding to a block of data, to
     * verify whether it is valid with respect to this tree and its hashes. A
     * subtree is valid if the hash of its root is equal to the hash of any
     * intermediate node in this tree. Note that the given subtree can
     * correspond to a leaf.
     *
     * @param branch  the root of the Merkle subtree to validate.
     * @return true if the Merkle subtree is valid; false otherwise.
     */
    public boolean validateBranch(MerkleNode branch) {
    	if (branch == null) throw new IllegalArgumentException();
    	
        String hashBranch = branch.getHash();
        
        return this.validateBranchRec(this.root, hashBranch);
    }

    /**
     * Recursively checks whether a node with the specified hash exists in a
     * branch of the Merkle tree. This method explores the Merkle tree starting
     * from the given node, comparing the hash of each node with the hash of the
     * branch being checked. If the hash of the current node matches the hash of
     * the branch, true is returned. If the current node is null or the hash of
     * the current node does not match, the method continues the recursive
     * search of the left and right children of the node.
     *
     * @param node  the current node in the Merkle tree to look up the branch
     *              hash at.
     * @param hashBranch  the hash of the branch to verify.
     * @return true if the branch hash is found at a tree node, false otherwise.
     * @throws IllegalArgumentException if the node or branch hash is null.
     */
    private boolean validateBranchRec(MerkleNode node, String hashBranch) {
        if (node == null) return false;
        
        if (node.getHash().equals(hashBranch)) return true;
        
        boolean validSinistra = this.validateBranchRec(
        		                node.getLeft(), hashBranch);
        
        if (validSinistra) return true;
        
        return this.validateBranchRec(node.getRight(), hashBranch);
    }

    /**
     * Validates a given Merkle tree to see if it is valid with respect to this
     * tree and its hashes. Thanks to the properties of Merkle trees, this can
     * be done in constant time.
     *
     * @param otherTree  the root node of the other Merkle tree to be validated.
     * @return true if the other Merkle tree is valid; false otherwise.
     * @throws IllegalArgumentException if the provided tree is null.
     */
    public boolean validateTree(MerkleTree<T> otherTree) {
    	if (otherTree == null) throw new IllegalArgumentException();
    	
        MerkleNode otherRoot = otherTree.getRoot();
        
        return this.validateTreeRec(this.root, otherRoot);
    }

    /**
     * Recursively validates two Merkle trees by checking whether they have the
     * same structure and hashes. This method checks whether two Merkle trees
     * are identical based on the given nodes. One tree is considered valid
     * relative to the other if both nodes at each corresponding position have
     * the same hash and structure. The method uses a recursive approach to
     * compare each pair of nodes.
     *
     * @param node  the current node of the first Merkle tree.
     * @param otherNode  the current node of the second Merkle tree.
     * @return true if the trees are identical in terms of structure and hash,
     *         false otherwise.
     * @throws IllegalArgumentException if one of the provided nodes is null.
     */
    private boolean validateTreeRec(MerkleNode node, MerkleNode otherNode) {
        if (node == null && otherNode == null) return true;
        
        if (node == null || otherNode == null) return false;
        
        if (!node.getHash().equals(otherNode.getHash())) return false;
        
        boolean validSinistra = this.validateTreeRec(
        		                node.getLeft(), otherNode.getLeft());
        
        boolean validDestra = this.validateTreeRec(
        		              node.getRight(), otherNode.getRight());
        
        return validSinistra && validDestra;
    }

    /**
     * Find the indices of invalid data elements (i.e., those with different
     * hashes) in a given Merkle Tree, according to this Merkle Tree. Thanks to
     * the properties of Merkle trees, this can be done by comparing the hashes
     * of corresponding internal nodes in the two trees. For example, in the
     * case of a single invalid data point, a single path of length equal to the
     * height of the tree would be followed. The indices provided start at 0 and
     * correspond to the order of the elements in the last level of the tree
     * from left to right (and therefore the order of the elements provided
     * during construction). If the provided tree has a different structure,
     * possibly due to a different number of elements in its construction, and
     * therefore does not represent the same data, an exception is thrown.
     *
     * @param otherTree  the other Merkle Tree.
     * @return the index set of invalid data items.
     * @throws IllegalArgumentException if the other tree is null or has a
     *         different structure.
     */
    public Set<Integer> findInvalidDataIndices(MerkleTree<T> otherTree) {
        if (otherTree == null || otherTree.getWidth() != this.width)
            throw new IllegalArgumentException();
        
        Set<Integer> invalidIndex = new HashSet<Integer>();
        
        this.compareNodes(this.root, otherTree.getRoot(), 0, invalidIndex);
        
        return invalidIndex;
    }

    /**
     * Recursively compares two Merkle nodes and identifies differences in their
     * hashes. This method compares two Merkle nodes and their respective
     * subtrees, adding the index of nodes with different hashes to a set of
     * invalid indices. If one of the nodes is null, the index is added
     * directly. If the node hashes do not match, the method checks whether both
     * nodes are leaves; if so, the index is added; otherwise, the search
     * continues recursively in the left and right children.
     *
     * @param node1  the first Merkle node to compare.
     * @param node2  the second Merkle node to compare.
     * @param index  the current index in the comparison.
     * @param invalidIndices  the set of invalid node indices.
     * @throws IllegalArgumentException if one of the provided nodes is null.
     */
    private void compareNodes(MerkleNode node1, MerkleNode node2, int index,
    		Set<Integer> invalidIndices) {
        if (node1 == null || node2 == null) {
            if (node1 != node2) invalidIndices.add(index);
            return;
        }
        
        if (!node1.getHash().equals(node2.getHash())) {
            if (node1.isLeaf() && node2.isLeaf()) invalidIndices.add(index);
            else {
                this.compareNodes(node1.getLeft(), node2.getLeft(), index * 2,
                		          invalidIndices);
                this.compareNodes(node1.getRight(), node2.getRight(),
                		          index * 2 + 1, invalidIndices);
            }
        }
    }

    /**
     * Returns the Merkle proof for a given element, that is, the list of hashes
     * of the sibling nodes of each node on the path from the root to a leaf
     * containing the data item. The Merkle proof should provide a list of
     * MerkleProofHash objects such that, by combining the data item's hash with
     * the hash of the first MerkleProofHash object into a new hash, the result
     * with the next, and so on until the last object, the hash of the tree's
     * parent node can be obtained. If, at certain steps of the proof, there are
     * no two distinct hashes to combine, the hash must still be recalculated
     * based on the single available hash.
     *
     * @param data  the element for which to generate the Merkle proof.
     * @return Merkle's proof for the given.
     * @throws IllegalArgumentException if the data is null or not part of the
     *         tree.
     */
    public MerkleProof getMerkleProof(T data) {
        if (data == null) throw new IllegalArgumentException();
        
        String hash = HashUtil.dataToHash(data);
        
        List<MerkleProof.MerkleProofHash> proofHashes =
        		new ArrayList<MerkleProof.MerkleProofHash>();
        
        if (!this.buildMerkleProofRec(this.root, hash, proofHashes))
        	throw new IllegalArgumentException();
        
        MerkleProof proof = new MerkleProof(this.root.getHash(),
        		                            proofHashes.size());
        
        for (MerkleProof.MerkleProofHash proofHash : proofHashes) {
        	proof.addHash(proofHash.getHash(), proofHash.isLeft());
        }
        
        return proof;
    }

    /**
     * Recursively constructs a Merkle proof for a given data point with the
     * specified hash in a Merkle tree. This method explores the Merkle tree
     * starting from the given node, searching for a node with the specified
     * hash. If a node with a matching hash is found, the method adds the hashes
     * of its siblings to the proof list, thus constructing a Merkle proof. The
     * method checks both the left and right subtrees and adds the corresponding
     * hashes to the proof list to reconstruct the verification path.
     *
     * @param node  the current node in the Merkle tree in which to look up the
     *              hash of the data.
     * @param hash  the hash of the data to be verified.
     * @param proofHashes  the list of Merkle proofs for the given.
     * @return true if the hash of the data is found and the Merkle proof is
     *         constructed, false otherwise.
     * @throws IllegalArgumentException if the node or hash of the data is null.
     */
    private boolean buildMerkleProofRec(MerkleNode node, String hash,
    		List<MerkleProof.MerkleProofHash> proofHashes) {
        if (node == null) return false;
        
        if (node.getHash().equals(hash)) return true;
        
        if (node.getLeft() != null && this.buildMerkleProofRec(
        		                      node.getLeft(), hash, proofHashes)) {
        	proofHashes.add(new MerkleProof.MerkleProofHash(
        			        node.getRight() != null ?
        			        node.getRight().getHash() : "", false));
            return true;
        }
        
        if (node.getRight() != null && this.buildMerkleProofRec(
        		                       node.getRight(), hash, proofHashes)) {
        	proofHashes.add(new MerkleProof.MerkleProofHash(
        			        node.getLeft() != null ?
        			        node.getLeft().getHash() : "", true));
        	return true;
        }
        
        return false;
    }

    /**
     * Returns the Merkle proof for a given branch, that is, the list of hashes
     * of the sibling nodes of each node on the path from the root to the given
     * branch node, representing a block of data. The Merkle proof should
     * provide a list of MerkleProofHash objects such that, by combining the
     * branch hash with the hash of the first MerkleProofHash object into a new
     * hash, the result with the next, and so on until the last object, we can
     * obtain the hash of the parent node of the tree. If, at certain steps of
     * the proof, there are no two distinct hashes to combine, the hash must
     * still be recalculated based on the single available hash.
     *
     * @param branch  the branch for which to generate the Merkle proof.
     * @return Merkle's proof for the branch.
     * @throws IllegalArgumentException if the branch is null or not part of
     *         the tree.
     */
    public MerkleProof getMerkleProof(MerkleNode branch) {
        if (branch == null) throw new IllegalArgumentException();
        
        String hash = branch.getHash();
        
        List<MerkleProof.MerkleProofHash> proofHashes =
        		new ArrayList<MerkleProof.MerkleProofHash>();
        
        if (!this.buildMerkleProofRec(this.root, hash, proofHashes))
        	throw new IllegalArgumentException();
        
        MerkleProof proof = new MerkleProof(
        		            this.root.getHash(), proofHashes.size());
        
        for (MerkleProof.MerkleProofHash provaHash : proofHashes) {
        	proof.addHash(provaHash.getHash(), provaHash.isLeft());
        }
        
        return proof;
    }
}