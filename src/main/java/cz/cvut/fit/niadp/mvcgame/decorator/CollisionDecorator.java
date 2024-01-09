package cz.cvut.fit.niadp.mvcgame.decorator;

import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;

public abstract class CollisionDecorator implements ICollisionChecker {

    protected final ICollisionChecker wrappee;

    public CollisionDecorator(ICollisionChecker collisionChecker) {
        this.wrappee = collisionChecker;
    }

    @Override
    public GameObject getReference() {
        return this.wrappee.getReference();
    }

    @Override
    public Vector getHitBox() {
        return this.wrappee.getHitBox();
    }
}
