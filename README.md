
### Programming Assignment 1: Percolation
##### Tips:
- Introduce 2 virtual sites (and connections to top and bottom).
- Using two UF, the one is only with top wirtual site and the other with both.

### Programming Assignment 2: Deques and Randomized Queues
##### Tips:
- Randomized Queues just need one end, not two.

### Programming Assignment 3: Pattern Recognition
##### Tips
- Sort the points first(this makes it easy to find end points).
- We can restrict the origin point to be the start of the line(this makes subsegments unnecessary to worry about).

### Programming Assignment 4: 8 Puzzle
##### Tips
- When two search nodes have the same Manhattan priority, you can break ties however you want, e.g., by comparing either the Hamming or Manhattan distances of the two boards.
- Use only one PQ to run the A* algorithm on the initial board and its twin.

### Programming Assignment 5: Kd-Trees
##### Tips
- Use rect(the node in this rect) to diliver information to next recursion.
- We also can add rect to Class Node as a Class variable.