package de.axelspringer.ideas.mate;

import org.apache.commons.lang3.ArrayUtils;

import javax.sound.sampled.*;
import java.util.List;

public final class StreamPlayer {

    public final AudioFormat audioFormat;
    public final DataLine.Info info;
    public final SourceDataLine soundLine;

    public StreamPlayer() {
        try {
            audioFormat = new AudioFormat(4000, 8, 1, true, false);
            info = new DataLine.Info(SourceDataLine.class, audioFormat, 1500);
            soundLine = (SourceDataLine) AudioSystem.getLine(info);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void playSound(List<Byte> buffer) {
        playSound(buffer.toArray(new Byte[buffer.size()]));
    }

    public static void playSound(Byte[] buffer) {
        playSound(ArrayUtils.toPrimitive(buffer));
    }

    public static void playSound(byte[] buffer) {
        try {
            StreamPlayer streamPlayer = new StreamPlayer();
            streamPlayer.startSoundLine();
            streamPlayer.playStream(buffer);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void startSoundLine() throws LineUnavailableException {
        soundLine.open(audioFormat);
        soundLine.start();
    }

    public void playStream(byte[] buffer) {
        soundLine.write(buffer, 0, buffer.length);
    }
}