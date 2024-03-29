package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class CannonMoveDownCommand extends AbstractGameCommand {

    public CannonMoveDownCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void innerExecute() {
        this.subject.moveCannonDown();
    }
}
