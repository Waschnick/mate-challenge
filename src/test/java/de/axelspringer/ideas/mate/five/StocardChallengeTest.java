package de.axelspringer.ideas.mate.five;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.axelspringer.ideas.mate.six.Caeser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

// https://stocodechallenge.de/index/challenge/2?

public class StocardChallengeTest {

    private static final String sessionId = "connect.sid=" + "s%3AcBzL2puxmB7dy5j_dKgdtTDqoSkby0Ro.1iwzN0V6dGht%2FQCDVHxTF5fIPId8IKvO4mkz0unoQOA";

    private CloseableHttpClient httpclient = HttpClients.createDefault();

    private static class Solution {
        public List<Integer> solution = new ArrayList<>();

        public Solution add(Integer code) {
            this.solution.add(code);
            return this;
        }
    }

    @Test
    // 15 March 44 BC = The Ides of March
    public void challenge_4() throws Exception {

        {
            Caeser caeser = new Caeser();
            caeser.offset = 23;
// FIXME Remove System.out
            System.out.println(caeser.encrypt("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG"));
            System.out.println(caeser.decrypt("QEB NRFZH YOLTK CLU GRJMP LSBO QEB IXWV ALD"));

        }

        // I have no memory of this place
        String value = "QEFP FP X HXKPXP ZFQV PERCCIB";
        String image = "zexoifb zexmifk lkzb ilpq x zexoifb zexmifk illhxifhb zlkqbpq";

        for (int i = 0; i < 26; i++) {
            Caeser caeser = new Caeser();
            caeser.offset = i;

            System.out.println(caeser.decrypt(value));
            System.out.println(caeser.decrypt(image) +  "\n\n");
        }

//        for (int i = 0; i < 50; i++) {
//        NeverPay neverPay = new NeverPay();
//            neverPay.offset = i;
//
//            // FIXME Remove System.out
//            System.out.println(neverPay.decode(value));
//            System.out.println(neverPay.decode(image) + "\n\n");
//        }

//        for (int i = 0; i < 26; i++) {
//            System.out.println(rot(value, i));
//            System.out.println(rot(image, i) + "\n\n");
//        }
    }

    private String rot(String s, int offset) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'm') {
                c += offset;
            } else if (c >= 'A' && c <= 'M') {
                c += offset;
            } else if (c >= 'n' && c <= 'z') {
                c -= offset;
            } else if (c >= 'N' && c <= 'Z') {
                c -= offset;
            }
            result += c;
        }
        return result;
    }

    @Test
    public void challenge_4_B() throws Exception {
        // FIXME Remove System.out
        System.out.println(new String(Base64.getDecoder().decode(("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"))));
    }

    @Test
    public void challenge_2() throws Exception {
        String url = "https://stocodechallenge.de/index/challenge/2";

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("Cookie", sessionId);

        Solution solution = new Solution();
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            String resultBody = EntityUtils.toString(entity);
            if (response.getStatusLine().getStatusCode() > 200) {
                throw new RuntimeException("Could not read answer, result was: " + resultBody);
            }

            JsonParser jsonParser = new JsonParser();
            JsonElement codes = jsonParser.parse(resultBody)
                    .getAsJsonObject().get("codes");


            for (JsonElement jsonElement : codes.getAsJsonArray()) {
                String code = jsonElement.getAsString();
                solution.add(EanHasher.checksum(code));
            }
            // FIXME Remove System.out
            System.out.println(resultBody);
        }

        // FIXME Remove System.out
        System.out.println("\n\n### PUT ###\n\n");

        HttpPut httpPut = new HttpPut(url + "/solution");
        httpPut.addHeader("Accept", "application/json");
        httpPut.addHeader("Content-Type", "application/json");
        httpPut.addHeader("Cookie", sessionId);
        String resultString = new Gson().toJson(solution);
        // FIXME Remove System.out
        System.out.println(resultString);

        httpPut.setEntity(new StringEntity(resultString));

        try (CloseableHttpResponse response = httpclient.execute(httpPut)) {
            HttpEntity entity = response.getEntity();
            String resultBody = EntityUtils.toString(entity);
            System.out.println(resultBody);
        }

    }


//    <!--### Request to get list of mean codes
//
//    GET: /index/challenge/2
//
//    Expected header:
//    {
//        "Accept" : "application/json",
//            "Cookie" : "<Your current session cookie ( e.g. connect.sid=<value> )>"
//    }
//
//    Returns:
//    {
//        "message": "",
//            "codes":[] //  mEAN codes without checkdigit
//    }
//-->
//
//
//<!--### Request to upload solution
//
//    PUT: /index/challenge/2/solution
//
//    Expected header:
//    {
//        "Accept": "application/json",
//            "Content-Type": "application/json",
//            "Cookie" : "<Your current session cookie value>"
//    }
//
//    Expected data:
//    {
//        solution: [checkdigit1, checkdigit2, .. ,checkdigitN]
//    }
//
//    Returns:
//    {
//        "message": "",
//    }
//-->


}
