package cz.cvut.fit.niadp.mvcgame.model;

import cz.cvut.fit.niadp.mvcgame.command.UndoableGameCommand;
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
import java.util.stream.Collectors;

public class GameModel implements IGameModel {

    private final Map<Aspect, Set<IObserver>> observers;
    private final IGameObjectsFactory gameObjectsFactory;

    private final AbsCannon cannon;
    private List<AbsMissile> missiles;
    private List<AbsEnemy> enemies;
    private final List<AbsWall> walls;
    private IMovingStrategy missilesMovingStrategy;

    private int score;
    private int numberOfMissilesShot;

    private SoundMaker soundMaker;
    private AbsGameInfo gameInfo;

    private final Queue<AbstractGameCommand> unexecutedCommands;
    private final Stack<UndoableGameCommand> undoableCommands;

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
        this.undoableCommands = new Stack<>();

        this.missilesMovingStrategy = new SimpleMovingStrategy();

        this.soundMaker = new SoundMaker();
        this.registerObserver(soundMaker, Aspect.MISSILE_SPAWN);
        this.registerObserver(soundMaker, Aspect.MISSILE_WALL_HIT);
        this.registerObserver(soundMaker, Aspect.MISSILE_ENEMY_HIT);

        this.score = 0;
        this.numberOfMissilesShot = 0;
    }

    @Override
    public void update() {
        this.executeCommands();
        this.moveMissiles();
        this.handleMissilesWithWallsCollisions();
        this.handleMissilesWithEnemiesCollisions();
        this.checkLifeTimeLimitedGameObjects();
    }


    /* COMMANDS */

    private void executeCommands() {
        while (!this.unexecutedCommands.isEmpty()) {
            AbstractGameCommand command = this.unexecutedCommands.poll();

            command.execute();

            if (command instanceof UndoableGameCommand) {
                this.undoableCommands.add((UndoableGameCommand) command);
            }
        }
    }

    @Override
    public void registerCommand(AbstractGameCommand command) {
        this.unexecutedCommands.add(command);
    }

    @Override
    public void undoLastCommand() {
        if (this.undoableCommands.empty())
            return;
        this.undoableCommands.pop().unExecute();
        // TODO add some way to notify only the correct aspect
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        this.notifyObservers(Aspect.STATUS);
    }

    @Override
    public void moveCannonUp() {
        this.cannon.moveUp();
        if (isCannonTouchingWall()) {
            this.cannon.moveDown();
            return;
        }
        this.notifyObservers(Aspect.OBJECT_POSITIONS);
        this.notifyObservers(Aspect.CANNON_MOVE);
    }

    @Override
    public void moveCannonDown() {
        this.cannon.moveDown();
        if (isCannonTouchingWall()) {
            this.cannon.moveUp();
            return;
        }
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
        if (this.missilesMovingStrategy instanceof SimpleMovingStrategy) {
            this.missilesMovingStrategy = new RealisticMovingStrategy();
        }
        else if (this.missilesMovingStrategy instanceof RealisticMovingStrategy) {
            this.missilesMovingStrategy = new SinusoidalMovingStrategy();
        }
        else if (this.missilesMovingStrategy instanceof SinusoidalMovingStrategy) {
            this.missilesMovingStrategy = new CircularMovingStrategy();
        }
        else if (this.missilesMovingStrategy instanceof CircularMovingStrategy) {
            this.missilesMovingStrategy = new SimpleMovingStrategy();
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

    private void handleMissilesWithWallsCollisions() {
        List<AbsMissile> missilesToRemove = new ArrayList<>();
        for (AbsMissile missile: this.missiles) {
            for (AbsWall wall: this.walls) {
                if (missile.getCollisionChecker().collided(wall.getCollisionChecker())) {
                    missilesToRemove.add(missile);
                }
            }
        }
        if (!missilesToRemove.isEmpty())
            this.notifyObservers(Aspect.MISSILE_WALL_HIT);
        this.missiles.removeAll(missilesToRemove);
    }

    private void handleMissilesWithEnemiesCollisions() {
        List<AbsEnemy> hitEnemies = new ArrayList<>();
        List<AbsMissile> missilesToRemove = new ArrayList<>();
        for (AbsMissile missile: this.missiles) {
            for (AbsEnemy enemy: this.enemies) {
                if (missile.getCollisionChecker().collided(enemy.getCollisionChecker())) {
                    missilesToRemove.add(missile);
                    hitEnemies.add(enemy);
                    score += MvcGameConfig.POINTS_FOR_HIT;
                }
            }
        }
        if (!missilesToRemove.isEmpty())
            this.notifyObservers(Aspect.MISSILE_ENEMY_HIT);
        this.missiles.removeAll(missilesToRemove);

        List<AbsEnemy> enemiesToRemove = new ArrayList<>();
        for (AbsEnemy enemy: hitEnemies) {
            switch (enemy.getType()) {
                case BASIC -> enemiesToRemove.add(enemy);
                case WITH_HELMET -> enemy.changeType(EnemyType.BASIC);
            }
        }
        this.enemies.removeAll(enemiesToRemove);
    }

    private boolean isCannonTouchingWall() {
        for (AbsWall wall: this.walls) {
            if (this.cannon.getCollisionChecker().collided(wall.getCollisionChecker())) {
                return true;
            }
        }
        return false;
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
        return this.missilesMovingStrategy;
    }


    /* MEMENTO */

    private static class Memento {
        private Position cannonPosition;
        private List<AbsMissile> missiles;;
        private List<AbsEnemy> enemies;;
        private int score;
        private int numberOfMissilesShot;
    }

    @Override
    public Object createMemento() {
        Memento memento = new Memento();
        memento.cannonPosition = new Position(this.cannon.getPosition().getX(), this.cannon.getPosition().getY());
        memento.missiles = this.missiles.stream().map(m -> this.gameObjectsFactory.createMissile(m.getInitAngle(), m.getInitVelocity(), MvcGameConfig.DEFAULT_MISSILE_LIFETIME)).collect(Collectors.toList());
        memento.enemies = this.enemies.stream().map(e -> this.gameObjectsFactory.createEnemy(e.getPosition(), e.getType())).collect(Collectors.toList());
        memento.score = this.score;
        memento.numberOfMissilesShot = this.numberOfMissilesShot;
        return memento;
    }

    @Override
    public void setMemento(Object memento) {
        Memento memento_ = (Memento) memento;
        this.cannon.getPosition().setX(memento_.cannonPosition.getX());
        this.cannon.getPosition().setY(memento_.cannonPosition.getY());
        this.missiles = memento_.missiles;
        this.enemies = memento_.enemies;
        this.score = memento_.score;
        this.numberOfMissilesShot = memento_.numberOfMissilesShot;
    }
}
