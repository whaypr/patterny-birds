package cz.cvut.fit.niadp.mvcgame;

import java.util.List;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
// in the future, use Bridge to remove this dependency
import cz.cvut.fit.niadp.mvcgame.controller.GameController;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.view.GameView;
import javafx.scene.canvas.GraphicsContext;

public class MvcGame {


    private GameModel model;
    private GameView view;
    private GameController controller;

    public void init() {
        this.model = new GameModel();
        this.view = new GameView(model);
        this.controller = this.view.getController();
    }

    public void processPressedKeys(List<String> pressedKeysCodes) {
        this.controller.processPressedKeys(pressedKeysCodes);
    }

    public String getWindowTitle() {
        return MvcGameConfig.GAME_TITLE;
    }

    public int getWindowWidth() {
        return MvcGameConfig.MAX_X;
    }

    public int getWindowHeight() {
        return  MvcGameConfig.MAX_Y;
    }

    public void setGraphicsContext(GraphicsContext gr) {
        this.view.setGraphicsContext(gr);
    }
}