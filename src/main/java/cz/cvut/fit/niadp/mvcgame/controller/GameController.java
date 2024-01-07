package cz.cvut.fit.niadp.mvcgame.controller;

import cz.cvut.fit.niadp.mvcgame.command.*;
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
                    this.model.registerCommand(new CannonMoveUpCommand(this.model));
                    break;
                case MvcGameConfig.DOWN_KEY:
                    this.model.registerCommand(new CannonMoveDownCommand(this.model));
                    break;
                case MvcGameConfig.SHOOT_KEY:
                    this.model.registerCommand(new CannonShootCommand(this.model));
                    break;
                case MvcGameConfig.AIM_UP_KEY:
                    this.model.registerCommand(new CannonAimUpCommand(this.model));
                    break;
                case MvcGameConfig.AIM_DOWN_KEY:
                    this.model.registerCommand(new CannonAimDownCommand(this.model));
                    break;
                case MvcGameConfig.POWER_UP_KEY:
                    this.model.registerCommand(new CannonPowerUpCommand(this.model));
                    break;
                case MvcGameConfig.POWER_DOWN_KEY:
                    this.model.registerCommand(new CannonPowerDownCommand(this.model));
                    break;
                case MvcGameConfig.MOVING_STRATEGY_KEY:
                    this.model.registerCommand(new ToggleMissileMovingStrategyCommand(this.model));
                    break;
                case MvcGameConfig.DYNAMIC_SHOOTING_MODE_ADD_MISSILE_KEY:
                    this.model.registerCommand(new AddMissilesForDynamicShootingModeCommand(
                            this.model,
                            MvcGameConfig.DYNAMIC_SHOOTING_MODE_ADD_STEP)
                    );
                    break;
                case MvcGameConfig.DYNAMIC_SHOOTING_MODE_REMOVE_MISSILE_KEY:
                    this.model.registerCommand(new RemoveMissilesForDynamicShootingModeCommand(
                            this.model,
                            MvcGameConfig.DYNAMIC_SHOOTING_MODE_REMOVE_STEP)
                    );
                    break;
                case MvcGameConfig.TOGGLE_MISSILES_WALL_PIERCING_KEY:
                    this.model.registerCommand(new ToggleMissilesWallPiercingCommand(this.model));
                    break;
                case MvcGameConfig.TOGGLE_MISSILES_ENEMY_PIERCING_KEY:
                    this.model.registerCommand(new ToggleMissilesEnemyPiercingCommand(this.model));
                    break;
                case MvcGameConfig.SHOOTING_MODE_KEY:
                    this.model.registerCommand(new ToggleCannonShootingModeCommand(this.model));
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
