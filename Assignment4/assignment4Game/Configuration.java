package assignment4Game;

public class Configuration {
 
 public int[][] board;
 public int[] available;
 boolean spaceLeft;
 
 public Configuration(){
  board = new int[7][6];
  available = new int[7];
  spaceLeft = true;
 }
 
 public void print(){
  System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
  System.out.println("+---+---+---+---+---+---+---+");
  for (int i = 0; i < 6; i++){
   System.out.print("|");
   for (int j = 0; j < 7; j++){
    if (board[j][5-i] == 0){
     System.out.print("   |");
    }
    else{
     System.out.print(" "+ board[j][5-i]+" |");
    }
   }
   System.out.println();
  }
 }
 
 public void addDisk (int index, int player){
       this.board[index][this.available[index]] = player;
       this.available[index] ++; 
 }
 
 public boolean isWinning (int lastxColumnPlayed, int player){
   int c = lastxColumnPlayed;
   int r = available[c] - 1; 
   //check to see if there is a vertical connect4
   int j = 5;
     while(j >= 3){
     if(this.board[c][j] == player){
       if(this.board[c][j-1] == player && this.board[c][j-2] == player && this.board[c][j-3] == player){
         return true;
       }
     }
        j--;
   }
   //check to see if there is a horizontal connect4
    int i = 0;
     while(i < 4){
     if(this.board[i][r] == player && j < 4){
       if(this.board[i+1][r] == player && this.board[i+2][r] == player && this.board[i+3][r] == player){
         return true;
       }
     }
     i++;
   }
   //check to see if there is a downward diagonal connect4
   i = c; j = r;
   while(i < 6 && j > 0){
     i++; j--;
   }
   
   while(i > 2 && j < 3){
     if(this.board[i][j] == player){
       if(this.board[i-1][j+1] == player && this.board[i-2][j+2] == player && this.board[i-3][j+3] == player){
         return true; 
       } 
     } 
     i-- ; j ++;
     
   }
   //check to see if there is an upward diagonal connect4
   i = c; j = r;
   while(i > 0 && j > 0){
     i--; j--;
   }
   while(i < 4 && j < 3){
       if(this.board[i][j] == player){
         if(this.board[i+1][j+1] == player && this.board[i+2][j+2] == player && this.board[i+3][j+3] == player){
           return true;
         }
       } 
       i++ ; j++; 
     }
   return false; // DON'T FORGET TO CHANGE THE RETURN
 }
 
 public int canWinNextRound (int player){
   for(int k = 0; k < 7; k++){ 
     if(this.available[k] > 5){
       continue;
     }
     this.addDisk(k, player);
     if(this.isWinning(k, player) == true){
       this.board[k][this.available[k]-1] = 0;
       this.available[k]--;
       return k;
     }
     this.board[k][this.available[k]-1] = 0;
     this.available[k]--;
   }
   return -1; // DON'T FORGET TO CHANGE THE RETURN
 }
 
 public int canWinTwoTurns (int player){
   int other = 2;
   if(player == 2){
     other = 1;
   }
   /*
    * if  column k is full, skip to the next one, if it is not full, however, add a disk to in order to check the result 
    * We can check to see if we can win, having added another disk, and if we can and the other player cannot win, they will try to block us,
    * if they do block us, can we stil win, if yes, we return that column 
   */
   for(int k = 0; k < 7; k++){ 
     
     if(this.available[k] > 5){
       continue;
     }
     this.addDisk(k, player);
     
     int column = this.canWinNextRound(player);
     
     if(this.canWinNextRound(other) >= 0){
       this.board[k][this.available[k] -1] = 0;
       this.available[k]--;
       continue;
     }
     
     else if(column >= 0 && this.canWinNextRound(other) == -1){
       this.addDisk(column, other);
       int column2 = this.canWinNextRound(player);
       if(this.canWinNextRound(other) >= 0){
         this.board[column][this.available[column] -1] = 0;
         this.available[column]--;
         continue;
       }
       else if(column2 >= 0){
         this.board[k][this.available[k] -1] = 0;
         this.available[k]--;
         this.board[column][this.available[column] -1] = 0;
         this.available[column]--;
         return k;
       }
       this.board[column][this.available[column] -1] = 0;
       this.available[column]--;
     }
     this.board[k][this.available[k] -1] = 0;
     this.available[k]--;
   }
   
   return -1; // DON'T FORGET TO CHANGE THE RETURN
 }
 
}
