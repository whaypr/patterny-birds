package cz.cvut.fit.niadp.mvcgame.state;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon.AbsCannon;

public class DynamicShootingMode implements IShootingMode {

    private int numberOfMissiles;

    public DynamicShootingMode(int numberOfMissiles) {
        this.numberOfMissiles = numberOfMissiles;
    }

    public int getNumberOfMissiles() {
        return numberOfMissiles;
    }

    @Override
    public String getName() {
        return DynamicShootingMode.class.getSimpleName()
                .replace("ShootingMode", "");
    }

    @Override
    public void shoot(AbsCannon cannon) {
        int n = numberOfMissiles;

        if (n % 2 == 1) {
            cannon.primitiveShoot();
            n--;
        }

        n = n / 2;

        for (int i = 0; i < n; ++i) {
            cannon.aimUp();
            cannon.primitiveShoot();
        }
        for (int i = 0; i < n; ++i) {
            cannon.aimDown();
        }
        for (int i = 0; i < n; ++i) {
            cannon.aimDown();
            cannon.primitiveShoot();
        }
        for (int i = 0; i < n; ++i) {
            cannon.aimUp();
        }
    }

    public void addMissiles(int toAdd) {
        this.numberOfMissiles += toAdd;
        int max = (int)Math.floor(2*Math.PI / MvcGameConfig.ANGLE_STEP);
        this.numberOfMissiles = Math.min(this.numberOfMissiles, max);
    }

    public void removeMissiles(int toRemove) {
        this.numberOfMissiles -= toRemove;
        this.numberOfMissiles = Math.max(this.numberOfMissiles, 1);
    }
}
