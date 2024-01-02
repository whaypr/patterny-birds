package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class CannonAimUpCommand extends AbstractGameCommand {
    public CannonAimUpCommand(IGameModel model) {
        super(model);
    }

    @Override
    protected void execute() {
        this.subject.aimCannonUp();
    }
}
