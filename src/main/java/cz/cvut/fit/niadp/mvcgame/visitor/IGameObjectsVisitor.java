package cz.cvut.fit.niadp.mvcgame.visitor;

import cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.gameInfo.AbsGameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;

public interface IGameObjectsVisitor {
    void visitCannon(AbsCannon cannon);
    void visitMissile(AbsMissile missile);
    void visitEnemy(AbsEnemy enemy);
    void visitWall(AbsWall wall);
    void visitGameInfo(AbsGameInfo gameInfo);
}
