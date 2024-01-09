package TestSuites;

import cz.cvut.fit.niadp.mvcgame.model.GameModelCannonTest;
import cz.cvut.fit.niadp.mvcgame.model.GameModelCommandsTest;
import cz.cvut.fit.niadp.mvcgame.model.GameModelMissilesTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GameModelCommandsTest.class,
        GameModelCannonTest.class,
        GameModelMissilesTest.class
})
public class GameModelTestSuite { }
