package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link MerkleProof} class. This class tests the behavior
 * of the {@link MerkleProof} class methods.
 * Below is a list of the included tests with a brief description:
 * 
 * <ul>
 * <li>{@link #testBuildProof1()}: Verify that a Merkle proof is constructed
 *     correctly by adding valid hashes, and that it is not possible to add more
 *     hashes than the specified limit.</li>
 * 
 * <li>{@link #testBuildProof2()}: Verifies that a Merkle proof with maximum
 *     length 0 does not allow any hashes to be added.</li>
 * 
 * <li>{@link #testBuildProof3()}: Internally check that the hashes inserted
 *     into the Merkle proof are stored correctly and in the correct position
 *     (left or right).</li>
 * 
 * <li>{@link #testVerifyProofOnData1()}: Verify that the validity of a piece of
 *     data is correctly confirmed against the constructed Merkle proof.</li>
 * 
 * <li>{@link #testVerifyProofOnData2()}: Verifies that the validity of a single
 *     piece of data (with proof length 0) is correctly confirmed against the
 *     Merkle proof.</li>
 * 
 * <li>{@link #testVerifyProofOnData3()}: Verifies that invalid data is
 *     correctly rejected by the Merkle proof.</li>
 * </ul>
 */
public class MerkleProofTest {

    private String rootHash1, rootHash2;


    @BeforeEach
    void setUp() {
        this.rootHash1 = HashUtil.computeMD5(
                    (HashUtil.computeMD5(
                    		  ((HashUtil.dataToHash("Alice paga Bob")
                    	        + HashUtil.dataToHash("Bob paga Charlie"))
                    				      .getBytes()))
                    		   + (HashUtil.computeMD5(
                    		      (HashUtil.dataToHash("Charlie paga Diana")
                    		       + HashUtil.dataToHash("Diana paga Alice"))
                    		                 .getBytes()))).getBytes()
                    );

        this.rootHash2 = HashUtil.dataToHash(true);
    }

    @Test
    void testBuildProof1() {
        MerkleProof proof = new MerkleProof(this.rootHash1, 2);
        
        assertTrue(proof.addHash(HashUtil.dataToHash("Alice paga Bob"), true),
        		                 "L'hash dovrebbe essere inserito "
        		                 + "correttamente");
        assertTrue(proof.addHash(HashUtil.computeMD5(
        		                 (HashUtil.dataToHash("Charlie paga Diana")
        		                  + HashUtil.dataToHash("Diana paga Alice"))
        		                            .getBytes()), false),
        		                  "L'hash dovrebbe essere inserito "
        		                  + "correttamente");
        
        assertFalse(proof.addHash("Hash non inserito", false),
        		                  "L'hash non dovrebbe essere inserito");
    }

    @Test
    void testBuildProof2() {
        MerkleProof proof = new MerkleProof(this.rootHash2, 0);
        
        assertFalse(proof.addHash("Hash non inserito", false),
        		                  "L'hash non dovrebbe essere inserito");
    }

	@Test
    void testBuildProof3() {
        try {
            MerkleProof proof =  new MerkleProof(this.rootHash1, 2);
            
            proof.addHash(HashUtil.dataToHash("Alice paga Bob"), true);
            
            proof.addHash(HashUtil.computeMD5(
            		      (HashUtil.dataToHash("Charlie paga Diana")
            		       + HashUtil.dataToHash("Diana paga Alice"))
            		                 .getBytes()), false);
            
            Class<?> clazz = proof.getClass();
            Field privateField = clazz.getDeclaredField("proof");
            privateField.setAccessible(true);

			HashLinkedList<MerkleProof.MerkleProofHash> list =
            		(HashLinkedList<MerkleProof.MerkleProofHash>)
            		privateField.get(proof);

            Iterator<MerkleProof.MerkleProofHash> itr = list.iterator();
            assertTrue(itr.hasNext());
            MerkleProof.MerkleProofHash next = itr.next();
            assertEquals(next.getHash(), HashUtil.dataToHash("Alice paga Bob"),
            		     "L'hash dovrebbe essere inserito correttamente");
            assertTrue(next.isLeft(),
            		   "L'hash dovrebbe essere inserito a sinistra");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testVerifyProofOnData1() {
        MerkleProof proof = new MerkleProof(this.rootHash1, 2);
        
        proof.addHash(HashUtil.dataToHash("Alice paga Bob"), true);
        
        proof.addHash(HashUtil.computeMD5(
        		      (HashUtil.dataToHash("Charlie paga Diana")
        		       + HashUtil.dataToHash("Diana paga Alice"))
        		                 .getBytes()), false);

        assertTrue(proof.proveValidityOfData("Bob paga Charlie"),
        		   "La prova di validit� del dato dovrebbe andare a buon fine");
    }

    @Test
    void testVerifyProofOnData2() {
        MerkleProof proof = new MerkleProof(this.rootHash2, 0);

        assertTrue(proof.proveValidityOfData(true),
        		   "La prova di validit� del dato dovrebbe andare a buon fine");
    }

    @Test
    void testVerifyProofOnData3() {
        MerkleProof proof = new MerkleProof(this.rootHash1, 2);
        
        proof.addHash(HashUtil.dataToHash("Alice paga Bob"), true);
        
        proof.addHash(HashUtil.computeMD5(
        		      (HashUtil.dataToHash("Charlie paga Diana")
        		       + HashUtil.dataToHash("Diana paga Alice"))
        		                 .getBytes()), false);

        assertFalse(proof.proveValidityOfData("Dato non valido"),
        		    "La prova di validit� del dato non dovrebbe andare a buon "
        		    + "fine");
    }
}