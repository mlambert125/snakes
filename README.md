# Snake Game

This is a simple project that demonstrates how to use JavaFX to
create a simple game.  It is intended to be used as a starting
point that will progressively become the classic snake game.

## Project Structure

This project has one class named `SnakesApplication`.  This class
is derived from JavaFx's `Application` class which provides
the basis for making a desktop application that runs in a 
windows.  

Code is provided in the `run` method to set up this window
to contain a single `Canvas` that we can draw on, and to 
repeatedly call the class's `update` method to update the 
game state and then the `draw` method to clear and redraw the 
canvas in a way that reflects the current state.  The startup
code also polls for keyboard input and sets flags corresponding
to the space bar and the arrow keys.

## Part 1

Run the project and observe the window that appears.  You should
be able to use the arrow keys to move the red block on the screen
around the screen.  This is the starting point for the game, and a 
proof that we have all of the basic elements to create a simple game:

- Graphics
- Input
- Game state
- Game loop functions:
  - Update
  - Draw