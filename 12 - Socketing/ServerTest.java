import java.net.*; 
import java.io.*; 

public class ServerTest 
{ 
 public static void main(String[] args) throws IOException 
   { 
    ServerSocket serverSocket = null; 

    try { 
         serverSocket = new ServerSocket(3000); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10007."); 
         System.exit(1); 
        } 

    Socket clientSocket = null; 
    System.out.println ("Waiting for connection.....");

    try { 
         clientSocket = serverSocket.accept(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Accept failed."); 
         System.exit(1); 
        } 

    System.out.println ("Connection successful");
    System.out.println ("Waiting for input.....");

    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
                                      true); 
    BufferedReader in = new BufferedReader( 
            new InputStreamReader( clientSocket.getInputStream())); 

    String inputLine; 

    while ((inputLine = in.readLine()) != null) 
        { 
         System.out.println ("Server: " + inputLine); 
         out.println(inputLine); 

         if (inputLine.equals("Bye.")) 
             break; 
        } 

    out.close(); 
    in.close(); 
    clientSocket.close(); 
    serverSocket.close(); 
   } 
} 


/*import java.io.*;
import java.net.*;

public class ServerTest {
    public static void main(String[] args) {
        final int PORT_NUMBER = 80;
        while(true) 
        {
            try 
            {
                //Listen on port
                ServerSocket serverSock = new ServerSocket(PORT_NUMBER);
                System.out.println("Listening...");

                //Get connection
                Socket clientSock = serverSock.accept();
                System.out.println("Connected client");

                //Get input
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                String test="";
                do{
                    test=br.readLine();
                    System.out.println(test);
                }while(!test.equals("quit"))
                br.close();
                serverSock.close();
                clientSock.close();
            } 
            catch(Exception e) 
            {
                //e.printStackTrace();
                System.out.println(e);
            }
        }
    }
}

/*import java.net.*;
import java.io.*;
public class SocketTest
{
    
    public static void main(String args[])
    {
        String hostName = "10.32.61.173"; 
        //args[0];
        int portNumber =80; 
        //Integer.parseInt(args[1]);

        try 
        {
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out =new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) 
            {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        

    }
}*/

