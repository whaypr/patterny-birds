package cz.cvut.fit.niadp.mvcgame.view;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.controller.GameController;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;
import cz.cvut.fit.niadp.mvcgame.view.graphicscontext.GraphicsContextNull;
import cz.cvut.fit.niadp.mvcgame.view.graphicscontext.IGraphicsContext;
import javafx.scene.image.Image;

public class GameView implements IObserver {

    private final GameModel model;
    private final GameController controller;
    private IGraphicsContext gr;

    public GameView(GameModel model) {
        this.model = model;
        this.controller = new GameController(this.model);
        this.model.registerObserver(this);
        this.gr = GraphicsContextNull.getInstance();
    }

    public GameController getController() {
        return this.controller;
    }

    private void render() {
        // Clear the canvas
        this.gr.clearRect(0, 0, MvcGameConfig.MAX_X, MvcGameConfig.MAX_Y);
        this.drawCannon();
    }

    private void drawCannon() {
        Position cannonPosition = this.model.getCannonPosition();
        this.gr.drawImage(new Image(MvcGameConfig.CANNON_IMAGE_RESOURCE), cannonPosition.getX(), cannonPosition.getY());
    }

    public void setGraphicsContext(IGraphicsContext gr) {
        this.gr = gr;
        this.render();
    }

    @Override
    public void update() {
        this.render();
    }
}
