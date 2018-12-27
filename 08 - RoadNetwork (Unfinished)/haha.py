inFile=open("p107_network.txt")
outFile=open("output.txt","w")
for line in inFile:
    outLine=line.trim().split(",")
    for elem in outLine:
        if len(elem)==1: elem+="  "
        if len(elem)==2: elem+=" "
        outFile.write(","+elem);
    
    
