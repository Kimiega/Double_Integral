package models;

import static java.lang.Math.*;

public class Line {
    private final Dot dot1;
    private final Dot dot2;

    public Line(Dot dot1, Dot dot2) {
        this.dot1 = dot1;
        this.dot2 = dot2;
    }

    public Dot getDot1() {
        return dot1;
    }

    public Dot getDot2() {
        return dot2;
    }

    boolean dotOnLine(Dot dot)
    {
       return dot.getX() <= max(dot1.getX(), dot2.getX())
                && dot.getX() <= min(dot1.getX(), dot2.getX())
                && (dot.getY() <= max(dot1.getY(), dot2.getY())
                && dot.getY() <= min(dot1.getY(), dot2.getY()));
    }
}
