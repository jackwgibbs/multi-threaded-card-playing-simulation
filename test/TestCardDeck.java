import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCardDeck {
    private CardDeck deck1;
    private Card card1;
    private Card card2;
    private Card card3;

    /**
     * Create a new CardDeck object and 3 Card objects
     */
    @Before
    public void setUp() {
        deck1 = new CardDeck();
        card1 = new Card(5);
        card2 = new Card(3);
        card3 = new Card(9);
    }

    /** Testing: addCard
     * Check that when a card is added to the CardDeck, the number of cards in the deck
     * is incremented correctly
     */
    @Test
    public void testAddCardNumberOfCardsIncremented() {
        int numberOfCardsBefore = deck1.getCurrentNumberOfCards();
        // Get the number of cards in the deck before a card is added

        deck1.addCard(card1);
        int numberOfCardsAfter = deck1.getCurrentNumberOfCards();
        // Get the number of cards in the deck after a card is added

        assert (numberOfCardsAfter == numberOfCardsBefore + 1) : "Card incrementation is incorrect";
        // Ensure that the number of cards in the deck after a card is added is 1 more than before
    }


    /** Testing: addCard
     *  Test that the card that is added to a deck is added to the bottom of the deck
     */
    @Test
    public void testAddCardIsAdded() {
        deck1.addCard(card1);
        deck1.addCard(card2);
        // Add 2 cards to the CardDeck

        int lastPositionInDeck = deck1.getCurrentNumberOfCards();
        Card cardAtBackOfDeck = deck1.getDeck()[lastPositionInDeck-1];
        // Retrieve the last card in the deck

        assertEquals ("Added card is not at the bottom of the deck", cardAtBackOfDeck, card2);
        // Ensure that the last card added is the same as the card at the bottom of the deck
    }

    /** Testing: topCard
     * Test that the card that is returned from this method is the top card from the deck
     */
    @Test
    public void testTopCard() {
        deck1.addCard(card1);
        deck1.addCard(card2);
        // Add 2 cards to the CardDeck

        Card topCard = deck1.getTopCard();
        // Get the top card from the deck

        assertEquals("The card on the top of the deck is not as expected", topCard, card1);
        // Check that the card added to the deck first is the same as the top card on the deck
    }

    /** Testing: topCard
     * Test that when the topCard method is called the number of cards in the deck
     * is decremented to reflect this
     */
    @Test
    public void testTopCardDecremented() {
        deck1.addCard(card1);
        deck1.addCard(card2);
        Card topCard = deck1.getTopCard();
        // Get the top card from the deck, thus removing it from the deck

        int numberOfCardsInDeck = deck1.getCurrentNumberOfCards();

        assertEquals("There should only be 1 card in the deck", numberOfCardsInDeck, 1);
        // Check that the number of cards in the deck is 1, since a card has been removed
    }

    /** Testing: removeCardFromDeck
     * Test that the correct card is removed from the deck
     */
    @Test
    public void testRemoveCardFromDeck(){
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.removeCardFromDeck(card1);
        // Remove card1 from the CardDeck

        int numberOfCardsInDeck = deck1.getCurrentNumberOfCards();
        Card[] cardsInDeck = deck1.getDeck();
        for (int i=0; i<numberOfCardsInDeck; i++){
            // Iterate through the cards in deck1`
            if (cardsInDeck[i] == card1){
                fail("Card should no longer be in card deck");
            }
        }
        // Check that the correct card has been removed, as it is no longer in the deck
    }

    /** Testing: removeCard
     * Test that when a card is removed from the deck that the number of cards in that
     * deck is decremented
     */
    @Test
    public void testRemoveCardFromDeckNumberOfCardsIsDecremented(){
        deck1.addCard(card1);
        deck1.addCard(card2);
        int numberOfCardsInDeck = deck1.getCurrentNumberOfCards();
        // Get the number of cards before a card is removed

        deck1.removeCardFromDeck(card1);
        int numberOfCardsInDeckAfterRemoval = deck1.getCurrentNumberOfCards();
        // Get the number of cards after a card is removed

        assertEquals("Number of cards has not been decremented correctly", numberOfCardsInDeckAfterRemoval, numberOfCardsInDeck -1);
        // Number of cards should equal 1, as a card has been removed from the deck
    }

    /** Testing: isNotEmpty
     * Check that this deck returns that it is not empty as it has cards in it
     */
    @Test
    public void testIsNotEmptyFalse(){
        deck1.addCard(card1);
        deck1.addCard(card2);
        boolean isCardDeckEmpty = deck1.isNotEmpty();

        assert(isCardDeckEmpty);
    }

    /** Testing: isNotEmpty
     * As this deck has no cards, the method should return that it is empty
     */
    @Test
    public void testIsNotEmptyTrue(){
        boolean isCardDeckEmpty = deck1.isNotEmpty();

        assert(!isCardDeckEmpty);
    }

    /** Testing: numberOfCardsInDeck
     * Test that the correct number of cards in the deck is calculated
     */
    @Test
    public void testNumberOfCardsInDeck(){
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.addCard(card3);

        Card[] cardsInDeck = deck1.getDeck();
        int numberOfCardsInDeckAttribute = deck1.getCurrentNumberOfCards();
        // Get the number of cards in the deck

        int numberOfCardsInDeckCalculated = 0;
        for (int i = 0; i < cardsInDeck.length; i ++)
            if (cardsInDeck[i] != null)
                // Iterate through the deck to check if the position has a card in it

                numberOfCardsInDeckCalculated ++;
            else{
                break;
            }

        assertEquals("The number of cards attribute is incorrect", numberOfCardsInDeckAttribute, numberOfCardsInDeckCalculated);
        // Check that the number of cards in the deck is as expected
    }

    /**
     * Clean up test to ensure test independence
     */
    @After
    public void tearDown() {

    }
}