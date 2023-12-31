package cz.cvut.fit.niadp.mvcgame.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({GameModelBasicTest.class, GameModelMockedTest.class, GameModelReflectionTest.class})
public class GameModelTestSuit { }
