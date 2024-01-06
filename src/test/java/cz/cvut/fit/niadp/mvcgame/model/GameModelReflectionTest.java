package cz.cvut.fit.niadp.mvcgame.model;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.IntStream;

public class GameModelReflectionTest {

    private static final String MOVE_MISSILES_METHOD_NAME = "moveMissiles";
    private static final int ITERATION_START_CONST = 1;
    private static final int MISSILE_COUNT = 50;
    private static final int MOVE_COUNT = 100000;
    private static final int EXPECTED_MISSILES_COUNT = 0;

    @Test
    public void moveMissilesTest() throws NoSuchMethodException {
        GameModel model = new GameModel();
        Method method = model.getClass().getDeclaredMethod(MOVE_MISSILES_METHOD_NAME);
        method.setAccessible(true);
        IntStream.rangeClosed(ITERATION_START_CONST, MISSILE_COUNT).forEach(i->model.cannonShoot());
        Assert.assertEquals(MISSILE_COUNT, model.getMissiles().size());
        IntStream.rangeClosed(ITERATION_START_CONST, MOVE_COUNT).forEach(i-> {
            try {
                method.invoke(model);
            } catch (IllegalAccessException | InvocationTargetException ignored) {}
        });
        method.setAccessible(false);
        Assert.assertEquals(EXPECTED_MISSILES_COUNT, model.getMissiles().size());
    }
}
