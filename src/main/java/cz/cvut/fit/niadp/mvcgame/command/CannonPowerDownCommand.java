package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class CannonPowerDownCommand extends AbstractGameCommand {
    public CannonPowerDownCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void innerExecute() {
        this.subject.cannonPowerDown();
    }
}
