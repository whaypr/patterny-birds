package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class ToggleMissilesEnemyPiercingCommand extends AbstractGameCommand {

    public ToggleMissilesEnemyPiercingCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void innerExecute() {
        this.subject.toggleMissilesEnemyPiercing();
    }
}
