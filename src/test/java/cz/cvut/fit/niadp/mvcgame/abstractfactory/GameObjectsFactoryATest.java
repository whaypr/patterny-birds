package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.strategy.IMovingStrategy;
import cz.cvut.fit.niadp.mvcgame.strategy.SimpleMovingStrategy;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;

public class GameObjectsFactoryATest {

    private static final int CANNON_TEST_POSITION_X = 555;
    private static final int CANNON_TEST_POSITION_Y = 666;
    private static final double INIT_TEST_ANGLE = 0;
    private static final int INIT_TEST_VELOCITY = 0;
    private static final int INIT_TEST_MISSILE_LIFETIME = 0;

    private void generalMockSetup() {
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

    @Mocked
    private GameModel model;


    @Test
    public void singletonTest() {
        GameObjectsFactoryA.createInstance(new GameModel());
        IGameObjectsFactory instance1 = GameObjectsFactoryA.getInstance();
        IGameObjectsFactory instance2 = GameObjectsFactoryA.getInstance();
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    
    @Test
    public void createMissile() {
        this.generalMockSetup();

        GameObjectsFactoryA.createInstance(this.model);
        IGameObjectsFactory gameObjectsFactory = GameObjectsFactoryA.getInstance();

        AbsMissile missile = gameObjectsFactory.createMissile(INIT_TEST_ANGLE, INIT_TEST_VELOCITY, INIT_TEST_MISSILE_LIFETIME);

        Assert.assertEquals(CANNON_TEST_POSITION_X, missile.getPosition().getX());
        Assert.assertEquals(CANNON_TEST_POSITION_Y, missile.getPosition().getY());
    }
}
