import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCard {
    private Card card1;

    /**
     * Create a new card with the value 5
     */
    @Before
    public void setUp() {
        card1 = new Card(5);
    }

    /** Testing: getCardNumber
     * Test that the correct value of the card is returned
     */
    @Test
    public void testGetCardNumber() {
        // Call the getCardNumber on the card1 object to get the cards number
        int cardNumberAttribute = card1.getCardNumber();

        assertEquals("Incorrect card number", cardNumberAttribute, 5);
    }

    /**
     * Clean up test to ensure test independence
     */
    @After
    public void tearDown() {

    }
}