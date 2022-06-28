import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class TestFileOutputHandler {
    Player player1;
    CardDeck deck1;
    CardDeck deck2;

    /**
     * Create a new Player object
     */
    @Before
    public void setUp() {
        player1 = new Player(1, 2, new Object());
    }

    /** Testing: outputToFileDraw
     * Test that the output to the file is correct when a card is drawn from a deck
     */
    @Test
    public void testOutputToFileDraw(){
        FileOutputHandler outputHandler = new FileOutputHandler("player1_output.txt", 1, 2);
        // Create a new Object to write to a file

        Card newPickedUpCard = new Card(10);
        outputHandler.outputToFileDraw(newPickedUpCard);
        // Output to the file the card that has been picked up

        String expectedStringToWriteToFile = "player 1 draws a 10 from deck 2";
        String lineInFile = "";


        FileReader fileReader;
        try {
            fileReader = new FileReader("player1_output.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // Read the file

            lineInFile = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Incorrect data written to file", expectedStringToWriteToFile, lineInFile);
        // Check that the correct information was recorded in the file
    }

    /** Testing: outputToFileDiscard
     * Test that the output is as expected when a card is discarded from a player's hand
     */
    @Test
    public void testOutputToFileDiscard(){
        FileOutputHandler outputHandler = new FileOutputHandler("player1_output.txt", 1, 2);

        Card cardToDiscard = new Card(10);
        outputHandler.outputToFileDiscard(cardToDiscard);
        // Write the the file the card that has been discarded

        String expectedStringToWriteToFile = "player 1 discards a 10 to deck 1";
        String lineInFile = "";


        FileReader fileReader;
        try {
            fileReader = new FileReader("player1_output.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            lineInFile = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Incorrect data written to file", expectedStringToWriteToFile, lineInFile);
        // Check that the information regarding the discarded card is as expected
    }

    /** Testing: outputToFileCurrentHand
     * Test that the current hand of the player is correctly recorded to the player output file
     */
    @Test
    public void testOutputToFileCurrentHand(){
        FileOutputHandler outputHandler = new FileOutputHandler("player1_output.txt", 1, 2);
        player1.addCardToHand(new Card(5));
        player1.addCardToHand(new Card(8));
        player1.addCardToHand(new Card(2));
        player1.addCardToHand(new Card(1));

        CardDeck playerHand = player1.getPlayerHand();
        // Get the player's hand

        String expectedStringToWriteToFile = "player 1 current hand is 5 8 2 1 ";
        String lineInFile = "";

        outputHandler.outputToFileCurrentHand(playerHand);
        // Write the player's hand to the file

        FileReader fileReader;
        try {
            fileReader = new FileReader("player1_output.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // Read the file to get the data
            lineInFile = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Incorrect data written to file", expectedStringToWriteToFile, lineInFile);
        // Check that the expected data was correctly recorded in the file
    }

    /** Testing: outputToFileInitialHand
     * Test that the initial hand of the player is outputted correctly to the file
     */
    @Test
    public void testOutputToFileInitialHand(){
        FileOutputHandler outputHandler = new FileOutputHandler("player1_output.txt", 1, 2);
        player1.addCardToHand(new Card(5));
        player1.addCardToHand(new Card(8));
        player1.addCardToHand(new Card(2));
        player1.addCardToHand(new Card(1));

        CardDeck playerHand = player1.getPlayerHand();

        String expectedStringToWriteToFile = "player 1 initial hand 5 8 2 1 ";
        String lineInFile = "";

        outputHandler.outputToFileInitialHand(playerHand);
        // Write the player's initial hand to the file

        FileReader fileReader;
        try {
            fileReader = new FileReader("player1_output.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            lineInFile = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Incorrect data written to file", expectedStringToWriteToFile, lineInFile);
        // Check that the player's initial hand was correctly recorded in the file
    }

    /** Testing: outputToFilePlayerWins
     * Test that when a player wins the game, the correct information is outputted to the file
     */
    @Test
    public void testOutputToFilePlayerWins(){
        FileOutputHandler outputHandler = new FileOutputHandler("player1_output.txt", 1, 2);
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(1));

        CardDeck playerHand = player1.getPlayerHand();

        String[] expectedOutput = new String[3];
        expectedOutput[0] = "player 1 wins ";
        expectedOutput[1] = "player 1 exits ";
        expectedOutput[2] = "player 1 final hand: 1 1 1 1 ";


        outputHandler.outputToFilePlayerWins(playerHand);

        FileReader fileReader;
        try {
            fileReader = new FileReader("player1_output.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // Read each line of the file

            for (int i = 0; i<3; i++){
                assertEquals("Incorrect data written to file", expectedOutput[i], bufferedReader.readLine());
                // Check that for each line in the file, the data is correct
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Testing: outputToFilePlayerLost
     * Test that the correct information is outputted to the player output file when the player
     * has lost
     */
    @Test
    public void testOutputToFilePlayerLost(){
        FileOutputHandler outputHandler = new FileOutputHandler("player1_output.txt", 1, 2);
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(2));
        player1.addCardToHand(new Card(3));
        player1.addCardToHand(new Card(4));

        CardDeck playerHand = player1.getPlayerHand();

        String[] expectedOutput = new String[3];
        expectedOutput[0] = "player 2 has informed player 1 that player 2 has won ";
        expectedOutput[1] = "player 1 exits ";
        expectedOutput[2] = "player 1 hand: 1 2 3 4 ";


        outputHandler.outputToFilePlayerLost(playerHand, 2);

        FileReader fileReader;
        try {
            fileReader = new FileReader("player1_output.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            for (int i = 0; i<3; i++){
                // Iterate through each expected line of output
                assertEquals("Incorrect data written to file", expectedOutput[i], bufferedReader.readLine());
                // Check that the data read from the file is the same as the expected output
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Clean up test to ensure test independence
     */
    @After
    public void tearDown() {

    }

}
