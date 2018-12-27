import java.io.*;
import java.net.*;

public class ClientTest 
{
    public static void main(String[] args) throws IOException 
    {

        String serverHostname = new String ("127.0.0.1");

        if (args.length > 0) serverHostname = args[0];
        System.out.println ("Attemping to connect to host " +
		serverHostname + " on port 10007.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // echoSocket = new Socket("taranis", 7);
            echoSocket = new Socket(serverHostname, 3000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput;

        System.out.print ("input: ");
	while ((userInput = stdIn.readLine()) != null) {
	    out.println(userInput);
	    System.out.println("echo: " + in.readLine());
            System.out.print ("input: ");
	}

	out.close();
	in.close();
	stdIn.close();
	echoSocket.close();
    }
}



/*import java.io.*;
import java.net.*;
import java.util.*;

public class ClientTest {
    public static void main(String[] args) throws IOException 
    {
        final int PORT_NUMBER = 80;
        final String HOSTNAME = "10.32.61.173";
        Scanner sc=new Scanner(System.in);
        String test="";
        //Attempt to connect
        try 
        {
            Socket sock = new Socket(HOSTNAME, PORT_NUMBER);
                PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            //Output
            System.out.println("Connected");
            do
            {
                test=sc.nextLine();
                out.println(test);
                out.flush();
            }while(!test.equals("quit"));
            out.close();
            sock.close();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}*/
