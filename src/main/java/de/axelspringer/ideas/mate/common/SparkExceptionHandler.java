package de.axelspringer.ideas.mate.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class SparkExceptionHandler implements spark.ExceptionHandler<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(SparkExceptionHandler.class);

    @Override
    public void handle(Exception e, Request request, Response response) {
        LOG.error(e.getMessage(), e);
        if (e instanceof ClassCastException) {
            response.type("application/json");
            response.status(400);
            response.body("{\"message\":\"Bad request or wrong format: " + e.getMessage() + "\"}");
        } else {
            response.type("application/json");
            response.status(500);
            response.body("{\"message\":\"Server Error: " + e.getMessage() + "\"}");
        }


    }
}
