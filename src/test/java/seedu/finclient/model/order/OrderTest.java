package seedu.finclient.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.finclient.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.finclient.model.order.Order.OrderType;

public class OrderTest {

    // =======================
    // Tests for first constructor: Order(OrderType orderType, String price, int quantity)
    // =======================

    @Test
    public void constructor_nullOrderType_throwsNullPointerException() {
        // orderType is null
        assertThrows(NullPointerException.class, () -> new Order(null, "10.00", 10));
    }

    @Test
    public void constructor_nullPrice_throwsNullPointerException() {
        // price is null
        // Even though quantity is a primitive int, we can still verify the error for price.
        assertThrows(NullPointerException.class, () -> new Order(OrderType.BUY, null, 10));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        // price is invalid (not matching the positive number with up to 2 decimals rule)
        String invalidPrice = "-5.50";
        assertThrows(IllegalArgumentException.class, () -> new Order(OrderType.BUY, invalidPrice, 10));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        // quantity is invalid (<= 0)
        assertThrows(IllegalArgumentException.class, () -> new Order(OrderType.BUY, "10.00", 0));
        assertThrows(IllegalArgumentException.class, () -> new Order(OrderType.BUY, "10.00", -5));
    }

    // =======================
    // Tests for second constructor: Order(String orderDescription)
    // =======================

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        // orderDescription is null
        assertThrows(NullPointerException.class, () -> new Order((String) null));
    }

    @Test
    public void constructor_noneString_noException() {
        // "NONE" should create a valid NONE order
        Order noneOrder = new Order("NONE");
        assertTrue(noneOrder.getOrderType() == OrderType.NONE);
        assertTrue(noneOrder.getPrice() == 1.0);
        assertTrue(noneOrder.getQuantity() == 1);
    }

    @Test
    public void constructor_hiddenString_noException() {
        // "HIDDEN" should create a valid HIDDEN order
        Order hiddenOrder = new Order("HIDDEN");
        assertTrue(hiddenOrder.getOrderType() == OrderType.HIDDEN);
        assertTrue(hiddenOrder.getPrice() == 1.0);
        assertTrue(hiddenOrder.getQuantity() == 1);
    }

    @Test
    public void constructor_validDescription_noException() {
        // Example: "BUY 10 @ $5.50"
        Order buyOrder = new Order("BUY 10 @ $5.50");
        assertTrue(buyOrder.getOrderType() == OrderType.BUY);
        assertTrue(buyOrder.getPrice() == 5.50);
        assertTrue(buyOrder.getQuantity() == 10);
    }

    @Test
    public void constructor_invalidFormat_throwsIllegalArgumentException() {
        // Not enough tokens => invalid
        assertThrows(IllegalArgumentException.class, () -> new Order("BUY 10 $5.50"));

        // Extra tokens => invalid
        assertThrows(IllegalArgumentException.class, () -> new Order("BUY 10 something @ $5.50"));

        // Quantity not an integer => invalid
        assertThrows(IllegalArgumentException.class, () -> new Order("BUY abc @ $5.50"));

        // negative Price
        assertThrows(IllegalArgumentException.class, () -> new Order("SELL 10 @ $-5.50")); // negative price
    }

    // =======================
    // Tests for validation methods: isValidPrice, isValidQuantity
    // =======================

    @Test
    public void isValidPrice_null_throwsNullPointerException() {
        // Should throw if we call isValidPrice with null
        assertThrows(NullPointerException.class, () -> Order.isValidPrice(null));
    }

    @Test
    public void isValidPrice() {
        // invalid prices
        assertFalse(Order.isValidPrice(""));
        assertFalse(Order.isValidPrice(" "));
        assertFalse(Order.isValidPrice("-10.00"));
        assertFalse(Order.isValidPrice("0"));
        assertFalse(Order.isValidPrice("5.999"));
        assertFalse(Order.isValidPrice("price"));

        // valid prices
        assertTrue(Order.isValidPrice("5"));
        assertTrue(Order.isValidPrice("5.0"));
        assertTrue(Order.isValidPrice("5.00"));
        assertTrue(Order.isValidPrice("999999.99"));
    }

    @Test
    public void isValidQuantity() {
        // invalid quantities
        assertFalse(Order.isValidQuantity(0));
        assertFalse(Order.isValidQuantity(-1));

        // valid quantities
        assertTrue(Order.isValidQuantity(1));
        assertTrue(Order.isValidQuantity(9999));
    }

    // =======================
    // Tests for equals
    // =======================

    @Test
    public void equals() {
        Order order = new Order(OrderType.BUY, "10.00", 10);

        // same values -> returns true
        Order orderCopy = new Order(OrderType.BUY, "10.00", 10);
        assertTrue(order.equals(orderCopy));

        // same object -> returns true
        assertTrue(order.equals(order));

        // null -> returns false
        assertFalse(order.equals(null));

        // different types -> returns false
        assertFalse(order.equals("some string"));

        // different order type -> returns false
        Order differentType = new Order(OrderType.SELL, "10.00", 10);
        assertFalse(order.equals(differentType));

        // different price -> returns false
        Order differentPrice = new Order(OrderType.BUY, "9.99", 10);
        assertFalse(order.equals(differentPrice));

        // different quantity -> returns false
        Order differentQty = new Order(OrderType.BUY, "10.00", 5);
        assertFalse(order.equals(differentQty));
    }

    @Test
    public void compareTo_validOrder_success() {
        Order order = new Order(OrderType.BUY, "10.00", 10);
        Order orderCopy = new Order(OrderType.BUY, "10.00", 10);
        Order orderHigherPrice = new Order(OrderType.BUY, "11.00", 10);
        Order orderHigherAmount = new Order(OrderType.BUY, "10.00", 11);
        assertEquals(0, order.compareTo(orderCopy, "price"));
        assertEquals(0, order.compareTo(orderCopy, "amount"));
        assertEquals(-1, order.compareTo(orderHigherPrice, "price"));
        assertEquals(-1, order.compareTo(orderHigherAmount, "amount"));
        assertEquals(1, orderHigherPrice.compareTo(order, "price"));
        assertEquals(1, orderHigherAmount.compareTo(order, "amount"));
    }

    @Test
    public void compareTo_invalidOrder_exceptionThrown() {
        Order order = new Order(OrderType.BUY, "10.00", 10);
        Order orderCopy = new Order(OrderType.BUY, "10.00", 10);
        assertThrows(NullPointerException.class, () -> order.compareTo(null, "price"));
        assertThrows(NullPointerException.class, () -> order.compareTo(orderCopy, null));
    }

    @Test
    public void compareTo_invalidCriteria_returnZero() {
        Order order = new Order(OrderType.BUY, "10.00", 10);
        Order orderCopy = new Order(OrderType.BUY, "10.00", 10);
        assertEquals(0, order.compareTo(orderCopy, "invalid"));
    }

    @Test
    public void compareTo_differentOrderType_success() {
        Order buyOrder = new Order(OrderType.BUY, "10.00", 10);
        Order sellOrder = new Order(OrderType.SELL, "10.00", 10);
        assertEquals(-1, buyOrder.compareTo(sellOrder, "amount"));
        assertEquals(-1, buyOrder.compareTo(sellOrder, "price"));
    }

    @Test
    public void constructor_validLargeQuantity_noException() {
        // Larger than 2,147,483,647 (int max)
        long largeQuantity = 2_147_483_648L;
        Order largeOrder = new Order(OrderType.BUY, "10.00", largeQuantity);

        // Verify the quantity is stored accurately as a long
        assertTrue(largeOrder.getQuantity() == largeQuantity);
    }

    // Tests for Constructor with long quantity
    @Test
    public void constructor_maxLongQuantity_noException() {
        // Test an extreme boundary: Long.MAX_VALUE
        long maxQuantity = Long.MAX_VALUE;
        Order largeOrder = new Order(OrderType.BUY, "99.99", maxQuantity);

        // Ensure we can handle extremely large values
        assertTrue(largeOrder.getQuantity() == maxQuantity);
    }

    @Test
    public void constructor_parseLargeQuantity_noException() {
        // "BUY 2147483648 @ $5.50" -> quantity is 2,147,483,648 (exceeds int range)
        long largeQuantity = 2_147_483_648L;
        Order largeOrder = new Order("BUY 2147483648 @ $5.50");

        assertTrue(largeOrder.getOrderType() == OrderType.BUY);
        assertTrue(largeOrder.getPrice() == 5.50);
        assertTrue(largeOrder.getQuantity() == largeQuantity);
    }

    // Tests for String Parsing of Large Quantities
    @Test
    public void constructor_parseExceedLongMax_throwsIllegalArgumentException() {
        // Something beyond Long.MAX_VALUE
        String invalidOrderString = "BUY 99999999999999999999999999 @ $5.50";
        assertThrows(IllegalArgumentException.class, () -> new Order(invalidOrderString));
    }
}
