package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.EnemyType;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.gameInfo.AbsGameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;
import cz.cvut.fit.niadp.mvcgame.observer.SoundMaker;
import cz.cvut.fit.niadp.mvcgame.abstractfactory.GameObjectsFactoryA;
import cz.cvut.fit.niadp.mvcgame.abstractfactory.IGameObjectsFactory;
import cz.cvut.fit.niadp.mvcgame.command.AbstractGameCommand;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.*;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;

import java.util.*;

import cz.cvut.fit.niadp.mvcgame.state.IShootingMode;
import cz.cvut.fit.niadp.mvcgame.strategy.*;

import java.util.concurrent.LinkedBlockingQueue;

public class GameModel implements IGameModel {

    private final Map<Aspect, Set<IObserver>> observers;
    private final IGameObjectsFactory gameObjectsFactory;

    private final AbsCannon cannon;
    private final List<AbsMissile> missiles;
    private final List<AbsEnemy> enemies;
    private final List<AbsWall> walls;
    private IMovingStrategy movingStrategy;

    private int score;
    private int numberOfMissilesShot;

    private SoundMaker soundMaker;
    private AbsGameInfo gameInfo;

    private final Queue<AbstractGameCommand> unexecutedCommands;
    private final Stack<AbstractGameCommand> executedCommands;

    public GameModel() {
        GameObjectsFactoryA.createInstance(this);
        this.gameObjectsFactory = GameObjectsFactoryA.getInstance();
        this.gameInfo = this.gameObjectsFactory.createGameInfo();
        this.cannon = this.gameObjectsFactory.createCannon();
        this.missiles = new ArrayList<>();
        this.enemies = createEnemies();
        this.walls = createWalls();

        this.observers = new HashMap<>();

        this.unexecutedCommands = new LinkedBlockingQueue<>();
        this.executedCommands = new Stack<>();

        this.movingStrategy = new SimpleMovingStrategy();

        this.soundMaker = new SoundMaker();
        this.registerObserver(soundMaker, Aspect.CANNON_MOVE);
        this.registerObserver(soundMaker, Aspect.MISSILE_SPAWN);

        this.score = 0;
        this.numberOfMissilesShot = 0;
    }

    @Override
    public void update() {
        this.checkLifeTimeLimitedGameObjects();
        this.executeCommands();
        this.moveMissiles();
    }


    /* COMMANDS */

    private void executeCommands() {
        while (!this.unexecutedCommands.isEmpty()) {
            AbstractGameCommand command = this.unexecutedCommands.poll();
            command.doExecute();
            executedCommands.add(command);
        }
    }

    @Override
    public void registerCommand(AbstractGameCommand command) {
        this.unexecutedCommands.add(command);
    }

    @Override
    public void undoLastCommand() {
        if (this.executedCommands.empty())
            return;
        this.executedCommands.pop().unExecute();
        // TODO add some way to notify only the correct aspect
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        this.notifyObservers(Aspect.STATUS);
    }

    @Override
    public void moveCannonUp() {
        this.cannon.moveUp();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        this.notifyObservers(Aspect.CANNON_MOVE);
    }

    @Override
    public void moveCannonDown() {
        this.cannon.moveDown();
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        this.notifyObservers(Aspect.CANNON_MOVE);
    }

    @Override
    public void aimCannonUp() {
        this.cannon.aimUp();
    }

    @Override
    public void aimCannonDown() {
        this.cannon.aimDown();
    }

    @Override
    public void cannonPowerUp() {
        this.cannon.powerUp();
        this.notifyObservers(Aspect.STATUS);
    }

    @Override
    public void cannonPowerDown() {
        this.cannon.powerDown();
        this.notifyObservers(Aspect.STATUS);
    }

    @Override
    public void cannonShoot() {
        List<AbsMissile> newMissiles = this.cannon.shoot();

        this.missiles.addAll(newMissiles);
        this.numberOfMissilesShot += newMissiles.size();

        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        this.notifyObservers(Aspect.MISSILE_SPAWN);
    }

    @Override
    public void toggleShootingMode() {
        this.cannon.toggleShootingMode();
        this.notifyObservers(Aspect.STATUS);
    }

    @Override
    public void toggleMovingStrategy() {
        if (this.movingStrategy instanceof SimpleMovingStrategy) {
            this.movingStrategy = new RealisticMovingStrategy();
        }
        else if (this.movingStrategy instanceof RealisticMovingStrategy) {
            this.movingStrategy = new SinusoidalMovingStrategy();
        }
        else if (this.movingStrategy instanceof SinusoidalMovingStrategy) {
            this.movingStrategy = new CircularMovingStrategy();
        }
        else if (this.movingStrategy instanceof CircularMovingStrategy) {
            this.movingStrategy = new SimpleMovingStrategy();
        }
    }

    @Override
    public void addMissilesForDynamicShootingMode(int toAdd) {
        cannon.addMissilesForDynamicShootingMode(toAdd);
    }

    @Override
    public void removeMissilesForDynamicShootingMode(int toRemove) {
        cannon.removeMissilesForDynamicShootingMode(toRemove);
    }


    /* OBSERVER */

    @Override
    public void registerObserver(IObserver observer, Aspect aspect) {
        if (!this.observers.containsKey(aspect))
            this.observers.put(aspect, new HashSet<>());

        this.observers.get(aspect).add(observer);
    }

    @Override
    public void unregisterObserver(IObserver observer, Aspect aspect) {
        this.observers.get(aspect).remove(observer);
    }

    @Override
    public void notifyObservers(Aspect aspect) {
        this.observers.get(aspect).forEach(o -> o.update(aspect));
    }


    /* HELPERS, MODEL INFO */

    private List<AbsEnemy> createEnemies() {
        List<AbsEnemy> enemies = new ArrayList<>();
        enemies.add(this.gameObjectsFactory.createEnemy(
                new Position(300, 500),
                EnemyType.BASIC
        ));
        enemies.add(this.gameObjectsFactory.createEnemy(
                new Position(500, 600),
                EnemyType.WITH_HELMET
        ));
        enemies.add(this.gameObjectsFactory.createEnemy(
                new Position(700, 300),
                EnemyType.WITH_HELMET
        ));
        enemies.add(this.gameObjectsFactory.createEnemy(
                new Position(900, 250),
                EnemyType.WITH_HELMET
        ));
        enemies.add(this.gameObjectsFactory.createEnemy(
                new Position(1050, 400),
                EnemyType.WITH_HELMET
        ));
        return enemies;
    }

    private List<AbsWall> createWalls() {
        List<AbsWall> walls = new ArrayList<>();
        walls.add(this.gameObjectsFactory.createWall(
                new Position(200, 100)
        ));
        walls.add(this.gameObjectsFactory.createWall(
                new Position(250, 500)
        ));
        walls.add(this.gameObjectsFactory.createWall(
                new Position(800, 132)
        ));
        walls.add(this.gameObjectsFactory.createWall(
                new Position(800, 164)
        ));
        walls.add(this.gameObjectsFactory.createWall(
                new Position(800, 196)
        ));
        walls.add(this.gameObjectsFactory.createWall(
                new Position(800, 228)
        ));
        walls.add(this.gameObjectsFactory.createWall(
                new Position(800, 260)
        ));
        return walls;
    }

    private void moveMissiles() {
        this.missiles.forEach(AbsMissile::move);
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
    }

    private void checkLifeTimeLimitedGameObjects() {
        List<AbsMissile> toDestroy = this.missiles.stream().filter(LifetimeLimitedGameObject::shouldBeDestroyed).toList();
        this.missiles.removeAll(toDestroy);
        toDestroy.forEach(m -> m = null);
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getNumberOfMissilesShot() {
        return numberOfMissilesShot;
    }

    @Override
    public int getNumberOfEnemiesLeft() {
        return enemies.size();
    }

    @Override
    public List<GameObject> getGameObjects() {
        List<GameObject> objects = new ArrayList<>();
        objects.add(this.cannon);
        objects.add(this.gameInfo);
        objects.addAll(this.missiles);
        objects.addAll(this.enemies);
        objects.addAll(this.walls);
        return objects;
    }

    @Override
    public Position getCannonPosition() {
        return this.cannon.getPosition();
    }

    @Override
    public double getCannonAngle() {
        return this.cannon.getAngle();
    }

    @Override
    public int getCannonPower() {
        return this.cannon.getPower();
    }

    @Override
    public IShootingMode getCannonShootingMode() {
        return this.cannon.getShootingMode();
    }

    @Override
    public int getCannonDynamicShootingModeNumberOfMissiles() {
        return this.cannon.getDynamicShootingModeNumberOfMissiles();
    }

    @Override
    public List<AbsMissile> getMissiles() {
        return this.missiles;
    }

    @Override
    public IMovingStrategy getMissileMovingStrategy() {
        return this.movingStrategy;
    }


    /* MEMENTO */

    private static class Memento {
        private int cannonPositionX;
        private int cannonPositionY;
        // game model snapshot (enemies, gameInfo, strategy, state)
    }

    @Override
    public Object createMemento() {
        Memento gameModelSnapshot = new Memento();
        gameModelSnapshot.cannonPositionX = this.getCannonPosition().getX();
        gameModelSnapshot.cannonPositionY = this.getCannonPosition().getY();
        return gameModelSnapshot;
    }

    @Override
    public void setMemento(Object memento) {
        Memento gameModelSnapshot = (Memento) memento;
        this.cannon.getPosition().setX(gameModelSnapshot.cannonPositionX);
        this.cannon.getPosition().setY(gameModelSnapshot.cannonPositionY);
    }
}
