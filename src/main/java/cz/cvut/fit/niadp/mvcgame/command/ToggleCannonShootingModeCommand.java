package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class ToggleCannonShootingModeCommand extends AbstractGameCommand {
    public ToggleCannonShootingModeCommand(IGameModel model) {
        super(model);
    }

    @Override
    protected void execute() {
        this.subject.toggleShootingMode();
    }
}
