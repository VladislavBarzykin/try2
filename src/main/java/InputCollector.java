import java.util.Scanner;
import java.util.concurrent.*;

public class InputCollector {
    private final Scanner scanner;

    public InputCollector(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getInputWithTimeout(long timeout, TimeUnit timeUnit) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Future<String> inputFuture = executor.submit(() -> scanner.nextLine());

        try {
            return inputFuture.get(timeout, timeUnit);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return null;
        } finally {
            executor.shutdownNow();
        }
    }
}