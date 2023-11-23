package cz.cvut.fit.niadp.mvcgame.abstractfactory;

import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import org.junit.Assert;
import org.junit.Test;

public class GameObjectsFactoryATest {

    @Test
    public void singletonTest() {
        GameModel model = new GameModel();
        IGameObjectsFactory instance1 = GameObjectsFactoryA.getInstance(model);
        IGameObjectsFactory instance2 = GameObjectsFactoryA.getInstance(model);
        Assert.assertEquals(instance1.hashCode(), instance2.hashCode());
    }
}
