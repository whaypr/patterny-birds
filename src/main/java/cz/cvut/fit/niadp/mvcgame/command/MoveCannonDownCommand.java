package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public class MoveCannonDownCommand extends AbstractGameCommand {

    public MoveCannonDownCommand(IGameModel model) {
        this.subject = model;
    }


    @Override
    protected void execute() {
        this.subject.moveCannonDown();
    }
}
