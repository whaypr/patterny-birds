package cz.cvut.fit.niadp.mvcgame.visitor;

import cz.cvut.fit.niadp.Utils;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;


public class GameObjectsSoundMaker implements IGameObjectsVisitor {

    @Override
    public void visitCannon(AbsCannon cannon) {
        Utils.getInstance().playSound(MvcGameConfig.CANNON_MOVE_SOUND_RESOURCE);
    }

    @Override
    public void visitMissile(AbsMissile missile) {
        Utils.getInstance().playSound(MvcGameConfig.MISSILE_LAUNCH_SOUND_RESOURCE);
    }
}
