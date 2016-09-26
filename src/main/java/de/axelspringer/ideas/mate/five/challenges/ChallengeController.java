package de.axelspringer.ideas.mate.five.challenges;

import de.axelspringer.ideas.mate.five.util.Views;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static de.axelspringer.ideas.mate.five.util.Sha1.sha1;

@Slf4j
@RestController
@RequestMapping(path = "/five")
public class ChallengeController {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private QrGenerator qrGenerator;

    @RequestMapping(path = {"", "/"})
    public String zero() {
        return Views.fromClasspath("five/zero.html");
    }

    @RequestMapping(
            path = {"", "/"},
            method = {RequestMethod.PUT, RequestMethod.POST},
            consumes = {MediaType.ALL_VALUE}
    )
    @SneakyThrows
    public String oneAndTwoAndThree(HttpServletRequest httpServletRequest,
                                    @RequestBody(required = false) String body) {

        if (StringUtils.isNotBlank(body)) {
            log.info("Found Body: " + body);
            body = URLDecoder.decode(body, "UTF-8");

            Map<String, String> parameters = bodyToParameters(body);

            if (hasNoContentLength(httpServletRequest)) {
                return Views.fromClasspath("five/two_error.html");
            }
            String name = parameters.get("name");
            if (name != null) {
                String email = parameters.get("email");
                if (email == null) {
                    log.info("Name gefunden, Challenge 2 complete! Name: " + name);
                    return Views.fromClasspath("five/three.html", new Views.ViewParameter("name", name));
                } else {
                    log.info("E-Mail gefunden: " + email);

                    if (sha1(email).startsWith("a51dea5")) {

                        mailSender.send(name, email, qrGenerator.generateQrText(email));

                        return Views.fromClasspath("five/three_with_email_ok.html",
                                new Views.ViewParameter("name", name),
                                new Views.ViewParameter("email", email),
                                new Views.ViewParameter("digest", sha1(email))
                        );
                    } else {
                        return Views.fromClasspath("five/three_with_email.html",
                                new Views.ViewParameter("name", name),
                                new Views.ViewParameter("email", email),
                                new Views.ViewParameter("digest", sha1(email))
                        );
                    }
                }
            }
            return Views.fromClasspath("five/two.html");
        }

        return Views.fromClasspath("five/two.html");
    }

    private Map<String, String> bodyToParameters(String body) {
        Map<String, String> result = new HashMap<>();
        String[] allParameters = body.split(";|&");
        for (String s : allParameters) {
            String[] split1 = s.split("=");
            if (split1.length >= 2) {
                result.put(split1[0].toLowerCase(), split1[1]);
            }
        }
        return result;
    }

    @RequestMapping(path = "/cheesecake/{key}")
    public String five(@PathVariable String key) {
        log.info("Called last challenge with key: " + key);
        return Views.fromClasspath("five/five.html", new Views.ViewParameter("key", key));
    }

    private boolean hasNoContentLength(HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        for (String headerName : Collections.list(headerNames)) {
            if ("Content-length".equalsIgnoreCase(headerName)) {
                log.info("Found content-length header.");
                return false;
            }
        }
        log.info("No content length!");
        return true;
    }


}
