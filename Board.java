package Battleship;

import java.util.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A game board for the Battleship game.
 *
 * @author Amelia Lane
 * @version (2/11/2021)
*/

public class Board
{
  private final int ROWS = 8;
  private final int COLUMNS = 8;
  private String [][] grid;
  

  /**
  * Creates a new board with default number of rows and columns. 
  * Each space is ordered from 1-64 consecutively. 
  */
  public Board()
  {
    grid = new String [ROWS][COLUMNS];
    int num = 1;
    
    for (int r = 0; r < ROWS; r++)
    {
      for (int c = 0; c < COLUMNS; c++)
      {
        if ((r == 0 && c != 0) || (r==1 && c== 1))
        {
          grid [r][c] = " " + num;
        }
        else
        {
          grid[r][c] = "" + num;
        }
        num++;
      }
    }

  }


  /**
  * Prints out the game board. 
  */

  public void printBoard()
  {
    for (int r = 0; r < ROWS; r++)
    {
      for (int c = 0; c < COLUMNS; c ++)
      {
        System.out.print("  " + grid[r][c] + "  " );
      }
      System.out.println("\n");
    }

  }

  
  /** 
   *  Looks through the board to find a specific spot and returns its index. 
   *  If the desired spot is not found, this method will return -1.
   *  @param desiredSpot an integer representing the spot which index is needed.
   *  @param giveRow boolean asking if the user wants the row number returned. (if false, then this method will return the column number)
   *  @return int of either the row or column number. (dependent on giveRow parameter)
   */
  
  public int getIndex(int desiredSpot, boolean giveRow)
  {
    String spot = "" + desiredSpot;
    for (int r = 0; r < grid.length; r++)
    {
      for (int c = 0; c< grid.length; c++)
      {
        
        if (grid[r][c].indexOf(spot)!= -1)
        {
          if (giveRow == true)
          {
            return r;
          }
          else
          {
            return c;
          }
        }
      }
    }
    //if the desired spot is not found - that means it is already occupied 
    return -1;
  }



  /**
   *  Attempts to place the desired ship at the desired location.
   *  If the ship cannot be placed, this method leaves the board
   *  unchanged and returns false.
   *  @param whereToPutShip an integer representing the desired location (1-64)
   *  @param shipToPlace the ship to place
   *  @param placeHorizontally should this ship be placed horizontally? (if false, then it will be placed vertically)
   *  @return true if the ship was successfully placed, false otherwise.
   */
  public boolean placeShip(int whereToPutShip, Ship shipToPlace, boolean placeHorizontally)
  {
      int shipLength = shipToPlace.getLengthOfShip();
      int startRow = getIndex(whereToPutShip, true);
      int startColumn = getIndex(whereToPutShip, false);
      boolean canBePlaced = true;

      if (startRow == -1 || startColumn ==-1)
      {
        return false;
      }

      //going horizontally
      if (placeHorizontally == true)
      {
        if((startColumn + shipLength)> grid.length)
        {
          return false;
        }
        for(int c = startColumn; c <startColumn + shipLength; c++) 
        {
          String element = grid[startRow][c];
          if (element.indexOf("X") != -1)
          {
            canBePlaced = false;
            break;
          }
        }

        if (canBePlaced == true)
        {
          for(int c = startColumn; c <startColumn + shipLength; c++)
          {
            grid[startRow][c] = " X";  
          }
          return true;
        }
        else
        {
          return false;
        }
      }

      //place vertically 
      else
      {
        if((startRow + shipLength) > grid.length)
        {
          return false;
        }

        for(int r = startRow; r<startRow + shipLength - 1; r++)
        {
          String element = grid[r][startColumn];
          if (element.indexOf("X") != -1)
          {
            canBePlaced = false;
            break;
          }
        }

        if (canBePlaced == true)
        {
          for(int r = startRow; r < startRow + shipLength; r++)
          {
            grid[r][startColumn] = " X"; //edit to make sure the board is still lined up 
          }
          return true;
        }
        else
        {
          return false;
        }

      }


  }



/** 
 *  Randomly places ships on the board.
 *  @param allShips list of all the ships to be randomly placed on the board. 
 */

 public void randomPlacement(Ship[] allShips)
 {
   for (Ship current : allShips)
   {
     int spot;
     Random rando = new Random();
     boolean isHorizontal;

     do
     {
       spot = (int)(Math.random() * 64 + 1);
       isHorizontal = rando.nextBoolean();

     }
     while (placeShip(spot, current, isHorizontal) == false); 

   }
   
  }

/**
* Gets the 2D grid array (representing a game board) and returns it.
* @return the grid.
*/
 public String[][] getGrid()
 {
   return grid;
 }
  
/**
  * Looks to see if the guessed spot is a hit (spot with a ship piece) or a miss (empty spot).
  * If it is a hit returns true and changes the board space to a "*".
  * If it is a miss returns false and changes the board space to a "-".
  * @param spotGuessed int representing the spot to be checked for a hit or miss 
  * @param inQuestion board whose spot is being inspected.
  * @return true if the spot guessed has a ship piece, and false otherwise
*/
  public boolean turnResult(int spotGuessed, Board inQuestion)
  {
  //check if spot is free 
    if(this.getIndex(spotGuessed, true)== -1)
    {
      return false;
    }
    int r = this.getIndex(spotGuessed, true);
    int c = this.getIndex(spotGuessed, false);
    String [][] boardInQuestion = inQuestion.getGrid();
    String element = boardInQuestion[r][c];

    if(element.indexOf("X")!= -1)
    {
      grid[r][c] = " *";
      return true;
    }
    else
    {
      grid[r][c] = " -";
      return false;
    }
 
  }
  
 

}










