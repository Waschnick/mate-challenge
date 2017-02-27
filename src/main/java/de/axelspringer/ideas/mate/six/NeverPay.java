package de.axelspringer.ideas.mate.six;

public class NeverPay {

    private int offset = 1;

    public String encode(String data) {
        return encode(data.toCharArray());
    }

    public String encode(char[] charArray) {
        char[] cryptArray = new char[charArray.length];
        // erstmal ein leeres Char Array erstellen

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c != ' ') {
                // ursprüngliches Zeichen plus Offset modulo 128
                cryptArray[i] = (char) ((c + offset) % 128);
            } else {
                cryptArray[i] = c;
            }
        }
        return new String(cryptArray);
    }

    public String decode(String data) {
        return decode(data.toCharArray());
    }

    public String decode(char[] charArray) {
        char[] cryptArray = new char[charArray.length];

        int shift;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c != ' ') {
                // nach Verschiebung kleiner 0? Wenn ja, dann 128 addieren
                // wenn nein, einfach nur modulo 128
                if (c - offset < 0) {
                    shift = c - offset + 128;
                } else {
                    shift = (c - offset) % 128;
                }
                cryptArray[i] = (char) (shift);
            } else {
                cryptArray[i] = c;
            }

        }
        return new String(cryptArray);

    }

}
