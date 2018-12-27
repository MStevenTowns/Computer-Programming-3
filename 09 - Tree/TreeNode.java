public class TreeNode<E>
{
    E value;
    TreeNode<E> left;
    TreeNode<E> right;
    
    public TreeNode(E value)
    {
        this.value=value;
        
    }
    
    public void add(E value)
    {
        Comparable a,b;
        try
        {
            a=(Comparable)value;
            b=(Comparable)this.value;
        }
        catch(Exception ClassCastException)
        {
            System.out.println("no");
            System.exit(0);
            a=5;
            b=5;
        }
        
        if(a.compareTo(b)>0)
        {
            if(right==null)right=new TreeNode<E>(value);
            else right.add(value);
        }
        else
        {
            if(left==null)left=new TreeNode<E>(value);
            else left.add(value);
        }
    }
    
    public int depth()
    {
        int leftDepth,rightDepth;
        if(left==null && right==null)return 0;
        else
        {
            leftDepth=left.depth();
            rightDepth=right.depth();
        }
        if(leftDepth>rightDepth) return leftDepth+1;
        else return rightDepth+1;
    }
    public String toString()
    {
        return ""+value;
    }
    
    public static void main(String[] args)
    {
        TreeNode<Integer> root=new TreeNode<Integer>(16);
        root.add(5);
    }
    
}






