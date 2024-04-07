import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * An individual node of a blockchain. 
 * 
 * @author Tim Yu
 * @author Nye Tenerelli
 * @author Keely Miyamoto
 */
public class Block {
  // +--------+
  // | Fields |
  // +--------+-------------------------------------------------------------------------------------

  /**
   * The number of this block.
   */
  private int num;

  /**
   * The data of the block, which is the integer amount transferred between the two parties.
   */
  private int data;

  /**
   * The hash of the previous block of the blockchain.
   */
  private Hash prevHash;

  /**
   * The nonce of this block.
   */
  private long nonce;

  /**
   * The hash of this block.
   */
  private Hash hash;

  // +--------------+
  // | Constructors |
  // +--------------+-------------------------------------------------------------------------------

  /**
   * Creates a new block from the specified parameters, performing the mining operation to discover
   * the nonce and hash for this block given these parameters.
   */
  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    // Convert provided data into byte array
    byte[] numBytes = ByteBuffer.allocate(Integer.BYTES).putInt(num).array();
    byte[] dataBytes = ByteBuffer.allocate(Integer.BYTES).putInt(num).array();
    byte[] prevHashBytes = prevHash.getData();

    // Create instance of MessageDigest
    MessageDigest md = MessageDigest.getInstance("sha-256");

    // Mine for the block by looping through all possible long values
    for (long nonceCandidate = Long.MIN_VALUE; nonceCandidate <= Long.MAX_VALUE; nonceCandidate++) {
      // Convert nonce candidate into byte array
      byte[] nonceCandidateBytes = ByteBuffer.allocate(Long.BYTES).putInt(num).array();

      md.update(numBytes);
      md.update(dataBytes);
      md.update(prevHashBytes);
      md.update(nonceCandidateBytes);

      byte[] hashBytes = md.digest();

      Hash hashCandidate = new Hash(hashBytes);

      if (hashCandidate.isValid()) {
        this.num = num;
        this.data = amount;
        this.prevHash = prevHash;
        this.nonce = nonceCandidate;
        this.hash = hashCandidate;
      }
    }
  }

  /**
   * Creates a new block from the specified parameters, using the provided nonce and additional
   * parameters to generate the hash for the block. Because the nonce is provided, this constructor
   * does not need to perform the mining operation; it can compute the hash directly.
   */
  public Block(int num, int amount, Hash prevHash, long nonce) {
    // STUB
  }

  // +----------------+
  // | Public methods |
  // +----------------+-----------------------------------------------------------------------------

  /**
   * Returns the number of this block.
   */
  public int getNum() {
    return this.num;
  }

  /**
   * Returns the amount transferred that is recorded in this block.
   */
  public int getAmount() {
    return this.data;
  }

  /**
   * Returns the nonce of this block.
   */
  public long getNonce() {
    return this.nonce;
  }

  /**
   * Returns the hash of the previous block in the blockchain.
   */
  public Hash getPrevHash() {
    return this.prevHash;
  }

  /**
   * Returns the hash of this block.
   */
  public Hash getHash() {
    return this.hash;
  }

  /**
   * Returns a string representation of this block.
   */
  public String toString() {
    return "Block " + getNum() + " (Amount: " + getAmount() + ", Nonce: " + getNonce()
        + ", prevHash: " + getPrevHash() + ", hash: " + getHash() + ")";
  }
}
