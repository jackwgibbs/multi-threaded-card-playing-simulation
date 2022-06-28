# Multi-Threaded Card Simulation

Multi-Threaded Card Simulation is a game written in Java where players aim to have a hand of 4 cards with the same value by picking up a card from their left, and discarding one to their right.

## Game Set-Up

At the beginning of the game, the user is prompted to input (via the terminal window) the number of players in the game, before being asked for the name of a valid pack file. The Multi-Threaded Card Simulation game then begins. Each player has its own thread, therefore players are able to pick up and discard cards concurrently. The first person to have a hand of 4 identical cards wins and the game ends. 

## Output Files

At the end of every game, an output file for each player is produced which contains information such as all the cards the player picked up and the state of their hand after each turn. Furthermore, for each deck an output file is generated which contains the decks final cards.


## Testing

Unit tests have been written to test the code and this Multi-Thread Card Simulation provides a Test Suite that runs all the tests in one batch. The Test Suite is named 'TestSuite' and can be found in the following directory: 'cardsTest/out/test/cards/TestSuite.java'.
This Multi-Threaded Card Simulation was written using the IntelliJ IDEA IDE, therefore the steps below which outline how to run the test suite apply to IntelliJ IDEA.
1. Open IntelliJ IDEA
2. Select Open and select the cardsTest directory
3. IntelliJ IDEA will then load in the project.
4. You may need to go to 'Edit Configurations', 'Add New Configuration', 'JUnit', and select the TestSuite class. 
5. To then run the test suite select the 'Run' button (located top right of the IDE), ensuring that you are running the TestSuite configuration.
6. At the bottom of the IDE window you will see the tests run.
7. To run the tests using Code Coverage select the second button on the right from the 'Run' button labelled 'Run TestSuite with Code Coverage'.

Tests have been designed to thoroughly test each method in the cards game classes and have all passed as you will be able to verify by running the test suite.
