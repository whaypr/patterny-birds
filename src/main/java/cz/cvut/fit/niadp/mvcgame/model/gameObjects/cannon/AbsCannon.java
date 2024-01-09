package cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.iterator.IIterator;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionResponse;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollidable;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.state.DynamicShootingMode;
import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

import java.util.List;

public abstract class AbsCannon extends GameObject implements ICollidable {

    private final ICollisionChecker collisionChecker;

    protected IIterator<IShootingMode> shootingModeIterator;
    protected IShootingMode shootingMode;
    protected DynamicShootingMode DYNAMIC_SHOOTING_MODE;

    protected int power;
    protected double angle;

    protected AbsCannon() {
        this.collisionChecker = new CollisionChecker(
                this, MvcGameConfig.CANNON_HITBOX,
                CollisionResponse.IGNORE, CollisionResponse.IGNORE, CollisionResponse.STOP
        );
    }

    public int getPower() {
        return power;
    }

    public double getAngle() {
        return -angle * 180 / Math.PI;
    }

    public IShootingMode getShootingMode() {
        return shootingMode;
    }

    public abstract void moveUp();

    public abstract void moveDown();
    public abstract void aimUp();
    public abstract void aimDown();
    public abstract void powerUp();
    public abstract void powerDown();

    public abstract void primitiveShoot();
    public abstract List<AbsMissile> shoot();

    @Override
    public void acceptVisitor(IGameObjectsVisitor visitor) {
        visitor.visitCannon(this);
    }

    @Override
    public ICollisionChecker getCollisionChecker() {
        return this.collisionChecker;
    }

    public abstract void toggleShootingMode();
    public abstract void addMissilesForDynamicShootingMode(int toAdd);
    public abstract void removeMissilesForDynamicShootingMode(int toRemove);
    public abstract int getDynamicShootingModeNumberOfMissiles();
}
