package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class CannonAimDownCommand extends AbstractGameCommand {
    public CannonAimDownCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void innerExecute() {
        this.subject.aimCannonDown();
    }
}
