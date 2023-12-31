package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.abstractfactory.GameObjectsFactoryA;
import cz.cvut.fit.niadp.mvcgame.abstractfactory.IGameObjectsFactory;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;
import cz.cvut.fit.niadp.mvcgame.strategy.SimpleMovingStrategy;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;

public class GameModelMockedTest {

    private static final int CANNON_TEST_POSITION_X = 555;
    private static final int CANNON_TEST_POSITION_Y = 666;
    private static final double INIT_TEST_ANGLE = 0;
    private static final int INIT_TEST_VELOCITY = 0;

    @Mocked
    private GameModel model;

    @Test
    public void createMissile() {
        this.generalMockSetup();
        IGameObjectsFactory gameObjectsFactory = new GameObjectsFactoryA(this.model);
        AbsMissile missile = gameObjectsFactory.createMissile(INIT_TEST_ANGLE, INIT_TEST_VELOCITY);
        Assert.assertEquals(CANNON_TEST_POSITION_X, missile.getPosition().getX());
        Assert.assertEquals(CANNON_TEST_POSITION_Y, missile.getPosition().getY());
    }


    public void generalMockSetup() {
        new MockUp<GameModel>() {
            @Mock
            public Position getCannonPosition() {
                return new Position(CANNON_TEST_POSITION_X, CANNON_TEST_POSITION_Y);
            }

            @Mock
            public IMovingStrategy getMovingStrategy() {
                return new SimpleMovingStrategy();
            }
        };
    }
}
