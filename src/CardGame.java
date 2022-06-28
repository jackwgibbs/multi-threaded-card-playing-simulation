import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CardGame {
    public static void main(String args[]){
        // Get the number of players in the game
        int numOfPlayers = getNumberOfPlayers();

        // Read in the cards, provided the inputted pack is valid
        Card[] allCards = readInValidPack(numOfPlayers);

        // Create array of card decks
        // Card deck 1 is stored at position 1 and so on...
        CardDeck[] deckList = new CardDeck[numOfPlayers + 1];

        // Create the card decks that the players will pick cards up from and
        // discard to
        for (int i = 1; i<= numOfPlayers; i++) {
            deckList[i] = new CardDeck();
        }

        // Create all the players and add them to a list
        Player[] playerList = createPlayers(numOfPlayers, deckList);

        // Deal the cards from the pack to the players in a round robin fashion
        dealCardsToPlayers(playerList, allCards, deckList);

        System.out.println("Game has begun... Winner will be announced below (providing there is one)");
        for (int i = 1; i<=numOfPlayers; i++){
            // Create threads for the game
            Thread thread = new Thread(playerList[i], Integer.toString(i));

            // Start each thread
            thread.start();
        }
    }

    /**
     * Check whether the number of players inputted is valid
     * @return true if the number of players is valid
     */
    public static boolean isNumberOfPlayersValid(int numberOfPlayers){
        boolean valid = false;
        if (numberOfPlayers < 2) {
            System.out.println("Invalid integer, too low");
        }
        else if (numberOfPlayers > 10) {
            System.out.println("Invalid integer, too high");
        }
        /* Ensure that the inputted number of players in within 2 and 10
        Return error messages if this is not the case
         */
        else {
            // If none of the other conditions have been met then the input is valid
            valid = true;
        }
        return valid;
    }

    /**
     * Get the number of players from the user via command line
     * @return the number the player inputted
     */
    public static int getNumberOfPlayers() {
        int numberOfPlayers = -1;
        boolean valid = false;
        /* Starting values of numberOfPlayers and valid mean that the game cannot start
        without a valid user input
        */

        while (!valid) {
            // Create a Scanner object to get the user input
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter number of players: ");
            if (scanner.hasNextInt()) {
                // Check that the user has inputted an integer
                // Get the integer value that the user has inputted
                numberOfPlayers = scanner.nextInt();
                valid = isNumberOfPlayersValid(numberOfPlayers);

            } else {
                // Return error message if the input was not an integer
                System.out.println("Please enter an integer");
            }
        }
        // Return the user inputted number of players
        return numberOfPlayers;
    }

    /**
     * Gets the name/path of the pack file
     * @return the name/path of the pack file.
     */
    public static String getPackName(){
        // User inputs a file name which is then returned
        String packPath;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter location of pack to load");
        packPath = scanner.nextLine();
        return packPath;
    }

    /**
     * Reads in a valid pack file and creates the Cards.
     * @param numOfPlayers The number of players in the game
     * @return an array of all cards made from the non-negative integers from the pack file
     */
    public static Card[] readInValidPack(int numOfPlayers) {
        boolean isPackValid;
        int count = 0;
        int cardNumber;
        File file;
        // Number of cards should be this length if the pack is valid
        Card[] allCards = new Card[8 * numOfPlayers];

        do {
            // Get the file name from the user
            String packFilePath = getPackName();

            // Create file with this filename
            file = new File(packFilePath);

            // Check that the inputted pack in valid
            isPackValid = checkPackValidity(file, numOfPlayers);
        }while (!isPackValid);

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                // Check that the file has another line, meaning there are more cards

                // Get the next integer in the file
                cardNumber = myReader.nextInt();

                // Create a new Card object with the number from the pack
                Card card = new Card(cardNumber);

                // Add the card to the list of cards for dealing
                allCards[count] = card;

                // Increment the count for adding to the list
                count ++;
            }
            myReader.close();

        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        // Return the list of cards
        return allCards;
    }

    /**
     * check the validity of the pack file
     * @param myObj the pack file
     * @param numOfPlayers the number of players in the pack file
     * @return Returns false if the pack file is invalid
     */
    public static boolean checkPackValidity(File myObj, int numOfPlayers) {
        //Count variable to keep track of how many lines are in the file
        int count = 0;
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if (myReader.nextInt() <=0) {
                    // Check that the integers are non-negative
                    System.out.println("Input pack does not contain 32 non-negative integers");
                    return false;
                }
                // Increment the count for the number of lines in the pack
                count ++;
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            // The filename couldn't be found in the current directory
            System.out.println("File not found");
            return false;
        }catch (Exception e){
            // The value found was not an integer
            System.out.println("Input pack does not contain 32 non-negative integers");
            return false;
        }

        if (count!=8*numOfPlayers){
            // The number of cards should be equal to 8 * the number of players
            System.out.println("Invalid pack, incorrect number of cards");
            return false;
        }

        // If the pack passes all the above conditions, it is valid.
        return true;
    }

    /**
     * Creates the Player objects and adds them to an array.
     * @param numOfPlayers the number of players in the game
     * @param deckList an array of all the card decks
     * @return an array of the created Player objects
     */
    public static Player[] createPlayers(int numOfPlayers, CardDeck[] deckList) {
        //Create an empty array to store the Player objects.
        //Player 1 will be stored at index 1 and etc...
        Player[] playerList = new Player[numOfPlayers+1];

        //Create an object to use as the common lock for the player threads
        Object lockObject = new Object();

        // Create the number of players specified by the user input
        for (int i =numOfPlayers; i>=1; i--){
            playerList[i] = new Player(i, numOfPlayers, lockObject);
        }

        //Set the static attribute deckList for all players.
        playerList[1].setDeckList(deckList);

        // Return the array of Player objects
        return playerList;
    }

    /**
     * Takes in an array of all created Card objects and deals them
     * in a round robin fashion to the players, and then the
     * remaining cards to the decks, again in a round robin fashion.
     * @param playerList array of Player objects
     * @param allCards array of all created Card objects from the pack file
     * @param deckList array of the decks
     */
    public static void dealCardsToPlayers(Player[] playerList, Card[] allCards, CardDeck[] deckList) {
        // All cards are dealt in a round robin fashion

        int dealCount = 0;
        // Keeps track of which card from the pack is being dealt

        //Deal cards to players.
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < playerList.length; j++) {
                playerList[j].addCardToHand(allCards[dealCount++]);
            }
        }

        //Deal remaining cards to decks.
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < deckList.length; j++) {
                deckList[j].addCard(allCards[dealCount++]);
            }
        }
    }
}
