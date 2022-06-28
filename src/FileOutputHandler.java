import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutputHandler {

    private String nameOfFile;
    private int playerNumber;
    private int previousPlayer;

    public FileOutputHandler(String nameOfFile, int playerNumber, int previousPlayer) {

        // The name of the output file
        this.nameOfFile = nameOfFile;

        // The current player's number
        this.playerNumber = playerNumber;

        // The number of the previous player in the circle
        this.previousPlayer = previousPlayer;

        try {
            File file = new File(nameOfFile);
            if (!file.createNewFile()){
                // If the file with that name already exists

                // Clear the file so that it is empty
                FileWriter myWriter = new FileWriter(nameOfFile,false);

                // Close the file
                myWriter.close();
            }
        }catch (IOException e) {
            System.out.println("An error occurred making the player output file.");
            e.printStackTrace();
        }
    }

    /**
     * Outputs to the player file the card the player draws
     * @param newCard the card the player has drawn.
     */
    public void outputToFileDraw(Card newCard) {
        try {
            // Append is true, the file will not overwrite itself
            FileWriter myWriter = new FileWriter(nameOfFile, true);

            // Add to the file the card that this player has picked up
            myWriter.write("player " + playerNumber + " draws a " + newCard.getCardNumber() + " from deck " + previousPlayer + "\n");
            myWriter.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }catch (IOException e) {
            System.out.println("An error occurred writing to the player output file.");
            e.printStackTrace();
        }
    }

    /**
     * Outputs to the player file the card the player discards
     * @param cardToDiscard the card the player has discarded
     */
    public void outputToFileDiscard(Card cardToDiscard) {
        // Show the card that the player has discarded in the file
        try {
            FileWriter myWriter = new FileWriter(nameOfFile, true);
            myWriter.write("player " + playerNumber + " discards a " + cardToDiscard.getCardNumber() + " to deck " + playerNumber + "\n");
            myWriter.close();
        }catch (IOException e) {
            System.out.println("An error occurred writing to the player output file.");
            e.printStackTrace();
        }
    }
    /**
     * Outputs to the player file the players current hand
     * @param currentHand the players current hand
     */

    public void outputToFileCurrentHand(CardDeck currentHand) {
        // Output the player's current hand to te file
        try {
            FileWriter myWriter = new FileWriter(nameOfFile, true);

            myWriter.write("player " + playerNumber + " current hand is " + currentHand.cardDeckToString() + "\n");
            myWriter.close();
        }catch (IOException e) {
            System.out.println("An error occurred writing to the player output file.");
            e.printStackTrace();
        }
    }

    /**
     * Outputs to the player file the players initial hand
     * @param currentHand the players initial hand
     */
    public void outputToFileInitialHand(CardDeck currentHand) {
        // Output the starting hand of the player, after the cards have been dealt
        try {
            FileWriter myWriter = new FileWriter(nameOfFile, true);

            myWriter.write("player " + playerNumber + " initial hand " + currentHand.cardDeckToString() + "\n");
            myWriter.close();
        }catch (IOException e) {
            System.out.println("An error occurred writing to the player output file.");
            e.printStackTrace();
        }
    }

    /**
     * Outputs to the player file the players final hand
     * @param finalHand the players final hand
     */
    public void outputToFilePlayerWins(CardDeck finalHand) {
        // Output when the player wins, including their final hand
        try {
            FileWriter myWriter = new FileWriter(nameOfFile, true);
            myWriter.write("player " + playerNumber + " wins \n");
            myWriter.write("player " + playerNumber + " exits \n");
            myWriter.write("player " + playerNumber + " final hand: " + finalHand.cardDeckToString() + "\n");
            myWriter.close();
        }catch (IOException e) {
            System.out.println("An error occurred writing to the player output file.");
            e.printStackTrace();
        }
    }

    /**
     * Outputs to the player file that they have lost
     * @param finalHand the players final hand
     * @param winningPlayerNumber the number of the winning player
     */
    public void outputToFilePlayerLost(CardDeck finalHand, int winningPlayerNumber) {
        // Output when another player has won, including which player won
        try {
            FileWriter myWriter = new FileWriter(nameOfFile, true);
            myWriter.write("player " + winningPlayerNumber + " has informed player " + playerNumber + " that player " + winningPlayerNumber + " has won \n");
            myWriter.write("player " + playerNumber + " exits \n");
            myWriter.write("player " + playerNumber + " hand: " + finalHand.cardDeckToString() + "\n");
            myWriter.close();
        }catch (IOException e) {
            System.out.println("An error occurred writing to the player output file.");
            e.printStackTrace();
        }
    }
}
