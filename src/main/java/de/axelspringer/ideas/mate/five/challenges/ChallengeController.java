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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static de.axelspringer.ideas.mate.five.util.Sha1.sha1;

@Slf4j
@RestController
@RequestMapping(path = "/five")
public class ChallengeController {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private QrGenerator qrGenerator;

    @Autowired
    private SymetricEncryption symetricEncryption;

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
                        log.info("Hash for E-Mail " + email + " is correct!!!");
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

    @SneakyThrows
    @RequestMapping(path = "/cheesecake/{key}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public String five(@PathVariable String key) {
        log.info("Called last challenge with key: " + key);
        if (isValidKey(key)) {
            log.info("Key is valid!");
            return Views.fromClasspath("five/five.html", new Views.ViewParameter("key", key));
        } else {
            return Views.fromClasspath("five/five_nope.html");
        }
    }

    @SneakyThrows
    @RequestMapping(path = "/cheesecake/{key}/solve", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public String fiveSolve(@PathVariable String key,
                            @RequestParam("s1") String s1,
                            @RequestParam("s2") String s2,
                            @RequestParam("s3") String s3,
                            @RequestParam("s4") String s4,
                            @RequestParam("s5") String s5,
                            @RequestParam("s6") String s6,
                            @RequestParam("s7") String s7,
                            @RequestParam("s8") String s8


    ) {
        if (!isValidKey(key)) {
            return Views.fromClasspath("five/five_nope.html");
        }

        String text = "" + s1 + ", " + s2 + ", " + s3 + ", " + s4 + ", " + s5 + ", " + s6 + ", " + s7 + ", " + s8;
        if (are8differenStrings(s1, s2, s3, s4, s5, s6, s7, s8)) {
            log.info("Called solve: " + s1 + ", " + s2 + ", " + s3 + ", " + s4);
            if ((s1.hashCode() == s2.hashCode())
                    && (s1.hashCode() == s3.hashCode())
                    && (s1.hashCode() == s4.hashCode())
                    && (s1.hashCode() == s5.hashCode())
                    && (s1.hashCode() == s6.hashCode())
                    && (s1.hashCode() == s7.hashCode())
                    && (s1.hashCode() == s8.hashCode())
                    ) {
                log.info("All hashes equal!");
                doWin(key);
                return "CHALLENGE 5: COMPLETED!!! Du bekommst jetzt eine Mail mit einem Gewinn-Token, den kannst du bei mir einlösen.";
            }
            return text + " = :(((";
        } else {
            return "You need 8 different Strings :(";
        }
    }

    private void doWin(String key) {
        String email = symetricEncryption.decrypt(key);
        String gewinnCode = symetricEncryption.encrypt("winner:" + email);
        log.info("WIN for E-Mail!!!");
        mailSender.send(email, email, "Hey! Hier ist dein Gewinn-Code: " + gewinnCode);
        mailSender.send(email, "barriwaschi@gmail.com", "Gewinner: " + email + " mit Code: " + gewinnCode);
    }

    private boolean are8differenStrings(String... strings) {
        Set<String> mySet = new HashSet<>();
        for (String string : strings) {
            mySet.add(string);
        }
        return mySet.size() == 8;
    }

    private boolean isValidKey(String key) {
        try {
            String email = symetricEncryption.decrypt(key);
            return sha1(email).startsWith("a51dea5");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return false;
        }
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
