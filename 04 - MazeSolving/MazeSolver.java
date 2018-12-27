import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;
import java.awt.Color;

public class MazeSolver
{
    public static void main(String[] args)
    {
        Cell nextCell;
        
        BufferedImage img = null;
        Scanner sc=new Scanner(System.in);
        System.out.print("name of image: ");
        String name=sc.nextLine();
        if(name.equals("")) name="Maze.png";
        try {img = ImageIO.read(new File(name));}
        catch (IOException e) {}
        Cell dim=new Cell(img.getWidth(),img.getHeight());
        int[][]maze=new int[dim.x][dim.y];
        for(int y=0;y<img.getHeight();y++)
        {
            for(int x=0;x<img.getWidth();x++)
            {
                int red = (img.getRGB(x,y) >> 16) & 0x000000FF;
                if(red<128) red=0;
                if(red>128) red=255;
                maze[x][y]=red;
            }
        }
        long startTime=System.nanoTime();
        solveMaze(maze,dim);
        long endTime=System.nanoTime();
        long time=(endTime-startTime);
        System.out.println("solution: "+time+" Nanoseconds.");
        
        sendToImage(maze,dim);
    }
    public static void solveMaze(int[][] maze,Cell dim)
    {
        int count=0;
        
        int dist=0;
        int index=0;
        int out=0;
        int endX=dim.x-2;
        int endY=dim.y-2;
        
        ArrayList<Cell> borders;
        ArrayList<Cell> front=new ArrayList<Cell>();
        ArrayList<Cell> done=new ArrayList<Cell>();
        
        Cell currentCell=new Cell(1,1,255);
        front.add(currentCell);
        while(front.size()!=0&&out==0)
        {
            index=front.size()-1;
            currentCell=front.get(index);
            borders=getBorders(maze,front,done,currentCell,dim);
            dist++;//1 for each unit from start
            for(Cell c:borders)
            {
                c.d=dist;
                if(c.equals(endX,endY)) {done.add(c);out=done.size();}
                else front.add(c);
            }
            front.remove(index);
            done.add(currentCell);
        }
        System.out.println("solved");
        currentCell=done.get(out-1);
        System.out.println("current cell: "+currentCell);
        ArrayList<Cell> previous=new ArrayList<Cell>();
        
        ArrayList<Cell> rem=new ArrayList<Cell>();
        for(Cell c:done)
        {
            for(Cell d:done)
            {
                if (c.d==d.d&&!c.equals(d))
                {
                    rem.add(c);
                }
            }
        }
        for(Cell c:rem)
        {
            done.remove(c);
        }
        for(Cell c:done)
        {
            maze[c.x][c.y]=128;
        }
        maze[1][1]=128;//1,1 is always going to be part of the solution
    }
    public static void printList(Cell[] list)
    {        
        System.out.println("\n\n\n");
        for(Cell c:list)
        {
            System.out.println(c);
        }
        System.out.println("\n\n\n");
    }
    public static void printList(ArrayList<Cell> list)
    {
        for(Cell c:list)
        {
            System.out.println(c);
        }
        System.out.println();
    }
    public static Cell getNext(Cell c,ArrayList<Cell> done)
    {
        for(Cell x:done)
        {
            if(x.d==c.d-1)c=x;
        }
        return c;
    }
    public static ArrayList<Cell> getBorders(int[][] maze,ArrayList<Cell> front,ArrayList<Cell> done,Cell currentCell,Cell dim)
    {
        ArrayList<Cell> borders = new ArrayList<Cell>();
        if ((maze[currentCell.x-1][currentCell.y]>128)&&(!inList(front,done,currentCell.x-2,currentCell.y))) borders.add(new Cell(currentCell.x-2,currentCell.y,255));
        if ((maze[currentCell.x+1][currentCell.y]>128)&&(!inList(front,done,currentCell.x+2,currentCell.y))) borders.add(new Cell(currentCell.x+2,currentCell.y,255));
        if ((maze[currentCell.x][currentCell.y-1]>128)&&(!inList(front,done,currentCell.x,currentCell.y-2))) borders.add(new Cell(currentCell.x,currentCell.y-2,255));
        if ((maze[currentCell.x][currentCell.y+1]>128)&&(!inList(front,done,currentCell.x,currentCell.y+2))) borders.add(new Cell(currentCell.x,currentCell.y+2,255));
        return borders;
    }
    public static boolean inList(ArrayList<Cell> front,ArrayList<Cell> done,int x,int y)
    {
        Cell c=new Cell(x,y);
        for(Cell e:front) if(e.equals2(c)) return true;
        for(Cell e:done) if(e.equals2(c)) return true;
        return false;
    }
    public static void sendToImage(int[][] maze,Cell dim)
    {
        for(int y=0;y<dim.y;y++)
        {
            for(int x=0;x<dim.x;x++)
            {
                if(x<dim.x-2&&maze[x][y]==128&&maze[x+2][y]==128&&maze[x+1][y]==255)maze[x+1][y]=128;//right
                if(x>2&&maze[x][y]==128&&maze[x-2][y]==128&&maze[x-1][y]==255)maze[x-1][y]=128;//left
                if(y<dim.y-2&&maze[x][y]==128&&maze[x][y+2]==128&&maze[x][y+1]==255)maze[x][y+1]=128;//down
                if(y>2&&maze[x][y]==128&&maze[x][y-2]==128&&maze[x][y-1]==255)maze[x][y-1]=128;//up
            }
        }
        BufferedImage img=new BufferedImage(dim.x,dim.y,BufferedImage.TYPE_INT_RGB);//create a black image
        for(int y=0;y<dim.y;y++)
        {
            for(int x=0;x<dim.x;x++)
            {
                int z=maze[x][y];
                if(z!=0&&z!=255)img.setRGB(x,y,(new Color(z,0,0).getRGB()));//color at x,y is 0 or 255
                else img.setRGB(x,y,(new Color(z,z,z).getRGB()));//color at x,y is 0 or 255
            }
        }

        try{ImageIO.write(img, "png", new File("Solved.png"));}
        catch(IOException e){System.out.println("no Image");}
    }
}
