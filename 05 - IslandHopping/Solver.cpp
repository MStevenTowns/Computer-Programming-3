#include <iostream>

using namespace std;



int main()
{
    int r,g,b;
    int w,h=500;
    int obstacles[w][h];
    int costs[w][h];
    int start[2],end[2]={-1,-1};
    for(int x=0;x<w;x++)
    {
        for(int y=0;y<h;y++)
        {
            r,g,b=pix[x][y];
            if (g==255&&r==b==0) obstacles[x][y]=2;
            else if(b==255&&g==r==0) obstacles[x][y]=4;
            else if(r==g==b==128) obstacles[x][y]=1;
            else obstacles=-1;
        }
    }
    
    for(int x=0;x<w;x++)
    {
        for(int y=0;y<h;y++)
        {
            costs[x][y]=-1;
        }
    }
    
    
    
    return 0;
}
