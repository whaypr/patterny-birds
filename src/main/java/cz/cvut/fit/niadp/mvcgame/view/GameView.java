package cz.cvut.fit.niadp.mvcgame.view;

import cz.cvut.fit.niadp.mvcgame.bridge.GameGraphics;
import cz.cvut.fit.niadp.mvcgame.bridge.IGameGraphics;
import cz.cvut.fit.niadp.mvcgame.bridge.NoGraphics;
import cz.cvut.fit.niadp.mvcgame.controller.GameController;
import cz.cvut.fit.niadp.mvcgame.model.IGameModel;
import cz.cvut.fit.niadp.mvcgame.observer.Aspect;
import cz.cvut.fit.niadp.mvcgame.observer.IObserver;
import cz.cvut.fit.niadp.mvcgame.visitor.GameObjectsRender;

public class GameView implements IObserver {

    private final IGameModel model;
    private final GameController controller;
    private IGameGraphics gameGraphics;
    private final GameObjectsRender render;

    public GameView(IGameModel model) {
        this.gameGraphics = new GameGraphics(NoGraphics.getInstance());
        this.model = model;
        this.model.registerObserver(this, Aspect.OBJECT_POSITIONS);
        this.model.registerObserver(this, Aspect.OBJECT_ANGLES);
        this.model.registerObserver(this, Aspect.STATUS);
        this.controller = new GameController(this.model);
        this.render = new GameObjectsRender();
    }

    public GameController getController() {
        return this.controller;
    }

    private void render() {
        this.gameGraphics.clear();
        this.model.getGameObjects().forEach(gameObject -> gameObject.acceptVisitor(this.render));
    }

    public void setGraphicsContext(IGameGraphics gameGraphics) {
        this.gameGraphics = gameGraphics;
        this.render.setGraphicsContext(gameGraphics);
        this.render();
    }

    @Override
    public void update(Aspect aspect) {
        switch (aspect) {
            case OBJECT_POSITIONS:
            case OBJECT_ANGLES:
            case STATUS:
                this.render();
            default: {}
        }
    }
}
