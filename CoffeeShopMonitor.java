import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The CoffeeShopMonitor class manages the synchronization of baristas and an agent.
 */
public class CoffeeShopMonitor {
    private boolean hasWater = false;
    private boolean hasBeans = false;
    private boolean hasSugar = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int totalCoffeesMade = 0;

    /**
     * Places the selected ingredients on the counter and notifies the baristas.
     *
     * @param water Indicates if water is placed.
     * @param beans Indicates if beans are placed.
     * @param sugar Indicates if sugar is placed.
     */
    public void placeIngredients(boolean water, boolean beans, boolean sugar) {
        lock.lock();
        try {
            hasWater = water;
            hasBeans = beans;
            hasSugar = sugar;
            System.out.println("Ingredients on the counter: " +
                    (water ? "Water " : "") +
                    (beans ? "Beans " : "") +
                    (sugar ? "Sugar" : ""));
            condition.signalAll(); // Notify baristas
        } catch (Exception e) {
            lock.unlock(); // Release lock on exception
            throw e;
        }
        lock.unlock(); // Release lock for normal execution
    }

    /**
     * Allows a barista to pick up the required ingredients and make coffee.
     */
    public void pickupIngredients(String ingredient) throws InterruptedException {
        lock.lock();
        try {
            while (!((hasWater && !ingredient.equals("water")) ||
                    (hasBeans && !ingredient.equals("beans")) ||
                    (hasSugar && !ingredient.equals("sugar")))) {
                condition.await(); // Wait for ingredients
            }
            makeCoffee(ingredient);
        } catch (InterruptedException e) {
            throw e; // Rethrow InterruptedException
        } catch (Exception e) {
            throw e; // Handle any unexpected exception
        } finally {
            notifyAgent();
            lock.unlock(); // Ensure lock is released
        }
    }

    /**
     * Helper method to make coffee and increment the total count of coffees made.
     *
     * @param ingredient The ingredient used by the barista to make coffee.
     */
    private void makeCoffee(String ingredient) {
        totalCoffeesMade++;
        System.out.println("Barista with " + ingredient + " made coffee. Total coffees made: " + totalCoffeesMade);
    }

    /**
     * Notifies the agent that coffee has been made and resets the ingredient availability.
     */
    private void notifyAgent() {
        hasWater = false;
        hasBeans = false;
        hasSugar = false;
        condition.signal();
    }

    /**
     * Checks if the target number of coffees has been reached.
     */
    public boolean isTargetReached() {
        return totalCoffeesMade >= 20;
    }
}
