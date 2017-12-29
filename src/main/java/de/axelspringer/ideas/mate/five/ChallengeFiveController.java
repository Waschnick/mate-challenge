package de.axelspringer.ideas.mate.five;

import de.axelspringer.ideas.mate.common.MailSender;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static de.axelspringer.ideas.mate.five.Sha1.sha1;

public class ChallengeFiveController {

    private static final Logger log = LoggerFactory.getLogger(ChallengeFiveController.class);
    private static final HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

    private static MailSender mailSender = new MailSender();
    private static QrGenerator qrGenerator = new QrGenerator();

    public static void init() {
        Spark.get("/five/", rootRoute, engine);
        Spark.put("/five/", oneAndTwoAndThree, engine);
        Spark.post("/five/", oneAndTwoAndThree, engine);
    }

    public static TemplateViewRoute rootRoute = (request, response) -> {
        return new ModelAndView(new HashMap(), "five/zero.html");
    };


    public static TemplateViewRoute oneAndTwoAndThree = (request, response) -> {
        log.info("Challenge one and two called.");
        String body = request.body();

        if (StringUtils.isNotBlank(body)) {
            log.info("Found Body: " + body);
            body = URLDecoder.decode(body, "UTF-8");
            log.info("Found Body Decoded: " + body);

            Map<String, String> parameters = bodyToParameters(body);

            if (hasNoContentLength(request)) {
                log.info("Challenge two: Nope.");
                return new ModelAndView(new HashMap(), "five/two_error.html");
            }
            String name = parameters.get("name");
            if (name != null) {
                String email = parameters.get("email");
                if (email == null) {
                    log.info("Name gefunden, Challenge 2 complete! Name: " + name);
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    return new ModelAndView(params, "five/three.html");
                } else {
                    log.info("E-Mail gefunden: " + email);
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("digest", sha1(email));

                    if (sha1(email).startsWith("a51dea5")) {
                        log.info("Hash for E-Mail " + email + " is correct!!!");

                        if (EmailValidator.getInstance().isValid(email)) {
                            mailSender.send(email, "AS Ideas Mate-Challenge 5", "<pre>" + qrGenerator.generateQrText(email) + "</pre>");
                            log.info("Challenge three: Complete! name: " + name + " mail: " + email);

                            return new ModelAndView(params, "five/three_with_email_ok.html");
                        } else {
                            log.info("Challenge three: Nope, no real mail.");

                            return new ModelAndView(params, "five/three_with_email_invalid.html");
                        }
                    } else {
                        log.info("Challenge three: wrong hash. name: " + name + " mail: " + email);

                        return new ModelAndView(params, "five/three_with_email.html");
                    }
                }
            } else {
                log.info("Body found, but no name: {}", parameters);
            }
        }

        return new ModelAndView(new HashMap(), "five/two.html");
    };


    private static Map<String, String> bodyToParameters(String body) {
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

    private static boolean hasNoContentLength(Request request) {
        for (String headerName : request.headers()) {
            if (headerName.toLowerCase().startsWith("content-length")) {
                log.info("Found content-length headerName: {} = {}", headerName, request.headers(headerName));
                return false;
            }
        }
        log.info("No content length!");
        return true;
    }


}
