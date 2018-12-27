public class Cell
{
    //instance variable
	int x=0;
	int y=0;
    int z=0;
    int a=0;
	
	//constructor
	public Cell()
	{
		this.x=0;
        this.y=0;
        this.z=0;
	}
	public Cell(int x,int y,int z)
	{
		this.x=x;
        this.y=y;
        this.z=z;
	}
    public Cell(int x,int y)
	{
		this.x=x;
        this.y=y;
	}
    public Cell(int x)
    {
        this.x=x;
    }
	public Cell(Cell p)
	{
		this.x=p.x;
        this.y=p.y;
        this.z=p.z;
	}
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public Cell getLocation()
    {
        return new Cell(x,y,z);
    }
    public boolean equals3(Cell p)
    {
        return (p.x==this.x&&p.y==this.y&&p.z==this.z);
    }
    public boolean equals2(Cell p)
    {
        return (p.x==this.x&&p.y==this.y);
    }
    public boolean equals1(Cell p)
    {
        return (p.x==this.x);
    }
	public String toString()
    {
        return ("("+this.x+","+this.y+","+this.z+")");
    }
}
