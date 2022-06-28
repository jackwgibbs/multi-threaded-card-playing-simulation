import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class TestCardGame {

    @Before
    public void setUp() {

    }

    /** Testing: checkPackValidity
     * Test whether a valid input pack is identified as being valid
     * by reading in a valid pack file for four players.
     */
    @Test
    public void testCheckPackValidityValid() {
        // Open the valid text file
        File myFile = new File("test/fourValidTest.txt");
        int numOfPlayers = 4;

        // Call the checkPackValidity method with the valid file and number of players
        boolean valid = CardGame.checkPackValidity(myFile, numOfPlayers);

        assertTrue("Pack is valid but has been recognised as non valid", valid);
    }

    /** Testing: checkPackValidityNotValid1
     * Test whether a non-valid input pack is identified as being non-valid
     * by reading in a non-valid pack file for four players that contains letters
     */
    @Test
    public void testCheckPackValidityValidNotValid1() {
        // Open the invalid pack file
        File myFile = new File("test/fourInvalidTest1.txt");
        int numOfPlayers = 4;
        // Call the checkPackValidity method with the invalid file and number of players
        boolean valid = CardGame.checkPackValidity(myFile, numOfPlayers);

        assertFalse("Pack is not valid but has been recognised as valid", valid);
    }

    /** Testing: checkPackValidity
     * Test whether a non-valid input pack is identified as being non-valid
     * by reading in a non-valid pack file for four players that contains the wrong number of integers
     */
    @Test
    public void testCheckPackValidityValidValid2() {
        // Open the invalid pack file
        File myFile = new File("test/fourInvalidTest2.txt");
        int numOfPlayers = 4;
        // Call the checkPackValidity method with the invalid file and number of players
        boolean valid = CardGame.checkPackValidity(myFile, numOfPlayers);

        assertFalse("Pack is not valid but has been recognised as valid", valid);
    }

    /** Testing: checkPackValidity
     * Test whether a non-valid input pack is identified as being non-valid
     * by reading in a non-valid pack file for four players that contains negative integers
     */
    @Test
    public void testCheckPackValidityValidValid3() {
        // Open the invalid pack file
        File myFile = new File("test/fourInvalidTest3.txt");
        int numOfPlayers = 4;
        // Call the checkPackValidity method with the invalid file and number of players
        boolean valid = CardGame.checkPackValidity(myFile, numOfPlayers);

        assertFalse("Pack is not valid since it contains a negative integer but has been recognised as valid", valid);
    }

    /** Testing: checkPackValidity
     * Test whether a non-valid input pack is identified as being non-valid
     * by trying to read in a pack that does not exist.
     */
    @Test
    public void testCheckPackValidityValidFileNotFound() {
        // Open file that does not exist
        File myFile = new File("fileDoesNotExist.txt");
        int numOfPlayers = 4;
        // Call the checkPackValidity method with the invalid file and number of players
        boolean valid = CardGame.checkPackValidity(myFile, numOfPlayers);

        assertFalse("File does not exist so should not try to be opened", valid);
    }

    /** Testing: dealCardsToPlayers
     * Create 16 cards, 1 to 16 and deal them out to player 1. Since there are only
     * 2 players, we should expect that player 1 gets cards 1 3 5 and 7. Test whether
     * this is the case and that the dealCardsToPlayers works correctly in a round robin fashion.
     */
    @Test
    public void testDealCardsToPlayers() {
        Object myObj = new Object();
        Player[] playerList = new Player[3];
        // Create two Player objects and store them at the correct index
        // in the playerList array
        playerList[1] = new Player(1, 2, myObj);
        playerList[2] = new Player(2, 2, myObj);

        // Create a deckList array and store two new CardDeck instances in it
        CardDeck[] deckList = new CardDeck[3];
        deckList[1] = new CardDeck();
        deckList[2] = new CardDeck();

        // Create 16 new Card instances and add them to a new card array allCards
        Card[] allCards = new Card[17];
        for (int i = 0; i<=16; i++){
            allCards[i] = new Card(i+1);
        }

        // Call the method to deal the cards to the players.
        CardGame.dealCardsToPlayers(playerList, allCards, deckList);

        // Get the cards of player 1 that have been dealt to them
        CardDeck player1Deck = playerList[1].getPlayerHand();
        Card[] player1Hand = player1Deck.getDeck();

        //We expect the cards in the players hand to be 1, 3, 5 and 7
        int expectedCardValue = 1;
        for (int i = 0; i<4; i++){
            //Get the cards card number
            int cardNumber = player1Hand[i].getCardNumber();
            assertEquals("Cards have been dealt incorrectly", expectedCardValue, cardNumber);

            // Increment expectedCardValue by 2
            expectedCardValue = expectedCardValue + 2;
        }
    }

    /** Testing: isNumberOfPlayersValid
     * Test whether 2 is a valid number of players. 2 is an edge case which should return true.
     */
    @Test
    public void testIsNumberOfPlayersValid1(){
        // Call the isNumberOfPlayersValid method with a valid parameter of 2
        boolean isValidNumberOfPlayers = CardGame.isNumberOfPlayersValid(2);

        assertTrue("2 is a valid number of players so should not return false", isValidNumberOfPlayers);
    }

    /** Testing: isNumberOfPlayersValid
     * Test whether 10 is a valid number of players. 10 is an edge case which should return true.
     */
    @Test
    public void testIsNumberOfPlayersValid2(){
        // Call the isNumberOfPlayersValid method with an valid parameter of 10
        boolean isValidNumberOfPlayers = CardGame.isNumberOfPlayersValid(10);

        assertTrue("10 is a valid number of players so should not return false", isValidNumberOfPlayers);
    }

    /** Testing: isNumberOfPlayersValid
     * Test whether 5 is a valid number of players. 5 is in the middle of the accepted range and should
     * return true.
     */
    @Test
    public void testIsNumberOfPlayersValid3(){
        // Call the isNumberOfPlayersValid method with an valid parameter of 5
        boolean isValidNumberOfPlayers = CardGame.isNumberOfPlayersValid(5);

        assertTrue("5 is a valid number of players so should not return false", isValidNumberOfPlayers);
    }

    /** Testing: isNumberOfPlayersValid
     * Test whether 12 is a valid number of players. 12 is an invalid number of players
     * which should return false.
     */
    @Test
    public void testIsNumberOfPlayersInvalid1(){
        // Call the isNumberOfPlayersValid method with an invalid parameter of 12
        boolean isValidNumberOfPlayers = CardGame.isNumberOfPlayersValid(12);

        assertFalse("12 is not a valid number of players so should not return true", isValidNumberOfPlayers);
    }

    /** Testing: isNumberOfPlayersValid
     * Test whether 0 is a valid number of players. 0 is an invalid number of players
     * and should return false
     */
    @Test
    public void testIsNumberOfPlayersInvalid2(){
        // Call the isNumberOfPlayersValid method with an invalid parameter of 0
        boolean isValidNumberOfPlayers = CardGame.isNumberOfPlayersValid(0);

        assertFalse("0 is not a valid number of players so should not return true", isValidNumberOfPlayers);
    }

    /** Testing: createPlayers
     * Test whether when the number of players is 4,  4 players are created and that the playerList
     * which is the array holding them has length 5.
     */
    @Test
    public void testCreatePlayers(){
        int numOfPlayers = 4;
        CardDeck[] deckList = new CardDeck[numOfPlayers+1];

        // Call the createPlayers method to create 4 new Player instances which
        // are returned and stored in an array of players called playerList
        Player[] playerList = CardGame.createPlayers(numOfPlayers, deckList);

        assertEquals("", numOfPlayers+1, playerList.length);
    }

    @After
    public void tearDown() {

    }

}
