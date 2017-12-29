package de.axelspringer.ideas.mate.five;

import de.axelspringer.ideas.mate.common.RestClient;
import org.junit.Test;

import java.net.URLEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class ChallengeSixTestManuell {

    @Test
    public void rootPage_shouldReturnText() {
        String result = RestClient.get("http://localhost:8080/five/", String.class);
        assertThat(result).isEqualTo("<pre>\n" +
                "\n" +
                "\n" +
                "  _     _\n" +
                " (_)   | |\n" +
                "  _  __| | ___  __ _ ___\n" +
                " | |/ _` |/ _ \\/ _` / __|\n" +
                " | | (_| |  __/ (_| \\__ \\\n" +
                " |_|\\__,_|\\___|\\__,_|___/\n" +
                "\n" +
                "\n" +
                "                  _\n" +
                "                 | |\n" +
                "  _ __ ___   __ _| |_ ___\n" +
                " | '_ ` _ \\ / _` | __/ _ \\\n" +
                " | | | | | | (_| | ||  __/\n" +
                " |_| |_| |_|\\__,_|\\__\\___|\n" +
                "\n" +
                "\n" +
                "       _           _ _\n" +
                "      | |         | | |\n" +
                "   ___| |__   __ _| | | ___ _ __   __ _  ___\n" +
                "  / __| '_ \\ / _` | | |/ _ \\ '_ \\ / _` |/ _ \\\n" +
                " | (__| | | | (_| | | |  __/ | | | (_| |  __/\n" +
                "  \\___|_| |_|\\__,_|_|_|\\___|_| |_|\\__, |\\___|\n" +
                "                                  __/ |\n" +
                "                                 |___/\n" +
                "\n" +
                "Challenge 0: Lasst die Spiele beginnen\n" +
                "==========================\n" +
                "\n" +
                "Die ClubMate ist mal wieder alle. Oder eher das Bier. Lasst\n" +
                "also die Spiele beginnen! Dieses Mal gewinnt jeder, der alle\n" +
                "Challenges besteht, zwei Craft Beer von Brewer's Tribute aus\n" +
                "Berlin. Alternativ ist natürlich ein Tausch gegen Mate oder\n" +
                "Cola möglich.\n" +
                "\n" +
                "Wie es funktioniert\n" +
                "===================\n" +
                "\n" +
                "Du wirst insgesamt fünf Challenges absolvieren müssen. Naja,\n" +
                "eigentlich eher sechs, aber die erste zähle wir mal nicht ;)\n" +
                "\n" +
                "</pre>\n" +
                "<!-\n" +
                "=====================\n" +
                "Challenge 0 Complete!\n" +
                "\n" +
                "=====================\n" +
                "\n" +
                "Stimmt, das war einfach. Vielleicht zu einfach...\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Challenge 1: Don't stop with GET, there is so much more to POST\n" +
                "===============================================================\n" +
                "->");
    }

    @Test
    public void rootPage_withPost_shouldReturnError() throws Exception {
        String result = RestClient.post("http://localhost:8080/five/", String.class, null);
        assertThat(result).isEqualTo("<pre>\n" +
                "Woohooo! Challenge 1 Complete!\n" +
                "-------------------------------\n" +
                "\n" +
                "Yeah, das war nicht schwer, oder? Wieder ein Schritt näher am Bier!\n" +
                "\n" +
                "Aber die eigentliche Frage ist:\n" +
                "Was ist das eigentlich, was du hier POSTest? Ich schätze dein Name wäre\n" +
                "ganz nett...\n" +
                "\n" +
                "\n" +
                "Challenge 2: Was ist ein Namen?\n" +
                "===============================\n" +
                "\n" +
                "Anleitung: POSTe deinen \"name\"n\n" +
                "\n" +
                "</pre>");
    }

    @Test
    public void rootPage_withPostAndNameAndContentLength_shouldReturnChallengeN() throws Exception {
        Object body = URLEncoder.encode("name=Waschi", "UTF-8");
        String result = RestClient.post("http://localhost:8080/five/", String.class, body);
        assertThat(result).isEqualTo("<pre>\n" +
                "Challenge 2: Completed!\n" +
                "=======================\n" +
                "\n" +
                "Hey Waschi, schön dich kennenzulernen! Da haben wir ja einen richtigen\n" +
                "Senkrechtstarter hier. Lust auf mehr?\n" +
                "\n" +
                "\n" +
                "\n" +
                "Challenge 3: Is it worth it? Let me work it.\n" +
                "=============================================\n" +
                "\n" +
                "Diese Challenge ist ein wenig anders und wird etwas Aufwand auf deiner Seite\n" +
                "erfodern.\n" +
                "\n" +
                "Also wenn du nicht unter einem Stein lebst, dann hast du schon von Bitcoins gehört.\n" +
                "Ja, es war nur eine Frage der Zeit, bis sowas kommt, aber hey, das wird schon.\n" +
                "Der Grund wieso ich Bitcoins erwähne ist, dass es eine gemeinsame Geschichte mit\n" +
                "E-Mails hat. Insbesondere das proof-of-work system[1], also hashing, bis der hash\n" +
                "einem bestimmten Muster entspricht, damit man Spam bekämpft.\n" +
                "\n" +
                "Spam ist böse. Du bist gut.\n" +
                "\n" +
                "Ich sende dir eine E-Mail mit Challenge 4 wenn du mir eine E-Mail Adresse gibst,\n" +
                "deren 'sha1' Hash mit 'a51dea5' beginnt.\n" +
                "\n" +
                "Dann POSTe es als 'email' zusammen mit deinem 'name'n.\n" +
                "\n" +
                "Als Beispiel: (das \"-n\" unterdrückt das führende newline-Zeichen)\n" +
                "\n" +
                "$ echo -n example+19546430@example.com | sha1sum\n" +
                "a51dea52388b4acd501e296382873057560c4a0c  -\n" +
                "^^^^^^^\n" +
                "Das würde funktionieren, weil der sha1 hash mit 'a51dea5' anfängt.\n" +
                "Als Beispiel: Auf einem moderen Laptop sollte die Generierung eines Matches für in weniger\n" +
                "als 10 Minute funktionieren.\n" +
                "\n" +
                "\n" +
                "Pro tip: Test deinen Code mit einem führenden \"a\", was einfach sein sollte,\n" +
                "danach mit führendem \"a5\", dann mit \"a51\", etc. bis du dir sicher bist,\n" +
                "dass dein Code funktionert, da 7 Zeichen zu finden sehr lange dauern kann.\n" +
                "\n" +
                "\n" +
                "Am besten testest du deinen Code auch mal mit der Beispiel-Adresse.\n" +
                "\n" +
                "Pro tip2: *hust* urlencoding *hust*\n" +
                "\n" +
                "[1] https://en.wikipedia.org/wiki/Proof-of-work_system\n" +
                "</pre>");
    }

    @Test
    public void rootPage_withPostAndNameAndEmail_shouldReturnChallengeN() throws Exception {
        Object body = URLEncoder.encode("name=Waschi&email=barriwaschi@gmail.com", "UTF-8");
        String result = RestClient.post("http://localhost:8080/five/", String.class, body);
        assertThat(result).isEqualTo("<pre>\n" +
                "Challenge 3: Is it worth it? Let me work it.\n" +
                "=============================================\n" +
                "\n" +
                "Diese Challenge ist ein wenig anders und wird etwas Aufwand auf deiner Seite\n" +
                "erfodern.\n" +
                "\n" +
                "Also wenn du nicht unter einem Stein lebst, dann hast du schon von Bitcoins gehört.\n" +
                "Ja, es war nur eine Frage der Zeit, bis sowas kommt, aber hey, das wird schon.\n" +
                "Der Grund wieso ich Bitcoins erwähne ist, dass es eine gemeinsame Geschichte mit\n" +
                "E-Mails hat. Insbesondere das proof-of-work system[1], also hashing, bis der hash\n" +
                "einem bestimmten Muster entspricht, damit man Spam bekämpft.\n" +
                "\n" +
                "Spam ist böse. Du bist gut.\n" +
                "\n" +
                "Ich sende dir eine E-Mail mit Challenge 4 wenn du mir eine E-Mail Adresse gibst,\n" +
                "deren 'sha1' Hash mit 'a51dea5' beginnt.\n" +
                "\n" +
                "Dann POSTe es als 'email' zusammen mit deinem 'name'n.\n" +
                "\n" +
                "Als Beispiel: (das \"-n\" unterdrückt das führende newline-Zeichen)\n" +
                "\n" +
                "$ echo -n example+19546430@example.com | sha1sum\n" +
                "a51dea52388b4acd501e296382873057560c4a0c  -\n" +
                "^^^^^^^\n" +
                "Das würde funktionieren, weil der sha1 hash mit 'a51dea5' anfängt.\n" +
                "Als Beispiel: Auf einem moderen Laptop sollte die Generierung eines Matches für in weniger\n" +
                "als 10 Minute funktionieren.\n" +
                "\n" +
                "\n" +
                "Pro tip: Test deinen Code mit einem führenden \"a\", was einfach sein sollte,\n" +
                "danach mit führendem \"a5\", dann mit \"a51\", etc. bis du dir sicher bist,\n" +
                "dass dein Code funktionert, da 7 Zeichen zu finden sehr lange dauern kann.\n" +
                "\n" +
                "Am besten testest du deinen Code auch mal mit der Beispiel-Adresse.\n" +
                "\n" +
                "\n" +
                "[1] https://en.wikipedia.org/wiki/Proof-of-work_system\n" +
                "\n" +
                "Was du gesendet hast:\n" +
                "---------------------\n" +
                "\n" +
                "Name: Waschi\n" +
                "Email: barriwaschi@gmail.com\n" +
                "\n" +
                "Berechnet\n" +
                "---------\n" +
                "Digest: 24252fce2bf93d195ed2ec7ca44d9f5e2e4f2368\n" +
                "\n" +
                "\n" +
                "    ... Muss ich dazu noch was sagen?\n" +
                "</pre>");
    }

    @Test
    public void rootPage_withPostAndNameAndEmail_shouldSendMail() throws Exception {
        Object body = URLEncoder.encode("name=Waschi&email=barriwaschi+130219734@gmail.com", "UTF-8");
        String result = RestClient.post("http://localhost:8080/five/", String.class, body);
        assertThat(result).isEqualTo("<pre>\n" +
                "Challenge 3: Is it worth it? Let me work it.\n" +
                "=============================================\n" +
                "\n" +
                "Diese Challenge ist ein wenig anders und wird etwas Aufwand auf deiner Seite\n" +
                "erfodern.\n" +
                "\n" +
                "Also wenn du nicht unter einem Stein lebst, dann hast du schon von Bitcoins gehört.\n" +
                "Ja, es war nur eine Frage der Zeit, bis sowas kommt, aber bleib bei mir.\n" +
                "Der Grund wieso ich Bitcoins erwähne ist, dass es eube gemeinsame Gesichte mit\n" +
                "E-Mails haf. Insbesondere das proof-of-work system[1], also hashing, bis der hash\n" +
                "einem bestimmten Muster entspricht, damit man Spam bekämpft.\n" +
                "\n" +
                "Spam ist böse. Du bist gut.\n" +
                "\n" +
                "Ich sende dir eine E-Mail mit Challenge 4 wenn du mir eine E-Mai Adresse gibst,\n" +
                "deren 'sha1' Hash mit 'a51dea5' beginnt.\n" +
                "\n" +
                "Dann POSTe es als 'email' zusammen mit deinem name.\n" +
                "\n" +
                "Als Beispiel: (das \"-n\" unterdrückt das führende newline-Zeichen)\n" +
                "\n" +
                "$ echo -n example+19546430@example.com | sha1sum\n" +
                "a51dea52388b4acd501e296382873057560c4a0c  -\n" +
                "^^^^^^^\n" +
                "Das würde funktionieren, weil der sha1 hash mit 'a51dea5' anfängt.\n" +
                "Als Beispiel: Auf einem moderen Laptop sollte die Generierung eines Matches für in weniger\n" +
                "als 10 Minute funktionieren.\n" +
                "\n" +
                "\n" +
                "Pro tip: Test deinen Code mit einem führenden \"a\", was einfach sein sollte,\n" +
                "danach mit führendem \"a5\", dann mit \"a51\", etc. bis du dir sicher bist,\n" +
                "dass dein Code funktionert, da 7 Zeichen zu finden sehr lange dauern kann.\n" +
                "\n" +
                "Am besten testest du deinen Code auch mal mit der Beispiel-Adresse.\n" +
                "\n" +
                "\n" +
                "[1] https://en.wikipedia.org/wiki/Proof-of-work_system\n" +
                "\n" +
                "Was du gesendet hast:\n" +
                "---------------------\n" +
                "\n" +
                "Name: Waschi\n" +
                "Email: barriwaschi+130219734@gmail.com\n" +
                "\n" +
                "Berechnet\n" +
                "---------\n" +
                "Digest: {digest}\n" +
                "\n" +
                "\n" +
                "Gute Arbeit, Waschi! Challenge 3 Complete!!\n" +
                "---------------------------------------\n" +
                "\n" +
                "Für die Challenge 4 schau unter 'barriwaschi+130219734@gmail.com' nach...\n" +
                "</pre>");
    }
}
