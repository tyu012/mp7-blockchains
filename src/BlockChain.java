import java.security.NoSuchAlgorithmException;
import java.io.PrintWriter;

/**
 * A linked list of Block objects.
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
   
  public void append(Block blk)  {

    Node mover = this.first;

    while (mover.next != null) {
      mover = mover.next;
    } // while

    if (blk.getHash().isValid() == false) {
      throw new IllegalArgumentException(); 
    } else if (!(blk.getPrevHash().equals(getHash()))) {
      throw new IllegalArgumentException(); 
    } // if

    size++;
  } // append(Block)
  */
  public void append(Block blk) throws IllegalArgumentException {
    // Check if hash is valid and appropriate to add to BlockChain after last Block in 'this'.
    if ((blk.getHash().isValid()) && (blk.getPrevHash().equals(this.last.data.getHash()))) {
      // Create 'temp' Node for new Block.
      Node temp = new Node(blk);
      // Position block after the last Block in 'this'
      this.last.next = temp;
      // Update this.last
      this.last = temp;
    } else {
      throw new IllegalArgumentException();
    } // if
  } // append(Block)

  /**
   * Removes the last block from the chain, returning true. If the chain 
   * only contains a single block, then removeLast does nothing and returns false.
   * @return boolean
   */
  public boolean removeLast() {
    // If this contains one or fewer blocks, we cannot remove a block.
    if (this.getSize() <= 1) {
      return false;
    } // if

    // Create Node at this.first.
    Node mover = this.first;
    // Progress mover to the second to last element of the BlockChain.
    while (!mover.next.equals(this.last)) {
      mover = mover.next;
    } // while
    // Update this.last to mover.
    this.last = mover;
    // Set original this.last to null.
    this.last.next = null;
    return true;
  } // removeLast()


  /**
   * Returns the hash of the last block in the chain.
   * @return Hash
   */
  public Hash getHash()  {
    return this.last.data.getHash();
  } // getHash()


  /**
   * Walks the BlockChain and ensures that its blocks are consistent 
   * (the balances are legal) and valid (as in append).
   * @return boolean

  public boolean isValidBlockChain()  {
    Node mover = this.first;
    while (mover.next != null) {
      if (!(mover.data.getPrevHash().equals(mover.next.data.getHash()))) {
        return true;
      } // if
      mover = mover.next;
    } // while
    return true;
  } // isValidBlockChain()
   */
  public boolean isValidBlockChain() {
    // Create Node at this.first.
    Node mover = this.first;
    // Initialize balance of all transactions.
    int balance = 0;
    // While mover is not at the end of the BlockChain.
    while (mover != null) {
      // Add amount transferred in current block to 'balance'.
      balance += mover.data.getAmount();
      // Check that current hash is valid, that balance is positive, and that balance does not exceed Alexis' initial.
      if (!mover.data.getHash().isValid() || (balance < 0) || (balance > this.first.data.getAmount())) {
        return false;
      } // if
      // Progress 'mover'.
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
    // Initialize variables to track Alexis' and Blake's respective balances.
    int alexisBalance = this.first.data.getAmount();
    int blakeBalance = 0;
    // Create node.
    Node mover = this.first.next;

    // While there is another node in the BlockChain.
    while (mover != null) {
      // Use node to update balances. Then progress mover.
      alexisBalance += mover.data.getAmount();
      blakeBalance -= mover.data.getAmount();
      mover = mover.next;
    } // while
    // Print balances.
    pen.println("Alexis: " + alexisBalance + ", Blake: " + blakeBalance);
  } // printBalance(PrintWriter)


  /**
   * Returns a string representation of the BlockChain which is simply the 
   * string representation of each of its blocks, earliest to latest, one per line.
   * @return String
   */
  public String toString() {
    // Create string to be returned.
    String str = ""; 
    // Create node at first Node of BlockChain.
    Node mover = this.first;

    // While 'mover' is not at the end of the BlockChain...
    while (mover != null) {
      // Update 'str' with current Block.
      str += ("Block " + mover.data.getNum() + " (Amount: " + mover.data.getAmount() + ", Nonce: " + mover.data.getNonce() + ", prevHash: " + mover.data.getPrevHash() + ", Hash: "  + mover.data.getHash() + ")\n");
      // Progress 'mover'.
      mover = mover.next;
    } // while
    return str;
  } // toString()

} // class BlockChain
