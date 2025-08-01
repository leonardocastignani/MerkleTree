package it.unicam.cs.asdl2425.mp1;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * A class representing a linked list with MD5 hashes computed for each element.
 * Each node in the list contains the original data of generic type T and its
 * hash computed using the MD5 algorithm.
 *
 * <p>
 * The class supports the following main operations:
 * <ul>
 * <li>Add an item to the head of the list</li>
 * <li>Add an item to the tail of the list</li>
 * <li>Remove an item from the list based on data</li>
 * <li>Retrieve a sorted list of all hashes contained in the list</li>
 * <li>Building a textual representation of the list</li>
 * </ul>
 *
 * <p>
 * This implementation includes optimizations such as maintaining a reference to
 * the last node in the list (tail), which makes inserting into the tail an O(1)
 * operation.
 *
 * <p>
 * The class uses the HashUtil class to calculate the MD5 hash of the data.
 *
 * @param <T> the generic type of data contained in the nodes of the list.
 * 
 * @author Leonardo Castignani @UNICAM
 * 
 */
public class HashLinkedList<T> implements Iterable<T> {
	
    private Node head;
    private Node tail;
    private int size;
    private int numberChanges;

    public HashLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.numberChanges = 0;
    }

    /**
     * Returns the current number of nodes in the list.
     *
     * @return the number of nodes in the list.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Represents a node in the linked list.
     */
    private class Node {
        String hash;

        T data;

        Node next;

        Node(T data) {
            this.data = data;
            this.hash = HashUtil.dataToHash(data);
            this.next = null;
        }
    }

    /**
     * Adds a new item to the head of the list.
     *
     * @param data  the data to be added.
     */
    public void addAtHead(T data) {
    	if (data == null) throw new NullPointerException();

    	Node newNode = new Node(data);

    	newNode.next = this.head;
        this.head = newNode;
        if (this.tail == null) this.tail = newNode;

        this.size++;
        this.numberChanges++;
    }

    /**
     * Adds a new item to the tail of the list.
     *
     * @param data  the data to be added.
     */
    public void addAtTail(T data) {
    	if (data == null) throw new NullPointerException();

    	Node newNode = new Node(data);

        if (this.tail != null) this.tail.next = newNode;
        this.tail = newNode;
        if (this.head == null) this.head = newNode;

        this.size++;
        this.numberChanges++;
    }

    /**
     * Returns an ArrayList containing all the hashes in the list in sorted
     * order.
     *
     * @return a list with all the hashes of the list.
     */
    public ArrayList<String> getAllHashes() {
    	ArrayList<String> hashList = new ArrayList<String>();

        Iterator<T> iterator = this.iterator();
        
        Node current = this.head;

        while (iterator.hasNext()) {
            T data = iterator.next();

            while (current != null) {
                if (current.data.equals(data)) {
                	hashList.add(current.hash);
                    break;
                }
                
                current = current.next;
            }
        }

        return hashList;
    }

    /**
     * Builds a string containing all the nodes in the list, including data and
     * hashes. The string should be formatted as in the following example:
     * 
     * <pre>
     *     Dato: StringaDato1, Hash: 5d41402abc4b2a76b9719d911017c592
     *     Dato: StringaDato2, Hash: 7b8b965ad4bca0e41ab51de7b31363a1
     *     ...
     *     Dato: StringaDatoN, Hash: 2c6ee3d301aaf375b8f026980e7c7e1c
     * </pre>
     *
     * @return a text representation of all the nodes in the list.
     */
    public String buildNodesString() {
    	StringBuilder sb = new StringBuilder();

        Iterator<T> iterator = this.iterator();

        Node current = this.head;

        while (iterator.hasNext()) {
            T data = iterator.next();

            while (current != null) {
                if (current.data.equals(data)) {
                    sb.append("Dato: ").append(current.data)
                      .append(", Hash: ").append(current.hash)
                      .append("\n");
                    break;
                }

                current = current.next;
            }
        }

        return sb.toString();
    }

    /**
     * Removes the first element in the list that contains the specified data.
     *
     * @param data  the data to be removed.
     * @return true if the item was found and removed, false otherwise.
     */
	public boolean remove(T data) {
		if (data == null) throw new NullPointerException();

	    Iterator<T> iterator = this.iterator();

	    Node current = this.head;
	    Node precedent = null;

	    while (iterator.hasNext()) {
	        T dataCorrente = iterator.next();

	        if (dataCorrente.equals(data)) {
	            while (current != null) {
	                if (current.data.equals(data)) {
	                    if (precedent == null) this.head = current.next;
	                    else precedent.next = current.next;	                    

	                    if (current == this.tail) this.tail = precedent;

	                    this.size--;
	                    this.numberChanges++;

	                    return true;
	                }
	                
	                precedent = current;
	                current = current.next;
	            }
	        }
	    }

	    return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    /**
     * A class that implements a fail-fast iterator for HashLinkedList.
     */
    private class Itr implements Iterator<T> {
    	private Node current;
        private final int numExpectedChanges;
        
        private Itr() {
        	this.current = head;
            this.numExpectedChanges = numberChanges;
        }

        @Override
        public boolean hasNext() {
        	if(this.numExpectedChanges != numberChanges)
        		throw new ConcurrentModificationException();

            return this.current != null;
        }

        @Override
        public T next() {
        	if(!hasNext()) throw new NoSuchElementException();

            T data = this.current.data;
            this.current = this.current.next;
            return data;
        }
    }
}