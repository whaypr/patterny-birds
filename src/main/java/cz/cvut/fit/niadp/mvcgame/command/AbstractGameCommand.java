package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public abstract class AbstractGameCommand {

    protected IGameModel subject;

    protected abstract void innerExecute();

    public AbstractGameCommand(IGameModel model) {
        this.subject = model;
    }

    public void execute() {
        innerExecute();
    }

}
