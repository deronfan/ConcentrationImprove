import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
/** 
 * A game board of NxM board of tiles.
 * 
 *  @author PLTW
 * @version 2.0
 */

/** 
 * A Board class for concentration
 */
public class Board
{  
  private String[] tileValues = {" Ambusher  ", " Ambusher  ",
                                 "Artillerist", "Artillerist",
                                 "  Bruiser  ", "  Bruiser  ",
                                 " Dominator ", " Dominator ",
                                 "Quickstrike", "Quickstrike",
                                 " Sentinel  ", " Sentinel  ",
                                 "  Sniper   ", "  Sniper   ",
                                 "BloodHunter", "BloodHunter",
                                 "  Sorcerer ", "  Sorcerer ",
                                 " Visionary ", " Visionary ",
                                 "  Watcher  ", "  Watcher  ",
                                 "Mech Herald", "Mech Herald"}; 
  private Tile[][] gameboard = new Tile[6][4];

  /**  
   * Constructor for the game. Creates the 2D gameboard
   * by populating it with card values
   * 
   */
  public Board()
  {
    for(int c = 0; c < 6; c++){
      for(int r = 0; r < 4; r++){
        boolean finish = false;
        while(!finish){
          int insert = (int)(Math.random()*tileValues.length);
          if(tileValues[insert] != null){
            gameboard[c][r] = new Tile(tileValues[insert]);
            // System.out.print(tileValues[insert]);
            tileValues[insert] = null;
            finish = true;
        }
      }
    }
      // System.out.println();
  }
}
 /** 
   * Returns a string representation of the board, getting the state of
   * each tile. If the tile is showing, displays its value, 
   * otherwise displays it as hidden.
   * 
   * Precondition: gameboard is populated with tiles
   * 
   * @return a string represetation of the board
   */
  public String toString()
  {
    String out = "";
    for(int c = 0; c < 6; c++){
      out += "" + c;
      for(int r = 0; r < 4; r++){
        if(gameboard[c][r].isShowingValue()) out += " : " + gameboard[c][r].toString();
        else out += " : " + gameboard[c][r].getHidden();
      }
      out += "\n";
    }
    System.out.println(out);
    return out;
  }

  /** 
   * Determines if the board is full of tiles that have all been matched,
   * indicating the game is over.
   * 
   * Precondition: gameboard is populated with tiles
   * 
   * @return true if all tiles have been matched, false otherwse
   */
  public boolean allTilesMatch()
  {
    boolean check = true;
for(int c = 0; c < 6; c++){
      for(int r = 0; r < 4; r++){
    if(!gameboard[c][r].matched()) check = false;
  }
    }
    return check;
  }

  /** 
   * Sets the tile to show its value (like a playing card face up)
   * 
   * Preconditions:
   *   gameboard is populated with tiles,
   *   row values must be in the range of 0 to gameboard.length,
   *   column values must be in the range of 0 to gameboard[0].length
   * 
   * @param row the row value of Tile
   * @param column the column value of Tile
   */
  public void showValue (int row, int column)
  {
    gameboard[row][column].show();
    /* your code here */
  }  

  /** 
   * Checks if the Tiles in the two locations match.
   * 
   * If Tiles match, show Tiles in matched state and return a "matched" message
   * If Tiles do not match, re-hide Tiles (turn face down).
   * 
   * Preconditions:
   *   gameboard is populated with Tiles,
   *   row values must be in the range of 0 to gameboard.length,
   *   column values must be in the range of 0 to gameboard[0].length
   * 
   * @param row1 the row value of Tile 1
   * @param col1 the column value of Tile 1
   * @param row2 the row value of Tile 2
   * @param col2 the column vlue of Tile 2
   * @return a message indicating whether or not a match occured
   */
  public String checkForMatch(int row1, int col1, int row2, int col2)
  {
    String msg = "";
    if(row1 == row2 && col1 == col2){
      msg = "You cannot choose the same thing twice!";
      gameboard[row1][col1].hide();
      gameboard[row2][col2].hide();
    }
    else if(gameboard[row1][col1].getValue().equals(gameboard[row2][col2].getValue())){
      msg = "You found a match!";
      gameboard[row1][col1].foundMatch();
      gameboard[row2][col2].foundMatch();
    }
    else{
      msg = "Incorrect";
      gameboard[row1][col1].hide();
      gameboard[row2][col2].hide();
    }
     return msg;
  }

  /** 
   * Checks the provided values fall within the range of the gameboard's dimension
   * and that the tile has not been previously matched
   * 
   * @param rpw the row value of Tile
   * @param col the column value of Tile
   * @return true if row and col fall on the board and the row,col tile is unmatched, false otherwise
   */
  public boolean validateSelection(int row, int col)
  {
    if (row < 6 && col < 4 && row > -1 && col > -1) {
      return true; 
    }
    else{
      return false;
    }
  }

}
