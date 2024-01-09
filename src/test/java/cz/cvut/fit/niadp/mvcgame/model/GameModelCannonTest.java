package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.abstractfactory.GameObjectsFactoryA;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.iterator.CircularIterator;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon.CannonA;
import cz.cvut.fit.niadp.mvcgame.state.DoubleShootingMode;
import cz.cvut.fit.niadp.mvcgame.state.DynamicShootingMode;
import cz.cvut.fit.niadp.mvcgame.state.SingleShootingMode;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;
import cz.cvut.fit.niadp.mvcgame.strategy.SimpleMovingStrategy;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class GameModelCannonTest {

    @Test
    public void cannonMovement() {
        IGameModel model = new GameModel();

        Position startingPosition = model.getCannonPosition().clone();
        model.moveCannonUp();
        model.moveCannonUp();
        Assert.assertEquals(
                (-2) * MvcGameConfig.MOVE_STEP + startingPosition.getY(),
                model.getCannonPosition().getY()
        );
        Assert.assertEquals(
                startingPosition.getX(),
                model.getCannonPosition().getX()
        );

        model.moveCannonDown();
        model.moveCannonDown();
        model.moveCannonDown();
        Assert.assertEquals(
                (1) * MvcGameConfig.MOVE_STEP + startingPosition.getY(),
                model.getCannonPosition().getY()
        );
        Assert.assertEquals(
                startingPosition.getX(),
                model.getCannonPosition().getX()
        );
    }


    @Test
    public void cannonAiming() {
        IGameModel model = new GameModel();

        double startingAngle = model.getCannonAngle();
        model.aimCannonUp();
        model.aimCannonUp();
        Assert.assertEquals(
                ((2) * MvcGameConfig.ANGLE_STEP * 180 / Math.PI) % 360 + startingAngle,
                model.getCannonAngle(),
                0.000001
        );

        model.aimCannonDown();
        model.aimCannonDown();
        model.aimCannonDown();
        Assert.assertEquals(
                ((-1) * MvcGameConfig.ANGLE_STEP * 180 / Math.PI + 360) % 360 + startingAngle,
                model.getCannonAngle(),
                0.000001
        );
    }


    @Test
    public void cannonPowerChanging() {
        IGameModel model = new GameModel();

        int startingPower = model.getCannonPower();
        model.cannonPowerUp();
        model.cannonPowerUp();
        Assert.assertEquals(
                (2) * MvcGameConfig.POWER_STEP + startingPower,
                model.getCannonPower()
        );

        model.cannonPowerDown();
        model.cannonPowerDown();
        model.cannonPowerDown();
        Assert.assertEquals(
                (-1) * MvcGameConfig.POWER_STEP + startingPower,
                model.getCannonPower()
        );
    }
}
