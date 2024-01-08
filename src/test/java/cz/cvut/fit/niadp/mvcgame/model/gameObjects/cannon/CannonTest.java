package cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon;

import cz.cvut.fit.niadp.mvcgame.abstractfactory.GameObjectsFactoryA;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.iterator.CircularIterator;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.model.IGameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
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

public class CannonTest {

    private void setupGameObjectFactoryAMock(SingleShootingMode s, DoubleShootingMode d) {
        GameObjectsFactoryA.createInstance(new GameModel());

        new MockUp<GameObjectsFactoryA>() {
            @Mock
            public CannonA createCannon() {
                return new CannonA(
                        new Position(MvcGameConfig.CANNON_POS_X, MvcGameConfig.CANNON_POS_Y),
                        new CircularIterator<>(
                                Arrays.asList(d, s)
                        ),
                        null,
                        GameObjectsFactoryA.getInstance());
            }
        };
    }

    @Mocked
    private GameObjectsFactoryA factoryA;


    @Test
    public void shootingModeToggling() {
        SingleShootingMode single = new SingleShootingMode();
        DoubleShootingMode dbl = new DoubleShootingMode();

        setupGameObjectFactoryAMock(single, dbl);

        CannonA cannon = factoryA.createCannon();

        Assert.assertEquals(cannon.getShootingMode(), dbl);
        cannon.toggleShootingMode();
        Assert.assertEquals(cannon.getShootingMode(), single);
        cannon.toggleShootingMode();
        Assert.assertEquals(cannon.getShootingMode(), dbl);
    }
}
