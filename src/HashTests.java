import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

/**
 * A file to test methods in the Hash class.
 * 
 * @author Keely Miyamoto
 * @author Nye Tenerelli
 * @author Tim Yu
 */

public class HashTests {

  byte[] data1 = {0, 0, 0, 1, 2, 3};
  byte[] data2 = {0, 0, 1, 1, 2, 3};
  byte[] data3 = {0, 1, 0, 1, 2, 3};
  byte[] data4 = {1, 0, 0, 1, 2, 3};
  byte[] data5 = {0, 0};

  @Test
  public void isValidTest() {
    assertTrue((new Hash(data1)).isValid());
    assertFalse((new Hash(data2)).isValid());
    assertFalse((new Hash(data3)).isValid());
    assertFalse((new Hash(data4)).isValid());
    assertFalse((new Hash(data5)).isValid());
  } // isValidTest()

  @Test
  public void equalsTest() {
    Hash testHash1 = new Hash(data1);
    Hash testHashCopy = new Hash(data1);
    Hash testHash2 = new Hash(data2);

    assertTrue(testHash1.equals(testHashCopy));
    assertFalse(testHash2.equals(testHash1));
  } // isValidTest()


} // class HashTests
