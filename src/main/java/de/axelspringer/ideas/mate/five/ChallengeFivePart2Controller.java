package de.axelspringer.ideas.mate.five;

import de.axelspringer.ideas.mate.common.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Route;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static de.axelspringer.ideas.mate.five.Sha1.sha1;

public class ChallengeFivePart2Controller {

    private static final Logger log = LoggerFactory.getLogger(ChallengeFivePart2Controller.class);
    private static final HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

    private static MailSender mailSender = new MailSender();
    private static SymetricEncryption symetricEncryption = new SymetricEncryption();


    public static void init() {
        Spark.get("five/cheesecake/:key", fiveRoute);
        Spark.get("five/cheesecake/:key/solve", fiveSolveRoute);

    }

    public static Route fiveRoute = (request, response) -> {
        String key = request.params("key");

        log.info("Called last challenge with key: " + key);
        if (isValidKey(key)) {
            log.info("Key is valid!");
            Map<String, String> params = new HashMap<>();
            params.put("key", key);
            return new ModelAndView(params, "five/five.html");
        } else {
            return new ModelAndView(new HashMap(), "five/five_nope.html");
        }
    };

    public static Route fiveSolveRoute = (request, response) -> {
        String key = request.params("key");
        String s1 = request.params("s1");
        String s2 = request.params("s2");
        String s3 = request.params("s3");
        String s4 = request.params("s4");
        String s5 = request.params("s5");
        String s6 = request.params("s6");
        String s7 = request.params("s7");
        String s8 = request.params("s8");

        log.info("Chessecake with key: " + key);

        if (!isValidKey(key)) {
            return new ModelAndView(new HashMap(), "five/five_nope.html");
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
    };

    private static void doWin(String key) {
        String email = symetricEncryption.decrypt(key);
        String gewinnCode = symetricEncryption.encrypt("winner:" + email);
        log.info("WIN for E-Mail!!!");
        mailSender.send(email, "WIN! AS Ideas Mate-Challenge 5", "Hey! Hier ist dein Gewinn-Code: " + gewinnCode);
        mailSender.send("barriwaschi@gmail.com", "WIN! AS Ideas Mate-Challenge 5", "Gewinner: " + email + " mit Code: " + gewinnCode);
    }

    private static boolean are8differenStrings(String... strings) {
        Set<String> mySet = new HashSet<>();
        for (String string : strings) {
            mySet.add(string);
        }
        return mySet.size() == 8;
    }

    private static boolean isValidKey(String key) {
        try {
            String email = symetricEncryption.decrypt(key);
            return sha1(email).startsWith("a51dea5");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return false;
        }
    }


}
