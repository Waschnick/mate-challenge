package de.axelspringer.ideas.mate;

import de.axelspringer.ideas.mate.common.SparkExceptionHandler;
import de.axelspringer.ideas.mate.common.SparkUtils;
import de.axelspringer.ideas.mate.five.ChallengeFiveController;
import de.axelspringer.ideas.mate.five.ChallengeFivePart2Controller;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class MateChallengeApp {

    private static final Logger LOG = LoggerFactory.getLogger(MateChallengeApp.class);
    private static final int PORT = 8080;

    public static void main(String[] args) {

        Spark.staticFiles.location("/public");

        // HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        Spark.port(getPort());
        Spark.staticFileLocation("/public");
        Spark.exception(Exception.class, new SparkExceptionHandler());

        // Set up before-filters (called before each get/post)
        Spark.before("*", SparkUtils.addTrailingSlashes);

        //Set up after-filters (called after each get/post)
        Spark.after("*", SparkUtils.addGzipHeader);

//        get("/", (request, response) -> {
//            return new ModelAndView(new HashMap(), "index.hbs");
//        }, engine);

        ChallengeFiveController.init();
        ChallengeFivePart2Controller.init();


    }

    private static int getPort() {
        String property = System.getProperty("server.port");
        if (StringUtil.isNotBlank(property)) {
            return Integer.valueOf(property);
        }
        return PORT;
    }

}
