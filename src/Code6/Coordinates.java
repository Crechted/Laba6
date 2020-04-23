package Code6;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 18L;
    private long x; //Значение поля должно быть больше -904
    private int y; //Максимальное значение поля: 551

    public Coordinates(long x, int y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        Coordinates cor = (Coordinates) obj;
        if(this.x == cor.getX() && this.y == cor.getY())
            return true;
        else
            return false;
    }
}