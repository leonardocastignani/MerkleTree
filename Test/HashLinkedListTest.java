package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link HashLinkedList} class. This class includes tests to
 * verify the behavior of the MD5-hashed linked list.
 * Below is a list of the included tests with a brief description:
 * 
 * <ul>
 * <li>{@link #testIsEmpty()}: Verify that a newly created list is empty.</li>
 * 
 * <li>{@link #testAddAtHead()}: Adds items at the top and checks that they are
 *     inserted correctly in reverse order.</li>
 * 
 * <li>{@link #testAddAtTail()}: Adds items to the queue and verifies that they
 *     are placed in the correct order.</li>
 * 
 * <li>{@link #testBuildNodesString1()}: Adds items to the head and checks that
 *     the text representation of the list is correct.</li>
 * 
 * <li>{@link #testBuildNodesString2()}: Adds items to the end and verifies that
 *     the text representation of the list is correct.</li>
 * 
 * <li>{@link #testBuildNodesString3()}: Adds items to the head and tail, then
 *     verifies the text representation of the list.</li>
 * 
 * <li>{@link #testGetAllHashes()}: Verify that the {@code getAllHashes} method
 *     returns the correct hashes of the list elements.</li>
 * 
 * <li>{@link #testRemoveHeadElement()}: Removes the leading element and
 *     verifies that the list is updated correctly.</li>
 * 
 * <li>{@link #testRemoveTailElement()}: Removes the item from the queue and
 *     verifies that the list is updated correctly.</li>
 * 
 * <li>{@link #testRemoveMiddleElement()}: Removes an element from the middle of
 *     the list and checks that the pointers are updated correctly.</li>
 * 
 * <li>{@link #testRemoveMultipleElements()}: Remove multiple items and verify
 *     that the list is updated correctly.</li>
 * 
 * <li>{@link #testRemoveAndAddElements()}: Removes one element and adds
 *     another, then checks the text representation of the list.</li>
 * 
 * <li>{@link #testRemoveNonExistentElement()}: Attempts to remove a non-
 *     existent element and verifies that the method returns {@code false}.</li>
 * 
 * <li>{@link #testIteratorHasNext1()}: Checks that the iterator has a next
 *     element when the list contains multiple elements.</li>
 * 
 * <li>{@link #testIteratorHasNext2()}: Checks that the iterator has no next
 *     element when it has been fully iterated.</li>
 * 
 * <li>{@link #testIterator()}: Verify that the iterator correctly traverses all
 *     elements of the list.</li>
 * 
 * <li>{@link #testFailFastIterator1()}: Check that the iterator is
 *     {@code fail-fast} by adding an element during iteration.</li>
 * 
 * <li>{@link #testFailFastIterator2()}: Verify that the iterator is
 *     {@code fail-fast} by removing an element during iteration.</li>
 * </ul>
 */
class HashLinkedListTest {

    private HashLinkedList<String> list;

    @BeforeEach
    void setUp() {
        this.list = new HashLinkedList<>();
    }

    @Test
    void testIsEmpty() {
        assertEquals(0, this.list.getSize(),
                     "La lista non dovrebbe contenere elementi inizialmente.");
    }

    @Test
    void testAddAtHead() {
    	this.list.addAtHead("Alice paga Bob");
        assertEquals(1, this.list.getSize(),
                     "La lista dovrebbe contenere un solo elemento.");

        this.list.addAtHead("Bob paga Charlie");
        assertEquals(2, this.list.getSize(),
                     "La lista dovrebbe contenere due elementi.");
    }

    @Test
    void testAddAtTail() {
    	this.list.addAtTail("Alice paga Bob");
        assertEquals(1, this.list.getSize(),
                     "La lista dovrebbe contenere un solo elemento.");

        this.list.addAtTail("Bob paga Charlie");
        assertEquals(2, this.list.getSize(),
                     "La lista dovrebbe contenere due elementi.");
    }
    
    @Test
    void testBuildNodesString1() {
    	this.list.addAtHead("Alice paga Bob");
    	this.list.addAtHead("Bob paga Charlie");

        String expected = "Dato: Bob paga Charlie, Hash: "
                          + HashUtil.dataToHash("Bob paga Charlie") + "\n"
                          + "Dato: Alice paga Bob, Hash: "
                          + HashUtil.dataToHash("Alice paga Bob") + "\n";

        assertEquals(expected, this.list.buildNodesString(),
                     "La rappresentazione della lista non è corretta.");
    }

    @Test
    void testBuildNodesString2() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");

        String expected = "Dato: Alice paga Bob, Hash: "
                          + HashUtil.dataToHash("Alice paga Bob") + "\n"
                          + "Dato: Bob paga Charlie, Hash: "
                          + HashUtil.dataToHash("Bob paga Charlie") + "\n";

        assertEquals(expected, this.list.buildNodesString(),
                     "La rappresentazione della lista non è corretta.");
    }

    @Test
    void testBuildNodesString3() {
    	this.list.addAtHead("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");

        String expected = "Dato: Alice paga Bob, Hash: "
                          + HashUtil.dataToHash("Alice paga Bob") + "\n"
                          + "Dato: Bob paga Charlie, Hash: "
                          + HashUtil.dataToHash("Bob paga Charlie") + "\n";

        assertEquals(expected, this.list.buildNodesString(),
                     "La rappresentazione della lista non è corretta.");
    }

    @Test
    void testGetAllHashes() {
    	this.list.addAtHead("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");

        ArrayList<String> expectedHashes = new ArrayList<String>();
        expectedHashes.add(HashUtil.dataToHash("Alice paga Bob"));
        expectedHashes.add(HashUtil.dataToHash("Bob paga Charlie"));

        assertEquals(expectedHashes, this.list.getAllHashes(),
                     "Gli hash della lista non corrispondono all'atteso.");
    }

    @Test
    void testRemoveHeadElement() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");

        assertTrue(this.list.remove("Alice paga Bob"),
                   "L'elemento 'Alice paga Bob' doveva essere rimosso.");
        assertFalse(this.list.remove("Alice paga Bob"),
                    "L'elemento 'Alice paga Bob' non doveva più esistere.");
    }

    @Test
    void testRemoveTailElement() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");

        assertTrue(this.list.remove("Bob paga Charlie"),
                   "L'elemento 'Bob paga Charlie' doveva essere rimosso.");
        assertFalse(this.list.remove("Bob paga Charlie"),
                    "L'elemento 'Bob paga Charlie' non doveva più esistere.");
    }

    @Test
    void testRemoveMiddleElement() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");
    	this.list.addAtTail("Charlie paga Diana");

        assertTrue(this.list.remove("Bob paga Charlie"),
                   "L'elemento 'Bob paga Charlie' doveva essere rimosso.");
        String expected = "Dato: Alice paga Bob, Hash: "
                          + HashUtil.dataToHash("Alice paga Bob") + "\n"
                          + "Dato: Charlie paga Diana, Hash: "
                          + HashUtil.dataToHash("Charlie paga Diana") + "\n";
        assertEquals(expected, this.list.buildNodesString(),
                     "La lista non è corretta dopo la rimozione.");
    }

    @Test
    void testRemoveMultipleElements() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");
    	this.list.addAtTail("Charlie paga Diana");
    	this.list.addAtTail("Diana paga Alice");

        assertTrue(this.list.remove("Bob paga Charlie"),
                   "L'elemento 'Bob paga Charlie' doveva essere rimosso.");
        assertTrue(this.list.remove("Diana paga Alice"),
                   "L'elemento 'Diana paga Alice' doveva essere rimosso.");

        String expected = "Dato: Alice paga Bob, Hash: "
                          + HashUtil.dataToHash("Alice paga Bob") + "\n"
                          + "Dato: Charlie paga Diana, Hash: "
                          + HashUtil.dataToHash("Charlie paga Diana") + "\n";

        assertEquals(expected, this.list.buildNodesString(),
        		     "La lista non è corretta dopo le rimozioni.");
    }

    @Test
    void testRemoveAndAddElements() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");
    	this.list.addAtTail("Charlie paga Diana");
    	this.list.addAtTail("Diana paga Alice");

        assertTrue(this.list.remove("Bob paga Charlie"),
                   "L'elemento 'Bob paga Charlie' doveva essere rimosso.");
        this.list.addAtTail("Charlie paga Diana");

        String expected = "Dato: Alice paga Bob, Hash: "
                          + HashUtil.dataToHash("Alice paga Bob") + "\n"
                          + "Dato: Charlie paga Diana, Hash: "
                          + HashUtil.dataToHash("Charlie paga Diana") + "\n"
                          + "Dato: Diana paga Alice, Hash: "
                          + HashUtil.dataToHash("Diana paga Alice") + "\n"
                          + "Dato: Charlie paga Diana, Hash: "
                          + HashUtil.dataToHash("Charlie paga Diana") + "\n";

        assertEquals(expected, this.list.buildNodesString(),
        		     "La lista non è corretta dopo le rimozioni e l'aggiunta.");
    }

    @Test
    void testRemoveNonExistentElement() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");

        assertFalse(this.list.remove("Charlie paga Diana"),
                    "Non dovrebbe essere possibile rimuovere un elemento "
                    + "inesistente.");
    }

    @Test
    void testIteratorHasNext1() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");

        assertTrue(this.list.iterator().hasNext(),
        		   "L'iteratore dovrebbe avere un prossimo elemento.");
    }

    @Test
    void testIteratorHasNext2() {
    	this.list.addAtTail("Alice paga Bob");

        Iterator<String> iterator = this.list.iterator();
        assertDoesNotThrow(iterator::next,
        		           "L'iteratore dovrebbe avere un prossimo elemento.");
        assertFalse(iterator.hasNext(),
        		    "L'iteratore non dovrebbe avere un prossimo elemento.");
    }

    @Test
    void testIterator() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");
    	this.list.addAtTail("Charlie paga Diana");
    	this.list.addAtTail("Diana paga Alice");

        List<String> iteratorList = new ArrayList<String>();
        for (String s : this.list) {
            iteratorList.add(s);
        }

        List<String> expectedList = Arrays.asList(
                "Alice paga Bob",
                "Bob paga Charlie",
                "Charlie paga Diana",
                "Diana paga Alice"
        );

        assertEquals(expectedList, iteratorList,
        		     "La lista generata dall'iteratore non è corretta.");
    }

    @Test
    void testFailFastIterator1() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");
    	this.list.addAtTail("Charlie paga Diana");
    	this.list.addAtTail("Diana paga Alice");

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (String s : this.list) {
                    	this.list.addAtTail("Alice paga Bob");
                    }
                },
                "L'iteratore non è fail-fast."
        );
    }

    @Test
    void testFailFastIterator2() {
    	this.list.addAtTail("Alice paga Bob");
    	this.list.addAtTail("Bob paga Charlie");
    	this.list.addAtTail("Charlie paga Diana");
    	this.list.addAtTail("Diana paga Alice");

        assertThrows(
                ConcurrentModificationException.class,
                () -> {
                    for (String s : this.list) {
                    	this.list.remove("Bob paga Charlie");
                    }
                },
                "L'iteratore non è fail-fast."
        );
    }
}