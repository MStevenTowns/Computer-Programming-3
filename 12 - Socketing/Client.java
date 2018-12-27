import java.io.*;
import java.util.*;
import java.net.*;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        Socket sock = new Socket(args[0], 3000);
        // reading from keyboard (keyRead object)
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc=new Scanner(System.in);
        // sending to client (pwrite object)
        OutputStream ostream = sock.getOutputStream(); 
        PrintWriter pwrite = new PrintWriter(ostream, true);

        // receiving from server ( receiveRead  object)
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

        String receiveMessage, sendMessage;     
        String in=null;
        while(true)
        {
			in=null;
            System.out.println("Oponent's move");
            while(in==null)
            {
				in=receiveRead.readLine();
			}
			System.out.println("Your move");
			System.out.println(in);
			for(int i=0;i<6;i++)
			{
				in=receiveRead.readLine();
				if(in !=null)System.out.println(in); // displaying at DOS prompt
			}
            
            int choice=Integer.parseInt(keyRead.readLine());
            pwrite.println(choice);// sending to server
            pwrite.flush();// flush the data
                    
        }               
    }                    
}
