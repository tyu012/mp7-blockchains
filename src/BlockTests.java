import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * A file to test methods in the Block class.
 * 
 * @author Keely Miyamoto
 * @author Nye Tenerelli
 * @author Tim Yu
 */

public class BlockTests {
  /**
   * The number of blocks to mine in tests which mine arbitrary numbers of blocks.
   */
  static int BLOCKS = 20;

  static int RAND_MAX = 1000;

  Random rand = new Random();

  /**
   * Mines a single block with no previous hash.
   */
  @Test
  public void makeOneBlockTest() throws Exception {
    Block b = new Block(1, 123, null);
    assertTrue(b.getHash().isValid());
  }

  /**
   * Mines two blocks, the second referring to the first block's hash.
   */
  @Test
  public void makeTwoBlocksTest() throws Exception {
    Block b1 = new Block(1, 456, null);
    Block b2 = new Block(2, 789, b1.getHash());
    assertTrue(b1.getHash().isValid());
    assertTrue(b2.getHash().isValid());
    assertEquals(b2.getPrevHash(), b1.getHash());
  }

  /**
   * Mines many blocks, each (except the first) referring to the previous block's hash.
   */
  @Test
  public void makeManyBlocksTest() throws Exception {
    Block[] blocks = new Block[BLOCKS];

    for (int i = 0; i < BLOCKS; i++) {
      blocks[i] = new Block(1,
          rand.nextInt(RAND_MAX) - RAND_MAX / 2,
          i == 0 ? null : blocks[i - 1].getHash());
      assertTrue(blocks[i].getHash().isValid());
      if (i > 0) {
        assertEquals(blocks[i].getPrevHash(), blocks[i - 1].getHash());
      }
    }
  }

} // class HashTests
