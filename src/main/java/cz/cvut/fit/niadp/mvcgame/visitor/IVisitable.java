package cz.cvut.fit.niadp.mvcgame.visitor;

public interface IVisitable {

    void acceptVisitor(IGameObjectsVisitor visitor);
}
