package cz.cvut.fit.niadp.mvcgame.view;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.controller.GameController;
import cz.cvut.fit.niadp.mvcgame.model.GameModel;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;
import cz.cvut.fit.niadp.mvcgame.view.graphicscontext.GraphicsContextNull;
import cz.cvut.fit.niadp.mvcgame.view.graphicscontext.IGraphicsContext;
import cz.cvut.fit.niadp.mvcgame.visitor.GameObjectsRender;

public class GameView implements IObserver {

    private final GameModel model;
    private final GameController controller;
    private IGraphicsContext gr;
    private final GameObjectsRender render;

    public GameView(GameModel model) {
        this.gr = GraphicsContextNull.getInstance();
        this.model = model;
        this.model.registerObserver(this, Aspect.OBJECT_POSITIONS);
        this.controller = new GameController(this.model);
        this.render = new GameObjectsRender();
    }

    public GameController getController() {
        return this.controller;
    }

    private void render() {
        // Clear the canvas
        this.gr.clearRect(0, 0, MvcGameConfig.MAX_X, MvcGameConfig.MAX_Y);
        this.model.getGameObjects().forEach(gameObject -> gameObject.acceptVisitor(this.render));
    }

    public void setGraphicsContext(IGraphicsContext gr) {
        this.gr = gr;
        this.render.setGraphicsContext(gr);
        this.render();
    }

    @Override
    public void update(Aspect aspect) {
        switch (aspect) {
            case OBJECT_POSITIONS -> this.render();
            default -> {}
        }
    }
}
