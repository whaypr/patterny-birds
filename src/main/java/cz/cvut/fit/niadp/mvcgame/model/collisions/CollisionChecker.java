package cz.cvut.fit.niadp.mvcgame.model.collisions;

import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;

public class CollisionChecker implements ICollisionChecker {

    private final GameObject reference;
    private final Vector hitBox;

    private final CollisionResponse missileCollisionResponse;
    private final CollisionResponse enemyCollisionResponse;
    private final CollisionResponse wallCollisionResponse;

    private Boolean collided(ICollisionChecker other) {
        int X = this.reference.getPosition().getX();
        int Y = this.reference.getPosition().getY();
        int otherX = other.getReference().getPosition().getX();
        int otherY = other.getReference().getPosition().getY();

        int W = this.hitBox.getDX();
        int H = this.hitBox.getDY();
        int otherW = other.getHitBox().getDX();
        int otherH = other.getHitBox().getDY();

        // AABB - collision occurs
        if (X + W >= otherX && otherX + otherW >= X && Y + H >= otherY && otherY + otherH >= Y)
            return true;

        return false;
    }

    public CollisionChecker(
            GameObject gameObject, Vector hitBox,
            CollisionResponse missileCollisionResponse,
            CollisionResponse enemyCollisionResponse,
            CollisionResponse wallCollisionResponse
    ) {
        this.reference = gameObject;
        this.hitBox = hitBox;

        this.missileCollisionResponse = missileCollisionResponse;
        this.enemyCollisionResponse = enemyCollisionResponse;
        this.wallCollisionResponse = wallCollisionResponse;
    }

    @Override
    public GameObject getReference() {
        return this.reference;
    }

    @Override
    public Vector getHitBox() {
        return this.hitBox;
    }

    @Override
    public CollisionResponse checkAndRespond(AbsMissile missile) {
        if (!this.collided(missile.getCollisionChecker()))
            return CollisionResponse.NO_COLLISION;
        return this.missileCollisionResponse;
    }

    @Override
    public CollisionResponse checkAndRespond(AbsEnemy enemy) {
        if (!this.collided(enemy.getCollisionChecker()))
            return CollisionResponse.NO_COLLISION;
        return this.enemyCollisionResponse;
    }

    @Override
    public CollisionResponse checkAndRespond(AbsWall wall) {
        if (!this.collided(wall.getCollisionChecker()))
            return CollisionResponse.NO_COLLISION;
        return this.wallCollisionResponse;
    }
}
