package cz.cvut.fit.niadp;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public final class Utils {

    // volatile for double check lock to work correctly
    private static volatile Utils instance;

    private Utils() {}

    public static Utils getInstance() {
        // double-checked locking
        Utils result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Utils.class) {
            if (instance == null) {
                instance = new Utils();
            }
            return instance;
        }
    }

    public void playSound(String path) {
        try {
            URL resource = getClass().getClassLoader().getResource(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(resource.toURI()).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
