package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link MerkleTree} class. This class tests the behavior of
 * the {@link MerkleTree} class methods.
 * Below is a list of the included tests with a brief description:
 * 
 * <ul>
 * <li>{@link #testConstructorWithValidHashList1()}: Verifies the correct
 *     construction of a MerkleTree with a valid 4-element hash list.</li>
 * 
 * <li>{@link #testConstructorWithValidHashList2()}: Verifies the correct
 *     construction of a MerkleTree with a valid 13-element hash list.</li>
 * 
 * <li>{@link #testConstructorWithValidHashList3()}: Verifies the correct
 *     construction of a MerkleTree with a valid 1-element hash list.</li>
 * 
 * <li>{@link #testConstructorWithEmptyHashList()}: Ensures that an exception is
 *     thrown for an empty list.</li>
 * 
 * <li>{@link #testConstructorWithNullHashList()}: Verify that an exception is
 *     thrown for a null list.</li>
 * 
 * <li>{@link #testGetHeight1()}, {@link #testGetHeight2()},
 *     {@link #testGetHeight3()}: They verify that the tree height is calculated
 *     correctly for different configurations.</li>
 * 
 * <li>{@link #testValidateData1()}, {@link #testValidateData2()},
 *     {@link #testValidateData3()}: They verify that data validation in the
 *     tree is working correctly.</li>
 * 
 * <li>{@link #testGetIndexOfData1()}, {@link #testGetIndexOfData2()},
 *     {@link #testGetIndexOfData3()}, {@link #testGetIndexOfData4()}: They
 *     verify that the index of a data item is calculated correctly or returns
 *     -1 for non-present data.</li>
 * 
 * <li>{@link #testGetIndexOfDataInBranch()},
 *     {@link #testGetIndexOfDataInBranchNotPresent()}: They verify the
 *     calculation of the relative index of a data item in a branch of the
 *     tree.</li>
 * 
 * <li>{@link #testValidateBranch1()}, {@link #testValidateBranch2()},
 *     {@link #testValidateBranch3()}, {@link #testValidateBranch4()}: They
 *     verify that the tree branches are validated correctly.</li>
 * 
 * <li>{@link #testValidateTree1()}, {@link #testValidateTree2()},
 *     {@link #testValidateTree3()}, {@link #testValidateTree4()}: They check
 *     the validity of one MerkleTree against another, whether the trees are
 *     identical or different.</li>
 * 
 * <li>{@link #testFindInvalidDataIndices1()},
 *     {@link #testFindInvalidDataIndices2()},
 *     {@link #testFindInvalidDataIndices3()},
 *     {@link #testFindInvalidDataIndices4()}: They verify that invalid data
 *     indexes are correctly identified against another tree.</li>
 * 
 * <li>{@link #testGetMerkleProofData()}, {@link #testGetMerkleProofData2()},
 *     {@link #testGetMerkleProofData3()}, {@link #testGetMerkleProofData4()}:
 *     They verify that Merkle proofs are generated correctly for different data
 *     points in the tree.</li>
 * 
 * <li>{@link #testGetMerkleProofDataNotPresent()}: Ensure that an exception is
 *     thrown when requesting a Merkle proof for data that is not present.</li>
 * 
 * <li>{@link #testVerifyProofData()}, {@link #testVerifyProofData2()},
 *     {@link #testVerifyProofData3()}, {@link #testVerifyProofData4()},
 *     {@link #testVerifyProofData5()}: They test the validity of a Merkle proof
 *     for data both present and not present in the tree.</li>
 * 
 * <li>{@link #testGetMerkleProofBranch1()},
 *     {@link #testGetMerkleProofBranch2()}: They verify the generation of
 *     Merkle proofs for specific branches of the tree.</li>
 * 
 * <li>{@link #testVerifyProofBranch()}: Check the validity of a Merkle proof
 *     for a valid branch.</li>
 * 
 * <li>{@link #testVerifyProofBranchInvalid()}: Verifies that a Merkle proof for
 *     an invalid branch is rejected.</li>
 * 
 * <li>{@link #testSingleLeafTree()}: Tests the behavior of the MerkleTree for a
 *     single element, checking the width, height, and validity of the
 *     data.</li>
 * </ul>
 */
class MerkleTreeTest {

    private HashLinkedList<String> hashList1;
    private HashLinkedList<Long> hashList2;
    private HashLinkedList<Boolean> hashList3;

    private MerkleTree<String> merkleTree1;
    private MerkleTree<Long> merkleTree2;
    private MerkleTree<Boolean> merkleTree3;

    @BeforeEach
    void setUp() {
    	this.hashList1 = new HashLinkedList<String>();
    	this.hashList1.addAtTail("Alice paga Bob");
    	this.hashList1.addAtTail("Bob paga Charlie");
    	this.hashList1.addAtTail("Charlie paga Diana");
    	this.hashList1.addAtTail("Diana paga Alice");
        
    	this.merkleTree1 = new MerkleTree<String>(this.hashList1);

    	this.hashList2 = new HashLinkedList<Long>();
        for (int i = 1; i <= 13; i++) this.hashList2.addAtTail(111L * i);
        
        this.merkleTree2 = new MerkleTree<Long>(this.hashList2);

        this.hashList3 = new HashLinkedList<Boolean>();
        this.hashList3.addAtTail(true);

        this.merkleTree3 = new MerkleTree<Boolean>(this.hashList3);

    }

    @Test
    void testConstructorWithValidHashList1() {
        assertNotNull(this.merkleTree1.getRoot(), "La radice dell'albero non "
        		      + "dovrebbe essere null.");
        assertEquals(4, this.merkleTree1.getWidth(), "La larghezza dell'albero "
        		     + "dovrebbe essere 4.");
    }

    @Test
    void testConstructorWithValidHashList2() {
        assertNotNull(this.merkleTree2.getRoot(), "La radice dell'albero non "
        		      + "dovrebbe essere null.");
        assertEquals(13, this.merkleTree2.getWidth(), "La larghezza dell'"
        		     + "albero dovrebbe essere 13.");
    }

    @Test
    void testConstructorWithValidHashList3() {
        assertNotNull(this.merkleTree3.getRoot(), "La radice dell'albero non "
        		      + "dovrebbe essere null.");
        assertEquals(1, this.merkleTree3.getWidth(), "La larghezza dell'albero "
        		     + "dovrebbe essere 1.");
    }

    @Test
    void testConstructorWithEmptyHashList() {
        HashLinkedList<String> emptyList = new HashLinkedList<>();
        
        assertThrows(IllegalArgumentException.class,
                     () -> new MerkleTree<>(emptyList),
                     "Dovrebbe lanciare IllegalArgumentException per una lista "
                     + "vuota.");
    }

    @Test
    void testConstructorWithNullHashList() {
        assertThrows(IllegalArgumentException.class,
                     () -> new MerkleTree<>(null),
                     "Dovrebbe lanciare IllegalArgumentException per una lista "
                     + "null.");
    }

    @Test
    void testGetHeight1() {
        int expectedHeight = 2;
        
        assertEquals(expectedHeight, this.merkleTree1.getHeight(), "L'altezza "
        		     + "dell'albero non è corretta.");
    }

    @Test
    void testGetHeight2() {
        int expectedHeight = 4;
        
        assertEquals(expectedHeight, this.merkleTree2.getHeight(), "L'altezza "
        		     + "dell'albero non è corretta.");
    }

    @Test
    void testGetHeight3() {
        int expectedHeight = 0;
        
        assertEquals(expectedHeight, this.merkleTree3.getHeight(), "L'altezza "
        		     + "dell'albero non è corretta.");
    }

    @Test
    void testValidateData1() {
        assertTrue(this.merkleTree1.validateData("Alice paga Bob"),
                   "Il dato dovrebbe essere valido.");
        assertFalse(this.merkleTree1.validateData("Dati non presenti"),
                    "Il dato non dovrebbe essere valido.");
    }

    @Test
    void testValidateData2() {
        assertTrue(this.merkleTree2.validateData(555L), "Il dato dovrebbe "
        		   + "essere valido.");
        assertFalse(this.merkleTree2.validateData(112L), "Il dato non dovrebbe "
        		    + "essere valido.");
    }

    @Test
    void testValidateData3() {
        assertTrue(this.merkleTree3.validateData(true), "Il dato dovrebbe "
        		   + "essere valido.");
        assertFalse(this.merkleTree3.validateData(false), "Il dato non "
        		    + "dovrebbe essere valido.");
    }

    @Test
    void testGetIndexOfData1() {
        int index = this.merkleTree1.getIndexOfData("Alice paga Bob");
        assertEquals(0, index, "L'indice del dato 'Alice paga Bob' dovrebbe "
        		     + "essere 0.");

        index = this.merkleTree1.getIndexOfData("Diana paga Alice");
        assertEquals(3, index, "L'indice del dato 'Diana paga Alice' dovrebbe "
        		     + "essere 3.");
    }

    @Test
    void testGetIndexOfData2() {
        int index = this.merkleTree1.getIndexOfData("Dato non presente");
        
        assertEquals(-1, index, "Un dato non presente dovrebbe restituire -1.");
    }

    @Test
    void testGetIndexOfData3() {
        int index = this.merkleTree2.getIndexOfData(999L);
        assertEquals(8, index, "L'indice del dato 999 dovrebbe essere 8.");

        index = this.merkleTree2.getIndexOfData(1332L);
        assertEquals(11, index, "L'indice del dato 1332 dovrebbe essere 11.");
    }

    @Test
    void testGetIndexOfData4() {
        int index = this.merkleTree2.getIndexOfData(556L);
        
        assertEquals(-1, index, "Un dato non presente dovrebbe restituire -1.");
    }

    @Test
    void testGetIndexOfDataInBranch() {
        HashLinkedList<String> branchList = new HashLinkedList<String>();
        branchList.addAtTail("Charlie paga Diana");
        branchList.addAtTail("Diana paga Alice");
        
        MerkleTree<String> merkleTreeBranch =
        		           new MerkleTree<String>(branchList);

        int index = this.merkleTree1.getIndexOfData(merkleTreeBranch.getRoot(),
        		                                    "Charlie paga Diana");
        
        assertEquals(0, index, "L'indice relativo del dato 'Charlie paga "
        		     + "Diana' dovrebbe essere 0.");
        
        index = this.merkleTree1.getIndexOfData(merkleTreeBranch.getRoot(),
        		                                "Diana paga Alice");
        assertEquals(1, index, "L'indice relativo del dato 'Diana paga Alice' "
        		     + "dovrebbe essere 1.");
    }

    @Test
    void testGetIndexOfDataInBranchNotPresent() {
        HashLinkedList<String> branchList = new HashLinkedList<String>();
        branchList.addAtTail("Charlie paga Diana");
        branchList.addAtTail("Diana paga Alice");
        
        MerkleTree<String> merkleTreeBranch =
        		           new MerkleTree<String>(branchList);

        int index = this.merkleTree1.getIndexOfData(merkleTreeBranch.getRoot(),
        		                                    "Dato non presente");
        assertEquals(-1, index, "Un dato non presente nel branch dovrebbe "
        		     + "restituire -1.");
    }

    @Test
    void testValidateBranch1() {
        MerkleNode rootNode = this.merkleTree1.getRoot();
        assertTrue(this.merkleTree1.validateBranch(rootNode), "La radice "
        		   + "dovrebbe essere un branch valido.");

        MerkleNode leftNode = rootNode.getLeft();
        assertTrue(this.merkleTree1.validateBranch(leftNode), "Il nodo "
        		   + "sinistro della radice dovrebbe essere un branch valido.");
    }

    @Test
    void testValidateBranch2() {
        MerkleNode invalidNode = new MerkleNode("HashNonValido");
        
        assertFalse(this.merkleTree1.validateBranch(invalidNode), "Un nodo con "
        		    + "hash non valido non dovrebbe essere valido.");
    }

    @Test
    void testValidateBranch3() {
        HashLinkedList<Long> branchList = new HashLinkedList<Long>();
        branchList.addAtTail(111L);
        branchList.addAtTail(222L);
        branchList.addAtTail(333L);
        branchList.addAtTail(444L);
        
        MerkleTree<Long> merkleTreeBranch = new MerkleTree<Long>(branchList);
        MerkleNode branchRoot = merkleTreeBranch.getRoot();
        
        assertTrue(this.merkleTree2.validateBranch(branchRoot), "Il branch "
        		   + "dovrebbe essere valido.");
    }

    @Test
    void testValidateBranch4() {
        HashLinkedList<Long> branchList = new HashLinkedList<Long>();
        branchList.addAtTail(111L);
        branchList.addAtTail(222L);
        branchList.addAtTail(333L);
        branchList.addAtTail(555L);
        
        MerkleTree<Long> merkleTreeBranch = new MerkleTree<Long>(branchList);
        MerkleNode branchRoot = merkleTreeBranch.getRoot();
        
        assertFalse(this.merkleTree2.validateBranch(branchRoot), "Il branch "
        		    + "non dovrebbe essere valido.");
    }

    @Test
    void testValidateTree1() {
        HashLinkedList<String> identicalList = new HashLinkedList<String>();
        identicalList.addAtTail("Alice paga Bob");
        identicalList.addAtTail("Bob paga Charlie");
        identicalList.addAtTail("Charlie paga Diana");
        identicalList.addAtTail("Diana paga Alice");

        MerkleTree<String> identicalTree =
        		           new MerkleTree<String>(identicalList);
        
        assertTrue(this.merkleTree1.validateTree(identicalTree), "Gli alberi "
        		   + "identici dovrebbero essere validi.");
    }

    @Test
    void testValidateTree2() {
        HashLinkedList<String> differentList1 = new HashLinkedList<String>();
        differentList1.addAtTail("Dato diverso");
        
        MerkleTree<String> differentTree1 =
        		           new MerkleTree<String>(differentList1);
        assertFalse(this.merkleTree1.validateTree(differentTree1), "Gli alberi "
        		    + "diversi non dovrebbero essere validi.");

        HashLinkedList<String> differentList2 = new HashLinkedList<String>();
        differentList2.addAtTail("Alice paga Bob");
        differentList2.addAtTail("Bob paga Charlie");
        differentList2.addAtTail("Dato modificato");
        differentList2.addAtTail("Diana paga Alice");
        
        MerkleTree<String> differentTree2 =
        		           new MerkleTree<String>(differentList2);
        
        assertFalse(this.merkleTree1.validateTree(differentTree2), "Gli alberi "
        		    + "diversi non dovrebbero essere validi.");
    }

    @Test
    void testValidateTree3() {
        HashLinkedList<Long> identicalList = new HashLinkedList<Long>();
        for (int i = 1; i <= 13; i++) identicalList.addAtTail(111L * i);
        
        MerkleTree<Long> identicalTree = new MerkleTree<Long>(identicalList);
        
        assertTrue(this.merkleTree2.validateTree(identicalTree), "Gli alberi "
        		   + "identici dovrebbero essere validi.");
    }

    @Test
    void testValidateTree4(){
        HashLinkedList<Long> differentList = new HashLinkedList<Long>();
        for (int i = 1; i <= 13; i++) differentList.addAtTail(111L * i);
        
        differentList.addAtTail(0L);
        differentList.addAtTail(0L);
        
        MerkleTree<Long> differentTree = new MerkleTree<Long>(differentList);
        
        assertFalse(this.merkleTree2.validateTree(differentTree), "Gli alberi "
        		    + "diversi non dovrebbero essere validi.");
    }

    @Test
    void testFindInvalidDataIndices1() {
        HashLinkedList<String> modifiedList = new HashLinkedList<String>();
        modifiedList.addAtTail("Alice paga Bob");
        modifiedList.addAtTail("Bob paga Charlie");
        modifiedList.addAtTail("Dato modificato");
        modifiedList.addAtTail("Diana paga Alice");

        MerkleTree<String> modifiedTree = new MerkleTree<String>(modifiedList);
        Set<Integer> invalidIndices = this.merkleTree1
        		                          .findInvalidDataIndices(modifiedTree);

        assertEquals(1, invalidIndices.size(), "Ci dovrebbe essere 1 indice "
        		     + "non valido.");
        assertTrue(invalidIndices.contains(2), "L'indice 2 dovrebbe essere "
        		   + "non valido.");
    }

    @Test
    void testFindInvalidDataIndices2() {
        HashLinkedList<String> modifiedList = new HashLinkedList<String>();
        modifiedList.addAtTail("Alice paga Bob");
        modifiedList.addAtTail("Dato modificato 1");
        modifiedList.addAtTail("Dato modificato 2");
        modifiedList.addAtTail("Diana paga Alice");

        MerkleTree<String> modifiedTree = new MerkleTree<String>(modifiedList);
        Set<Integer> invalidIndices = this.merkleTree1
        		                          .findInvalidDataIndices(modifiedTree);

        assertEquals(2, invalidIndices.size(), "Ci dovrebbe essere 1 indice "
        		     + "non valido.");
        assertEquals(new HashSet<>(Arrays.asList(1,2)), invalidIndices,
        		     "Gli indici 1 e 2 dovrebbero essere non validi.");
    }

    @Test
    void testFindInvalidDataIndices3() {
        HashLinkedList<Long> modifiedList = new HashLinkedList<Long>();
        
        for (int i = 1; i <= 5; i++) modifiedList.addAtTail(111L * i);
        
        modifiedList.addAtTail(0L);
        modifiedList.addAtTail(777L);
        modifiedList.addAtTail(0L);
        modifiedList.addAtTail(999L);
        modifiedList.addAtTail(0L);
        
        for (int i = 11; i <= 13; i++) modifiedList.addAtTail(111L * i);
        
        MerkleTree<Long> modifiedTree = new MerkleTree<Long>(modifiedList);
        
        Set<Integer> invalidIndices = this.merkleTree2
        		                          .findInvalidDataIndices(modifiedTree);

        assertEquals(3, invalidIndices.size(), "Ci dovrebbero essere 3 indici "
        		     + "non validi.");
        assertEquals(new HashSet<>(Arrays.asList(5, 7, 9)), invalidIndices,
                     "Gli indici 5, 7 e 9 dovrebbero essere non validi.");
    }

    @Test
    void testFindInvalidDataIndices4() {
        HashLinkedList<Boolean> modifiedList = new HashLinkedList<Boolean>();
        modifiedList.addAtTail(false);

        MerkleTree<Boolean> modifiedTree =
        		            new MerkleTree<Boolean>(modifiedList);
        Set<Integer> invalidIndices = this.merkleTree3
                                          .findInvalidDataIndices(modifiedTree);
        
        assertEquals(1, invalidIndices.size(), "Ci dovrebbe essere 1 indice"
        		     + "non valido.");
    }

    @Test
    void testGetMerkleProofData() {
        MerkleProof proof = this.merkleTree1.getMerkleProof("Alice paga Bob");
        
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        
        assertEquals(2, proof.getLength(), "La prova di Merkle dovrebbe avere"
        		     + "dimensione 2.");
    }

    @Test
    void testGetMerkleProofData2() {
        MerkleProof proof = this.merkleTree2.getMerkleProof(555L);
        
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        
        assertEquals(4, proof.getLength(), "La prova di Merkle dovrebbe avere"
        		     + "dimensione 4.");
    }

    @Test
    void testGetMerkleProofData3() {
        MerkleProof proof = this.merkleTree2.getMerkleProof(1443L);
        
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        
        assertEquals(4, proof.getLength(), "La prova di Merkle dovrebbe avere "
        		     + "dimensione 4.");
    }

    @Test
    void testGetMerkleProofData4() {
        MerkleProof proof = this.merkleTree3.getMerkleProof(true);
        
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        
        assertEquals(0, proof.getLength(), "La prova di Merkle dovrebbe avere "
        		     + "dimensione 0.");
    }

    @Test
    void testGetMerkleProofDataNotPresent() {
        assertThrows(IllegalArgumentException.class, 
        		     () -> this.merkleTree1.getMerkleProof("Dato non presente"),
                     "Dovrebbe lanciare IllegalArgumentException per un dato "
                     + "non presente.");
    }

    @Test
    void testVerifyProofData() {
        MerkleProof proof = this.merkleTree1.getMerkleProof("Alice paga Bob");
        
        assertTrue(proof.proveValidityOfData("Alice paga Bob"), "La prova di "
        		   + "Merkle dovrebbe essere valida.");

        assertFalse(proof.proveValidityOfData("Dati non presenti"), "Una prova "
        		    + "di Merkle per un dato non presente non dovrebbe essere "
        		    + "valida.");
    }

    @Test
    void testVerifyProofData2() {
        MerkleProof proof = this.merkleTree2.getMerkleProof(555L);
        
        assertTrue(proof.proveValidityOfData(555L), "La prova di Merkle "
        		   + "dovrebbe essere valida.");
        
        assertFalse(proof.proveValidityOfData(556L), "Una prova di Merkle per "
        		    + "un dato non presente non dovrebbe essere valida.");
    }

    @Test
    void testVerifyProofData3(){
        MerkleProof proof = this.merkleTree3.getMerkleProof(true);
        
        assertTrue(proof.proveValidityOfData(true), "La prova di Merkle "
        		   + "dovrebbe essere valida.");

        assertFalse(proof.proveValidityOfData(false), "Una prova di Merkle per "
        		    + "un dato non presente non dovrebbe essere valida.");
    }

    @Test
    void testVerifyProofData4(){
        try {
            MerkleProof proof =  this.merkleTree1
            		                 .getMerkleProof("Alice paga Bob");
            Class<?> clazz = proof.getClass();
            Field privateField = clazz.getDeclaredField("proof");
            privateField.setAccessible(true);

            HashLinkedList<MerkleProof.MerkleProofHash> list =
            		(HashLinkedList<MerkleProof.MerkleProofHash>)
            		privateField.get(proof);

            Iterator<MerkleProof.MerkleProofHash> itr = list.iterator();
            
            assertTrue(itr.hasNext());
            
            MerkleProof.MerkleProofHash hash = itr.next();
            
            assertEquals(HashUtil.dataToHash("Bob paga Charlie"),
            		     hash.getHash(), "L'hash dovrebbe essere uguale a "
            		     + "quello di 'Bob paga Charlie'");
            
            assertFalse(hash.isLeft(), "L'hash dovrebbe essere concatenato a "
            		    + "destra");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testVerifyProofData5(){
        try {
            MerkleProof proof =  this.merkleTree2.getMerkleProof(1443L);
            Class<?> clazz = proof.getClass();
            Field privateField = clazz.getDeclaredField("proof");
            privateField.setAccessible(true);

            HashLinkedList<MerkleProof.MerkleProofHash> list =
            		(HashLinkedList<MerkleProof.MerkleProofHash>)
            		privateField.get(proof);

            Iterator<MerkleProof.MerkleProofHash> itr = list.iterator();
            
            assertTrue(itr.hasNext() && itr.next().getHash().equals(""));
            assertTrue(itr.hasNext() && itr.next().getHash().equals(""));
            assertTrue(itr.hasNext() && !itr.next().getHash().equals(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetMerkleProofBranch1() {
        MerkleProof proof = this.merkleTree1
        		                .getMerkleProof(this.merkleTree1
        		                		            .getRoot()
        		                                    .getLeft());
        
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        assertEquals(1, proof.getLength(), "La prova di Merkle dovrebbe avere "
        		     + "dimensione 1.");
    }

    @Test
    void testGetMerkleProofBranch2() {
        MerkleProof proof = this.merkleTree2
        		                .getMerkleProof(this.merkleTree2
        		                		            .getRoot()
        		                                    .getRight()
        		                                    .getRight());
        
        assertNotNull(proof, "La prova di Merkle non dovrebbe essere null.");
        assertEquals(2, proof.getLength(), "La prova di Merkle dovrebbe avere "
        		     + "dimensione 2.");
    }

    @Test
    void testVerifyProofBranch() {
        HashLinkedList<String> branchList = new HashLinkedList<String>();
        branchList.addAtTail("Alice paga Bob");
        branchList.addAtTail("Bob paga Charlie");
        
        MerkleTree<String> merkleTreeBranch =
        		           new MerkleTree<String>(branchList);
        MerkleProof proof = this.merkleTree1.getMerkleProof(this.merkleTree1
        		                                                .getRoot()
        		                                                .getLeft());

        assertTrue(proof.proveValidityOfBranch(merkleTreeBranch.getRoot()),
                   "La prova di Merkle per un branch dovrebbe essere valida.");
    }

    @Test
    void testVerifyProofBranchInvalid() {
        HashLinkedList<String> branchList = new HashLinkedList<String>();
        branchList.addAtTail("Alice paga Bob");
        branchList.addAtTail("Dato non presente");
        
        MerkleTree<String> merkleTreeBranch =
        		           new MerkleTree<String>(branchList);
        MerkleProof proof = this.merkleTree1.getMerkleProof(this.merkleTree1
        		                                                .getRoot()
        		                                                .getLeft());

        assertFalse(proof.proveValidityOfBranch(merkleTreeBranch.getRoot()),
                    "La prova di Merkle per il branch non dovrebbe essere "
                    + "valida.");
    }
    
    @Test
    void testSingleLeafTree() {
        HashLinkedList<String> singleList = new HashLinkedList<String>();
        singleList.addAtTail("Alice paga Bob");
        MerkleTree<String> singleTree = new MerkleTree<String>(singleList);

        assertEquals(1, singleTree.getWidth(), "La larghezza dovrebbe "
        		     + "essere 1.");
        assertEquals(0, singleTree.getHeight(), "L'altezza dovrebbe essere 0.");
        assertTrue(singleTree.validateData("Alice paga Bob"), "Il dato "
        		   + "dovrebbe essere valido.");
    }
}