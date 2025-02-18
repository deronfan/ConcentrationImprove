/** 
 * The game that uses a n x m board of tiles or cards.
 *  
 * Player chooses two random tiles from the board. The chosen tiles
 * are temporarily shown face up. If the tiles match, they are removed from board.
 * 
 * Play continues, matching two tiles at a time, until all tles have been matched.
 * 
 * @author PLTW
 * @version 2.0
*/
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
/**
 * A game class to play concentration
 */
public class Game
{
  private Scanner in = new Scanner(System.in);
  
  private Board board;
  private int row1, col1;
  private int row2, col2;
  private int colpower;
  private int rowpower;

  public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException
  {
    int numguess = 0;
    this.rowpower = 3;
    this.colpower = 2;
    // AudioInputStream flipsound = AudioSystem.getAudioInputStream(new File("Flip.wav"));
    // var fclip = AudioSystem.getClip();
    // fclip.open(flipsound);
    // fclip.start();
    // instructions
    System.out.println("Welcome!");
    System.out.println("Select the tile locations you want to match,");
    System.out.println("or enter 'q' to quit.");
    System.out.println("Type 'col' to use your column power or");
    System.out.println("'row' to use your row power.");
    System.out.println("(You will need to know 2D arrays to play!)");
    System.out.println("\nPress Enter to continue...");
    in.nextLine();

    board = new Board();
    // play until all tiles are matched
    long startTime = System.currentTimeMillis();
    while (!board.allTilesMatch())
    {
      long currentTime = System.currentTimeMillis();
      // get player's first selection, if not an integer, quit
      row1 = -1;
      col1 = -1;
      boolean validTile = false;
      while (!validTile)
      {
        displayBoard();
        System.out.println((currentTime - startTime)/1000 + " seconds");
        System.out.print("First choice (row col): ");
        validTile = getTile(true); 
      }

      // display first tile
      board.showValue(row1, col1);

      // get player's second selection, if not an integer, quit
      row2 = -1;
      col2 = -1;
      validTile = false;
      while (!validTile)
      {
        displayBoard();
        System.out.print("Second choice (row col): ");
        validTile = getTile(false);
         
        // check if user chosen same tile twice
        if ((row1 == row2) && (col1 == col2))
        {
          System.out.println("You must choose a different second tile");
          wait(2);
          validTile = false;
        }
      }
      numguess++;
      // display second tile
      board.showValue(row2, col2);
      displayBoard();
      System.out.println("You have used " + numguess + " guesses.");

      // determine if tiles match
      String matched = board.checkForMatch(row1, col1, row2, col2);
      System.out.println(matched);

      // wait 2 seconds to start the next turn
      wait(1);
    }

    displayBoard();
    System.out.println("You Win!");
    long endTime = System.currentTimeMillis();
    if(rowpower == 3 && colpower == 2){
        System.out.println("Achievement Got: I'm batman (Use no powers)");
    }
    if(numguess <= 24){
        System.out.println("Achievement Got: God like Memory (Use 24 or less guesses)");
    }
    if(numguess == 12){
        System.out.println("Achievement Got: RNJesus (Use only 12 guesses)");
    }
    if((endTime - startTime)/1000<=240){
      System.out.println("Achievement Got: Speedrunner (Beat the game in under 4 minutes)");
    }
    if((endTime - startTime)/1000<=120){
      System.out.println("Achievement Got: Two Fast (Beat the game in under 2 minutes)");
    }
    System.out.println("You used a total of " + numguess + " guesses");
    System.out.println("That took " + (endTime - startTime)/1000 + " seconds");
  }

  /**
   * Get tile choices from the user, validating that
   * the choice falls within the range of rows and columns on the board.
   * 
   * @param firstChoice if true, saves the user's first row/col choice, otherwise sets the user's second choice
   * @return true if user has made a valid choice, false otherwise
   */

  private boolean getTile(boolean firstChoice)
  {
    int num1 = 0;
    int num2 = 0;
    
    if (in.hasNextInt())
      num1 = in.nextInt();
    else if(in.hasNext()){
      String temp = in.next();
      if(temp.toLowerCase().contains("q")){
        quitGame(); 
      }
      else if(temp.toLowerCase().contains("row")){
        powerrow();
        return false;
      }
      else if(temp.toLowerCase().contains("col")){
        powercol();
        return false;
      }
    }
    else{
      System.out.print("Invalid input, please try again. ");
      wait(2);
      return false;
    }
  
    if (in.hasNextInt())
      num2 = in.nextInt();
    else{
      System.out.print("Invalid input, please try again. ");
      wait(2);
      return false;
    }

    in.reset(); // ignore any extra input

    if (!board.validateSelection(num1, num2))
    {
      System.out.print("Invalid input, please try again. ");
      wait(2);
      return false;
    }
    if (firstChoice)   
    {
      row1 = num1;
      col1 = num2;
    }
    else 
    {
      row2 = num1;
      col2 = num2;
    }
    return true;
  }

  /**
   * Clear the console and show the game board
   */
  public void powercol() {
    if(colpower > 0){
      System.out.println("Please enter the column you wish to reveal:");
      if(in.hasNextInt() ){
        int colvictim = in.nextInt();
        if(colvictim < 4 && colvictim > -1){
        colpower--;
        board.showCol(colvictim);
        displayBoard();
        wait(4);
        board.hideCol(colvictim);
        }
        else{
          System.out.println("Invalid Choice");
          wait(1);
        }
      }
      else{
        System.out.println("Invalid Choice");
        wait(1);
      }
    }
    else{
      System.out.println("Out of Power");
      wait(1);
    }
  }
  public void powerrow() {
    if(rowpower > 0){
      System.out.println("Please enter the row you wish to reveal:");
      if(in.hasNextInt()){
        int rowvictim = in.nextInt();
        if(rowvictim < 6 && rowvictim > -1){
        rowpower--;
        board.showRow(rowvictim);
        displayBoard();
        wait(4);
        board.hideRow(rowvictim);
        }
        else{
          System.out.println("Invalid Choice");
          wait(1);
        }
        
      }
      else{
        System.out.println("Invalid Choice");
        wait(1);
      }
    }
    else{
      System.out.println("Out of Power");
      wait(1);
    }
  }
  public void displayBoard()
  {

    // scroll current board off screen
    for (int x = 0; x < 60; x++) {
      System.out.println();
    }
    System.out.println(board);
    System.out.println("Number of Row Power: " + rowpower);
    System.out.println("Number of Column Power: " + colpower);
  }

  /**
   * Wait n seconds before clearing the console
   */
  private void wait(int n)
  {
    // a try is like an if statement, "throwing" an error if the body of the try fails
    try
    {
      Thread.sleep(n * 1000);
    } catch (InterruptedException e) { /* do nothing */ }
  }

  /** 
   * User quits game
   */
  private void quitGame() 
  {
    System.out.println("Quit game!");
    System.exit(0);
  }
}