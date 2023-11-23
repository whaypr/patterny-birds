package cz.cvut.fit.niadp.mvcgame.visitor;

import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;

public interface IGameObjectsVisitor {
    void visitCannon(AbsCannon cannon);
    void visitMissile(AbsMissile missile);
    // TODO visit collisions, gameInfo, enemy

}
