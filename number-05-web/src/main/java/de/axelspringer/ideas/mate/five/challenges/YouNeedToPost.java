package de.axelspringer.ideas.mate.five.challenges;

import de.axelspringer.ideas.mate.five.util.Files;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class YouNeedToPost {

    @RequestMapping(path = "/", method = {RequestMethod.PUT, RequestMethod.POST})
    public String one(HttpServletRequest httpServletRequest) {
        if (hasNoContentLength(httpServletRequest)) {
            return Files.fromClasspath("one_error.html");
        }
//    Challenge 2:What's in a name?
//            ==============================
//
//    Instructions:
//    POST your
//    "name"
//
//    What's in a name? that which we call a Sebastian
//    By any
//    other name
//    would code
//    as fast;
//
//    Challenge 2Complete!!
//            ----------------------

        String value = "Challenge 3: Is it worth it? Let me work it.\n" +
                "=============================================\n" +
                "\n" +
                "This one will be a little different, and will require effort on your part.\n" +
                "\n" +
                "So if you have not been hiding under a rock you may have heard of Bitcoin.\n" +
                "Yes yes, it was only a matter of time before this came up, but bear with us.\n" +
                "The reason we mention bitcoin is that it shares a particular history with email.\n" +
                "Specifically the proof-of-work system[1], hashing to match a specific pattern,\n" +
                "was created to combate spam. Spam is bad. You are good.\n" +
                "\n" +
                "We will email you challenge 4 if you can create an email address that when\n" +
                "hashed with `sha1` begins with 'c0ffee'. Then post it in 'email' with your name.\n" +
                "\n" +
                "For example: (the \"-n\" subpresses the trailing newline)\n" +
                "\n" +
                "$ echo -n example+1899634@example.com | sha1sum\n" +
                "c0ffee9c56ed151525700240ccb034ab34f27239  -\n" +
                "^^^^^^\n" +
                "This would work, because the sha1 hash starts with 'c0ffee'.\n" +
                "As a reference, on a modern laptop it should generate a match in under a minute.\n" +
                "\n" +
                "Pro tip: Verify your code works by matching on leading \"c\", which should be easy,\n" +
                "then leading \"c0\", then \"c0f\", etc. until you are sure your code works.\n" +
                "\n" +
                "Also verify that your code works with the example email above.\n" +
                "\n" +
                "\n" +
                "[1] https://en.wikipedia.org/wiki/Proof-of-work_system\n" +
                "\n" +
                "What you are sending:\n" +
                "\n" +
                "Name: Sebastian Ô£ô\n" +
                "Email: barriwaschi+8418162@gmail.com Ô£ô\n" +
                "Digest: c0ffee05c7fb022c19bf2e4d3e62fcee94c4fb8f Ô£ô\n" +
                "\n" +
                "\n" +
                "Good job, Sebastian! Challenge 3 Complete!!\n" +
                "---------------------------------------\n" +
                "\n" +
                "Look for challenge 4 at barriwaschi+8418162@gmail.com...";

        return value;
    }

        private boolean hasNoContentLength(HttpServletRequest httpServletRequest) {
                return true;
        }


//    Woohooo!Challenge 1Complete!
//            -------------------------------
//
//    Well well
//    well,aren't we special? We got us a big shot over here!
//
//    But the
//    real question
//    is:
//    What is
//    it that
//    you are
//    posting over
//    here?
//    I suppose
//    your name
//    would be
//    nice...
//
//
//    Challenge 2:What's in a name?
//            ==============================
//
//    Instructions:
//    POST your
//    "name"
//
//    What's in a name? that which we call a Sebastian
//    By any
//    other name
//    would code
//    as fast;
//
//    Challenge 2Complete!!
//            ----------------------
}
