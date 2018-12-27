public class Hanoi
{
    public static void main(String[] args)
    {
        playHanoi(4,"1","2","3");
    }
    private static void playHanoi(int n, String startPeg, String other, String endPeg)
    {
        if(n==0) return;
        else
        {
            playHanoi(n-1,startPeg,endPeg,other);
            System.out.printf("Move disk %s from peg %s to peg %s \n",n,startPeg,endPeg);
            playHanoi(n-1,other,startPeg,endPeg);
        }
    }
}
