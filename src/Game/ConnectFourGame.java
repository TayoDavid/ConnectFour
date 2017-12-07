package Game;

import java.util.Scanner;

/** 
 * @author Omotayo
 *
 * This program is an exercise in the book "Introduction to programming, Comprehensive Version" by Daniel Liang.
 * 
 * Connect Four console program.
 */

public class ConnectFourGame {
	static Scanner input = new Scanner(System.in);
	static final int NUMBEROFROWS = 6;
	static final int NUMBEROFCOLUMN = 7;
	static int numberOfCells = 0;
	
	static char[][] board = new char[NUMBEROFROWS][NUMBEROFCOLUMN];
	
	static char firstPlayer = 'B';
	static char secondPlayer = 'R';
	private static char winner;
	
	public static void main(String[] args) {
		System.out.println("*******   Connect Four Game   *******");
		System.out.println("\n RULES\n Player 1, chooses a coordinate on both row and column to play colour Blue (B);"
				+ "\n Player 2, chooses a coordinate to play colour Red (R)."
				+ "\n\n	*******   Start Game   *******\n\n");
		
		printBoard();
		play();
		printBoard();
		if (winner != 0) {
			System.out.println("\nGame Over!\n");
			if (winner == 'B') {
				System.out.println("The Blue (B) player wins.");
			} else { 
				System.out.println("The Red (R) player wins.");
			}
		}
	}

	private static void printBoard() {
		for (int i = 0; i < board.length; i++) {
			System.out.print(" |");
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 0) {
					System.out.print("     |");
				} else {
					System.out.print("  " + board[i][j] + "  |");
				}
			}
			System.out.println();
		}
		System.out.println(" ⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺⎺");
		for (int k = 0; k < board[0].length; k++) 
			System.out.print("    " + k + " ");
		
		System.out.println();
	}

	private static void play() {
		boolean gameCheck;
		
		do {
			currentPlayer(firstPlayer);
			gameCheck = check(board);
			if (gameCheck) {
				winner = firstPlayer;
				continue;
			} else {
				System.out.println();
				printBoard();
			}
			
			currentPlayer(secondPlayer);
			gameCheck = check(board);
			if (gameCheck) {
				winner = secondPlayer;
			} else {
				System.out.println();
				printBoard();
			}
			
		} while (winner == 0);
	}

	private static void currentPlayer(char player) {
		
		if (numberOfCells >= 42) {
			System.out.println("\nGame Over!\n");
			System.out.println("The game is a draw.".toUpperCase());
			System.exit(-1);
		}
		
		System.out.print("\nPlayer " + player + "'s Turn, chose column to drop " + player + ": ");
		int col = input.nextInt(); 
		
		if (col > 6 || col < 0) {
			System.out.println("Invalid Location! Please try again.");
			currentPlayer(player);
		} 
		
		int row = board.length - 1;
		if (row >= 0) {
			if (board[0][col] != 0) {
				System.out.println("\nInvalid Location! Please try again.");
				currentPlayer(player);
			}
			
			while (board[row][col] != 0 && row > 0) {
				row--;
			}
			board[row][col] = player;
			numberOfCells++;
		} else {
			System.out.println("Invalid location! Please try again.");
		}		
	}

	/*
	 *  This method checks the four possible solutions.
	 */
	private static boolean check(char[][] board2) {
		boolean thereIsAWinner = false;
		
		// This loop checks the rows for a winner.
		boolean row = rowCheck();
		if (row) thereIsAWinner = true;
		
		// This loop checks the columns for a winner.
		boolean col = columnCheck();
		if (col) thereIsAWinner = true;
		
		// This will check the backward diagonal for a winner.
		boolean backDiag = backwardDiagonalCheck();
		if (backDiag) thereIsAWinner = true;
		
		// This will check the forward diagonal for a winner.
		boolean forDiag = forwardDiagonalCheck();
		if (forDiag) thereIsAWinner = true;
		
		return thereIsAWinner;
	}

	/*
	 *  This method checks the rows for a solution.
	 */
	public static boolean rowCheck() {
		boolean thereIsAWinner = false;
		for (int row = board.length - 1; row >= 0; row--) {
			int countB = 0;
			int countR = 0;
			
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] == 'B') {
					countB++;
				
					if (countR < 4) countR = 0;
					
				} else if (board[row][col] == 'R') {				
					countR++;
					
					if (countB < 4) countB = 0;
					
				} else 
					break; // That is, if nothing then break out of the loop and discontinue the count.
				
				if (countB == 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
				else if (countR == 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
			}

			countR = 0;
			countB = 0;	
		}
		
		return thereIsAWinner;
	}
	
	/*
	 *  This method checks the columns for a solution.
	 */
	public static boolean columnCheck() {
		boolean thereIsAWinner = false;
		
		for (int col = 0; col < board[0].length; col++) {
			
			int countB = 0;
			int countR = 0;
			
			for (int row = board.length - 1; row >= 0 ; row--) {
				if (board[row][col] == 'B') {
					countB++;
					
					if (countR < 4) countR = 0;
					
				} else if (board[row][col] == 'R') {
					countR++;
					
					if (countB < 4) countB = 0;
	
				} else 
					break;		// That is, if nothing then break out of the loop and discontinue the count.
				
				if (countB == 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
				else if (countR == 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
			}
			countR = 0;
			countB = 0;			
		}
		return thereIsAWinner;
	}

	/*
	 *  This method checks the backward diagonal for a solution.
	 */
	public static boolean backwardDiagonalCheck() {
		boolean thereIsAWinner = false;
	
		int backwardDiagonalCountForB = 0;
		int backwardDiagonalCountForR = 0;
		int diagonalRow;
		int diagonalColumn;
		
		for (int col = board[0].length - 1; col > 2; col--) {
			int row = board.length - 1;
			
			diagonalColumn = col;
			diagonalRow = row;
			
			/* 
			 * The first part will check diagonally like below.
			 * 
			 * 0   0,0  0,1 
			 * 
			 * 1   1,0  1,1  1,2
			 * 
			 * 2   2,0  2,1  2,2  2,3
			 * 	
			 * 3	    3,1  3,2  3,3  3,4	
			 * 	
			 * 4		     4,2  4,3  4,4  4,5
			 * 
			 * 5			      5,3  5,4  5,5  5,6
			 * 
			 * 		0	 1	  2	   3    4	 5	  6
			 * 
			 */
			
			while (diagonalColumn >= 0) {
				
				if (col == 6) {
					if (diagonalColumn == 0) {
						break;
					}
				}
			
				if (board[diagonalRow][diagonalColumn] == 0) {
					diagonalRow--;
					diagonalColumn--;
					continue; // That is, if nothing then break out of the loop and discontinue the count.
				} 
				if (board[diagonalRow][diagonalColumn] == 'B') {
					backwardDiagonalCountForB++;
					
					if (backwardDiagonalCountForR < 4) backwardDiagonalCountForR = 0;
					
				} else {
					backwardDiagonalCountForR++;
				
					if (backwardDiagonalCountForB < 4) backwardDiagonalCountForB = 0;
				}
							
				diagonalRow--;
				diagonalColumn--;
					
				if (backwardDiagonalCountForB >= 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
				else if (backwardDiagonalCountForR >= 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
			} 

			backwardDiagonalCountForB = 0;
			backwardDiagonalCountForR = 0;
		}
		
		/* 
		 * This other part will check the upper part like so.
		 * 
		 * 0   			 0,2  0,3	 
		 * 
		 * 1   				  1,3  1,4     
		 * 
		 * 2					   2,4  2,5  
		 * 	
		 * 3							3,5	 3,6
		 * 	
		 * 4								 4,6
		 * 
		 * 5			      
		 * 
		 * 		0	 1	  2	   3    4	 5	  6
		 */
		
		int upperRow = 4;
		int upperCountForB = 0;
		int upperCountForR = 0;
		
		while (upperRow > 2) {
			int row = upperRow;
			for (int upperColumn = board[0].length - 1; upperColumn > 1; upperColumn--) {
				
				if (upperRow == 3) {
					if (upperColumn < 3) break;
				}
				
				if (board[row][upperColumn] == 0) {
					row--;
					upperColumn--;
					continue; // That is, if nothing then break out of the loop and discontinue the count.
				} 
				if (board[row][upperColumn] == 'B') {
					upperCountForB++;
				
					if (upperCountForR < 4) upperCountForR = 0; 
					
				} else {
					upperCountForR++;
			
					if (upperCountForB < 4) upperCountForB = 0; 
				}
				
				row--;

				if (upperCountForB >= 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
				else if (upperCountForR >= 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
			}

			upperCountForB = 0;
			upperCountForR = 0;
			
			upperRow--;
		}
		return thereIsAWinner;
	}
	
	/*
	 *  This method checks the forward diagonal for a solution.
	 */
	public static boolean forwardDiagonalCheck() {
		boolean thereIsAWinner = false;
		
		int forwardDiagonalCountForB = 0;
		int forwardDiagonalCountForR = 0;
		int fDiagonalRow;
		int fDiagonalColumn;		
		
		for (int col = 0; col < 4; col++) {
			int row = board.length - 1;
		
			fDiagonalRow = row;
			fDiagonalColumn = col;
			
			while (fDiagonalColumn <= 6) {
				
				if (col == 0) {
					if (fDiagonalColumn == 6) {
						break;
					}
				}
			
				if (board[fDiagonalRow][fDiagonalColumn] == 0) {
					fDiagonalRow--;
					fDiagonalColumn++;
					continue; // That is, if nothing then break out of the loop and discontinue the count.
				} 
				if (board[fDiagonalRow][fDiagonalColumn] == 'B') {
					forwardDiagonalCountForB++;
					
					if (forwardDiagonalCountForR < 4) 
						forwardDiagonalCountForR = 0;
					
				} else {
					forwardDiagonalCountForR++;
					
					if (forwardDiagonalCountForB < 4)
						forwardDiagonalCountForB = 0;
				}
							
				fDiagonalRow--;
				fDiagonalColumn++;
					
				if (forwardDiagonalCountForB >= 4)  {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
				else if (forwardDiagonalCountForR >= 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
			} 
	
			forwardDiagonalCountForB = 0;
			forwardDiagonalCountForR = 0;
		}
		
		/*
		 * This part checks the upper forward diagonal.
		 */
		int upperRow = 4;
		int upperCountForB = 0;
		int upperCountForR = 0;
		
		while (upperRow > 2) {
			int row = upperRow;
			for (int upperColumn = 0; upperColumn < 5; upperColumn++) {
				
				if (upperRow == 3) {
					if (upperColumn > 3) break;
				}
				
				if (board[row][upperColumn] == 0) {
					row--;
					upperColumn++;
					continue; // That is, if nothing then break out of the loop and discontinue the count.
				} 
				if (board[row][upperColumn] == 'B') {
					upperCountForB++;
				
					if (upperCountForR < 4) upperCountForR = 0; 
					
				} else {
					upperCountForR++;
			
					if (upperCountForB < 4) upperCountForB = 0; 
				}
				
				row--;
	
				if (upperCountForB >= 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
				else if (upperCountForR >= 4) {
					thereIsAWinner = true;
					return thereIsAWinner;
				}
			}
	
			upperCountForB = 0;
			upperCountForR = 0;
			
			upperRow--;
		}
		return thereIsAWinner;
	}
}
