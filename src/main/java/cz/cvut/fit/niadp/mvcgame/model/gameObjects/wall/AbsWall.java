package cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.collisions.CollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.collisions.ICollidable;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public abstract class AbsWall extends GameObject implements ICollidable {

    private final CollisionChecker collisionChecker;

    protected AbsWall() {
        this.collisionChecker = new CollisionChecker(this, MvcGameConfig.WALL_HITBOX);
    }

    @Override
    public void acceptVisitor(IGameObjectsVisitor visitor) {
        visitor.visitWall(this);
    }

    @Override
    public CollisionChecker getCollisionChecker() {
        return this.collisionChecker;
    }
}
