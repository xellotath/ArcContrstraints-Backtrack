# Sudoku Solver in Java

A Java-based Sudoku solver utilizing arc consistency rounds or backtrack search to solve input puzzles.

## Features

- **Arc Consistency:** Applies constraint propagation techniques to reduce the search space.
- **Backtracking Search:** Falls back to backtracking when arc consistency alone cannot solve the puzzle.
- **Flexible Input:** Accepts Sudoku puzzles as input files or via standard input (customize as needed).

### Usage

Program will apply one round of Arc Consistency when clicked, filling in values and reducing search space if possible

At any point, user may click on the bottom right corner of window to trigger a backtrack search, effectively solving the puzzle even when Arc consistency cannot proceed further

Puzzles are in text form, and puzzle selection is **currently** possible through changing the filename in the code

```bash
java -cp src Main input.txt
```

Replace `Main` with your main class, and `input.txt` with your puzzle file.

### Example

```
5 3 . . 7 . . . .
6 . . 1 9 5 . . .
. 9 8 . . . . 6 .
8 . . . 6 . . . 3
4 . . 8 . 3 . . 1
7 . . . 2 . . . 6
. 6 . . . . 2 8 .
. . . 4 1 9 . . 5
. . . . 8 . . 7 9
```

## Project Structure

- `src/` - Java source files
- `input.txt` - Example Sudoku puzzle input (create your own as needed)

## Algorithms

- **Arc Consistency:** Enforces binary constraints iteratively to reduce possible values for each cell.
- **Backtrack Search:** Depth-first search with constraint checks at each step.



