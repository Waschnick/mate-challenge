package de.axelspringer.ideas.mate.six;

import java.io.IOException;

public class Caeser {

    public int offset = 3;

    public String encrypt(String s) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char t = s.charAt(i);
            if (t == ' ') {
                sb.append(" ");
            } else if (t >= 'A' && t <= 'Z') {
                int t1 = t - 'A' + offset;
                t1 = t1 % 26;
                sb.append((char) (t1 + 'A'));
            } else if (t >= 'a' && t <= 'z') {
                int t1 = t - 'a' + offset;
                t1 = t1 % 26;
                sb.append((char) (t1 + 'a'));
            }
        }
        return sb.toString();
    }


    public String decrypt(String s) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char t = s.charAt(i);
            if (t == ' ') {
                sb.append(" ");
            } else if (t >= 'A' && t <= 'Z') {
                int t1 = t - 'A' - offset;
                if (t1 < 0) {
                    t1 = 26 + t1;
                }
                sb.append((char) (t1 + 'A'));
            } else if (t >= 'a' && t <= 'z') {
                int t1 = t - 'a' - offset;
                if (t1 < 0) {
                    t1 = 26 + t1;
                }
                sb.append((char) (t1 + 'a'));
            }
        }
        return sb.toString();
    }
}
