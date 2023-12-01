package cz.cvut.fit.niadp.mvcgame;

import java.util.List;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
// in the future, use Bridge to remove this dependency
import cz.cvut.fit.niadp.mvcgame.controller.GameController;
import cz.cvut.fit.niadp.mvcgame.memento.CareTaker;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.view.GameView;
import cz.cvut.fit.niadp.mvcgame.view.graphicscontext.GraphicsContextJavaFXWrapper;
import cz.cvut.fit.niadp.mvcgame.visitor.GameObjectsSoundMaker;
import javafx.scene.canvas.GraphicsContext;

public class MvcGame {


    private GameModel model;
    private GameView view;
    private GameController controller;

    public void init() {
        GameObjectsSoundMaker soundMaker = new GameObjectsSoundMaker();
        this.model = new GameModel(soundMaker);
        this.view = new GameView(model);
        this.controller = this.view.getController();
        CareTaker.getInstance().setModel(this.model);
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
        this.view.setGraphicsContext(new GraphicsContextJavaFXWrapper(gr));
    }
}