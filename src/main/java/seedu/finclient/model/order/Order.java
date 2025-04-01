package seedu.finclient.model.order;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.finclient.commons.util.AppUtil.checkArgument;
import static seedu.finclient.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Order in the address book (buy or sell at a given price).
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Order {

    public static final String MESSAGE_CONSTRAINTS_PRICE =
            "Price should be a positive number with at most two decimal places";
    public static final String VALIDATION_REGEX_PRICE = "\\d+(\\.\\d{1,2})?";

    public static final String MESSAGE_CONSTRAINTS_QUANTITY =
            "Quantity should be a positive integer";

    public static final String MESSAGE_CONSTRAINTS = "Order should be with amount and price";

    private OrderType orderType;
    private String price;
    private int quantity;

    /**
     * Constructs a {@code Order}.
     *
     * @param orderType Either BUY or SELL.
     * @param price     A valid price (e.g. "10.50").
     * @param quantity  A valid quantity (e.g. 100).
     */
    public Order(OrderType orderType, String price, int quantity) {
        requireAllNonNull(orderType, price, quantity);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS_PRICE);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS_QUANTITY);
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Constructs a {@code Order} from a string using the format ("%s %d @ $%s", orderType, quantity, price).
     * Example: "BUY 10 @ $5.50"
     *
     * If the string is exactly "NONE", creates a none order.
     * if the string is exactly "HIDDEN", creates a hidden order.
     */
    public Order(String orderDescription) {
        requireNonNull(orderDescription);

        if (orderDescription.equals("NONE")) {
            // Special none-case initialization
            this.orderType = OrderType.NONE;
            this.price = "1";
            this.quantity = 1;
        } else if (orderDescription.equals("HIDDEN")) {
            // Special hidden-case initialization
            this.orderType = OrderType.HIDDEN;
            this.price = "1";
            this.quantity = 1;
        } else {
            // Expected format: e.g. "BUY 10 @ $5.50"
            String[] orderParts = orderDescription.split(" ");

            // Quick safety check: we expect exactly 4 tokens
            // [0] = "BUY" or "SELL"
            // [1] = "10" (quantity)
            // [2] = "@"
            // [3] = "$5.50" (price with $)
            if (orderParts.length != 4) {
                throw new IllegalArgumentException(
                        "Invalid order description format! Expected something like: BUY 10 @ $5.50");
            }

            // Parse order type
            this.orderType = OrderType.valueOf(orderParts[0].toUpperCase());

            // Parse quantity
            int parsedQuantity;
            try {
                parsedQuantity = Integer.parseInt(orderParts[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantity must be an integer. Example: '10'");
            }

            // Parse price (remove the leading '$')
            String rawPrice = orderParts[3];
            if (rawPrice.startsWith("$")) {
                rawPrice = rawPrice.substring(1);
            }

            // Validate and assign
            checkArgument(isValidQuantity(parsedQuantity), MESSAGE_CONSTRAINTS_QUANTITY);
            checkArgument(isValidPrice(rawPrice), MESSAGE_CONSTRAINTS_PRICE);

            this.quantity = parsedQuantity;
            this.price = rawPrice;
        }
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX_PRICE) && Double.parseDouble(test) > 0;
    }

    public static boolean isValidQuantity(int test) {
        return test > 0;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public double getPrice() {
        return Double.parseDouble(price);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOrderType(OrderType orderType) {
        requireNonNull(orderType);
        this.orderType = orderType;
    }

    public void setPrice(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS_PRICE);
        this.price = price;
    }

    public void setQuantity(int quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS_QUANTITY);
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        if (orderType == OrderType.HIDDEN) {
            return "HIDDEN";
        }

        if (orderType == OrderType.NONE) {
            return "NONE";
        }

        return String.format("%s %d @ $%s", orderType, quantity, price);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Order)) {
            return false;
        }
        Order otherOrder = (Order) other;
        return orderType == otherOrder.orderType
                && price.equals(otherOrder.price)
                && quantity == otherOrder.quantity;
    }

    @Override
    public int hashCode() {
        return orderType.hashCode() ^ price.hashCode() ^ quantity;
    }

    /**
     * Indicates whether the order is a BUY or SELL order.
     */
    public enum OrderType {
        BUY, SELL, HIDDEN, NONE
    }

    /**
     * Compares this order with another order based on the given criteria.
     */
    public int compareTo(Order other, String criteria) {
        requireAllNonNull(other, criteria);
        if (getOrderType() != other.getOrderType()) {
            return getOrderType() == OrderType.BUY ? -1 : 1;
        }
        if (criteria.equals("price")) {
            return Double.compare(getPrice(), other.getPrice());
        }
        if (criteria.equals("amount")) {
            return Integer.compare(getQuantity(), other.getQuantity());
        }
        return 0;
    }
}
