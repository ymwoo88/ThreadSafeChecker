import com.example.NumberCaller;

import java.util.stream.IntStream;

public class ThreadSafeCheckerApplication {

    public static void main(String[] args) {

        IntStream.range(0, 5)
                .forEach(i -> {
                    try {
                        NumberCaller numberCaller = new NumberCaller();
                        Thread thread = new Thread(numberCaller);
                        thread.start();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }
}
