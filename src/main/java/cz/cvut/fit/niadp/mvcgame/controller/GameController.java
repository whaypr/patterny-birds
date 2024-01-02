package cz.cvut.fit.niadp.mvcgame.controller;

import cz.cvut.fit.niadp.mvcgame.command.MoveCannonDownCommand;
import cz.cvut.fit.niadp.mvcgame.command.MoveCannonUpCommand;
import cz.cvut.fit.niadp.mvcgame.model.IGameModel;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;

import java.util.List;

public class GameController {

    private final IGameModel model;
    public GameController(IGameModel model) {
        this.model = model;
    }

    public void processPressedKeys(List<String> pressedKeysCodes) {
        for(String code : pressedKeysCodes) {
            switch(code) {
                case MvcGameConfig.UP_KEY:
                    this.model.registerCommand(new MoveCannonUpCommand(this.model));
                    break;
                case MvcGameConfig.DOWN_KEY:
                    this.model.registerCommand(new MoveCannonDownCommand(this.model));
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
                case MvcGameConfig.DYNAMIC_SHOOTING_MODE_ADD_MISSILE_KEY:
                    this.model.addMissilesForDynamicShootingMode(1);
                    break;
                case MvcGameConfig.DYNAMIC_SHOOTING_MODE_REMOVE_MISSILE_KEY:
                    this.model.removeMissilesForDynamicShootingMode(1);
                    break;
                case MvcGameConfig.SHOOTING_MODE_KEY:
                    this.model.toggleShootingMode();
                    break;
                case MvcGameConfig.UNDO_LAST_COMMAND_KEY:
                    this.model.undoLastCommand();
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
