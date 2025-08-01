package it.unicam.cs.asdl2425.mp1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link HashUtil} class. This class tests the behavior of
 * static methods for calculating MD5 hashes.
 * Below is a list of the included tests with a brief description:
 * 
 * <ul>
 * <li>{@link #testComputeMD5_validInput()}: Verify that the {@code computeMD5}
 *     method correctly computes the MD5 hash for a valid byte array.</li>
 * 
 * <li>{@link #testComputeMD5_emptyInput()}: Verify that the {@code computeMD5}
 *     method returns the correct MD5 hash for an empty byte array.</li>
 * 
 * <li>{@link #testComputeMD5_nullInput()}: Ensure that the {@code computeMD5}
 *     method throws a {@code NullPointerException} when the input is
 *     {@code null}.</li>
 * </ul>
 */
class HashUtilTest {

    @Test
    void testComputeMD5_validInput() {
        byte[] input = "Hello, World!".getBytes();
        String expectedHash = "65a8e27d8879283831b664bd8b7f0ad4";

        String actualHash = HashUtil.computeMD5(input);

        assertNotNull(actualHash, "L'hash calcolato non dovrebbe essere null.");
        assertEquals(expectedHash, actualHash,
        		     "L'hash calcolato non corrisponde all'atteso.");
    }

    @Test
    void testComputeMD5_emptyInput() {
        byte[] input = new byte[0];
        String expectedHash = "d41d8cd98f00b204e9800998ecf8427e";

        String actualHash = HashUtil.computeMD5(input);

        assertNotNull(actualHash, "L'hash calcolato non dovrebbe essere null.");
        assertEquals(expectedHash, actualHash,
        		     "L'hash calcolato per l'input vuoto non corrisponde "
        		     + "all'atteso.");
    }

    @Test
    void testComputeMD5_nullInput() {
        assertThrows(NullPointerException.class,
        		     () -> HashUtil.computeMD5(null),
                     "Dovrebbe lanciare NullPointerException se l'input è "
                     + "null.");
    }
}