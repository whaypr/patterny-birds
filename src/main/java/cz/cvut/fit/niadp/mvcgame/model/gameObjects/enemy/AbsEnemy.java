package cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy;

import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public abstract class AbsEnemy extends GameObject {

    protected EnemyType type;

    public EnemyType getType() {
        return this.type;
    }

    @Override
    public void acceptVisitor(IGameObjectsVisitor visitor) {
        visitor.visitEnemy(this);
    }
}
