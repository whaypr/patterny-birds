package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class CannonPowerUpCommand extends AbstractGameCommand {
    public CannonPowerUpCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void innerExecute() {
        this.subject.cannonPowerUp();
    }
}
