package cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy;

import cz.cvut.fit.niadp.mvcgame.model.Position;

public class EnemyA extends AbsEnemy {

    public EnemyA(Position position, EnemyType type) {
        super();
        this.position = position;
        this.type = type;
    }
}
