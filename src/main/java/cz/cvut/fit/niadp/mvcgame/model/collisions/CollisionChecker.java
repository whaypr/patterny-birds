package cz.cvut.fit.niadp.mvcgame.model.collisions;

import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;

public class CollisionChecker {

    private final GameObject reference;
    private final Vector hitBox;

    public CollisionChecker(GameObject gameObject, Vector hitBox) {
        this.reference = gameObject;
        this.hitBox = hitBox;
    }

    public Boolean collided(CollisionChecker other) {
        int X = this.reference.getPosition().getX();
        int Y = this.reference.getPosition().getY();
        int otherX = other.reference.getPosition().getX();
        int otherY = other.reference.getPosition().getY();

        int W = this.hitBox.getDX();
        int H = this.hitBox.getDY();
        int otherW = other.hitBox.getDX();
        int otherH = other.hitBox.getDY();

        // AABB - collision occurs
        if (X + W >= otherX && otherX + otherW >= X && Y + H >= otherY && otherY + otherH >= Y)
            return true;

        return false;
    }
}
