package cz.cvut.fit.niadp.mvcgame.visitor;

import cz.cvut.fit.niadp.mvcgame.config.MvcGameConfig;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsCannon;
import cz.cvut.fit.niadp.mvcgame.model.gameObjects.AbsMissile;
import cz.cvut.fit.niadp.mvcgame.view.graphicscontext.GraphicsContextNull;
import cz.cvut.fit.niadp.mvcgame.view.graphicscontext.IGraphicsContext;

public class GameObjectsRender implements IGameObjectsVisitor {
    private IGraphicsContext gr;

    public GameObjectsRender() {
        this.gr = GraphicsContextNull.getInstance();
    }

    public void setGraphicsContext(IGraphicsContext gr) {
        this.gr = gr;
    }

    @Override
    public void visitCannon(AbsCannon cannon) {
        this.gr.drawImage(
                MvcGameConfig.CANNON_IMAGE_RESOURCE,
                cannon.getPosition().getX(),
                cannon.getPosition().getY()
        );
    }

    @Override
    public void visitMissile(AbsMissile missile) {
        this.gr.drawImage(
                MvcGameConfig.MISSILE_IMAGE_RESOURCE,
                missile.getPosition().getX(),
                missile.getPosition().getY()
        );
    }
}
