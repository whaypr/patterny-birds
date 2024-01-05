package cz.cvut.fit.niadp.mvcgame.view;

import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.visitor.GameObjectsSoundMaker;
import org.junit.Test;

public class GameViewTest {

    @Test
    public void renderNullObjectTest(){
        GameView view = new GameView(new GameModel());
        view.update(Aspect.OBJECT_POSITIONS);
    }
}
