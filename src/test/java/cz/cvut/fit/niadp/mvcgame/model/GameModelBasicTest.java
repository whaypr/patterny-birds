package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.command.MoveCannonUpCommand;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import org.junit.Assert;
import org.junit.Test;

public class GameModelBasicTest {

    @Test
    public void undoLastCommandTest() {
        IGameModel model = new GameModel();
        int positionBeforeUndoY = model.getCannonPosition().getY();
        model.registerCommand(new MoveCannonUpCommand(model));
        model.update();
        Assert.assertEquals(positionBeforeUndoY - MvcGameConfig.MOVE_STEP, model.getCannonPosition().getY());
        model.undoLastCommand();
        Assert.assertEquals(positionBeforeUndoY, model.getCannonPosition().getY());
    }
}
