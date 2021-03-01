# tic-tac-toe
A little side project to pass the time

#### Background 
As interview practice one day, an engineer asked me to design a tic-tac-toe game. I was only able to implement a board and logic for processing a turn and checking for a winner. I decided to keep working on this as a project to get more familiar with building projects from scratch in Java

#### Implementation
The MVP is a command line game that assumes two players are sitting in front of the same computer~ 

#### How it's played
*First, run the program! When the game starts up:*
- Players are prompted to begin the game
- Once they have selected yes, they have the ability to specify the size of the board and their character string
- The first player to go is chosen by the computer
- During their turn, players select a position on the board by selecting a number representing an open spot
    - If a player chooses an occupied spot, they're once again prompted to select a different spot 
- After each turn, a check is performed to see if there's a winner~
- If all spots are filled on the board and neither player has won, the game is a draw

#### What I want to implement beyond this
- Introduce 1 player mode, where you play against a computer
- Best 'x' out of 'x' games (ex. keeping track of best 2 of 3)
- ~~Allow users to select the character string they'd like to use for the game~~
- ~~Create replay logic~~
- ~~Vary size of the board~~