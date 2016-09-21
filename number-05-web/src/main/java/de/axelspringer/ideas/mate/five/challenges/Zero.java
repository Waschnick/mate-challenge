package de.axelspringer.ideas.mate.five.challenges;

import de.axelspringer.ideas.mate.five.util.Files;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Zero {


    @RequestMapping(path = "/")
    public String zero() {
        return Files.fromClasspath("zero.html");
    }
}
