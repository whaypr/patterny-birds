package cz.cvut.fit.niadp.mvcgame.observer;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class SoundMaker implements IObserver {

    private void playSound(String path) {
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

    private void playMissileSpawnSound() {
        playSound(MvcGameConfig.MISSILE_LAUNCH_SOUND_RESOURCE);
    }

    private void playMissileWallHitSound() {
        playSound(MvcGameConfig.MISSILE_WALL_HIT_SOUND_RESOURCE);
    }

    private void playMissileEnemyHitSound() {
        playSound(MvcGameConfig.MISSILE_ENEMY_HIT_SOUND_RESOURCE);
    }


    @Override
    public void update(Aspect aspect) {
        switch (aspect) {
            case MISSILE_SPAWN:
                playMissileSpawnSound();
                break;
            case MISSILE_WALL_HIT:
                playMissileWallHitSound();
                break;
            case MISSILE_ENEMY_HIT:
                playMissileEnemyHitSound();
                break;
            default: {}
        }
    }
}
