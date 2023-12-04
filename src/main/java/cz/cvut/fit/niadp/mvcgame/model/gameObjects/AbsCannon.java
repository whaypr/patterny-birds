package cz.cvut.fit.niadp.mvcgame.model.gameObjects;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.state.DoubleShootingMode;
import cz.cvut.fit.niadp.mvcgame.state.DynamicShootingMode;
import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.niadp.mvcgame.state.SingleShootingMode;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

import java.util.List;

public abstract class AbsCannon extends GameObject {

    protected IShootingMode shootingMode;
    protected static IShootingMode SINGLE_SHOOTING_MODE = new SingleShootingMode();
    protected static IShootingMode DOUBLE_SHOOTING_MODE = new DoubleShootingMode();
    protected static DynamicShootingMode DYNAMIC_SHOOTING_MODE = new DynamicShootingMode(MvcGameConfig.DYNAMIC_SHOOTING_MODE_DEFAULT_NUMBER_OF_MISSILES);

    protected int power;
    protected double angle;

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

    public abstract void toggleShootingMode();
    public abstract void addMissilesForDynamicShootingMode(int toAdd);
    public abstract void removeMissilesForDynamicShootingMode(int toRemove);
}
