package de.axelspringer.ideas.mate.common;

import de.axelspringer.ideas.mate.MateChallengeProperties;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MailSender {

    private final static Logger log = LoggerFactory.getLogger(MailSender.class);


    public void send(String receiverEmail, String subject, String htmlBody) {
        try {
            log.info("Sending mail with ElasticMail to: " + receiverEmail);

            String from = "hello@asideas.de";
            String fromName = "Mate Challenge";

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://api.elasticemail.com/v2/email/send");

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("apikey", MateChallengeProperties.INSTANCE.elasticMailApiKey));
            nvps.add(new BasicNameValuePair("from", from));
            nvps.add(new BasicNameValuePair("fromName", fromName));
            nvps.add(new BasicNameValuePair("subject", subject));
            nvps.add(new BasicNameValuePair("bodyHtml", htmlBody));
            nvps.add(new BasicNameValuePair("to", receiverEmail));
            nvps.add(new BasicNameValuePair("isTransactional", "true"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                System.out.println(EntityUtils.toString(entity, "UTF-8"));
            }
            log.info("Done.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}