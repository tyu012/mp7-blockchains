import java.security.NoSuchAlgorithmException;
import java.io.PrintWriter;

/**
 * INSERT MESSAGE HERE
 * 
 * @author Tim Yu
 * @author Nye Tenerelli
 * @author Keely Miyamoto
 */
public class BlockChain {

  // +----------+
  // | Fields  |
  // +---------+----------------------------------------------------------------------------------
  
  // A nested static class for BlockChain Nodes.
  static class Node {
    Block data;
    Node next;

    // Constructor
    public Node(Block blk) {
      this.data = blk;
      this.next = null;
    } // Node(Block)
  } // Node

  // BlockChain Fields
  Node first;
  Node last;

  // +--------------+
  // | Constructor |
  // +------------+-------------------------------------------------------------------------------
 
  /**
   * Creates a BlockChain that possess a single block the starts with the given initial amount.
   * Note that to create this block, the prevHash field should be ignored when calculating 
   * the block’s own nonce and hash.
   * @param initial
   * @throws NoSuchAlgorithmException 
   */
  public BlockChain(int initial) throws NoSuchAlgorithmException {
    first = new Node (new Block(0, initial, null));
    last = first;
  } // BlockChain(int)

  // +----------+
  // | Methods |
  // +---------+----------------------------------------------------------------------------------
  /**
   * Mines a new candidate block to be added to the end of the chain. 
   * The returned Block should be valid to add onto this chain.
   * @param amount
   * @return blk, a new Block
   * @throws NoSuchAlgorithmException
   */
  public Block mine(int amount) throws NoSuchAlgorithmException  {
    Block blk = new Block(this.getSize(), amount, this.last.data.getHash());
    return blk;
  } // mine(int)


  /**
   * Returns the size of the BlockChain. Note that number of the blocks 
   * provides a convenient method for quickly determining the size of the chain.
   * @return int, size of the BlockChain
   */
  public int getSize() {
    return this.last.data.getNum() + 1;
  } // getSize()


  /** 
   * Adds this block to the list, throwing an IllegalArgumentException if this block 
   * cannot be added to the chain (because it has an invalid hash, because its 
   * hash is inappropriate for the contents, or because the previous hash is incorrect).
   * @param blk
   */
  public void append(Block blk)  {

    Node mover = arr;

    while (mover.next != null) {
      mover = mover.next;
    } // while

    if (blk.getHash().isValid() == false) {
      throw new IllegalArgumentException(); 
    } else if (!(blk.getPrevHash().equals(getHash()))) {
      throw new IllegalArgumentException(); 
    } // if

    size++;
    mover.next = new Node(blk, null);
  } // append(Block)


  /**
   * Removes the last block from the chain, returning true. If the chain 
   * only contains a single block, then removeLast does nothing and returns false.
   * @return boolean
   */
  public boolean removeLast() {
    if (this.getSize() == 0) {
      return false;
    } // if

    Node mover = arr;
    while (mover.next.next != null) {
      mover = mover.next;
    } // while
    mover.next = null;
    return true;
  } // removeLast()


  /**
   * Returns the hash of the last block in the chain.
   * @return Hash
   */
  public Hash getHash()  {
    Node mover = arr;

    while (mover.next != null) {
      mover = mover.next;
    } // while
    return mover.val.getHash();
  } // getHash()


  /**
   * Walks the BlockChain and ensures that its blocks are consistent 
   * (the balances are legal) and valid (as in append).
   * @return boolean
   */
  public boolean isValidBlockChain()  {
    Node mover = arr;
    while (mover.next != null) {
      if (!(mover.val.getPrevHash().equals(mover.next.val.getHash()))) {
        return true;
      } // if
      mover = mover.next;
    } // while
    return true;
  } // isValidBlockChain()


  /**
   * Prints Alexis’s and Blake’s respective balances in the form 
   * Alexis: <amt>, Blake: <amt> on a single line, e.g., Alexis: 300, Blake: 0.
   * @param pen
   */
  public void printBalances(PrintWriter pen)  {
    int balance = 0;
    Node mover = arr;

    while (mover.next != null) {
      balance += mover.val.getAmount();
    } // while
    pen.println("Alexis: " + balance + ", Blake" + balance*-1 + 300);
  } // printBalance(PrintWriter)


  /**
   * Returns a string representation of the BlockChain which is simply the 
   * string representation of each of its blocks, earliest to latest, one per line.
   * @return String
   */
  public String toString() {
    String str = ""; 
    Node mover = arr;

    while (mover.next != null) {
      str += ("Block " + mover.val.getNum() + "(Amount: " + mover.val.getAmount() + ", Nonce: " + mover.val.getNonce() + ", prevHash: " + mover.val.getPrevHash() + ", Hash: "  + mover.val.getHash() + ")\n");
    } // while
    return str;
  } // toString()

} // class BlockChain
