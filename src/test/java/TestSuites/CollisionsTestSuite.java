package TestSuites;

import cz.cvut.fit.niadp.mvcgame.decorator.IgnoreWallCollisionsDecoratorTest;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionCheckerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CollisionCheckerTest.class,
        IgnoreWallCollisionsDecoratorTest.class
})
public class CollisionsTestSuite { }
