import java.util.Random;
public class Agent implements Runnable {
    private CoffeeShopMonitor monitor;
    /**
     * Defualt constructer with the specified monitor.
     *
     * @param monitor the monitor used to coordinate the placement of ingredients
     */
    public Agent(CoffeeShopMonitor monitor) {
        this.monitor = monitor;
    }

    /**
     * Continuously selects two random ingredients to place on the counter until the target number of coffees
     * has been reached.
     */
    @Override
    public void run() {
        Random random = new Random();
        while (!monitor.isTargetReached()) {
            boolean[] ingredients = new boolean[3]; // Represents water, beans, sugar
            int count = 0;

            // Randomly select exactly two ingredients
            while (count < 2) {
                int index = random.nextInt(3);
                if (!ingredients[index]) {
                    ingredients[index] = true;
                    count++;
                }
            }

            // Place the ingredients
            monitor.placeIngredients(ingredients[0], ingredients[1], ingredients[2]);

            try {
                Thread.sleep(1000); // Waiting time for placing ingredients
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
