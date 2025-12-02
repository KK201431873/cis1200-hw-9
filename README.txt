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
objects. Additionally, 2D arrays are great for algorithms (such as the tile-moving algorithm I
implemented) that depend on motion

  2. I/O
I used I/O to save the game state persistently to files/game_state.txt. Every time the user changes
the board (i.e. by moving tiles, pressing undo or reset), the BoardUI class automatically calls a
saveGame() method that writes to the file. When starting the program, it first checks if that file
exists, and if so, attempts to read it. If it's corrupted, then the program loads in a fresh new
game. This implementation is an appropriate use of the IO concept because it requires exception
handling and using FileReader/FileWriter.

  3. Collections
I used a Stack<BoardState> to implement the undo feature. Every time the user changes the board,
the model will push the previous BoardState onto the stack, and every time the user presses the undo
button, the model will pop the top off the stack (if it is nonempty) and replace the current board
state. I decided on using Stack for this feature because I received feedback advising me to do so
for my project proposal. This feature is appropriate for the Collections concept because Stack is
being used as it should be (i.e. calling add() and pop()), and the data contained by the Stack is
part of the core game state.

  4. Testable Component
I wrote several tests in src/test/java/org/cis1200/game2048/BoardTest.java which cover board
initialization, moving tiles in all directions, scorekeeping, board state history, undo, win/loss,
and IO actions like saving/loading games. This usage of the Testable Component concept is
appropriate because there are at least 13 total tests (10 for the concept plus 3 minimum tests)
and they all target the game's core model independent of the GUI. These tests are all distinct from
each other, since even though moveUp and moveDown both test moving, the direction is significant
due to edge cases such as array index being 0 or array.length-1.

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

GameSaverLoader: handles all IO exchanges, providing methods for writing a game to a file and
loading a game from a file.

Board: this is where all the game logic is kept and is independent of the GUI. Board has an inner
class BoardState, which is a representation of one single board state (hence the name), containing
data like score, grid size, and the values for each tile (null if it's empty). BoardState handles
all the low-level array logic and exposes a custom move() function which I wrote to accept any
MoveDirection enum value and apply the same algorithm to shift all tiles in that direction. The
outer Board class contains the current BoardState and a Stack of BoardStates to represent the
player's past moves, exposing higher-level methods like moveUp(), saveGame(), undoMove(), and
reset().

BoardUI: a subclass of JPanel and manages the GUI for the game (excluding instructions button),
popup windows for win/loss/undo, keyboard inputs, and high-level IO commands. I spent quite a while
getting the UI to look just like the real 2048 game online, except with no animations because that
is difficult with Swing.

Run2048: implements Runnable and creates the JFrame, adds a BoardUI, and also adds the instructions
button & manages the instructions popup.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
