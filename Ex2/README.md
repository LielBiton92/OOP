# README - OOP Ex2
Authors: Lior Abramovich & Liel Biton


## The implements :

-  DirectedWeightedGraph (implemented by DirectedWeightedGraphImpl) - an object which represents a directed weighted graph.
- NodeData (implemented by NodeDataImpl) - an object displays the vertices in the graph and the actions that are performed on them. The vertex receives a key, tag, and data.
- EdgeData (implemented by EdgeDataImpl) - an object displays the edges in the graph and the actions that are performed on them. Theedges receives a src, dest, and wieght.
- DirectedWeightedGraphAlgorithms (implemented by DirectedWeightedGraphAlgorithmsImpl) - an object that implements some basic graph algorithms with Dijkstra's and BFS algorithms.
- Gui.

## Methods

## DirectedWeightedGraphImpl
In this class we implementing the interfaces - DirectedWeightedGraph.

| Name | Description |
| ------ | ------ |
DirectedWeightedGraphAlgorithmsImpl()|	Constructors
init(DirectedWeightedGraph g)|	Initialization the graph
getGraph()|	Returns a pointer to the initialized graph
copy()|	Copy constructor - deep copy
isConnected()|	Checking connectivity of the graph
shortestPathDist(int src, int dest)|	Returns the sort distance between src to dest whit Dijkstra's algorithm
shortestPath(int src, int dest)|	Returns the way from src to dist whit Dijkstra's algorithm and whit a list
isNumeric(String str)| helper function for the shortest path, converts string to a int
center()| Finds the NodeData which minimizes the max distance to all the other nodes
Dijkstra(NodeData source)| Helper function for finding the shortest paths between nodes
tsp(List<NodeData> cities)|
copyArrToOtherList(List<NodeData> res, List<NodeData> tmp)| copies the array to another list
removeDestFromList(List<NodeData> targets, int dest)| Removes destination from the list
runFirstIteration(List<NodeData> targets)| Runs the first iteration
save(String file)|	Saves a graph to a file whit Json
load(String file)|	Load a graph from a file whit Json
paintWhiteColorToAllNodes()| Paints all the nodes with white color
bfs(NodeData nodeData)| void bfs



## EdgeDataImpl

Name|	Description
| ------ | ------ |
EdgeDataImpl(int src, int dest, double weight)|	Constructor
getSrc()| Returns the edge source
getDest()|	Returns the edge destination
getWeight()|	Returns the edge weight
getInfo()|	Returns the edge info
setInfo(String s)|	Set the edge info
getTag()|	Returns the edge tag
setTag(int t)|	Set the edge tag


## GeoLocationImpl
Name|	Description
| ------ | ------ |
GeoLocationImpl(String str)|	Constructor
GeoLocationImpl(double x, double y, double z)| Constructor
distance(GeoLocation g)|	Returns the distance bewteen two locations
x()|	Returns the x
y()|	Returns the y
z()|	Returns the z


## NodeDataImpl

Name|	Description
| ------ | ------ |
NodeDataImpl (int key, String location)|	Constructor
NodeDataImpl(int key, int tag, String info, double nodeWeight, GeoLocation geoLocation)|	Constructor that got a key
getKey()|	Returns the nodes key
getLocation()| Returns the nodes location
getWeight()| Returns the nodes weight
setWeight(double w)| Set the weight nodes
getInfo()|	Returns the nodes String
setInfo(String s)|	Set the info nodes
getTag()|	Returns the nodes tag
setTag(double t)|	Set the tag nodes
compareTo(node_info n)|	comper between two nodes weight
 
 
 


![UML](https://user-images.githubusercontent.com/92746221/145861331-3ce880b7-2877-4d1b-88fc-0bd6a14d1d25.png)


## Part 2 - GUI
- In this part are the departments that implement the design (GUI, JPanel, JFarme).


![GUI](https://i.ibb.co/RgxxKYm/Screen-Shot-2021-12-08-at-15-32-37.png)

Classes:
- GUI.java - implementaion of the graph's GUI.
- Ex2.java - The main class, represents the initialization of the graph.

## How to use:
First step: run the 'Ex2.main()'
Then our GUI should be loaded and you have these options to work with:

## File
![File](https://i.ibb.co/X5Zty3b/Screen-Shot-2021-12-08-at-16-10-10.png)

- Save Graph - Saves the graph
- Reset Graph - Resets the graph
- Load Graph - Loads an imported graph


## Graph Actions


![Graph Actions](https://i.ibb.co/f8VTDCH/Untitled.png)

- Add Node - Letting you to add a node

 ![Add Node](https://i.ibb.co/F3K8VTL/Screen-Shot-2021-12-08-at-16-22-44.png)

- Remove Node - Letting you to remove node
- Add Edge - Letting you to add edge

![Add Edge](https://i.ibb.co/fxTWqHN/Screen-Shot-2021-12-08-at-16-32-50.png)

- Remove Edge - Letting you to remove edge 


![Remove Edge](https://i.ibb.co/kHVF9DV/Screen-Shot-2021-12-08-at-18-46-32.png)




## Algo

![Algo](https://i.ibb.co/8m6nrrC/Screen-Shot-2021-12-08-at-16-06-26.png)

- Short Path - Draws a green path that is showing us the shortest path between two nodes

For Example, lets check the shortest path from 40 to 37:

![Short Path Example](https://i.ibb.co/ry7J3p8/Screen-Shot-2021-12-08-at-19-11-16.png)

The Result:


![Short Path Result](https://i.ibb.co/B2pFn3s/Screen-Shot-2021-12-08-at-19-11-29.png)

- Is Connected - checking if its connected and will answer with true/false

Example:

![Is Connected Example](https://i.ibb.co/xHm1ZXQ/Screen-Shot-2021-12-08-at-17-44-22.png)


- TSP - Draws a blue path that is showing us the shortest path between multiple nodes

For Example, lets pick "2,7,13":

![TSP Example](https://i.ibb.co/vDhpS3y/Screen-Shot-2021-12-08-at-17-50-43.png)

The result:

![TSP Example Result](https://i.ibb.co/ZmcFqR6/Screen-Shot-2021-12-08-at-17-50-55.png)

- Center - tells you which one of the nodes is the center

Example:

![Center Example](https://i.ibb.co/k4xtK6K/Screen-Shot-2021-12-08-at-18-41-37.png)

## Performance test results:

## 1,000 Nodes

![1k nodes](https://i.ibb.co/c1QB55h/1k.png)

## 10,000 Nodes

![10k nodes](https://i.ibb.co/HCr85Nt/10k.png)

## 100,000 Nodes

![100k nodes](https://i.ibb.co/JmHB7QC/100k.png)
 
 
##1,000,000 Nodes
 could not create .
 
 ## How to open the jar file
 

-download the jar file from the github


- type the command java -jar Ex2.jar "Graph json file path"









## Sources and links:

https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
