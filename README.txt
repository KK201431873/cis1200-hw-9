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
I used a 2D array of my custom Tile class to keep track of the tiles on the board. This is an
appropriate use of the concept because 2048 is played on a grid, and 2D arrays naturally represent a
grid of objects. Additionally, 2D arrays are great for algorithms (such as the tile-moving algorithm
I implemented) that require traversing across a grid. The custom type contained in the 2D array,
Tile, is appropriate since each element in the array needs to carry data about its display string
and appearance colors. Of course, this could have been done with a Map, but it is more elegant to
use a class that directly contains said data.

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
button, the model will pop the top of the stack (if it is nonempty) and replace the current board
state. I decided to use Stack for this feature because I received feedback advising me to do so for
my project proposal. This feature is appropriate for the Collections concept because the board
history Stack must grow or shrink during runtime, and the data contained by the Stack is part of the
core game state. I didn't need to use a Deque because Board only needs to be able to access the top
element of the Stack, while Deque provides unnecessary access to both the start and end of the
queue.

  4. Testable Component
I wrote several tests in src/test/java/org/cis1200/game2048/BoardTest.java which cover board
initialization, moving tiles in all directions, scorekeeping, board state history, undo, win/loss,
and saving/loading IO operations. This usage of the Testable Component concept is appropriate
because there are at least 13 total tests (10 for the concept plus 3 minimum tests) and they all
target the game's core model independent of the GUI. These tests are all distinct from each other,
since even though moveUp and moveDown both test moving, the direction is significant due to edge
cases such as array index being 0 or array.length-1.

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

BoardTest: contains tests for Board.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
One of the most difficult parts of the Board implementation was the moving and merging algorithm,
since there are so many ways to do it, many of which I have strong reasons for why they are not good
design. I initially considered writing four separate methods for merging up, down, left, and right,
but I felt that would be extremely redundant and wanted a more general helper function. However,
the algorithm for such function was nontrivial, especially since moving in different directions can
mean iterating over columns or rows and accounting for 0 or max index edge cases, which could also
result in a lot of if-statements and redundant code. I ultimately settled on an algorithm that
treats the given MoveDirection as a vector, finds an orthogonal vector for iterating over lines,
goes to the farthest element in each line, and merges tiles backwards (i.e. in the negative
MoveDirection vector direction). It took quite a bit of debugging to get right, but it ultimately
led to elegant code.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
I think I encapsulated private state well, except for testing/debugging methods like
getBoardHistory() in Board.java, which I needed for a test in BoardTest. I think I separated the
functionality between the game's model and GUI well, since none of my tests needed to reference
anything in BoardUI or Run2048.
I don't particularly like how I created the inner BoardState class within the Board class, since I
originally imagined a separation of the levels of abstraction: BoardState would deal with lower-
level algorithms compared to Board, but this ultimately made accessing fields clunky and forced me
to write redundant getter/setter methods for both classes. If I were to refactor my code, I think
I would move the BoardState class to a different file. I don't think I'll be able to completely
delete BoardState because the boardHistory Stack needs to keep track of both scores and tile grids.
It would be even more clunky to have two Stacks, one for scores and one for tile grids.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
https://stackoverflow.com/questions/8802320/draw-text-with-graphics-object-on-jframe
https://stackoverflow.com/questions/69149687/how-to-open-a-new-window-on-button-click-in-swing-gui-using-java
https://stackoverflow.com/questions/9554636/the-use-of-multiple-jframes-good-or-bad-practice
https://stackoverflow.com/questions/21228740/how-do-i-keep-a-jbutton-from-changing-size
https://stackoverflow.com/questions/17244713/using-filewriter-and-bufferedwriter-clearing-file-for-some-reason
https://stackoverflow.com/questions/25219423/setalignmentxcenter-alignment-does-not-center-boxlayout-in-jframe
https://stackoverflow.com/questions/14380035/java-font-size-from-width
https://stackoverflow.com/questions/21247776/java-swing-how-to-smoothly-animate-move-component
