import java.util.List;

public class OrderService {

    /**
     * Calculates the final price after applying a discount percentage.
     * @param total Total amount before discount (USD)
     * @param discountPercent Discount percentage (0–100)
     * @return Final amount after applying the discount
     * @throws IllegalArgumentException if total or discount is invalid
     */
    public double applyDiscount(double total, double discountPercent) {
        if (total < 0 || discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Invalid input values.");
        }
        return total - (total * discountPercent / 100);
    }

    /**
     * Validates if an email is in correct format.
     * @param email Email to validate
     * @return true if email is valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    /**
     * Calculates the average order value from a list of orders.
     * @param orders List of order values
     * @return Average order amount
     * @throws IllegalArgumentException if list is null or empty
     */
    public double calculateAverageOrder(List<Double> orders) {
        if (orders == null || orders.isEmpty()) {
            throw new IllegalArgumentException("Order list must not be empty.");
        }
        return orders.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Validates that all orders are non-negative.
     * @param orders List of order values
     * @return true if all values are >= 0, false if any are invalid
     */
    public boolean validateOrderAmounts(List<Double> orders) {
        if (orders == null) return false;
        return orders.stream().allMatch(o -> o != null && o >= 0);
    }

    /**
     * Converts amount from USD to VND.
     * @param usd Amount in USD
     * @return Amount in VND (rounded)
     * @throws IllegalArgumentException if usd is negative
     */
    public long convertUsdToVnd(double usd) {
        if (usd < 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        return (long)(usd * 24000);
    }
}