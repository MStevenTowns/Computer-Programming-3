import java.util.*;
import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.Connection.Response;

//to run
// add to compile: javac -cp /usr/share/java/jsoup/jsoup-1.7.2.jar "%f"
// add to execute: java -cp /usr/share/java/jsoup/jsoup-1.7.2.jar:. "%e"
// normal compile: javac "%f"
// normal execute: java "%e"

//org.jsoup.HttpStatusException: HTTP error fetching URL. Status=404
//org.jsoup.HttpStatusException: HTTP error fetching URL. Status=400

//public class URL implements Runnable 
public class URL
{
    //Set s = Collections.synchronizedSet(new HashSet(...)); use to make thread safe
    private static HashSet<String> visited=new HashSet<String>();
    private static HashSet<String> bad=new HashSet<String>();
    private static String[] extensions={".pdf",".sh",".bmp",".dib",".dcx",".gif",".im",".jpg",".jpe",".jpeg",".pcd",".pcx",".png",".pbm",".pgm",".ppm",".psd",".tif",".tiff",".xbm",".xpm"};
    private static Document doc;
    private static Response res;
    private static PrintWriter fout;
    /*public void run()
    {
        System.out.printf("Hello From #%d Thread!\n",Thread.currentThread().getId());
        //bad.add((int)Thread.currentThread().getId());
        
    }
    
    public static synchronized void updateList(String a)
    {
        if(!bad.contains(a))bad.add(a);
    }*/
    
    public static void main(String args[])
    {
        try {
            fout = new PrintWriter(new FileWriter("out.txt"), true);
        }
        catch(Exception e)
        {
            System.exit(0);
        }
        //crawl("http://www.reddit.com/r/darkweb",2);
        String page="http://google.com";
        int depth=6;
        //4: 4523
        //
        
        System.out.println("Starting\nDepth: "+depth+"\n");
        crawl(page,depth);
        System.out.println("\n\n\n"+bad);
        System.out.println(visited.size()+" links visited");
        /*ArrayList<Thread> threads=new ArrayList<Thread>();
        threads.add(new Thread(new URL()));*/
    }
    public static void crawl(String page, int depth)
    {
        
        //System.out.println("searched: "+page);
        if(depth<0) return;
        try
        {
            res = Jsoup.connect(page)
            .userAgent("Mozilla/5.0 (X11; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0")
            .referrer("http://compsci.asmsa.org/townss15")
            .timeout(10000)
            .execute();
            doc=res.parse();
        }
        catch(HttpStatusException e)
        {
            //System.out.println(res);
            int code=e.getStatusCode();
            if(code==404||code==403||code==400||code==204)
            {
                if(!bad.contains(page))
                {
                    bad.add(page);
                    System.out.println("\n\nstill running:\n"+bad);
                    System.out.println(visited.size()+" links visited");
                }
            }
            else fout.println(code);
            return;
        }
        catch(Exception e)
        {
            fout.println("\n\npage:"+page+"\n"+res+"\n"+e+"\n\n");
        }
        Elements links = doc.select("a[href]");
        for(Element link:links)
        {
            page=link.attr("abs:href"); // "http://jsoup.org/"
            
            if(visited.contains(page)) continue;
            else visited.add(page);
            //System.out.println("visited: "+visited);
            if(page.startsWith("javascript")||page.startsWith("mailto"))continue;
            int index=page.indexOf('?');
            if(index>=0) page=page.substring(0,index);
            index=page.indexOf('#');
            if(index>=0) page=page.substring(0,index);
            //if(page.endsWith("/")) page=page.substring(0,page.length()-1);
        
            if(page.length()>0) 
            {
                    crawl(page,depth-1);
            }
        }
    }
}
