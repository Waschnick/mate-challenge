package de.axelspringer.ideas.mate.common;

import com.google.gson.Gson;
import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;

public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        if (model instanceof Response) {
            return gson.toJson(new HashMap<>());
        } else {
            return gson.toJson(model);
        }
    }

}