import java.util.*;
import java.io.*;
public class p107
{
    public static void main(String []args)
    {
        int count=40;//elements per row/col
        int[][]map=getNums(count);
        int smallNum,smallIndex;
        smallNum=-1;
        smallIndex=-1;
        List<Integer> activeRows = new ArrayList<>();
        activeRows.add(0);//add element "A" to list(first row)
        for(int i=0;i<count;i++)
        {
            map[0][i]=-1;
        }
        //print(map);
        while(activeRows.size()<count)
        {
            for(int row:activeRows)
            {
                for(int col=0;col<count;col++)
                {
                    if(smallNum==-1||(map[row][col]>=0&&smallNum<map[row][col]))//if smallNum isn't set or current is less than smallNum
                    {
                        smallNum=map[row][col];
                        smallIndex=col;
                    }
                }
            }
            for(int row=0;row<count;row++)
            {
                map[row][smallIndex]=-1;
            }
            activeRows.add(smallIndex);
            smallIndex=smallNum=-1;
        }
        print(map);
    }
    
    public static int[][] getNums(int count)
    {
        int [][]map=new int[40][40];
        try
        {
            String[]hold=new String[count];
            Scanner sc=new Scanner(new File("p107_network.txt"));
            for(int i=0;i<count;i++)
            {
                hold=sc.nextLine().split(",");
                for(int j=0;j<count;j++)
                {
                    if(hold[j].equals("-"))map[i][j]=-1;
                    else map[i][j]=Integer.parseInt(hold[j]);
                }
            }
            return map;
        }
        catch(Exception FileNotFound)
        {
            System.out.println("file doesn't exist");
            System.exit(0);
        }
        return map;
    }
    
    public static void print(int[][] list)
    {
        for(int i=0;i<40;i++)
        {
            for(int j=0;j<40;j++)
            {
                System.out.print(list[i][j]+" ");
            }
            System.out.println();
        }
    }
}
