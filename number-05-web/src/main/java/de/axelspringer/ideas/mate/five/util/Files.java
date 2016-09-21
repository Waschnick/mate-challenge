package de.axelspringer.ideas.mate.five.util;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

public class Files {

    @SneakyThrows
    public static String fromClasspath(String file) {
        return IOUtils.toString(Files.class.getClassLoader().getResourceAsStream(file));
    }
}
