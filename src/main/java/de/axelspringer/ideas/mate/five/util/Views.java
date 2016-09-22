package de.axelspringer.ideas.mate.five.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Views {

    @Data
    @AllArgsConstructor
    public static class ViewParameter {
        public String key;
        public String value;
    }

    @SneakyThrows
    public static String fromClasspath(String file, ViewParameter... params) {
        String view = IOUtils.toString(Views.class.getClassLoader().getResourceAsStream(file), Charsets.UTF_8);
        for (ViewParameter param : params) {
            view = view.replaceAll("{" + param.getKey() + "}", param.getValue());
        }
        return view;
    }
}
