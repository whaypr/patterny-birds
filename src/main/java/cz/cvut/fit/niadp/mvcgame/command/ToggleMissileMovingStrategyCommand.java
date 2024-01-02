package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class ToggleMissileMovingStrategyCommand extends AbstractGameCommand {
    public ToggleMissileMovingStrategyCommand(IGameModel model) {
        super(model);
    }

    @Override
    protected void execute() {
        this.subject.toggleMovingStrategy();
    }
}
