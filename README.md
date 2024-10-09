# Java Game Project

## Data Structures and Classes

### Classes
- **Tournament**: Tracks statistics and determines the tournament winner.
- **Round**: Manages the starting player and input validation based on a coin flip.
- **Player**: Have strategies stored, such as the characteristics of players' colors, total pieces, if they are human, score, win, and track of all pieces.
- **Human**: Derived from `Player`, handles validation for user input.
- **Computer**: Derived from `Player`, implements strategies for computer play.
- **BoardView**: Composite class integrating `Board`, `Round`, `Player`, `Human`, `Computer`, and `Tournament`. Manages game logic, including board manipulation, validation, coin flips, scoring, and serialization.
- **Board**: Validates if a piece can move to specific positions. Look for win conditions. Modifies board to move pieces. Finds pieces on the board and looks for positions to be played on the board. Converts coordinates between readable human text for the user and converted coordinates for my game to read correctly.

### Data Structures
- **Player**: 
  - `p_pieces`: Current player's pieces on the board.
  - `p_newpos`: Potential new positions for pieces.
  - `allStrats`: List of available strategies.

- **Board**: 
  - `visitedPieces`: Used for win condition logic, tracking the pieces encountered during the game.

### Algorithms
- **Depth-First Search (DFS)**:
  - DFS (Depth First Search) - I used a simpler version of DFS for my win condition. The logic behind my win condition was a recursive function. I would find a piece on the board that matched the current player's color in the recursive function. From there, I would look around the piece and see if other similar colored pieces exist around the current piece. If so, go onto this piece and see if similar pieces exist next to it. However, it would also store pieces that have already been visited. After branching out, the total number of visited pieces is compared to the total number on the board. If the totals are equal, the win condition is true; however, if it doesn't, it is false.

## Development Log

### April 8
- **Task**: I translated my tournament class from C++ into Java, aiming to familiarize myself with the Language and grasp its nuances. One notable difference I encountered was that Java requires header files to be consolidated into a single file, which posed a unique challenge.
- **Time Spent**: 2 hours.

### April 10
- **Task**: The next class I tackled was my Round. While there were similarities with C++, one of the most notable differences was the need in Java to initialize a Random Object for my coinflip function to work. I then proceeded to create my Player class, although I set everything up without the computer/helper functions, another key difference from C++.
- **Time Spent**: 2 hours.

### April 15
- **Task**: Finish implementing the rest of the code, including Computer, Human, Board, and BoardView classes, and start with the main difference between vectors and List and vectors to have a difference in bounds checking. Some of my C++ code would sometimes go out of bounds in its vectors, but Java was very strict with the limitations of going outside the array. So, additional boundaries were added to my movement validations. Another issue arose when selecting a piece because the character conversion in its math is different. So, instead, I changed my approach using a switch statement because Strings in Java have a function to get specific characters in a string. From this, I would compare the characters and then return a number appropriate for the board to parse through.
- **Time Spent**: 2 hours.

### April 18 - 20
- **Task**: Watched half of the video https://www.youtube.com/watch?v=tZvjSl9dswg&t=6191s&ab_channel=CalebCurry
- **Time Spent**: 10 hours total.

### April 22 - 28
- **Tasks**:
  - Implemented game setup, including piece selection and validation.
  - Added swap player functionality using coin flip.
  - Enhanced user interaction and logging features.
  - Implemented help functionality and score display.
  - Added serialization and load game functionality.
- **Time Spent**: Varies between tasks, approximately 2 hours per task.


## Screenshots
![image](https://github.com/user-attachments/assets/93443577-5195-4931-883c-7ecc213babff)
![image](https://github.com/user-attachments/assets/79f9ebc3-d7be-405d-8e37-ccc3ee87e0b2)
![image](https://github.com/user-attachments/assets/ac23498f-5883-4ba2-aaaa-e1ea58b406f0)



