package cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon;

import cz.cvut.fit.niadp.mvcgame.abstractfactory.IGameObjectsFactory;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.iterator.IIterator;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.state.DynamicShootingMode;
import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;

import java.util.ArrayList;
import java.util.List;

public class CannonA extends AbsCannon {

    private final IGameObjectsFactory gameObjectsFactory;
    private final List<AbsMissile> shootingBatch;

    public CannonA(Position initPosition,
                   IIterator<IShootingMode> shootingModeIterator, DynamicShootingMode dynamicShootingMode,
                   IGameObjectsFactory gameObjectsFactory) {
        super();

        this.position = initPosition;
        this.gameObjectsFactory = gameObjectsFactory;

        this.power = MvcGameConfig.INIT_POWER;
        this.angle = MvcGameConfig.INIT_ANGLE;

        this.shootingBatch = new ArrayList<>();

        this.DYNAMIC_SHOOTING_MODE = dynamicShootingMode;
        this.shootingModeIterator = shootingModeIterator;
        this.shootingMode = this.shootingModeIterator.next();
    }

    @Override
    public void moveUp() {
        this.move(new Vector(0, -MvcGameConfig.MOVE_STEP));
    }

    @Override
    public void moveDown() {
        this.move(new Vector(0, MvcGameConfig.MOVE_STEP));
    }

    @Override
    public void aimUp() {
        this.angle = (this.angle - MvcGameConfig.ANGLE_STEP) % (2 * Math.PI);
    }

    @Override
    public void aimDown() {
        this.angle = (this.angle + MvcGameConfig.ANGLE_STEP - 2 * Math.PI) % (2 * Math.PI);
    }

    @Override
    public void powerUp() {
        this.power = Math.min(MvcGameConfig.MAX_POWER, this.power + MvcGameConfig.POWER_STEP);
    }

    @Override
    public void powerDown() {
        this.power = Math.max(MvcGameConfig.MIN_POWER, this.power - MvcGameConfig.POWER_STEP);
    }

    @Override
    public void primitiveShoot() {
        this.shootingBatch.add(this.gameObjectsFactory.createMissile(
                this.angle,
                this.power,
                MvcGameConfig.DEFAULT_MISSILE_LIFETIME
        ));
    }

    @Override
    public List<AbsMissile> shoot() {
        this.shootingBatch.clear();
        this.shootingMode.shoot(this);
        return this.shootingBatch;
    }

    @Override
    public void toggleShootingMode() {
        this.shootingMode = shootingModeIterator.next();
    }

    @Override
    public void addMissilesForDynamicShootingMode(int toAdd) {
        DYNAMIC_SHOOTING_MODE.addMissiles(toAdd);
    }

    @Override
    public void removeMissilesForDynamicShootingMode(int toRemove) {
        DYNAMIC_SHOOTING_MODE.removeMissiles(toRemove);
    }

    @Override
    public int getDynamicShootingModeNumberOfMissiles() {
        return DYNAMIC_SHOOTING_MODE.getNumberOfMissiles();
    }
}
