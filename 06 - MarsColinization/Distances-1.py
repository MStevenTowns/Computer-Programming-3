from PIL import Image
import math,time,os
def minActive():
    global active, costs
    minA=0
    minV=-1
    for a in active:
        x,y=a
        v=costs[x][y]
        if minV==-1 or v<minV:
            minV=v
            minA=a
    return minA

def visitNeighbors(minA):
    global costs, obstacles, w, h
    x,y=minA
    initialCost=costs[x][y]
    for i in range(x-1,x+2):
        for j in range(y-1,y+2):
            j=(j%h+h)%h
            i=(i%w+w)%w
            if(i==x and j==y):
                continue
            r,g,b=pix[i,j]
            pix[i,j]=(r,g,128)
            costV=costs[i][j]#curent best cost to enter tile
            obsV=obstacles[i][j]#cost to move to tile
            
            # obsV=obsV+h*h/obsV
            
            height=pix[i,j][0]-pix[x,y][0]
            if(j!=y and i!=x): obsV*=math.sqrt(2)
            obsV=obsV+height*height/obsV
            if not (i,j) in done and not (i,j) in active:
                active.append((i,j))
            if ((costV==-1) or ((initialCost+obsV)<costV)):
                    costs[i][j]=initialCost+obsV
    remove(minA)
    append(minA)

t=time.time()
img=Image.open("mars.png").convert("RGB")

pix=img.load()
w,h=img.size
obstacles=[[0 for j in range(h)] for i in range(w)]
#goal=((100,100),(300,400)) 428ish
start=(int(input("Starting X: ")),int(input("Starting Y: ")))
end=(int(input("\nEnding X: ")),int(input("Ending Y: ")))


for i in range(w):
    for j in range(h):
        obstacles[i][j]=1
        
pix[end[0],end[1]]=(255,0,0)
pix[start[0],start[1]]=(0,255,0)
costs=[[-1 for j in range(h)] for i in range(w)]
active=[start]
done=[]
costs[start[0]][start[1]]=0
i=1
remove=active.remove
append=done.append

newpath = "output/" 
if not os.path.exists(newpath): os.makedirs(newpath)

while (not end in done)and len(active)!=0:
    if(((time.time()-t)/60)>i):
        i=i+1
        print(str(int((time.time()-t)/60))+" Minutes")
        img.save("output/output.png"+str(i),"PNG")
    minA=minActive()
    visitNeighbors(minA)
    

if(len(active)==0):
    print("no path found")
    
else:
    print("\nTotal Cost: "+str(costs[end[0]][end[1]]))
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
#print(str((time.time()-t)/60)+" Minutes")
img.save("output.png","PNG")
