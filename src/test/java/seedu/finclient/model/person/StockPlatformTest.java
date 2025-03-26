package seedu.finclient.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StockPlatformTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StockPlatform(null));
    }

    @Test
    public void constructor_invalidStockPlatform_throwsIllegalArgumentException() {
        String invalidStockPlatform = "";
        assertThrows(IllegalArgumentException.class, () -> new StockPlatform(invalidStockPlatform));
    }

    @Test
    public void isValidStockPlatform() {
        // null stock platform
        assertThrows(NullPointerException.class, () -> StockPlatform.isValidStockPlatform(null));

        // blank stock platform
        assertFalse(StockPlatform.isValidStockPlatform("")); // empty string
        assertFalse(StockPlatform.isValidStockPlatform(" ")); // spaces only

        // valid stock platforms
        assertTrue(StockPlatform.isValidStockPlatform("eToro"));
        assertTrue(StockPlatform.isValidStockPlatform("Robinhood"));
        assertTrue(StockPlatform.isValidStockPlatform("Fidelity Investments"));
        assertTrue(StockPlatform.isValidStockPlatform("Charles Schwab"));
        assertTrue(StockPlatform.isValidStockPlatform("Interactive Brokers"));
        assertTrue(StockPlatform.isValidStockPlatform("Coinbase"));
        assertTrue(StockPlatform.isValidStockPlatform("Binance"));
        assertTrue(StockPlatform.isValidStockPlatform("TD Ameritrade"));
        assertTrue(StockPlatform.isValidStockPlatform("Webull"));
        assertTrue(StockPlatform.isValidStockPlatform("Vanguard"));
        assertTrue(StockPlatform.isValidStockPlatform("MetaTrader 4"));
        assertTrue(StockPlatform.isValidStockPlatform("MetaTrader 5"));
        assertTrue(StockPlatform.isValidStockPlatform("Trading212"));
        assertTrue(StockPlatform.isValidStockPlatform("Plus500"));
        assertTrue(StockPlatform.isValidStockPlatform("Acorns"));
        assertTrue(StockPlatform.isValidStockPlatform("Wealthfront"));
        assertTrue(StockPlatform.isValidStockPlatform("Betterment"));
        assertTrue(StockPlatform.isValidStockPlatform("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        StockPlatform stockPlatform = new StockPlatform("valid@platform");

        // same values -> returns true
        assertTrue(stockPlatform.equals(new StockPlatform("valid@platform")));

        // same object -> returns true
        assertTrue(stockPlatform.equals(stockPlatform));

        // null -> returns false
        assertFalse(stockPlatform.equals(null));

        // different types -> returns false
        assertFalse(stockPlatform.equals(5.0f));

        // different values -> returns false
        assertFalse(stockPlatform.equals(new StockPlatform("other.valid@platform")));
    }
}
