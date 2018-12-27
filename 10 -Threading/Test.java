import java.util.*;
/*
public class Test
{
    public static void main(String[]args)
    {
        for(int i=0;i<100;i++)
        {
            System.out.print("      \r"+i);
            try{Thread.sleep(100);}
            catch(Exception e){System.out.println("no");}
        }
    }
}
*/

public class Test implements Runnable 
{
    public static ArrayList<Integer> list;
    
    public void run()
    {
        System.out.printf("Hello From #%d Thread!\n",Thread.currentThread().getId());
        //list.add((int)Thread.currentThread().getId());
        sleeper();
    }
    public static synchronized void sleeper()
    {
        System.out.printf("#%d thread is going to sleep\n",Thread.currentThread().getId());
        try{Thread.sleep(5000);}
        catch(Exception e){System.out.println("no");}
        System.out.printf("#%d thread is awake\n",Thread.currentThread().getId());
    }
    public static void main(String args[])
    {
        list=new ArrayList<Integer>();
        ArrayList<Thread> threads=new ArrayList<Thread>();
        for(int i=0;i<20;i++)
        {
            threads.add(new Thread(new Test()));
            threads.get(i).start();
        }
        try
        {
            for(int i=0;i<20;i++)
            {
                threads.get(i).join();
            }
        }
        catch(Exception e){System.out.println("no");}
        System.out.println("Hello From Main Thread!");
        System.out.println(list);
    }
}
