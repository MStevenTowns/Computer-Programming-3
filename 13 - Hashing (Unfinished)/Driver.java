import java.io.*;
import java.util.*;

public class Driver
{
    public static void main(String[]args)
    {
        Scanner sc=new Scanner(System.in);
        Scanner str=new Scanner(System.in);
        boolean go=true;
        Table t=new Table();
        while(go)
        {
            System.out.println("1: Insert");
            System.out.println("2: Change Size");
            System.out.println("3: Get Load Factor");
            System.out.println("4: Get Size");
            System.out.println("5: Restart");
            System.out.println("6: Exit");
            System.out.print("What do you want to do?: ");
            int choice=sc.nextInt();
            switch(choice)
            {
                case 1:
                    System.out.print("Enter your string: ");
                    t.insert(str.nextLine());
                    break;
                case 2:
                    System.out.print("What do you want to change the size to?: ");
                    t.setSize(sc.nextInt());
                    break;
                case 3:
                    System.out.println(t.getLoadFactor());
                    break;
                case 4:
                    System.out.println(t.getSize());
                    break;
                case 5:
                    t=new Table();
                    break;
                case 6:
                    go=false;
                    break;
            }
        }
    }
}
