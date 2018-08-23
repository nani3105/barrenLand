<h1>BarrenLand Analysis</h1>

The algorithm for this is doing a simple DFS traversal of the matrix.

Approach:

- We initialize a boolean 2D - matrix with width, height.
- Next we read the inputs for the rectangle and run it through some battery checks
    - They fall in the boundaries of the farm.
    - The top right corner coordinates are greater than the left bottom coordinates
    - The four coordinates dont form a point or vertical or horizontal line.
- Next we loop through all the barren lands and mark the farm as true (visited).
- Then we start from the (0, 0) to the entire matrix and check if the current cell is not visited.
- If the current cell is not visited, then we start the DFS search using a stack by looking at the top, bottom, right, left neighbours.
    - DFS using stack.
      - We check of not visited cells and push it onto the stack and iterate till the stack is empty.
      - When we reach all the leaf node i.e all the area surrounded by the barren land and visited land we stock and add the result to a list.
  - We sort the list which is the areas of the fertile land and output the result.
  
  
  Running the code.
  
```bash
  ./gradlew build && java -jar build/libs/barrenLand-1.0-SNAPSHOT.jar
```