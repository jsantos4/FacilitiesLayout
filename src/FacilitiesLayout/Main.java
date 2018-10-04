package FacilitiesLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static int threads = Runtime.getRuntime().availableProcessors();
    private static Floor floor = new Floor(10, 10);
    private static Generation generation = new Generation(floor);
    private static ExecutorService executor = Executors.newFixedThreadPool(threads);

    public static void main(String args[]) {
        for (int i = 0; i < threads; i++) {
            executor.execute(generation);
        }

        executor.shutdown();
    }
}
