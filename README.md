# SameGame
---
## Table of Contents

- Description
- How to Build
- Rules
- Tips
- More Information

## Description
This is a game I made for a college class in February 11, 2011. It's a java project called SameGame, which features a fully-operational gui, score table, and separate levels.

## How to Build

The jar file is included, so simply download the repository and run the jar. This program requires the Java Runtime library, and compiling it requires Java SDK 7. To compile the program, simply run the release bat files.

## Rules

You are given a 20 x 20 board of tiles. Each tile has one of a number of colors in the level, usually three or four, but potentially more. Your objective is to completely remove the board of tiles to head to the next level. The way to do this is to click on one of the tiles. When you do this, all tiles of the same color adjacent to the one you clicked will be selected and colored gray. If you click it again, it will remove the selected tiles and award you points.

Tiles can only be removed if at least two are selected. You cannot only remove one tile. Therefore, it is possible to find yourself in an unwinnable state. In that case, you can reset the board and try again. Keep in mind, however, that you only have a limited amount of resets. If you run out and cannot complete the level, the game is over and you are forced to quit.

When a board is cleared, you head to the next level. New levels give you more colors and more difficult challenges. There are a total of 10 colors. Can you make it to the top?

## Tips

- Go from the top down first. Don't go for the middle as that can break up any potential matches.
- Think about the move you make before removing the tiles. Don't just click on the tiles randomly.
- Be sure to save big clusters of tiles so that you can move single tiles of the same color to said clusters. This allows you to take care of any leftover single tiles.

## More Information

SameGame was originally called *Chain Shot!* and was programmed by Kuniaki Moribe in 1985. More information can be found [here](https://en.wikipedia.org/wiki/SameGame).

