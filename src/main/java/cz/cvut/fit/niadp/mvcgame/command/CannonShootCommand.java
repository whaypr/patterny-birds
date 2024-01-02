package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class CannonShootCommand extends AbstractGameCommand {
    public CannonShootCommand(IGameModel model) {
        super(model);
    }

    @Override
    protected void execute() {
        this.subject.cannonShoot();
    }
}
