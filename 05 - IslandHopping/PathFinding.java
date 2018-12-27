import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;
import java.awt.Color;

public class PathFinding
{
    public static void main(String[] args)
    {
        int inX=0;
        int inY=0;
        int outX=255;
        int outY=255;
        
        BufferedImage img = null;
        Scanner sc=new Scanner(System.in);
        System.out.print("name of image: ");
        String name=sc.nextLine();
        if(name.equals("")) name="Map.png";
        try {img = ImageIO.read(new File(name));}
        catch (IOException e) 
        {
            System.out.println("No Image Found");
            System.exit(0);
        }
        Cell dim=new Cell(img.getWidth(),img.getHeight());
        Cell next=new Cell();
        
        Cell road=new Cell(128,128,128,1);
        Cell grass=new Cell(0,255,0,2);
        Cell water=new Cell(0,0,255,4);
        
        Cell[][] map=new Cell[dim.a][dim.b];//each cell is r,g,b, total value needed to step on
        for(int y=0;y<dim.b;y++)
        {
            for(int x=0;x<dim.a;x++)
            {
                int rgb = img.getRGB(x,y);
                next.a = (rgb >> 16) & 0x000000FF;
                next.b = (rgb >>8 ) & 0x000000FF;
                next.c = (rgb) & 0x000000FF;
                if(next.equals3(road))next=road.copy();
                if(next.equals3(grass))next=grass.copy();
                if(next.equals3(water))next=water.copy();
                else next.d= (-1);
                map[x][y]=next;
            }
        }
        Cell[][] distMap=new Cell[dim.a][dim.b];
        for(int y=0;y<dim.b;y++)
        {
            for(int x=0;x<dim.a;x++)
            {
                distMap[x][y]=map[x][y];
                distMap[x][y].d=-2;//not assigned
            }
        }
        //when go to find value to place, just do cell.d+=commingfrom.d
        
        
        //all hold xand y location in map
        ArrayList<Cell> closedSet=new ArrayList<Cell>();
        ArrayList<Cell> openSet=new ArrayList<Cell>();
        ArrayList<Cell> cameFrom=new ArrayList<Cell>();
        
        openSet.add(new Cell(inX,inY));
        Cell current=null;
        System.out.println("test: "+openSet.remove(current));
        int cost=0;
        int terrain;
        while(!openSet.isEmpty())
        {
            System.out.println(openSet.size());
            for(Cell c:openSet) 
            {
                if(current==null||distMap[c.a][c.b].d<distMap[current.a][current.b].d) 
                {
                    current=c;
                }
            }
            for(int y=current.b-1;y<=current.b+1;y++)
            {
                for(int x=current.a-1;x<=current.a+1;x++)
                {
                    if(y<0||x<0||(x==current.a&&y==current.b)) 
                    {
                        System.out.println("out of bounds or current");
                        continue;//if out of bounds or the current cell
                    }
                    else if(map[x][y].a==0&&map[x][y].b==0&&map[x][y].c==0)//if wall
                    {
//                        System.out.println("wall");
                        distMap[x][y].d=-1;
                        closedSet.add(distMap[x][y]);
                        continue;
                    }
                    else if(closedSet.contains(distMap[x][y])) 
                    {
//                        System.out.println("done before");
                        continue;//already found shortest path to
                    }
                    else if(openSet.contains(distMap[x][y])) continue;
                    else
                    {
//                        System.out.println("do crap");
                        if (map[x][y].a==road.a)terrain=1;
                        else if (map[x][y].b==grass.b)terrain=2;
                        else if (map[x][y].c==water.c)terrain=4;
                        else terrain=-1;
                        if(distMap[x][y].d==-2||distMap[x][y].d>current.d+terrain) 
                        {
                            if(terrain==-1) System.out.println("fuck");//went over wall
                            distMap[x][y].d=distMap[current.a][current.b].d+terrain;
                        }
                        openSet.add(new Cell(x,y));
                    }
                }
            }
            closedSet.add(current);
            System.out.println("removed: "+openSet.remove(current)+" "+current);
            current=null;
        }
        System.out.println("done?");
        
        /*for(int y=0;y<dim.b;y++)
        {
            for(int x=0;x<dim.a;x++)
            {
                System.out.print(distMap[x][y].d+" ");
            }
            System.out.println();
        }*/
        
    }
    /*
    public static void getNeighbors(Cell[][] map,Cell[][] neighbors,int cX,int cY)//dont call, put in main
    {
        for(int y=currentY-1;y<currentY+1;y++)
        {
            for(int x=currentX-1;x<currentY+1;x++)
            {
                if(y<0||x<0||(x==currentY&&y==currentY)) continue;//if out of bounds or the current cell
                
            }
        }
    }
    */
}
