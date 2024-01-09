package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class ToggleMissilesWallPiercingCommand extends AbstractGameCommand {

    public ToggleMissilesWallPiercingCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void innerExecute() {
        this.subject.toggleMissilesWallPiercing();
    }
}
