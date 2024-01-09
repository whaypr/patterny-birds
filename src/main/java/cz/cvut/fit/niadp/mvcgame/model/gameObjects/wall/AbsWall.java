package cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionResponse;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollidable;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public abstract class AbsWall extends GameObject implements ICollidable {

    private final ICollisionChecker collisionChecker;

    protected AbsWall() {
        this.collisionChecker = new CollisionChecker(
                this, MvcGameConfig.WALL_HITBOX,
                CollisionResponse.IGNORE, CollisionResponse.IGNORE, CollisionResponse.IGNORE
        );
    }

    @Override
    public void acceptVisitor(IGameObjectsVisitor visitor) {
        visitor.visitWall(this);
    }

    @Override
    public ICollisionChecker getCollisionChecker() {
        return this.collisionChecker;
    }
}
