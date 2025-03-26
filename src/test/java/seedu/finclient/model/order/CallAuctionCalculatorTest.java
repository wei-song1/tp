package seedu.finclient.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.finclient.model.order.Order.OrderType;

public class CallAuctionCalculatorTest {

    @Test
    public void calculateClearingPrice_noOrders_returnsEmpty() {
        // empty list
        List<Order> orders = new ArrayList<>();
        Optional<Double> result = CallAuctionCalculator.calculateClearingPrice(orders);
        assertFalse(result.isPresent());
    }

    @Test
    public void calculateClearingPrice_onlyHiddenAndNoneOrders_returnsEmpty() {
        // even though we have orders, they are all HIDDEN/NONE => no valid matching
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(OrderType.HIDDEN, "10.00", 100));
        orders.add(new Order(OrderType.NONE, "5.00", 50));
        orders.add(new Order("NONE"));
        orders.add(new Order("HIDDEN"));

        Optional<Double> result = CallAuctionCalculator.calculateClearingPrice(orders);
        assertFalse(result.isPresent());
    }

    @Test
    public void calculateClearingPrice_onlyBuys_returnsEmpty() {
        // no sells => can't match
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(OrderType.BUY, "10.00", 100));
        orders.add(new Order(OrderType.BUY, "15.00", 50));

        Optional<Double> result = CallAuctionCalculator.calculateClearingPrice(orders);
        assertFalse(result.isPresent());
    }

    @Test
    public void calculateClearingPrice_onlySells_returnsEmpty() {
        // no buys => can't match
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(OrderType.SELL, "5.00", 200));
        orders.add(new Order(OrderType.SELL, "4.00", 100));

        Optional<Double> result = CallAuctionCalculator.calculateClearingPrice(orders);
        assertFalse(result.isPresent());
    }

    @Test
    public void calculateClearingPrice_singleBuySingleSell_matchAtSellPrice() {
        // One BUY at 10.00, quantity=100
        // One SELL at 9.50, quantity=50
        // Demand(p=9.50) = 100 (buy limit >= 9.50)
        // Supply(p=9.50) = 50  (sell limit <= 9.50)
        // MatchedVolume = min(100, 50) = 50
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(OrderType.BUY, "10.00", 100));
        orders.add(new Order(OrderType.SELL, "9.50", 50));

        Optional<Double> result = CallAuctionCalculator.calculateClearingPrice(orders);
        assertTrue(result.isPresent());
        // We expect the clearing price to be 9.50 (the only candidate prices are 9.50 and 10.00,
        // but 9.50 will have matched volume=50, 10.00 => Demand=100, Supply=50 => still matched=50,
        // tie on volume => leftover at 9.50 is |100 - 50|=50, leftover at 10.00 is |100 - 50|=50 => same leftover
        // then pick the lower price => 9.50
        assertEquals(9.50, result.get(), 1e-9);
    }

    @Test
    public void calculateClearingPrice_multipleBuysSells_noTie() {
        // Buys:
        //   B1: 10.00 x 100
        //   B2:  9.50 x  50
        // Sells:
        //   S1:  9.00 x  70
        //   S2: 10.00 x  40
        //
        // Distinct prices = 9.0, 9.5, 10.0
        // Demand(9.0) = sum(buys >=9.0) => all are >=9.0 => 100 + 50 = 150
        // Supply(9.0) = sum(sells <=9.0) => only S1 => 70
        // MatchedVolume(9.0) = min(150, 70) = 70
        //
        // Demand(9.5) = buys with limit >=9.5 => B1(10.0), B2(9.5) => 100 + 50 = 150
        // Supply(9.5) = sells with limit <=9.5 => S1(9.0) => 70
        // MatchedVolume(9.5) = min(150, 70) = 70
        //
        // Demand(10.0) = B1(10.0), B2(9.5) => B2 not >=10.0? Actually 9.5 < 10.0, so B2 doesn't count
        //                => only B1 => 100
        // Supply(10.0) = S1(9.0), S2(10.0) => both <=10.0 => 70 + 40 = 110
        // MatchedVolume(10.0) = min(100, 110) = 100  <-- this is the highest matched volume
        //
        // So final answer => 10.0
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(OrderType.BUY, "10.00", 100));
        orders.add(new Order(OrderType.BUY, "9.50", 50));
        orders.add(new Order(OrderType.SELL, "9.00", 70));
        orders.add(new Order(OrderType.SELL, "10.00", 40));

        Optional<Double> result = CallAuctionCalculator.calculateClearingPrice(orders);
        assertTrue(result.isPresent());
        assertEquals(10.0, result.get(), 1e-9);
    }

    @Test
    public void calculateClearingPrice_tieOnVolume_chooseSmallestLeftover() {
        // Buys:
        //   B1: 10.00 x 100
        // Sells:
        //   S1: 8.00 x  50
        //   S2: 10.00 x 50
        //
        // Distinct prices => 8.0, 10.0
        // At p=8.0 => Demand = buys >=8.0 => 100
        //           Supply = sells <=8.0 => S1=50
        //           MatchedVolume=50, leftover=|100-50|=50
        // At p=10.0 => Demand=buys >=10.0 => 100
        //             Supply=sells <=10.0 => S1(8.0) + S2(10.0) => 50+50=100
        //             MatchedVolume=100, leftover=|100-100|=0
        //
        // Actually, p=10.0 yields the largest volume => no tie. Let's adjust to create a tie:
        // Let's add more sells at 8.0 to match 100 => say S1: 8.00 x 100
        // Then:
        //   p=8.0 => Demand=100, Supply=100 => matched=100 leftover=0
        //   p=10.0 => Demand=100, Supply=S1+S2=100+50=150 => matched=100 leftover=|100-150|=50
        //
        // Now both prices have matchedVolume=100 => tie
        // leftover(8.0)=0 leftover(10.0)=50 => pick the one with leftover=0 => 8.0
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(OrderType.BUY, "10.00", 100));
        orders.add(new Order(OrderType.SELL, "8.00", 100));
        orders.add(new Order(OrderType.SELL, "10.00", 50));

        Optional<Double> result = CallAuctionCalculator.calculateClearingPrice(orders);
        assertTrue(result.isPresent());
        assertEquals(8.0, result.get(), 1e-9);
    }

    @Test
    public void calculateClearingPrice_tieOnVolumeLeftover_pickLowestPrice() {
        // We want to contrive a scenario where two prices yield same matchedVolume
        // and same leftover => then pick the lower price.
        //
        // Buys:
        //   B1: 10.00 x 100
        // Sells:
        //   S1: 9.00 x 50
        //   S2: 10.00 x 50
        // Distinct prices => 9.0, 10.0
        // p=9.0 => Demand=all buys >=9.0 => 100
        //         Supply=all sells <=9.0 => S1=50
        //         matched=50 leftover=|100-50|=50
        // p=10.0 => Demand=all buys >=10.0 => 100
        //          Supply=all sells <=10.0 => 50+50=100
        //          matched=100 leftover=|100-100|=0  => not a tie scenario
        //
        // Let's change S2 so that at p=10.0 supply is also 50 total => leftover=50 => exactly same as p=9.0
        // That means S2 limit price > 10 => e.g. 11.00 => so it won't supply at 10
        // Then:
        //   p=9.0 => matched=50 leftover=50
        //   p=10.0 => Demand=100, Supply=sells <=10 => only S1(9.0)=50 => matched=50 leftover=50
        // Now there's a tie in matchedVolume=50, leftover=50 => pick the lower price => 9.0
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(OrderType.BUY, "10.00", 100));
        orders.add(new Order(OrderType.SELL, "9.00", 50));
        orders.add(new Order(OrderType.SELL, "11.00", 50)); // won't match at 10 => leftover same as 9.0 scenario

        Optional<Double> result = CallAuctionCalculator.calculateClearingPrice(orders);
        assertTrue(result.isPresent());
        // Both p=9.0 and p=10.0 => matched=50, leftover=50 => pick the lower price => 9.0
        assertEquals(9.0, result.get(), 1e-9);
    }
}
