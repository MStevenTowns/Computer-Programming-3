import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;
import java.awt.Color;

public class planned
{
    public static void main(String[] args)
    {
        int inX=0;
        int inY=0;
        int outX=2;
        int outY=2;
        
        BufferedImage img = null;
        Scanner sc=new Scanner(System.in);
        System.out.print("name of image: ");
        String name=sc.nextLine();
        System.out.println();
        if(name.equals("")) name="Map.png";
        try {img = ImageIO.read(new File(name));}
        catch (IOException e) 
        {
            System.out.println("No Image Found");
            System.exit(0);
        }
        Cell dim=new Cell(img.getWidth(),img.getHeight());
        
        ArrayList<Cell> closedSet=new ArrayList<Cell>();//found values from it, meaning already was a shortest path
        ArrayList<Cell> touched=new ArrayList<Cell>();//looked at, found 1 value, but not values from
        Cell[][] map=new Cell[dim.a][dim.b];//each cell is (x cord, y cord, cost to enter, cost from start)
        
        setMap(map,img);
        
        map[inX][inY].d=0;//it is free to step onto the first space
        boolean go=true;
        Cell current=null;
        int index;
        int count=0;
        int counter=0;
        double cost;
        touched.add(map[inX][inY]);
        
/**check to see if checking if it equals a finished value**/
        
    while(go)
        {
//            System.out.println("final: "+ map[outX][outY]);
//            System.out.println("length of list: "+touched.size());
//            System.out.println("this "+(++count)+" times");
            if(touched.size()>500*500) go=false;
            //if(touched.size()%500==0) System.out.println(touched.size()/100+"%");
            //if( touched.size()%((500.0*500)/100)==0) System.out.println(touched.size()/(500.0*500)/100);
            index=0;
            for(int i=0;i<touched.size();i++)
            {
//                System.out.println();
//                System.out.println("current: "+touched.get(index));
//                System.out.println("possible: "+touched.get(i));
                if(touched.get(index).d<touched.get(i).d) 
                {
//                    System.out.println("bigger");
                    index=i; //if current is smaller than prev smallest, make current the prev smallest
                }
            }
//            System.out.println("index: "+index);
            current=touched.get(index);
//            System.out.println("current"+current);
            if(current.a==outX&&current.b==outY)
            {
//                System.out.println("done");
                go=false;
            }
/*            if(current.equals(outX,outY)) 
            {
                System.out.println("done");
                go=false;//if the smallest of the touched is the out, then we are done
            }
            */
//            System.out.println("go: "+go);
           // else
            {
                getNeighborsY:
                for(int y=current.b-1;y<current.b+2;y++)
                {
                    getNeighborsX:
                    for(int x=current.a-1;x<current.a+2;x++)
                    {
//                        System.out.println("\ntesting if valid: "+ x+", "+y);
                        if(x<0||y<0||x>dim.a-1||y>dim.b-1) 
                        {
//                            System.out.println("not on map");
                            continue getNeighborsX; //not on the map
                        }
                        else if(inSet(touched,x,y)||inSet(closedSet,x,y)) 
                        {
//                            System.out.println("in a set");
                            continue getNeighborsX; //in one of the sets, doesn't need to be added
                        }
                        else if(current.c==-1)//if wall
                        {
//                            System.out.println("walll");
                            continue getNeighborsX;
                        }
                        else//if it is a valid cell
                        {
//                            System.out.println("we good");
                            //if(y==current.b&&x!=current.a) cost=Math.sqrt(2);//if on cross it cost sqrt(2) to access
                            //else cost=1;
                            cost=Math.sqrt(Math.abs(current.a-x)+Math.abs(current.b-y));
//                            System.out.println("cost: "+cost);
                            if((map[x][y].d==-1)||(current.d+(cost*map[x][y].c)<map[x][y].d))
                            {
                                map[x][y].d=current.d+(cost*map[x][y].c);//cost from start=
//                                System.out.println("cost for current "+map[x][y].d);
                            }
//                            System.out.println("added"+map[x][y]);
                            touched.add(map[x][y]);
                        }
                    }
                }
            }
            touched.remove(index);
        }
        System.out.println("cost from start to finish: "+map[outX][outY].d);
        sendToImage(map,dim);
    }
    public static boolean inSet(ArrayList<Cell> set,int x,int y)
    {
        for(Cell c: set)
        {
            if(c.equals(x,y)) return true;
        }
        return false;
    }
    public static void setMap(Cell[][] map, BufferedImage img)
    {
        Cell road=new Cell(128,128,128,1);
        Cell grass=new Cell(0,255,0,2);
        Cell water=new Cell(0,0,255,4);
        Cell next=new Cell();
        
        for(int y=0;y<img.getHeight();y++)
        {
            for(int x=0;x<img.getWidth();x++)
            {
                int rgb = img.getRGB(x,y);
                next.a = (rgb >> 16) & 0x000000FF;
                next.b = (rgb >>8 ) & 0x000000FF;
                next.c = (rgb) & 0x000000FF;
                if(next.a==128) //road
                {
                    next.a=x;
                    next.b=y;
                    next.c=1;
                    next.d=-1;
                }
                else if(next.b==255) //grass
                {
                    next.a=x;
                    next.b=y;
                    next.c=2;
                    next.d=-1;
                }
                else if(next.c==255) //water
                {
                    next.a=x;
                    next.b=y;
                    next.c=4;
                    next.d=-1;
                }
                else //wall
                {
                    next.a=x;
                    next.b=y;
                    next.c=-9;
                    next.d=-1;
                }
                map[x][y]=next.copy();
//                System.out.print(map[x][y].c);
            }
//        System.out.println();
            
        }
    }
    public static void sendToImage(Cell[][] map,Cell dim)
    {
        BufferedImage img=new BufferedImage(dim.a,dim.b,BufferedImage.TYPE_INT_RGB);//create a black image
        for(int y=0;y<dim.b;y++)
        {
            for(int x=0;x<dim.a;x++)
            {
                double z=map[x][y].d;
                if(z!=-1.0)img.setRGB(x,y,(new Color(0,0,0).getRGB()));//color at x,y is 0 or 255
                else img.setRGB(x,y,(new Color(255).getRGB()));//color at x,y is 0 or 255
            }
        }

        try{ImageIO.write(img, "png", new File("Solved.png"));}
        catch(IOException e){System.out.println("no Image");}
    }
}
