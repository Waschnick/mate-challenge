package de.axelspringer.ideas.mate.five;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.axelspringer.ideas.mate.six.NeverPay;
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
    public void challenge_4() throws Exception {
        String value = "QEFP FP X HXKPXP ZFQV PERCCIB";

        NeverPay neverPay = new NeverPay();

        // FIXME Remove System.out
        System.out.println(neverPay.decode(value));
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
