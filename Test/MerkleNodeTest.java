package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link MerkleNode} class. This class tests the behavior of
 * the {@link MerkleNode} class methods.
 * Below is a list of the included tests with a brief description:
 * 
 * <ul>
 * <li>{@link #testLeafNodeCreation()}: Verifies that a leaf node is created
 *     correctly by checking its hash, null children, and identification as a
 *     leaf.</li>
 * 
 * <li>{@link #testBranchNodeCreation()}: Verify that a branch node is created
 *     correctly with hashes, left and right children, and that it is not
 *     identified as a leaf.</li>
 * 
 * <li>{@link #testToString()}: Check that the {@code toString} method correctly
 *     returns the node hash.</li>
 * 
 * <li>{@link #testEqualsSameHash()}: Verifies that two nodes with the same hash
 *     are considered equal.</li>
 * 
 * <li>{@link #testEqualsDifferentHash()}: Verify that two nodes with different
 *     hashes are not considered equal.</li>
 * 
 * <li>{@link #testHashCode()}: Check that two nodes with the same hash have the
 *     same {@code hashCode} value.</li>
 * 
 * <li>{@link #testNotEqualsWithNull()}: Checks that a node is not equal to
 *     {@code null}.</li>
 * 
 * <li>{@link #testNotEqualsWithDifferentType()}: Checks that a node is not
 *     equal to an object of a different type.</li>
 * </ul>
 */
class MerkleNodeTest {

    @Test
    void testLeafNodeCreation() {
        MerkleNode leaf = new MerkleNode("hash123");

        assertEquals("hash123", leaf.getHash(),
                     "L'hash del nodo foglia non è corretto.");
        assertNull(leaf.getLeft(),
                   "Un nodo foglia non dovrebbe avere un figlio sinistro.");
        assertNull(leaf.getRight(),
                   "Un nodo foglia non dovrebbe avere un figlio destro.");
        assertTrue(leaf.isLeaf(),
                   "Il nodo dovrebbe essere identificato come foglia.");
    }

    @Test
    void testBranchNodeCreation() {
        MerkleNode left = new MerkleNode("leftHash");
        MerkleNode right = new MerkleNode("rightHash");
        MerkleNode branch = new MerkleNode("branchHash", left, right);

        assertEquals("branchHash", branch.getHash(),
                     "L'hash del nodo branch non è corretto.");
        assertEquals(left, branch.getLeft(),
                     "Il figlio sinistro del nodo branch non è corretto.");
        assertEquals(right, branch.getRight(),
                     "Il figlio destro del nodo branch non è corretto.");
        assertFalse(branch.isLeaf(),
                    "Il nodo branch non dovrebbe essere identificato come "
                    + "foglia.");
    }

    @Test
    void testToString() {
        MerkleNode node = new MerkleNode("hashToString");

        assertEquals("hashToString", node.toString(),
                     "Il metodo toString non restituisce l'hash corretto.");
    }

    @Test
    void testEqualsSameHash() {
        MerkleNode node1 = new MerkleNode("hashEquals");
        MerkleNode node2 = new MerkleNode("hashEquals");

        assertEquals(node1, node2,
                     "Due nodi con lo stesso hash dovrebbero essere uguali.");
    }

    @Test
    void testEqualsDifferentHash() {
        MerkleNode node1 = new MerkleNode("hash1");
        MerkleNode node2 = new MerkleNode("hash2");

        assertNotEquals(node1, node2,
                        "Due nodi con hash diversi non dovrebbero essere "
                        + "uguali.");
    }

    @Test
    void testHashCode() {
        MerkleNode node1 = new MerkleNode("hash123");
        MerkleNode node2 = new MerkleNode("hash123");

        assertEquals(node1.hashCode(), node2.hashCode(),
                     "Due nodi con lo stesso hash dovrebbero avere lo stesso "
                     + "hashCode.");
    }

    @Test
    void testNotEqualsWithNull() {
        MerkleNode node = new MerkleNode("hash123");

        assertNotEquals(null, node,
                        "Un nodo non dovrebbe essere uguale a null.");
    }

    @Test
    void testNotEqualsWithDifferentType() {
        MerkleNode node = new MerkleNode("hash123");

        assertNotEquals(node,
                        "Un oggetto di tipo diverso non dovrebbe essere uguale "
                        + "a un MerkleNode.");
    }
}