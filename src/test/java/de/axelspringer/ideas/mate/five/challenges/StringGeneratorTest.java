package de.axelspringer.ideas.mate.five.challenges;

import org.junit.Test;

public class StringGeneratorTest {

    @Test
    public void blub() throws Exception {
        String a = "Aa";
        String b = "BB";

        // FIXME Remove System.out
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());

        System.out.println("---");

        //createHash(1);

        System.out.println("AaAa".hashCode());
        System.out.println("BBBB".hashCode());
        System.out.println("AaBB".hashCode());
        System.out.println("BBAa".hashCode());


        // FIXME Remove System.out
        System.out.println("---");

        System.out.println((a + a + a + b));
        System.out.println((a + a + b + a));
        System.out.println((a + a + b + b));
        System.out.println((a + b + b + b));
        System.out.println((b + a + a + b));
        System.out.println((b + a + b + a));
        System.out.println((b + a + b + b));
        System.out.println((b + b + b + b));


    }

    private String createHash(int i) {
        return "";
    }
}
