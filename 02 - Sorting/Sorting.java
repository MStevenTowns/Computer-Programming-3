import java.util.Scanner;

//due friday

public class Sorting
{
    public static long compares;
    public static long swaps;
    //i know, i am a piece of crap for this
    
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		long startTime,endTime,time;
		int x;
        boolean canPrint;
        while(true)
        {
            System.out.print("List Size: ");
            x=sc.nextInt();
            if(x<=20) canPrint=true;
            else canPrint=false;
            int[] list=new int[x];
            list=makeList(list);
            
    
            /*
             * 
            System.out.println("\nBubbling:");
            list=setList(list);
            if(canPrint)print(list);
            swaps=0;
            compares=0;
            
            startTime=System.nanoTime();
            bubble(list);
            endTime=System.nanoTime();
            time=(endTime-startTime);
            System.out.println("Bubble Sort: "+time+" Nanoseconds.");
            System.out.println("Compares: "+compares);
            System.out.println("Swaps: "+swaps);
            if(canPrint)print(list);
            
            
            System.out.println("\nSelecting:");
            list=setList(list);
            if(canPrint)print(list);
            swaps=0;
            compares=0;
            
            startTime=System.nanoTime();
            selection(list);
            endTime=System.nanoTime();
            time=(endTime-startTime);
            System.out.println("Selection Sort: "+time+" Nanoseconds.");
            System.out.println("Compares: "+compares);
            System.out.println("Swaps: "+swaps);
            if(canPrint)print(list);
            
            
            System.out.println("\nInserting:");
            list=setList(list);
            if(canPrint)print(list);
            swaps=0;
            compares=0;
            
            startTime=System.nanoTime();
            insertion(list);
            endTime=System.nanoTime();
            time=(endTime-startTime);
            System.out.println("Insertion Sort: "+time+" Nanoseconds.");
            System.out.println("Compares: "+compares);
            System.out.println("Swaps: "+swaps);
            if(canPrint)print(list);
            
            
            System.out.println("\nMerging:");
            list=setList(list);
            if(canPrint)print(list);
            swaps=0;
            compares=0;
            
            startTime=System.nanoTime();
            list=mergeSort(list);
            endTime=System.nanoTime();
            time=(endTime-startTime);
            System.out.println("Merge Sort: "+time+" Nanoseconds.");
            System.out.println("Compares: "+compares);//need to set
            System.out.println("Swaps: "+swaps);//need to set
            if(canPrint)print(list);
            
            
            System.out.println("\nQuicking:");
            list=setList(list);
            if(canPrint)print(list);
            swaps=0;
            compares=0;
            
            startTime=System.nanoTime();
            quickSort(list,0,list.length-1);
            endTime=System.nanoTime();
            time=(endTime-startTime);
            System.out.println("Quick Sort: "+time+" Nanoseconds.");
            System.out.println("Compares: "+compares);//need to set
            System.out.println("Swaps: "+swaps);//need to set
            if(canPrint)print(list);
            
            */
            
            System.out.println("\nEsotericing:");
            list=setList(list);
            if(canPrint)print(list);
            swaps=0;
            compares=0;
            
            startTime=System.nanoTime();
            list=esoteric(list);
            endTime=System.nanoTime();
            time=(endTime-startTime);
            System.out.println("Esoteric Sort: "+time+" Nanoseconds.");
            System.out.println("Compares: "+compares);//need to set
            System.out.println("Swaps: "+swaps);//need to set
            print(list);
            
            
            System.out.println("\n\n\n---------------------------------------------------\n\n\n");
		}
	}
    public static int[] esoteric(int[] list)
    {
        int[] hold=new int[list.length];
        int count=-1;
        int i=0;
        if(list.length<3) 
        {
            swaps++;
            compares++;
            int[] out={list[0]};
            return out;
        }
        while(list[i]>list[i+1])
        {
            compares++;
            i++;
        }
        while(list[i]<list[i+1]) 
        {
            compares++;
            count++;
            i++;
            hold[count]=list[i];
            swaps++;
        }
        int[] out=new int[count+1];
        while(count>=0)
        {
            compares++;
            swaps++;
            out[count]=hold[count];
            count--;
        }
        compares+=3;
        return out;
    }
    public static int partition(int list[], int first, int last)
    {
        int pivot = list[first];
        while (first < last)
        {
            compares++;
            swaps++;
            while (list[first] < pivot) 
            {
                compares++;
                first++;
            }
            while (list[last] > pivot) 
            {
                compares++;
                last--;
            }
            int hold=list[first];
            list[first]=list[last];
            list[last]=hold;
        }
        return first;
    }

    public static void quickSort(int list[], int first, int last)
    {
        compares++;
        if (first >= last) return;
        int index = partition(list, first, last);
        quickSort(list,first, index);
        quickSort(list,index+1, last);
    }
    
    public static int[] mergeSort(int[] list)
    {
        if(list.length==1) return list;
        int[] left=new int[list.length/2];//5
        int[] right=new int[list.length-list.length/2]; //10-5
        int i;
        for(i=0;i<list.length/2;i++)
        {
            left[i]=list[i];
            swaps++;
        }
        for(i=i;i<list.length;i++)
        {
            right[i-left.length]=list[i];
            swaps++;
        }
        left=mergeSort(left);
        right=mergeSort(right);
        return zip(left,right);
    }
    public static int[] zip(int[] list1,int[] list2)
    {
        int slot1=0;
        int slot2=0;
        int place=0;
        int len=list1.length+list2.length;
        int[] out=new int[len];
        while(slot1<list1.length&&slot2<list2.length)
        {
            if (list1[slot1]<list2[slot2])
            {
                out[place]=list1[slot1];
                slot1++;
                place++;
            }
            else
            {
                out[place]=list2[slot2];
                slot2++;
                place++;
            }
            compares++;
            swaps++;
        }
        for(slot1=slot1;slot1<list1.length;slot1++)
        {
            out[place]=list1[slot1];
            place++;
            swaps++;
        }
        for(slot2=slot2;slot2<list2.length;slot2++)
        {
            out[place]=list2[slot2];
            place++;
            swaps++;
        }
        return out;
    }
    
    
	public static void insertion(int[] list)
	{
		int num,slot;
		for(int i=1;i<list.length;i++)//check each number
		{
			num=list[i];
			slot=i-1;
			while(slot>=0&&list[slot]>num)
			{
				list[slot+1]=list[slot];
				slot--;
                compares++;
                swaps++;
			}
			list[slot+1]=num;
            compares++;
		}
        return;
	}
	public static void selection(int[] list)
	{
		int min,hold;
		int count;
		for(int i=0;i<list.length;i++)
		{
			count=0;
			min=i;
			for(int j=i+1;j<list.length;j++)
			{
				if(list[j]<list[min])
				{
					min=j;
					count++;
                    swaps++;//swap
				}
                compares++;//compare
			}
            //drops out too early
			//if(count==0) return; //early exit condition
			hold=list[min];
			list[min]=list[i];
			list[i]=hold;
		}
        return;
		
	}
	
	public static void bubble(int[] list)
	{
		int count=0;
		int hold=0;
		for(int i=0;i<list.length;i++)//sort each number
		{
			count=0;
			for(int j=0;j<list.length-i-1;j++)//move biggest to end
			{
				if (list[j]>list[j+1])
				{
					hold=list[j+1];
					list[j+1]=list[j];
					list[j]=hold;
					count++;
                    swaps++;
				}
                compares++;
                
			}
			if (count==0) return;//early exit condition
		}
        return;
	}    
    
	public static void print(int[]  list)
	{
		for(int i=0;i<list.length;i++)
		{
			System.out.print(list[i]+" ");
		}
		System.out.println();
	}
	
	public static int[] setList(int[] list)
	{
		int x=list.length;
		int hold,num1,num2;
		for(int i=0;i<x*2;i++)
		{
			num1=(int)(x*Math.random());
			num2=(int)(x*Math.random());
			hold=list[num1];
			list[num1]=list[num2];
			list[num2]=hold;
		}
		return list;
	}
    public static int[] makeList(int[] list)
    {
        int x=list.length;
		for(int i=0;i<x;i++)
		{
			list[i]=i+1;
		}
        return list;
    }
}
