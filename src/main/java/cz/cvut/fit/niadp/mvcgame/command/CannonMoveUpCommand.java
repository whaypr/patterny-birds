package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class CannonMoveUpCommand extends AbstractGameCommand {

    public CannonMoveUpCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void innerExecute() {
        this.subject.moveCannonUp();
    }
}
