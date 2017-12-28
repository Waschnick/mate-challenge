package de.axelspringer.ideas.mate.six;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ChallengeSixControllerTest {


    @Test
    public void name() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:8080/six", "123456", String.class);
        // FIXME Remove System.out
        System.out.println(stringResponseEntity.getStatusCode());
        System.out.println(stringResponseEntity.getBody());
    }


}