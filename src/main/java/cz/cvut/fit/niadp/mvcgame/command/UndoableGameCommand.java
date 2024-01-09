package cz.cvut.fit.niadp.mvcgame.command;

import cz.cvut.fit.niadp.mvcgame.model.IGameModel;

public abstract class UndoableGameCommand extends AbstractGameCommand {

    private Object memento;

    public UndoableGameCommand(IGameModel model) {
        super(model);
    }

    @Override
    public void execute() {
        this.memento = this.subject.createMemento();
        innerExecute();
    }

    public void unExecute() {
        this.subject.setMemento(this.memento);
    }
}
