/**
 * INSERT MESSAGE HERE
 * 
 * @author Tim Yu
 * @author Nye Tenerelli
 * @author Keely Miyamoto
 */
public class BlockChain {
  
  Block val;  

  Block next;

  int size;

  Node arr;

  public Node(Block val, Block next) {  
      this.val = val;  
      this.next = next;
  }  

//     BlockChain(int initial): creates a BlockChain that possess a single block the starts with the given initial amount. Note that to create this block, the prevHash field should be ignored when calculating the block’s own nonce and hash.
  public BlockChain(int initial) {
    arr = (new Block(0, initial, null), null);
    size = 1;
  }
//     Block mine(int amount): mines a new candidate block to be added to the end of the chain. The returned Block should be valid to add onto this chain.
  public Block mine(int amount)  {

    Node mover = arr;

    while (mover.next != null) {
      mover = mover.next;
    }
    mover.next = Node(blk, null);

    Block blk = new Block(size + 1, amount, blk.prevHash());
    return blk;
  }
//     int getSize(): returns the size of the blockchain. Note that number of the blocks provides a convenient method for quickly determining the size of the chain.
  public int getSize() {
    return size;
  }
//     void append(Block blk): adds this block to the list, throwing an IllegalArgumentException if this block cannot be added to the chain (because it has an invalid hash, because its hash is inappropriate for the contents, or because the previous hash is incorrect).
  public void append(Block blk)  {

    Node mover = arr;

    while (mover.next != null) {
      mover = mover.next;
    }

    if (blk.getHash().isValid() == false) {
      throw new IllegalArgumentException(); 
    } else if (!(blk.getPrevHash().equals(getHash()))) {
      throw new IllegalArgumentException(); 
    }

    size++;
    mover.next = Node(blk, null);
  }
//     boolean removeLast(): removes the last block from the chain, returning true. If the chain only contains a single block, then removeLast does nothing and returns false.
  public boolean removeLast() {
    if (size == 0) {
      return false;
    }

    Node mover = arr;
    while (mover.next.next != null) {
      mover = mover.next;
    }
    mover.next = null;
    return true;
  }
//     Hash getHash(): returns the hash of the last block in the chain.
  public Hash getHash()  {
    Node mover = arr;

    while (mover.next != null) {
      mover = mover.next;
    }
    return mover.val.getHash();
  }
//     boolean isValidBlockChain(): walks the blockchain and ensures that its blocks are consistent (the balances are legal) and valid (as in append).

//     void printBalances(PrintWriter pen): prints Alexis’s and Blake’s respective balances in the form Alexis: <amt>, Blake: <amt> on a single line, e.g., Alexis: 300, Blake: 0.
  public void getBalance()  {
    int balance = 0;

    while (mover.next != null) {
      balance += mover.val.getAmount();
    }
    System.out.println("Alexis: " + amount + ", Blake" + amount*-1 + 300);
  }
//     String toString(): returns a string representation of the BlockChain which is simply the string representation of each of its blocks, earliest to latest, one per line.
  public String toString() {
    StringBuffer str = new StringBuffer(""); 

    while (mover.next != null) {
      str.append("Block " + mover.val.getNum + "(Amount: " + mover.val.getAmount + ", Nonce: " + mover.val.getNonce + ", prevHash: " + mover.val.getPrevHash + ", Hash: "  + mover.val.getHash + ")\n");
    }
    return str;
  }
}
