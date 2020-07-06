package assignment4Game;

import java.io.*;

public class Game {
 
 public static int play(InputStreamReader input){
  BufferedReader keyboard = new BufferedReader(input);
  Configuration c = new Configuration();
  int columnPlayed = 3; int player;

  c.addDisk(firstMovePlayer1(), 1);
  int nbTurn = 1;
  
  while (nbTurn < 42){ 
   player = nbTurn %2 + 1;
   if (player == 2){
    columnPlayed = getNextMove(keyboard, c, 2);
   }
   if (player == 1){
    columnPlayed = movePlayer1(columnPlayed, c);
   }
   System.out.println(columnPlayed);
   c.addDisk(columnPlayed, player);
   if (c.isWinning(columnPlayed, player)){
    c.print();
    System.out.println("Congrats to player " + player + " !");
    return(player);
   }
   nbTurn++;
  }
  return -1;
 }
 
 public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
   System.out.println("The current board configuration is: ");
   c.print();
   System.out.println("Waiting on your next move ... pick your column!");
   int move = 0;
   try {
     move = Integer.parseInt(keyboard.readLine());
     while(c.available[move] > 5){
       System.out.println("You cannot drop your disk there, the column is full!");
       move = Integer.parseInt(keyboard.readLine());
     } 
       return move;
     } 
   catch(IOException e){
     System.err.println("Error: " + e);
     move = getNextMove(keyboard, c, player);
   }
   catch (NumberFormatException e) {
     System.err.println("Invalid number");
     move = getNextMove(keyboard, c, player);
   }
   catch(ArrayIndexOutOfBoundsException e){
     System.err.println("That is not a valid column, only columns 0-6 are valid.");
     move = getNextMove(keyboard, c, player);
   }
   return move; // DON'T FORGET TO CHANGE THE RETURN
 }
 
 public static int firstMovePlayer1 (){
  return 3;
 }
 
 public static int movePlayer1 (int columnPlayed2, Configuration c){
   int firstChoice =  c.canWinNextRound(1);
   int secondChoice = c.canWinTwoTurns(1);
   int k = columnPlayed2;
   if(firstChoice >= 0){
     System.out.println("Player 1 played to win");
     return firstChoice;
   }
   else if(secondChoice >= 0){
     System.out.println("Player 1 played to win in 2 moves");
     return secondChoice;
   }
   else if(c.available[k] < 6){
     System.out.println("Player 1 played the default move");
     return k;
   }
   else{
      System.out.println("Player 1 played the default full-column move");
     for(int i = 1; i < 6; i++){
       if(k-i >= 0){
         if(c.available[k-i] < 6){
           return k-i;
         }
       }
       if(k+i < 7){
         if(c.available[k+i] < 5){
           return k+i;
         }
       }
     }
   }
   
   return 0; // DON'T FORGET TO CHANGE THE RETURN
 }
 
}
