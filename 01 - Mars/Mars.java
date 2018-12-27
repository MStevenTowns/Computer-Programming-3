import java.util.*;
import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;

/**Mars: A program for leveling a court on mars
* @author M. Steven Towns
* @version 0.5
*/

public class Mars
{
    /**Main driver for the program
     * @param map       holds the values of the terrain
     * @param t         object with the map of terrain
     * @param remainder tells how many tiles are left over after leveling the court
     * @param start     holds the cordinates for the start location of the court
     * @param section   holds the terrain for the section to become a court
     * 
     * 
     * 
     * */
	public static void main(String[] args)
	{
        int[][] map=textToArray();
        Terrain t;
        int remainder;
        int[] start={350,330};
        
        
        int[][] section=findSection(map,start);
        System.out.println("section");
        t=new Terrain(map,section);
        System.out.println("terrain");
        remainder=t.level();
        System.out.println("remainder: "+remainder);
        if (remainder!=0) t.finishLevel();
        System.out.println("finished");
        map=smash(map,section,350,330);
        System.out.println("smash");
        t.sendToImage();
//        t.sendToText();
        System.out.println("sent");
//        sur(map, 330,349);
	}
/**Smashes the section back into the terrain map
 * @param y     the y cordinate in the terrain
 * @param x     the x cordinate in the terrain
 * @return      the map object with the terrain
 */
    public static int[][] smash(int[][] map, int[][]section,int startx,int starty)
    {
        for(int y=starty;y<starty+64;y++)
        {
            for(int x=startx;x<startx+32;x++)
            {
                map[x][y]=section[x-startx][y-starty];
            }
        }
        return map;
    }
    /**
     * turns the text input into an array
     * @param row hold the row in text
     * @param num holds the individual numbers
     * @param sc scanns through the text file
     * @param map the map object holding the terrain
     * @param y y location in the terrain
     * @param x x location in the terrain
     * @return map the map of the terrain
     */
    public static int[][] textToArray()
    {
        String[] row;
        Scanner sc=new Scanner(System.in);
		int num;
        try 
        {
            sc=new Scanner(new File("input.txt"));
        }
        catch (FileNotFoundException e) 
        {
            System.exit(0);
        }
        
		int[][] map=new int[512][512];//holds all values for the terrain
        
        for(int y=0;y<512;y++)//loop through input and save them all as ints(row major).
        {
            row=sc.next().split(",");
            for(int x=0;x<row.length;x++)
            {
                num=Integer.parseInt(row[x]);
                map[x][y]=num;
            }
        }
        return map;
    }
    /**
     * pulls section out of map
     * @param startx    the start location for x
     * @param starty    the start location for y
     * @param endx      the end location for x
     * @param endy      the end location for y
     * @param section   the section of the map to be leveled
     * @return          the section of the map to be leveled
     * */
    public static int[][] findSection(int[][] map,int[] start)
    {
        int startx=start[0];
        int starty=start[1];
        int endx=startx+32;
        int endy=starty+64;
        int[][] section=new int[32][64];
        for(int y=start[1];y<endy;y++)
        {
            
            for(int x=start[0];x<endx;x++)
            {
                section[x-startx][y-starty]=map[x][y];
            }
        }
        return section;
    }
    /**prints the surrounding cordinates in map to a given point
     * used for error testing
     * @param map   stores the terrain being eddited
     * @param x     the x cordinate of the centerpoint
     * @param y     the y cordinate of the centerpoint
     * */
    public static void sur(int[][] map,int x,int y)
    {
        System.out.println("    "+map[x][y+1]);
        System.out.println(map[x-1][y]+" "+map[x][y]+" "+map[x+1][y]);
        System.out.println("    "+map[x][y-1]);
    }
}
