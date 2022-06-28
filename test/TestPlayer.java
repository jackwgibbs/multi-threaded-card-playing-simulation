import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestPlayer {
    Player player1;
    CardDeck deck1;
    CardDeck deck2;

    /**
     * Create a new Player object and 2 CardDeck objects, 1 deck where
     * cards are being picked up from and 1 where cards are discarded to
     */
    @Before
    public void setUp() {
        player1 = new Player(1, 2, new Object());
        deck1 = new CardDeck();
        deck2 = new CardDeck();
    }

    /** Testing: canPickUp
     * The deck that the player is picking up from is empty so a card cannot be picked up
     */
    @Test
    public void testCanPickUpFalse(){
        // Create a deckList array and add the two decks to it
        CardDeck[] deckList = new CardDeck[3];
        deckList[1] = deck1;
        deckList[2] = deck2;
        player1.setDeckList(deckList);

        // Add 4 cards to the players hand
        player1.addCardToHand(new Card(8));
        player1.addCardToHand(new Card(7));
        player1.addCardToHand(new Card(2));
        player1.addCardToHand(new Card(11));

        // Call the canPickUp method on the player to check whether they can pick up
        // from an empty deck
        boolean canPlayerPickUpFromDeck = player1.canPickUp();

        assertFalse("Deck is empty so player can not pick up", canPlayerPickUpFromDeck);

    }

    /** Testing: canPickUp
     * Cards are in the deck that the player is picking up from
     * therefore the player can pick up a card
     */
    @Test
    public void testCanPickUpTrue(){
        // Add 4 cards to deck1
        deck1.addCard(new Card(5));
        deck1.addCard(new Card(12));
        deck2.addCard(new Card(1));
        deck2.addCard(new Card(9));

        // Create a deckList array and add the two decks
        CardDeck[] deckList = new CardDeck[3];
        deckList[1] = deck1;
        deckList[2] = deck2;
        player1.setDeckList(deckList);

        // Add 4 cards to the players hand
        player1.addCardToHand(new Card(8));
        player1.addCardToHand(new Card(7));
        player1.addCardToHand(new Card(2));
        player1.addCardToHand(new Card(11));

        // Call the canPickUp method on the player1 object to check
        // whether they can pick up from a deck with cards
        boolean canPlayerPickUpFromDeck = player1.canPickUp();

        assertTrue("Deck is  not empty so player can pick up", canPlayerPickUpFromDeck);
    }

    /** Testing: addCardToHand
     * Add a card to the player's hand
     * It should be added at the bottom of the hand
     */
    @Test
    public void testAddCardToHand(){
        // Add 3 cards to the players hand
        player1.addCardToHand(new Card(8));
        player1.addCardToHand(new Card(7));
        player1.addCardToHand(new Card(2));

        // Add a fourth card to the players hand - this
        // is the card we will be testing has been added
        Card cardToAdd = new Card(1);
        player1.addCardToHand(cardToAdd);

        // Get the cards in the players hand
        CardDeck playerHand = player1.getPlayerHand();
        Card[] cardsInPlayerHand = playerHand.getDeck();

        // Get the number of cards in the players hand
        int numberOfCardsInPlayerHand = playerHand.getCurrentNumberOfCards();

        // Check that the last card added to the players hand is at the back of the
        // players hand
        if (cardsInPlayerHand[numberOfCardsInPlayerHand-1] != cardToAdd){
            // If the card is not the same, the test has failed
            fail("Card not added to bottom of hand");
        }
    }

    /** Testing: checkPlayerWon
     * PLayer has a hand only containing cards with the same value
     * Therefore the player has won the game
     */
    @Test
    public void testCheckPlayerWonTrue(){
        // Add 4 cards with the same value to the players hand
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(1));

        // Call the checkPlayerWon to check whether the player has won
        boolean hasPlayerWon = player1.checkPlayerWon();
        assertTrue("Player should have declared they have won", hasPlayerWon);
    }


    /** Testing: checkPlayerWon
     * The player's cards do not all have the same value
     * Therefore the player has not won
     */
    @Test
    public void testCheckPlayerWonFalse(){
        //Add 4 different cards to the players hand
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(2));
        player1.addCardToHand(new Card(3));
        player1.addCardToHand(new Card(4));

        // Call the checkPlayerWon method to check whether the player has won
        boolean hasPlayerWon = player1.checkPlayerWon();
        assertFalse("Player should not have declared they have won", hasPlayerWon);
    }

    /** Testing: chooseCardToDiscard
     * The player should select a card which is not equal to their player number
     */
    @Test
    public void testChooseCardToDiscard(){
        // Add two cards to the players hand
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(1));

        // Add a different card which is to be removed to the players hand
        Card cardNotOfCorrectDenomination = new Card(9);
        player1.addCardToHand(cardNotOfCorrectDenomination);

        // Add a final card to the players hand
        player1.addCardToHand(new Card(1));

        // Call the chooseCardToDiscard method on the player object
        Card cardToDiscard = player1.chooseCardToDiscard();

        // Check the expected card to remove and actual card to remove are the same
        assertEquals("Player has chosen to discard a card of their own denomination", cardNotOfCorrectDenomination, cardToDiscard);
    }

    /** Testing: takeTurn
     * This tests a number of smaller methods
     * The player should have a card with the value of 4 added to their deck
     * The player should then choose the card with the value of 7 to discard
     */
    @Test
    public void testTakeTurn(){
        // Add 4 different cards to players hand
        player1.addCardToHand(new Card(1));
        player1.addCardToHand(new Card(7));
        player1.addCardToHand(new Card(2));
        player1.addCardToHand(new Card(11));

        // Add 4 cards to deck 1
        deck1.addCard(new Card(5));
        deck1.addCard(new Card(12));
        deck1.addCard(new Card(1));
        deck1.addCard(new Card(9));

        // Add 4 cards to deck 2
        deck2.addCard(new Card(4));
        deck2.addCard(new Card(2));
        deck2.addCard(new Card(11));
        deck2.addCard(new Card(2));

        // Add decks to a deck array
        CardDeck[] deckList = new CardDeck[3];
        deckList[1] = deck1;
        deckList[2] = deck2;
        player1.setDeckList(deckList);

        // Get the players hand before they take their turn
        CardDeck deckBeforeTurn = player1.getPlayerHand();
        Card[] playerCardsBeforeTurn = deckBeforeTurn.getDeck();

        // Get the card at position 3 in the players hand before
        // they take their turn
        Card fourthCardInHandBefore = playerCardsBeforeTurn[3];

        // Call the takeTurn method on the player object
        player1.takeTurn();

        // Get the players hand after they take their turn
        CardDeck deckAfterTurn = player1.getPlayerHand();
        Card[] playerCardsAfterTurn = deckAfterTurn.getDeck();

        // Get the card at position 2 in the players hand
        // after they take their turn
        Card thirdCardInHandAfter = playerCardsAfterTurn[2];

        // Check both these cards are the same
        assertEquals("4th card before should now be in position 3 in hand", fourthCardInHandBefore, thirdCardInHandAfter);

    }

    @Test
    /** Testing: writeDeckOutputFile
     * Write the contents of the player's hand to a file
     * Ensure that the correct cards have been recorded in the correct order
     */
    public void testWriteDeckOutputFile(){
        // Add 4 cards to deck 1
        deck1.addCard(new Card(5));
        deck1.addCard(new Card(12));
        deck1.addCard(new Card(1));
        deck1.addCard(new Card(9));

        // Add deck 1 to the deck array
        CardDeck[] deckList = new CardDeck[3];
        deckList[1] = deck1;
        player1.setDeckList(deckList);

        // Call the writeOutputFile method with parameter 1,
        // meaning deck 1
        Player.writeDeckOutputFile(1);

        String expectedStringToWriteToFile = "deck1 contents: 5 12 1 9 ";
        String lineInFile = "";

        // Read the written file to test it wrote the expected output
        FileReader fileReader;
        try {
            fileReader = new FileReader("deck1.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            lineInFile = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Incorrect data written to file", expectedStringToWriteToFile, lineInFile);

    }

    /**
     * Clean up test to ensure test independence
     */
    @After
    public void tearDown() {

    }

}
