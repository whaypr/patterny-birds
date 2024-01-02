package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public abstract class AbstractGameCommand {

    protected IGameModel subject;
    private Object memento;

    public AbstractGameCommand(IGameModel model) {
        this.subject = model;
    }

    protected abstract void execute();

    public void doExecute() {
        this.memento = this.subject.createMemento();
        this.execute();
    }

    public void unExecute() {
        this.subject.setMemento(this.memento);
    }
}
