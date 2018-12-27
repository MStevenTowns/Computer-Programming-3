import pygame,sys
from random import randint

#Check if the cell is valid for the maze
def isValid(cell,width,height):
    x,y=cell
    return (x>0 and x<width and y>0 and y<height)

#see where it is possible to make a path
def getBorders(cell,width,height):
    x,y=cell
    borders=[]
    if isValid((x-2,y),width,height): borders.append((x-2,y))
    if isValid((x+2,y),width,height): borders.append((x+2,y))
    if isValid((x,y-2),width,height): borders.append((x,y-2))
    if isValid((x,y+2),width,height): borders.append((x,y+2))
    return borders
    
#Make a path between the start and the end points
def makePath(start, end, window,surface):
    mazeSurface.blit(surface,end)
    endX,endY=end
    nextX,nextY=start
    if nextX<endX:nextX+=1
    elif nextX>endX:nextX-=1
    elif nextY<endY:nextY+=1
    elif nextY>endY:nextY-=1
    window.blit(surface,(nextX,nextY))


cellRows=cellColumns=0
while cellRows<1 or cellColumns<1:
    cellRows=int(input("Height of Cells: "))
    cellColumns=int(input("Width of Cells: "))
height=2*cellRows+1  #*2 creates black pixle after, +1 creates black pixle before
width=2*cellColumns+1#same



#Start pygame and create window and various surfaces
pygame.init()
window = pygame.display.set_mode((width,height))
cellSurface=pygame.Surface((1,1))
cellSurface.fill((255,255,255))
mazeSurface=pygame.Surface((width,height))

front=[(1,1)]
done=[]
mazeSurface.blit(cellSurface,(1,1))
pygame.transform.scale(mazeSurface,(width,height),window)

count=0 #to control screen updating if wanted

while(len(front)>0):#While there are still unjoined pieces of the maze
    for event in pygame.event.get():
        if event.type==pygame.QUIT:
            sys.exit()
        elif event.type==pygame.KEYDOWN:
            if event.key==pygame.K_ESCAPE:
                sys.exit()
    cell=front[-1]#pick last cell added to list, makes very long paths
        
    borders=getBorders(cell,width,height)#Get list of valid border cells
    cleared=False
    while not cleared:
        if len(borders) == 0:#if there is no valid path, move the cell to the done list
            front.remove(cell)
            done.append(cell)
            cleared = True
        else:
            index = randint(0,len(borders)-1)#Pick a random border
            if borders[index] not in front and borders[index] not in done:#If there is no path to the border cell, make the path
                makePath(cell, borders[index], mazeSurface, cellSurface)
                front.append(borders[index])
                cleared = True
            else:#if all paths have been cleared, remove
                borders.remove(borders[index])
    pygame.transform.scale(mazeSurface,(width,height),window)
    #if(count%(1000)==0):pygame.display.update()#use for big mazes
    pygame.display.update()
    count+=1;
    
pygame.display.update()#only actually usefull if you update based on count, but might be needed
print("done");
while(1):#keep displaying image
    for event in pygame.event.get():
        if event.type==pygame.QUIT or(event.type==pygame.KEYDOWN and event.key==pygame.K_ESCAPE):
            pygame.image.save(mazeSurface,"../Assignment4-MazeSolving/Maze.png")
            sys.exit()
