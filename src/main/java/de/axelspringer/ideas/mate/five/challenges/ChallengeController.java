package de.axelspringer.ideas.mate.five.challenges;

import de.axelspringer.ideas.mate.five.util.Views;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/five")
public class ChallengeController {

    @RequestMapping(path = {"", "/"})
    public String zero() {
        return Views.fromClasspath("five/zero.html");
    }

    @RequestMapping(
            path = {"", "/"},
            method = {RequestMethod.PUT, RequestMethod.POST},
            consumes = {MediaType.ALL_VALUE}
    )
    public String oneAndTwoAndThree(HttpServletRequest httpServletRequest,
                                    @RequestBody(required = false) String body) {

        if (StringUtils.isNotBlank(body)) {
            log.info("Found Body: " + body);
            Map<String, String> parameters = bodyToParameters(body);

            if (hasNoContentLength(httpServletRequest)) {
                return Views.fromClasspath("five/two_error.html");
            }
            String name = parameters.get("name");
            if (name != null) {
                String email = parameters.get("email");
                if (email != null) {

                    if (sha1(email).startsWith("a51dea5")) {
                        Views.fromClasspath("five/three_with_email_ok.html",
                                new Views.ViewParameter("name", name),
                                new Views.ViewParameter("email", email),
                                new Views.ViewParameter("digest", sha1(email))
                        );
                    } else {
                        Views.fromClasspath("five/three_with_email.html",
                                new Views.ViewParameter("name", name),
                                new Views.ViewParameter("email", email),
                                new Views.ViewParameter("digest", sha1(email))
                        );
                    }

                } else {
                    Views.fromClasspath("five/three.html", new Views.ViewParameter("name", name));
                }
            }
            return Views.fromClasspath("five/two.html");
        }

        return Views.fromClasspath("five/one.html");
    }

    private Map<String, String> bodyToParameters(String body) {
        Map<String, String> result = new HashMap<>();
        String[] allParameters = body.split(";");
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

    @SneakyThrows
    private String sha1(String input) {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
