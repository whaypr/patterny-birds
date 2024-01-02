package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class MoveCannonUpCommand extends AbstractGameCommand {

    public MoveCannonUpCommand(IGameModel model) {
        this.subject = model;
    }


    @Override
    protected void execute() {
        this.subject.moveCannonUp();
    }
}
