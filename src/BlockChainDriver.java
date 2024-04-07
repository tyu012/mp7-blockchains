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
    // Create PrintWriter objects for error messages and for printing to terminal.
    PrintWriter redpen = new PrintWriter(System.err, true);
    PrintWriter pen = new PrintWriter(System.out, true);
    // Create Scanner to read user input.
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
      pen.println("Command? ");
      command = eyes.nextLine();

      switch (command) {

        case "mine" :
          mineHelper(pen, eyes, blockChain);
          break;

        case "append" :
          addBlock(pen, eyes, blockChain);
          break;
        
        case "remove" :
        blockChain.removeLast();
        break;

        case "check" :
        checkChain(pen, blockChain);
        break;

        case "report" :
        blockChain.printBalances(pen);
        break;

        case "help" : 
          validCommands(pen);
          break;

        case "quit" :
          break;

        default :
          pen.println("Invalid command. Enter \"help\" to view valid commands.");
      } // switch
    } // while
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
    // Create string to store user input.
    String input = "";
    // Request user input and store in 'input'.
    pen.printf("Amount transferred? ");
    input = scanner.next();

    // Create new Block to print nonce.
    try {
      Block temp = blockChain.mine(Integer.parseInt(input));
      pen.println("amount = " + input + ", nonce = " + temp.getNonce() + "\n");
    } catch (Exception e) {
      PrintWriter redpen = new PrintWriter(System.err, true);
      redpen.println("Invalid number format.");
    }
  } // mineHelper(PrintWriter, Scanner, BlockChain)

  /**
   * Helper function to append new block of given transfer amount and nonce.
   * @param pen
   * @param scanner
   * @param blockChain
   */
  public static void addBlock(PrintWriter pen, Scanner scanner, BlockChain blockChain) {
    // Create string to store user input.
    String input = "";
    // Request and store user input.
    pen.printf("Amount transferred? ");
    input = scanner.next();
    int transferred = Integer.parseInt(input);

    pen.printf("Nonce? ");
    input = scanner.next();
    Long nonce = Long.parseLong(input);

    // Create new Block to add.
    try {
      Block temp = new Block(blockChain.getSize(), transferred, blockChain.last.hash, nonce);
      blockChain.append(temp);
    } catch (Exception e) {
      PrintWriter redpen = new PrintWriter(System.err, true);
      redpen.println("Invalid block.");
    }
  } // addBlock(PrintWriter, Scanner, BlockChain)

  /**
   * Helper function to print appropriate responses depending on if a given BlockChain is valid.
   * @param pen
   * @param blockChain
   */
  public static void checkChain(PrintWriter pen, BlockChain blockChain) {
    if (blockChain.isValidBlockChain()) {
      pen.println("Chain is valid!");
    } else {
      pen.println("Chain is not valid!");
    } // if
  } // checkChain(PrintWriter, BlockChain)

} // class BlockChainDriver
