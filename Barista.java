public class Barista implements Runnable {
    /**
     * The specific ingredient this barista is responsible for using.
     */
    private String ingredient;

    /**
     * The monitor object for coordinating the pickup of ingredients.
     */
    private CoffeeShopMonitor monitor;

    /**
     * Defualt constructer with the specified ingredient and monitor.
     *
     * @param ingredient the ingredient this barista will use
     * @param monitor the monitor used to coordinate the pickup of ingredients
     */
    public Barista(String ingredient, CoffeeShopMonitor monitor) {
        this.ingredient = ingredient;
        this.monitor = monitor;
    }

    /**
     * Continuously attempts to pick up the assigned ingredient and make coffee until the target number of coffees has
     * been reached.
     */
    @Override
    public void run() {
        try {
            while (!monitor.isTargetReached()) {
                monitor.pickupIngredients(ingredient);
                try {
                    Thread.sleep(1000); // 1 second pause
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
