package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.command.CannonMoveUpCommand;
import cz.cvut.fit.niadp.mvcgame.command.CannonShootCommand;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import org.junit.Assert;
import org.junit.Test;

public class GameModelCommandsTest {

    @Test
    public void undoLastCommandTest() {
        IGameModel model = new GameModel();

        model.registerCommand(new CannonMoveUpCommand(model));
        model.registerCommand(new CannonShootCommand(model));
        model.registerCommand(new CannonMoveUpCommand(model));

        int positionBeforeUndoY = model.getCannonPosition().getY();
        model.update();
        Assert.assertEquals(positionBeforeUndoY - 2*MvcGameConfig.MOVE_STEP, model.getCannonPosition().getY());
        Assert.assertEquals(1, model.getNumberOfMissilesShot());

        model.undoLastCommand();
        Assert.assertEquals(positionBeforeUndoY - MvcGameConfig.MOVE_STEP, model.getCannonPosition().getY());
        Assert.assertEquals(0, model.getNumberOfMissilesShot());
    }
}
