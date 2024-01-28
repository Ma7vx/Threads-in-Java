public class Main {
    public static void main(String[] args) {
        // Initialize a shared monitor for coordinating between the agent and baristas.
        CoffeeShopMonitor monitor = new CoffeeShopMonitor();

        // Create and start the agent thread responsible for providing ingredients.
        Thread agentThread = new Thread(new Agent(monitor));
        agentThread.start();

        // Create and start barista threads, each responsible for using a specific ingredient.
        Thread barista1 = new Thread(new Barista("water", monitor));
        Thread barista2 = new Thread(new Barista("beans", monitor));
        Thread barista3 = new Thread(new Barista("sugar", monitor));
        barista1.start();
        barista2.start();
        barista3.start();

        // Wait for all threads to complete their execution.
        try {
            agentThread.join();
            barista1.join();
            barista2.join();
            barista3.join();
        } catch (InterruptedException e) {
            // Handle the case where the current thread is interrupted while waiting.
            Thread.currentThread().interrupt();
        }
    }
}

