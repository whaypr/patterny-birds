package cz.cvut.fit.niadp.mvcgame.model.collisions;

import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;

public interface ICollisionChecker {
    GameObject getReference();
    Vector getHitBox();

    CollisionResponse checkAndRespond(AbsMissile missile);
    CollisionResponse checkAndRespond(AbsEnemy enemy);
    CollisionResponse checkAndRespond(AbsWall wall);
}
