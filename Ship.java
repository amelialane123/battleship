package Battleship;

/**
* Represents a ship piece for battleship game.
*
* @author Amelia Lane
* @version (2/11/2021)
*/

public class Ship
{
  private String nameOfShip;
  private int lengthOfShip;

  /**
  * creates a new ship with a specified name and length.
  * @param theName string representing the name of the ship. 
  * @param theLength int representing the length of the ship.
  */
  public Ship(String theName, int theLength)
  {
    nameOfShip = theName;
    lengthOfShip = theLength;

  }

  /**
  * gets the name of the ship and returns it. 
  * @return name of the ship.
  */

  public String getNameOfShip()
  {
    return nameOfShip;
  }

 /**
  * gets the length of the ship and returns it. 
  * @return length of the ship.
  */
  public int getLengthOfShip()
  {
    return lengthOfShip;
  }
}
