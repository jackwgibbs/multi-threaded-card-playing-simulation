import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Player implements Runnable{
    private static CardDeck[] deckList;
    private static int winningPlayer;
    private final Object OBJECT_LOCK;
    private final int PLAYER_NUMBER;
    private final int PREVIOUS_PLAYER;
    private CardDeck playerHand;
    private String playerOutputFileName;
    private FileOutputHandler fileOutputHandler;
    private boolean hasPlayerWon;


    public Player(int playerNumber, int totalNumberOfPlayers, Object object) {
        this.PLAYER_NUMBER = playerNumber;
        this.OBJECT_LOCK = object;

        // playerHand is an instance of the CardDeck class which manages a deck of cards
        this.playerHand = new CardDeck();
        this.hasPlayerWon = false;

        // The name of this player's output file
        this.playerOutputFileName = "player" + playerNumber + "_output.txt";

        // Determine previous player(which is also the deck to the left).
        if (playerNumber == 1) {
            this.PREVIOUS_PLAYER = totalNumberOfPlayers;
        }
        else {
            this.PREVIOUS_PLAYER = PLAYER_NUMBER - 1;
        }

        // Create a fileOutputHandler to manage the players output file
        this.fileOutputHandler = new FileOutputHandler(playerOutputFileName, playerNumber, PREVIOUS_PLAYER);
    }

    /**
     * Run method for the player thread to run when started
     */
    @Override
    public void run() {
        fileOutputHandler.outputToFileInitialHand(playerHand);
        // Output the player's hand after the cards have been dealt

        while (!Thread.currentThread().isInterrupted()) {
            // Check that the thread hasn't been interrupted
            while (!canPickUp()) {
                // Player cannot pick up a card if the deck they are picking up from is empty
                try {
                    synchronized (OBJECT_LOCK) {
                        // Player waits until a card is discarded, when all threads are notified
                        OBJECT_LOCK.wait();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Player picks up and discards a card, providing they can take their turn
            checkTakeTurn();

            synchronized (OBJECT_LOCK) {
                /* All players are notified that cards have been moved
                Therefore players may now be able to pick up a card
                 */
                OBJECT_LOCK.notifyAll();
            }
        }

        // When game has ended.
        // Write to file the deck
        writeDeckOutputFile(PREVIOUS_PLAYER);


        if (!hasPlayerWon) {
            // If player hasn't won, output their losing hand to the file
            fileOutputHandler.outputToFilePlayerLost(playerHand, winningPlayer);

        }
    }

    /**
     * Method which acts as the player taking their turn.
     * Method calls the relevant methods which allow the player
     * to take their turn
     */
    public void takeTurn(){
        // Get the top card from the deck to the left
        Card newCard = deckList[PREVIOUS_PLAYER].getTopCard();

        // Add the card to the player's hand
        playerHand.addCard(newCard);

        // Write to the file the card that was picked up
        fileOutputHandler.outputToFileDraw(newCard);

        // Select a card from the hand to discard
        Card cardToDiscard = chooseCardToDiscard();

        // Remove the chosen card from the deck
        playerHand.removeCardFromDeck(cardToDiscard);

        // Add the removed card to the deck on the right
        deckList[PLAYER_NUMBER].addCard(cardToDiscard);

        // Write to the file the card that was discarded
        fileOutputHandler.outputToFileDiscard(cardToDiscard);

        // Write to the file the current hand of the player
        fileOutputHandler.outputToFileCurrentHand(playerHand);
    }

    /**
     * Method to check whether the player can take their turn.
     * If they have won or if the player thread has been interrupted, they cannot
     * take their turn
     */
    public void checkTakeTurn() {
        if (!checkPlayerWon() && !Thread.currentThread().isInterrupted()){
            // Check that the player hasn't won and that the thread hasn't been interrupted
            takeTurn();
        }
    }

    /**
     * Checks whether the player can pick up a card from the deck to their left
     * @return Returns true if the player can pick up
     */
    public boolean canPickUp() {
        // Check the deck the player is picking up from is not empty
        return deckList[PREVIOUS_PLAYER].isNotEmpty();
    }

    /**
     * Takes a number and returns its square root.
     * @param cardToAddToHand card to add to the players hand
     */
    public void addCardToHand(Card cardToAddToHand) {
        // Add a card to the player's hand
        playerHand.addCard(cardToAddToHand);
    }

    /**
     * Checks whether a player has won.
     * Code that declares win is synchronised on the common OBJECT_LOCK
     * to ensure that only one user can be winner.
     * @return Returns true if the player has won
     */
    public boolean checkPlayerWon(){
        // Assume player has won, method looks to prove this wrong
        boolean hasWon = true;

        Card[] playersCards = playerHand.getDeck();
        for (int i = 0; i < 3; i++){
            if (playersCards[i].getCardNumber() != playersCards[i + 1].getCardNumber()) {
                // Check that the cards all have the same value
                // If cards have different values then the player has not won
                hasWon = false;
                break;
            }
        }

        //Use synchronised to ensure that only ONE player can declare a win.
        synchronized (OBJECT_LOCK){
            if (hasWon && !Thread.currentThread().isInterrupted()){
                // Set the winning player by the player's number
                hasPlayerWon = true;
                winningPlayer = PLAYER_NUMBER;

                // Get all running threads
                Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                for ( Thread thread : threadSet){
                    // Interrupt all threads
                    thread.interrupt();
                }
                // Output to the file that the player has won
                fileOutputHandler.outputToFilePlayerWins(playerHand);

                // Print in the terminal that the player has won
                System.out.println("player " + PLAYER_NUMBER + " wins");
            }
        }
        return hasWon;
    }

    /**
     * Selects the card to discard from the users hand.
     * Card must be one that is not of their preferred number.
     * @return The card to remove from the players hand.
     */
    public Card chooseCardToDiscard() {
        // Get the player's current hand
        Card[] deck = playerHand.getDeck();

        for (int i = 0; i <= 4; i++) {
            if (deck[i].getCardNumber() != PLAYER_NUMBER) {
                // Check if the card number is equal to the player number
                // If it is not equal then choose this card to discard
                return deck[i];
            }
        }
        return deck[0];
    }

    /**
     * Sets the array of all the decks.
     * @param deckListIn The array of decks.
     */
    public void setDeckList(CardDeck[] deckListIn){
        // Set deckList to the provided list of CardDeck
        deckList = deckListIn;
    }

    /**
     * Write the output file for the deck.
     * @param deckNumber The number of the deck
     */
    public static void writeDeckOutputFile(int deckNumber){
        // Determine the name of the deck output file
        String fileName = "deck" + deckNumber + ".txt";

        // Gets the values of the cards in the deck, in string form
        // ready to write to the file.
        String cardDeckString = deckList[deckNumber].cardDeckToString();

        try {
            // Create the file
            File file = new File(fileName);

            if (!file.createNewFile()){
                //Clear old file if exists
                FileWriter myWriter = new FileWriter(fileName,false);
                myWriter.close();
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred making the deck output file.");
            e.printStackTrace();
        }

        try {
            // Write the deck's contents to the file
            FileWriter myWriter = new FileWriter(fileName, true);
            myWriter.write("deck"+ deckNumber +" contents: "+ cardDeckString);
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred writing to the deck output file.");
            e.printStackTrace();
        }
    }

    /**
     * Get the players hand
     * @return The players current hand
     */
    public CardDeck getPlayerHand(){
        return playerHand;
    }
}