package cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy;

import cz.cvut.fit.niadp.mvcgame.model.Position;

public class EnemyA extends AbsEnemy {

    public EnemyA(Position position, EnemyType type) {
        super();
        this.position = position;
        this.type = type;
    }

    public EnemyA(EnemyA other) {
        super(other);
        this.position = other.position;
        this.type = other.type;
    }

    @Override
    public AbsEnemy clone() {
        return new EnemyA(this);
    }
}
