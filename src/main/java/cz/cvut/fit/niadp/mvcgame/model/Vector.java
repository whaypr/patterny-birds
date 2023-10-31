package cz.cvut.fit.niadp.mvcgame.model;

public class Vector {
    private int dX = 0;
    private int dY = 0;

    public Vector(int dX, int dY) {
        this.dX = dX;
        this.dY = dY;
    }

    public int getDX() {
        return this.dX;
    }

    public int getDY() {
        return this.dY;
    }

    public void setDX(int dX) {
        this.dX = dX;
    }

    public void setDY(int dY) {
        this.dY = dY;
    }
}
