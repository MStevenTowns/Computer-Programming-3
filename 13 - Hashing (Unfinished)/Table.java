import java.io.*;
import java.util.*;

class Table
{
    private String[][] tbl;
    private int size;
    
    public Table()
    {
        this.size=100;
        this.tbl=new String[size][2];
        for(int i=0;i<size;i++) 
        {
            tbl[i][0]="";
            tbl[i][1]="0";
        }
    }
    public Table(int size)
    {
        this.tbl=new String[size][2];
        for(int i=0;i<size;i++) 
        {
            tbl[i][0]="";
            tbl[i][1]="0";
        }
    }
    public void insert(String item)
    {
        int code=item.hashCode()%size;
        int hold;
        if(code<0) code*=-1;
        while(this.tbl[code][0]!=item&&this.tbl[code][0]!="")
        {
            hold=code.hashCode()%size;
        }
        if(this.tbl[code][0]!="") 
        {
            this.tbl[code][1]=""+Integer.parseInt(this.tbl[code][1])+1;
        }
        else 
        {
            this.tbl[code][0]=item;
            this.tbl[code][1]=1;
        }
        if(getLoadFactor()>.7)
        {
            this.size*=2;
            String[] tmp=new String[size];
            for(String s:tbl)
            {
                try
                {
                    if(s!="")
                    {
                        code=s.hashCode()%size;
                        if(code<0) code*=-1;
                        tmp[code]=s;
                    }
                }
                catch(Exception e)
                {
                }
            }
            this.tbl=tmp;
            System.out.println("Table Resized to hold "+size+"values");
        }
    }
    public double getLoadFactor()
    {
        int num=0;
        for(int i=0;i<size;i++)
        {
            if(tbl[i][0]!="")
            {
                num+=this.tbl[i][1];
            }
        }
        return num/size;
    }
    public int getSize()
    {
        return size;
    }
    public void setSize(int a)
    {
        this.size=a;
    }
}
