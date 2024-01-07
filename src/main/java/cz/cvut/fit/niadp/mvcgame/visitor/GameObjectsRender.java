package cz.cvut.fit.niadp.mvcgame.visitor;

import cz.cvut.fit.niadp.mvcgame.bridge.GameGraphics;
import cz.cvut.fit.niadp.mvcgame.bridge.IGameGraphics;
import cz.cvut.fit.niadp.mvcgame.bridge.NoGraphics;
import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.cannon.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.enemy.AbsEnemy;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.missile.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.gameInfo.AbsGameInfo;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.wall.AbsWall;

public class GameObjectsRender implements IGameObjectsVisitor {
    private IGameGraphics gameGraphics;

    private static final Position borderTopLeft = new Position(10, 10);
    private static final Position borderBottomRight = new Position(MvcGameConfig.MAX_X - 10, 100);
    private static final int columnsXOffset = 320;
    private static final int textYOffset = 25;
    private void printColumn(int columnNumber, String top, String middle, String bottom) {
        Position columnTopLeft = new Position(
                borderTopLeft.getX() + columnNumber*columnsXOffset, borderTopLeft.getY()
        );

        this.gameGraphics.drawText(
                top,
                new Position(columnTopLeft.getX(), columnTopLeft.getY() + textYOffset)
        );
        this.gameGraphics.drawText(
                middle,
                new Position(columnTopLeft.getX(), columnTopLeft.getY() + 2*textYOffset)
        );
        this.gameGraphics.drawText(
                bottom,
                new Position(columnTopLeft.getX(), columnTopLeft.getY() + 3*textYOffset)
        );
    }

    public GameObjectsRender() {
        this.gameGraphics = new GameGraphics(NoGraphics.getInstance());
    }

    public void setGraphicsContext(IGameGraphics gameGraphics) {
        this.gameGraphics = gameGraphics;
    }

    @Override
    public void visitCannon(AbsCannon cannon) {
        this.gameGraphics.drawImage(MvcGameConfig.CANNON_IMAGE_RESOURCE, cannon.getPosition());
    }

    @Override
    public void visitMissile(AbsMissile missile) {
        this.gameGraphics.drawImage((MvcGameConfig.MISSILE_IMAGE_RESOURCE), missile.getPosition());
    }

    @Override
    public void visitEnemy(AbsEnemy enemy) {
        switch (enemy.getType()) {
            case BASIC ->
                    this.gameGraphics.drawImage(MvcGameConfig.ENEMY_BASIC_IMAGE_RESOURCE, enemy.getPosition());
            case WITH_HELMET ->
                    this.gameGraphics.drawImage(MvcGameConfig.ENEMY_WITH_HELMET_IMAGE_RESOURCE, enemy.getPosition());
        }
    }

    @Override
    public void visitWall(AbsWall wall) {
        this.gameGraphics.drawImage((MvcGameConfig.WALL_IMAGE_RESOURCE), wall.getPosition());
    }

    @Override
    public void visitGameInfo(AbsGameInfo gameInfo) {
        this.gameGraphics.drawRectangle(borderTopLeft, borderBottomRight);
        printColumn(
                1,
                "Cannon angle: " + gameInfo.cannonAngle(),
                "Cannon power: " + gameInfo.cannonPower(),
                "Cannon shooting state: " + gameInfo.cannonShootingState().getName()
        );
        printColumn(
                2,
                "Missile moving strat: " + gameInfo.missilesMovingStrategy().getName(),
                "Dynamic mode missiles: " + gameInfo.cannonDynamicShootingModeNumberOfMissiles(),
                ""
        );
        printColumn(
                3,
                "Score: " + gameInfo.score(),
                "Missiles shot: " + gameInfo.numberOfMissilesShot(),
                "Enemies left: " + gameInfo.enemiesLeft()
        );
    }
}
