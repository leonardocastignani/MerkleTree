package it.unicam.cs.asdl2425.mp1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A class provided with utilities for calculating MD5 hashes.
 * 
 * @author Leonardo Castignani @UNICAM
 */
public class HashUtil {

    /**
     * Calculate the hash of the provided data using MD5.
     *
     * @param data  the data to be hashed.
     * @return the hash as a hexadecimal string.
     */
    public static String dataToHash(Object data) {
        return HashUtil.computeMD5(intToBytes(data.hashCode()));
    }

    /**
     * Computes the hash of a supplied byte array using MD5.
     *
     * @param input  the byte array to hash.
     * @return the hash as a hexadecimal string.
     * @throws RuntimeException if the hashing algorithm is not available.
     */
    public static String computeMD5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input);
            StringBuilder hashString = new StringBuilder();
            
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * Converts an integer (int) value to a byte array.
     *
     * This method splits the integer into four bytes, representing it in
     * big-endian format, that is, from the most significant byte (MSB) to the
     * least significant byte (LSB). This is useful for transforming a numeric
     * value into a format compatible with hashing algorithms or communication
     * protocols that require byte representation.
     *
     * @param value  the integer value to convert.
     * @return a byte array representing the integer value.
     */
    public static byte[] intToBytes(int value) {
        return new byte[] { (byte) (value >> 24), (byte) (value >> 16),
        		   (byte) (value >> 8), (byte) value };
    }
}