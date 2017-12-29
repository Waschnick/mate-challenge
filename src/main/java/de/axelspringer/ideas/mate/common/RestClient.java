package de.axelspringer.ideas.mate.common;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RestClient {

    private static final Gson gson = new Gson();
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    public static <T> T get(String url, Class<T> responseType) {
        try {
            HttpGet httpGet = new HttpGet(url);

            return execute(responseType, httpGet);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    public static <T> T post(String url, Class<T> responseType, Object body) {
        try {
            HttpPost httpPost = new HttpPost(url);
            if (body instanceof String) {
                httpPost.setEntity(new StringEntity((String) body));
            } else {
                httpPost.setEntity(new StringEntity(gson.toJson(body)));
            }

            return execute(responseType, httpPost);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static <T> T execute(Class<T> responseType, HttpRequestBase httpPost) throws IOException {
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String resultString = EntityUtils.toString(entity);

            if (responseType == String.class) {
                return (T) resultString;
            }

            return gson.fromJson(resultString, responseType);
        }
    }


}
