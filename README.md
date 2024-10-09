# Java Game Project

## Bug Report
- No known bugs.

## Feature Report
- All features are implemented as intended.

## Data Structures and Classes

### Classes
- **Tournament**: Tracks statistics and determines the tournament winner.
- **Round**: Manages the starting player and input validation based on a coin flip.
- **Player**: Contains attributes such as player color, total pieces, human status, score, wins, and strategies.
- **Human**: Derived from `Player`, handles validation for user input.
- **Computer**: Derived from `Player`, implements strategies for computer play.
- **BoardView**: Composite class integrating `Board`, `Round`, `Player`, `Human`, `Computer`, and `Tournament`. Manages game logic, including board manipulation, validation, coin flips, scoring, and serialization.
- **Board**: Handles board validation, move operations, win conditions, and piece management. Converts human-readable coordinates for internal game use.

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
- **Task**: Converted C++ code to Java (Tournament class).
- **Time Spent**: 2 hours.

### April 10
- **Task**: Continued C++ to Java conversion (Round and Player classes).
- **Time Spent**: 2 hours.

### April 15
- **Task**: Finished converting base game (Computer, Human, Board, BoardView).
- **Time Spent**: 2 hours.

### April 18 - 20
- **Task**: Learning Android Studio.
- **Time Spent**: 6 hours total.

### April 22 - 28
- **Tasks**:
  - Implemented game setup, including piece selection and validation.
  - Added swap player functionality using coin flip.
  - Enhanced user interaction and logging features.
  - Implemented help functionality and score display.
  - Added serialization and load game functionality.
- **Time Spent**: Varies between tasks, approximately 2 hours per task.

## Generative AI Assistance
- None.

## Screenshots
_(Add screenshots of the game interface here)_
