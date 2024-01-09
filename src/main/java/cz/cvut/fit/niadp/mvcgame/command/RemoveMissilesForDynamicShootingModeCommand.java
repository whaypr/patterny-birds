package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class RemoveMissilesForDynamicShootingModeCommand extends AbstractGameCommand {
    private int toRemove;

    public RemoveMissilesForDynamicShootingModeCommand(IGameModel model, int toRemove) {
        super(model);
        this.toRemove = toRemove;

    }

    @Override
    public void innerExecute() {
        this.subject.removeMissilesForDynamicShootingMode(toRemove);
    }
}
