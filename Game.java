package Battleship;

import java.util.Scanner;
import java.util.ArrayList;
import javax.lang.model.util.ElementScanner6;

/**
 * Creates a Battleship game for a user to play against the computer.
 *
 * @author Amelia Lane
 * @version (2/11/2021)
*/


public class Game
{
  private Board userBoard; 
  private Board computerBoard;
  private Board userGuesses;
  private Board computerGuesses;
  private Ship[] playerShips;
  private int userPoints;
  private int computerPoints;
  private ArrayList <Integer> userNumsGuessed;
  private ArrayList <Integer> computerNumsGuessed;

  /**
  * Creates a new Battleship game against computer with a default board size and default number of ships.
  */
  public Game()
  {
    playerShips = new Ship[]{new Ship("Carrier",5), new Ship("Battleship",4), new Ship("Submarine",3), new Ship("Cruiser",3), new Ship("Destroyer",2), };

    userPoints = 0;
    computerPoints = 0;
    computerBoard = new Board(); 
    userGuesses = new Board();
    computerGuesses = new Board();
    computerBoard.randomPlacement(playerShips);
    Scanner in = new Scanner(System.in);
    Scanner intIn = new Scanner(System.in);
    userNumsGuessed = new ArrayList <Integer>(); 
    computerNumsGuessed = new ArrayList <Integer>(); 

    
    
    
    System.out.println("Hey! Let's play battleship! \n \nTo start you must place your ships. Here is your board... \n \n");
    sleep(1000);
    userBoard = new Board();
    userBoard.printBoard();

    //let user place their ships...

    System.out.println("You have 5 ships to place\n");

    for (Ship current : playerShips)
    {
      boolean isHorizontal = false;
      int spot = 0;
      boolean tryAgain = false;
      System.out.println("\n\nNow we are going to place your " + current.getNameOfShip() + ". This ship takes up " + current.getLengthOfShip() + " spaces.");

      do
      {
        if (tryAgain == true)
        {
          System.out.println("Sorry that answer is not an option. Please make sure to submit either \"h\" or \"v\". Let's try again... ");
        }
        System.out.println("\nTo place this ship horizontally: type \"h\"\nTo place this ship vertically: type \"v\" ");
        String answer = in.nextLine();
        if (answer.equals("h"))
        {
          isHorizontal = true;
          tryAgain = false;
        }
        else if (answer.equals("v"))
        {
          isHorizontal = false;
          tryAgain = false;
        }
        else
        {
          tryAgain = true;
        }
      }
      while (tryAgain == true);



      System.out.print("\nGreat! Now enter the space number to start...\n");

      do
      {
        if (tryAgain ==true)
        {
          System.out.print("Sorry, that space is not available! Let's try again... \nenter the space number to start.\n");
        }
        
        if (isHorizontal == true)
        {
          System.out.println("Your ship will take up this space and the " + (current.getLengthOfShip()-1) + " spaces to its right.");
        }
        else
        {
          System.out.println("Your ship will take up this space and the " + (current.getLengthOfShip()-1) + " spaces below it.");
        }

        //makes sure the user's input is an int so their is no errors.
        boolean isAcceptable = true;
        do
        {
          isAcceptable = true;
          try 
          {
            spot = Integer.parseInt(in.nextLine());
          } 
          catch(NumberFormatException nfe) 
          {
            isAcceptable = false;
            System.out.println("Oh no! That is not an acceptable answer! Please make sure to type an integer from 1-64.");
          }
        }
        while(isAcceptable == false);

        if (userBoard.placeShip(spot, current, isHorizontal)== false)
        {
          tryAgain = true;
        }
        else
        {
          tryAgain = false;
        }

      }
      while(tryAgain == true);

      System.out.println("");
      userBoard.printBoard();

      if (current.getNameOfShip().equals("Destroyer"))
      {
        System.out.println("\nGreat Spot!\nAll your ships are placed. Let's start the game.");
        break;
      }
      System.out.println("\n Great spot! Let's move on to the next ship...");
      

    }

    //game begins 

    System.out.println("\nBelow is your guessing board. It will keep track of all the hits and misses you get when guessing the placement of the computer's ships.\n");

    do
    {
      int computerGuess = 0;
     //user turn 
      System.out.println("It is your turn.\n");
    
      userGuesses.printBoard();
      System.out.println("Pick a spot to guess.");
      int guess = intIn.nextInt();

      while (userNumsGuessed.indexOf(guess) !=-1 || userGuesses.getIndex(guess, true) == -1)
      {
        System.out.println("Sorry, that spot is not available to guess! Try picking another spot on the board.");
        guess = intIn.nextInt();
      }
      userNumsGuessed.add(guess);

      System.out.println("\n\nOOH good guess! Let's see if it was a hit or miss...");
      sleep(5000);
      while(userGuesses.turnResult(guess, computerBoard) == true)
      {
        userPoints ++;
        System.out.println("BOOM! HIT! Great guess!\n");

        if(userPoints == 17)
        {
          userGuesses.printBoard();
          System.out.println("YOU WIN! GREAT GAME!");
          return;
        }
        sleep(3000);
        System.out.println("You get to guess again!\n");
        userGuesses.printBoard();
        System.out.println("Pick a spot to guess.");
        guess = intIn.nextInt();

        while (userNumsGuessed.indexOf(guess) !=-1 || userGuesses.getIndex(guess, true) == -1)
          {
            System.out.println("Sorry, that spot is not available to guess! Try picking another spot on the board.");
            guess = intIn.nextInt();
          }
        sleep(5000);
      }
      System.out.println("MISS! Good try. Your turn is over :(\nYou have " + userPoints + "/17 points."+"\n\n");
      sleep(3000);

    //Computer's turn
    int currentR = 0;
    int currentC = 0;
    do
    {
      computerGuess = (int)(Math.random() * 64 + 1);
    }
    while (computerNumsGuessed.indexOf(computerGuess)!= -1 || computerGuesses.getIndex(computerGuess, true) == -1);


    System.out.println("The computer guessed spot " + computerGuess + ". And it was... \n");
    sleep(5000);
    

    //when computer guesses correctly 
    while(computerGuesses.turnResult(computerGuess, userBoard)== true)
    {
      computerGuesses.printBoard();
      computerPoints ++;
      
      boolean tryAgain = true;
      currentR = computerGuesses.getIndex(computerGuess, true);
      currentC = computerGuesses.getIndex(computerGuess, false);
      System.out.println("BOOM! A HIT! The computer hit one of your ships!\n");
      if(computerPoints == 17)
      {
        computerGuesses.printBoard();
        System.out.println("COMPUTER WINS! Good try :)");
        return;
      }
      System.out.println("Now it gets to go again.");

      do
      {
        do
        {
          int nextGuess = (int)(Math.random()*4 + 1);
          if(nextGuess == 1)
          {
            if (currentR-1 != -1)
            {
              tryAgain = false;
              computerGuess = computerGuess - 8;
            }
          }
          else if(nextGuess == 2)
          {
            if(currentC - 1 != -1)
            {
              tryAgain = false;
              computerGuess = computerGuess - 1;
            }
          }
          else if (nextGuess == 3)
          {
            if(currentC + 1 != 8)
            {
              tryAgain = false;
              computerGuess = computerGuess +1;
            }
          }
          else 
          {
            if (currentR + 1 != 8)
            {
              tryAgain = false;
              computerGuess = computerGuess + 8;
            }
          }
        }
        while (tryAgain == true);

      }
      while (computerNumsGuessed.indexOf(computerGuess)!= -1 || computerGuesses.getIndex(computerGuess, true) == -1);
      
      System.out.println("The computer guessed spot " + computerGuess + ". And it was... \n");
      sleep(5000);
      

    }
    computerGuesses.printBoard();
    System.out.println("MISS! The computer has " + computerPoints + "/17 points.");

    sleep(5000);
    }
    while(userPoints != 17 || computerPoints != 17);
    
    if (userPoints == 17)
    {
      System.out.println("YOU WIN! GREAT GAME!");
      return;
    }
    else 
    {
      System.out.println("COMPUTER WINS! Good try :)");
      return;
    } 

  }

  /**
  * Delays the code from running.
  * @param time an int representing the number of milliseconds to rest.
  */
  
  public void sleep(int time)
  {
    try 
    {
      Thread.sleep(time);
    } 
    catch (InterruptedException ex) 
    {
      ex.printStackTrace();
    }
  }

  


}

