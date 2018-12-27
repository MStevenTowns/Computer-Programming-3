public class Cell
{
    //instance variable
	int a=0;//a-cord
	int b=0;//b-cord
    int c=0;//color
    double d=0;//distance
	
	//constructor
	public Cell()
	{
		this.a=0;
        this.b=0;
        this.c=0;
        this.d=0;
    }
    public Cell(int a,int b,int c,double d)
	{
		this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
	}
	public Cell(int a,int b,int c)
	{
		this.a=a;
        this.b=b;
        this.c=c;
	}
    public Cell(int a,int b)
	{
		this.a=a;
        this.b=b;
	}
    public Cell(int a)
    {
        this.a=a;
    }
	public Cell(Cell p)
	{
		this.a=p.a;
        this.b=p.b;
        this.c=p.c;
        this.d=p.d;
	}
    
    public int geta(){return this.a;}
    public int getb(){return this.b;}
    public int getc(){return this.c;}
    public double getd(){return this.d;}
    
    public Cell copy()
    {
        return new Cell(this.a,this.b,this.c,this.d);
    }
    public boolean equals3(Cell p)
    {
        return (p.a==this.a&&p.b==this.b&&p.c==this.c);
    }
    public boolean equals2(Cell p)
    {
        return (p.a==this.a&&p.b==this.b);
    }
    public boolean equals1(Cell p)
    {
        return (p.a==this.a);
    }
    public boolean equals(Cell p)
    {
        return (p.a==this.a&&p.b==this.b&&p.c==this.c&&p.d==this.d);
    }
    public boolean equals(int a,int b)
    {
        return (a==this.a&&b==this.b);
    }
	public String toString()
    {
        return ("("+this.a+","+this.b+","+this.c+","+this.d+")");
    }
}
