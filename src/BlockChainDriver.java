import java.io.PrintWriter;
import java.util.Scanner;

/**
 * User-facing program to interact with BlockChain.
 * 
 * The program creates a blockchain with the initial dollar amount and then repeatedly:
 *    Prints out the contents of the blockchain.
 *    Reads in a command from the user.
 *    Executes that command, potentially updating the blockchain and reporting back to the user.
 * Supports commands for: mine, append, remove, check, report, help, quit.
 * 
 * @author Tim Yu
 * @author Nye Tenerelli
 * @author Keely Miyamoto
 */
public class BlockChainDriver {
  /**
   * Main method to interact with BlockChain.
   * @param args - Enter Alexis' initial amount (must be > $0).
   */
  public static void main(String[] args) throws Exception {
    // Create PrintWriter objects for error messages ('redpen') 
    // and for printing to terminal ('pen').
    PrintWriter redpen = new PrintWriter(System.err, true);
    PrintWriter pen = new PrintWriter(System.out, true);
    // Create Scanner to read user input
    // and String to store most recent user command.
    Scanner eyes = new Scanner(System.in);
    String command = "";

    // Check for appropriate number of command line args.
    if (args.length != 1) {
      redpen.println("Invalid amount of command line args.");
      System.exit(1);
    } // if

    // Initialize Alexis' initial amount.
    int initial = 0;
    try { 
      initial = Integer.parseInt(args[0]);
      // Check if positive.
      if (initial < 0) {
        redpen.println("Please enter valid stating amount (> $0).");
        System.exit(1);
      } // if
    } catch (Exception e) {
      redpen.println("Please enter valid stating amount.");
      System.exit(1);
    } // try/catch

    // Initialize a new BlockChain using 'initial' amount.
    BlockChain blockChain = new BlockChain(initial);

    // While 'command' is not "quit"...
    while (!command.equals("quit")) {
      // Print 'blockChain' and read in next command.
      pen.println(blockChain.toString());
      pen.printf("Command? ");
      command = eyes.next();

      // Switch actions depending on 'command':
      switch (command) {

        // Mine new nonce, using helper function.
        case "mine" :
          mineHelper(pen, eyes, blockChain);
          break;

        // Append new block to 'blockChain' using helper.
        case "append" :
          addBlock(pen, eyes, blockChain);
          break;
        
        // Remove last block.
        case "remove" :
          blockChain.removeLast();
          break;

        // Check if 'blockChain' is valid.
        case "check" :
          checkChain(pen, blockChain);
          break;

        // Get report of Alexis' and Blake's balances.
        case "report" :
          blockChain.printBalances(pen);
          break;

        // View menu of commands.
        case "help" : 
          validCommands(pen);
          break;

        // Quit program.
        case "quit" :
          break;
        
        // Error message if invalid command is entered.
        default :
          pen.println("Invalid command. Enter \"help\" to view valid commands.");
      } // switch
      pen.println();
    } // while
    eyes.close();
  } // main


  // +-----------------+
  // | Helper Methods |
  // +----------------+---------------------------------------------------------------------------
  /**
   * Helper function to print menu of valid commands.
   * @param pen
   */
  public static void validCommands(PrintWriter pen) {
    pen.println("Valid commands:");
    pen.println("    mine: discovers the nonce for a given transaction");
    pen.println("    append: appends a new block onto the end of the chain");
    pen.println("    remove: removes the last block from the end of the chain");
    pen.println("    check: checks that the block chain is valid");
    pen.println("    report: reports the balances of Alexis and Blake");
    pen.println("    help: prints this list of commands");
    pen.println("    quit: quits the program");
  } // validCommands(PrintWriter)

  /**
   * Helper function to discover nonce of inputted transaction.
   * @param pen
   * @param scanner
   * @param blockChain
   */
  public static void mineHelper(PrintWriter pen, Scanner scanner, BlockChain blockChain) {
    // Request user input for amount, convert String to Integer, and store in 'input'.
    pen.printf("Amount transferred? ");
    int input = Integer.valueOf(scanner.next());

    // Create new Block to print nonce.
    try {
      Block temp = blockChain.mine(input);
      pen.println("amount = " + input + ", nonce = " + temp.getNonce());
    } catch (Exception e) {
      PrintWriter redpen = new PrintWriter(System.err, true);
      redpen.println("Error creating valid nonce. Please try again.");
    } // try/catch
  } // mineHelper(PrintWriter, Scanner, BlockChain)

  /**
   * Helper function to append new block of given transfer amount and nonce.
   * @param pen
   * @param scanner
   * @param blockChain
   */
  public static void addBlock(PrintWriter pen, Scanner scanner, BlockChain blockChain) {
    // Request user input for amount transferred, convert String to Integer, and store in 'transfer'.
    pen.printf("Amount transferred? ");
    int transfer = Integer.valueOf(scanner.next());

    // Request user input for nonce, convert String to Long, and store in 'nonce'.
    pen.printf("Nonce? ");
    long nonce = Long.valueOf(scanner.next());

    // Create new Block to append to blockChain.
    try {
      Block temp = new Block(blockChain.getSize(), transfer, blockChain.getHash(), nonce);
      blockChain.append(temp);
    } catch (Exception e) {
      PrintWriter redpen = new PrintWriter(System.err, true);
      redpen.println("Invalid block. Please try again.");
    } // try/catch
  } // addBlock(PrintWriter, Scanner, BlockChain)

  /**
   * Helper function to print appropriate responses depending on if a given BlockChain is valid.
   * @param pen
   * @param blockChain
   */
  public static void checkChain(PrintWriter pen, BlockChain blockChain) {
    // Check if 'blockChain' is valid.
    if (blockChain.isValidBlockChain()) {
      pen.println("Chain is valid!");
    } else {
      pen.println("Chain is not valid!");
    } // if
  } // checkChain(PrintWriter, BlockChain)

} // class BlockChainDriver
