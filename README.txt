=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 23629530
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays
I used a 2D of my custom Tile class to keep track of the tiles on the board. This is an appropriate
use of the concept because 2048 is played on a grid, and 2D arrays naturally represent a grid of
objects.

  2. I/O
I used I/O to save the game state persistently to files/game_state.txt. Every time the user changes
the board (i.e. by moving tiles, pressing undo or reset), the BoardUI class automatically calls a
saveGame() method that writes to the file. When starting the program, it first checks if that file
exists, and if so, attempts to read it. If it's corrupted, then the program loads in a fresh new
game.

  3. Collections
I used a Stack<BoardState> to implement the undo feature. Every time the user changes the board,
the model will push the previous BoardState onto the stack, and every time the user presses the undo
button, the model will pop the top off the stack (if it is nonempty) and replace the current board
state.

  4. Testable Component
I wrote several tests in src/test/java/org/cis1200/game2048/BoardTest.java which cover board
initialization, moving tiles in all directions, scorekeeping, board state history, undo, win/loss,
and IO actions like saving/loading games.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
Tile: basically a wrapper class for the Tile.State enum, holds a private final State field, and has
utility methods for comparison and upgrading its value.
GameSaverLoader:

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
