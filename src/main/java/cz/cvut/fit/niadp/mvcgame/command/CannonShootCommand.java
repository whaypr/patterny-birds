package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class CannonShootCommand extends UndoableGameCommand {
    public CannonShootCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void innerExecute() {
        this.subject.cannonShoot();
    }
}
