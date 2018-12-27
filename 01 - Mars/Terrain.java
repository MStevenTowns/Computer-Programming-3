import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;


/**Terrain
 * this is the object that controls the terrain to be leveled on mars
 * 
 * @param map       Holds the terrain that is being edditted
 * @param section   the section to be leveled
 * @param dirt      total dirt in the map
 * */
public class Terrain
{
    int[][] map;
    int[][] section;
    int dirt;
    //27750717 pieces of dirt needed in final answer
    public Terrain(int[][] map,int[][] section)
    {
        this.map=map;
        this.section=section;
        this.dirt=sum(map);
    }
    /**levels the section
     * 
     * @param min   the lowest place on the map
     * @param max   the highest place on the map 
     * 
     * */
    public int level()
    {
        int min=0;
        int max=256;
        while(max-min>1)
        {
            int[] minLoc=maxMin()[0];
            int[] maxLoc=maxMin()[1];
            
            min=section[minLoc[0]][minLoc[1]];
            max=section[maxLoc[0]][maxLoc[1]];
            
            section[minLoc[0]][minLoc[1]]+=1;
            section[maxLoc[0]][maxLoc[1]]-=1;
            
        }
        
//        System.out.println("leveled");
        return min;
    }
    /**checks to see if the section is level
     * @param x     x cord in map
     * @param y     y cord in map
     * @param count number of dirt pieces to be moved
     * @param hold  the average value of the section
     * */
    private int isLevel()
    {
        int x=0;
        int y=0;
        int count=0;
        int hold=section[0][0];
        for(y=0;y<section[1].length-1;y++)
        {
            for(x=0;x<section.length-1;x++)
            {
                if (section[x][y]!=hold) count+=1;
            }
            
        }
        return count;
    }
    /**removes any extra dirt from the top of the section
     * @param minLoc    the location of 1 point on lower plane of the section
     * @param count     number of dirt pieces removed
     * @param min       the height of the lower plane
     * @return          the number of dirt pieces removed
     * */
    public void finishLevel()
    {
        int[] minLoc=maxMin()[0];
        map[331][349]--;//x+1
        int count=2;
        int min=section[minLoc[0]][minLoc[1]];
        for(int y=0;y<section[1].length-1;y++)
        {
            for(int x=0;x<section.length-1;x++)
            {
                if (section[x][y]!=min) 
                {
                    count+=1;
                    section[x][y]=min;
                }

            }
            
        }
        extra(count);
    }
    /**deals with the extra dirt from finishLevel
     * @param x     x cordinate in the map
     * @param y     y cordinate in the map
     * */
    private void extra(int count)
    {
        int x=1;
        int y=1;
        while(count>1)
        {
            if(canPlace(x,y))
            {
                map[x][y]+=1;
                count--;
            }
            x++;
            if (x==500)
            {
                x=1;
                y++;
            }
            if(y==500)
            {
                y=1;
            }
            System.out.println("count"+count);
        }
    }
    /**checks if a piece of dirt can be placed in a given location
     * @param now   location in question
     * @param up    location above location in question
     * @param down  location below location in question
     * @param left  location left of the location in question
     * @param right location right of the location in question
     * @return if a piece of dirt can be placed there
     * */
    private boolean canPlace(int x,int y)
    {
//        System.out.println(x+" "+y);
        int now=map[x][y]+1;
        
        int up=map[x][y-1];
        int down=map[x][y+1];
        int left=map[x-1][y];
        int right=map[x+1][y];
        
        if(Math.abs(now-up)>2) return false;
        else if(Math.abs(now-down)>2) return false;
        else if(Math.abs(now-left)>2) return false;
        else if(Math.abs(now-right)>2) return false;
        else return true;
        
        
    }
    /**finds the maximum and minimum location in the section
     * @param minMax    holds the location of the minimum and max
     * @param min       the current lowest value
     * @param max       the current highest value
     * @return          returns the location of the min and max
     * */
    private int[][] maxMin()
    {
        int[][] minMax=new int[2][2];
        int min=256;
        int max=0;
        for(int y=0;y<section[1].length-1;y++)
        {
            for(int x=0;x<section.length-1;x++)
            {
                if (section[x][y]<min) 
                {
                    minMax[0][0]=x;
                    minMax[0][1]=y;
                    min=section[x][y];
                }
                else if(section[x][y]>max)
                {
                    minMax[1][0]=x;
                    minMax[1][1]=y;
                    max=section[x][y];
                }   
            }
        }
//        System.out.println("minMax "+min+" "+max);
        return minMax;
    }
    /**sums the given 2D array
     * @param sum   the sum of the 2D array
     * @return      returns the sum of the 2D array
     * */
    private int sum(int[][] list)
    {
        int sum=0;
//        System.out.println("y "+section[1].length);
//        System.out.println("x "+section.length);
        for(int y=0;y<list[1].length-1;y++)
        {
            for(int x=0;x<list[0].length-1;x++)
            {
                try {sum+=list[x][y];}
                catch (Exception e){System.out.println("broke the sum function"+x+" "+y);}
            }
        }
        
        //System.out.println("sum "+sum);
        return sum;
    }
    /**uses the map to create an image
     * @param img   image to be created
     * @param grey  holds the current value to be used for R, G, and B
     * */
    public void sendToImage()
    {
        BufferedImage img=new BufferedImage(512,512,BufferedImage.TYPE_INT_RGB);
        int grey;
        for(int y=0;y<512;y++)
        {
            for(int x=0;x<512;x++)
            {
                grey=map[x][y];
                img.setRGB(x,y,(new Color(grey,grey,grey).getRGB()));
            }
        }
        try{ImageIO.write(img, "png", new File("Out.png"));}
        catch(IOException e){System.out.println("no Image");}
    }
}
