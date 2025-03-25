package seedu.finclient.model.order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;

/**
 * Utility class that calculates a single clearing price for a group of buy/sell orders
 * using a call auction mechanism:
 *
 * 1) Collect all unique prices from the order book.
 * 2) For each price p:
 *    - Demand(p) = total quantity of buy orders with limit >= p
 *    - Supply(p) = total quantity of sell orders with limit <= p
 *    - MatchedVolume(p) = min(Demand(p), Supply(p))
 * 3) Choose the price that yields the highest matched volume.
 *    If there's a tie, pick the price with the smallest absolute leftover (|Demand - Supply|).
 *    If still tied, pick the lowest price.
 *
 * Ignores HIDDEN and NONE orders.
 */
public class CallAuctionCalculator {

    /**
     * A small container to track the results at each candidate price.
     */
    private static class CandidateResult {
        double price;
        int matchedVolume;
        int demand;
        int supply;

        CandidateResult(double price, int matchedVolume, int demand, int supply) {
            this.price = price;
            this.matchedVolume = matchedVolume;
            this.demand = demand;
            this.supply = supply;
        }
    }

    /**
     * Computes the single clearing price for the provided orders (if any).
     * Returns an Optional containing the clearing price, or empty if
     * no valid match is possible (e.g., no buys or no sells).
     *
     * This method:
     *  - Ignores orders marked HIDDEN or NONE
     *  - Tries every distinct limit price in the book
     *  - Follows the standard "maximize matched volume; minimize leftover; pick lowest price" tie-break
     */
    public static Optional<Double> calculateClearingPrice(List<Order> allOrders) {
        // Separate into buy vs sell (ignore HIDDEN, NONE)
        List<Order> buyOrders = new ArrayList<>();
        List<Order> sellOrders = new ArrayList<>();

        for (Order o : allOrders) {
            if (o.getOrderType() == Order.OrderType.BUY) {
                buyOrders.add(o);
            } else if (o.getOrderType() == Order.OrderType.SELL) {
                sellOrders.add(o);
            }
        }

        // If we have no buys or no sells, we can't match anything
        if (buyOrders.isEmpty() || sellOrders.isEmpty()) {
            return Optional.empty();
        }

        // Collect all unique prices from buy + sell
        Set<Double> candidatePrices = new HashSet<>();
        for (Order b : buyOrders) {
            candidatePrices.add(b.getPrice());
        }
        for (Order s : sellOrders) {
            candidatePrices.add(s.getPrice());
        }

        // Sort ascending
        List<Double> sortedPrices = new ArrayList<>(candidatePrices);
        sortedPrices.sort(Double::compareTo);

        // Evaluate matched volume at each candidate price
        List<CandidateResult> results = new ArrayList<>();
        for (double p : sortedPrices) {
            int demand = calculateDemand(buyOrders, p);   // total buy quantity if limit >= p
            int supply = calculateSupply(sellOrders, p); // total sell quantity if limit <= p
            int matched = Math.min(demand, supply);
            results.add(new CandidateResult(p, matched, demand, supply));
        }

        // 1) Find the maximum matchedVolume
        int maxVolume = results.stream()
                .mapToInt(r -> r.matchedVolume)
                .max()
                .orElse(0);

        // Filter only those with matchedVolume == maxVolume
        List<CandidateResult> topVolumeResults = new ArrayList<>();
        for (CandidateResult r : results) {
            if (r.matchedVolume == maxVolume) {
                topVolumeResults.add(r);
            }
        }

        // 2) Among those, pick the one with the smallest leftover = |demand - supply|
        //    leftover = demand - supply (positive means leftover demand, negative leftover supply)
        //    We only care about absolute leftover
        int minLeftover = Integer.MAX_VALUE;
        for (CandidateResult r : topVolumeResults) {
            int leftover = Math.abs(r.demand - r.supply);
            if (leftover < minLeftover) {
                minLeftover = leftover;
            }
        }
        List<CandidateResult> bestLeftoverResults = new ArrayList<>();
        for (CandidateResult r : topVolumeResults) {
            int leftover = Math.abs(r.demand - r.supply);
            if (leftover == minLeftover) {
                bestLeftoverResults.add(r);
            }
        }

        // 3) If there's still more than one, pick the lowest price
        bestLeftoverResults.sort(Comparator.comparingDouble(r -> r.price));
        double finalClearingPrice = bestLeftoverResults.get(0).price;

        return Optional.of(finalClearingPrice);
    }

    /**
     * Sum of all buy orders' quantities whose limit >= p
     */
    private static int calculateDemand(List<Order> buyOrders, double p) {
        int sum = 0;
        for (Order b : buyOrders) {
            if (b.getPrice() >= p) {
                sum += b.getQuantity();
            }
        }
        return sum;
    }

    /**
     * Sum of all sell orders' quantities whose limit <= p
     */
    private static int calculateSupply(List<Order> sellOrders, double p) {
        int sum = 0;
        for (Order s : sellOrders) {
            if (s.getPrice() <= p) {
                sum += s.getQuantity();
            }
        }
        return sum;
    }
}