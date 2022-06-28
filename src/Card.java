public class Card {

    // Card number final attribute
    private final int CARD_NUMBER;

    public Card(int cardNumber){
        // Initialise card number
        this.CARD_NUMBER = cardNumber;
    }

    /**
     * Method to return the card number of the specific card instance
     * @return Returns the card number
     */
    public int getCardNumber(){
        return CARD_NUMBER;
    }
}