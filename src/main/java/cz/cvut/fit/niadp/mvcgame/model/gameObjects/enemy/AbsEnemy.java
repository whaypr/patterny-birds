package cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionResponse;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.GameObject;
import cz.cvut.fit.niadp.mvcgame.model.collisions.CollisionChecker;
import cz.cvut.fit.niadp.mvcgame.model.collisions.ICollidable;
import cz.cvut.fit.niadp.mvcgame.visitor.IGameObjectsVisitor;

public abstract class AbsEnemy extends GameObject implements ICollidable {

    private final ICollisionChecker collisionChecker;

    protected EnemyType type;

    protected AbsEnemy() {
        this.collisionChecker = new CollisionChecker(
                this, MvcGameConfig.ENEMY_HITBOX,
                CollisionResponse.DESTROY, CollisionResponse.IGNORE, CollisionResponse.STOP
        );
    }

    protected AbsEnemy(AbsEnemy other) {
        this.collisionChecker = other.collisionChecker;
    }

    public EnemyType getType() {
        return this.type;
    }

    public void changeType(EnemyType type) {
        this.type = type;
    }

    @Override
    public void acceptVisitor(IGameObjectsVisitor visitor) {
        visitor.visitEnemy(this);
    }

    @Override
    public ICollisionChecker getCollisionChecker() {
        return this.collisionChecker;
    }

    public abstract AbsEnemy clone();
}
