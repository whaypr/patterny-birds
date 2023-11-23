package cz.cvut.fit.niadp.mvcgame.model.gameObjects;

import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public abstract class AbsCannon extends GameObject {

    // private int power
    // private double angle

    public abstract void moveUp();

    public abstract void moveDown();

    public abstract AbsMissile shoot();

    @Override
    public void acceptVisitor(IGameObjectsVisitor visitor) {
        visitor.visitCannon(this);
    }
}
