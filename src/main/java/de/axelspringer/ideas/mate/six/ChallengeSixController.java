package de.axelspringer.ideas.mate.six;

import de.axelspringer.ideas.mate.five.util.Views;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(path = "/six")
public class ChallengeSixController {


    @RequestMapping(path = {"", "/"})
    public String zero() {
        return Views.fromClasspath("six/zero.html");
    }

    @RequestMapping(
            path = {"", "/"},
            method = {RequestMethod.PUT, RequestMethod.POST},
            consumes = {MediaType.ALL_VALUE}
    )
    @SneakyThrows
    public String oneAndTwoAndThree(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    @RequestBody(required = false) String body) {
        // FIXME Remove System.out
        System.out.println(body);
        if ("123456".equals(body)) {
            return "1";
        }
        httpServletResponse.setStatus(400);
        return "0";
    }


}
