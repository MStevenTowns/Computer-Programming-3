/*
M. Steven Towns
TicTacToeDriver
1/15/2014
*/

import java.io.*;
import java.util.*;

public class TicTacToeDriver
{
    public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		TicTacToeGrid board;
		boolean go=true;
		int x,y,outcome;
		String run;
		outcome=0;
		while(go)
		{
			board = new TicTacToeGrid();
			
			while (!board.isWin("X") && !board.isWin("O"))
			{
				board.print();
				System.out.print("Row Number: ");
				x=sc.nextInt();
				System.out.print("Column Number: ");
				y = sc.nextInt();
				if(board.play(x-1,y-1, "X"))
				{
					if (board.isWin("X"));
					else board.computer();
				}
				board.print();
			}
			if (board.isWin("X")) outcome=1;
			if (board.isWin("O")) outcome=2;
			board.end(outcome);
			System.out.print("Play again?: ");
			run=sc.next();
			if (run.length()>0)
			{
				if (run.substring(0,1).equals("y")) go=true;
				else go=false;
			}	
			else go=false;
			
		}
		
    }
}
