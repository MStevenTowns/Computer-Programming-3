from PIL import Image
import math,time

t=time.time()

img=Image.open("Maze.png").convert("RGB")

pix=img.load()
w,h=img.size
obstacles=[[0 for j in range(h)] for i in range(w)]
#goal=((100,100),(300,400)) 428ish
start=(int(input("Starting X: ")),int(input("Starting Y: ")))
end=(int(input("\nEnding X: ")),int(input("Ending Y: ")))


for i in range(w):
    for j in range(h):
        r,g,b=pix[i,j]
        if g==r==b==255:
            obstacles[i][j]=1
        else:
            obstacles[i][j]=-1

costs=[[-1 for j in range(h)] for i in range(w)]
active=[start]
done=[]
costs[start[0]][start[1]]=0
i=1
while (not end in done)and len(active)!=0:
    if(((time.time()-t)/60)>i):
        i=i+1
        print(i)
        print(str((time.time()-t)/60)+" Minutes")
        img.save("output.png","PNG")

    minA=0
    minV=-1
    for a in active:
        x,y=a
        v=costs[x][y]
        if minV==-1 or v<minV:
            minV=v
            minA=a
    remove=active.remove
    append=done.append
    x,y=minA
    initialCost=costs[x][y]
    for i in range(x-1,x+2):
        if i<0 or i>=w:
            continue
        for j in range(y-1,y+2):
            if j<0 or j>=h or (i==x and j==y):
                continue
            if (j!=y and i!=x) :
                continue
            r,g,b=pix[i,j]
            if(pix[i,j]!=(0,0,0)): pix[i,j]=(0,255,0)
            costV=costs[i][j]#curent best cost to enter tile
            obsV=obstacles[i][j]#cost to move to tile
            if obsV==-1:#black tile
                continue
            if not (i,j) in done and not (i,j) in active:
                active.append((i,j))
            if ((costV==-1) or ((initialCost+obsV)<costV)):
                    costs[i][j]=initialCost+obsV
    remove(minA)
    append(minA)

print(costs[end[0]][end[1]]) 
path=[end]
while costs[path[-1][0]][path[-1][1]]!=0:
    x,y=path[-1]
    minN=-1
    minV=-1
    for i in range(x-1,x+2):
        if i<0 or i>=w:
            continue
        for j in range(y-1,y+2):
            if j<0 or j>=h or (i==x and j==y):
                continue
            if (j!=y and i!=x) :
                continue
            costV=costs[i][j]
            if costV==-1:
                continue
            if minV==-1 or costV<minV:
                minV=costV
                minN=(i,j)
    path.append(minN)        
for x,y in path:
    pix[x,y]=(255,0,0)
        
pix[start]=(255,0,255)
pix[end]=(255,0,255)
print(str((time.time()-t)/60)+" Minutes")
img.save("output.png","PNG")

