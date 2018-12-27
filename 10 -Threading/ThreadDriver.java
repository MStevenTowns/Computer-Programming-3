import java.util.*;
import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.Connection.Response;

public class ThreadDriver
{
    public static ArrayList<Thread> threads=new ArrayList<Thread>();
    
    public static void main(String[]args)
    {
        String page="https://reddit.com";
        int depth=2;
        
        System.out.println("Starting\nDepth: "+depth+"\n");
        ThreadedURL u=new ThreadedURL(page,depth);
        Thread t=new Thread(u);
        t.start();
        System.out.println("\n\n\n"+u.bad);
    }
    public static class ThreadedURL implements Runnable 
    {
        //private static Set visited = Collections.synchronizedSet(new HashSet(...)); //use to make thread safe
        //private static Set bad = Collections.synchronizedSet(new HashSet(...)); //use to make thread safe
        public  HashSet<String> visited=new HashSet<String>();
        public  HashSet<String> bad=new HashSet<String>();
        private  String[] extensions={".pdf",".sh",".bmp",".dib",".dcx",".gif",".im",".jpg",".jpe",".jpeg",".pcd",".pcx",".png",".pbm",".pgm",".ppm",".psd",".tif",".tiff",".xbm",".xpm"};
        private  Document doc;
        private  Response res;
        private  Elements links;
        private  PrintWriter fout;
        private  String page;
        private  int depth;
        
        public ThreadedURL(String page,int depth)
        {
            this.page=page;
            this.depth=depth;
        }
        
        public void run()
        {
            this.crawl();
        }
        
        public void crawl()
        {
            //System.out.println("searched: "+page);
            if(depth<0) 
            {
                threads.remove(Thread.currentThread());
                return;
            }
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
                        updateBad(page);
                        System.out.println("\n\nstill running:\n"+bad);
                    }
                }
                else fout.println(code);
                return;
            }
            catch(UnsupportedMimeTypeException e)
            {
                updateList(page);
            }
            catch(Exception e)
            {
                System.out.println("\n\npage:"+page+"\n"+res+"\n"+e+"\n\n");
            }
            Elements links = doc.select("a[href]");
            for(Element link:links)
            {
                page=link.attr("abs:href"); // "http://jsoup.org/"
                
                if(visited.contains(page)) continue;
                else updateList(page);
                //System.out.println("visited: "+visited);
                if(page.startsWith("javascript")||page.startsWith("mailto"))continue;
                int index=page.indexOf('?');
                if(index>=0) page=page.substring(0,index);
                index=page.indexOf('#');
                if(index>=0) page=page.substring(0,index);
                //if(page.endsWith("/")) page=page.substring(0,page.length()-1);
            
                if(page.length()>0) 
                {
                    Thread t=new Thread(new ThreadedURL(page,this.depth-1));
                    updateThreads(t);
                    t.start();
                }
            }
            
        }
        public synchronized void updateThreads(Thread t)
        {
            threads.add(t);
        }
        public synchronized void updateBad(String a)
        {
            if(!bad.contains(a))bad.add(a);
        }
        public synchronized void updateList(String a)
        {
            if(!bad.contains(a))visited.add(a);
        }
        
    }
}
