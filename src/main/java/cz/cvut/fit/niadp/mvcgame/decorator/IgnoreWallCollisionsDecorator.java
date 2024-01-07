package cz.cvut.fit.niadp.mvcgame.decorator;

import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionResponse;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;

public class IgnoreWallCollisionsDecorator extends CollisionDecorator {

    public IgnoreWallCollisionsDecorator(ICollisionChecker collisionChecker) {
        super(collisionChecker);
    }

    @Override
    public CollisionResponse checkAndRespond(AbsMissile missile) {
        return this.wrappee.checkAndRespond(missile);
    }

    @Override
    public CollisionResponse checkAndRespond(AbsEnemy enemy) {
        return this.wrappee.checkAndRespond(enemy);
    }

    @Override
    public CollisionResponse checkAndRespond(AbsWall wall) {
        CollisionResponse response = this.wrappee.checkAndRespond(wall);
        if (response == CollisionResponse.NO_COLLISION)
            return response;
        return CollisionResponse.IGNORE;
    }
}
