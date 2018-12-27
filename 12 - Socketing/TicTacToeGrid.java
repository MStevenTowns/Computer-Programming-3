import java.io.*;
import java.util.*;

class TicTacToeGrid {

	private int[][] board={{7,8,9},{4,5,6},{1,2,3}};
    
    boolean play(int x, int y, int choice)
	{
		if(x<0 || x>2 || y<0 || y>3) return false;
		if(board[x][y]<10) board[x][y] = choice;
		else return false;
		return true;
    }
    int[] convertChoice(int choice)
    {
        int x=0;
        int y=0;
        switch(choice)
        {
            case 1:
            {
                x=2;
                y=0;
                break;
            }
            case 2:
            {
                x=2;
                y=1;
                break;
            }
            case 3:
            {
                x=2;
                y=2;
                break;
            }
            case 4:
            {
                x=1;
                y=0;
                break;
            }
            case 5:
            {
                x=1;
                y=1;
                break;
            }
            case 6:
            {
                x=1;
                y=2;
                break;
            }
            case 7:
            {
                x=0;
                y=0;
                break;
            }
            case 8:
            {
                x=0;
                y=1;
                break;
            }
            case 9:
            {
                x=0;
                y=2;
                break;
            }
        }
        int[] out={x,y};
        return out;
    }
    boolean isValidChoice(int choice)
    {
        if(choice<1||choice>9) return false;
        int[] selection=convertChoice(choice);
        if(board[selection[0]][selection[1]]<10) return true;
        return false;
    }
    boolean isWin(int s)
    {
        for(int x=0;x<3;x++)
        {
            if(board[x][0]==s && board[x][1]==s && board[x][2]==s) 
            {
                return true;
            }
        }
        
        for(int y = 0; y < 3; y++)
        {
            if(board[0][y]==s && board[1][y]==s && board[2][y]==s) 
            {
                return true;
            }
        }
        
        if(board[0][0]==s && board[1][1]==s && board[2][2]==s) 
        {
            return true;
        }
        if(board[0][2]==s && board[1][1]==s && board[2][0]==s) 
        {
            return true;
        }
        return false;
    }
    
	public String print()
    {
        String send="";
		send+=("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j]==10) send+="O";
                else if(board[i][j]==11) send+="X";
                else send+=board[i][j];
                send+="  ";
            }
            send+=("\n\n");
        }
        return send;
    }
    
}
