package cz.cvut.fit.niadp.mvcgame.controller;

import cz.cvut.fit.niadp.mvcgame.memento.CareTaker;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;

import java.util.List;

public class GameController {

    private final GameModel model;
    public GameController(GameModel model) {
        this.model = model;
    }

    public void processPressedKeys(List<String> pressedKeysCodes) {
        for(String code : pressedKeysCodes) {
            switch(code) {
                case MvcGameConfig.UP_KEY:
                    this.model.moveCannonUp();
                    break;
                case MvcGameConfig.DOWN_KEY:
                    this.model.moveCannonDown();
                    break;
                case MvcGameConfig.SHOOT_KEY:
                    this.model.cannonShoot();
                    break;
                case MvcGameConfig.AIM_UP_KEY:
                    this.model.aimCannonUp();
                    break;
                case MvcGameConfig.AIM_DOWN_KEY:
                    this.model.aimCannonDown();
                    break;
                case MvcGameConfig.POWER_UP_KEY:
                    this.model.cannonPowerUp();
                    break;
                case MvcGameConfig.POWER_DOWN_KEY:
                    this.model.cannonPowerDown();
                    break;
                case MvcGameConfig.MOVING_STRATEGY_KEY:
                    this.model.toggleMovingStrategy();
                    break;
                case MvcGameConfig.SHOOTING_MODE_KEY:
                    this.model.toggleShootingMode();
                    break;
                case MvcGameConfig.STORE_SNAPSHOT_KEY:
                    CareTaker.getInstance().createMemento();
                    break;
                case MvcGameConfig.RESTORE_SNAPSHOT_KEY:
                    CareTaker.getInstance().setMemento();
                    break;
                case MvcGameConfig.EXIT_KEY:
                    System.exit(0);
                    break;
                default:
                    //nothing
            }
        }
        this.model.update();
        pressedKeysCodes.clear();
    }
}
