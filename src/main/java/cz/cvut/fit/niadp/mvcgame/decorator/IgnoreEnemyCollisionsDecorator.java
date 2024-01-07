package cz.cvut.fit.niadp.mvcgame.decorator;

import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionResponse;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;

public class IgnoreEnemyCollisionsDecorator extends CollisionDecorator {

    public IgnoreEnemyCollisionsDecorator(ICollisionChecker collisionChecker) {
        super(collisionChecker);
    }

    @Override
    public CollisionResponse checkAndRespond(AbsMissile missile) {
        return this.wrappee.checkAndRespond(missile);
    }

    @Override
    public CollisionResponse checkAndRespond(AbsEnemy enemy) {
        CollisionResponse response = this.wrappee.checkAndRespond(enemy);
        if (response == CollisionResponse.NO_COLLISION)
            return response;
        return CollisionResponse.IGNORE;
    }

    @Override
    public CollisionResponse checkAndRespond(AbsWall wall) {
        return this.wrappee.checkAndRespond(wall);
    }
}
