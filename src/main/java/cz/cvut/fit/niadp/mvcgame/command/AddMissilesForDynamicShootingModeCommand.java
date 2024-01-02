package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class AddMissilesForDynamicShootingModeCommand extends AbstractGameCommand {
    private int toAdd;

    public AddMissilesForDynamicShootingModeCommand(IGameModel model, int toAdd) {
        super(model);
        this.toAdd = toAdd;

    }

    @Override
    protected void execute() {
        this.subject.addMissilesForDynamicShootingMode(toAdd);
    }
}
