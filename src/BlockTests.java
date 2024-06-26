import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertNull;
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
    assertEquals(1, b.getNum());
    assertEquals(123, b.getAmount());
    assertNull(b.getPrevHash());
  }

  /**
   * Creates the same block using both constructors several times.
   */
  @Test
  public void makeIdenticalBlocksTest() throws Exception {
    int blockNum = rand.nextInt(RAND_MAX);
    int amount = rand.nextInt(RAND_MAX) - RAND_MAX / 2;
    Block b0 = new Block(0, 0, null);
    for (int i = 0; i < BLOCKS; i++) {
      Block b1 = new Block(blockNum, amount, b0.getHash());
      Block b2 = new Block(blockNum, amount, b0.getHash(), b1.getNonce());

      assertEquals(b1.getNum(), b2.getNum());
      assertEquals(b1.getAmount(), b2.getAmount());
      assertEquals(b1.getPrevHash(), b2.getPrevHash());
      assertEquals(b1.getNonce(), b2.getNonce());
      assertTrue(b2.getHash().isValid());
    }
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
