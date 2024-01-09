package cz.cvut.fit.niadp.mvcgame.model.collisions;

import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.Vector;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;
import org.junit.Assert;
import org.junit.Test;


public class CollisionCheckerTest {

    private final class DummyMissile extends AbsMissile {
        public DummyMissile(Position initPosition, double initAngle, int initVelocity, long lifeTime) {
            super(initPosition, initAngle, initVelocity, lifeTime);
        }
        @Override
        public void move() {}
        @Override
        public AbsMissile clone() { return null; }
    }
    private final class DummyEnemy extends AbsEnemy {
        public DummyEnemy(Position position) { this.position = position; }
        @Override
        public AbsEnemy clone() { return null; }
    }
    private final class DummyWall extends AbsWall {
        public DummyWall(Position position) { this.position = position; }
    }


    private static CollisionResponse MM = CollisionResponse.IGNORE;
    private static CollisionResponse ME = CollisionResponse.STOP;
    private static CollisionResponse MW = CollisionResponse.DESTROY;
    private static CollisionResponse EM = CollisionResponse.DESTROY;
    private static CollisionResponse EE = CollisionResponse.DESTROY;
    private static CollisionResponse EW = CollisionResponse.STOP;
    private static CollisionResponse WM = CollisionResponse.STOP;
    private static CollisionResponse WE = CollisionResponse.IGNORE;
    private static CollisionResponse WW = CollisionResponse.IGNORE;



    @Test
    public void collisions() {
        // prepare data
        DummyMissile missile = new DummyMissile(new Position(0, 0), 0, 0, 0);
        CollisionChecker ccMissile = new CollisionChecker(
                missile, new Vector(15,10), MM, ME, MW
        );

        DummyEnemy enemy = new DummyEnemy(new Position(10,5));
        CollisionChecker ccEnemy = new CollisionChecker(
                enemy, new Vector(10,15), EM, EE, EW
        );

        DummyWall wall = new DummyWall(new Position(40,40));
        CollisionChecker ccWall = new CollisionChecker(
                wall, new Vector(5,5), WM, WE, WW
        );

        // test
        Assert.assertEquals(MM, ccMissile.checkAndRespond(missile));
        Assert.assertEquals(ME, ccMissile.checkAndRespond(enemy));
        Assert.assertEquals(CollisionResponse.NO_COLLISION, ccMissile.checkAndRespond(wall));

        Assert.assertEquals(EM, ccEnemy.checkAndRespond(missile));
        Assert.assertEquals(EE, ccEnemy.checkAndRespond(enemy));
        Assert.assertEquals(CollisionResponse.NO_COLLISION, ccEnemy.checkAndRespond(wall));

        Assert.assertEquals(CollisionResponse.NO_COLLISION, ccWall.checkAndRespond(missile));
        Assert.assertEquals(CollisionResponse.NO_COLLISION, ccWall.checkAndRespond(enemy));
        Assert.assertEquals(WW, ccWall.checkAndRespond(wall));
    }
}
