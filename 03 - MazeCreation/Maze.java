import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;
import java.awt.Color;

public class Maze
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        Cell dim = new Cell();//point to hold the dimensions of the list
        dim.x=dim.y=10;
        while(dim.x<0||dim.y<1)
        {
            System.out.print("Cells per row: ");
            dim.x=sc.nextInt();
            System.out.print("Cells per column: ");
            dim.y=sc.nextInt();
        }
        dim.x=2*dim.x+1;
        dim.y=2*dim.y+1;
        ArrayList<Cell> maze = new ArrayList<Cell>();
        Cell nextCell = new Cell();
        for(int y=0;y<dim.y;y++)
        {
            for(int x=0;x<dim.x;x++)
            {
                
                nextCell.x=x;
                nextCell.y=y;
                if((x%2==0)||(y%2==0))
                {
                    nextCell.z=0;
                }
                else nextCell.z=255;
                maze.add(nextCell.getLocation());//stores a copy of the point in the array as a seperate object
            }
        }
        sendToImage(0,maze,dim);
        
        ArrayList<Cell> done = new ArrayList<Cell>();
        makeMaze(maze,done,dim);
    }
    public static void makeMaze(ArrayList<Cell> maze,ArrayList<Cell> done, Cell dim)
    {
        int count=1;
        int counter=0;
       
        ArrayList<Cell> front = new ArrayList<Cell>();//Cells that can cut to new ones(frontline)
        ArrayList<Cell> borders;
        Cell currentCell;//this is to be used to refer to the point currently being inspected
        Cell nextCell;//this is to be used to refer to the point to be cleared to
        
        front.add(new Cell(1,1,1));//this puts the starting Cell in the front (x,y,color)0=black,1=white
        while(front.size()!=0)//while there are still things to check
        {
            int lastIndex=front.size()-1;
            currentCell=front.get(lastIndex);//get the last Cell to be changed
            borders=getBorders(currentCell,dim);
            while(borders.size()!=0)//while there are still paths to take from the current Cell
            {
                int index=(int)(borders.size()*Math.random());
                nextCell=borders.get(index);//borders[index] in python
                
                if (!inLists(front,done,nextCell))//if the next Cell is not in the front or in the completed lists
                {
                    System.out.println("checkpoint 1");
                    makePath(maze,currentCell, nextCell);
                    front.add(nextCell);
                    System.out.println("checkpoint 2");
                    counter++;
                }
                else//if it has been cleared to, remove it from the list of possibles(prevents loops)
                {
                    borders.remove(index);//remove cell at index
                    System.out.println("checkpoint 3");
                }
                System.out.println("border size"+borders.size());
            }
            
            //by the time it gets to this, it drops 1 pixel
            sendToImage(++count,maze,dim);
            //FIX IT!!!!!!
            
            System.out.println("checkpoint 4");
            //when all paths have been checked
            front.remove(lastIndex);
            done.add(currentCell);
            System.out.println("front size"+front.size());
        }
        System.out.println("paths made: "+counter);
        System.out.println("DONE");
    }
    
    public static ArrayList<Cell> getBorders(Cell currentCell,Cell dim)
    {
        ArrayList<Cell> borders = new ArrayList<Cell>();
        if (isValid(currentCell.x-2,currentCell.y,dim)) borders.add(new Cell(currentCell.x-2,currentCell.y));
        if (isValid(currentCell.x+2,currentCell.y,dim)) borders.add(new Cell(currentCell.x+2,currentCell.y));
        if (isValid(currentCell.x,currentCell.y-2,dim)) borders.add(new Cell(currentCell.x,currentCell.y-2));
        if (isValid(currentCell.x,currentCell.y+2,dim)) borders.add(new Cell(currentCell.x,currentCell.y+2));
        return borders;
    }
    public static boolean isValid(int x,int y,Cell dim)
    {
        return (x>0 && x<dim.x && y>0 && y<dim.y);
    }
    
    public static boolean inLists(ArrayList<Cell> front,ArrayList<Cell> done,Cell px)
    {
        System.out.println("inLists?");
        for(Cell c:front)
        {
            if (c.equals3(px)) {System.out.println("in front");return true;}
        }
        for(Cell c:done)
        {
            if (c.equals3(px)) {System.out.println("in done");return true;}
        }
        return false;
    }
    
    public static void makePath(ArrayList<Cell> maze,Cell start,Cell end)
    {
        int endX=end.x;
        int endY=end.y;
        int nextX=start.x;
        int nextY=start.y;
        if (nextX<endX) nextX++;
        else if (nextX>endX)nextX--;
        else if (nextY<endY)nextY++;
        else if (nextY>endY)nextY--;
        int index=nextX+nextY;
        maze.remove(index);
        maze.add((index),(new Cell(nextX,nextY,255)));
        System.out.println("made path\nx: "+nextX+" y: "+nextY);
    }
    
    
    
    
    public static void sendToImage(int num,ArrayList<Cell> maze,Cell dim)
    {
        BufferedImage img=new BufferedImage(dim.x,dim.y,BufferedImage.TYPE_INT_RGB);//create a black image
        for(Cell px:maze)
        {
            //img.setRGB(px.x,px.y,(new Color(px.z,px.z,px.z).getRGB()));//color at x,y is 0 or 255
            img.setRGB(px.x,px.y,(new Color(128,128,128).getRGB()));//color at x,y is 0 or 255
        }

        try{ImageIO.write(img, "png", new File("pics/Out"+num+".png"));}
        catch(IOException e){System.out.println("no Image");}
    }
}
