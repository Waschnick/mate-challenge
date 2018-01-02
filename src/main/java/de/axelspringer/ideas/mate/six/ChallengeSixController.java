package de.axelspringer.ideas.mate.six;

import de.axelspringer.ideas.mate.common.MailSender;
import de.axelspringer.ideas.mate.five.ChallengeFiveController;
import de.axelspringer.ideas.mate.five.QrGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Random;

public class ChallengeSixController {


    private static final Logger log = LoggerFactory.getLogger(ChallengeFiveController.class);
    private static final HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

    private static MailSender mailSender = new MailSender();
    private static QrGenerator qrGenerator = new QrGenerator();
    private static Random random = new Random();

    public static void init() {
        Spark.get("/six/cheesecake/pin", pinRoute);
    }

    public static Route pinRoute = (request, response) -> {
        String pin = request.queryParams("pin");
        Thread.sleep(random.nextInt(50) + 20);
        return "123456".equalsIgnoreCase(pin);
    };


}
