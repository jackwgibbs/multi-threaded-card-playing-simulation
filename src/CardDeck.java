import java.util.Arrays;

public class CardDeck {

    private Card[] cardsInDeck = new Card[100];
    /* Array of cards with length 100
     Deck is set to a length of 100 as one thread may put down many cards
     without the player next to them picking any up in this time due to non-deterministic
      property of threads. */

    // Attribute for the number of cards that are currently in the deck
    private int currentNumberOfCards;

    public CardDeck() {
        // Initialise the number of cards in CardDeck as 0 at the beginning of the game
        //before any cards have been added to the deck
        currentNumberOfCards = 0;
    }

    /**
     * Takes a Card and adds it to the back of the deck
     * @param cardToAddToDeck The card to add to the deck
     */
    public void addCard(Card cardToAddToDeck) {
        // Adds the card provided to the deck of cards at the position currentNumberOfCards
        cardsInDeck[currentNumberOfCards] = cardToAddToDeck;

        // Increment the number of cards currently in the deck by 1
        currentNumberOfCards++;

    }

    /**
     * Removes and returns the first card in the deck.
     * The remaining cards are shifted along.
     * @return The card at the top of the deck
     */
    public Card getTopCard() {
        Card cardAtTopOfDeck = cardsInDeck[0];
        // Shift all the remaining cards along in the array so there are no gaps
        for (int i = 1; i < currentNumberOfCards; i++) {
            cardsInDeck[i - 1] = cardsInDeck[i];
        }

        // Decrement the number of cards by 1
        currentNumberOfCards--;

        // Return the card at the top of the deck
        return cardAtTopOfDeck;

    }

    /**
     * Removes a given card from the deck and shifts along the remaining cards
     * @param cardToRemoveFromDeck The card to remove from the deck
     */
    public void removeCardFromDeck(Card cardToRemoveFromDeck) {
        // Remove the card provided from the player hand

        //Find the index within the array of the card to remove
        int indexOfCardToRemove = Arrays.asList(cardsInDeck).indexOf(cardToRemoveFromDeck);

        // Shift over remaining cards in the deck to rewrite the card to remove
        for (int i = indexOfCardToRemove + 1; i < currentNumberOfCards; i++) {
            cardsInDeck[i - 1] = cardsInDeck[i];
        }

        // Decrement the number of cards by 1
        currentNumberOfCards--;
    }

    /**
     * Generates and returns the String representation of the current state
     * of the card deck. Used to output to the deck and player output file.
     * @return current String representation of the deck.
     */
    public String cardDeckToString() {
        String cardDeckStringRepresentation = "";
        for (int i = 0; i < currentNumberOfCards; i++) {
            cardDeckStringRepresentation = cardDeckStringRepresentation + cardsInDeck[i].getCardNumber() + " ";
        }

        //Return string representation of the deck
        return cardDeckStringRepresentation;
    }

    /**
     * Checks whether the deck is not empty
     * @return Returns False if the deck is empty
     */
    public boolean isNotEmpty() {
        // Checks that the CardDeck is not empty
        return currentNumberOfCards != 0;
    }

    /**
     * Returns the array of the deck
     * @return Returns the array of cards in the deck
     */
    public Card[] getDeck() {
        return cardsInDeck;
    }

    /**
     * Method to return the number of cards in the deck
     * @return RReturns the number of cards in the deck
     */
    public int getCurrentNumberOfCards() {
        return currentNumberOfCards;
    }
}