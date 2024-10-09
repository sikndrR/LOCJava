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
  - A simplified DFS is used for the win condition, checking for contiguous pieces of the same color. The algorithm recursively searches around a piece, marking visited pieces and comparing the total count to the expected number of pieces.

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
