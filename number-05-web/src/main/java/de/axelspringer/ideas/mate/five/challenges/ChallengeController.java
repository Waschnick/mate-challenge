package de.axelspringer.ideas.mate.five.challenges;

import de.axelspringer.ideas.mate.five.util.Files;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
@RestController
public class ChallengeController {


    @RequestMapping(path = "/")
    public String zero() {
        return Files.fromClasspath("zero.html");
    }

    @RequestMapping(
            path = "/",
            method = {RequestMethod.PUT, RequestMethod.POST},
            consumes = {MediaType.ALL_VALUE}
    )
    public String one(HttpServletRequest httpServletRequest,
                      @RequestBody(required = false) String body) {

        log.info("body: " + body);
        if (StringUtils.isBlank(body)) {
            if (hasNoContentLength(httpServletRequest)) {
                return Files.fromClasspath("one_error.html");
            }
        }

        return Files.fromClasspath("one.html");
    }

    private boolean hasNoContentLength(HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        for (String headerName : Collections.list(headerNames)) {
            if ("Content-length".equalsIgnoreCase(headerName)) {
                log.info("Found content-length header.");
                return false;
            }
        }
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
