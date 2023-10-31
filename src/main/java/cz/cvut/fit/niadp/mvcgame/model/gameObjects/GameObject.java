package cz.cvut.fit.niadp.mvcgame.model.gameObjects;

import cz.cvut.fit.niadp.mvcgame.model.Position;
import cz.cvut.fit.niadp.mvcgame.model.Vector;

public abstract class GameObject {

    protected Position position;

    public void move(Vector vector){
        this.position.add(vector);
    }

    public Position getPosition() {
        return this.position;
    }
}
